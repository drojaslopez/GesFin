package com.gesfin.adapters.in.rest.controller.command;

import com.gesfin.adapters.in.rest.dto.CreateFamilyGroupRequest;
import com.gesfin.adapters.in.rest.dto.read.FamilyGroupReadDto;
import com.gesfin.application.commands.FamilyGroupCommandService;
import com.gesfin.domain.model.FamilyGroup;
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
@RequestMapping("/api/grupos-familiares")
@Tag(name = "Grupos Familiares - Comandos", description = "Operaciones de escritura para la creación de grupos familiares")
public class FamilyGroupCommandController {

    private final FamilyGroupCommandService familyGroupCommandService;

    public FamilyGroupCommandController(FamilyGroupCommandService familyGroupCommandService) {
        this.familyGroupCommandService = familyGroupCommandService;
    }

    @PostMapping
    @Operation(
            summary = "Crear un grupo familiar",
            description = "Comando que crea un grupo familiar nuevo y asigna los integrantes indicados (los usuarios deben existir previamente).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grupo familiar creado con sus integrantes",
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
            @ApiResponse(responseCode = "400", description = "La lista de miembros está vacía o no existen los usuarios",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<FamilyGroupReadDto> crear(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateFamilyGroupRequest.class),
                    examples = @ExampleObject(value = """
                            {
                              "nombre": "Familia Pérez",
                              "miembroIds": [1, 2, 3]
                            }
                            """)))
            CreateFamilyGroupRequest request) {
        FamilyGroup grupo = familyGroupCommandService.crear(request.nombre(), request.miembroIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(FamilyGroupReadDto.from(grupo));
    }
}