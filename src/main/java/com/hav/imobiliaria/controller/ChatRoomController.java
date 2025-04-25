package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.chat.ChatGetDTO;
import com.hav.imobiliaria.controller.dto.chat.ChatMessageDTO;
import com.hav.imobiliaria.controller.mapper.chat.ChatGetMapper;
import com.hav.imobiliaria.controller.mapper.chat.ChatPostMapper;
import com.hav.imobiliaria.controller.mapper.chat.ChatResponseMapper;
import com.hav.imobiliaria.exceptions.ChatJaCadastradoException;
import com.hav.imobiliaria.exceptions.ChatNaoEncontradoException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.UsuarioNaoEncontradoException;
import com.hav.imobiliaria.model.entity.ChatMessage;
import com.hav.imobiliaria.model.entity.Chats;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.repository.ChatMessagesRepository;
import com.hav.imobiliaria.repository.ChatsRepository;
import com.hav.imobiliaria.repository.UsuarioRepository;
import com.hav.imobiliaria.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/chat")
@CrossOrigin("http://localhost:3000")
@Tag(name = "Chat Rooms", description = "Operações relacionadas a salas de chat")
public class ChatRoomController {

    ChatsRepository repository;
    UsuarioRepository usuarioRepository;
    ChatMessagesRepository chatMessagesRepository;
    ChatGetMapper chatGetMapper;
    ChatResponseMapper chatResponseMapper;
    ChatPostMapper chatPostMapper;
    MessageService messageService;

