package bts66.anthony.castlefightarena.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Combat {
    private final int id;
    private final Personnage gagnant;
    private final Personnage perdant;
    private final boolean egalite;
    private final int vieRestant;
    private final String date;
}
