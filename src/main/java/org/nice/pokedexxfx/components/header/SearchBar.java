package org.nice.pokedexxfx.components.header;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.nice.pokedexxfx.services.SearchService;
import rx.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

public class SearchBar extends HBox {
    public SearchBar() {
        var field = new TextField();
        field.setPromptText("Search pokemons...");
        field.setStyle(
                "-fx-background-radius: 10;-fx-focus-color: transparent; -fx-faint-focus-color: transparent;-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 5, 0, 0, 5);");
        var searchBtn = new Button("🔍");
        searchBtn.setStyle(
                "-fx-background-color: white; -fx-background-radius: 100;-fx-focus-color: transparent; -fx-faint-focus-color: transparent;-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 5, 0, 0, 5);");
        getChildren().addAll(field, searchBtn);
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(6);
        setHgrow(field, Priority.ALWAYS);
        searchBtn.setOnAction(e -> {
        });
        PublishSubject<String> debounce = PublishSubject.create();
        debounce.debounce(500, TimeUnit.MILLISECONDS).subscribe(this::onSubmit);
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            debounce.onNext(newValue);
        });
    }

    private void onSubmit(String s) {
        SearchService.getInstance().setSearchString(s.trim());
    }
}
