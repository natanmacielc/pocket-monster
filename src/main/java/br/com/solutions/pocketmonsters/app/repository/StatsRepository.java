package br.com.solutions.pocketmonsters.app.repository;

import br.com.solutions.pocketmonsters.pokemon.skill.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {
}
