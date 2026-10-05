# Tablero Kanban - GesFin

**Proyecto:** Gestión Financiera Familiar  
**Arquitectura:** Hexagonal + DDD + CQRS  
**Fecha de revisión:** 05/10/2026  
**Estado general:** ~95% implementado (Core completo + REST + Persistencia + Tests)

---

## 1. BACKLOG (Por hacer / Mejoras futuras)

| ID | Tarea | Prioridad | Estado | Notas |
|---|---|---|---|---|
| B01 | Implementar `IncomeBasedStrategy` (prorrateo por ingresos) | Baja | Pendiente | Interfaz `CalculateQuotasStrategy` ya preparada. Falta implementación concreta para asignación proporcional según ingresos de cada miembro. |
| B02 | Ampliar validaciones de negocio | Media | Pendiente | Existen validaciones básicas. Se pueden añadir reglas específicas (fechas límite coherentes, montos máximos, etc.) en capa de dominio. |
| B03 | Implementar manejo de excepciones globales (@ControllerAdvice) | Media | Pendiente | Actualmente se lanzan `IllegalArgumentException`. Conviene crear excepciones de dominio personalizadas y mapearlas a códigos HTTP apropiados. |
| B04 | Añadir paginación a endpoints de listado | Baja | Pendiente | Endpoints GET que devuelven listados (`/api/metas/grupo/{id}`, aportes, transacciones) devuelven `List<>` completo. Implementar paginación con `Pageable`. |
| B05 | Implementar Seguridad (Autenticación/Autorización) | Alta | Pendiente | No implementado. Pendiente integrar Spring Security + JWT para proteger endpoints REST. |
| B06 | Crear adaptadores para Supabase | Baja | Pendiente | Arquitectura totalmente agnóstica con puertos out. Solo resta implementar nuevos adaptadores driven para Supabase cuando se requiera. |

---

## 2. EN PROGRESO (En desarrollo)

| ID | Tarea | Responsable | Estado | Notas |
|---|---|---|---|---|
| - | *No hay tareas en progreso* | - | - | - |

---

## 3. HECHO - Núcleo de Dominio (Domain Core)

| ID | Funcionalidad | Categoría | Archivos | Notas |
|---|---|---|---|---|
| D01 | **Modelo User** | Entidad de Dominio | `domain/model/User.java` | Entidad pura sin dependencias de frameworks. Atributos: id, nombre, email, password, rol, familyGroupId. |
| D02 | **Modelo FamilyGroup** | Entidad de Dominio | `domain/model/FamilyGroup.java` | Agrupación familiar con lista de miembros. Método `getCantidadMiembros()`. |
| D03 | **Modelo Goal (Meta)** | Entidad de Dominio | `domain/model/Goal.java` | Lógica de negocio: `calcularProgresoTotal()` (con redondeo HALF_UP a 2 decimales) y `estaCumplida()` (>= 100%). |
| D04 | **Modelo MemberQuota (Cuota)** | Entidad de Dominio | `domain/model/MemberQuota.java` | Cuota por miembro: monto asignado, aportado, restante (`getMontoRestante()`) y cumplimiento (`estaCumplida()`). |
| D05 | **Modelo Contribution (Aporte)** | Entidad de Dominio | `domain/model/Contribution.java` | Registro de aportes individuales a metas. |
| D06 | **Modelo Transaction (Transacción)** | Entidad de Dominio | `domain/model/Transaction.java` | Registro de ingresos/gastos familiares. |
| D07 | **Enums del Dominio** | Enumeraciones | `domain/enums/Rol.java`, `EstadoMeta.java`, `TipoTransaccion.java` | Rol (ADMIN, MIEMBRO), EstadoMeta (ACTIVA, CUMPLIDA, CANCELADA), TipoTransaccion (INGRESO, GASTO). |
| D08 | **CalculateQuotasStrategy (Interfaz)** | Estrategia/Port | `domain/ports/strategy/CalculateQuotasStrategy.java` | Interfaz para estrategia de prorrateo. Prepara extensibilidad para estrategias futuras. |
| D09 | **EqualDistributionStrategy** | Estrategia | `domain/ports/strategy/EqualDistributionStrategy.java` | Implementación por defecto: distribución equitativa. Manejo de redondeo y resto (último miembro recibe ajuste). |
| D10 | **Test Unitario - EqualDistributionStrategy** | Testing (Dominio) | `domain/ports/strategy/EqualDistributionStrategyTest.java` | Valida cálculo equitativo, redondeo con decimales, distribución con resto y caso con 0 miembros. |

