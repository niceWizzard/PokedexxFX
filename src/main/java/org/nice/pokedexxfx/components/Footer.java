package org.nice.pokedexxfx.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.nice.pokedexxfx.Utils;

public class Footer extends HBox {
    public Footer() {
        var pokeball = Utils.getImageView("/images/items/4.png");
        pokeball.setFitHeight(32);
        pokeball.setFitWidth(32);

        var pokeText = new Label();
        pokeText.setText("Pokedexx");
        pokeText.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: Verdana;");
        getChildren().addAll(
                pokeball,
                pokeText);
    }
}