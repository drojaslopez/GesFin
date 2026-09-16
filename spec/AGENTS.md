# Reglas e Instrucciones para Agentes de IA

## Principios Globales
1. **Idioma:** Todas las respuestas, código comentado, commits y documentación deben mantenerse exclusivamente en **Español**.
2. **Arquitectura Hexagonal Estricta:**
   - La carpeta `domain` NUNCA debe importar paquetes de Spring, Hibernate, JPA o frameworks de infraestructura.
   - Toda comunicación entre el dominio y el exterior se realiza mediante interfaces en `ports`.
3. **Estrategia de Persistencia:**
   - Los tests de integración deben correr en H2 o Testcontainers.
   - El entorno local utiliza PostgreSQL.
   - MANTENER LOS ADAPTADORES DESACOPPLADOS para facilitar la migración futura a Supabase.
4. **Reglas de Negocio:**
   - Para el prorrateo de metas, el cálculo por defecto asigna partes iguales entre los integrantes de la familia.