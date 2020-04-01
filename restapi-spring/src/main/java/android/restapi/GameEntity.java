package android.restapi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Game", schema = "statistics")
public class GameEntity {
    private int gameId;
    private int firstPlayer;
    private int secondPlayer;

    @Column(name = "first_player")
    public int getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(int firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    @Column(name = "second_player")
    public int getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(int secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    @Id
    @Column(name = "game_id")
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int id) {
        this.gameId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEntity that = (GameEntity) o;
        return gameId == that.gameId &&
                firstPlayer == that.firstPlayer &&
                secondPlayer == that.secondPlayer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, firstPlayer, secondPlayer);
    }
}
