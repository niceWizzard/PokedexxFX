package org.nice.pokedexxfx.components;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class PokemonDetails extends GridPane {
    public PokemonDetails() {
        getChildren().add(new Text("Pokemon Details"));
    }
}