---

## 4. HECHO - Puertos del Dominio (Hexagonal Ports)

### 4.1 Puertos de Salida (Driven Ports) - Persistencia
| ID | Funcionalidad | Archivos | Métodos |
|---|---|---|---|
| P01 | `UserRepositoryPort` | `domain/ports/out/UserRepositoryPort.java` | `guardar()`, `buscarPorId()`, `buscarPorEmail()`, `buscarPorFamilyGroupId()`, `eliminar()` |
| P02 | `FamilyGroupRepositoryPort` | `domain/ports/out/FamilyGroupRepositoryPort.java` | `guardar()`, `buscarPorId()`, `eliminar()` |
| P03 | `GoalRepositoryPort` | `domain/ports/out/GoalRepositoryPort.java` | `guardar()`, `buscarPorId()`, `buscarPorFamilyGroupId()`, `eliminar()` |
| P04 | `ContributionRepositoryPort` | `domain/ports/out/ContributionRepositoryPort.java` | `guardar()`, `buscarPorId()`, `buscarPorGoalId()`, `eliminar()` |
| P05 | `TransactionRepositoryPort` | `domain/ports/out/TransactionRepositoryPort.java` | `guardar()`, `buscarPorId()`, `buscarPorFamilyGroupId()`, `eliminar()` |

### 4.2 Puertos de Entrada - Comandos (CQRS Escritura)
| ID | Funcionalidad | Archivos |
|---|---|---|
| PC01 | `CreateUserCommand` | `domain/ports/in/commands/CreateUserCommand.java` |
| PC02 | `CreateFamilyGroupCommand` | `domain/ports/in/commands/CreateFamilyGroupCommand.java` |
| PC03 | `CreateGoalCommand` | `domain/ports/in/commands/CreateGoalCommand.java` |
| PC04 | `CalculateQuotasCommand` | `domain/ports/in/commands/CalculateQuotasCommand.java` |
| PC05 | `RecordContributionCommand` | `domain/ports/in/commands/RecordContributionCommand.java` |
| PC06 | `RegisterTransactionCommand` | `domain/ports/in/commands/RegisterTransactionCommand.java` |

### 4.3 Puertos de Entrada - Consultas (CQRS Lectura)
| ID | Funcionalidad | Archivos |
|---|---|---|
| PQ01 | `GetUserByIdQuery` | `domain/ports/in/queries/GetUserByIdQuery.java` |
| PQ02 | `GetFamilyGroupByIdQuery` | `domain/ports/in/queries/GetFamilyGroupByIdQuery.java` |
| PQ03 | `GetGoalByIdQuery` | `domain/ports/in/queries/GetGoalByIdQuery.java` |
| PQ04 | `GetGoalsByFamilyGroupQuery` | `domain/ports/in/queries/GetGoalsByFamilyGroupQuery.java` |
| PQ05 | `GetContributionsByGoalQuery` | `domain/ports/in/queries/GetContributionsByGoalQuery.java` |
| PQ06 | `GetTransactionsByFamilyGroupQuery` | `domain/ports/in/queries/GetTransactionsByFamilyGroupQuery.java` |

---

## 5. HECHO - Capa de Aplicación (Application Layer)

