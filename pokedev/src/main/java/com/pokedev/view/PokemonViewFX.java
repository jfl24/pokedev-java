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
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;


public class PokemonViewFX {
    // Composants exposes au Controller
    public ListView<Pokemon> listePokemon;
    public final Label details;
    public final TextField champNomId;
    public final Label nombrePokemon;
    public final Button btnCharger;
    public Button btnSupprimer;
    public final ToggleButton btnTheme;
    public ToggleButton btnMute;
    public final ImageView imagePokemon;
    public final Label messageErreur;
    public final VBox barres;
    public Label titrePokemon;
    public ProgressBar barreHp;
    public ProgressBar barreAttaque;
    public ProgressBar barreDefense;
    public ProgressBar barreAttSpeciale;
    public ProgressBar barreDefSpeciale;
    public ProgressBar barreVitesse;
    public Label valeurHp;
    public Label valeurAttaque;
    public Label valeurDefense;
    public Label valeurAttSpeciale;
    public Label valeurDefSpeciale;
    public Label valeurVitesse;
    public HBox typeWrapper;
    public Label type1Label;
    public Label type2Label;

    public final BorderPane racine;

    public PokemonViewFX() {
        // Liste des Pokemon (gauche)
        listePokemon = new ListView<>();
        listePokemon.setPrefWidth(320);

        listePokemon.setCellFactory(lv -> new ListCell<Pokemon>() {
            @Override
            protected void updateItem(Pokemon pokemon, boolean vide) {
                super.updateItem(pokemon, vide);
                if (vide || pokemon == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                Label nom = new Label("#" + pokemon.id + " " +pokemon.nom.toUpperCase());
                Label type1 = new Label(pokemon.type1.name().toLowerCase());
                type1.getStyleClass().addAll("type-badge", pokemon.type1.name().toLowerCase());
                HBox ligne = new HBox(8);
                ligne.setAlignment(Pos.CENTER_LEFT);
                ligne.getChildren().addAll(nom, type1);
                if (pokemon.type2 != null) {
                    Label type2 = new Label(pokemon.type2.name().toLowerCase());
                    type2.getStyleClass().addAll("type-badge", pokemon.type2.name().toLowerCase());
                    ligne.getChildren().add(type2);
                }

                setText(null);
                setGraphic(ligne);
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

        //Nom du pokemon
        titrePokemon = new Label();
        titrePokemon.getStyleClass().add("pokemon-title");
        titrePokemon.setMaxWidth(Double.MAX_VALUE);
        titrePokemon.setAlignment(Pos.CENTER);
        titrePokemon.setVisible(false);
        titrePokemon.setManaged(false);

        //Wrapper pour les types
        typeWrapper = new HBox(8);
        typeWrapper.setAlignment(Pos.CENTER);
        type1Label = new Label();
        type2Label = new Label();
        type1Label.getStyleClass().add("type-badge");
        type2Label.getStyleClass().add("type-badge");
        typeWrapper.getChildren().addAll(type1Label, type2Label);
        typeWrapper.setVisible(false);
        typeWrapper.setManaged(false);


        // Image (droite-bas)
        imagePokemon = new ImageView();
        imagePokemon.setPreserveRatio(true);
        imagePokemon.setFitWidth(200);
        imagePokemon.setFitHeight(200);

        // Barres (droite-milieu)
        this.barreHp = new ProgressBar(0);
        this.barreAttaque = new ProgressBar(0);
        this.barreDefense = new ProgressBar(0);
        this.barreAttSpeciale = new ProgressBar(0);
        this.barreDefSpeciale = new ProgressBar(0);
        this.barreVitesse = new ProgressBar(0);

        this.valeurHp = new Label("0/255");
        this.valeurAttaque = new Label("0/255");
        this.valeurDefense = new Label("0/255");
        this.valeurAttSpeciale = new Label("0/255");
        this.valeurDefSpeciale = new Label("0/255");
        this.valeurVitesse = new Label("0/255");

        barreHp.getStyleClass().addAll("stat-bar", "hp");
        barreAttaque.getStyleClass().addAll("stat-bar", "attaque");
        barreDefense.getStyleClass().addAll("stat-bar", "defense");
        barreAttSpeciale.getStyleClass().addAll("stat-bar", "att-speciale");
        barreDefSpeciale.getStyleClass().addAll("stat-bar", "def-speciale");
        barreVitesse.getStyleClass().addAll("stat-bar", "vitesse");

        valeurHp.getStyleClass().add("stat-value");
        valeurAttaque.getStyleClass().add("stat-value");
        valeurDefense.getStyleClass().add("stat-value");
        valeurAttSpeciale.getStyleClass().add("stat-value");
        valeurDefSpeciale.getStyleClass().add("stat-value");
        valeurVitesse.getStyleClass().add("stat-value");

        barreHp.setMaxWidth(Double.MAX_VALUE);
        barreAttaque.setMaxWidth(Double.MAX_VALUE);
        barreDefense.setMaxWidth(Double.MAX_VALUE);
        barreAttSpeciale.setMaxWidth(Double.MAX_VALUE);
        barreDefSpeciale.setMaxWidth(Double.MAX_VALUE);
        barreVitesse.setMaxWidth(Double.MAX_VALUE);

        StackPane wrapHp = new StackPane(barreHp, valeurHp);
        StackPane wrapAttaque = new StackPane(barreAttaque, valeurAttaque);
        StackPane wrapDefense = new StackPane(barreDefense, valeurDefense);
        StackPane wrapAttSpeciale = new StackPane(barreAttSpeciale, valeurAttSpeciale);
        StackPane wrapDefSpeciale = new StackPane(barreDefSpeciale, valeurDefSpeciale);
        StackPane wrapVitesse = new StackPane(barreVitesse, valeurVitesse);

        wrapHp.setMaxWidth(Double.MAX_VALUE);
        wrapAttaque.setMaxWidth(Double.MAX_VALUE);
        wrapDefense.setMaxWidth(Double.MAX_VALUE);
        wrapAttSpeciale.setMaxWidth(Double.MAX_VALUE);
        wrapDefSpeciale.setMaxWidth(Double.MAX_VALUE);
        wrapVitesse.setMaxWidth(Double.MAX_VALUE);

        HBox ligneHp = new HBox(10);
        ligneHp.setAlignment(Pos.CENTER_LEFT);
        Label lblHp = new Label("HP : ");
        lblHp.setPrefWidth(120);
        HBox.setHgrow(wrapHp, Priority.ALWAYS);
        ligneHp.getChildren().addAll(lblHp, wrapHp);

        HBox ligneAttaque = new HBox(10);
        ligneAttaque.setAlignment(Pos.CENTER_LEFT);
        Label lblAtt = new Label("ATTAQUE : ");
        lblAtt.setPrefWidth(120);
        HBox.setHgrow(wrapAttaque, Priority.ALWAYS);
        ligneAttaque.getChildren().addAll(lblAtt, wrapAttaque);

        HBox ligneDefense = new HBox(10);
        ligneDefense.setAlignment(Pos.CENTER_LEFT);
        Label lblDef = new Label("DEFENSE : ");
        lblDef.setPrefWidth(120);
        HBox.setHgrow(wrapDefense, Priority.ALWAYS);
        ligneDefense.getChildren().addAll(lblDef, wrapDefense);

        HBox ligneAttSpec = new HBox(10);
        ligneAttSpec.setAlignment(Pos.CENTER_LEFT);
        Label lblAttSpec = new Label("ATTAQUE SPECIALE : ");
        lblAttSpec.setPrefWidth(120);
        HBox.setHgrow(wrapAttSpeciale, Priority.ALWAYS);
        ligneAttSpec.getChildren().addAll(lblAttSpec, wrapAttSpeciale);

        HBox ligneDefSpec = new HBox(10);
        ligneDefSpec.setAlignment(Pos.CENTER_LEFT);
        Label lblDefSpec = new Label("DEFENSE SPECIALE : ");
        lblDefSpec.setPrefWidth(120);
        HBox.setHgrow(wrapDefSpeciale, Priority.ALWAYS);
        ligneDefSpec.getChildren().addAll(lblDefSpec, wrapDefSpeciale);

        HBox ligneVitesse = new HBox(10);
        ligneVitesse.setAlignment(Pos.CENTER_LEFT);
        Label lblVitesse = new Label("VITESSE : ");
        lblVitesse.setPrefWidth(120);
        HBox.setHgrow(wrapVitesse, Priority.ALWAYS);
        ligneVitesse.getChildren().addAll(lblVitesse, wrapVitesse);

        this.barres = new VBox(10);
        this.barres.setVisible(false);
        this.barres.setManaged(false);
        this.barres.setMaxWidth(420);

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

        // Le label pour afficher le nombre de Pokémon
        nombrePokemon = new Label("0");
        nombrePokemon.getStyleClass().add("capture-number");
        Label texteCaptures = new Label("POKEMON CAPTURES");
        texteCaptures.getStyleClass().add("capture-title");
        HBox compteurCaptures = new HBox(8, nombrePokemon, texteCaptures);
        compteurCaptures.setAlignment(Pos.CENTER_LEFT);
        compteurCaptures.getStyleClass().add("capture-wrapper");
        VBox bas = new VBox(8, formulaire, compteurCaptures, messageErreur);
        bas.setMinHeight(100);
        bas.setPrefHeight(100);
        bas.setMaxHeight(100);

        // Zone details : BorderPane imbrique
        VBox zoneDetails = new VBox(15);
        zoneDetails.setAlignment(javafx.geometry.Pos.CENTER);
        zoneDetails.setPadding(new Insets(20));
        zoneDetails.setSpacing(18);
        zoneDetails.getChildren().addAll(titrePokemon, typeWrapper, barres, conteneurImage);
        zoneDetails.getStyleClass().add("carte-pokemon");
        zoneDetails.setMinHeight(430);
        zoneDetails.setPrefHeight(430);

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

