package org.nice.pokedexxfx.components.reusable;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DynamicVBox<T> extends VBox implements ListChangeListener<T>{
    private final Node defaultView;
    private  Comparator<T> comparator;
    private Function<T, String> keyBuilder;
    private Function<T, Node> viewBuilder;
    private ObservableList<T> list;

    private Map<String, Node> viewMap = new HashMap<>();
    private Map<Node, T> viewToItem = new HashMap<>();

    public DynamicVBox(
            ObservableList<T> list,
            Function<T, String> keyBuilder,
            Function<T, Node> viewBuilder,
            Node defaultView
    ) {
        this.list = list;
        this.keyBuilder = keyBuilder;
        this.viewBuilder = viewBuilder;
        this.defaultView = defaultView;
        init();
    }

    public DynamicVBox(
            ObservableList<T> list,
            Function<T, String> keyBuilder,
            Function<T, Node> viewBuilder,
            Node defaultView,
            Comparator<T> comparator
    ) {
        this.list = list;
        this.keyBuilder = keyBuilder;
        this.viewBuilder = viewBuilder;
        this.defaultView = defaultView;
        this.comparator = comparator;
        init();
    }

    private void init() {
        list.addListener(this);
        list.forEach(item -> {
            var key = keyBuilder.apply(item);
            var view = viewBuilder.apply(item);
            viewMap.put(key, view);
            viewToItem.put(view, item);
            getChildren().add(view);
        });
        if(comparator != null && !list.isEmpty()) {
            list.sort(comparator);
        }
        if(list.isEmpty()) {
            getChildren().add(defaultView);
        }
    }


    @Override
    public void onChanged(Change<? extends T> c) {
        if(!c.next()) {
            return;
        }
        if(!list.isEmpty()) {
            getChildren().remove(defaultView);
        }
        c.getRemoved().forEach(removed -> {
            var key = keyBuilder.apply(removed);
            if(viewMap.containsKey(key)) {
                var view = viewMap.get(key);
                getChildren().remove(view);
                viewMap.remove(key);
                viewToItem.remove(view);
            }
        });
        c.getAddedSubList().forEach(added -> {
           var key = keyBuilder.apply(added);
           var view = viewBuilder.apply(added);
           viewMap.put(key, view);
           viewToItem.put(view, added);
           getChildren().add(view);
        });
        
        if(list.isEmpty()) {
            getChildren().add(defaultView);
        }

        if(comparator != null) {
            FXCollections.sort(getChildren(), (a,b) -> {
                var aView = viewToItem.get(a);
                var bView = viewToItem.get(b);
                return comparator.compare(aView,bView);
            });
        }

    }
}
