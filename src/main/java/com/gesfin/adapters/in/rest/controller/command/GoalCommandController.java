package com.gesfin.adapters.in.rest.controller.command;

import com.gesfin.adapters.in.rest.dto.CreateGoalRequest;
import com.gesfin.adapters.in.rest.dto.read.GoalReadDto;
import com.gesfin.application.commands.GoalCommandService;
import com.gesfin.domain.model.Goal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/metas")
@Tag(name = "Metas - Comandos", description = "Operaciones de escritura para metas de ahorro: creación y recálculo de cuotas")
public class GoalCommandController {

    private final GoalCommandService goalCommandService;

    public GoalCommandController(GoalCommandService goalCommandService) {
        this.goalCommandService = goalCommandService;
    }

    @PostMapping
    @Operation(
            summary = "Crear una meta de ahorro",
            description = """
                    Comando para crear una nueva meta de ahorro. Calcula automáticamente el prorrateo del \
                    monto objetivo en cuotas iguales entre los integrantes del grupo (estrategia \
                    EqualDistributionStrategy).
                    """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Meta creada correctamente con sus cuotas asignadas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalReadDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "familyGroupId": 1,
                                      "descripcion": "Viaje familiar a la costa",
                                      "montoObjetivo": 1200000.00,
                                      "fechaLimite": "2027-03-15",
                                      "estado": "ACTIVA",
                                      "progreso": 0.00,
                                      "cuotas": [
                                        { "userId": 1, "montoAsignado": 400000.00, "montoAportado": 0.00, "montoRestante": 400000.00 },
                                        { "userId": 2, "montoAsignado": 400000.00, "montoAportado": 0.00, "montoRestante": 400000.00 },
                                        { "userId": 3, "montoAsignado": 400000.00, "montoAportado": 0.00, "montoRestante": 400000.00 }
                                      ]
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "El grupo familiar no existe o los datos son inválidos",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<GoalReadDto> crear(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateGoalRequest.class),
                    examples = @ExampleObject(value = """
                            {
                              "familyGroupId": 1,
                              "descripcion": "Viaje familiar a la costa",
                              "montoObjetivo": 1200000.00,
                              "fechaLimite": "2027-03-15"
                            }
                            """)))
            CreateGoalRequest request) {
        Goal creada = goalCommandService.crear(
                request.familyGroupId(),
                request.descripcion(),
                request.montoObjetivo(),
                request.fechaLimite()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(GoalReadDto.from(creada));
    }

    @PostMapping("/{id}/calcular-cuotas")
    @Operation(
            summary = "Recalcular cuotas de una meta",
            description = "Comando que recalcula y reasigna las cuotas de una meta en partes iguales según los integrantes vigentes del grupo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuotas recalculadas correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalReadDto.class))),
            @ApiResponse(responseCode = "400", description = "La meta o el grupo familiar no existen",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<GoalReadDto> calcularCuotas(
            @Parameter(description = "Identificador de la meta", example = "1", required = true)
            @PathVariable Long id) {
        Goal goal = goalCommandService.calcularYAsignarCuotas(id);
        return ResponseEntity.ok(GoalReadDto.from(goal));
    }
}