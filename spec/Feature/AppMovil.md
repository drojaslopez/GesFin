# Solicitud de Cambio: Implementar App Móvil Android Nativa (Kotlin + Jetpack Compose) con Glance Widget como Funcionalidad Principal

## Contexto del Proyecto

Desarrollar la aplicación cliente para Android de la plataforma de **Gestión Financiera Familiar** utilizando la pila nativa de Android (**Kotlin + Jetpack Compose**). La aplicación se conectará al backend (Java / Spring Boot con arquitectura hexagonal y CQRS) y tendrá como **elemento central de la experiencia del usuario un Widget de pantalla de inicio (Android Home Screen Widget)** implementado con **Jetpack Glance**.

---

## 1. Núcleo Principal: Widget de Pantalla de Inicio (Jetpack Glance Widget)

El Glance Widget en Kotlin es la puerta de entrada principal para que la familia mantenga visibilidad financiera en tiempo real directamente desde la pantalla de inicio.

### Funcionalidades del Widget

- **Estado de Metas Rápidas:** Visualización de la meta de ahorro familiar activa, porcentaje de avance acumulado y monto pendiente.
- **Mi Cuota Individual:** Estado del aporte del usuario autenticado (ejemplo: *"Tu cuota: $50.000 / $100.000 — Pendiente"*).
- **Acción Rápida ("Aportar / Registrar"):** Botón integrado que activa un `ActionCallback` para abrir la pantalla de registro rápido de aportes/gastos.
- **Sincronización en Segundo Plano:** Uso de `WorkManager` para actualizar periódicamente los datos del widget consumiendo la API REST del backend.

---

## 2. Aplicación Móvil (Kotlin + Jetpack Compose)

La app nativa permite la administración detallada, configuración del grupo familiar y visualización de datos usando la arquitectura recomendada por Google (MVVM + Clean Architecture).

### Vistas y Funcionalidades

1. **Dashboard Principal:**
   - Resumen del grupo familiar y presupuesto mensual.
   - Detalle de metas activas con desglose de cuotas prorrateadas por integrante.
2. **Registro Rápido de Transacciones (Comandos / CQRS):**
   - Formulario reactivo para registrar un gasto o un aporte en un solo paso.
3. **Historial y Consultas (Queries / CQRS):**
   - Lista de movimientos familiares clasificados por categoría y miembro.
4. **Configuración del Widget:**
   - Selección de la meta preferente a fijar en la pantalla de inicio.

---

## 3. Especificaciones Técnicas y Stack Android

- **Lenguaje:** Kotlin 100% nativo.
- **UI:** Jetpack Compose (App) + Jetpack Glance (Home Screen Widget).
- **Arquitectura Móvil:** Clean Architecture + MVVM (Model-View-ViewModel) + Coroutines & Flow.
- **Inyección de Dependencias:** Hilt / Dagger.
- **Red & Persistencia Local:** Retrofit + OkHttp (para consumir la API REST) y Room Database (para caché local del widget y la app).
- **Integración CQRS / API REST:**
  - Comandos: `POST /api/v1/commands/contributions`
  - Consultas: `GET /api/v1/queries/family-goals/active`
- **Autenticación:** Almacenamiento seguro de tokens con `EncryptedSharedPreferences` / Jetpack DataStore para permitir peticiones autenticadas desde el widget.

---

## Instrucciones para el Agente de IA

1. Configurar la estructura del proyecto Android en Kotlin con Hilt, Jetpack Compose y Jetpack Glance.
2. Implementar primero la clase `GlanceAppWidget` y su `GlanceAppWidgetReceiver` para renderizar el Widget de pantalla de inicio.
3. Configurar el `WorkManager` para actualizar la vista del widget de forma eficiente en segundo plano sin drenar batería.
4. Desarrollar la capa de red con Retrofit orientada a consumir los endpoints de comandos y consultas (CQRS).
5. Crear las pantallas de Compose conectadas a sus respectivos `ViewModels`.
