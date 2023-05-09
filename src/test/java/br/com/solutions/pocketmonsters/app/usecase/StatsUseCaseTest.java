package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.app.repository.StatsRepository;
import br.com.solutions.pocketmonsters.pokemon.skill.Stats;
import org.junit.jupiter.api.BeforeAll;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatsUseCaseTest extends BaseUseCaseTest<Stats> {
    @Override
    Stats createEntity() {
        return Stats.builder().id(1L).build();
    }

    @Override
    List<Stats> createEntityList() {
        return List.of(
                Stats.builder().id(1L).build(),
                Stats.builder().id(2L).build()
        );
    }

    @BeforeAll
    static void setUp() {
        entityManager = mock(EntityManager.class);
        repository = mock(StatsRepository.class);
        useCase = new StatsUseCase(entityManager, repository);
        clazz = Stats.class;
        when(entityManager.createNativeQuery(anyString(), eq(clazz))).thenReturn(mock(Query.class));
    }
}
