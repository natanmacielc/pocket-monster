package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.app.repository.StatsRepository;
import br.com.solutions.pocketmonsters.pokemon.skill.Stats;
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

class StatsUseCaseTest {
    private StatsUseCase useCase;

    @Mock
    private EntityManager entityManager;

    @Mock
    private StatsRepository repository;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        repository = mock(StatsRepository.class);
        useCase = new StatsUseCase(entityManager, repository);
    }

    @Test
    void testFindAll() {
        List<Stats> statsList = Arrays.asList(
                Stats.builder().id(1L).build(),
                Stats.builder().id(2L).build()
        );
        when(repository.findAll()).thenReturn(statsList);

        List<Stats> result = useCase.findAll();

        assertEquals(statsList, result);
    }

    @Test
    void testFindById() {
        Stats stats = Stats.builder().id(1L).build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(stats));

        Stats result = useCase.findById(stats.getId());

        assertEquals(stats, result);
    }

    @Test
    void testSave() {
        Stats stats = Stats.builder().id(1L).build();
        when(entityManager.createNativeQuery(anyString(), eq(Stats.class))).thenReturn(mock(Query.class));
        Query query = entityManager.createNativeQuery("", Stats.class);
        when(query.getSingleResult()).thenReturn(stats);

        Stats result = useCase.save(stats);

        assertEquals(stats, result);
    }

    @Test
    void testSaveAll() {
        List<Stats> statsList = Arrays.asList(
                Stats.builder().id(1L).build(),
                Stats.builder().id(2L).build()
        );
        when(entityManager.createNativeQuery(anyString(), eq(Stats.class))).thenReturn(mock(Query.class));

        Query query = entityManager.createNativeQuery("", Stats.class);
        when(query.getResultList()).thenReturn(statsList);

        List<Stats> result = useCase.saveAll(statsList);

        assertEquals(statsList, result);
    }

    @Test
    void testUpdate() {
        Stats stats = Stats.builder().id(1L).build();
        when(entityManager.createNativeQuery(anyString(), eq(Stats.class))).thenReturn(mock(Query.class));
        Query query = entityManager.createNativeQuery("", Stats.class);
        when(query.getSingleResult()).thenReturn(stats);

        Stats result = useCase.update( stats);

        assertEquals(stats, result);
    }
}