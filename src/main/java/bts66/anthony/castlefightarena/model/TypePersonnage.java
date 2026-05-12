package bts66.anthony.castlefightarena.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypePersonnage {
    ELFE("file:src/main/resources/bts66/anthony/castlefightarena/images/elfe.png"),
    GUERRIER("file:src/main/resources/bts66/anthony/castlefightarena/images/guerrier.png"),
    NAIN("file:src/main/resources/bts66/anthony/castlefightarena/images/nain.png"),
    SORCIERE("file:src/main/resources/bts66/anthony/castlefightarena/images/sorciere.png");

    private final String image;
}
