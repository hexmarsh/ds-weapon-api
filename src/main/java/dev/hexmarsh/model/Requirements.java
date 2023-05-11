package dev.hexmarsh.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter @EqualsAndHashCode
public class Requirements {
    private int strength;
    private int dexterity;
    private int intelligence;
    private int faith;
}
