package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.app.repository.StatsRepository;
import br.com.solutions.pocketmonsters.pokemon.skill.Stats;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class StatsUseCase extends BaseUseCase<Stats> {
    public StatsUseCase(EntityManager entityManager, StatsRepository statsRepository) {
        super(entityManager, Stats.class, statsRepository);
    }
}
