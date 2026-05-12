module bts66.anthony.castlefightarena {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires static lombok;

    opens bts66.anthony.castlefightarena to javafx.fxml;
    exports bts66.anthony.castlefightarena;
    exports bts66.anthony.castlefightarena.controllers;
    opens bts66.anthony.castlefightarena.controllers to javafx.fxml;
}