package org.nice.pokedexxfx;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.nice.pokedexxfx.components.Body;
import org.nice.pokedexxfx.components.Footer;
import org.nice.pokedexxfx.components.header.Header;
import org.nice.pokedexxfx.services.PokemonService;

public class App extends GridPane {

    public App() {

        PokemonService.init();
        this.setStyle("-fx-background-color: white;");

        var header = new Header();
        var body = new Body();
        var footer = new Footer();
        header.getStyleClass().add("header");
        body.getStyleClass().add("body");
        footer.getStyleClass().add("footer");

        var headerRow = new RowConstraints();
        var bodyRow = new RowConstraints();
        var footerRow = new RowConstraints();
        headerRow.setPercentHeight(10);
        bodyRow.setPercentHeight(80);
        footerRow.setPercentHeight(10);

        getRowConstraints().addAll(headerRow, bodyRow, footerRow);
        var colConstraint = new ColumnConstraints();
        colConstraint.setPercentWidth(100);
        colConstraint.setHgrow(Priority.ALWAYS);
        getColumnConstraints().add(colConstraint);

        add(header, 0, 0);
        add(body, 0, 1);
        add(footer, 0, 2);

    }
}
