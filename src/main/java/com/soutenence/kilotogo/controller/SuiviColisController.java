package com.soutenence.kilotogo.controller;

import com.soutenence.kilotogo.entity.SuiviColis;
import com.soutenence.kilotogo.service.SuiviColisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suiviColis")
public class SuiviColisController {
    private final SuiviColisService suiviColisService;

    public SuiviColisController(SuiviColisService suiviColisService) {
        this.suiviColisService = suiviColisService;
    }

    @GetMapping
    public List<SuiviColis> getAllSuiviColis() {
        return suiviColisService.getAllSuiviColis();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuiviColis> getSuiviColisById(@PathVariable Long id) {
        Optional<SuiviColis> suiviColis = suiviColisService.getSuiviColisById(id);
        return suiviColis.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SuiviColis createSuiviColis(@RequestBody SuiviColis suiviColis) {
        return suiviColisService.createSuiviColis(suiviColis);
    }

    @PutMapping("/{id}")
    public SuiviColis updateSuiviColis(@PathVariable Long id, @RequestBody SuiviColis suiviColisDetails) {
        return suiviColisService.updateSuiviColis(id, suiviColisDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteSuiviColis(@PathVariable Long id) {
        suiviColisService.deleteSuiviColis(id);
    }

    @PatchMapping("/{id}")
    public SuiviColis partialUpdateSuiviColis(@PathVariable Long id, @RequestBody SuiviColis suiviColisUpdates) {
        return suiviColisService.partialUpdateSuiviColis(id, suiviColisUpdates);
    }

}
