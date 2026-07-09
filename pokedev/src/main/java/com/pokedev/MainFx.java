package com.pokedev;

import com.pokedev.controller.PokemonController;
import com.pokedev.view.PokemonViewFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {

    private PokemonController ctrl;

    @Override public void start(Stage stage) {
        PokemonViewFX view = new PokemonViewFX();
        ctrl = new PokemonController(view);
        ctrl.demarrer();

        Scene scene = new Scene(view.getRoot(), 900, 550);
        scene.getStylesheets().add(Thread.currentThread().getContextClassLoader().getResource("styles/style.css").toExternalForm());
        stage.setTitle("Pokedex -- Mes Pokemon");
        stage.setScene(scene);
        stage.show();
    }
    @Override public void stop() {
        if (ctrl != null) ctrl.arreter();
    }
    public static void main(String[] args) {
        launch(args); }
}
