package br.com.solutions.pocketmonsters.pokemon.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Stats {
    @Id
    @GeneratedValue
    private Long id;
    private Double total;
    @OneToMany(mappedBy = "stats", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BaseStat> stats;
}
