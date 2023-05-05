package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.app.repository.BaseStatRepository;
import br.com.solutions.pocketmonsters.pokemon.skill.BaseStat;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class BaseStatUseCase extends BaseUseCase<BaseStat> {
    public BaseStatUseCase(EntityManager entityManager, BaseStatRepository baseStatRepository) {
        super(entityManager, BaseStat.class, baseStatRepository);
    }
}
