package org.nice.pokedexxfx.components;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import org.nice.pokedexxfx.Utils;
import org.nice.pokedexxfx.components.reusable.StatBar;
import org.nice.pokedexxfx.models.PokemonModel;
import org.nice.pokedexxfx.models.PokemonModel.Image;
import org.nice.pokedexxfx.services.PokemonImage;
import org.nice.pokedexxfx.services.PokemonService;

import java.text.MessageFormat;

public class PokemonTabs extends HBox {
    public PokemonTabs() {

        setStyle(
                " -fx-alignment: center;-fx-margin: 12px; -fx-spacing: 6px; -fx-padding:12px;");
        var evolutionContent = new PokemonEvolutionTabContent();
        var statsContent = new PokemonStatTabContent();
        var descriptionContent = new PokemonDescriptionTabContent();

        TabPane triunePane = new TabPane();
        triunePane.setStyle("-fx-background-color: #f0f0f0; -fx-alignment: center;");

        Tab evolutionTab = new Tab("Evolution", evolutionContent);
        Tab descriptionTab = new Tab("Description", descriptionContent);
        Tab statsTab = new Tab("Stats", statsContent);

        evolutionTab.setStyle(
                "-fx-background-color: #808080; -fx-font-family: Arial; -fx-font-size: 16px; -fx-padding:8px;-fx-font-weight: bold;  -fx-focus-color: transparent;");
        descriptionTab.setStyle(
                "-fx-background-color: #808080; -fx-font-family: Arial; -fx-font-size: 16px; -fx-padding:8px;-fx-font-weight: bold;  -fx-focus-color: transparent;");
        statsTab.setStyle(
                "-fx-background-color: #808080; -fx-font-family: Arial; -fx-font-size: 16px; -fx-padding:8px;-fx-font-weight: bold;  -fx-focus-color: transparent;");

        triunePane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        triunePane.getTabs().addAll(descriptionTab, evolutionTab, statsTab);
        getChildren().add(triunePane);
        HBox.setHgrow(triunePane, Priority.ALWAYS);
    }
}

class PokemonDescriptionTabContent extends HBox {
    public PokemonDescriptionTabContent() {
        setStyle("-fx-font-family: Verdana; -fx-padding: 12px;");
        var descriptionText = new Label();
        descriptionText
                .setStyle("-fx-font-size: 16px;-fx-wrap-text: true; -fx-line-spacing: 10px;");
        PokemonService.getInstance().onCurrentPokemon().subscribe(p -> {
            getChildren().clear();
            descriptionText.setText(p.description());
            getChildren().add(descriptionText);
        });
    }
}

class PokemonStatTabContent extends VBox {

    int maxStat = 300;

    public PokemonStatTabContent() {
        setStyle("-fx-alignment: center;-fx-padding: 1px; -fx-spacing: 6px;");

        PokemonService.getInstance().onCurrentPokemon().subscribe(p -> {
            getChildren().clear();
            if (p.base().isPresent()) {
                getChildren().addAll(
                        new StatBar("HP", p.base().get().HP(), maxStat, Color.web("0xFFDF6D")),
                        new StatBar("ATK", p.base().get().Attack(), maxStat, Color.web("0xE46666")),
                        new StatBar("DEF", p.base().get().Defense(), maxStat, Color.web("0x7480ED")),
                        new StatBar("Sp. A", p.base().get().SpAttack(), maxStat, Color.web("0xF2A6A6")),
                        new StatBar("Sp. D", p.base().get().SpDefense(), maxStat, Color.web("0x7DA6CC")),
                        new StatBar("SPD", p.base().get().Speed(), maxStat, Color.web("0x796CC9")));
            }
        });
    }
}

class PokemonEvolutionTabContent extends ScrollPane {
    public PokemonEvolutionTabContent() {
        var evolutionPanel = new HBox();
        setContent(evolutionPanel);
        setFitToHeight(true);
        setFitToWidth(true);
        setStyle("-fx-alignment: center;");
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
                            p, "Current"));
            evolutionPanel.getChildren().add(current);
            current.setMaxHeight(140);
            if (!next.isEmpty()) {
                var nextEvolPanel = new FlowPane();
                nextEvolPanel.setStyle(" -fx-alignment: center-left;");
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
        card.setStyle(
                "-fx-border-color: #a8a8a8; -fx-border-width: 1px; -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-padding: 6px; -fx-alignment: center;");
        card.setMaxSize(120, 120);

        ImageView resizedImage = Utils.getImageView(evol.model().image().thumbnail());
        resizedImage.setFitHeight(100);
        resizedImage.setFitWidth(100);
        resizedImage.setPreserveRatio(true);

        card.getChildren().add(
                resizedImage);
        var nameLabel = new Label(
                MessageFormat.format(
                        "{0} #{1}",
                        evol.model().name(),
                        evol.model().id()));

        card.getChildren().add(nameLabel);
        var condition = new Label(evol.level());
        card.getChildren().add(condition);

        card.setOnMouseClicked(e -> {
            PokemonService.getInstance().setCurrentPokemon(evol.model());
        });

        return card;
    }
}