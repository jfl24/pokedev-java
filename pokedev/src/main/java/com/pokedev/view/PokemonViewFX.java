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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class PokemonViewFX {
    // Composants exposes au Controller
    public ListView<Pokemon> listePokemon;
    public final TextArea details;
    public final TextField champNomId;
    public final Button btnCharger;
    public Button btnSupprimer;
    public final ToggleButton btnTheme;
    public ToggleButton btnMute;
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

        // Details (droite-haut)
        details = new TextArea();
        details.setEditable(false);
        details.setWrapText(true);
        details.setPromptText("Selectionnez un Pokemon a gauche pour voir ses details.");

        // Image (droite-bas)
        imagePokemon = new ImageView();
        imagePokemon.setPreserveRatio(true);
        imagePokemon.setFitWidth(200);
        imagePokemon.setFitHeight(200);

        // Barres (droite-milieu)
        Rectangle barreHp = new Rectangle();
        barreHp.setHeight(15);
        barreHp.setFill(Color.web("2ed573"));
        Rectangle barreAttaque = new Rectangle();
        barreAttaque.setHeight(15);
        barreAttaque.setFill(Color.web("ff2c2c"));
        Rectangle barreDefense = new Rectangle();
        barreDefense.setHeight(15);
        barreDefense.setFill(Color.web("2c75ff"));
        Rectangle barreAttSpeciale = new Rectangle();
        barreAttSpeciale.setHeight(15);
        barreAttSpeciale.setFill(Color.web("ffd700"));
        Rectangle barreDefSpecial = new Rectangle();
        barreDefSpecial.setHeight(15);
        barreDefSpecial.setFill(Color.web("e0b0ff"));
        Rectangle barreVitesse = new Rectangle();
        barreVitesse.setHeight(15);
        barreVitesse.setFill(Color.web("ff6d2d"));


        // Zone qui contient les barres d'attibuts
        VBox barres = new VBox(5, barreHp, barreAttaque, barreDefense, barreAttSpeciale, barreDefSpecial, barreVitesse);

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
        zoneDetails.setCenter(barres);
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

