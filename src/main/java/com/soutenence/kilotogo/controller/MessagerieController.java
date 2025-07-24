package com.soutenence.kilotogo.controller;

import com.soutenence.kilotogo.entity.Message;
import com.soutenence.kilotogo.entity.Messagerie;
import com.soutenence.kilotogo.service.MessagerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messageries")
public class MessagerieController {
    private final MessagerieService messagerieService;

    public MessagerieController(MessagerieService messagerieService) {
        this.messagerieService = messagerieService;
    }

    @GetMapping
    public List<Messagerie> getAllMessageries() {
        return messagerieService.getAllMessageries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Messagerie> getMessagerieById(@PathVariable Long id) {
        Optional<Messagerie> messagerie = messagerieService.getMessagerieById(id);
        return messagerie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Messagerie createMessagerie(@RequestBody Messagerie messagerie) {
        return messagerieService.createMessagerie(messagerie);
    }

    @PutMapping("/{id}")
    public Messagerie updateMessagerie(@PathVariable Long id, @RequestBody Messagerie messagerieDetails) {
        return messagerieService.updateMessagerie(id, messagerieDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteMessagerie(@PathVariable Long id) {
        messagerieService.deleteMessagerie(id);
    }

    @PatchMapping("/{id}")
    public Messagerie partialUpdateMessagerie(@PathVariable Long id, @RequestBody Messagerie messagerieUpdates) {
        return messagerieService.partialUpdateMessagerie(id, messagerieUpdates);
    }

    @PostMapping("/{messagerieId}/messages")
    public Message createMessageForConversation(
            @PathVariable Long messagerieId,
            @RequestBody Message message) {
        return messagerieService.createMessageForConversation(messagerieId, message);
    }

    @GetMapping("/{messagerieId}/messages")
    public List<Message> getMessagesForConversation(@PathVariable Long messagerieId) {
        return messagerieService.getMessagesForConversation(messagerieId);
    }
}
