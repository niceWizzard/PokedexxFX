package org.nice.pokedexxfx.components;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.nice.pokedexxfx.components.reusable.StatBar;

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
        r1.setVgrow(Priority.ALWAYS);
        r2.setVgrow(Priority.ALWAYS);
        getRowConstraints().addAll(r1, r2);

        add(pokemonList, 0, 0, 1, 2);
        add(pokemonDetails, 1, 0);
        add(pokemonTabs, 1, 1);

    }
}