### 5.1 Servicios de Comandos (Escritura)
| ID | Funcionalidad | Archivos | Descripción |
|---|---|---|---|
| AC01 | Crear Usuario | `application/commands/UserCommandService.java` | Valida email único. Crea usuario con rol especificado. `@Transactional`. |
| AC02 | Crear Grupo Familiar | `application/commands/FamilyGroupCommandService.java` | Crea grupo y actualiza `familyGroupId` de miembros (seteo explícito, sin cascade ALL). `@Transactional`. |
| AC03 | Crear Meta | `application/commands/GoalCommandService.java` | Obtiene miembros del grupo, calcula cuotas con estrategia, crea meta con cuotas. Implementa `CreateGoalCommand`. |
| AC04 | Calcular/Reasignar Cuotas | `application/commands/GoalCommandService.java` | Recalcula cuotas según integrantes actuales del grupo. Implementa `CalculateQuotasCommand`. |
| AC05 | Registrar Aporte | `application/commands/ContributionCommandService.java` | Valida monto>0, actualiza cuota del usuario, marca meta como CUMPLIDA si alcanza 100%, persiste aporte. `@Transactional`. |
| AC06 | Registrar Transacción | `application/commands/TransactionCommandService.java` | Valida monto>0 y tipo obligatorio. Asigna fecha actual si es nula. `@Transactional`. |

### 5.2 Servicios de Consultas (Lectura)
| ID | Funcionalidad | Archivos | Descripción |
|---|---|---|---|
| AQ01 | Consultar Usuario por ID | `application/queries/UserQueryService.java` | Lanza excepción si no existe. `@Transactional(readOnly=true)`. |
| AQ02 | Consultar Grupo Familiar por ID | `application/queries/FamilyGroupQueryService.java` | Consulta con read-only. |
| AQ03 | Consultar Meta por ID | `application/queries/GoalQueryService.java` | Implementa `GetGoalByIdQuery`. |
| AQ04 | Listar Metas por Grupo Familiar | `application/queries/GoalQueryService.java` | Implementa `GetGoalsByFamilyGroupQuery`. |
| AQ05 | Listar Aportes por Meta | `application/queries/ContributionQueryService.java` | Implementa `GetContributionsByGoalQuery`. |
| AQ06 | Listar Transacciones por Grupo | `application/queries/TransactionQueryService.java` | Implementa `GetTransactionsByFamilyGroupQuery`. |

---

## 6. HECHO - Adaptadores de Persistencia (Driven Adapters)

### 6.1 Entidades JPA
| ID | Funcionalidad | Archivos | Observaciones |
|---|---|---|---|
| JP01 | `UserJpaEntity` | `adapters/out/persistence/entity/UserJpaEntity.java` | Mapeo a tabla `usuarios`. |
| JP02 | `FamilyGroupJpaEntity` | `adapters/out/persistence/entity/FamilyGroupJpaEntity.java` | `@OneToMany(mappedBy="familyGroup")` **sin `cascade = ALL`** (cumple regla AGENTS.md). |
| JP03 | `GoalJpaEntity` | `adapters/out/persistence/entity/GoalJpaEntity.java` | Entidad de meta con cuotas embebidas. |
| JP04 | `MemberQuotaJpaEntity` | `adapters/out/persistence/entity/MemberQuotaJpaEntity.java` | Entidad para cuotas de meta. |
| JP05 | `ContributionJpaEntity` | `adapters/out/persistence/entity/ContributionJpaEntity.java` | Entidad para aportes. |
| JP06 | `TransactionJpaEntity` | `adapters/out/persistence/entity/TransactionJpaEntity.java` | Entidad para transacciones. |

### 6.2 Repositorios Spring Data JPA
| ID | Funcionalidad | Archivos | Métodos personalizados |
|---|---|---|---|
| JR01 | `UserJpaRepository` | `adapters/out/persistence/repository/UserJpaRepository.java` | `findByEmail()`, `findByFamilyGroupId()` |
| JR02 | `FamilyGroupJpaRepository` | `.../FamilyGroupJpaRepository.java` | CRUD estándar |
| JR03 | `GoalJpaRepository` | `.../GoalJpaRepository.java` | `findByFamilyGroupId()` |
| JR04 | `ContributionJpaRepository` | `.../ContributionJpaRepository.java` | `findByGoalId()` |
| JR05 | `TransactionJpaRepository` | `.../TransactionJpaRepository.java` | `findByFamilyGroupId()` |

