package org.nice.pokedexxfx.components;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

public class PokemonList extends ScrollPane {
    public PokemonList() {
        setContent(new Text("Pokemon list"));
    }
}
