package org.nice.pokedexxfx.components.reusable;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;


public class StatBar extends HBox {
    public StatBar(String statLabel, int statValue, int statMax, Color fillColor){
        setAlignment(Pos.CENTER);
        setSpacing(20);

        var hexColor = "#" + fillColor.toString().replace("0x", "");
        var statName = new Label(statLabel);
        statName.setMinWidth(50);
        statName.setAlignment(Pos.CENTER);
        statName.setStyle(
                "-fx-font-family: Arial; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 12px; -fx-background-radius: 6px;" +
                       "-fx-background-color: "+ hexColor +";"
        );

        var pb = new ProgressBar(0.0);
        pb.setProgress((double) statValue / statMax);
        pb.setMinHeight(25);
        pb.setMinWidth(250);
        pb.setStyle(
                "-fx-accent: " + hexColor + ";"
        );

        var statValueText = new Label();
        statValueText.setMinWidth(50);
        statValueText.setAlignment(Pos.CENTER);
        statValueText.setText(Integer.toString(statValue));
        statValueText.setStyle("-fx-font-family: Arial; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: black");

        setHgrow(statName, Priority.ALWAYS);

        getChildren().addAll(
                statName,
                pb,
                statValueText
        );
    }
}
