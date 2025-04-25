package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.notificacao.ComunicadoDTO;
import com.hav.imobiliaria.controller.dto.usuario.*;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelGetMapper;
import com.hav.imobiliaria.controller.mapper.usuario.ApresentacaoCorretorGetMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioListaSelectResponseMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioGetMapper;
import com.hav.imobiliaria.controller.mapper.usuario.UsuarioListagemResponseMapper;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.enums.RoleEnum;
import com.hav.imobiliaria.service.UsuarioService;
import com.hav.imobiliaria.validator.DtoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
@Tag(name = "Usuários", description = "Operações relacionadas a usuários e corretores")
public class UsuarioController implements GenericController{

    private final UsuarioListaSelectResponseMapper usuarioListaSelectResponseMapper;
    private UsuarioService service;
    private DtoValidator  dtoValidator;
    private final UsuarioGetMapper usuarioGetMapper;
    private final UsuarioListagemResponseMapper usuarioListagemResponseMapper;
    private final ImovelGetMapper imovelGetMapper;
    private final ApresentacaoCorretorGetMapper apresentacaoCorretorGetMapper;


    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna uma lista paginada de usuários com base nos filtros fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<UsuarioListagemResponseDTO>> listarEmPaginas(
            @Parameter(description = "Nome do usuário para busca") 
            @RequestParam(value = "nome", required = false) String nome,
            @Parameter(description = "Filtrar por status ativo") 
            @RequestParam(value = "ativo", required = false) Boolean ativo,
            @Parameter(description = "Filtrar por perfil de usuário") 
            @RequestParam(value = "role", required = false) RoleEnum role,
            @Parameter(description = "Configuração de paginação") 
            Pageable pageable) {

        return ResponseEntity.ok(service.buscarTodos(nome,ativo,role,pageable).map(usuarioListagemResponseMapper::toDto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/total")
    @Operation(summary = "Buscar total de usuários", description = "Retorna o número total de usuários cadastrados no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Long> buscarTotalUsuarios() {
        return ResponseEntity.ok(service.buscarTotalUsuarios());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes de um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<UsuarioGetDTO> buscarPorId(
            @Parameter(description = "ID do usuário", required = true) 
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioGetMapper.toDto(service.buscarPorId(id)));
    }

    @GetMapping("/corretor/{id}")
    @Operation(summary = "Buscar corretor por ID", description = "Retorna os detalhes de um corretor específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Corretor encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Corretor não encontrado", content = @Content)
    })
    public ResponseEntity<CorretorResponseDto> buscarPorCorretor(
            @Parameter(description = "ID do corretor", required = true) 
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioListagemResponseMapper.toCorretorResponseDto(service.buscarCorretor(id)));
    }
    
