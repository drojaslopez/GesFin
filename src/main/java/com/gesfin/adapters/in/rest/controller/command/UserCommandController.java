package com.gesfin.adapters.in.rest.controller.command;

import com.gesfin.adapters.in.rest.dto.CreateUserRequest;
import com.gesfin.adapters.in.rest.dto.read.UserReadDto;
import com.gesfin.application.commands.UserCommandService;
import com.gesfin.domain.model.User;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios - Comandos", description = "Operaciones de escritura para el registro de usuarios")
public class UserCommandController {

    private final UserCommandService userCommandService;

    public UserCommandController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping
    @Operation(
            summary = "Registrar un usuario",
            description = "Comando que crea un nuevo perfil de usuario. El email debe ser único. El rol puede ser ADMINISTRADOR o MIEMBRO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserReadDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "nombre": "Daniel Pérez",
                                      "email": "daniel@gesfin.com",
                                      "rol": "ADMINISTRADOR",
                                      "familyGroupId": null
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "El email ya está registrado o los datos son inválidos",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<UserReadDto> crear(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserRequest.class),
                    examples = @ExampleObject(value = """
                            {
                              "nombre": "Daniel Pérez",
                              "email": "daniel@gesfin.com",
                              "password": "clave_segura_123",
                              "rol": "ADMINISTRADOR"
                            }
                            """)))
            CreateUserRequest request) {
        User user = userCommandService.crear(
                request.nombre(),
                request.email(),
                request.password(),
                request.rol()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(UserReadDto.from(user));
    }
}