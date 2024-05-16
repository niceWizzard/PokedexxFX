package org.nice.pokedexxfx.components;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
                    var listBox = new GridPane();
                    listBox.setMinHeight(68);
                    listBox.setPrefHeight(68);
                    listBox.setMaxHeight(68);
                    listBox.setPadding(new javafx.geometry.Insets(10));
                    listBox.setStyle(
                            "-fx-border-radius: 10;-fx-background-color: " + "-" + p.type().get(0) + ";"
                                    + "-fx-margin: 10px; -fx-border-color: white; -fx-border: 1px; -fx-border-radius: 10px;-fx-background-radius: 10px;");

                    // id
                    var idPanel = new HBox();
                    idPanel.setStyle(
                            "-fx-background-color: #ffffff; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                    idPanel.setMinSize(50, 28);
                    idPanel.setMaxSize(50, 28);
                    idPanel.setAlignment(javafx.geometry.Pos.CENTER);
                    var idLabel = new Label(String.valueOf(p.id()));
                    idLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                    idLabel.setStyle("-fx-text-fill: " + "-" + p.type().get(0) + ";");
                    idPanel.getChildren().add(idLabel);
                    listBox.add(idPanel, 0, 0);

                    // name
                    var pokeName = new Label(p.name());
                    pokeName.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                    pokeName.setTextFill(Color.WHITE);
                    listBox.add(pokeName, 1, 0);

                    //
                    var Psprite = new VBox();
                    Psprite.setMinSize(60, 60);
                    Psprite.setMaxSize(60, 60);
                    Psprite.setAlignment(Pos.CENTER);
                    Psprite.setStyle(
                            "-fx-background-color: #ffffff; -fx-border-radius: 20px;-fx-background-radius: 60px;");

                    Image image = PokemonImage.getSprite(p);
                    var sprite = new ImageView(image);
                    sprite.setFitHeight(50);
                    sprite.setFitWidth(50);
                    Psprite.getChildren().add(sprite);
                    listBox.add(Psprite, 2, 0);

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
                    Platform.runLater(
                            () -> PokemonService.getInstance().filterPokemons(List.of(), Optional.empty()));
                    var filters = (List<PokemonType>) v.get(1);
                    Platform.runLater(() -> PokemonService.getInstance().filterPokemons(filters,
                            Optional.of(v.get(0).toString())));
                });
    }

}
