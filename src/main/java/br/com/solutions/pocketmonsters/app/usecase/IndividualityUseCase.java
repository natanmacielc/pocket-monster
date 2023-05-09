package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.pokemon.individuality.Individuality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class IndividualityUseCase extends BaseUseCase<Individuality> {
    public IndividualityUseCase(EntityManager entityManager, JpaRepository<Individuality, Long> individualityRepository) {
        super(entityManager, Individuality.class, individualityRepository);
    }
}
