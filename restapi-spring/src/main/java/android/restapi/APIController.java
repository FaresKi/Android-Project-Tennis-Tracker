package android.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class APIController {
    @Autowired
    PlayerDAO playerDAO;

    @Autowired
    GameDAO gameDAO;
    
    @Autowired
    StatsDAO statsDAO;

    @GetMapping(value = "/Players")
    public List<PlayersEntity> playersEntityList() {
        return playerDAO.findAll();
    }

    @GetMapping(value = "/Games")
    public List<GameEntity> matchEntityList() {
        return gameDAO.findAll();
    }
    
    @PostMapping(value = "/Games", consumes = "application/json", produces="application/json")
    public ResponseEntity<Void> addGame(@RequestBody GameEntity gameEntity, UriComponentsBuilder ucBuilder){
        gameDAO.saveAndFlush(gameEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Games/{id}").buildAndExpand(gameEntity.getGameId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PostMapping(value = "/Players", consumes = "application/json", produces="application/json")
    public ResponseEntity<Void> addPlayer(@RequestBody PlayersEntity playersEntity, UriComponentsBuilder ucBuilder){
        playerDAO.saveAndFlush(playersEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Players/{id}").buildAndExpand(playersEntity.getPlayerId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PostMapping(value = "/Stats", consumes = "application/json", produces="application/json")
    public ResponseEntity<Void> addStats(@RequestBody PlayerStatsEntity playerStatsEntity, UriComponentsBuilder ucBuilder){
        statsDAO.saveAndFlush(playerStatsEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Stats/{id}").buildAndExpand(playerStatsEntity.getPlayerId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
    
}
