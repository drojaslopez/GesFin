package com.gesfin.domain.ports.strategy;

import com.gesfin.domain.model.MemberQuota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EqualDistributionStrategyTest {

    private final EqualDistributionStrategy strategy = new EqualDistributionStrategy();

    @Nested
    @DisplayName("Prorrateo equitativo")
    class ProrrateoEquitativo {

        @Test
        @DisplayName("Divide el monto en partes iguales entre 4 miembros")
        void divideMontoEntreCuatroMiembros() {
            List<MemberQuota> cuotas = strategy.calcularCuotas(
                    new BigDecimal("400000"), List.of(1L, 2L, 3L, 4L));

            assertEquals(4, cuotas.size());
            cuotas.forEach(cuota -> {
                assertEquals(new BigDecimal("100000.00"), cuota.getMontoAsignado());
                assertEquals(BigDecimal.ZERO, cuota.getMontoAportado());
            });
        }

        @Test
        @DisplayName("Asigna el monto por miembro con 2 decimales (redondeo HALF_UP)")
        void redondeaADosDecimales() {
            List<MemberQuota> cuotas = strategy.calcularCuotas(
                    new BigDecimal("100000"), List.of(1L, 2L, 3L));

            assertEquals(3, cuotas.size());
            cuotas.forEach(cuota -> {
                assertEquals(new BigDecimal("33333.33").setScale(2, RoundingMode.HALF_UP),
                        cuota.getMontoAsignado());
            });
        }

        @Test
        @DisplayName("Cubre el caso de una meta con un solo miembro")
        void unSoloMiembro() {
            List<MemberQuota> cuotas = strategy.calcularCuotas(
                    new BigDecimal("50000"), List.of(1L));

            assertEquals(1, cuotas.size());
            assertEquals(new BigDecimal("50000.00"), cuotas.get(0).getMontoAsignado());
        }
    }

    @Nested
    @DisplayName("Validaciones")
    class Validaciones {

        @Test
        @DisplayName("Lanza excepción si el monto objetivo es nulo")
        void montoNulo() {
            assertThrows(IllegalArgumentException.class,
                    () -> strategy.calcularCuotas(null, List.of(1L)));
        }

        @Test
        @DisplayName("Lanza excepción si el monto objetivo es menor o igual a cero")
        void montoInvalido() {
            assertThrows(IllegalArgumentException.class,
                    () -> strategy.calcularCuotas(BigDecimal.ZERO, List.of(1L)));
            assertThrows(IllegalArgumentException.class,
                    () -> strategy.calcularCuotas(new BigDecimal("-100"), List.of(1L)));
        }

        @Test
        @DisplayName("Lanza excepción si la lista de usuarios está vacía")
        void listaVacia() {
            assertThrows(IllegalArgumentException.class,
                    () -> strategy.calcularCuotas(new BigDecimal("1000"), List.of()));
        }
    }
}