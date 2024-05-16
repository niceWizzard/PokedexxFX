package org.nice.pokedexxfx.components.header;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class Header extends HBox {
    public Header() {
        var container = new HBox();
        var filler = new HBox(); // Change filler to HBox to set its width
        var searchBar = new SearchBar();
        var filterBtn = new FilterBtn();

        container.prefWidthProperty().bind(this.widthProperty().multiply(0.45)); // container takes 40% of the width
        filler.prefWidthProperty().bind(this.widthProperty().multiply(0.51)); // filler takes 60% of the width
        container.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox.setHgrow(searchBar, Priority.ALWAYS);
        container.getChildren().addAll(searchBar, filterBtn);
        container.setSpacing(10);
        getChildren().addAll(container, filler);
    }
}