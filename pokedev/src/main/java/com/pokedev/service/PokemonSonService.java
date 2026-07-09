package com.pokedev.service;

import com.pokedev.modele.Pokemon;
import javafx.scene.media.AudioClip;
import java.net.URL;

public class PokemonSonService {

    public void jouerCri(Pokemon pokemon) throws Exception {
        URL url = getClass().getClassLoader().getResource("cries/" + pokemon.id + ".mp3");
        if (url == null) {
            throw new Exception("Aucun cri trouve pour : " + pokemon.id + " - " + pokemon.nom);
        }
        AudioClip cri = new AudioClip(url.toExternalForm());
        cri.play();
    }
}