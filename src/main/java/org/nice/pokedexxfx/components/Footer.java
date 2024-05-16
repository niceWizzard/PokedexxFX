package org.nice.pokedexxfx.components;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.nice.pokedexxfx.Utils;

public class Footer extends HBox {
    public Footer() {
        var pokeball = Utils.getImageView("/images/items/4.png");
        pokeball.setFitHeight(25);
        pokeball.setFitWidth(32);
        getChildren().addAll(
                pokeball,
                new Text("Pokedexx"));
    }
}
