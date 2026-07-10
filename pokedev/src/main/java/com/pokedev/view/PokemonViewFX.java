package com.pokedev.view;

import com.pokedev.modele.Pokemon;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    public final Label details;
    public final TextField champNomId;
    public final Button btnCharger;
    public Button btnSupprimer;
    public final ToggleButton btnTheme;
    public ToggleButton btnMute;
    public final ImageView imagePokemon;
    public final Label messageErreur;
    public final VBox barres;
    public Rectangle barreHp;
    public Rectangle barreAttaque;
    public Rectangle barreDefense;
    public Rectangle barreAttSpeciale;
    public Rectangle barreDefSpeciale;
    public Rectangle barreVitesse;

    public final BorderPane racine;

    public PokemonViewFX() {
        // Liste des Pokemon (gauche)
        listePokemon = new ListView<>();
        listePokemon.setPrefWidth(320);

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
        details = new Label();
        details.setWrapText(true);
        details.setText("Selectionnez un Pokemon a gauche pour voir ses details.");
        details.setMinHeight(50);
        details.setPrefHeight(50);
        details.setMaxHeight(50);
        details.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Image (droite-bas)
        imagePokemon = new ImageView();
        imagePokemon.setPreserveRatio(true);
        imagePokemon.setFitWidth(200);
        imagePokemon.setFitHeight(200);

        // Barres (droite-milieu)
        this.barreHp = new Rectangle();
        this.barreAttaque = new Rectangle();
        this.barreDefense = new Rectangle();
        this.barreAttSpeciale = new Rectangle();
        this.barreDefSpeciale = new Rectangle();
        this.barreVitesse = new Rectangle();


        // Les barres avec le texte
        barreHp.setHeight(15);
        barreHp.setWidth(0);
        barreHp.setFill(Color.web("2ed573"));
        barreAttaque.setHeight(15);
        barreAttaque.setWidth(0);
        barreAttaque.setFill(Color.web("ff2c2c"));
        barreDefense.setHeight(15);
        barreDefense.setWidth(0);
        barreDefense.setFill(Color.web("2c75ff"));
        barreAttSpeciale.setHeight(15);
        barreAttSpeciale.setWidth(0);
        barreAttSpeciale.setFill(Color.web("ffd700"));
        barreDefSpeciale.setHeight(15);
        barreDefSpeciale.setWidth(0);
        barreDefSpeciale.setFill(Color.web("e0b0ff"));
        barreVitesse.setHeight(15);
        barreVitesse.setWidth(0);
        barreVitesse.setFill(Color.web("ff6d2d"));

        // Zone qui contient les barres d'attibuts
        this.barres = new VBox(10);
        this.barres.setVisible(false);
        this.barres.setManaged(false);

        HBox ligneHp = new HBox(10);
        ligneHp.setAlignment(Pos.CENTER_LEFT);
        Label lblHp = new Label("HP : ");
        lblHp.setPrefWidth(120); // Pour éviter que les barres soient décalées à cause de la longueur du texte différente
        ligneHp.getChildren().addAll(lblHp, barreHp);

        HBox ligneAttaque = new HBox(10);
        ligneAttaque.setAlignment(Pos.CENTER_LEFT);
        Label lblAtt = new Label("ATTAQUE : ");
        lblAtt.setPrefWidth(120);
        ligneAttaque.getChildren().addAll(lblAtt, barreAttaque);

        HBox ligneDefense = new HBox(10);
        ligneDefense.setAlignment(Pos.CENTER_LEFT);
        Label lblDef = new Label("DEFENSE : ");
        lblDef.setPrefWidth(120);
        ligneDefense.getChildren().addAll(lblDef, barreDefense);

        HBox ligneAttSpec = new HBox(10);
        ligneAttSpec.setAlignment(Pos.CENTER_LEFT);
        Label lblAttSpec = new Label("ATTAQUE SPECIALE : ");
        lblAttSpec.setPrefWidth(120);
        ligneAttSpec.getChildren().addAll(lblAttSpec, barreAttSpeciale);

        HBox ligneDefSpec = new HBox(10);
        ligneDefSpec.setAlignment(Pos.CENTER_LEFT);
        Label lblDefSpec = new Label("DEFENSE SPECIALE : ");
        lblDefSpec.setPrefWidth(120);
        ligneDefSpec.getChildren().addAll(lblDefSpec, barreDefSpeciale);

        HBox ligneVitesse = new HBox(10);
        ligneVitesse.setAlignment(Pos.CENTER_LEFT);
        Label lblVitesse = new Label("VITESSE : ");
        lblVitesse.setPrefWidth(120);
        ligneVitesse.getChildren().addAll(lblVitesse, barreVitesse);

        // On récupère toutes les lignes dans le conteneur
        barres.getChildren().addAll(ligneHp, ligneAttaque, ligneDefense, ligneAttSpec, ligneDefSpec, ligneVitesse);

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
        VBox zoneDetails = new VBox(15);
        zoneDetails.setAlignment(javafx.geometry.Pos.CENTER);
        zoneDetails.setPadding(new Insets(20));
        zoneDetails.getChildren().addAll(details, barres, conteneurImage);
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

