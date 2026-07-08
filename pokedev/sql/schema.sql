CREATE TABLE IF NOT EXISTS pokemons (
    id INT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    image_url TEXT,
    type1 VARCHAR(50) NOT NULL,
    type2 VARCHAR(50),
    hp INT NOT NULL,
    attaque INT NOT NULL,
    defense INT NOT NULL,
    attaque_speciale INT NOT NULL,
    defense_speciale INT NOT NULL,
    vitesse INT NOT NULL,
    date_ajout TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);