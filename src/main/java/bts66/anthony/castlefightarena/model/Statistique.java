package bts66.anthony.castlefightarena.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class Statistique {
    private final int id;
    private float winRate;
    private int victoires;
    private int defaites;
    private Date dernierCombat;
}
