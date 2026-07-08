package com.pokedev.view;

import com.pokedev.modele.Pokemon;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FilmViewFX {
    // Composants exposes au Controller
    public final ListView<Pokemon> listePokemon;
    public final TextArea details;
    public final TextField champNomId;
    public final Button btnCharger;
    public final Button btnSupprimer;
    public final ToggleButton btnTheme;
    public final ToggleButton btnMute;
    public final ImageView imagePokemon;
    public final Label messageErreur;

    private final BorderPane racine;

    public FilmViewFx() {
        // Liste des Pokemon (gauche)
        listePokemon = new ListView<>();
        listePokemon.setPrefWidth(280);

        // Details (centre-droite)
        details = new TextArea();
        details.setEditable(false);
        details.setWrapText(true);
        details.setPromptText("Selectionnez un Pokemon a gauche pour voir ses details.");

        // Image (centre-gauche)
        imagePokemon = new ImageView();
        imagePokemon.setPreserveRatio(true);

        // Formulaire bas
        champNomId = new TextField();
        champNomId.setPromptText("Nom ou ID d'un Pokémon a charger");
        HBox.setHgrow(champNomId, Priority.ALWAYS);

        btnCharger = new Button("Charger");
        btnSupprimer = new Button("Supprimer");
        btnSupprimer.setDisable(true);
        btnTheme = new ToggleButton("Nuit");
        btnMute = new ToggleButton("Son");

        messageErreur = new Label();

        HBox formulaire = new HBox(10, champNomId, btnCharger, btnSupprimer, btnTheme, btnMute);
        VBox bas = new VBox(6, formulaire, messageErreur);

        // Zone details : BorderPane imbrique
        BorderPane zoneDetails = new BorderPane();
        zoneDetails.setLeft(imagePokemon);
        zoneDetails.setCenter(details);
        BorderPane.setMargin(details, new Insets(0, 0, 0, 15));
        zoneDetails.getStyleClass().add("carte-film");

        // BorderPane racine
        racine = new BorderPane();
        racine.setLeft(listePokemon);
        racine.setCenter(zoneDetails);
        racine.setBottom(bas);
        racine.setPadding(new Insets(15));
        BorderPane.setMargin(zoneDetails, new Insets(0, 0, 0, 15));
        BorderPane.setMargin(bas, new Insets(15, 0, 0, 0));

        // Image responsive
        imagePokemon.fitWidthProperty().bind(Bindings.max(140, Bindings.min(320, racine.widthProperty().multiply(0.25))));

    }
        public Parent getRoot() {
            return racine;
        }
    }

