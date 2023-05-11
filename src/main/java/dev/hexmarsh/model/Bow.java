package dev.hexmarsh.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter @EqualsAndHashCode
@Document(collection = "weapons")
public class Bow {
    private String name;
    private AttackPower attackPower;
    private DamageReduction damageReduction;
    private Requirements requirements;
    private ParameterBonus parameterBonus;
    private int durability;
    private double weight;
    private int range;
    private String category;
}
