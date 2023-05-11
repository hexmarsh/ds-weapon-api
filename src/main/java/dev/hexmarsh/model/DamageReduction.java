package dev.hexmarsh.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter @EqualsAndHashCode
public class DamageReduction {
    private int physical;
    private int magic;
    private int fire;
    private int lightning;
    private int stability;
}
