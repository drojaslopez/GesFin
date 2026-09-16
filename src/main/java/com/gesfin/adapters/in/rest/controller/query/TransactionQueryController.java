package com.gesfin.adapters.in.rest.controller.query;

import com.gesfin.adapters.in.rest.dto.read.TransactionReadDto;
import com.gesfin.application.queries.TransactionQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@Tag(name = "Transacciones - Consultas", description = "Operaciones de lectura para consultar movimientos financieros del grupo")
public class TransactionQueryController {

    private final TransactionQueryService transactionQueryService;

    public TransactionQueryController(TransactionQueryService transactionQueryService) {
        this.transactionQueryService = transactionQueryService;
    }

    @GetMapping("/grupo/{familyGroupId}")
    @Operation(
            summary = "Consultar transacciones de un grupo familiar",
            description = "Devuelve todos los movimientos financieros (ingresos y gastos) registrados por el grupo familiar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transacciones del grupo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionReadDto.class),
                            examples = @ExampleObject(value = """
                                    [
                                      { "id": 1, "familyGroupId": 1, "userId": 1, "monto": 2000000.00, "tipo": "INGRESO", "categoria": "Sueldo", "fecha": "2026-09-01" },
                                      { "id": 2, "familyGroupId": 1, "userId": 1, "monto": 85000.00, "tipo": "GASTO", "categoria": "Supermercado", "fecha": "2026-09-16" }
                                    ]
                                    """))),
            @ApiResponse(responseCode = "400", description = "Grupo familiar no encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<TransactionReadDto>> buscarPorGrupo(
            @Parameter(description = "Identificador del grupo familiar", example = "1", required = true)
            @PathVariable Long familyGroupId) {
        List<TransactionReadDto> transacciones = transactionQueryService.buscarPorFamilyGroupId(familyGroupId)
                .stream()
                .map(TransactionReadDto::from)
                .toList();
        return ResponseEntity.ok(transacciones);
    }
}