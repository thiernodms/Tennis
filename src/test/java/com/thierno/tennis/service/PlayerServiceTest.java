package com.thierno.tennis.service;

import com.thierno.tennis.Player;
import com.thierno.tennis.data.PlayerEntityList;
import com.thierno.tennis.data.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;
    private PlayerService playerService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository);
    }
    @Test
    public void shouldReturnPlayersRanking(){
        //Given
        Mockito.when(playerRepository.findAll()).thenReturn(PlayerEntityList.ALL);

        //When
        List<Player> allPlayers = playerService.getAllPlayers();

        //Then
        Assertions.assertThat(allPlayers)
                .extracting("lastName")
                .containsExactly("Nadal", "Djokovic", "Federer", "Murray");
    }

    @Test
    public void shouldRetrievePlayer(){
        //Given
        String playerToRetrieve = "nadal";
        Mockito.when(playerRepository.findOneByLastNameIgnoreCase(playerToRetrieve)).thenReturn(Optional.of(PlayerEntityList.RAFAEL_NADAL));

        //When
        Player retrievedPlayer = playerService.getByLastName(playerToRetrieve);

        //Then
        Assertions.assertThat(retrievedPlayer.lastName()).isEqualTo("Nadal");
        Assertions.assertThat(retrievedPlayer.firstName()).isEqualTo("Rafael");
        Assertions.assertThat(retrievedPlayer.rank().position()).isEqualTo(1);

    }

    @Test
    public void shouldFailToRetrieve_WhenPlayerDoesNotExist(){
        //Given
        String unknownPlayer = "doe";
        Mockito.when(playerRepository.findOneByLastNameIgnoreCase(unknownPlayer)).thenReturn(Optional.empty());

        // When / Then
        Exception exception = assertThrows(PlayerNotFoundException.class, () -> {
            playerService.getByLastName(unknownPlayer);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Player with last name doe could not be found.");

    }



}
