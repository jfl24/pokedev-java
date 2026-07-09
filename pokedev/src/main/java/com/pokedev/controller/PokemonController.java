package com.pokedev.controller;

import com.pokedev.modele.Pokemon;
import com.pokedev.modele.PokemonDAO;
import com.pokedev.service.PokemonApiService;
import com.pokedev.util.Connexion;
import com.pokedev.view.PokemonViewFX;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import java.util.Optional;

import java.sql.SQLException;

public class PokemonController {

    private final PokemonApiService service = new PokemonApiService();
    private final PokemonDAO dao = new PokemonDAO();
    private final PokemonViewFX view;

    public PokemonController(PokemonViewFX view) {
        this.view = view;
        view.btnCharger.setOnAction(e -> chargerDepuisApi());

        view.listePokemon.getSelectionModel().selectedItemProperty().addListener((obs, ancien, nouveau) -> afficherDetails(nouveau));

        view.btnSupprimer.setOnAction(e -> supprimerSelection());

        view.listePokemon.getSelectionModel().selectedItemProperty().addListener((obs, ancien, nouveau) -> {
            view.btnSupprimer.setDisable(nouveau == null);
            afficherDetails(nouveau);
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
            view.details.clear();
            view.imagePokemon.setImage(null);
            return;
        }
        String text =
                "=== " + p.nom + " ( " + p.type1 + " ) ===\n\n" +
                        "Attaque : " + p.attaque + "\n" +
                        "Défense : " + p.defense;
        view.details.setText(text);

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
        } catch (Exception e) {
            view.messageErreur.setText("Pokemon introuvable ou API en erreur : "+ e.getMessage());
        }
    }

    public void rafraichirListe(){
        try{
            view.listePokemon.getItems().setAll(dao.lister());
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
                view.details.clear();
                view.imagePokemon.setImage(null);
            } catch (Exception ex) {
                view.messageErreur.setText("Erreur : " + ex.getMessage());
            }
        }
    }

    public void demarrer() { rafraichirListe(); }

    public void arreter() {
        System.out.println("Application arrêtée avec succès.");
    }
}



