package com.gesfin.adapters.in.rest.controller.command;

import com.gesfin.adapters.in.rest.dto.CreateTransactionRequest;
import com.gesfin.adapters.in.rest.dto.read.TransactionReadDto;
import com.gesfin.application.commands.TransactionCommandService;
import com.gesfin.domain.model.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacciones")
@Tag(name = "Transacciones - Comandos", description = "Operaciones de escritura para registrar movimientos financieros")
public class TransactionCommandController {

    private final TransactionCommandService transactionCommandService;

    public TransactionCommandController(TransactionCommandService transactionCommandService) {
        this.transactionCommandService = transactionCommandService;
    }

    @PostMapping
    @Operation(
            summary = "Registrar una transacción",
            description = """
                    Comando que registra un movimiento financiero (INGRESO o GASTO) del grupo familiar. \
                    Si no se envía fecha, se usa la fecha actual del sistema.
                    """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transacción registrada correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionReadDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "familyGroupId": 1,
                                      "userId": 1,
                                      "monto": 85000.00,
                                      "tipo": "GASTO",
                                      "categoria": "Supermercado",
                                      "fecha": "2026-09-16"
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "El monto es inválido, falta el tipo o el grupo no existe",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<TransactionReadDto> crear(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateTransactionRequest.class),
                    examples = @ExampleObject(value = """
                            {
                              "familyGroupId": 1,
                              "userId": 1,
                              "monto": 85000.00,
                              "tipo": "GASTO",
                              "categoria": "Supermercado",
                              "fecha": "2026-09-16"
                            }
                            """)))
            CreateTransactionRequest request) {
        Transaction transaction = transactionCommandService.registrar(
                request.familyGroupId(),
                request.userId(),
                request.monto(),
                request.tipo(),
                request.categoria(),
                request.fecha()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionReadDto.from(transaction));
    }
}