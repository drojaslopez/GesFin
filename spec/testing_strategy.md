
# Estrategia de Pruebas y Calidad de Código

Este documento establece las directrices, herramientas y estándares de testing para garantizar un código limpio, robusto y desacoplado, en alineación con los principios de la Arquitectura Hexagonal y Domain-Driven Design (DDD).

---

## 1. Pirámide de Pruebas y Cobertura

El proyecto sigue una estructura de pruebas en tres niveles priorizando la velocidad y la independencia de la lógica de negocio:

* **Pruebas Unitarias (Dominio y Aplicación):** Representan el 70% de la suite de pruebas. Evalúan las entidades, reglas de negocio y casos de uso en total aislamiento.
* **Pruebas de Integración (Adaptadores y Persistencia):** Representan el 20%. Validan que los adaptadores de salida interactúen correctamente con la base de datos (PostgreSQL/H2).
* **Pruebas End-to-End / API (Adaptadores de Entrada):** Representan el 10%. Verifican la integración de los endpoints REST y el flujo completo.

### Criterios de Cobertura

* **Capa de Dominio (`domain`):** Mínimo **85%** de cobertura de líneas y ramas.
* **Capa de Aplicación (`application`):** Mínimo **80%** de cobertura.
* **Adaptadores de Persistencia (`adapters`):** Pruebas de integración obligatorias para cada método de repositorio.

---

## 2. Pruebas Unitarias (Hexágono Central)

### Reglas

* **Cero dependencias:** No se permite cargar el contexto de Spring (`@SpringBootTest`), instancias de base de datos ni frameworks externos en esta capa.
* **Mapeo y Mocks:** Los puertos de salida (repositorios) deben simularse utilizando dobles de prueba (Mocks o Stubs).

### Ejemplos de Escenarios

1. **Cálculo de Prorrateo Equitativo:**
   * *Dado* un grupo familiar con 4 miembros y una meta de $400.000.
   * *Cuando* se ejecuta la estrategia `EqualDistributionStrategy`.
   * *Entonces* el sistema debe generar 4 cuotas de $100.000 cada una.
2. **Estrategia Futura (Basada en Ingresos):**
   * *Dado* un miembro con ingresos del 60% del total familiar y otro con el 40%.
   * *Cuando* se calcula la distribución para una meta de $100.000.
   * *Entonces* las cuotas asignadas deben ser $60.000 y $40.000 respectivamente.

---

## 3. Pruebas de Integración (Persistencia)

Para asegurar la independencia del motor de base de datos y facilitar la transición futura hacia Supabase, las pruebas de integración deben validar que los adaptadores respeten los contratos definidos por los puertos.

### Entorno de Ejecución

* **Modo Ligero (H2):** Se utiliza para la ejecución rápida de pruebas locales durante el desarrollo continuo.
* **Modo Aislado (Testcontainers):** Se ejecuta en entornos de CI/CD utilizando un contenedor real de PostgreSQL para garantizar compatibilidad total con producción.

### Mapeo y Aislamiento

* Verificar que el mapeador (`Mapper`) transforme correctamente las entidades puras del dominio hacia las entidades ORM/JPA y viceversa.
* Toda prueba de integración debe ser transaccional y limpiar el estado de la base de datos al finalizar.

---

## 4. Automatización y Calidad en CI/CD

* **Formato y Convenciones:** El código debe cumplir con las reglas de estilo del proyecto antes de cada commit.
* **Pipeline de Verificación:**
  1. Compilación limpia del proyecto.
  2. Ejecución de pruebas unitarias (`domain` y `application`).
  3. Ejecución de pruebas de integración (`adapters`).
  4. Reporte de cobertura y validación de umbrales mínimos.
