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
    public ResponseEntity<?> createChat(
            @PathVariable Long idUsuario1,
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
        chat.setUsuarios(List.of(usuario1, usuario2));

        Chats chatSalvo = repository.save(chat);
        return ResponseEntity.ok(chatPostMapper.toDto(chatSalvo));
    }

    @GetMapping("/join/{idChat}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> joinChat(@PathVariable Long idChat) {
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
    public ResponseEntity<List<ChatMessage>> getMensagem(
            @PathVariable Long idChat,
            @RequestParam Long idUsuario,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
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
    public ResponseEntity<List<ChatGetDTO>> getChats(@PathVariable Long idUsuario) {
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
    public ResponseEntity<ChatGetDTO> getChat
            (@PathVariable Long idUsuario1, @PathVariable Long idUsuario2) {
        Chats chat = repository.findByUsuario1IdAndUsuario2Id(idUsuario1, idUsuario2);
        return ResponseEntity.ok(chatGetMapper.toDto(chat));
    }

    @PostMapping("/{idChat}/marcarLidas")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> marcarMensagensComoLidas(
            @PathVariable Long idChat,
            @RequestParam Long idUsuario) {
        messageService.marcarMensagensComoLidas(idChat, idUsuario);
        return ResponseEntity.ok().build();
    }
}
