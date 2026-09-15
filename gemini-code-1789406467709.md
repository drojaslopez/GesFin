# Especificaciones Técnicas y Modelo de Dominio

## Modelo de Dominio (Hexágono Central)

### Entidades Principales
1. **Usuario (`User`):**
   - Atributos: `id`, `nombre`, `email`, `rol`.
2. **Grupo Familiar (`FamilyGroup`):**
   - Atributos: `id`, `nombre`, `miembros` (Lista de `User`).
3. **Meta (`Goal`):**
   - Atributos: `id`, `familyGroupId`, `montoObjetivo`, `fechaLimite`, `estado`, `cuotas` (Lista de `MemberQuota`).
4. **Cuota de Miembro (`MemberQuota`):**
   - Atributos: `userId`, `montoAsignado`, `montoAportado`.
   - Regla de negocio actual: `montoAsignado = montoObjetivo / cantidadMiembros`.
5. **Aporte (`Contribution`):**
   - Atributos: `id`, `goalId`, `userId`, `monto`, `fecha`.
6. **Transacción (`Transaction`):**
   - Atributos: `id`, `familyGroupId`, `userId`, `monto`, `tipo` (INGRESO / GASTO), `categoria`, `fecha`.

---

## Puertos (Ports)

### Puertos de Entrada (Casos de Uso / Application Layer)
- `CreateGoalUseCase`
- `RecordContributionUseCase`
- `CalculateQuotasUseCase` (Estrategia de cálculo: por defecto `EqualDistributionStrategy`, extensible a `IncomeBasedStrategy`).
- `RegisterTransactionUseCase`

### Puertos de Salida (Persistencia e Infraestructura / Domain Ports)
- `UserRepositoryPort`
- `GoalRepositoryPort`
- `ContributionRepositoryPort`
- `TransactionRepositoryPort`

---

## Adaptadores (Adapters)

### Adaptadores de Entrada (Driving Adapters)
- REST Controllers (Spring Boot / API)
- Controladores / Clientes para Mobile y Web

### Adaptadores de Salida (Driven Adapters)
- `PostgresGoalRepositoryAdapter` (Implementa `GoalRepositoryPort` usando Spring Data JPA).
- `H2GoalRepositoryAdapter` / `TestcontainersAdapter` (Para pruebas de integración aisladas).
- *Adaptador Futuro:* `SupabaseGoalRepositoryAdapter`.