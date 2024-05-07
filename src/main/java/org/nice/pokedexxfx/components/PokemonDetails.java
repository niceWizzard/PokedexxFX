package org.nice.pokedexxfx.components;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.nice.pokedexxfx.services.PokemonService;

public class PokemonDetails extends GridPane {
    public PokemonDetails() {
        var name = new Text("NAme");
        getChildren().add(name);
        PokemonService.getInstance().onCurrentPokemon().subscribe(v -> {
           name.setText(v.name());
        });
    }
}
