package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.Message;
import com.soutenence.kilotogo.repository.MessageRepository;
import com.soutenence.kilotogo.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message updateMessage(Long id, Message messageDetails) {
        Message existingMessage = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        BeanUtils.copyProperties(messageDetails, existingMessage, "id", "conversation", "utilisateur", "dateEnvoi");
        return messageRepository.save(existingMessage);
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Message partialUpdateMessage(Long id, Message messageUpdates) {
        return messageRepository.findById(id).map(existingMessage -> {
            if (messageUpdates.getContenu() != null) existingMessage.setContenu(messageUpdates.getContenu());
            if (messageUpdates.getLu() != null) existingMessage.setLu(messageUpdates.getLu());
            return messageRepository.save(existingMessage);
        }).orElseThrow(() -> new RuntimeException("Message not found"));
    }
}