package com.miguelangel.zonagis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Entidad que representa una zona de despliegue de red FTTH.
 * Cada zona tiene una ubicación geográfica, un estado y materiales asignados.
 */
@Entity
@Table(name = "zonas")
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la zona es obligatorio")
    @Column(nullable = false, unique = true)
    private String nombre;

    @NotBlank(message = "El municipio es obligatorio")
    private String municipio;

    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoZona estado = EstadoZona.PLANIFICADA;

    @Min(value = 0, message = "Los splitters no pueden ser negativos")
    private int splittersTotales;

    @Min(value = 0, message = "Los splitters instalados no pueden ser negativos")
    private int splittersInstalados;

    @Min(value = 0, message = "Los metros de cable no pueden ser negativos")
    private int metrosCable;

    private String responsableTecnico;

    private String notas;

    // Coordenadas GIS
    private Double latitud;
    private Double longitud;

    public enum EstadoZona {
        PLANIFICADA,
        EN_DESPLIEGUE,
        COMPLETADA,
        INCIDENCIA
    }

    // ── Constructores ──────────────────────────────────────────
    public Zona() {}

    public Zona(String nombre, String municipio, String provincia,
                EstadoZona estado, int splittersTotales, int metrosCable,
                String responsableTecnico) {
        this.nombre = nombre;
        this.municipio = municipio;
        this.provincia = provincia;
        this.estado = estado;
        this.splittersTotales = splittersTotales;
        this.metrosCable = metrosCable;
        this.responsableTecnico = responsableTecnico;
    }

    // ── Getters y Setters ──────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public EstadoZona getEstado() { return estado; }
    public void setEstado(EstadoZona estado) { this.estado = estado; }

    public int getSplittersTotales() { return splittersTotales; }
    public void setSplittersTotales(int splittersTotales) { this.splittersTotales = splittersTotales; }

    public int getSplittersInstalados() { return splittersInstalados; }
    public void setSplittersInstalados(int splittersInstalados) { this.splittersInstalados = splittersInstalados; }

    public int getMetrosCable() { return metrosCable; }
    public void setMetrosCable(int metrosCable) { this.metrosCable = metrosCable; }

    public String getResponsableTecnico() { return responsableTecnico; }
    public void setResponsableTecnico(String responsableTecnico) { this.responsableTecnico = responsableTecnico; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    // Métrica calculada: % de instalación
    @Transient
    public double getPorcentajeInstalacion() {
        if (splittersTotales == 0) return 0;
        return Math.round((splittersInstalados * 100.0 / splittersTotales) * 10.0) / 10.0;
    }
}
