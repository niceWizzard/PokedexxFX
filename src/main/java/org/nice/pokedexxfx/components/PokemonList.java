package org.nice.pokedexxfx.components;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.nice.pokedexxfx.components.reusable.DynamicVBox;
import org.nice.pokedexxfx.models.PokemonType;
import org.nice.pokedexxfx.services.PokemonImage;
import org.nice.pokedexxfx.services.PokemonService;
import org.nice.pokedexxfx.services.SearchService;
import rx.Observable;

import java.util.List;
import java.util.Optional;

public class PokemonList extends ScrollPane {
    public PokemonList() {

        var listView = new DynamicVBox<>(
                PokemonService.getInstance().filteredPokemonList,
                p -> String.valueOf(p.id()),
                p -> {
                    // main container
                    var listBox = new HBox();
                    listBox.setMinHeight(68);
                    listBox.setPrefHeight(68);
                    listBox.setMaxHeight(68);
                    listBox.setSpacing(10);
                    listBox.setPadding(new javafx.geometry.Insets(10));
                    listBox.setStyle("-fx-border-radius: 10;");

                    // id
                    var idPanel = new HBox();
                    var idLabel = new Label(String.valueOf(p.id()));
                    idLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                    // TODO: set color
                    listBox.getChildren().add(idLabel);

                    // name
                    var pokeName = new Label(p.name());
                    pokeName.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                    pokeName.setTextFill(Color.WHITE);
                    listBox.getChildren().add(pokeName);

                    // sprite
                    Image image = PokemonImage.getSprite(p);
                    var sprite = new ImageView(image);
                    sprite.setFitHeight(50);
                    sprite.setFitWidth(50);
                    listBox.getChildren().add(sprite);

                    listBox.setOnMouseClicked(tite -> {
                        PokemonService.getInstance().setCurrentPokemon(p);
                    });

                    return listBox;
                },
                new Text("No pokemons found ;("));

        setContent(listView);
        var service = SearchService.getInstance();

        Observable.combineLatest(service.onSearchStringChange(), service.onTypeFilterChange(), List::of)
                .subscribe(v -> {
                    if (v.get(0) instanceof List) {
                        Platform.runLater(
                                () -> PokemonService.getInstance().filterPokemons(List.of(), Optional.empty()));
                        var filters = (List<PokemonType>) v.get(1);
                        Platform.runLater(() -> PokemonService.getInstance().filterPokemons(filters,
                                Optional.of(v.get(0).toString())));
                    }
                });
    }

}
