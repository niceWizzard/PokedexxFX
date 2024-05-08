package org.nice.pokedexxfx.components;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import org.nice.pokedexxfx.components.reusable.DynamicVBox;
import org.nice.pokedexxfx.models.PokemonModel;
import org.nice.pokedexxfx.models.PokemonType;
import org.nice.pokedexxfx.services.PokemonService;
import org.nice.pokedexxfx.services.SearchService;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PokemonList extends ScrollPane  {
    public PokemonList() {

        var listView = new DynamicVBox<>(
            PokemonService.getInstance().filteredPokemonList,
            pokemon -> String.valueOf(pokemon.id()),
            pokemon -> {
                var t = new Text(pokemon.name());
                t.setOnMouseClicked(_ -> {
                    PokemonService.getInstance().setCurrentPokemon(pokemon);
                });
                return t;
            },
            new Text("Empty list")
        );

        setContent(listView);
        var service = SearchService.getInstance();

        Observable.combineLatest(service.onSearchStringChange(), service.onTypeFilterChange(), List::of).subscribe(v -> {
            var filters = (List<PokemonType>)v.get(1);
            Platform.runLater(() -> PokemonService.getInstance().filterPokemons(filters, Optional.of(v.get(0).toString())));
        });
    }


}
