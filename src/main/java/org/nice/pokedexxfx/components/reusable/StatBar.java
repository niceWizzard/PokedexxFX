package org.nice.pokedexxfx.components.reusable;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class StatBar extends GridPane {
    public StatBar(String statLabel, int statValue, int statMax, Color fillColor){
        var c1 = new ColumnConstraints();
        var c2 = new ColumnConstraints();
        var c3 = new ColumnConstraints();
        var r1 = new RowConstraints();
        c1.setPercentWidth(10);
        c2.setHgrow(Priority.ALWAYS);
        c2.setHalignment(HPos.CENTER);
        c3.setPercentWidth(5);
        r1.setVgrow(Priority.ALWAYS);

        setVgap(12);
        setHgap(12);
        getColumnConstraints().addAll(c1, c2, c3);
        getRowConstraints().add(r1);

        var hexColor = fillColor.toString().replace("0x", "#");
        var statName = new Label(statLabel);
        statName.setStyle("-fx-text-fill: white;");
        var statNameWrapper = new HBox(statName);
        statNameWrapper.setStyle(
                "-fx-font-family: Arial; -fx-alignment: center;-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-alignment: center;  -fx-padding: 12px; -fx-background-radius: 6px;" +
                       "-fx-background-color: "+ hexColor +";"
        );

        var pb = new ProgressBar(0.0);
        pb.setProgress((double) statValue / statMax);
        pb.setStyle(
                "-fx-accent: " + hexColor + ";"
        );
        pb.setMaxHeight(Double.MAX_VALUE);
        pb.getStyleClass().add("progress-bar");
        pb.setMaxWidth(1280);

        var statValueText = new Label();
        statValueText.setText(Integer.toString(statValue));
        statValueText.setStyle("-fx-font-family: Arial; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: black");

        add(statNameWrapper, 0,0);
        add(pb, 1,0);
        add(statValueText, 2,0);
    }
}