    @PreAuthorize("permitAll()")
    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<UsuarioGetDTO> cadastrar(
            @Parameter(description = "Dados do usuário em formato JSON", required = true) 
            @RequestPart(value = "usuario") @Valid String usuarioJson,
            @Parameter(description = "Imagem de perfil do usuário") 
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException, MethodArgumentNotValidException {

        ObjectMapper mapper = new ObjectMapper();
        UsuarioPostDTO usuarioPostDTO = mapper.readValue(usuarioJson, UsuarioPostDTO.class);

        this.dtoValidator.validaDTO(UsuarioPostDTO.class, usuarioPostDTO,"usuarioPostDTO");

        return ResponseEntity.ok(this.usuarioGetMapper.toDto
                (service.salvar(usuarioPostDTO,file)));
    }
    
    @PreAuthorize("isAuthenticated()")
    @PutMapping("{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<UsuarioGetDTO> atualizar(
            @Parameter(description = "Dados atualizados do usuário em formato JSON", required = true) 
            @RequestPart @Valid String usuario,
            @Parameter(description = "Nova imagem de perfil") 
            @RequestPart(required = false) MultipartFile novaImagem,
            @Parameter(description = "ID do usuário", required = true) 
            @PathVariable Long id ) throws IOException, MethodArgumentNotValidException {

        ObjectMapper mapper = new ObjectMapper();
        UsuarioPutDTO usuarioPutDTO = mapper.readValue(usuario, UsuarioPutDTO.class);

        this.dtoValidator.validaDTO(UsuarioPutDTO.class, usuarioPutDTO,"usuarioPutDTO");

        return ResponseEntity.ok(this.usuarioGetMapper.toDto(service.atualizar(usuarioPutDTO,id, novaImagem)));
    }
    
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("{id}")
    @Operation(summary = "Remover usuário", description = "Remove um usuário pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> removerPorId(
            @Parameter(description = "ID do usuário", required = true) 
            @PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/restaurar/{id}")
    @Operation(summary = "Restaurar usuário", description = "Restaura um usuário previamente removido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário restaurado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> restaurarUsuario(
            @Parameter(description = "ID do usuário", required = true) 
            @PathVariable Long id) {
        this.service.restaurarUsuario(id);
        return ResponseEntity.ok().build();
    }

//    @DeleteMapping("/imagem/{id}")
//    public ResponseEntity<Void> removerImagemUsuario(@PathVariable Long id){
//        this.service.removerImagemUsuario(id);
//        return ResponseEntity.noContent().build();
//    }

    @PreAuthorize("permitAll()")
    @GetMapping("/corretores-lista-select")
    @Operation(summary = "Listar corretores para seleção", description = "Retorna uma lista de corretores disponíveis para seleção")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso")
    })
    public ResponseEntity<List<UsuarioListaSelectResponseDTO>> listarCorretoresListaSelect() {
        List<Usuario> usuarios = this.service.buscarCorretorListagem();
        return ResponseEntity.ok(usuarios.stream().map(usuarioListaSelectResponseMapper::toDto).toList());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("favoritos")
    @Operation(summary = "Adicionar imóvel aos favoritos", description = "Adiciona um imóvel à lista de favoritos do usuário autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Imóvel adicionado aos favoritos com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Imóvel não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> cadastrarFavorito(
            @Parameter(description = "ID do imóvel a ser favoritado", required = true) 
            @RequestParam Long idImovel) {
        this.service.adicionarImovelFavorito(idImovel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("favoritos")
    @Operation(summary = "Remover imóvel dos favoritos", description = "Remove um imóvel da lista de favoritos do usuário autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Imóvel removido dos favoritos com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Imóvel não encontrado nos favoritos", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> removerFavorito(
            @Parameter(description = "ID do imóvel a ser removido dos favoritos", required = true) 
            @RequestParam Long idImovel) {
        this.service.removerImovelFavorito(idImovel);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("lista-id-usuarios")
    @Operation(summary = "Listar IDs de usuários", description = "Retorna uma lista com todos os IDs de usuários cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de IDs obtida com sucesso")
    })
    public ResponseEntity<List<Long>> listarIdUsuario() {
        return ResponseEntity.ok(service.buscarIdUsuarios());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/corretorApresentacao/{role}")
    @Operation(summary = "Listar corretores para apresentação", description = "Retorna uma lista de corretores para exibição na página de apresentação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso"),
        @ApiResponse(responseCode = "400", description = "Tipo de função inválido", content = @Content)
    })
    public ResponseEntity<List<ApresentacaoCorretorDTO>> listarCorretorApresentacao(
            @Parameter(description = "Função/perfil do usuário", required = true) 
            @PathVariable RoleEnum role) {
        return ResponseEntity.ok(apresentacaoCorretorGetMapper.toDTO(service.buscarPorRole(role)));
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/ids-imoveis-favoritados")
    @Operation(summary = "Listar IDs de imóveis favoritados", description = "Retorna uma lista com todos os IDs de imóveis favoritados pelo usuário autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de IDs obtida com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<Long>> listarIdsImovelPorIdUsuario() {
        return ResponseEntity.ok(service.buscarIdsImovelFavoritadoPorIdUsuario());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/token-redefinir-senha")
    @Operation(summary = "Solicitar token para redefinição de senha", description = "Envia um email com token para redefinição de senha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email enviado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Email não encontrado", content = @Content)
    })
    public ResponseEntity<Void> tokenRedefinirSenha(
            @Parameter(description = "Email do usuário", required = true) 
            @RequestParam String email) {
        this.service.enviarEmailParaRefefinicaoSenha(email);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/redefinir-senha")
    @Operation(summary = "Redefinir senha", description = "Altera a senha do usuário utilizando o token de redefinição")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Token inválido ou expirado", content = @Content)
    })
    public ResponseEntity<Void> alterarSenha(
            @Parameter(description = "Dados para troca de senha", required = true) 
            @Valid @RequestBody TrocaDeSenhaDTO trocaDeSenhaDto) {
        this.service.alterarSenha(trocaDeSenhaDto);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("comunicado")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @Operation(summary = "Enviar comunicado", description = "Envia um comunicado para usuários do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comunicado enviado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> enviarComunidado(
            @Parameter(description = "Dados do comunicado", required = true) 
            @RequestBody ComunicadoDTO comunicadoDTO) {
        this.service.enviarComunicado(comunicadoDTO);
        return ResponseEntity.ok().build();
    }
}