### 6.3 Adaptadores (Implementación de Puertos Out)
| ID | Funcionalidad | Archivos | Descripción |
|---|---|---|---|
| JA01 | `UserRepositoryAdapter` | `adapters/out/persistence/adapter/UserRepositoryAdapter.java` | Implementa `UserRepositoryPort`. Usa mapper + JPA repo. `@Component`. |
| JA02 | `FamilyGroupRepositoryAdapter` | `.../FamilyGroupRepositoryAdapter.java` | Implementa `FamilyGroupRepositoryPort`. |
| JA03 | `GoalRepositoryAdapter` | `.../GoalRepositoryAdapter.java` | Implementa `GoalRepositoryPort`. |
| JA04 | `ContributionRepositoryAdapter` | `.../ContributionRepositoryAdapter.java` | Implementa `ContributionRepositoryPort`. |
| JA05 | `TransactionRepositoryAdapter` | `.../TransactionRepositoryAdapter.java` | Implementa `TransactionRepositoryPort`. |

### 6.4 Mappers (MapStruct)
| ID | Funcionalidad | Archivos | Uso |
|---|---|---|---|
| JM01 | `UserEntityMapper` | `adapters/out/persistence/mapper/UserEntityMapper.java` | Conversión User ↔ UserJpaEntity (MapStruct). |
| JM02 | `FamilyGroupEntityMapper` | `.../FamilyGroupEntityMapper.java` | Conversión FamilyGroup ↔ FamilyGroupJpaEntity. |
| JM03 | `GoalEntityMapper` | `.../GoalEntityMapper.java` | Conversión Goal ↔ GoalJpaEntity. Mapea nested `MemberQuota`. |
| JM04 | `ContributionEntityMapper` | `.../ContributionEntityMapper.java` | Conversión Contribution ↔ ContributionJpaEntity. |
| JM05 | `TransactionEntityMapper` | `.../TransactionEntityMapper.java` | Conversión Transaction ↔ TransactionJpaEntity. |

---

## 7. HECHO - Adaptadores REST (Driving Adapters) - API HTTP

### 7.1 DTOs de Entrada (Request)
| ID | Funcionalidad | Archivos | Validaciones |
|---|---|---|---|
| RD01 | `CreateUserRequest` | `adapters/in/rest/dto/CreateUserRequest.java` | DTO para creación de usuario (record). |
| RD02 | `CreateFamilyGroupRequest` | `.../CreateFamilyGroupRequest.java` | DTO para creación de grupo familiar. |
| RD03 | `CreateGoalRequest` | `.../CreateGoalRequest.java` | DTO para creación de meta. |
| RD04 | `CreateContributionRequest` | `.../CreateContributionRequest.java` | DTO para registro de aporte. |
| RD05 | `CreateTransactionRequest` | `.../CreateTransactionRequest.java` | DTO para registro de transacción. |

### 7.2 DTOs de Lectura (Response)
| ID | Funcionalidad | Archivos | Notas |
|---|---|---|---|
| RR01 | `UserReadDto` | `adapters/in/rest/dto/read/UserReadDto.java` | DTO de respuesta para usuarios (sin password). |
| RR02 | `FamilyGroupReadDto` | `.../FamilyGroupReadDto.java` | DTO con miembros incluidos. |
| RR03 | `GoalReadDto` | `.../GoalReadDto.java` | Incluye `progreso` calculado y lista de `cuotas` (MemberQuotaReadDto). |
| RR04 | `ContributionReadDto` | `.../ContributionReadDto.java` | DTO de respuesta para aportes. |
| RR05 | `TransactionReadDto` | `.../TransactionReadDto.java` | DTO de respuesta para transacciones. |
| RR06 | `MemberQuotaReadDto` | `.../MemberQuotaReadDto.java` (implícito en GoalReadDto) | Incluye monto asignado, aportado, restante y calculados. |

