package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.imovel.ImovelListagemDTO;
import com.hav.imobiliaria.controller.dto.usuario.*;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelGetMapper;
import com.hav.imobiliaria.controller.mapper.usuario.ApresentacaoCorretorGetMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioListaSelectResponseMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioGetMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioListagemResponseMapper;
import com.hav.imobiliaria.model.entity.Imovel;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.enums.RoleEnum;
import com.hav.imobiliaria.service.UsuarioService;
import com.hav.imobiliaria.validator.DtoValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController implements GenericController{

    private final UsuarioListaSelectResponseMapper usuarioListaSelectResponseMapper;
    private UsuarioService service;
    private DtoValidator  dtoValidator;
    private final UsuarioGetMapper usuarioGetMapper;
    private final UsuarioListagemResponseMapper usuarioListagemResponseMapper;
    private final ImovelGetMapper imovelGetMapper;
    private final ApresentacaoCorretorGetMapper apresentacaoCorretorGetMapper;


    @GetMapping
    public ResponseEntity<Page<UsuarioListagemResponseDTO>> listarEmPaginas(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "ativo", required = false) Boolean ativo,
            @RequestParam(value = "role", required = false) RoleEnum role,
            Pageable pageable) {

        return ResponseEntity.ok(service.buscarTodos(nome,ativo,role,pageable).map(usuarioListagemResponseMapper::toDto));
    }

    @GetMapping("/total")
    public ResponseEntity<Long> buscarTotalUsuarios() {
        return ResponseEntity.ok(service.buscarTotalUsuarios());
    }
    @GetMapping("{id}")
    public ResponseEntity<UsuarioGetDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioGetMapper.toDto(service.buscarPorId(id)));
    }
    @GetMapping("/corretor/{id}")
    public ResponseEntity<CorretorResponseDto> buscarPorCorretor(@PathVariable Long id) {

        return ResponseEntity.ok(usuarioListagemResponseMapper.toCorretorResponseDto(service.buscarCorretor(id)));
    }
    @PostMapping
    public ResponseEntity<UsuarioGetDTO> cadastrar(@RequestPart(value = "usuario") @Valid String usuarioJson,
                                                   @RequestPart(value = "file", required = false) MultipartFile file) throws IOException, MethodArgumentNotValidException {

        ObjectMapper mapper = new ObjectMapper();
        UsuarioPostDTO usuarioPostDTO = mapper.readValue(usuarioJson, UsuarioPostDTO.class);

        this.dtoValidator.validaDTO(UsuarioPostDTO.class, usuarioPostDTO,"usuarioPostDTO");


        return ResponseEntity.ok(this.usuarioGetMapper.toDto
                (service.salvar(usuarioPostDTO,file)));


    }
    @PutMapping("{id}")
    public ResponseEntity<UsuarioGetDTO> atualizar(@RequestPart @Valid String usuario,
                                                   @RequestPart(required = false) MultipartFile novaImagem,
                                                   @PathVariable Long id ) throws IOException, MethodArgumentNotValidException {

        ObjectMapper mapper = new ObjectMapper();
        UsuarioPutDTO usuarioPutDTO = mapper.readValue(usuario, UsuarioPutDTO.class);

        this.dtoValidator.validaDTO(UsuarioPutDTO.class, usuarioPutDTO,"usuarioPutDTO");


        return ResponseEntity.ok(this.usuarioGetMapper.toDto(service.atualizar(usuarioPutDTO,id, novaImagem)));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/restaurar/{id}")
    public ResponseEntity<Void> restaurarUsuario(@PathVariable Long id) {
        this.service.restaurarUsuario(id);

        return  ResponseEntity.ok().build();
    }

    @DeleteMapping("/imagem/{id}")
    public ResponseEntity<Void> removerImagemUsuario(@PathVariable Long id){
        this.service.removerImagemUsuario(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/corretores-lista-select")
    public ResponseEntity<List<UsuarioListaSelectResponseDTO>> listarCorretoresListaSelect(){
        List<Usuario> usuarios = this.service.buscarCorretorListagem();
        return  ResponseEntity.ok(usuarios.stream().map(usuarioListaSelectResponseMapper::toDto).toList());
    }
    @GetMapping("favoritos/{id}")
    public ResponseEntity<Page<ImovelListagemDTO>> listarFavoritos(@PathVariable Long id, Pageable pageable) {
        Page<Imovel> imoveis = this.service.buscarImoveisFavoritados(id, pageable);

        return ResponseEntity.ok(imoveis.map(imovelGetMapper::toImovelListagemDto));
    }
    @PostMapping("favoritos")
    public ResponseEntity<Void> cadastrarFavorito(@RequestParam Long idImovel,
                                                           @RequestParam Long idUsuario){

        this.service.adicionarImovelFavorito(idImovel,idUsuario);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("favoritos")
    public ResponseEntity<Void> removerFavorito(@RequestParam Long idImovel,
                                                @RequestParam Long idUsuario){

        this.service.remocarImovelFavorito(idImovel,idUsuario);
        return ResponseEntity.noContent().build();

    }
    @GetMapping("lista-id-usuarios")
    public ResponseEntity<List<Long>> listarIdUsuario(){
        return ResponseEntity.ok(service.buscarIdUsuarios());
    }

    @GetMapping("/corretorApresentacao/{role}")
    public ResponseEntity<List<ApresentacaoCorretorDTO>> listarCorretorApresentacao(@PathVariable RoleEnum role) {
        return ResponseEntity.ok(apresentacaoCorretorGetMapper.toDTO(service.buscarPorRole(role)));
    }


//    @PatchMapping("/alterarSenha/{id}")
//    public ResponseEntity<Void> alterarSenha(@Valid @RequestBody SenhaUsuarioDto senhaUsuarioDto ,
//                                             @PathVariable Long id) {
//        this.service.alterarSenha(id,senhaUsuarioDto.senha());
//
//        return ResponseEntity.ok().build();
//    }


}
