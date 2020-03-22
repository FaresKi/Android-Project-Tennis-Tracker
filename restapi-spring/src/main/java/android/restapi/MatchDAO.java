package android.restapi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchDAO extends JpaRepository<MatchEntity,Integer> {
}
