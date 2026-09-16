package com.gesfin.adapters.in.rest.controller.query;

import com.gesfin.adapters.in.rest.dto.read.ContributionReadDto;
import com.gesfin.application.queries.ContributionQueryService;
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
@RequestMapping("/api/aportes")
@Tag(name = "Aportes - Consultas", description = "Operaciones de lectura para consultar aportes a las metas")
public class ContributionQueryController {

    private final ContributionQueryService contributionQueryService;

    public ContributionQueryController(ContributionQueryService contributionQueryService) {
        this.contributionQueryService = contributionQueryService;
    }

    @GetMapping("/meta/{goalId}")
    @Operation(
            summary = "Consultar aportes de una meta",
            description = "Devuelve el histórico de aportes realizados hacia una meta específica, ordenado por fecha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de aportes de la meta",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContributionReadDto.class),
                            examples = @ExampleObject(value = """
                                    [
                                      { "id": 1, "goalId": 1, "userId": 1, "monto": 50000.00, "fecha": "2026-08-01" },
                                      { "id": 2, "goalId": 1, "userId": 2, "monto": 100000.00, "fecha": "2026-08-15" }
                                    ]
                                    """))),
            @ApiResponse(responseCode = "400", description = "Meta no encontrada",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<ContributionReadDto>> buscarPorMeta(
            @Parameter(description = "Identificador de la meta", example = "1", required = true)
            @PathVariable Long goalId) {
        List<ContributionReadDto> aportes = contributionQueryService.buscarPorGoalId(goalId)
                .stream()
                .map(ContributionReadDto::from)
                .toList();
        return ResponseEntity.ok(aportes);
    }
}