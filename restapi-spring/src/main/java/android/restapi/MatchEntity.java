package android.restapi;

import javax.persistence.*;

@Entity
@Table(name = "Game")
public class MatchEntity {
    @Id
    @GeneratedValue
    private int id;
    
    private String firstPlayer;
    private String secondPlayer;

    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    @Basic
    @Column(name = "first_player")
    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    @Basic
    @Column(name = "second_player")
    public String getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchEntity that = (MatchEntity) o;

        if (id != that.id) return false;
        if (firstPlayer != null ? !firstPlayer.equals(that.firstPlayer) : that.firstPlayer != null) return false;
        if (secondPlayer != null ? !secondPlayer.equals(that.secondPlayer) : that.secondPlayer != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstPlayer != null ? firstPlayer.hashCode() : 0);
        result = 31 * result + (secondPlayer != null ? secondPlayer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MatchEntity{" +
                "id=" + id +
                ", firstPlayer='" + firstPlayer + '\'' +
                ", secondPlayer='" + secondPlayer + '\'' +
                '}';
    }
}
