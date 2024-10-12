package com.thierno.tennis.web;

import com.thierno.tennis.*;
import com.thierno.tennis.Error;
import com.thierno.tennis.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "tennis players API")
@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Operation(summary = "Finds players", description = "Finds players")
    @ApiResponses( value = {
            //Documentation des differentes reponses possible
            @ApiResponse(responseCode = "200", description = "Players list",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Player.class)))}),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action ")
    })
    @GetMapping
    public List<Player> list(){
        return playerService.getAllPlayers();
    }

    @Operation(summary = "Finds a player", description = "Finds a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action ")

    })
    @GetMapping("{lastName}")
    public Player getByLastName(@PathVariable("lastName") String lastName){
        return playerService.getByLastName(lastName);
    }

    @Operation(summary = "Create a player", description = "Create a player")
    @ApiResponses( value = {
            //Documentation des differentes reponses possible
            @ApiResponse(responseCode = "200", description = "Created Player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "400", description = "Player with specified last name already exists.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Player.class))}),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action ")

    })
    @PostMapping
    public Player createPlayer(@Valid @RequestBody PlayerToSave playerToSave){
        return playerService.create(playerToSave);
    }

    @Operation(summary = "Update a player", description = "Update a player")
    @ApiResponses( value = {
            //Documentation des differentes reponses possible
            @ApiResponse(responseCode = "200", description = "Updated Player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlayerToSave.class))}),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action ")
    })
    @PutMapping
    public Player updatePlayer(@Valid @RequestBody PlayerToSave playerToSave){
        return playerService.update(playerToSave);
    }

    @Operation(summary = "Delete a player", description = "Delete a player")
    @ApiResponses( value = {
            //Documentation des differentes reponses possible
            @ApiResponse(responseCode = "200", description = "Player has been deleted"),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class))}),
            @ApiResponse(responseCode = "403", description = "This user is not authorized to perform this action ")
    })
    @DeleteMapping("{lastName}")
    public void deletePlayersByLastName(@PathVariable("lastName") String lastName){
        playerService.delete(lastName);

    }

}
