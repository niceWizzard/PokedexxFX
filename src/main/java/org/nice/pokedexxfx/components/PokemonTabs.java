package org.nice.pokedexxfx.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.nice.pokedexxfx.Utils;
import org.nice.pokedexxfx.models.PokemonModel;
import org.nice.pokedexxfx.services.PokemonService;

import java.text.MessageFormat;

public class PokemonTabs extends HBox {
    public PokemonTabs() {
        setStyle(" -fx-alignment: center;");
        var evolutionPanel = new HBox();
        var evolutionPanelScroll= new ScrollPane(evolutionPanel);
        evolutionPanelScroll.setStyle("-fx-background-color: transparent; -fx-alignment: center;");
        evolutionPanel.setStyle("-fx-alignment: center; -fx-padding: 12px;");
        PokemonService.getInstance().onCurrentPokemon().subscribe(p -> {
            evolutionPanel.getChildren().clear();
            var next = p.getNextEvolution();
            var prev = p.getPrevEvolution();
            prev.ifPresent(prevPokemon -> {
                evolutionPanel.getChildren().add(renderEvolutionCard(prevPokemon));
                evolutionPanel.getChildren().add(new Label("--->"));
            });
            evolutionPanel.getChildren().add(renderEvolutionCard(
                    new PokemonModel.EvolutionNiceData(
                            p,"Current"
                    )
            ));

            if(!next.isEmpty()) {
                var nextEvolPanel=  new FlowPane();
                nextEvolPanel.setPrefWrapLength(480);
                evolutionPanel.getChildren().add(new Label("--->"));
                evolutionPanel.getChildren().add(nextEvolPanel);
                next.forEach(n -> {
                    var card = renderEvolutionCard(n);
                    nextEvolPanel.getChildren().add(card);
                });
            }

        });
        getChildren().add(evolutionPanelScroll);
    }
    private VBox renderEvolutionCard(PokemonModel.EvolutionNiceData evol) {
        var card = new VBox();
        card.setPrefSize(120,120);

        card.getChildren().add(
                Utils.getImageView(evol.model().image().thumbnail())
        );
        var nameLabel = new Label(
                MessageFormat.format(
                        "{0} #{1}",
                        evol.model().name(),
                        evol.model().id()
                )
        );

        card.getChildren().add(nameLabel);
        var condition = new Label(evol.level());
        card.getChildren().add(condition);
        return card;
    }
}
