package com.gesfin.adapters.in.rest.controller.command;

import com.gesfin.adapters.in.rest.dto.CreateContributionRequest;
import com.gesfin.adapters.in.rest.dto.read.ContributionReadDto;
import com.gesfin.application.commands.ContributionCommandService;
import com.gesfin.domain.model.Contribution;
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
@RequestMapping("/api/aportes")
@Tag(name = "Aportes - Comandos", description = "Operaciones de escritura para aportes a las metas de ahorro")
public class ContributionCommandController {

    private final ContributionCommandService contributionCommandService;

    public ContributionCommandController(ContributionCommandService contributionCommandService) {
        this.contributionCommandService = contributionCommandService;
    }

    @PostMapping
    @Operation(
            summary = "Registrar un aporte a una meta",
            description = """
                    Comando que registra el aporte de un integrante hacia una meta. Incrementa el monto \
                    aportado de la cuota del miembro y actualiza el progreso. Si alcanza el 100%, la meta \
                    pasa a estado CUMPLIDA.
                    """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aporte registrado y progreso actualizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContributionReadDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "goalId": 1,
                                      "userId": 2,
                                      "monto": 100000.00,
                                      "fecha": "2026-09-16"
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "La meta no existe, el usuario no tiene cuota o el monto es inválido",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ContributionReadDto> crear(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateContributionRequest.class),
                    examples = @ExampleObject(value = """
                            {
                              "goalId": 1,
                              "userId": 2,
                              "monto": 100000.00
                            }
                            """)))
            CreateContributionRequest request) {
        Contribution contribution = contributionCommandService.registrar(
                request.goalId(),
                request.userId(),
                request.monto()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ContributionReadDto.from(contribution));
    }
}