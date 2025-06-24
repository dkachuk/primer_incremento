package com.dashboard.be.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.be.domain.PlantillaCosa;
import com.dashboard.be.repository.PlantillaCosaRepository;

import jakarta.servlet.http.HttpServletRequest;

// @CrossOrigin(origins = "http://localhost:3000") // Ajustá esto si tu front corre en otro puerto
@RestController
@RequestMapping("/api/plantillas")
@CrossOrigin(origins = "http://190.0.100.20:8080") // Ajustá esto si tu front corre en otro puerto
public class PlantillaCosaController {

    @Value("${dashboard.fe.url}")
    protected String fe_url;

    private final PlantillaCosaRepository plantillaRepo;

    public PlantillaCosaController(PlantillaCosaRepository plantillaRepo) {
        this.plantillaRepo = plantillaRepo;
    }

    @RequestMapping("/debug-origin")
    public ResponseEntity<String> debugOrigin(HttpServletRequest request) {
        return ResponseEntity.ok("Origin: " + request.getHeader("Origin"));
    }

    @GetMapping
    public List<PlantillaCosa> listarPlantillas() {
        return plantillaRepo.findAll();
    }

    @PostMapping
    public PlantillaCosa crearPlantilla(@RequestBody PlantillaCosa plantilla) {
        if (plantilla.getAtributos() != null) {
            plantilla.getAtributos().forEach(a -> a.setPlantilla(plantilla));
        }
        return plantillaRepo.save(plantilla);
    }
}
