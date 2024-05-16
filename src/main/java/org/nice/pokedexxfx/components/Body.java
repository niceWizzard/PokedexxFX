package org.nice.pokedexxfx.components;

import javafx.scene.layout.*;

public class Body extends GridPane {
    public Body() {

        var pokemonList = new PokemonList();
        var pokemonDetails = new PokemonDetails();
        var pokemonTabs = new PokemonTabs();

        var c1 = new ColumnConstraints();
        c1.setPercentWidth(40);
        var c2 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        c2.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(c1, c2);

        var r1 = new RowConstraints();
        var r2 = new RowConstraints();
        r1.setPercentHeight(50);
        r2.setPercentHeight(50);
        getRowConstraints().addAll(r1, r2);

        add(pokemonList, 0, 0, 1, 2);
        add(pokemonDetails, 1, 0);
        add(pokemonTabs, 1, 1);
    }
}