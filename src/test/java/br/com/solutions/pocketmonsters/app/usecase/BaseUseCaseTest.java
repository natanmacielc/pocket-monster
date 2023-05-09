package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.app.exception.NotFoundException;
import br.com.solutions.pocketmonsters.pokemon.skill.Stats;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
abstract class BaseUseCaseTest<T> {

    static BaseUseCase useCase;

    @Mock
    static JpaRepository repository;
    @Mock
    static EntityManager entityManager;
    static Class<?> clazz;

    abstract T createEntity();
    abstract List<T> createEntityList();

    @Test
    void testFindAll() {
        List<T> entityList = createEntityList();
        when(repository.findAll()).thenReturn(entityList);

        List<T> result = useCase.findAll();

        assertEquals(entityList, result);
    }

    @Test
    void testFindById() {
        T entity = createEntity();
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        Object result = useCase.findById(id);

        assertEquals(entity, result);
    }

    @Test
    void testFindByIdNotFound() {
        Long id = 999L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.findById(id));
        assertEquals(String.format("Registro com o id %d não encontrado.", id), exception.getMessage());
    }

    @Test
    void testSave() {
        T entity = createEntity();
        Query query = entityManager.createNativeQuery("", clazz);
        when(query.getSingleResult()).thenReturn(entity);

        Object result = useCase.save(entity);

        assertEquals(entity, result);
    }

    @Test
    void testSaveAll() {
        List<T> entityList = createEntityList();
        Query query = entityManager.createNativeQuery("", clazz);
        when(query.getResultList()).thenReturn(entityList);

        List<Stats> result = useCase.saveAll(entityList);

        assertEquals(entityList, result);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        T entity = createEntity();
        when(repository.existsById(id)).thenReturn(true);
        Query query = entityManager.createNativeQuery("", clazz);
        when(query.getSingleResult()).thenReturn(entity);

        Object result = useCase.update(entity, id);

        assertEquals(entity, result);
    }

    @Test
    void testUpdateNotFound() {
        Long id = 999L;
        T entity = createEntity();

        NotFoundException exception = assertThrows(
                NotFoundException.class, () -> useCase.update(entity, id)
        );
        assertEquals(String.format("Registro com o id %d não encontrado.", id), exception.getMessage());
    }
}
