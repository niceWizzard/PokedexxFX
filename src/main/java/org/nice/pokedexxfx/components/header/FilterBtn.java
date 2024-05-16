package org.nice.pokedexxfx.components.header;

import javafx.scene.control.*;
import org.nice.pokedexxfx.models.PokemonType;

import org.nice.pokedexxfx.services.SearchService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterBtn extends MenuBar {
    public FilterBtn() {
        setStyle(
                "-fx-background-color: white; -fx-background-radius: 10;-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 5, 0, 0, 5);");
        Menu menu = new Menu("Filter");
        menu.setStyle("-fx-font-size: 12px;");
        var btnMap = new HashMap<String, CheckBox>();

        var unselectAll = new Button("Unselect All");
        unselectAll.setStyle("-fx-text-fill: black;");
        var i = new CustomMenuItem(unselectAll);
        i.setHideOnClick(false);
        menu.getItems().add(i);

        for (PokemonType value : PokemonType.values()) {
            var checkbox = new CheckBox(value.name());
            checkbox.setStyle("-fx-text-fill: black;");
            var item = new CustomMenuItem(checkbox);
            item.setHideOnClick(false);
            menu.getItems().add(item);
            btnMap.put(value.name(), checkbox);
            checkbox.selectedProperty().addListener(v -> {
                var list = new ArrayList<>(SearchService.getInstance().currentTypeFilters());
                if (checkbox.isSelected()) {
                    list.add(value);
                    if (list.size() > 2) {
                        list.removeFirst();
                    }
                } else {
                    list.remove(value);
                }
                SearchService.getInstance().setTypeFilters(
                        list);
            });
        }
        getMenus().add(menu);

        SearchService.getInstance().onTypeFilterChange().subscribe(typeList -> {
            btnMap.keySet().forEach(type -> {
                var btn = btnMap.get(type);
                btn.setSelected(typeList.contains(PokemonType.valueOf(type)));
            });
        });

        unselectAll.setOnMouseClicked(v -> {
            SearchService.getInstance().setTypeFilters(List.of());
        });

    }
}
