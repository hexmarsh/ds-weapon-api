package dev.hexmarsh.model;


import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter @EqualsAndHashCode @AllArgsConstructor
@Document(collection = "weapons")
public class Weapon {
    private String name;
    private AttackPower attackPower;
    private DamageReduction damageReduction;
    private Requirements requirements;
    private ParameterBonus parameterBonus;
    private int durability;
    private double weight;
    private String attackType;
    private String weaponType;
}
