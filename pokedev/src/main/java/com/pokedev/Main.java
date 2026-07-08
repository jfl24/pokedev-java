package com.pokedev;

import com.pokedev.modele.Pokemon;
import com.pokedev.modele.PokemonDAO;
import com.pokedev.service.PokemonApiService;

public class Main {
    public static void main(String[] args) {
        // TESSST a supprimer
        try {
            PokemonApiService api = new PokemonApiService();
            PokemonDAO dao = new PokemonDAO();

            Pokemon p = api.recuperer("pikachu");

            dao.sauvegarder(p);

            System.out.println("Pokemon sauvegardé : " + p);

            System.out.println("Liste BD :");
            for (Pokemon pokemon : dao.lister()) {
                System.out.println(pokemon);
            }

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}