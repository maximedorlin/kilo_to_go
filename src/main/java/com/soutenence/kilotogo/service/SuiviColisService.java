package com.soutenence.kilotogo.service;

import com.soutenence.kilotogo.entity.SuiviColis;
import java.util.List;
import java.util.Optional;

public interface SuiviColisService {
    List<SuiviColis> getAllSuiviColis();
    Optional<SuiviColis> getSuiviColisById(Long id);
    SuiviColis createSuiviColis(SuiviColis suiviColis);
    SuiviColis updateSuiviColis(Long id, SuiviColis suiviColisDetails);
    void deleteSuiviColis(Long id);
    SuiviColis partialUpdateSuiviColis(Long id, SuiviColis suiviColisUpdates);
}
