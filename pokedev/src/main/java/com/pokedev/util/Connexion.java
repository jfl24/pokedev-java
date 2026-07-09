package com.pokedev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

    private static final String URL = "jdbc:postgresql://localhost:5432/pokedev";
    private static final String USER = "postgres";
    private static final String PASS = "Uk5%6gs4Vp";

    private Connexion() {
    }

    public static Connection getConnexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}