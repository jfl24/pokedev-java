DROP TABLE IF EXISTS pokemons;

CREATE TYPE type_pokemon AS ENUM ('NORMAL',
  'FEU',
  'EAU',
  'PLANTE',
  'ELECTRIK',
  'GLACE',
  'COMBAT',
  'POISON',
  'SOL',
  'VOL',
  'PSY',
  'INSECTE',
  'ROCHE',
  'SPECTRE',
  'DRAGON',
  'TENEBRES',
  'ACIER',
  'FEE'
);

CREATE TABLE IF NOT EXISTS pokemons (
    id INT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    image_url TEXT,
    type1 type_pokemon DEFAULT 'NORMAL' NOT NULL,
    type2 type_pokemon,
    hp INT NOT NULL,
    attaque INT NOT NULL,
    defense INT NOT NULL,
    attaque_speciale INT NOT NULL,
    defense_speciale INT NOT NULL,
    vitesse INT NOT NULL,
    date_ajout TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);