package com.pokedev.controller;

import com.pokedev.modele.Pokemon;
import com.pokedev.modele.PokemonDAO;
import com.pokedev.service.PokemonApiService;
import com.pokedev.view.PokemonViewFX;

public class PokemonController {

    private final PokemonApiService service = new PokemonApiService();
    private final PokemonDAO dao = new PokemonDAO();
    private final PokemonViewFX view = new PokemonViewFX();

    public void traiter(String numPokedex) {
        try {
            Pokemon p = service.recuperer(numPokedex);
            dao.sauvegarder(p);
            // view.afficherUn(p);
        } catch (Exception e)
        { view.afficherErreur("Pokemon introuvable : " + numPokedex);
}
