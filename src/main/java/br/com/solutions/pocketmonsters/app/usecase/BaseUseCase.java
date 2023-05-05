package br.com.solutions.pocketmonsters.app.usecase;

import br.com.solutions.pocketmonsters.utils.QueryBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

public abstract class BaseUseCase<T> {
    private final EntityManager entityManager;
    private final JpaRepository<T, Long> repository;
    private final Class<T> clazz;

    public BaseUseCase(EntityManager entityManager, Class<T> clazz, JpaRepository repository) {
        this.entityManager = entityManager;
        this.clazz = clazz;
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(Long id) {
        try {
            return repository.findById(id).orElseThrow(() -> new RuntimeException("Não existe esse objeto na base de dados"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public T save(T object) {
        QueryBuilder queryBuilder = QueryBuilder.insertQuery(object);
        Query query = entityManager.createNativeQuery(
                queryBuilder.getQuery(),
                clazz
        );
        queryBuilder.insertParameters(query);
        prePersist(object);
        return (T) query.getSingleResult();
    }

    @Transactional
    public List<T> saveAll(List<T> objects) {
        QueryBuilder queryBuilder = QueryBuilder.insertAllQuery(objects);
        Query query = entityManager.createNativeQuery(
                queryBuilder.getQuery(),
                clazz
        );
        queryBuilder.insertParameters(query);
        prePersist(objects.get(0));
        return (List<T>) query.getResultList();
    }

    @Transactional
    public T update(T object) {
        QueryBuilder queryBuilder = QueryBuilder.updateQuery(object);
        Query query = entityManager.createNativeQuery(
                queryBuilder.getQuery(),
                clazz
        );
        queryBuilder.updateParameters(query);
        preUpdate(object);
        return (T) query.getSingleResult();
    }

    protected void prePersist(T object) {}

    protected void preUpdate(T object) {}
}
