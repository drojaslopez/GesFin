package com.gesfin.adapters.in.rest.controller.query;

import com.gesfin.adapters.in.rest.dto.read.UserReadDto;
import com.gesfin.application.queries.UserQueryService;
import com.gesfin.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios - Consultas", description = "Operaciones de lectura para consultar perfiles de usuario")
public class UserQueryController {

    private final UserQueryService userQueryService;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Consultar un usuario por ID",
            description = "Devuelve el detalle de un usuario: nombre, email, rol y grupo familiar al que pertenece.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserReadDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "nombre": "Daniel Pérez",
                                      "email": "daniel@gesfin.com",
                                      "rol": "ADMINISTRADOR",
                                      "familyGroupId": 1
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<UserReadDto> buscar(
            @Parameter(description = "Identificador del usuario", example = "1", required = true)
            @PathVariable Long id) {
        User user = userQueryService.buscar(id);
        return ResponseEntity.ok(UserReadDto.from(user));
    }

    @GetMapping("/")
    @Operation(
            summary = "Consultar los usuarios",
            description = "Devuelve la lista de todos los usuarios: nombre, email, rol y grupo familiar al que pertenece.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserReadDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "nombre": "Daniel Pérez",
                                      "email": "daniel@gesfin.com",
                                      "rol": "ADMINISTRADOR",
                                      "familyGroupId": 1
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Usuarios no encontrados",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<UserReadDto>> listarTodos(){
        List<User> users = userQueryService.listarTodos();
        return ResponseEntity.ok(UserReadDto.from(users));
    }
}