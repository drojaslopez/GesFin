# Visión General y Requerimientos del Proyecto

## Descripción General
Plataforma de gestión financiera familiar bajo arquitectura hexagonal orientada al control presupuestario, metas de ahorro comunitarias y seguimiento transparente de transacciones y aportes.

---

## Requerimientos Funcionales (RF)

### RF-01: Gestión de Usuarios y Familias
- **RF-01.1:** Registro, autenticación y gestión de perfiles de usuario.
- **RF-01.2:** Creación y gestión de grupos familiares.
- **RF-01.3:** Asignación de roles dentro del grupo (ej. Administrador y Miembro).

### RF-02: Gestión de Metas de Ahorro
- **RF-02.1:** Creación de metas financieras del grupo familiar (monto objetivo, fecha límite y descripción).
- **RF-02.2 (Prorrateo Inicial):** Asignación automática del monto objetivo dividido en partes iguales entre los integrantes del grupo.
- **RF-02.3 (Extensibilidad Futura):** Arquitectura y dominio preparados para permitir la asignación de aportes por porcentaje de participación variable (ej. según ingresos).

### RF-03: Transacciones y Aportes
- **RF-03.1:** Registro de transacciones (ingresos, gastos grupales e individuales).
- **RF-03.2:** Registro de aportes individuales dirigidos a metas específicas.
- **RF-03.3:** Actualización en tiempo real del progreso de cumplimiento de las metas.

---

## Requerimientos No Funcionales (RNF)

### RNF-01: Arquitectura e Independencia
- **RNF-01.1:** Aplicación estrictamente estructurada bajo Arquitectura Hexagonal y Domain-Driven Design (DDD).
- **RNF-01.2:** Lógica de negocio 100% agnóstica a la infraestructura, bases de datos y frameworks externos.
- **RNF-01.3:** Código limpio, sin dependencias innecesarias ("código controlado").

### RNF-02: Persistencia y Escalabilidad
- **RNF-02.1:** Soporte inicial para persistencia en PostgreSQL local vía adaptadores JPA/Hibernate.
- **RNF-02.2:** Entorno de pruebas desacoplado utilizando base de datos en memoria (H2) o Testcontainers.
- **RNF-02.3:** Diseño de puertos listo para la migración/integración futura con Supabase.

### RNF-03: Integración Agéntica / OpenSpec
- **RNF-03.1:** Documentación técnica, specs y reglas de agentividad configuradas en español para automatización con herramientas como Devin, Cursor u OpenCode.