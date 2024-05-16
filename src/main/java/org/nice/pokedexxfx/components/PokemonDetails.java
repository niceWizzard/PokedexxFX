package org.nice.pokedexxfx.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

import org.nice.pokedexxfx.models.PokemonModel.Ability;
import org.nice.pokedexxfx.services.PokemonImage;
import org.nice.pokedexxfx.services.PokemonService;

public class PokemonDetails extends GridPane {

    String Name, Height, Weight, Species, Gender;
    int ID;
    List<String> Type;
    List<Ability> Ability;
    String zeros = "00";

    Label pokemonID, pokemonName, pokemonSpecies, pokeH, pokeW, pokeG, pokeA, type1Label, type2Label;
    ImageView pokeImageView;
    Image image;

    public PokemonDetails() {

        PokemonService.getInstance().onCurrentPokemon().subscribe(p -> {
            Name = p.name();
            if (pokemonName != null) {
                pokemonName.setText(Name);
            }

            Species = p.species();
            if (pokemonSpecies != null) {
                pokemonSpecies.setText(Species);
            }

            ID = p.id();
            if (ID < 10) {
                zeros = "00";
            } else if (ID < 100) {
                zeros = "0";
            } else {
                zeros = "";
            }
            if (pokemonID != null) {
                pokemonID.setText(zeros + ID);
            }
            Height = p.profile().height();
            if (pokeH != null) {
                pokeH.setText("Height: " + Height);
            }
            Weight = p.profile().weight();
            if (pokeW != null) {
                pokeW.setText("Weight: " + Weight);
            }
            Gender = p.profile().gender();
            if (p.profile().gender().equals("Genderless")) {
                Gender = "genderless";
            } else if (p.profile().gender().equals("100:0")) {
                Gender = "♂";
            } else if (p.profile().gender().equals("0:100")) {
                Gender = "♀";
            } else {
                Gender = "♂ ♀";
            }
            if (pokeG != null) {
                pokeG.setText("Gender: " + Gender);
            }
            Ability = p.profile().ability();
            if (pokeA != null) {
                pokeA.setText("Abilities: " + (Ability.isEmpty() ? "None" : Ability.get(0).name()));
            }
            image = PokemonImage.getHires(p);
            if (pokeImageView != null) {
                pokeImageView.setImage(image);
            }
        });

        // Image Section
        pokeImageView = new ImageView(image);
        pokeImageView.setFitHeight(200);
        pokeImageView.setFitWidth(200);
        pokeImageView.setPreserveRatio(true);

        VBox pokeImagecontainer = new VBox();
        pokeImagecontainer
                .setStyle(
                        "-fx-background-color: #E9FFFB; -fx-border-color: #808080; -fx-border-width: 15px;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 5);");
        pokeImagecontainer.setAlignment(Pos.CENTER);
        pokeImagecontainer.getChildren().add(pokeImageView);

        add(pokeImagecontainer, 0, 0);

        // Basic Info Section
        VBox pokeBasic = new VBox();
        pokeBasic.setSpacing(5);
        pokeBasic.setPadding(new Insets(10));

        pokemonID = new Label(zeros + ID);
        pokemonID.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        pokeBasic.getChildren().add(pokemonID);

        pokemonName = new Label(Name);
        pokemonName.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        pokeBasic.getChildren().add(pokemonName);

        pokemonSpecies = new Label(Species);
        pokemonSpecies.setFont(Font.font("Verdana", 16));
        pokemonSpecies.setTextFill(Color.GRAY);
        pokeBasic.getChildren().add(pokemonSpecies);

        // Stats Section
        VBox pokeStats = new VBox();
        pokeStats.setSpacing(5);
        pokeStats.setPadding(new Insets(10));
        pokeStats.setStyle(
                "-fx-background-color: #FFF3C7; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 5);");

        pokeH = new Label("Height: " + Height);
        pokeH.setFont(Font.font("Courier", 16));
        pokeStats.getChildren().add(pokeH);

        pokeW = new Label("Weight: " + Weight);
        pokeW.setFont(Font.font("Courier", 16));
        pokeStats.getChildren().add(pokeW);

        pokeG = new Label("Gender: " + Gender);
        pokeG.setFont(Font.font("Courier", 16));
        pokeStats.getChildren().add(pokeG);

        pokeA = new Label("Abilities: " + (Ability.isEmpty() ? "None" : Ability.get(0).name()));
        pokeA.setFont(Font.font("Courier", 16));
        pokeStats.getChildren().add(pokeA);

        // Type Section
        // HBox typePanel = new HBox();
        // typePanel.setSpacing(10);
        // typePanel.setPadding(new Insets(10));

        // type1Label = new Label(Type.get(0));
        // type1Label.setFont(Font.font("Arial", 14));
        // type1Label.setTextFill(Color.WHITE);

        // type2Label = new Label();
        // if (Type.size() > 1) {
        // type2Label.setText(Type.get(1));
        // type2Label.setFont(Font.font("Arial", 14));
        // type2Label.setTextFill(Color.WHITE);
        // }

        // Format
        VBox rightSide = new VBox();
        rightSide.setSpacing(10);
        rightSide.getChildren().addAll(pokeBasic, pokeStats);

        VBox.setVgrow(pokeBasic, Priority.ALWAYS);
        VBox.setVgrow(pokeStats, Priority.ALWAYS);
        add(rightSide, 1, 0);

        GridPane.setHgrow(rightSide, Priority.ALWAYS);
        GridPane.setVgrow(rightSide, Priority.ALWAYS);
        GridPane.setHgrow(pokeImagecontainer, Priority.ALWAYS);
        GridPane.setVgrow(pokeImagecontainer, Priority.ALWAYS);

        GridPane.setMargin(rightSide, new Insets(10));
        GridPane.setMargin(pokeImagecontainer, new Insets(10));
    }
}
