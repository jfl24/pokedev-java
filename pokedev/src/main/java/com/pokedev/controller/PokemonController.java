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

        view.listePokemon.getSelectionModel().selectedItemProperty().addListener((obs, ancien, nouveau) -> afficherDetails(nouveau));

        view.btnSupprimer.setOnAction(e -> supprimerSelection());

        view.btnMute.setOnAction(e -> jouerCriSelection());

        view.listePokemon.getSelectionModel().selectedItemProperty().addListener((obs, ancien, nouveau) -> {
            view.btnSupprimer.setDisable(nouveau == null);
            if (nouveau != null) {
                afficherDetails(nouveau);
                // Un écouteur d'événements pour changer la couleur de la zone à droite selon le type du Pokémon cliqué
                view.racine.getStyleClass().setAll("root", nouveau.type1.name().toLowerCase());
            } else {
                view.racine.getStyleClass().setAll("root");
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
            view.details.setText("");
            view.imagePokemon.setImage(null);
            return;
        }
        String text =
                "=== " + p.nom.toUpperCase() + " ( " + p.type1.toString().toLowerCase() + " ) ===\n\n";
        view.details.setText(text);
        view.barres.setVisible(true);
        view.barres.setManaged(true);

        view.barreHp.setWidth(p.hp / 255.0 * 100.0 * 2.0);
        view.barreAttaque.setWidth(p.attaque / 255.0 * 100.0 * 2.0);
        view.barreDefense.setWidth(p.defense / 255.0 * 100.0 * 2.0);
        view.barreAttSpeciale.setWidth(p.attaqueSpeciale / 255.0 * 100.0 * 2.0);
        view.barreDefSpeciale.setWidth(p.defenseSpeciale / 255.0 * 100.0 * 2.0);
        view.barreVitesse.setWidth(p.vitesse / 255.0 * 100.0 * 2.0);

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
            view.messageErreur.setText("");  // Pour faire disparaître le message d'erreur quand la recherche fonctionne
        } catch (Exception e) {
            view.messageErreur.setText("Pokemon introuvable ou API en erreur : "+ e.getMessage());
        }
    }

    public void rafraichirListe(){
        try{
            view.listePokemon.getItems().setAll(dao.lister());
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

    public void demarrer() { rafraichirListe(); }

    public void arreter() {
        System.out.println("Application arrêtée avec succès.");
    }
}



