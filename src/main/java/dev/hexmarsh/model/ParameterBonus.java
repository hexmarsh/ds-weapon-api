package dev.hexmarsh.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter @EqualsAndHashCode
public class ParameterBonus {
    private String strength;
    private String dexterity;
    private String intelligence;
    private String faith;
}
