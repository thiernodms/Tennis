package com.thierno.tennis.web;

import com.thierno.tennis.data.PlayerList;
import com.thierno.tennis.service.PlayerNotFoundException;
import com.thierno.tennis.service.PlayerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlayerController.class) //Permet de charger le contexte d'application sans charger le serveur d'application
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc; // fourni des utilitaire pour pouvoir declencher des requêtes

    @MockBean //permet de remplacer l'injection par une instance mockito
    private PlayerService playerService;
    @Test
    public void shouldListAllPlayers() throws Exception {
        //Given
        Mockito.when(playerService.getAllPlayers()).thenReturn(PlayerList.ALL);

        //When /Then
        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].lastName", CoreMatchers.is("Nadal")))
                .andExpect(jsonPath("$[1].lastName", CoreMatchers.is("Djokovic")))
                .andExpect(jsonPath("$[2].lastName", CoreMatchers.is("Federer")))
                .andExpect(jsonPath("$[3].lastName", CoreMatchers.is("Murray")));


    }

    @Test
    public void shouldRetrievePlayer() throws Exception {
        //Given
        String playerToRetrieve = "nadal";
        Mockito.when(playerService.getByLastName(playerToRetrieve)).thenReturn(PlayerList.RAFAEL_NADAL);

        //When / Then
        mockMvc.perform(get("/players/nadal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nadal")))
                .andExpect(jsonPath("$.rank.position", CoreMatchers.is(1)));
    }

    @Test
    public void shoulReturn404NotFound_WhePlayerDoesNoExist() throws Exception{
        //Given
        String playerToRetrieve = "doe";
        Mockito.when(playerService.getByLastName(playerToRetrieve)).thenThrow(new PlayerNotFoundException("player doe does not exist"));

        // When / Then
        mockMvc.perform(get("/players/doe"))
                .andExpect(status().isNotFound());
    }

}
