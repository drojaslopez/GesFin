package com.gesfin.adapters.in.rest.controller.query;

import com.gesfin.adapters.in.rest.dto.read.FamilyGroupReadDto;
import com.gesfin.application.queries.FamilyGroupQueryService;
import com.gesfin.domain.model.FamilyGroup;
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

@RestController
@RequestMapping("/api/grupos-familiares")
@Tag(name = "Grupos Familiares - Consultas", description = "Operaciones de lectura para consultar grupos familiares y sus integrantes")
public class FamilyGroupQueryController {

    private final FamilyGroupQueryService familyGroupQueryService;

    public FamilyGroupQueryController(FamilyGroupQueryService familyGroupQueryService) {
        this.familyGroupQueryService = familyGroupQueryService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Consultar un grupo familiar",
            description = "Devuelve el detalle de un grupo familiar con la lista completa de sus integrantes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupo familiar encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FamilyGroupReadDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "nombre": "Familia Pérez",
                                      "miembros": [
                                        { "id": 1, "nombre": "Daniel Pérez", "email": "daniel@gesfin.com", "rol": "ADMINISTRADOR", "familyGroupId": 1 },
                                        { "id": 2, "nombre": "María Pérez", "email": "maria@gesfin.com", "rol": "MIEMBRO", "familyGroupId": 1 }
                                      ]
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Grupo familiar no encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<FamilyGroupReadDto> buscar(
            @Parameter(description = "Identificador del grupo familiar", example = "1", required = true)
            @PathVariable Long id) {
        FamilyGroup grupo = familyGroupQueryService.buscar(id);
        return ResponseEntity.ok(FamilyGroupReadDto.from(grupo));
    }
}