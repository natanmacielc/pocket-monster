package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.app.repository.IndividualityRepository;
import br.com.solutions.pocketmonsters.pokemon.individuality.Individuality;
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

class IndividualityUseCaseTest {
    private IndividualityUseCase useCase;

    @Mock
    private EntityManager entityManager;

    @Mock
    private IndividualityRepository repository;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        repository = mock(IndividualityRepository.class);
        useCase = new IndividualityUseCase(entityManager, repository);
    }

    @Test
    void testFindAll() {
        List<Individuality> individualities = Arrays.asList(
                Individuality.builder().id(1L).build(),
                Individuality.builder().id(2L).build()
        );
        when(repository.findAll()).thenReturn(individualities);

        List<Individuality> result = useCase.findAll();

        assertEquals(individualities, result);
    }

    @Test
    void testFindById() {
        Individuality individuality = Individuality.builder().id(1L).build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(individuality));

        Individuality result = useCase.findById(individuality.getId());

        assertEquals(individuality, result);
    }

    @Test
    void testSave() {
        Individuality individuality = Individuality.builder().id(1L).build();
        when(entityManager.createNativeQuery(anyString(), eq(Individuality.class))).thenReturn(mock(Query.class));
        Query query = entityManager.createNativeQuery("", Individuality.class);
        when(query.getSingleResult()).thenReturn(individuality);

        Individuality result = useCase.save(individuality);

        assertEquals(individuality, result);
    }

    @Test
    void testSaveAll() {
        List<Individuality> individualities = Arrays.asList(
                Individuality.builder().id(1L).build(),
                Individuality.builder().id(2L).build()
        );
        when(entityManager.createNativeQuery(anyString(), eq(Individuality.class))).thenReturn(mock(Query.class));

        Query query = entityManager.createNativeQuery("", Individuality.class);
        when(query.getResultList()).thenReturn(individualities);

        List<Individuality> result = useCase.saveAll(individualities);

        assertEquals(individualities, result);
    }

    @Test
    void testUpdate() {
        Individuality individuality = Individuality.builder().id(1L).build();
        when(entityManager.createNativeQuery(anyString(), eq(Individuality.class))).thenReturn(mock(Query.class));
        Query query = entityManager.createNativeQuery("", Individuality.class);
        when(query.getSingleResult()).thenReturn(individuality);

        Individuality result = useCase.update( individuality);

        assertEquals(individuality, result);
    }
}