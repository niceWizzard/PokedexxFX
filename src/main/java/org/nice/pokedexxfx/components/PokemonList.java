package org.nice.pokedexxfx.components;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import org.nice.pokedexxfx.components.reusable.DynamicVBox;
import org.nice.pokedexxfx.models.PokemonModel;
import org.nice.pokedexxfx.services.PokemonService;

import java.util.ArrayList;
import java.util.List;

public class PokemonList extends ScrollPane {
    public PokemonList() {
        var pokeList = new ArrayList<>(PokemonService.getInstance().filterPokemons(List.of()));

        var listView = new DynamicVBox<>(
            PokemonService.getInstance().filteredPokemonList,
            v -> String.valueOf(v.id()),
            v -> {
                return new Text(v.name());
            },
            new Text("Empty list")
        );

        setContent(listView);
    }
}
