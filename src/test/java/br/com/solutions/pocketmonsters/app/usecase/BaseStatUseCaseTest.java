package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.app.repository.BaseStatRepository;
import br.com.solutions.pocketmonsters.pokemon.skill.BaseStat;
import org.junit.jupiter.api.BeforeAll;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BaseStatUseCaseTest extends BaseUseCaseTest<BaseStat> {
    @Override
    BaseStat createEntity() {
        return BaseStat.builder().id(1L).build();
    }

    @Override
    List<BaseStat> createEntityList() {
        return List.of(
                BaseStat.builder().id(1L).build(),
                BaseStat.builder().id(2L).build()
        );
    }

    @BeforeAll
    static void setUp() {
        entityManager = mock(EntityManager.class);
        repository = mock(BaseStatRepository.class);
        useCase = new BaseStatUseCase(entityManager, repository);
        clazz = BaseStat.class;
        when(entityManager.createNativeQuery(anyString(), eq(clazz))).thenReturn(mock(Query.class));
    }
}
