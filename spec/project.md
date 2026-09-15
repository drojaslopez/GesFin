# Visión General del Proyecto

## Descripción
Plataforma de gestión financiera familiar orientada a la administración de presupuestos, metas de ahorro en conjunto y registro de gastos comunitarios.

## Objetivos
- Permitir la creación de metas de ahorro familiares prorrateadas entre los integrantes.
- Ofrecer visibilidad y seguimiento transparente de aportes y gastos.
- Facilitar la integración futura con agentes de Inteligencia Artificial para recomendaciones financieras.

## Perfiles de Usuario
- **Administrador Familiar:** Crea el grupo familiar, define metas y gestiona miembros.
- **Integrante:** Contribuye a las metas de ahorro y registra sus ingresos y gastos.

## Alcance
- Módulo de Autenticación y Usuarios.
- Módulo de Metas de Ahorro y Gastos.
- Integración local inicial mediante bases de datos relacionales con proyección a arquitectura híbrida en la nube.

# Características y Funcionalidades Clave del Proyecto

## 1. Módulo de Gestión de Usuarios y Grupo Familiar
* **Registro y Autenticación:** Registro de perfiles de usuario y gestión de acceso a la plataforma.
* **Grupos Familiares:** Creación y administración de grupos familiares para compartir presupuestos y metas de ahorro.
* **Roles y Permisos:** Diferenciación de permisos dentro del grupo (administrador y miembro).

## 2. Módulo de Metas de Ahorro y Prorrateo
* **Creación de Metas:** Definición de objetivos financieros comunitarios con monto objetivo, descripción y fecha límite.
* **Prorrateo Inicial (Partes Iguales):** Cálculo automático que divide el monto total de la meta en cuotas obligatorias equitativas entre todos los integrantes activos del grupo.
* **Lógica de Porcentajes Flexible (Preparada a Futuro):** Arquitectura de dominio extensible mediante el patrón de estrategia (`CalculateQuotasStrategy`), lista para implementar asignaciones proporcionales basadas en el nivel de ingresos de cada integrante (el que gana más, aporta más).
* **Seguimiento y Progreso:** Registro de aportes individuales con actualización del porcentaje de avance hacia la meta.

## 3. Módulo de Transacciones y Presupuesto
* **Registro de Movimientos:** Control e ingreso de transacciones clasificadas en ingresos, gastos individuales y gastos compartidos del grupo familiar.
* **Categorización:** Clasificación de gastos e ingresos para un seguimiento financiero claro.

## 4. Arquitectura y Principios Técnicos
* **Arquitectura Hexagonal (DDD):** Dominio central completamente aislado de frameworks externos, herramientas de persistencia y controladores.
* **Persistencia Agnóstica:** 
  * Entorno local inicial basado en PostgreSQL mediante adaptadores JPA/Hibernate.
  * Entorno de pruebas desacoplado con H2 o Testcontainers.
  * Puertos de salida diseñados para permitir una migración o integración fluida con Supabase a futuro.
* **Desarrollo Guiado por IA (OpenSpec):** Estructura de documentación y configuración agéntica (`openspec/`, `AGENTS.md`) configurada en español para automatizar la generación y evolución del código mediante agentes como OpenCode, Devin o Cursor.