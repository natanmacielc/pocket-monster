package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.pokemon.skill.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class StatsUseCase extends BaseUseCase<Stats> {
    public StatsUseCase(EntityManager entityManager, JpaRepository<Stats, Long> statsRepository) {
        super(entityManager, Stats.class, statsRepository);
    }
}
