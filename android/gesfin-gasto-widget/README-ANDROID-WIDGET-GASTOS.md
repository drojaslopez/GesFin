# Widget Android - Registrar Gasto (GesFin)

## Descripción

Widget Android para registrar gastos rápidamente en GesFin. Utiliza la cuenta logueada del usuario, muestra quién registra el gasto y permite ingresar los datos del gasto (Concepto + Nota + Monto + Fecha). Al registrar correctamente muestra un mensaje de confirmación y cierra el formulario.

## Características

- **Widget minimalista**: Botón "+ Añadir Gasto" + línea "Realizado por: [Usuario]"
- **Registro directo con sesión**: Utiliza userId, nombreUsuario y familyGroupId de la sesión logueada
- **Formulario con Concepto + Nota**: Ambos campos. La Nota es opcional. Se concatenan en el campo `categoria` del backend
- **Monto**: Validación > 0
- **Fecha flexible**: Por defecto fecha actual. Toggle "Cambiar fecha" permite seleccionar otra fecha
- **Validación de sesión**: Si no hay sesión activa, redirige a Login
- **Solo lectura**: Usuario y Grupo Familiar no editables en el formulario
- **Confirmación**: Toast "Gasto registrado correctamente" al guardar con éxito

## Arquitectura

- **Widget**: Jetpack Glance AppWidget
- **API**: Retrofit 2 + OkHttp + Kotlinx Serialization
- **Persistencia**: DataStore Preferences (sesión)
- **Arquitectura**: Widget → Acción → Activity → ViewModel → Repository → Retrofit → Backend

## Estructura del Proyecto

```
app/src/main/java/com/gesfin/widget/
├── core/
│   ├── auth/AuthInterceptor.kt
│   └── datastore/SessionDataStore.kt
├── data/
│   ├── remote/
│   │   ├── api/ApiService.kt
│   │   ├── dto/request/CreateTransactionRequest.kt
│   │   └── dto/response/TransactionReadDto.kt
│   └── repository/
│       ├── AuthRepository.kt
│       └── TransactionRepository.kt
├── di/AppModule.kt
├── ui/
│   ├── gasto/
│   │   ├── RegistrarGastoActivity.kt
│   │   └── RegistrarGastoViewModel.kt
│   ├── login/LoginActivity.kt
│   └── widget/
│       ├── GesfinGastoWidget.kt
│       ├── GesfinGastoWidgetReceiver.kt
│       ├── action/RegistrarGastoAction.kt
│       └── config/WidgetConfigActivity.kt
└── GesfinWidgetApp.kt
```

## Configuración

### URL Base API

La URL base está configurada en `di/AppModule.kt`:

```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/"
```

- **Emulador Android**: `10.0.2.2:8080` apunta a `localhost:8080` del host
- **Dispositivo físico**: Cambiar por IP LAN del backend (ej. `http://192.168.1.100:8080/`)

### CORS (Backend)

Se creó `CorsDevConfig` en el backend con `@Profile("dev")` para permitir orígenes:
- `http://10.0.2.2:*`
- `http://localhost:*`
- `http://127.0.0.1:*`

### cleartextTraffic

En `AndroidManifest.xml`, `android:usesCleartextTraffic="true"` solo está habilitado para desarrollo. Para producción debe desactivarse y usar HTTPS.

## Uso

1. **Iniciar sesión**: Abrir la app y completar User ID, Nombre Usuario, Family Group ID y Token JWT (opcional en dev)
2. **Añadir widget**: Pulsar largo en escritorio → Widgets → GesFin Gasto Widget → Colocar
3. **Configurar widget**: Al añadirlo, se abrirá la configuración mostrando los datos de sesión
4. **Registrar gasto**: Pulsar "+ Añadir Gasto" en el widget → Completar Concepto*, Nota (opcional), Monto*, activar "Cambiar fecha" si es necesario → Guardar
5. **Confirmación**: Aparece toast "Gasto registrado correctamente" y se cierra el formulario

## Mapeo Backend

El widget envía `POST /api/transacciones` con:

```json
{
  "familyGroupId": 1,
  "userId": 1,
  "monto": "100.50",
  "tipo": "GASTO",
  "categoria": "Compra supermercado - Nota: Leche y pan",
  "fecha": "2026-10-05"
}
```

- `Concepto` + `Nota` se concatenan en `categoria`
- `tipo` siempre `GASTO`
- `fecha` en formato `yyyy-MM-dd` (ISO_LOCAL_DATE)

## Fases Implementadas

- ✅ FASE 0: Configuración CORS backend (dev)
- ✅ FASE 1: Estructura Android + Dependencias + Recursos
- ✅ FASE 2: Auth + DataStore + DTOs + Repository + DI + App
- ✅ FASE 3: Widget Glance + Action + Receiver + Config
- ✅ FASE 4: Login + Formulario Registro + ViewModel
- ✅ FASE 5: Documentación
