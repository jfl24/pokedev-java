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


public class PokemonViewFX {
    // Composants exposes au Controller
    public ListView<Pokemon> listePokemon;
    public final TextArea details;
    public final TextField champNomId;
    public final Button btnCharger;
    public Button btnSupprimer;
    public final ToggleButton btnTheme;
    public final ToggleButton btnMute;
    public final ImageView imagePokemon;
    public final Label messageErreur;

    public final BorderPane racine;

    public PokemonViewFX() {
        // Liste des Pokemon (gauche)
        listePokemon = new ListView<>();
        listePokemon.setPrefWidth(280);

        listePokemon.setCellFactory(lv-> new ListCell<Pokemon>() {
                    @Override
                    protected void updateItem(Pokemon pokemon, boolean vide) {
                        super.updateItem(pokemon, vide);
                        if (vide || pokemon == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(pokemon.nom.toUpperCase() + " ( " + pokemon.type1.name().toLowerCase() + " ) -  Niveau d'attaque : " + pokemon.attaque);
                        }
                    }
                });

        // Details (centre-droite)
        details = new TextArea();
        details.setEditable(false);
        details.setWrapText(true);
        details.setPromptText("Selectionnez un Pokemon a gauche pour voir ses details.");

        // Image (centre-gauche)
        imagePokemon = new ImageView();
        imagePokemon.setPreserveRatio(true);
        imagePokemon.setFitWidth(200);
        imagePokemon.setFitHeight(200);

        // On crée un conteneur pour l'image
        HBox conteneurImage = new HBox(imagePokemon);
        conteneurImage.setMinHeight(220);
        conteneurImage.setPrefHeight(220);
        conteneurImage.setMaxHeight(220);
        conteneurImage.setAlignment(javafx.geometry.Pos.CENTER);   // On détermine une taille et on place la photo au centre

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
        zoneDetails.setBottom(conteneurImage);
        zoneDetails.setTop(details);
        BorderPane.setMargin(details, new Insets(0, 0, 0, 15));
        zoneDetails.getStyleClass().add("carte-pokemon");

        // BorderPane racine
        racine = new BorderPane();
        racine.setLeft(listePokemon);
        racine.setCenter(zoneDetails);
        racine.setBottom(bas);
        racine.setPadding(new Insets(15));
        BorderPane.setMargin(zoneDetails, new Insets(0, 0, 0, 15));
        BorderPane.setMargin(bas, new Insets(15, 0, 0, 0));

    }
        public Parent getRoot() {
            return racine;
        }
    }

