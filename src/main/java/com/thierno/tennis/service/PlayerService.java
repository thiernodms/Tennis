package com.thierno.tennis.service;

import com.thierno.tennis.Player;
import com.thierno.tennis.PlayerToSave;
import com.thierno.tennis.Rank;
import com.thierno.tennis.data.PlayerEntity;
import com.thierno.tennis.data.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers(){
        return playerRepository.findAll().stream()
                .map(player -> new Player(player.getFirstName(),
                        player.getLastName(),
                        player.getBirthDate(),
                        new Rank(player.getRank(), player.getPoints())
                ))
                .sorted(Comparator.comparing(player -> player.rank().position()))
                .collect(Collectors.toList());
    }


    public Player getByLastName(String lastName){
        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(lastName);

        if (player.isEmpty()){
            throw new PlayerNotFoundException(lastName);
        }

        PlayerEntity playerEntity = player.get();
        return new Player(playerEntity.getFirstName(),
                playerEntity.getLastName(),
                playerEntity.getBirthDate(),
                new Rank(player.get().getRank(), player.get().getPoints())
        );
    }

    public Player create(PlayerToSave playerToSave){

       Optional<PlayerEntity> playerToCreate = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
       if (playerToCreate.isPresent()){
           throw new PlayerAlreadyExistsException(playerToSave.lastName());

       }
        PlayerEntity playerToRegister = new PlayerEntity(
                playerToSave.lastName(),
                playerToSave.firstName(),
                playerToSave.birthDate(),
                playerToSave.points(),
                999999999);



        PlayerEntity registeredPlayer = playerRepository.save(playerToRegister);

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> newRanking = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(newRanking);
        return getByLastName(registeredPlayer.getLastName());
    }


    public Player update(PlayerToSave playerToSave){
        Optional<PlayerEntity> playerToUpdate = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
        if (playerToUpdate.isEmpty()){
            throw new PlayerNotFoundException(playerToSave.lastName());

        }

        playerToUpdate.get().setFirstName(playerToSave.firstName());
        playerToUpdate.get().setBirthDate(playerToSave.birthDate());
        playerToUpdate.get().setPoints(playerToSave.points());

        PlayerEntity updatedPlayer = playerRepository.save(playerToUpdate.get());

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> newRanking = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(newRanking);

        return getByLastName(updatedPlayer.getLastName());
    }

    public void delete(String lastName) {
        Optional<PlayerEntity> playerToDelete = playerRepository.findOneByLastNameIgnoreCase(lastName);
        if (playerToDelete.isEmpty()){
            throw new PlayerNotFoundException(lastName);
        }

        playerRepository.delete(playerToDelete.get());

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> newRanking = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(newRanking);
    }

}
