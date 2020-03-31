package android.restapi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameDAO extends JpaRepository<GameEntity,Integer> {
}
