package com.soutenence.kilotogo.service;

import com.soutenence.kilotogo.entity.Message;
import com.soutenence.kilotogo.entity.Messagerie;
import java.util.List;
import java.util.Optional;

public interface MessagerieService {
    List<Messagerie> getAllMessageries();
    Optional<Messagerie> getMessagerieById(Long id);
    Messagerie createMessagerie(Messagerie messagerie);
    Messagerie updateMessagerie(Long id, Messagerie messagerieDetails);
    void deleteMessagerie(Long id);
    Messagerie partialUpdateMessagerie(Long id, Messagerie messagerieUpdates);

    Message createMessageForConversation(Long messagerieId, Message message);
    List<Message> getMessagesForConversation(Long messagerieId);
}