package org.nice.pokedexxfx.components.header;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class Header extends HBox {
    public Header() {
        var text = new Text("PokedexxFX");
        var searchBar = new SearchBar();
        var filterBtn = new FilterBtn();

        setSpacing(12);
        setHgrow(searchBar, Priority.ALWAYS);
        getChildren().addAll(
                text,
                searchBar,
                filterBtn
        );
    }
}
