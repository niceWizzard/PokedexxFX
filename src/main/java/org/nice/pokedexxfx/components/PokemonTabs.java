package org.nice.pokedexxfx.components;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.nice.pokedexxfx.Utils;
import org.nice.pokedexxfx.models.PokemonModel;
import org.nice.pokedexxfx.services.PokemonService;

import java.text.MessageFormat;

public class PokemonTabs extends HBox {
    public PokemonTabs() {
        setStyle(" -fx-alignment: center;");
        var evolutionContent = new PokemonEvolutionTabContent();
        getChildren().add(evolutionContent);
    }

}


class PokemonEvolutionTabContent extends  ScrollPane{
    public PokemonEvolutionTabContent() {
        var evolutionPanel = new HBox();
        setContent(evolutionPanel);
        setStyle("-fx-background-color: transparent; -fx-alignment: center;");
        evolutionPanel.setStyle("-fx-alignment: center; -fx-padding: 12px; -fx-spacing: 6px;");
        PokemonService.getInstance().onCurrentPokemon().subscribe(p -> {
            evolutionPanel.getChildren().clear();
            var next = p.getNextEvolution();
            var prev = p.getPrevEvolution();
            prev.ifPresent(prevPokemon -> {
                evolutionPanel.getChildren().add(renderEvolutionCard(prevPokemon));
                evolutionPanel.getChildren().add(new Label("--->"));
            });
            var current = renderEvolutionCard(
                    new PokemonModel.EvolutionNiceData(
                            p,"Current"
                    )
            );
            evolutionPanel.getChildren().add(current);
            current.setMaxHeight(140);
            if(!next.isEmpty()) {
                var nextEvolPanel=  new FlowPane();
                nextEvolPanel.setPrefWrapLength(480);
                nextEvolPanel.setHgap(6);
                nextEvolPanel.setVgap(6);
                evolutionPanel.getChildren().add(new Label("--->"));
                evolutionPanel.getChildren().add(nextEvolPanel);
                next.forEach(n -> {
                    var card = renderEvolutionCard(n);
                    nextEvolPanel.getChildren().add(card);
                });
            }

        });
    }
    private VBox renderEvolutionCard(PokemonModel.EvolutionNiceData evol) {
        var card = new VBox();
        card.setStyle("-fx-border-color: #a8a8a8; -fx-border-width: 1px; -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-padding: 6px; -fx-alignment: center;");
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

        card.setOnMouseClicked(e -> {
            PokemonService.getInstance().setCurrentPokemon(evol.model());
        });

        return card;
    }
}