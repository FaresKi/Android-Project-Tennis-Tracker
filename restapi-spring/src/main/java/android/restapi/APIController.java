package android.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class APIController {
    @Autowired
    PlayerDAO playerDAO;

    @Autowired
    MatchDAO matchDAO;

    @GetMapping(value = "/Users")
    public List<PlayersEntity> usersEntityList() {
        return playerDAO.findAll();
    }

    @GetMapping(value = "/Matches")
    public List<MatchEntity> matchEntityList() {
        return matchDAO.findAll();
    }
    
    @PostMapping(value = "/Matches")
    public void addMatch(MatchEntity match){
        matchDAO.saveAndFlush(match);
    }
}
