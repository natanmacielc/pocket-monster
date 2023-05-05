package br.com.solutions.pocketmonsters.app.repository;

import br.com.solutions.pocketmonsters.pokemon.individuality.Individuality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualityRepository extends JpaRepository<Individuality, Long> {
}
