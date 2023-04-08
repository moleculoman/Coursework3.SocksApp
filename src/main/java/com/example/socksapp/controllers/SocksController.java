package com.example.socksapp.controllers;

import com.example.socksapp.models.Socks;
import com.example.socksapp.models.Size;
import com.example.socksapp.models.Color;
import com.example.socksapp.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "Контроллер для учета носков", description = "CRUD-операции для взаимодействия с носками")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping
    @Operation(
            summary = "Поступление носков",
            description = "Регистрация новой партии"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Партия успешно добавлена",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос. Проверьте атрибутивный состав",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Сервис не отвечает",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )

    })
    public ResponseEntity<Socks> createSocks(@RequestBody Socks socks, @RequestParam long quantity) {
        Socks createdSocks = socksService.addSocks(socks, quantity);
        return ResponseEntity.ok(createdSocks);
    }

    @PutMapping
    @Operation(
            summary = "Отпуск носков",
            description = "Отпуск носков со склада"
    )
    @Parameters(value = {
            @Parameter(name = "quantity", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Партия успешно продана и списана со склада",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Запрашиваемая партия отсутствует.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Сервис не отвечает",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Socks> editSocks(@RequestBody Socks socks,
                                           @RequestParam long quantity) {
        Socks socks1 = socksService.editSocks(socks, quantity);
        if (socks1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(socks1);
    }

    @GetMapping
    @Operation(
            summary = "Поиск носков по составу хлопка",
            description = "Поиск происходит по составу"
    )
    @Parameters(
            value = {
                    @Parameter(
                            name = "Мин. кол-во хлопка", example = "0"
                    ),
                    @Parameter(
                            name = "Макс. кол-во хлопка", example = "100"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Товар, соответствующий запросу найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Товар под запрашиваемые параметры не найден. Проверьте запрос",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Сервис не отвечает",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Long> getSocksNumByParam(@RequestParam Color color,
                                                   @RequestParam Size size,
                                                   @RequestParam int cottonMin,
                                                   @RequestParam int cottonMax) {
        long count = socksService.getSocksNumByParam(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping
    @Operation(
            summary = "Списание носков",
            description = "Сервис для списания бракованных носков"
    )
    @Parameters(value = {
            @Parameter(name = "quantity", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Бракованные носки успешно списаны со склада",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Носки не найдены / Некорректный запрос",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Сервис не отвечает",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteSocks(@RequestBody Socks socks, @RequestParam long quantity) {
        if (socksService.deleteSocks(socks, quantity)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
