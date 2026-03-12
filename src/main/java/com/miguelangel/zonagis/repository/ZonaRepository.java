package com.miguelangel.zonagis.repository;

import com.miguelangel.zonagis.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para operaciones CRUD sobre Zona.
 * Spring Data genera la implementación automáticamente.
 */
@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {

    // Buscar por municipio (ignorando mayúsculas)
    List<Zona> findByMunicipioIgnoreCase(String municipio);

    // Buscar por estado
    List<Zona> findByEstado(Zona.EstadoZona estado);

    // Buscar por provincia
    List<Zona> findByProvinciaIgnoreCase(String provincia);

    // Zonas con incidencias activas
    List<Zona> findByEstadoOrderByNombreAsc(Zona.EstadoZona estado);

    // Total de metros de cable desplegados
    @Query("SELECT SUM(z.metrosCable) FROM Zona z WHERE z.estado = 'COMPLETADA'")
    Long totalMetrosDesplegados();

    // Buscar por nombre parcial
    List<Zona> findByNombreContainingIgnoreCase(String nombre);
}
