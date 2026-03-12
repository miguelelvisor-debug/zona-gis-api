# 🗺️ Zona GIS API

API REST desarrollada con **Java + Spring Boot** para la gestión de zonas de despliegue de red FTTH.  
Permite crear, consultar, actualizar y eliminar zonas de red, con estadísticas en tiempo real.

> Inspirada en flujos reales de trabajo en proyectos de despliegue de fibra óptica FTTH.

---

## 🚀 Endpoints disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/zonas` | Listar todas las zonas |
| `GET` | `/api/zonas?municipio=Madrid` | Filtrar por municipio |
| `GET` | `/api/zonas?estado=EN_DESPLIEGUE` | Filtrar por estado |
| `GET` | `/api/zonas/{id}` | Obtener zona por ID |
| `POST` | `/api/zonas` | Crear nueva zona |
| `PUT` | `/api/zonas/{id}` | Actualizar zona completa |
| `PATCH` | `/api/zonas/{id}/estado` | Cambiar solo el estado |
| `DELETE` | `/api/zonas/{id}` | Eliminar zona |
| `GET` | `/api/zonas/resumen` | Estadísticas globales |

### Estados posibles
`PLANIFICADA` · `EN_DESPLIEGUE` · `COMPLETADA` · `INCIDENCIA`

---

## 🛠️ Stack técnico

- **Java 21**
- **Spring Boot 3.2** — framework principal
- **Spring Data JPA** — acceso a datos con repositorios
- **H2** — base de datos en memoria (sin configuración extra)
- **Maven** — gestión de dependencias
- Arquitectura en capas: `Controller → Service → Repository → Model`

---

## ▶️ Cómo ejecutar

### Requisitos
- Java 21+
- Maven 3.8+

### Pasos
```bash
git clone https://github.com/miguelelvisor-debug/zona-gis-api.git
cd zona-gis-api
mvn spring-boot:run
```

La API arranca en `http://localhost:8080`

### Consola H2 (ver base de datos)
Accede a `http://localhost:8080/h2-console`  
- JDBC URL: `jdbc:h2:mem:zonagisdb`
- Usuario: `sa` · Contraseña: *(vacía)*

---

## 📋 Ejemplos de uso

### Crear una zona
```bash
curl -X POST http://localhost:8080/api/zonas \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "ZONA-F",
    "municipio": "Fuenlabrada",
    "provincia": "Madrid",
    "estado": "PLANIFICADA",
    "splittersTotales": 48,
    "metrosCable": 1100,
    "responsableTecnico": "M. Susarte"
  }'
```

### Ver resumen global
```bash
curl http://localhost:8080/api/zonas/resumen
```

### Cambiar estado
```bash
curl -X PATCH http://localhost:8080/api/zonas/1/estado \
  -H "Content-Type: application/json" \
  -d '{"estado": "EN_DESPLIEGUE"}'
```

---

## 📁 Estructura del proyecto

```
zona-gis-api/
├── pom.xml
└── src/main/java/com/miguelangel/zonagis/
    ├── ZonaGisApiApplication.java   # Arranque
    ├── DataInitializer.java          # Datos de ejemplo
    ├── model/
    │   └── Zona.java                 # Entidad JPA
    ├── repository/
    │   └── ZonaRepository.java       # Acceso a datos
    ├── service/
    │   └── ZonaService.java          # Lógica de negocio
    └── controller/
        └── ZonaController.java       # Endpoints REST
```

---

## 👤 Autor

**Miguel Angel Susarte**  
Técnico de Telecomunicaciones → Developer  
[LinkedIn](https://linkedin.com/in/miguelangel) · [GitHub](https://github.com/miguelelvisor-debug)
