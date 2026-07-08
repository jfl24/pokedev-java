package com.pokedev.modele;

public class Pokemon {
    public int id;
    public String nom;
    public String imageUrl;
    public String type1;
    public String type2;
    public int hp;
    public int attaque;
    public int defense;
    public int attaqueSpeciale;
    public int defenseSpeciale;
    public int vitesse;

    //Constructeur vide, les attribus viendra de la DB
    public Pokemon() {

    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", hp=" + hp +
                ", attaque=" + attaque +
                ", defense=" + defense +
                ", attaqueSpeciale=" + attaqueSpeciale +
                ", defenseSpeciale=" + defenseSpeciale +
                ", vitesse=" + vitesse +
                '}';
    }
}
