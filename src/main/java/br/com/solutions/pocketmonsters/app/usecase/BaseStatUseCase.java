package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.pokemon.skill.BaseStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class BaseStatUseCase extends BaseUseCase<BaseStat> {
    public BaseStatUseCase(EntityManager entityManager, JpaRepository<BaseStat, Long> baseStatRepository) {
        super(entityManager, BaseStat.class, baseStatRepository);
    }
}
