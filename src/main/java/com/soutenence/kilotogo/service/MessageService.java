package com.soutenence.kilotogo.service;

import com.soutenence.kilotogo.entity.Message;
import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Message> getAllMessages();
    Optional<Message> getMessageById(Long id);
    Message createMessage(Message message);
    Message updateMessage(Long id, Message messageDetails);
    void deleteMessage(Long id);
    Message partialUpdateMessage(Long id, Message messageUpdates);
}
