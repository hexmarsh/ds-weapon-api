package dev.hexmarsh.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter @EqualsAndHashCode
public class AttackPower {
    private int physical;
    private int magical;
    private int fire;
    private int lightning;
    private int critical;
}
