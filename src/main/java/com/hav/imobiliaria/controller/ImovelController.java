package com.hav.imobiliaria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hav.imobiliaria.controller.dto.imovel.ImovelGetDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelListagemDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPostDTO;
import com.hav.imobiliaria.controller.dto.imovel.ImovelPutDTO;
import com.hav.imobiliaria.controller.dto.usuario.CorretorResponseDto;
import com.hav.imobiliaria.controller.mapper.imovel.ImovelGetMapper;
import com.hav.imobiliaria.model.entity.Corretor;
import com.hav.imobiliaria.model.entity.HorarioCorretor;
import com.hav.imobiliaria.model.entity.Imovel;
import com.hav.imobiliaria.model.enums.TipoFinalidadeEnum;
import com.hav.imobiliaria.model.enums.TipoImovelEnum;
import com.hav.imobiliaria.service.ImovelService;
import com.hav.imobiliaria.validator.DtoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("imoveis")
@AllArgsConstructor
@Tag(name = "Imóveis", description = "Operações relacionadas a imóveis")
public class ImovelController implements GenericController {
    private final ImovelService service;
    private final DtoValidator dtoValidator;
    private final ImovelGetMapper imovelGetMapper;


    @GetMapping
    @Operation(summary = "Listar imóveis", description = "Retorna uma lista paginada de imóveis com base nos filtros fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imóveis encontrados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    public ResponseEntity<Page<ImovelListagemDTO>> listarImoveis(
            @Parameter(description = "Tamanho mínimo do imóvel em m²") @RequestParam(value = "tamanhoMinimo", required = false) Integer tamanhoMinimo,
            @Parameter(description = "Tamanho máximo do imóvel em m²") @RequestParam(value = "tamanhoMaximo", required = false) Integer tamanhoMaximo,
            @Parameter(description = "Título do imóvel para busca") @RequestParam(value = "titulo", required = false) String titulo,
            @Parameter(description = "Tipo de residência") @RequestParam(value = "tipoResidencia", required = false) TipoImovelEnum tipoResidencia,
            @Parameter(description = "Quantidade de quartos") @RequestParam(value = "qtdQuartos", required = false) Integer qtdQuartos,
            @Parameter(description = "Quantidade de banheiros") @RequestParam(value = "qtdBanheiros", required = false) Integer qtdBanheiros,
            @Parameter(description = "Quantidade de garagens") @RequestParam(value = "qtdGaragens", required = false) Integer qtdGaragens,
            @Parameter(description = "Preço mínimo") @RequestParam(value = "precoMinimo", required = false) Double precoMin,
            @Parameter(description = "Preço máximo") @RequestParam(value = "precoMaximo", required = false) Double precoMax,
            @Parameter(description = "Finalidade do imóvel (VENDA ou LOCACAO)") @RequestParam(value = "finalidade", required = false) TipoFinalidadeEnum finalidade,
            @Parameter(description = "Cidade") @RequestParam(value = "cidade", required = false) String cidade,
            @Parameter(description = "Bairro") @RequestParam(value = "bairro", required = false) String bairro,
            @Parameter(description = "Filtrar por imóveis em destaque") @RequestParam(value = "destaque", required = false) Boolean destaque,
            @Parameter(description = "Filtrar por imóveis com condições especiais") @RequestParam(value = "condicoesEspeciais", required = false) Boolean condicoesEspecias,
            @Parameter(description = "ID do usuário proprietário") @RequestParam(value = "idUsuario", required = false) Long idUsuario,
            @Parameter(description = "Buscar também imóveis arquivados") @RequestParam(value = "buscarArquivados", required = false) Boolean buscarArquivados,
            @Parameter(description = "Busca por título ou descrição") @RequestParam(value = "imovelDescTitulo", required = false) String imovelDescTitulo,
            @Parameter(description = "Configuração de paginação") @PageableDefault(page = 0, size = 9) Pageable pageable,
            @Parameter(description = "Filtrar por status ativo") @RequestParam(value = "ativo", required = false) Boolean ativo
    ) {


        Page<Imovel> paginaResultadoDto = service.pesquisa
                (imovelDescTitulo,tamanhoMinimo,tamanhoMaximo, titulo, tipoResidencia, qtdBanheiros, qtdQuartos,
                qtdGaragens, precoMin, precoMax, finalidade,cidade,bairro,
                        ativo,destaque,condicoesEspecias,idUsuario, buscarArquivados, pageable);



        return ResponseEntity.ok(paginaResultadoDto.map(imovelGetMapper::toImovelListagemDto));
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar imóvel por ID", description = "Retorna os detalhes de um imóvel específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imóvel encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Imóvel não encontrado", content = @Content)
    })
    public ResponseEntity<ImovelGetDTO> buscarPorId(
            @Parameter(description = "ID do imóvel", required = true) 
            @PathVariable Long id) {
        return ResponseEntity.ok(imovelGetMapper.toImovelGetDto(service.buscarPorId(id)));
    }
    
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @PostMapping
    @Operation(summary = "Cadastrar imóvel", description = "Cadastra um novo imóvel com suas imagens")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imóvel cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ImovelGetDTO> cadastrar(
            @Parameter(description = "Dados do imóvel em formato JSON", required = true) 
            @RequestPart("imovel") String imovelPostDtoJSON,
            @Parameter(description = "Lista de imagens do imóvel", required = true) 
            @RequestPart("imagens") List<MultipartFile> imagens,
            @Parameter(description = "Imagem principal do imóvel", required = true) 
            @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal
    ) throws IOException, MethodArgumentNotValidException {


        ObjectMapper mapper = new ObjectMapper();
        ImovelPostDTO imovelPostDTO = mapper.readValue(imovelPostDtoJSON, ImovelPostDTO.class);

        dtoValidator.validaDTO(ImovelPostDTO.class,imovelPostDTO,"ImovelPostDto");

        System.out.println(imovelPostDTO + "\n");

        return ResponseEntity.ok(imovelGetMapper.toImovelGetDto(service.salvar(imovelPostDTO,imagemPrincipal, imagens)));

    }
    
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @PutMapping("{id}")
    @Operation(summary = "Atualizar imóvel", description = "Atualiza os dados de um imóvel existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imóvel atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Imóvel não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ImovelGetDTO> atualizar(
            @Parameter(description = "Dados atualizados do imóvel em formato JSON", required = true) 
            @RequestPart("imovel") String imovelPutDTOJSON,
            @Parameter(description = "Lista de referências de imagens a serem deletadas") 
            @RequestParam(value = "refImagensDeletadas", required = false) List<String> refImagensDeletadas,
            @Parameter(description = "Nova imagem principal do imóvel") 
            @RequestPart(value = "imagemPrincipal", required = false) MultipartFile imagemCapa,
            @Parameter(description = "Novas imagens do imóvel") 
            @RequestPart(value = "imagens", required = false) List<MultipartFile> imagens,
            @Parameter(description = "ID do imóvel", required = true) 
            @PathVariable Long id) throws IOException, MethodArgumentNotValidException {


        ObjectMapper mapper = new ObjectMapper();
        ImovelPutDTO imovelPutDTO = mapper.readValue(imovelPutDTOJSON, ImovelPutDTO.class);
        dtoValidator.validaDTO(ImovelPutDTO.class, imovelPutDTO, "ImovelPutDTO");

        return ResponseEntity.ok(imovelGetMapper.toImovelGetDto(service.atualizar(id, imovelPutDTO, imagemCapa, imagens,refImagensDeletadas)));

    }
    
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @PostMapping("/restaurar/{id}")
    @Operation(summary = "Restaurar imagem", description = "Restaura uma imagem previamente removida de um imóvel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagem restaurada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Imóvel ou imagem não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> restaurarImagem(
            @Parameter(description = "ID do imóvel", required = true) 
            @PathVariable Long id) {
        this.service.restaurarImagem(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @DeleteMapping("{id}")
    @Operation(summary = "Remover imóvel", description = "Remove um imóvel pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Imóvel removido com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Imóvel não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> removerPorId(
            @Parameter(description = "ID do imóvel", required = true) 
            @PathVariable Long id) {
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EDITOR')")
    @DeleteMapping("/imagem/{id}")
    @Operation(summary = "Remover imagem de imóvel", description = "Remove uma imagem específica de um imóvel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Imagem removida com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Imagem não encontrada", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> removerImagemPorReferencia(
            @Parameter(description = "ID da imagem", required = true) 
            @PathVariable Long id) {
        this.service.removerImagemPorIdImagem(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/ids-imoveis")
    @Operation(summary = "Listar IDs de imóveis", description = "Retorna uma lista com todos os IDs de imóveis cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de IDs obtida com sucesso")
    })
    public ResponseEntity<List<Long>> listarImoveis() {
        return ResponseEntity.ok(service.buscarTodosIds());
    }
}
