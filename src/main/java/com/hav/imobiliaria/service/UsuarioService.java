package com.hav.imobiliaria.service;

import com.hav.imobiliaria.controller.dto.notificacao.ComunicadoDTO;
import com.hav.imobiliaria.controller.dto.usuario.TrocaDeSenhaDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPostDTO;
import com.hav.imobiliaria.controller.dto.usuario.UsuarioPutDTO;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioPostMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioPutMapper;
import com.hav.imobiliaria.exceptions.AcessoNegadoException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.SenhaUtilizadaRecentementeException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.TokenRecuperacaoDeSenhaInvalidoException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.UsuarioNaoEncontradoException;
import com.hav.imobiliaria.model.entity.*;
import com.hav.imobiliaria.model.enums.RoleEnum;
import com.hav.imobiliaria.model.enums.TipoEmailEnum;
import com.hav.imobiliaria.repository.TokenRecuperacaoSenhaRepository;
import com.hav.imobiliaria.repository.UsuarioRepository;
import com.hav.imobiliaria.repository.specs.UsuarioSpecs;
import com.hav.imobiliaria.security.utils.SecurityUtils;
import com.hav.imobiliaria.validator.UsuarioValidator;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final S3Service s3Service;
    private final UsuarioPostMapper usuarioPostMapper;
    private final UsuarioPutMapper usuarioPutMapper;
    private final UsuarioValidator validator;
    private final ImovelService imovelService;
    private final TokenRecuperacaoSenhaRepository tokenRecuperacaoSenhaRepository;
    private final EmailService emailService;
    private  PasswordEncoder passwordEncoder;

    public Page<Usuario> buscarTodos(
            String nome,
            Boolean ativo,
            RoleEnum role,
            Pageable pageable
            ) {

        Specification<Usuario> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (nome != null && !nome.isBlank()) {
            specs = specs.and(UsuarioSpecs.nomeLike(nome));
        }
        if (ativo != null) {
            System.out.println(ativo);
            specs = specs.and(UsuarioSpecs.usuarioAtivo(ativo));
        }
        if (role != null) {
            specs = specs.and(UsuarioSpecs.roleUsuario(role));
        }
        System.out.println(repository.findAll(specs, pageable));


        return repository.findAll(specs, pageable);
    }
    public Usuario salvarUsuarioRequisicaoGoogle(Usuario usuario){
        usuario.setSenha(passwordEncoder.encode("ADADADIASDHASUDBASUDUOAHDI1a#1asd"));
        usuario.setRole(RoleEnum.USUARIO);

        return this.repository.save(usuario);

    }
    public Usuario buscarPorId(Long id) {
        return repository.findById(id).get();

    }
    public List<Long> buscarIdUsuarios(){
        return repository.findAll().stream().map(Usuario::getId).toList();
    }
    public Usuario salvar(UsuarioPostDTO dto, MultipartFile foto) throws IOException {

        if(!dto.role().equals(RoleEnum.USUARIO) && !SecurityUtils.buscarUsuarioLogado().getRole().equals(RoleEnum.ADMINISTRADOR)){
            throw new AcessoNegadoException();
        }

        Usuario entity = instanciandoUsuarioPostDtoPorRole(dto);

        this.validator.validar(entity);

        String url = null;
        if(foto != null) {
            url = s3Service.uploadArquivo(foto);
        }
        entity.setFoto(url);
        entity.setSenha(passwordEncoder.encode(entity.getSenha()));
        return repository.save(entity);
    }
    public Usuario atualizar(UsuarioPutDTO dto, Long id, MultipartFile imagemNova) throws IOException {
        Usuario usuarioAtualizado = instanciadoUsuarioPutDtoPorRole(dto);
        Usuario usuarioSalvo = buscarPorId(id);
        usuarioAtualizado.setId(id);
        this.validator.validar(usuarioAtualizado);
        if(!usuarioSalvo.getRole().equals(usuarioAtualizado.getRole())) {
            if(SecurityUtils.buscarUsuarioLogado().getRole().equals(RoleEnum.ADMINISTRADOR)) {
                usuarioAtualizado.setId(null);
                excluirReferenciaImovelCorretor(id);
            }
            else{
                throw new AcessoNegadoException();
            }
        }
        Usuario usuarioJaSalvo = this.buscarPorId(id);
        if(imagemNova != null){
            if(usuarioJaSalvo.getFoto() != null){
                s3Service.excluirObjeto(usuarioJaSalvo.getFoto());
            }
            usuarioAtualizado.setFoto(s3Service.uploadArquivo(imagemNova));
        }else {
            usuarioAtualizado.setFoto(usuarioJaSalvo.getFoto());
        }
        if(usuarioAtualizado.getSenha() == null){
            usuarioAtualizado.setSenha(usuarioJaSalvo.getSenha());
        }else {
            usuarioAtualizado.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
        }

        if(usuarioAtualizado.getId() == null){
            this.repository.deleteById(id);
        }
        if(usuarioAtualizado.getAtivo() == null){
            usuarioAtualizado.setAtivo(usuarioJaSalvo.getAtivo());
        }
        if(usuarioAtualizado.getRole() == null){
            usuarioAtualizado.setRole(usuarioJaSalvo.getRole());
        }
        if(usuarioAtualizado.getAutenticacaoDoisFatoresHabilitado() == null){
            usuarioAtualizado.setAutenticacaoDoisFatoresHabilitado(usuarioSalvo.getAutenticacaoDoisFatoresHabilitado());
        }

        return repository.save(usuarioAtualizado);

    }
    public void removerPorId(Long id) {
        Optional<Usuario> usuarioOptional = repository.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();
            usuario.setAtivo(false);
            usuario.setDataDelecao(LocalDateTime.now());
            this.repository.save(usuario);
        }
    }
    public void removerImagemUsuario(Long idUsuario){
        Usuario usuario = this.buscarPorId(idUsuario);
        if(usuario.getFoto() != null){
            this.s3Service.excluirObjeto(usuario.getFoto());
            usuario.setFoto(null);
        }
    }
    public void restaurarUsuario(Long id){

        Usuario usuario = this.buscarPorId(id);

        usuario.setAtivo(true);
        usuario.setDataDelecao(null);
        this.repository.save(usuario);
    }

    @Transactional
    public void alterarSenha(TrocaDeSenhaDTO trocaDeSenhaDto) {

        TokenRecuperacaoSenha tokenRecuperacaoSenha =
                tokenRecuperacaoSenhaRepository.findByToken(trocaDeSenhaDto.token()).orElseThrow(TokenRecuperacaoDeSenhaInvalidoException::new);



        if(tokenRecuperacaoSenha.getDataExpiracao().plusMinutes(15).isBefore(LocalDateTime.now())){
            throw new TokenRecuperacaoDeSenhaInvalidoException();
        }
        validarSenhaAntiga(tokenRecuperacaoSenha.getUsuario(),trocaDeSenhaDto.senha());
        atualizarSenhaUsuario(tokenRecuperacaoSenha.getUsuario() ,trocaDeSenhaDto.senha());

        this.repository.save(tokenRecuperacaoSenha.getUsuario());

    }
    private Usuario instanciandoUsuarioPostDtoPorRole(UsuarioPostDTO dto) {

        if(dto.role().equals(RoleEnum.CORRETOR)){
            return usuarioPostMapper.toCorretorEntity(dto);
        }
        else if (dto.role().equals(RoleEnum.USUARIO)) {
            return usuarioPostMapper.toUsuarioComumEntity(dto);
        } else if (dto.role().equals(RoleEnum.EDITOR)) {
            return  usuarioPostMapper.toEditorEntity(dto);
        } else if (dto.role().equals(RoleEnum.ADMINISTRADOR)) {
            return usuarioPostMapper.toAdministradorEntity(dto);
        }
        throw new RuntimeException("Role inválida");
    }
    private Usuario instanciadoUsuarioPutDtoPorRole(UsuarioPutDTO dto) {

        if(dto.role().equals(RoleEnum.CORRETOR)){
            return usuarioPutMapper.toCorretorEntity(dto);
        }
        else if (dto.role().equals(RoleEnum.USUARIO)) {
            return usuarioPutMapper.toUsuarioComumEntity(dto);
        } else if (dto.role().equals(RoleEnum.EDITOR)) {
            return  usuarioPutMapper.toEditorEntity(dto);
        } else if (dto.role().equals(RoleEnum.ADMINISTRADOR)) {
            return usuarioPutMapper.toAdministradorEntity(dto);
        }
        throw new RuntimeException("Role inválida");
    }

    public Corretor buscarCorretor(Long id) {
        Usuario usuario = this.repository.findById(id).get();

        if(usuario.getRole().equals(RoleEnum.CORRETOR)){
            return (Corretor) usuario;
        }
        throw new RuntimeException("O usuário informado não é um corretor");
    }

    public Usuario buscarPorEmail(String email){
        return this.repository.findByEmail(email).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public void excluirReferenciaImovelCorretor(Long id) {
        Usuario usuario = this.repository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);

        if(usuario.getRole().equals(RoleEnum.CORRETOR)){
            ((Corretor) usuario).getImoveis().forEach(i -> {
               i.getCorretores().remove(usuario);
            });
        }
    }
    public List<Usuario> buscarCorretorListagem(){
        return  this.repository.findByRoleAndAtivoTrue(RoleEnum.CORRETOR);
    }
    public  List<Long> buscarIdsImovelFavoritadoPorIdUsuario(){
        Usuario usuarioLogado = SecurityUtils.buscarUsuarioLogado();
        return this.repository.findIdImoveisFavoritadosByUsuarioId(usuarioLogado.getId());
    }

    @Transactional
    public void adicionarImovelFavorito(Long idImovel) {
        Usuario usuarioAutenticado = SecurityUtils.buscarUsuarioLogado();
        Usuario usuarioAtual = this.repository.findById(usuarioAutenticado.getId()).orElseThrow(NoSuchElementException::new);
        Imovel imovel = this.imovelService.buscarPorId(idImovel);

        usuarioAtual.adicionarImovelFavorito(imovel);
        this.repository.save(usuarioAtual);

    }
    @Transactional
    public void removerImovelFavorito(Long idImovel) {
        Usuario usuario = SecurityUtils.buscarUsuarioLogado();
        Usuario usuarioAtual = this.repository.findById(usuario.getId()).orElseThrow(NoSuchElementException::new);

        usuarioAtual.removerImovelFavorito(idImovel);
    }
    public List<Usuario> buscarPorRole(RoleEnum role) {
        return repository.buscarPorRole(role);
    }
    public Long buscarTotalUsuarios() {
        return repository.count();
    }


    @Transactional
    public void enviarEmailParaRefefinicaoSenha(String email) {
        Usuario usuario = this.buscarPorEmail(email);

        String token = UUID.randomUUID().toString();

        criarTokenParaRefefinicaoSenha(token, usuario);

        Dotenv dotenv = Dotenv.load();
        Map<String,Object> variaveis = new HashMap<>();
        variaveis.put("nomeCliente", usuario.getNome());
        variaveis.put("titulo", "Recuperacao de senha");
        variaveis.put("mensagem", "Clique no botão abaixo para redefinir a sua senha");
        variaveis.put("linkAcao", dotenv.get("FRONTEND_URL")+ "/autenticacao/mudar-senha/" + token );
        variaveis.put("textoBotao", "Redefinir senha");

        emailService.enviarEmail(
                EmailRequest
                        .builder()
                        .destinatario(usuario.getEmail())
                        .tipoEmail(TipoEmailEnum.NOTIFICACAO_IMOBILIARIA)
                        .variaveis(variaveis)
                        .build()
        );


    }


    private void criarTokenParaRefefinicaoSenha(String token, Usuario usuario) {

       tokenRecuperacaoSenhaRepository.deleteByUsuario_Id(usuario.getId());

       tokenRecuperacaoSenhaRepository.flush();

        TokenRecuperacaoSenha tokenRecuperacaoSenha = new TokenRecuperacaoSenha(token,usuario);

        tokenRecuperacaoSenha.setDataExpiracao(LocalDateTime.now().plusMinutes(15));
        tokenRecuperacaoSenhaRepository.save(tokenRecuperacaoSenha);

    }

    public void enviarComunicado(ComunicadoDTO comunicadoDTO) {

        Map<String, Object> variables = new HashMap<>();


        variables.put("nomeCliente", comunicadoDTO.nome());
        variables.put("titulo", "Você recebeu um comunicado!");
        variables.put("mensagem", comunicadoDTO.mensagem());


        EmailRequest emailRequest = EmailRequest.builder()
                .tipoEmail(TipoEmailEnum.NOTIFICACAO_IMOBILIARIA)
                .destinatario(comunicadoDTO.email())
                .variaveis(variables).build();

        emailService.enviarEmail(emailRequest);

    }
    private void validarSenhaAntiga(Usuario usuario, String senha) {

        if(usuario.getSenhasAntigasUsuario().stream().anyMatch( usuarioSenha ->
                passwordEncoder.matches(senha, usuarioSenha.getSenha()))){
            throw new SenhaUtilizadaRecentementeException();
        }
    }
    private void atualizarSenhaUsuario(Usuario usuario, String senha){

        SenhaAntigaUsuario senhaAntigaUsuario = new SenhaAntigaUsuario();

        senhaAntigaUsuario.setSenha(usuario.getSenha());


        usuario.setSenha(passwordEncoder.encode(senha));
        if(usuario.getSenhasAntigasUsuario().size() >= 5){
            usuario.getSenhasAntigasUsuario().stream()
                    .min(Comparator.comparing(SenhaAntigaUsuario::getDataCadastro))
                    .ifPresent(usuarioSenha -> usuario.removerSenhaAntigaPorId(usuarioSenha.getId()) );
        }
        senhaAntigaUsuario.setUsuario(usuario);
        usuario.getSenhasAntigasUsuario().add(senhaAntigaUsuario);
    }

}
