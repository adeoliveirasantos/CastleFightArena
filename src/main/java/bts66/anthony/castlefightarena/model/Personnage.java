package bts66.anthony.castlefightarena.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Personnage {
    private final int id;
    private final TypePersonnage type;
    private final String nom;
    private final String description;
    private final int vie;
    private final int degats;
    private final Statistique statistique;
    @Setter private int vieCombat;
}