    @PostMapping("/{idUsuario1}/{idUsuario2}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Criar chat", description = "Cria uma nova sala de chat entre dois usuários")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chat criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Chat já existe entre estes usuários", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> createChat(
            @Parameter(description = "ID do primeiro usuário", required = true) 
            @PathVariable Long idUsuario1,
            @Parameter(description = "ID do segundo usuário", required = true) 
            @PathVariable Long idUsuario2) {
        // Verifica se já existe um chat entre esses usuários
        if (repository.existsByUsuario1IdAndUsuario2IdOrUsuario1IdAndUsuario2Id(
                idUsuario1, idUsuario2)) {
            throw new ChatJaCadastradoException("Chat já existente entre esses usuários");
        }
        Usuario usuario1 = usuarioRepository.findById(idUsuario1)
                .orElseThrow(UsuarioNaoEncontradoException::new);
        Usuario usuario2 = usuarioRepository.findById(idUsuario2)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        Chats chat = new Chats();
        chat.setIdChat(System.currentTimeMillis());
        
        // Inicializa a lista de usuários corretamente
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        chat.setUsuarios(usuarios);

        Chats chatSalvo = repository.save(chat);
        return ResponseEntity.ok(chatPostMapper.toDto(chatSalvo));
    }

    @GetMapping("/join/{idChat}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Entrar em chat", description = "Entra em uma sala de chat específica e retorna suas informações")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entrada no chat realizada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Chat não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> joinChat(
            @Parameter(description = "ID do chat", required = true) 
            @PathVariable Long idChat) {
        Optional<Chats> chatWithUsers = repository.findByIdChatWithUsersAndMessages(idChat);
        Optional<Chats> chatWithMessages = repository.findByIdChatWithMessages(idChat);
        
        if (chatWithUsers.isPresent() && chatWithMessages.isPresent()) {
            Chats chat = chatWithUsers.get();
            chat.setMessages(chatWithMessages.get().getMessages());
            return ResponseEntity.ok(chatResponseMapper.toDto(chat));
        }
        throw new ChatNaoEncontradoException("Chat não encontrado");
    }

    @GetMapping("/{idChat}/mensagens")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Buscar mensagens", description = "Retorna as mensagens de um chat específico com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagens encontradas com sucesso"),
        @ApiResponse(responseCode = "400", description = "Usuário não é participante do chat", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Chat não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<ChatMessage>> getMensagem(
            @Parameter(description = "ID do chat", required = true) 
            @PathVariable Long idChat,
            @Parameter(description = "ID do usuário solicitante", required = true) 
            @RequestParam Long idUsuario,
            @Parameter(description = "Número da página") 
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @Parameter(description = "Tamanho da página") 
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size
    ) {
        // Verificar se o usuário é participante do chat usando uma consulta otimizada
        boolean isParticipant = repository.isUserParticipantInChat(idChat, idUsuario);
        
        if (!isParticipant) {
            return ResponseEntity.badRequest().build();
        }
        
        // Buscar o chat com suas mensagens
        Chats chat = repository.findByIdChatWithMessagesOnly(idChat)
                .orElseThrow(() -> new RuntimeException("Chat não encontrado"));

        List<ChatMessage> messages = chat.getMessages();
        Integer start = Math.max(0, messages.size() - (page + 1) * size);
        Integer end = Math.min(messages.size(), start + size);
        List<ChatMessage> pageableMessages = messages.subList(start, end);
        return ResponseEntity.ok(pageableMessages);
    }

    @GetMapping("/list/{idUsuario}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Listar chats do usuário", description = "Retorna todos os chats de um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de chats obtida com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Nenhum chat encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<ChatGetDTO>> getChats(
            @Parameter(description = "ID do usuário", required = true) 
            @PathVariable Long idUsuario) {
        // Buscar os chats do usuário com informações básicas, sem carregar as mensagens
        List<Chats> chats = repository.findAllByUsuario1IdOrUsuario2IdOrderByLastMessageTime(idUsuario);
        
        if (chats.isEmpty()) {
            throw new ChatNaoEncontradoException("Nenhum chat encontrado");
        }

        List<ChatGetDTO> chatDTOs = new ArrayList<>();
        for (Chats chat : chats) {
            // Verificar se existem mensagens não lidas para este usuário neste chat
            boolean temMensagensNaoLidas = messageService.temMensagensNaoLidas(chat.getIdChat(), idUsuario);

            // Obter a última mensagem do chat
            ChatMessageDTO ultimaMensagem = null;
            
            // Buscar a última mensagem do chat em uma consulta separada
            Optional<ChatMessage> lastMessageOpt = chatMessagesRepository.findFirstByChatOrderByTimeStampDesc(chat);
            if (lastMessageOpt.isPresent()) {
                ultimaMensagem = chatGetMapper.toMessageDto(lastMessageOpt.get());
            }

            ChatGetDTO dto = chatGetMapper.toDto(chat);
            // Como o MapStruct não suporta diretamente a adição de campos, precisamos criar um novo DTO com o campo naoLido
            chatDTOs.add(new ChatGetDTO(
                    dto.id(),
                    dto.idChat(),
                    dto.usuario1(),
                    dto.usuario2(),
                    temMensagensNaoLidas,
                    ultimaMensagem
            ));
        }

        return ResponseEntity.ok(chatDTOs);
    }

    @GetMapping("/{idUsuario1}/{idUsuario2}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Buscar chat entre usuários", description = "Retorna o chat entre dois usuários específicos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chat encontrado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Chat não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<ChatGetDTO> getChat(
            @Parameter(description = "ID do primeiro usuário", required = true) 
            @PathVariable Long idUsuario1, 
            @Parameter(description = "ID do segundo usuário", required = true) 
            @PathVariable Long idUsuario2) {
        Chats chat = repository.findByUsuario1IdAndUsuario2Id(idUsuario1, idUsuario2);
        return ResponseEntity.ok(chatGetMapper.toDto(chat));
    }

    @PostMapping("/{idChat}/marcarLidas")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Marcar mensagens como lidas", description = "Marca todas as mensagens de um chat como lidas para um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagens marcadas como lidas com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Chat não encontrado", content = @Content)
    })
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> marcarMensagensComoLidas(
            @Parameter(description = "ID do chat", required = true) 
            @PathVariable Long idChat,
            @Parameter(description = "ID do usuário", required = true) 
            @RequestParam Long idUsuario) {
        messageService.marcarMensagensComoLidas(idChat, idUsuario);
        return ResponseEntity.ok().build();
    }
}
