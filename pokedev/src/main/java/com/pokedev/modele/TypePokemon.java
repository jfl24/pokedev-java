package com.pokedev.modele;

public enum TypePokemon {
    NORMAL, FEU, EAU, PLANTE, ELECTRIK, GLACE, COMBAT, POISON, SOL, VOL, PSY, INSECTE, ROCHE, SPECTRE, DRAGON, TENEBRES, ACIER, FEE;

    // On crée une boucle pour traduire les noms de types anglais renvoyés par l'API en français
public static TypePokemon fromString(String nomAnglais) {
    if (nomAnglais == null) return NORMAL;

    String propre = nomAnglais.trim().toLowerCase();

    return switch (propre) {
        case "normal" -> NORMAL;
        case "fire" -> FEU;
        case "water" -> EAU;
        case "grass" -> PLANTE;
        case "electric" -> ELECTRIK;
        case "ice" -> GLACE;
        case "fighting" -> COMBAT;
        case "poison" -> POISON;
        case "ground" -> SOL;
        case "flying" -> VOL;
        case "psychic" -> PSY;
        case "bug" -> INSECTE;
        case "rock" -> ROCHE;
        case "ghost" -> SPECTRE;
        case "dragon" -> DRAGON;
        case "dark" -> TENEBRES;
        case "steel" -> ACIER;
        case "fairy" -> FEE;
        default -> NORMAL;
    };
}
}