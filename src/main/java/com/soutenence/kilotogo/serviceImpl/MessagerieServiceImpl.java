package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.Messagerie;
import com.soutenence.kilotogo.repository.MessagerieRepository;
import com.soutenence.kilotogo.service.MessagerieService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessagerieServiceImpl implements MessagerieService {
    private final MessagerieRepository messagerieRepository;

    public MessagerieServiceImpl(MessagerieRepository messagerieRepository) {
        this.messagerieRepository = messagerieRepository;
    }

    @Override
    public List<Messagerie> getAllMessageries() {
        return messagerieRepository.findAll();
    }

    @Override
    public Optional<Messagerie> getMessagerieById(Long id) {
        return messagerieRepository.findById(id);
    }

    @Override
    public Messagerie createMessagerie(Messagerie messagerie) {
        return messagerieRepository.save(messagerie);
    }

    @Override
    public Messagerie updateMessagerie(Long id, Messagerie messagerieDetails) {
        Messagerie existingMessagerie = messagerieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Messagerie not found"));
        BeanUtils.copyProperties(messagerieDetails, existingMessagerie, "id", "expediteur", "destinataire", "transaction", "dateEnvoi");
        return messagerieRepository.save(existingMessagerie);
    }

    @Override
    public void deleteMessagerie(Long id) {
        messagerieRepository.deleteById(id);
    }

    @Override
    public Messagerie partialUpdateMessagerie(Long id, Messagerie messagerieUpdates) {
        return messagerieRepository.findById(id).map(existingMessagerie -> {
            if (messagerieUpdates.getSujet() != null) existingMessagerie.setSujet(messagerieUpdates.getSujet());
            if (messagerieUpdates.getLu() != null) existingMessagerie.setLu(messagerieUpdates.getLu());
            return messagerieRepository.save(existingMessagerie);
        }).orElseThrow(() -> new RuntimeException("Messagerie not found"));
    }
}
