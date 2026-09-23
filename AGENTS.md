# AGENTS.md

Reglas operativas específicas de GesFin. Las reglas globales de arquitectura viven en `spec/AGENTS.md`.

## Idioma
- Todo en **español**: respuestas, código, comentarios, commits, documentación y Swagger. Métodos y variables también en español (ej. `guardar()`, `buscarPorId()`, `montoObjetivo`).

## Arquitectura (Hexagonal + CQRS)
- `domain/` es Java puro: **no importar** Spring/Hibernate/JPA ahí. La comunicación exterior pasa solo por interfaces en `domain/ports/`.
- CQRS aplicado; seguir el patrón existente y no crear capas alternativas:
  - Escritura: `domain/ports/in/commands/*Command.java` → `application/commands/*CommandService` → `adapters/in/rest/controller/command/*CommandController` (`@Tag("... - Comandos")`).
  - Lectura: `domain/ports/in/queries/*Query.java` → `application/queries/*QueryService` → `adapters/in/rest/controller/query/*QueryController` (`@Tag("... - Consultas")`).
  - Los controllers exponen DTOs `dto/read/*ReadDto` en las respuestas, no entidades.
- **No** volver a poner `cascade = ALL` en `FamilyGroupJpaEntity.miembros` (`@OneToMany(mappedBy = "familyGroup")`): causa `detached entity passed to persist: ...UserJpaEntity`. El enlace de miembros se hace seteando `User.familyGroupId` y re-guardando (ver `FamilyGroupCommandService.crear()` y `UserEntityMapper.toEntity`).

## Build / test
- Compilar: `mvn compile`. Tests: `mvn test` (todas las suites; se espera BUILD SUCCESS).
- Integración de persistencia con `@DataJpaTest` + `@Import(adaptador)`; usa el H2 embebido automático (sin contexto Spring completo). Los tests de dominio no deben levantar el contexto de Spring.

## Lombok
- `lombok.version` fijado a **1.18.48** en `pom.xml` por compatibilidad con el JDK 26 local (bytecode target: Java 17). No bajarlo.

## Entorno
- PostgreSQL 16 en Docker, puerto **5433** (el 5432 está ocupado). Arranque: `docker compose up -d` (contenedor `gesfin-db`).
- Credenciales y datasource en `.env` (gitignored, no commitear).
- App en `http://localhost:8080`; Swagger UI en `/swagger-ui.html`, especificación en `/v3/api-docs`.

## Gotchas operativos
- En Windows, `curl` con JSON con acentos/"ñ" puede corromperse (encoding UTF-8). En pruebas manuales usar datos ASCII o `curl --data-binary @archivo.json`.