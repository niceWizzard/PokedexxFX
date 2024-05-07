package org.nice.pokedexxfx.components.header;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SearchBar extends HBox {
    public SearchBar() {
        var field = new TextField();
        field.setPromptText("Search pokemons...");
        var searchBtn = new Button("Search");
        getChildren().addAll(field, searchBtn);
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(6);
        setHgrow(field, Priority.ALWAYS);
        searchBtn.setOnAction(e -> {});
    }
}
