package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.SuiviColis;
import com.soutenence.kilotogo.repository.SuiviColisRepository;
import com.soutenence.kilotogo.service.SuiviColisService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuiviColisServiceImpl implements SuiviColisService {
    private final SuiviColisRepository suiviColisRepository;

    public SuiviColisServiceImpl(SuiviColisRepository suiviColisRepository) {
        this.suiviColisRepository = suiviColisRepository;
    }

    @Override
    public List<SuiviColis> getAllSuiviColis() {
        return suiviColisRepository.findAll();
    }

    @Override
    public Optional<SuiviColis> getSuiviColisById(Long id) {
        return suiviColisRepository.findById(id);
    }

    @Override
    public SuiviColis createSuiviColis(SuiviColis suiviColis) {
        return suiviColisRepository.save(suiviColis);
    }

    @Override
    public SuiviColis updateSuiviColis(Long id, SuiviColis suiviColisDetails) {
        SuiviColis existingSuiviColis = suiviColisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SuiviColis not found"));
        BeanUtils.copyProperties(suiviColisDetails, existingSuiviColis, "id", "transaction");
        return suiviColisRepository.save(existingSuiviColis);
    }

    @Override
    public void deleteSuiviColis(Long id) {
        suiviColisRepository.deleteById(id);
    }

    @Override
    public SuiviColis partialUpdateSuiviColis(Long id, SuiviColis suiviColisUpdates) {
        return suiviColisRepository.findById(id).map(existingSuiviColis -> {
            if (suiviColisUpdates.getStatut() != null) existingSuiviColis.setStatut(suiviColisUpdates.getStatut());
            if (suiviColisUpdates.getLocalisation() != null) existingSuiviColis.setLocalisation(suiviColisUpdates.getLocalisation());
            if (suiviColisUpdates.getCommentaire() != null) existingSuiviColis.setCommentaire(suiviColisUpdates.getCommentaire());
            return suiviColisRepository.save(existingSuiviColis);
        }).orElseThrow(() -> new RuntimeException("SuiviColis not found"));
    }
}
