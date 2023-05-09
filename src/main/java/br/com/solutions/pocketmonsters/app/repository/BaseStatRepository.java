package br.com.solutions.pocketmonsters.app.repository;

import br.com.solutions.pocketmonsters.pokemon.skill.BaseStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseStatRepository extends JpaRepository<BaseStat, Long> {
}
