package com.miguelangel.zonagis.service;

import com.miguelangel.zonagis.model.Zona;
import com.miguelangel.zonagis.repository.ZonaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servicio con la lógica de negocio para la gestión de zonas FTTH.
 */
@Service
public class ZonaService {

    private final ZonaRepository zonaRepository;

    public ZonaService(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    // ── CRUD básico ────────────────────────────────────────────

    public List<Zona> obtenerTodas() {
        return zonaRepository.findAll();
    }

    public Optional<Zona> obtenerPorId(Long id) {
        return zonaRepository.findById(id);
    }

    public Zona crear(Zona zona) {
        return zonaRepository.save(zona);
    }

    public Optional<Zona> actualizar(Long id, Zona datosNuevos) {
        return zonaRepository.findById(id).map(zona -> {
            zona.setNombre(datosNuevos.getNombre());
            zona.setMunicipio(datosNuevos.getMunicipio());
            zona.setProvincia(datosNuevos.getProvincia());
            zona.setEstado(datosNuevos.getEstado());
            zona.setSplittersTotales(datosNuevos.getSplittersTotales());
            zona.setSplittersInstalados(datosNuevos.getSplittersInstalados());
            zona.setMetrosCable(datosNuevos.getMetrosCable());
            zona.setResponsableTecnico(datosNuevos.getResponsableTecnico());
            zona.setNotas(datosNuevos.getNotas());
            zona.setLatitud(datosNuevos.getLatitud());
            zona.setLongitud(datosNuevos.getLongitud());
            return zonaRepository.save(zona);
        });
    }

    public boolean eliminar(Long id) {
        if (zonaRepository.existsById(id)) {
            zonaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ── Búsquedas ──────────────────────────────────────────────

    public List<Zona> buscarPorMunicipio(String municipio) {
        return zonaRepository.findByMunicipioIgnoreCase(municipio);
    }

    public List<Zona> buscarPorEstado(Zona.EstadoZona estado) {
        return zonaRepository.findByEstado(estado);
    }

    public List<Zona> buscarPorNombre(String nombre) {
        return zonaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // ── Estadísticas del panel ─────────────────────────────────

    public Map<String, Object> obtenerResumen() {
        List<Zona> todas = zonaRepository.findAll();
        long total = todas.size();

        Map<String, Long> porEstado = new HashMap<>();
        for (Zona.EstadoZona estado : Zona.EstadoZona.values()) {
            porEstado.put(estado.name(),
                todas.stream().filter(z -> z.getEstado() == estado).count());
        }

        int totalSplitters = todas.stream().mapToInt(Zona::getSplittersTotales).sum();
        int instalados = todas.stream().mapToInt(Zona::getSplittersInstalados).sum();
        double pctInstalacion = totalSplitters > 0
            ? Math.round((instalados * 100.0 / totalSplitters) * 10.0) / 10.0
            : 0;

        Long metrosDesplegados = zonaRepository.totalMetrosDesplegados();

        Map<String, Object> resumen = new HashMap<>();
        resumen.put("totalZonas", total);
        resumen.put("porEstado", porEstado);
        resumen.put("totalSplitters", totalSplitters);
        resumen.put("splittersInstalados", instalados);
        resumen.put("porcentajeInstalacionGlobal", pctInstalacion);
        resumen.put("metrosCableDesplegados", metrosDesplegados != null ? metrosDesplegados : 0);
        return resumen;
    }
}
