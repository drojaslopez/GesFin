# GesFin
Resumen del Proyecto

El proyecto consiste en el desarrollo de una aplicación de gestión financiera familiar construida bajo Arquitectura Hexagonal y Domain-Driven Design (DDD). El sistema permite administrar presupuestos familiares, registrar transacciones y gestionar metas de ahorro compartidas.

Principales características del enfoque:

    Persistencia agnóstica: Inicialmente se utilizará PostgreSQL local con soporte de pruebas en H2/Testcontainers, manteniendo el dominio totalmente desacoplado para migrar o integrar fácilmente Supabase a futuro.

    Código limpio y controlado: Cero dependencias innecesarias en el núcleo de negocio.

    Automatización guiada por IA: Estructura compatible con el estándar OpenSpec para automatizar el desarrollo usando herramientas como OpenCode, Devin o Cursor.

Detalle de Implementación

1. Núcleo de Dominio (Hexágono Central)

    Entidades puras:

        User: Información y perfil de los integrantes.

        FamilyGroup: Agrupación de miembros de la familia.

        Goal: Meta financiera con monto objetivo, fecha límite y estado.

        MemberQuota: Cuota asignada a cada miembro.

        Contribution: Aportes realizados a cada meta.

        Transaction: Registro de ingresos y gastos de la familia.

    Lógica de prorrateo:

        Implementación por defecto: EqualDistributionStrategy (división en partes iguales).

        Extensión futura preparada: IncomeBasedStrategy (asignación proporcional según ingresos).

2. Capa de Aplicación (Puertos de Entrada y Casos de Uso)

    CreateGoalUseCase: Creación de metas y cálculo inicial de cuotas.

    RecordContributionUseCase: Registro de aportes y actualización del progreso.

    RegisterTransactionUseCase: Registro de movimientos financieros.

    CalculateQuotasUseCase: Ejecución de la estrategia de distribución.

3. Capa de Adaptadores (Infraestructura)

    Adaptadores de Salida (Driven):

        PostgresGoalRepositoryAdapter (y equivalentes para usuarios/transacciones) usando Spring Data JPA.

        Interfaces agnósticas en ports para permitir el intercambio a adaptadores de Supabase más adelante.

    Adaptadores de Entrada (Driving):

        Controladores REST para conectar con las futuras aplicaciones Web (React) y Móvil (Flutter).

4. Estrategia de Pruebas

    Pruebas unitarias sobre la lógica de dominio sin dependencias externas.

    Pruebas de integración sobre los adaptadores de datos usando H2 local o Testcontainers.

5. Configuración Agéntica (OpenSpec)

    Archivo openspec/config.yaml con las reglas de arquitectura y stack.

    Requerimientos y specs en openspec/specs/ (project.md, especificaciones_tecnicas.md).

    Indicaciones de comportamiento para la IA en AGENTS.md (todo en idioma español).



Aquí tienes el plan de implementación ordenado paso a paso para ejecutar con las herramientas de IA (OpenCode / Devin / Cursor):

    Configurar el entorno OpenSpec

        Crear la carpeta openspec/ en la raíz del proyecto.

        Crear el archivo openspec/config.yaml definiendo el stack (Java/Spring Boot, PostgreSQL, React/Flutter) y las reglas de arquitectura hexagonal.

        Crear el archivo AGENTS.md o .cursorrules con las instrucciones del agente en español.

    Inicializar las especificaciones del proyecto

        Crear openspec/specs/project.md con los requerimientos funcionales y no funcionales.

        Crear openspec/specs/especificaciones_tecnicas.md detallando las entidades del dominio (User, FamilyGroup, Goal, MemberQuota, Contribution, Transaction) y los puertos.

    Construir el núcleo del dominio (Hexágono Central)

        Implementar las entidades puras sin anotaciones de persistencia ni dependencias de frameworks.

        Crear la interfaz/estrategia de prorrateo CalculateQuotasStrategy con la implementación inicial EqualDistributionStrategy (partes iguales).

        Dejar preparada la interfaz para la estrategia futura de asignación por porcentaje de ingresos.

    Definir los puertos de aplicación y salida

        Crear los casos de uso (CreateGoalUseCase, RecordContributionUseCase, RegisterTransactionUseCase).

        Crear las interfaces de los repositorios en el dominio (UserRepositoryPort, GoalRepositoryPort, TransactionRepositoryPort).

    Desarrollar los adaptadores de persistencia locales

        Crear el módulo/paquete de adaptadores de salida.

        Implementar PostgresGoalRepositoryAdapter y demás repositorios utilizando Spring Data JPA para la base de datos PostgreSQL local.

        Configurar el aislamiento para asegurar que el dominio no conozca los detalles de la base de datos.

    Configurar la estrategia de pruebas de integración

        Configurar H2 o Testcontainers para ejecutar pruebas de integración de los adaptadores de persistencia.

        Escribir pruebas unitarias para la lógica del dominio (prorrateo de metas y aportes).

        Escribir pruebas de integración para validar el comportamiento del repositorio local en PostgreSQL.

    Preparar la arquitectura para evoluciones futuras

        Diseñar los puertos de salida de forma totalmente agnóstica para facilitar la posterior creación de adaptadores para Supabase.

        Configurar las interfaces de entrada (REST Controllers) para la futura integración con las aplicaciones web y móvil.