package com.gesfin.adapters.out.persistence;

import com.gesfin.adapters.out.persistence.adapter.GoalRepositoryAdapter;
import com.gesfin.domain.enums.EstadoMeta;
import com.gesfin.domain.model.Goal;
import com.gesfin.domain.model.MemberQuota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@DataJpaTest
@Import(GoalRepositoryAdapter.class)
class GoalRepositoryAdapterTest {

    @Autowired
    private GoalRepositoryAdapter goalRepositoryAdapter;

    @Test
    @DisplayName("Guarda y recupera una meta con sus cuotas")
    void guardaYRecuperaMetaConCuotas() {
        Goal goal = new Goal();
        goal.setFamilyGroupId(1L);
        goal.setDescripcion("Viaje familiar");
        goal.setMontoObjetivo(new BigDecimal("400000"));
        goal.setFechaLimite(LocalDate.now().plusMonths(6));
        goal.setEstado(EstadoMeta.ACTIVA);

        MemberQuota cuota1 = new MemberQuota(10L, new BigDecimal("200000"));
        MemberQuota cuota2 = new MemberQuota(20L, new BigDecimal("200000"));
        goal.setCuotas(List.of(cuota1, cuota2));

        Goal guardada = goalRepositoryAdapter.guardar(goal);
        assertNotNull(guardada.getId());
        assertEquals(2, guardada.getCuotas().size());

        Goal recuperada = goalRepositoryAdapter.buscarPorId(guardada.getId()).orElse(null);
        assertNotNull(recuperada);
        assertEquals("Viaje familiar", recuperada.getDescripcion());
        assertEquals(new BigDecimal("400000"), recuperada.getMontoObjetivo());
        assertEquals(2, recuperada.getCuotas().size());
        assertEquals(10L, recuperada.getCuotas().get(0).getUserId());
        assertEquals(new BigDecimal("200000"), recuperada.getCuotas().get(0).getMontoAsignado());
    }

    @Test
    @DisplayName("Busca todas las metas de un grupo familiar")
    void buscaMetasPorGrupoFamiliar() {
        for (int i = 1; i <= 3; i++) {
            Goal goal = new Goal();
            goal.setFamilyGroupId(99L);
            goal.setDescripcion("Meta " + i);
            goal.setMontoObjetivo(new BigDecimal("100000"));
            goal.setEstado(EstadoMeta.ACTIVA);
            goalRepositoryAdapter.guardar(goal);
        }

        List<Goal> metas = goalRepositoryAdapter.buscarPorFamilyGroupId(99L);
        assertEquals(3, metas.size());
    }

    @Test
    @DisplayName("Devuelve Optional vacío si la meta no existe")
    void devuelveVacioCuandoNoExiste() {
        assertTrue(goalRepositoryAdapter.buscarPorId(999L).isEmpty());
    }
}