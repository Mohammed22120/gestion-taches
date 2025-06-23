package com.gestiontaches.model;

import java.time.LocalDate;

public class Tache {
    private int tacheID;
    private String titre;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    //Constructor
    public Tache(int id, String titre, String description, LocalDate dateDebut, LocalDate dateFin) {
        this.tacheID = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }


	//Getters
    public int getTacheID() {
        return tacheID;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    //Setters
    public void setTacheID(int tacheID) {
        this.tacheID = tacheID;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}
