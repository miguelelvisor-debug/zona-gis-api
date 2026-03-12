package com.miguelangel.zonagis.controller;

import com.miguelangel.zonagis.model.Zona;
import com.miguelangel.zonagis.service.ZonaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de zonas de red FTTH.
 * Base URL: /api/zonas
 */
@RestController
@RequestMapping("/api/zonas")
@CrossOrigin(origins = "*")
public class ZonaController {

    private final ZonaService zonaService;

    public ZonaController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    // GET /api/zonas — Listar todas las zonas
    @GetMapping
    public ResponseEntity<List<Zona>> listarTodas(
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String nombre) {

        if (municipio != null) {
            return ResponseEntity.ok(zonaService.buscarPorMunicipio(municipio));
        }
        if (estado != null) {
            try {
                Zona.EstadoZona estadoEnum = Zona.EstadoZona.valueOf(estado.toUpperCase());
                return ResponseEntity.ok(zonaService.buscarPorEstado(estadoEnum));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        if (nombre != null) {
            return ResponseEntity.ok(zonaService.buscarPorNombre(nombre));
        }
        return ResponseEntity.ok(zonaService.obtenerTodas());
    }

    // GET /api/zonas/{id} — Obtener zona por ID
    @GetMapping("/{id}")
    public ResponseEntity<Zona> obtenerPorId(@PathVariable Long id) {
        return zonaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/zonas — Crear nueva zona
    @PostMapping
    public ResponseEntity<Zona> crear(@Valid @RequestBody Zona zona) {
        Zona creada = zonaService.crear(zona);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // PUT /api/zonas/{id} — Actualizar zona existente
    @PutMapping("/{id}")
    public ResponseEntity<Zona> actualizar(@PathVariable Long id,
                                            @Valid @RequestBody Zona zona) {
        return zonaService.actualizar(id, zona)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /api/zonas/{id}/estado — Cambiar solo el estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Zona> cambiarEstado(@PathVariable Long id,
                                               @RequestBody Map<String, String> body) {
        try {
            Zona.EstadoZona nuevoEstado = Zona.EstadoZona.valueOf(body.get("estado").toUpperCase());
            return zonaService.obtenerPorId(id).map(zona -> {
                zona.setEstado(nuevoEstado);
                return ResponseEntity.ok(zonaService.crear(zona));
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/zonas/{id} — Eliminar zona
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (zonaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/zonas/resumen — Estadísticas globales
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> resumen() {
        return ResponseEntity.ok(zonaService.obtenerResumen());
    }
}
