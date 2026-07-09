package com.pokedev.modele;

import com.pokedev.util.Connexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAO {
    public void sauvegarder(Pokemon pokemon) throws SQLException {
        String sql = """
                INSERT INTO pokemons (id, nom, image_url, type1, type2, hp, attaque, defense, attaque_speciale, defense_speciale, vitesse)
                VALUES (?, ?, ?, ?::type_pokemon, ?::type_pokemon, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (id) DO UPDATE SET
                    nom = EXCLUDED.nom,
                    image_url = EXCLUDED.image_url,
                    type1 = EXCLUDED.type1::type_pokemon,
                    type2 = EXCLUDED.type2::type_pokemon,
                    hp = EXCLUDED.hp,
                    attaque = EXCLUDED.attaque,
                    defense = EXCLUDED.defense,
                    attaque_speciale = EXCLUDED.attaque_speciale,
                    defense_speciale = EXCLUDED.defense_speciale,
                    vitesse = EXCLUDED.vitesse
                """;

        try (Connection connexion = Connexion.getConnexion();
             PreparedStatement ps = connexion.prepareStatement(sql)) {
            ps.setInt(1, pokemon.id);
            ps.setString(2, pokemon.nom);
            ps.setString(3, pokemon.imageUrl);
            ps.setString(4, pokemon.type1.name());
            if (pokemon.type2 != null) {
                ps.setString(5, pokemon.type2.name());
            } else {
                ps.setNull(5, java.sql.Types.VARCHAR);  // On gère les cas où le type secondaire est nulle
            }
            ps.setInt(6, pokemon.hp);
            ps.setInt(7, pokemon.attaque);
            ps.setInt(8, pokemon.defense);
            ps.setInt(9, pokemon.attaqueSpeciale);
            ps.setInt(10, pokemon.defenseSpeciale);
            ps.setInt(11, pokemon.vitesse);
            ps.executeUpdate();
        }
    }

    public List<Pokemon> lister() throws SQLException {
        String sql = "SELECT * FROM pokemons ORDER BY id";
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection connexion = Connexion.getConnexion();
             Statement st = connexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Pokemon pokemon = new Pokemon();
                pokemon.id = rs.getInt("id");
                pokemon.nom = rs.getString("nom");
                pokemon.imageUrl = rs.getString("image_url");
                String t1 = rs.getString("type1");
                pokemon.type1 = TypePokemon.valueOf(t1);

                String t2 = rs.getString("type2");
                if (t2 != null) {
                    pokemon.type2 = TypePokemon.valueOf(t2);
                } else {
                    pokemon.type2 = null;
                }

                pokemon.hp = rs.getInt("hp");
                pokemon.attaque = rs.getInt("attaque");
                pokemon.defense = rs.getInt("defense");
                pokemon.attaqueSpeciale = rs.getInt("attaque_speciale");
                pokemon.defenseSpeciale = rs.getInt("defense_speciale");
                pokemon.vitesse = rs.getInt("vitesse");
                pokemons.add(pokemon);
            }
        }
        return pokemons;
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM pokemons WHERE id = ?";
        try (Connection co = Connexion.getConnexion();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setInt(1, id);
            int lignesAffectees = ps.executeUpdate();
            if (lignesAffectees == 0) {
                throw new SQLException("Aucun pokemon trouvé avec l'id : " + id);
            }
        }
    }
}