# Estructura de Archivos y Documentos para OpenSpec (Spec-Driven Development)

Este documento detalla la estructura completa de carpetas y archivos necesarios para implementar el desarrollo guiado por especificaciones con **OpenSpec**. Está diseñado para servir como plantilla de referencia tanto para el desarrollador como para el agente de IA.

---

## 📁 Arquitectura General del Directorio

```text
mi-proyecto/
├── .cursorrules o CLAUDE.md       # Instrucciones de comportamiento para el Agente IA
└── openspec/                      # Directorio raíz del framework OpenSpec
    ├── config.yaml                # Reglas globales, stack y arquitectura
    ├── specs/                     # Fuente de verdad (Requerimientos Activos)
    │   ├── project.md             # Visión general del proyecto
    │   ├── auth.md                # Ejemplo: Especificaciones del módulo de Auth
    │   └── usuarios.md            # Ejemplo: Especificaciones del módulo de Usuarios
    ├── changes/                   # Gestión de Cambios (Propuestas en Curso)
    │   └── cambio-001-login-jwt/  # Carpeta de una característica específica
    │       ├── proposal.md        # Justificación y alcance del cambio
    │       ├── design.md          # Diseño técnico y de arquitectura
    │       ├── tasks.md           # Lista secuencial de tareas de implementación
    │       └── auth_delta.md      # Cambios temporales en las especificaciones
    └── archive/                   # Historial de cambios ya aplicados y fusionados
```

---

## 🛠️ 1. Archivos Base de Configuración y Contexto (Generados por el Usuario)

Estos archivos establecen las reglas de juego y el contexto de negocio. Deben crearse al inicializar el framework.

### `openspec/config.yaml`
*   **Rol:** El cerebro normativo del framework.
*   **Contenido:** Define el stack tecnológico, patrones arquitectónicos obligatorios, reglas de testing y restricciones estrictas de código (ej: "las funciones no deben superar las 30 líneas").

### `openspec/specs/project.md`
*   **Rol:** Documento raíz de negocio.
*   **Contenido:** Descripción general de la plataforma, objetivos comerciales, perfiles de usuario (personas) y alcance del software.

### `.cursorrules` / `CLAUDE.md` / `AGENTS.md`
*   **Rol:** Configuración del entorno del Agente.
*   **Contenido:** Instrucciones de sistema para el LLM. Indica qué herramientas usar, flujos de Git requeridos y cómo interpretar los comandos de OpenSpec.

---

## 🤖 2. Artefactos del Ciclo de Cambios (Generados por la IA vía `/opsx:propose`)

Archivos operativos creados dinámicamente dentro de `openspec/changes/<nombre-caracteristica>/` para planificar una nueva funcionalidad antes de programar.

### `proposal.md`
*   **Rol:** Alineación conceptual y de negocio.
*   **Contenido:** Redactado por la IA. Explica qué problema resuelve la nueva funcionalidad, por qué es necesaria y los criterios de aceptación generales.

### `design.md`
*   **Rol:** Plan arquitectónico.
*   **Contenido:** Cambios detallados en bases de datos (esquemas), endpoints de API, diseño de componentes de UI y dependencias técnicas de la solución.

### `tasks.md`
*   **Rol:** Hoja de ruta para el desarrollo.
*   **Contenido:** Lista estrictamente secuencial e individual de tareas técnicas. El agente utilizará este archivo como guía obligatoria para codificar paso a paso con `/opsx:apply`.

### Delta Specs (`*_delta.md`)
*   **Rol:** Requerimientos técnicos temporales.
*   **Contenido:** Escenarios en formato Gherkin (`Dado / Cuando / Entonces`) o Markdown que describen los nuevos comportamientos esperados del sistema para este cambio específico.

---

## 🗄️ 3. Directorios de Almacenamiento y Ciclo de Vida

### `openspec/specs/`
*   **Rol:** Documentación activa.
*   **Contenido:** Contiene las especificaciones consolidadas y vigentes del sistema. Cuando un cambio se aprueba, sus *Delta Specs* se fusionan automáticamente aquí.

### `openspec/archive/`
*   **Rol:** Auditoría histórica.
*   **Contenido:** Una vez completado un desarrollo con `/opsx:archive`, la carpeta entera del cambio dentro de `changes/` se traslada aquí para mantener el repositorio limpio.
