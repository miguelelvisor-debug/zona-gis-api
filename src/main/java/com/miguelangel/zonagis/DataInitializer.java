package com.miguelangel.zonagis;

import com.miguelangel.zonagis.model.Zona;
import com.miguelangel.zonagis.repository.ZonaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Carga datos de ejemplo al arrancar la aplicación.
 * Solo en entorno de desarrollo (H2 en memoria).
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final ZonaRepository zonaRepository;

    public DataInitializer(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    @Override
    public void run(String... args) {
        if (zonaRepository.count() > 0) return;

        zonaRepository.save(new Zona("ZONA-A", "Madrid", "Madrid",
                Zona.EstadoZona.COMPLETADA, 48, 1200, "M. Susarte"));

        zonaRepository.save(new Zona("ZONA-B", "Alcalá de Henares", "Madrid",
                Zona.EstadoZona.EN_DESPLIEGUE, 36, 900, "M. Susarte"));

        Zona zonaC = new Zona("ZONA-C", "Getafe", "Madrid",
                Zona.EstadoZona.INCIDENCIA, 24, 600, "M. Susarte");
        zonaC.setNotas("Incidencia en splitter principal, pendiente revisión");
        zonaC.setLatitud(40.3056);
        zonaC.setLongitud(-3.7326);
        zonaRepository.save(zonaC);

        zonaRepository.save(new Zona("ZONA-D", "Leganés", "Madrid",
                Zona.EstadoZona.PLANIFICADA, 48, 1100, "M. Susarte"));

        zonaRepository.save(new Zona("ZONA-E", "Móstoles", "Madrid",
                Zona.EstadoZona.EN_DESPLIEGUE, 36, 850, "M. Susarte"));

        System.out.println("✅ Datos de ejemplo cargados correctamente.");
    }
}
