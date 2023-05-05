package br.com.solutions.pocketmonsters.pokemon.skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BaseStat {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Integer effortValue;
    private Integer individualValue;
    private Double actualValue;
    private Double baseValue;
    private Integer stageMultiplier;
    private Boolean isLastModified;
    @ManyToOne
    @JoinColumn(name = "stats_id")
    private Stats stats;

    public enum Category {
        HP,
        ATTACK,
        SPECIAL_ATTACK,
        DEFENSE,
        SPECIAL_DEFENSE,
        SPEED
    }
}
