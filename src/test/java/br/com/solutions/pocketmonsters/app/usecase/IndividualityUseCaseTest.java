package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.app.repository.IndividualityRepository;
import br.com.solutions.pocketmonsters.pokemon.individuality.Individuality;
import org.junit.jupiter.api.BeforeAll;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndividualityUseCaseTest extends BaseUseCaseTest<Individuality> {
    @Override
    Individuality createEntity() {
        return Individuality.builder().id(1L).build();
    }

    @Override
    List<Individuality> createEntityList() {
        return List.of(
                Individuality.builder().id(1L).build(),
                Individuality.builder().id(2L).build()
        );
    }

    @BeforeAll
    static void setUp() {
        entityManager = mock(EntityManager.class);
        repository = mock(IndividualityRepository.class);
        useCase = new IndividualityUseCase(entityManager, repository);
        clazz = Individuality.class;
        when(entityManager.createNativeQuery(anyString(), eq(clazz))).thenReturn(mock(Query.class));
    }
}
