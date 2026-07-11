package com.pokedev.controller;

import com.pokedev.modele.Pokemon;
import com.pokedev.modele.PokemonDAO;
import com.pokedev.service.PokemonApiService;
import com.pokedev.util.Connexion;
import com.pokedev.view.PokemonViewFX;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;


import java.util.Optional;
import com.pokedev.service.PokemonSonService;

import java.sql.SQLException;


public class PokemonController {

    private final PokemonApiService service = new PokemonApiService();
    private final PokemonDAO dao = new PokemonDAO();
    private final PokemonSonService sonService = new PokemonSonService();
    private final PokemonViewFX view;

    public PokemonController(PokemonViewFX view) {
        this.view = view;
        view.btnCharger.setOnAction(e -> chargerDepuisApi());

        view.btnSupprimer.setOnAction(e -> supprimerSelection());

        view.btnMute.setOnAction(e -> jouerCriSelection());

        view.listePokemon.getSelectionModel().selectedItemProperty().addListener((obs, ancien, nouveau) -> {
            view.btnSupprimer.setDisable(nouveau == null);
            if (nouveau != null) {
                afficherDetails(nouveau);
                // Un écouteur d'événements pour changer la couleur de la racine selon le type du Pokémon cliqué
                view.racine.getStyleClass().setAll("root", nouveau.type1.name().toLowerCase());
            } else {
                view.racine.getStyleClass().setAll("root");
            }
            if (view.btnTheme.isSelected()) {
                view.racine.getStyleClass().add("dark-theme");
            }
        });

        view.btnTheme.setOnAction(e -> {
            javafx.scene.Parent racine = view.getRoot();
            if (view.btnTheme.isSelected()) {
                racine.getStyleClass().add("dark-theme");
                view.btnTheme.setText("Jour");
            } else {
                racine.getStyleClass().remove("dark-theme");
                view.btnTheme.setText("Nuit");
            }
        });
    }

    public void afficherDetails(Pokemon p) {
        if (p == null) {
            view.titrePokemon.setText("");
            view.titrePokemon.setVisible(false);
            view.titrePokemon.setManaged(false);
            view.imagePokemon.setImage(null);
            return;
        }
        view.titrePokemon.setText(p.nom.toUpperCase());
        view.titrePokemon.setVisible(true);
        view.titrePokemon.setManaged(true);
        view.typeWrapper.setVisible(true);
        view.typeWrapper.setManaged(true);
        view.type1Label.setText(p.type1.name().toLowerCase());
        view.type1Label.getStyleClass().setAll("type-badge", "type-badge-detail", p.type1.name().toLowerCase());
        if (p.type2 != null) {
            view.type2Label.setText(p.type2.name().toLowerCase());
            view.type2Label.getStyleClass().setAll("type-badge", "type-badge-detail", p.type2.name().toLowerCase());
            view.type2Label.setVisible(true);
            view.type2Label.setManaged(true);
        } else {
            view.type2Label.setVisible(false);
            view.type2Label.setManaged(false);
        }
        view.barres.setVisible(true);
        view.barres.setManaged(true);
        view.barreHp.setProgress(p.hp / 255.0);
        view.barreAttaque.setProgress(p.attaque / 255.0);
        view.barreDefense.setProgress(p.defense / 255.0);
        view.barreAttSpeciale.setProgress(p.attaqueSpeciale / 255.0);
        view.barreDefSpeciale.setProgress(p.defenseSpeciale / 255.0);
        view.barreVitesse.setProgress(p.vitesse / 255.0);
        view.valeurHp.setText(p.hp + "/255");
        view.valeurAttaque.setText(p.attaque + "/255");
        view.valeurDefense.setText(p.defense + "/255");
        view.valeurAttSpeciale.setText(p.attaqueSpeciale + "/255");
        view.valeurDefSpeciale.setText(p.defenseSpeciale + "/255");
        view.valeurVitesse.setText(p.vitesse + "/255");
        if (p.imageUrl != null) {
            Image img = new Image(p.imageUrl, true);
            view.imagePokemon.setImage(img);
        }
    }


    public void chargerDepuisApi() {
        String recherche = view.champNomId.getText();
        try{
            Pokemon p = service.recuperer(recherche);
            dao.sauvegarder(p);
            rafraichirListe();
            view.messageErreur.setText("  ");  // Pour faire disparaître le message d'erreur quand la recherche fonctionne
        } catch (Exception e) {
            view.messageErreur.setText("Pokemon introuvable ou API en erreur : "+ e.getMessage());
        }
    }

    public void rafraichirListe(){
        try{
            view.listePokemon.getItems().setAll(dao.lister());
            compterNombre();
            view.barres.setVisible(false);
            view.barres.setManaged(false);
        } catch (Exception e) {
            view.messageErreur.setText("Erreur dans la BDD : "+ e.getMessage());
        }
    }

    private void supprimerSelection() {
        Pokemon selection = view.listePokemon.getSelectionModel().getSelectedItem();
        if (selection == null) return;

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer ce Pokemon ?");
        alert.setContentText(selection.nom + " (" + selection.type1 + ")");

        Optional<ButtonType> reponse = alert.showAndWait();
        if (reponse.isPresent() && reponse.get() == ButtonType.OK) {
            try {
                dao.supprimer(selection.id);
                rafraichirListe();
                view.details.setText("");
                view.imagePokemon.setImage(null);
                view.barres.setVisible(false);
                view.barres.setManaged(false);  // Pour libérer l'espace prise par les barres
                view.titrePokemon.setText("");
                view.titrePokemon.setVisible(false);
                view.titrePokemon.setManaged(false);
                view.typeWrapper.setVisible(false);
                view.typeWrapper.setManaged(false);
            } catch (Exception ex) {
                view.messageErreur.setText("Erreur : " + ex.getMessage());
            }
        }
    }

    private void jouerCriSelection() {
        Pokemon selection = view.listePokemon.getSelectionModel().getSelectedItem();
        if (selection == null) {
            view.messageErreur.setText("Selectionnez un Pokemon pour jouer son cri.");
            return;
        }
        try {
            sonService.jouerCri(selection);
            view.messageErreur.setText("");
        } catch (Exception e) {
            view.messageErreur.setText("Erreur son : " + e.getMessage());
        }
        view.btnMute.setSelected(false);
    }

    public void compterNombre (){
        try {
            long nombrePokemon = dao.compter();
            view.nombrePokemon.setText("Total capturés : "+ nombrePokemon);
        } catch (SQLException e) {
            view.messageErreur.setText("Erreur pour obtenir le nombre de Pokémon capturés.");
        }
    }

    public void demarrer() {
        rafraichirListe();
        compterNombre();
    }

    public void arreter() {
        System.out.println("Application arrêtée avec succès.");
    }
}



