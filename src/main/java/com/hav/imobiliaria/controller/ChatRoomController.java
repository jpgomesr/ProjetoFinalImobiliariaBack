package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.exceptions.ChatJaCadastradoException;
import com.hav.imobiliaria.exceptions.ChatNaoEncontradoException;
import com.hav.imobiliaria.exceptions.UsuarioNaoEncontradoException;
import com.hav.imobiliaria.model.entity.ChatMessage;
import com.hav.imobiliaria.model.entity.Chats;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.repository.ChatsRepository;
import com.hav.imobiliaria.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/chat")
@CrossOrigin("http://localhost:3000")
public class ChatRoomController {
    ChatsRepository repository;
    UsuarioRepository usuarioRepository;

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
        Chats chat = repository.findByIdChat(idChat)
                .orElseThrow(() -> new ChatNaoEncontradoException("Chat não encontrado"));

        // Verifica se o usuário é participante do chat
        if (!chat.getUsuario1().getId().equals(idUsuario) &&
                !chat.getUsuario2().getId().equals(idUsuario)) {
            return ResponseEntity.badRequest().body("Usuário não autorizado para este chat");
        }

        return ResponseEntity.ok(chat);
    }

    @GetMapping("/{idChat}/mensagens")
    public ResponseEntity<List<ChatMessage>> getMensagem(
            @PathVariable Long idChat,
            @RequestParam Long idUsuario,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size
    ) {
        Chats chat = repository.findByIdChat(idChat)
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
}
