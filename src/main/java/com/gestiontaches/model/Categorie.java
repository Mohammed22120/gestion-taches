package com.gestiontaches.model;

public class Categorie {
    private int idCategorie;
    private String nom;

    // Constructor
    public Categorie(int categorieID, String nom) {
        this.idCategorie = categorieID;
        this.nom = nom;
    }


	// Getters
    public int getCategorieID() {
        return idCategorie;
    }

    public String getNom() {
        return nom;
    }

    //Setters
    public void setCategorieID(int categorieID) {
        this.idCategorie = categorieID;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}

