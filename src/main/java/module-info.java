module org.nice.pokedexxfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires rxjava;
    requires org.json;
    requires javafx.graphics;

    opens org.nice.pokedexxfx to javafx.fxml;

    exports org.nice.pokedexxfx;
}