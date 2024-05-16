package org.nice.pokedexxfx.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

import org.nice.pokedexxfx.models.PokemonModel.Ability;
import org.nice.pokedexxfx.services.PokemonImage;
import org.nice.pokedexxfx.services.PokemonService;

public class PokemonDetails extends GridPane {

    private String name, height, weight, species, gender, type1, type2;
    private int id;
    private List<String> types;
    private List<Ability> abilities;

    private Label pokemonID, pokemonName, pokemonSpecies, pokeHeight, pokeWeight, pokeGender, pokeAbility, typeLabel1,
            typeLabel2;
    private ImageView pokeImageView;
    private Image image;
    private HBox pokeType1, pokeType2;

    public PokemonDetails() {
        initializeComponents();
        setupLayout();
        subscribeToPokemonUpdates();
    }

    private void initializeComponents() {
        // Initialize Labels and ImageView
        pokemonID = new Label();
        pokemonID.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        pokemonName = new Label();
        pokemonName.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        pokemonSpecies = new Label();
        pokemonSpecies.setFont(Font.font("Verdana", 16));
        pokemonSpecies.setTextFill(Color.GRAY);

        pokeHeight = new Label();
        pokeHeight.setFont(Font.font("Courier", 16));

        pokeWeight = new Label();
        pokeWeight.setFont(Font.font("Courier", 16));

        pokeGender = new Label();
        pokeGender.setFont(Font.font("Courier", 16));

        pokeAbility = new Label();
        pokeAbility.setFont(Font.font("Courier", 16));

        typeLabel1 = new Label();
        typeLabel1.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        typeLabel1.setTextFill(Color.WHITE);

        typeLabel2 = new Label();
        typeLabel2.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        typeLabel2.setTextFill(Color.WHITE);

        pokeType2 = new HBox();

        pokeImageView = new ImageView();
        pokeImageView.setFitHeight(200);
        pokeImageView.setFitWidth(200);
        pokeImageView.setPreserveRatio(true);
    }

    private void setupLayout() {
        VBox pokeImageContainer = new VBox(pokeImageView);
        pokeImageContainer.setAlignment(Pos.CENTER);
        pokeImageContainer.setStyle(
                "-fx-background-color: #E9FFFB; -fx-border-color: #808080; -fx-border-width: 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 5);");
        add(pokeImageContainer, 0, 0);

        VBox pokeBasic = new VBox(5, pokemonID, pokemonName, pokemonSpecies);
        pokeBasic.setPadding(new Insets(10));

        VBox pokeStats = new VBox(5, pokeHeight, pokeWeight, pokeGender, pokeAbility);
        pokeStats.setPadding(new Insets(10));
        pokeStats.setStyle(
                "-fx-background-color: #FFF3C7; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 5);");

        HBox typePanel = new HBox(5);
        pokeType1 = new HBox(typeLabel1);
        typePanel.getChildren().add(pokeType1);
        if (type2 != null) {
            pokeType2 = new HBox(typeLabel2);
            typePanel.getChildren().add(pokeType2);
        }
        pokeBasic.getChildren().add(typePanel);

        VBox rightSide = new VBox(10, pokeBasic, pokeStats);
        VBox.setVgrow(pokeBasic, Priority.ALWAYS);
        VBox.setVgrow(pokeStats, Priority.ALWAYS);
        add(rightSide, 1, 0);

        GridPane.setHgrow(rightSide, Priority.ALWAYS);
        GridPane.setVgrow(rightSide, Priority.ALWAYS);
        GridPane.setHgrow(pokeImageContainer, Priority.ALWAYS);
        GridPane.setVgrow(pokeImageContainer, Priority.ALWAYS);
        GridPane.setMargin(rightSide, new Insets(10));
        GridPane.setMargin(pokeImageContainer, new Insets(10));
    }

    private void subscribeToPokemonUpdates() {
        PokemonService.getInstance().onCurrentPokemon().subscribe(p -> {
            name = p.name();
            species = p.species();
            id = p.id();
            height = p.profile().height();
            weight = p.profile().weight();
            gender = formatGender(p.profile().gender());
            abilities = p.profile().ability();
            image = PokemonImage.getHires(p);
            types = p.type();
            type1 = types.get(0);
            type2 = types.size() > 1 ? types.get(1) : null;

            updateUI();
        });
    }

    private void updateUI() {
        pokemonName.setText(name);
        pokemonSpecies.setText(species);
        pokemonID.setText(formatID(id));
        pokeHeight.setText("Height: " + height);
        pokeWeight.setText("Weight: " + weight);
        pokeGender.setText("Gender: " + gender);
        pokeAbility.setText("Abilities: " + (abilities.isEmpty() ? "None" : abilities.get(0).name()));
        pokeImageView.setImage(image);

        typeLabel1.setText(type1);
        pokeType1.setStyle("-fx-background-color: " + "-" + type1 + "; -fx-padding: 5px; -fx-background-radius: 10px;");
        if (type2 != null) {
            typeLabel2.setText(type2);
            pokeType2.setStyle(
                    "-fx-background-color: " + "-" + type2 + "; -fx-padding: 5px; -fx-background-radius: 10px;");
        }
    }

    private String formatID(int id) {
        return String.format("%03d", id);
    }

    private String formatGender(String gender) {
        switch (gender) {
            case "Genderless":
                return "genderless";
            case "100:0":
                return "♂";
            case "0:100":
                return "♀";
            default:
                return "♂ ♀";
        }
    }
}
