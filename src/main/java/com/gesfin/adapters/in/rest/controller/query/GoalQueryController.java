package com.gesfin.adapters.in.rest.controller.query;

import com.gesfin.adapters.in.rest.dto.read.GoalReadDto;
import com.gesfin.application.queries.GoalQueryService;
import com.gesfin.domain.model.Goal;
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
@RequestMapping("/api/metas")
@Tag(name = "Metas - Consultas", description = "Operaciones de lectura para consultar metas de ahorro y su progreso")
public class GoalQueryController {

    private final GoalQueryService goalQueryService;

    public GoalQueryController(GoalQueryService goalQueryService) {
        this.goalQueryService = goalQueryService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Consultar detalle de una meta",
            description = "Devuelve el detalle completo de una meta: descripción, monto objetivo, fecha límite, estado, progreso y cuotas asignadas por integrante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meta encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalReadDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "familyGroupId": 1,
                                      "descripcion": "Viaje familiar a la costa",
                                      "montoObjetivo": 1200000.00,
                                      "fechaLimite": "2027-03-15",
                                      "estado": "ACTIVA",
                                      "progreso": 25.00,
                                      "cuotas": [
                                        { "userId": 1, "montoAsignado": 400000.00, "montoAportado": 300000.00, "montoRestante": 100000.00 },
                                        { "userId": 2, "montoAsignado": 400000.00, "montoAportado": 0.00, "montoRestante": 400000.00 },
                                        { "userId": 3, "montoAsignado": 400000.00, "montoAportado": 0.00, "montoRestante": 400000.00 }
                                      ]
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Meta no encontrada",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<GoalReadDto> buscarPorId(
            @Parameter(description = "Identificador de la meta", example = "1", required = true)
            @PathVariable Long id) {
        Goal goal = goalQueryService.buscar(id);
        return ResponseEntity.ok(GoalReadDto.from(goal));
    }

    @GetMapping("/grupo/{familyGroupId}")
    @Operation(
            summary = "Consultar metas de un grupo familiar",
            description = "Devuelve la lista de todas las metas pertenecientes a un grupo familiar, con su estado y progreso actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de metas del grupo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalReadDto.class))),
            @ApiResponse(responseCode = "400", description = "Grupo familiar no encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<GoalReadDto>> buscarPorGrupo(
            @Parameter(description = "Identificador del grupo familiar", example = "1", required = true)
            @PathVariable Long familyGroupId) {
        List<GoalReadDto> metas = goalQueryService.buscarPorFamilyGroupId(familyGroupId)
                .stream()
                .map(GoalReadDto::from)
                .toList();
        return ResponseEntity.ok(metas);
    }
}