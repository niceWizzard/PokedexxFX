package org.nice.pokedexxfx.components;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import org.nice.pokedexxfx.Utils;
import org.nice.pokedexxfx.components.reusable.StatBar;
import org.nice.pokedexxfx.models.PokemonModel;
import org.nice.pokedexxfx.services.PokemonService;

import java.text.MessageFormat;

public class PokemonTabs extends HBox{
    public PokemonTabs() {
        setStyle(" -fx-alignment: center;");
        var evolutionContent = new PokemonEvolutionTabContent();
        var statsContent = new PokemonStatTabContent();
        var descriptionContent = new PokemonDescriptionTabContent();

        TabPane triunePane = new TabPane();
        triunePane.setStyle("-fx-background-color: transparent; -fx-alignment: center;");

        Tab evolutionTab = new Tab();
        evolutionTab.setText("Evolution");
        evolutionTab.setContent(evolutionContent);

        Tab descriptionTab = new Tab();
        descriptionTab.setText("Description");
        descriptionTab.setContent(descriptionContent);

        Tab statsTab = new Tab();
        statsTab.setText("Stats");
        statsTab.setContent(statsContent);

        triunePane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        triunePane.getTabs().add(descriptionTab);
        triunePane.getTabs().add(evolutionTab);
        triunePane.getTabs().add(statsTab);
        getChildren().add(triunePane);

    }
}

class PokemonDescriptionTabContent extends HBox{
    public PokemonDescriptionTabContent(){
        var descriptionPanel = new HBox();
        getChildren().add(descriptionPanel);
        setStyle("-fx-font-family: Verdana");

        PokemonService.getInstance().onCurrentPokemon().subscribe(p -> {
            descriptionPanel.getChildren().clear();
            var descriptionText = new Label();
            descriptionText.setText(p.description());
            descriptionPanel.getChildren().add(descriptionText);
        });
    }
}

class PokemonStatTabContent extends HBox{
    
    int maxStat = 300;

    public PokemonStatTabContent(){
        var statsPanel = new HBox();
        getChildren().add(statsPanel);
        setStyle("-fx-alignment: center");

        PokemonService.getInstance().onCurrentPokemon().subscribe(p -> {
            statsPanel.getChildren().clear();
            if(p.base().isPresent()){
                statsPanel.getChildren().add(new StatBar("HP", p.base().get().HP(), maxStat, Color.web("0xFFDF6D")));
                statsPanel.getChildren().add(new StatBar("ATK", p.base().get().Attack(), maxStat, Color.web("0xE46666")));
                statsPanel.getChildren().add(new StatBar("DEF", p.base().get().Defense(), maxStat, Color.web("0x7480ED")));
                statsPanel.getChildren().add(new StatBar("SpA", p.base().get().SpAttack(), maxStat, Color.web("0xF2A6A6")));
                statsPanel.getChildren().add(new StatBar("SpD", p.base().get().SpDefense(), maxStat, Color.web("0x7DA6CC")));
                statsPanel.getChildren().add(new StatBar("SPD", p.base().get().Speed(), maxStat, Color.web("0x796CC9")));
            }
        });
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