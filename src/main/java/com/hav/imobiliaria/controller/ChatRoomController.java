package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.chat.ChatGetDTO;
import com.hav.imobiliaria.controller.dto.chat.ChatMessageDTO;
import com.hav.imobiliaria.controller.dto.chat.ChatResponseDTO;
import com.hav.imobiliaria.controller.mapper.chat.ChatGetMapper;
import com.hav.imobiliaria.controller.mapper.chat.ChatResponseMapper;
import com.hav.imobiliaria.controller.mapper.mensagem.MensagemResponseMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    MessageService messageService;

    @PostMapping("/{idUsuario1}/{idUsuario2}")
    public ResponseEntity<?> createChat(
            @PathVariable Long idUsuario1,
            @PathVariable Long idUsuario2) {
        // Verifica se já existe um chat entre esses usuários
        if (repository.existsByUsuario1IdAndUsuario2IdOrUsuario1IdAndUsuario2Id(
                idUsuario1, idUsuario2, idUsuario2, idUsuario1)) {
            throw new ChatJaCadastradoException("Chat já existente entre esses usuários");
        }
        Usuario usuario1 = usuarioRepository.findById(idUsuario1)
                .orElseThrow(UsuarioNaoEncontradoException::new);
        Usuario usuario2 = usuarioRepository.findById(idUsuario2)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        Chats chat = new Chats();
        chat.setUsuario1(usuario1);
        chat.setUsuario2(usuario2);
        chat.setIdChat(System.currentTimeMillis());

        Chats chatSalvo = repository.save(chat);
        return ResponseEntity.ok(chatSalvo);
    }

    @GetMapping("/{idChat}")
    public ResponseEntity<?> joinChat(
            @PathVariable Long idChat,
            @RequestParam Long idUsuario) {
        Chats chat = repository.findByIdChatWithUsersAndMessages(idChat)
                .orElseThrow(() -> new ChatNaoEncontradoException("Chat não encontrado"));

        // Verifica se o usuário é participante do chat
        if (!chat.getUsuario1().getId().equals(idUsuario) &&
                !chat.getUsuario2().getId().equals(idUsuario)) {
            return ResponseEntity.badRequest().body("Usuário não autorizado para este chat");
        }

        return ResponseEntity.ok(chatResponseMapper.toDto(chat));
    }

    @GetMapping("/{idChat}/mensagens")
    public ResponseEntity<List<ChatMessage>> getMensagem(
            @PathVariable Long idChat,
            @RequestParam Long idUsuario,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size
    ) {
        Chats chat = repository.findByIdChatWithUsersAndMessages(idChat)
                .orElseThrow(() -> new RuntimeException("Chat não encontrado"));

        // Verifica se o usuário é participante do chat
        if (!chat.getUsuario1().getId().equals(idUsuario) &&
                !chat.getUsuario2().getId().equals(idUsuario)) {
            return ResponseEntity.badRequest().build();
        }

        List<ChatMessage> messages = chat.getMessages();
        Integer start = Math.max(0, messages.size() - (page + 1) * size);
        Integer end = Math.min(messages.size(), start + size);
        List<ChatMessage> pageableMessages = messages.subList(start, end);
        return ResponseEntity.ok(pageableMessages);
    }

    @GetMapping("/list/{idUsuario}")
    public ResponseEntity<List<ChatGetDTO>> getChats(@PathVariable Long idUsuario) {
        List<Chats> chats = repository.findAllByUsuario1IdOrUsuario2IdOrderByMessagesTimeStamp(idUsuario, idUsuario);
        System.out.println(chats);
        if (chats.isEmpty()) {
            throw new ChatNaoEncontradoException("Nenhum chat encontrado");
        }
        
        List<ChatGetDTO> chatDTOs = new ArrayList<>();
        for (Chats chat : chats) {
            // Verificar se existem mensagens não lidas para este usuário neste chat
            boolean temMensagensNaoLidas = messageService.temMensagensNaoLidas(chat.getIdChat(), idUsuario);
            
            // Obter a última mensagem do chat
            ChatMessageDTO ultimaMensagem = null;
            if (chat.getMessages() != null && !chat.getMessages().isEmpty()) {
                Optional<ChatMessage> lastMessageOpt = messageService.getUltimaMensagem(chat.getMessages());
                if (lastMessageOpt.isPresent()) {
                    ultimaMensagem = chatGetMapper.toMessageDto(lastMessageOpt.get());
                }
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
    
    @PostMapping("/{idChat}/marcarLidas")
    public ResponseEntity<Void> marcarMensagensComoLidas(
            @PathVariable Long idChat,
            @RequestParam Long idUsuario) {
        messageService.marcarMensagensComoLidas(idChat, idUsuario);
        return ResponseEntity.ok().build();
    }
}
