package com.pokedev.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedev.modele.Pokemon;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokemonApiService {

    private static final String URL = "https://pokeapi.co/api/v2/pokemon/";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Pokemon recuperer(String recherche) throws Exception {
        recherche = recherche.trim().toLowerCase();
        HttpRequest req = HttpRequest.newBuilder(URI.create(URL + recherche)).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() == 404) {
            throw new RuntimeException("Pokemon introuvable");
        }
        if (res.statusCode() != 200) {
            throw new RuntimeException("API erreur");
        }

        JsonNode pokemonJson = mapper.readTree(res.body());
        System.out.println(pokemonJson);
        Pokemon p = new Pokemon();
        p.id = pokemonJson.get("id").asInt();
        p.nom = pokemonJson.get("name").asText();
        p.imageUrl = pokemonJson
                .get("sprites")
                .get("other")
                .get("official-artwork")
                .get("front_default")
                .asText();

        p.type1 = pokemonJson.get("types").get(0).get("type").get("name").asText();
        if (pokemonJson.get("types").size() > 1) {
            p.type2 = pokemonJson.get("types").get(1).get("type").get("name").asText();
        } else {
            p.type2 = null;
        }

        p.hp = pokemonJson.get("stats").get(0).get("base_stat").asInt();
        p.attaque = pokemonJson.get("stats").get(1).get("base_stat").asInt();
        p.defense = pokemonJson.get("stats").get(2).get("base_stat").asInt();
        p.attaqueSpeciale = pokemonJson.get("stats").get(3).get("base_stat").asInt();
        p.defenseSpeciale = pokemonJson.get("stats").get(4).get("base_stat").asInt();
        p.vitesse = pokemonJson.get("stats").get(5).get("base_stat").asInt();

        return p;
    }
}