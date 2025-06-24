package com.dashboard.be.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.be.domain.Ambiente;
import com.dashboard.be.domain.Atributo;
import com.dashboard.be.domain.Cosa;
import com.dashboard.be.domain.PlantillaAtributo;
import com.dashboard.be.domain.mqtt.MqttPublisher;
import com.dashboard.be.dto.ActualizarCosaDTO;
import com.dashboard.be.dto.AtributoDTO;
import com.dashboard.be.dto.CrearCosaDTO;
import com.dashboard.be.dto.NuevaCosaPayload;
import com.dashboard.be.repository.PlantillaCosaRepository;
import com.dashboard.be.service.AmbienteService;
import com.dashboard.be.service.CosaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// @CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/ambientes")
@CrossOrigin(origins = "http://190.0.100.20:8080")
public class AmbienteController {

    @Value("${dashboard.fe.url}")
    protected String fe_url;

    private final AmbienteService ambienteService;
    private final CosaService cosaService;
    private final PlantillaCosaRepository plantillaRepo;

    @Autowired
    private MqttPublisher mqttPublisher;

    @Autowired
    private ObjectMapper objectMapper;

    public AmbienteController(
            AmbienteService ambienteService,
            CosaService cosaService,
            PlantillaCosaRepository plantillaRepo) {
        this.ambienteService = ambienteService;
        this.cosaService = cosaService;
        this.plantillaRepo = plantillaRepo;
    }

    @GetMapping
    public List<Ambiente> getAllAmbientes() {
        return ambienteService.obtenerTodos();
    }

    /*  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Ambiente createAmbiente(@RequestBody Ambiente ambiente) {
        System.out.println("Recibido: " + ambiente.getName());
        return ambienteService.guardar(ambiente);
    }*/
    @PostMapping(consumes = MediaType.ALL_VALUE)
    public Ambiente createAmbiente(@RequestBody Ambiente ambiente) {
        System.out.println("Recibido: " + ambiente.getName());
        return ambienteService.guardar(ambiente);
    }

    @PostMapping("/{ambienteId}/componentes")
    public ResponseEntity<Cosa> addComponente(
            @PathVariable Long ambienteId,
            @RequestBody CrearCosaDTO dto) {

        return ambienteService.obtenerPorId(ambienteId).map(ambiente -> {
            Cosa cosa = new Cosa();
            cosa.setType(dto.getType());
            cosa.setState(dto.getState());
            cosa.setAmbiente(ambiente);

            // Si vienen atributos en el DTO, se usan directamente
            if (dto.getAtributos() != null && !dto.getAtributos().isEmpty()) {
                for (AtributoDTO attr : dto.getAtributos()) {
                    Atributo atributo = new Atributo();
                    atributo.setNombre(attr.getNombre());
                    atributo.setValor(attr.getValor());
                    atributo.setCosa(cosa);
                    cosa.getAtributos().add(atributo);
                }
            } else {
                // Si no vienen atributos, se copian desde la plantilla (si existe)
                plantillaRepo.findByNombre(dto.getType()).ifPresent(plantilla -> {
                    for (PlantillaAtributo pa : plantilla.getAtributos()) {
                        Atributo atributo = new Atributo();
                        atributo.setNombre(pa.getNombre());
                        atributo.setValor(pa.getValorPorDefecto());
                        atributo.setCosa(cosa);
                        cosa.getAtributos().add(atributo);
                    }
                });
            }

            // Guardar en base
            Cosa cosaGuardada = cosaService.guardar(cosa);

            // Construir el payload MQTT
            NuevaCosaPayload.CosaInternaDTO cosaDto = new NuevaCosaPayload.CosaInternaDTO(
                    cosaGuardada.getId(),
                    cosaGuardada.getType(),
                    cosaGuardada.getState()
            );

            NuevaCosaPayload payload = new NuevaCosaPayload(ambienteId, cosaDto);

            try {
                String json = objectMapper.writeValueAsString(payload);
                mqttPublisher.publish(json);
                System.out.println("[BACKEND] Publicada nueva cosa por MQTT: " + json);
            } catch (JsonProcessingException e) {
                System.err.println("Error al serializar el mensaje MQTT:");
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return new ResponseEntity<>(cosaGuardada, HttpStatus.CREATED);
        }).orElseThrow(() -> new RuntimeException("Ambiente no encontrado"));
    }

    @PatchMapping("/{ambienteId}/componentes/{componenteId}/dinamico")
    public ResponseEntity<Cosa> actualizarComponenteDinamico(
            @PathVariable Long ambienteId,
            @PathVariable Long componenteId,
            @RequestBody ActualizarCosaDTO dto) {

        return cosaService.obtenerPorId(componenteId).map(cosa -> {
            if (dto.getState() != null) {
                cosa.setState(dto.getState());
            }

            if (dto.getAtributos() != null) {
                cosa.getAtributos().clear();
                for (AtributoDTO attr : dto.getAtributos()) {
                    Atributo atributo = new Atributo();
                    atributo.setNombre(attr.getNombre());
                    atributo.setValor(attr.getValor());
                    atributo.setCosa(cosa);
                    cosa.getAtributos().add(atributo);
                }
            }

            return ResponseEntity.ok(cosaService.guardar(cosa));
        }).orElseThrow(() -> new RuntimeException("Componente no encontrado"));
    }

    @GetMapping("/{ambienteId}/componentes")
    public ResponseEntity<List<Cosa>> getComponentesPorAmbiente(@PathVariable Long ambienteId) {
        return ambienteService.obtenerPorId(ambienteId)
                .map(ambiente -> ResponseEntity.ok(ambiente.getCosas()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{envId}/componentes/{compId}")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Long envId,
            @PathVariable Long compId,
            @RequestBody Map<String, Boolean> body
    ) {
        boolean nuevoEstado = body.getOrDefault("state", false);
        System.out.println("🛠 PATCH recibido: id=" + compId + " nuevoEstado=" + nuevoEstado);
        cosaService.cambiarEstado(compId, nuevoEstado);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
