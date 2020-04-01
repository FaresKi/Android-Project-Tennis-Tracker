package android.restapi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatsDAO extends JpaRepository<PlayerStatsEntity,Integer> {
}
