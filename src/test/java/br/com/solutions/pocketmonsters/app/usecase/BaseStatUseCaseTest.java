package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.app.repository.BaseStatRepository;
import br.com.solutions.pocketmonsters.pokemon.skill.BaseStat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BaseStatUseCaseTest {

    private BaseStatUseCase useCase;

    @Mock
    private EntityManager entityManager;

    @Mock
    private BaseStatRepository repository;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        repository = mock(BaseStatRepository.class);
        useCase = new BaseStatUseCase(entityManager, repository);
    }

    @Test
    void testFindAll() {
        List<BaseStat> baseStatList = Arrays.asList(
                BaseStat.builder().id(1L).build(),
                BaseStat.builder().id(2L).build()
        );
        when(repository.findAll()).thenReturn(baseStatList);

        List<BaseStat> result = useCase.findAll();

        assertEquals(baseStatList, result);
    }

    @Test
    void testFindById() {
        BaseStat baseStat = BaseStat.builder().id(1L).build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(baseStat));

        BaseStat result = useCase.findById(baseStat.getId());

        assertEquals(baseStat, result);
    }

    @Test
    void testSave() {
        BaseStat baseStat = BaseStat.builder().id(1L).build();
        when(entityManager.createNativeQuery(anyString(), eq(BaseStat.class))).thenReturn(mock(Query.class));
        Query query = entityManager.createNativeQuery("", BaseStat.class);
        when(query.getSingleResult()).thenReturn(baseStat);

        BaseStat result = useCase.save(baseStat);

        assertEquals(baseStat, result);
    }

    @Test
    void testSaveAll() {
        List<BaseStat> baseStatList = Arrays.asList(
                BaseStat.builder().id(1L).build(),
                BaseStat.builder().id(2L).build()
        );
        when(entityManager.createNativeQuery(anyString(), eq(BaseStat.class))).thenReturn(mock(Query.class));

        Query query = entityManager.createNativeQuery("", BaseStat.class);
        when(query.getResultList()).thenReturn(baseStatList);

        List<BaseStat> result = useCase.saveAll(baseStatList);

        assertEquals(baseStatList, result);
    }

    @Test
    void testUpdate() {
        BaseStat baseStat = BaseStat.builder().id(1L).build();
        when(entityManager.createNativeQuery(anyString(), eq(BaseStat.class))).thenReturn(mock(Query.class));
        Query query = entityManager.createNativeQuery("", BaseStat.class);
        when(query.getSingleResult()).thenReturn(baseStat);

        BaseStat result = useCase.update( baseStat);

        assertEquals(baseStat, result);
    }
}