### 7.3 Controllers - Comandos (CQRS Escritura)
| ID | Funcionalidad | Archivos | Endpoints | Swagger/Tags |
|---|---|---|---|---|
| CC01 | `UserCommandController` | `adapters/in/rest/controller/command/UserCommandController.java` | `POST /api/usuarios` | `@Tag("Usuarios - Comandos")` |
| CC02 | `FamilyGroupCommandController` | `.../FamilyGroupCommandController.java` | `POST /api/grupos-familiares` | `@Tag("Grupos Familiares - Comandos")` |
| CC03 | `GoalCommandController` | `.../GoalCommandController.java` | `POST /api/metas`, `POST /api/metas/{id}/calcular-cuotas` | `@Tag("Metas - Comandos")`. Incluye documentación OpenAPI detallada con ejemplos. |
| CC04 | `ContributionCommandController` | `.../ContributionCommandController.java` | `POST /api/aportes` | `@Tag("Aportes - Comandos")` |
| CC05 | `TransactionCommandController` | `.../TransactionCommandController.java` | `POST /api/transacciones` | `@Tag("Transacciones - Comandos")` |

### 7.4 Controllers - Consultas (CQRS Lectura)
| ID | Funcionalidad | Archivos | Endpoints | Swagger/Tags |
|---|---|---|---|---|
| CQ01 | `UserQueryController` | `adapters/in/rest/controller/query/UserQueryController.java` | `GET /api/usuarios/{id}` | `@Tag("Usuarios - Consultas")` |
| CQ02 | `FamilyGroupQueryController` | `.../FamilyGroupQueryController.java` | `GET /api/grupos-familiares/{id}` | `@Tag("Grupos Familiares - Consultas")` |
| CQ03 | `GoalQueryController` | `.../GoalQueryController.java` | `GET /api/metas/{id}`, `GET /api/metas/grupo/{familyGroupId}` | `@Tag("Metas - Consultas")`. Documentación OpenAPI con ejemplos de respuesta. |
| CQ04 | `ContributionQueryController` | `.../ContributionQueryController.java` | `GET /api/aportes/meta/{goalId}` | `@Tag("Aportes - Consultas")` |
| CQ05 | `TransactionQueryController` | `.../TransactionQueryController.java` | `GET /api/transacciones/grupo/{familyGroupId}` | `@Tag("Transacciones - Consultas")` |

### 7.5 Configuración API
| ID | Funcionalidad | Archivos | Descripción |
|---|---|---|---|
| CFG01 | `OpenApiConfig` | `adapters/in/rest/config/OpenApiConfig.java` | Configuración de Swagger/OpenAPI 3 para documentación de API REST. |

---

## 8. HECHO - Configuración y Tests

| ID | Funcionalidad | Tipo | Archivos | Descripción |
|---|---|---|---|---|
| T01 | **Test Integración - GoalRepositoryAdapter** | Test de Integración | `src/test/java/com/gesfin/adapters/out/persistence/GoalRepositoryAdapterTest.java` | Usa `@DataJpaTest` + `@Import(GoalRepositoryAdapter.class)` con H2 embebido. Prueba guardar/recuperar meta con cuotas, buscar por grupo, caso not found. |
| T02 | **Test Unitario - Estrategia Prorrateo** | Test Unitario | `src/test/java/com/gesfin/domain/ports/strategy/EqualDistributionStrategyTest.java` | Tests de dominio puros (sin Spring Context). Valida lógica de prorrateo equitativo. |
| CFG02 | **Aplicación Principal** | Configuración | `src/main/java/com/gesfin/GesfinApplication.java` | Clase main de Spring Boot. |
| CFG03 | **Configuración de Base de Datos** | Config | `src/main/resources/application.properties` / `.env` | Configuración PostgreSQL (puerto 5433 según AGENTS.md). |

---

**Leyenda de Estados:**  
- **Pendiente**: No iniciado
- **En Progreso**: En desarrollo activo  
- **Hecho**: Implementado, probado y funcional

**Cobertura funcional actual:** Core de negocio completo + CRUD completo + CQRS + API REST + Persistencia + Tests básicos. Arquitectura Hexagonal correctamente aplicada siguiendo las reglas de `AGENTS.md`.