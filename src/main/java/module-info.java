module org.nice.pokedexxfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.nice.pokedexxfx to javafx.fxml;
    exports org.nice.pokedexxfx;
}