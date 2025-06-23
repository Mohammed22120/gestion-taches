package com.gestiontaches.model;

public class EtapeTache {
    private int EtapeTacheID;
    private String statut;
    private String description;
 

    //Constructor
    public EtapeTache(int id, String statut, String description) {
        this.EtapeTacheID = id;
        this.statut = statut;
        this.description = description;
    }


	//Getters
    public int getEtapeTacheID() {
        return EtapeTacheID;
    }

    public String getStatut() {
        return statut;
    }

    public String getDescription() {
        return description;
    }


    //Setters
    public void setTacheID(int EtapeTacheID) {
        this.EtapeTacheID = EtapeTacheID;
    }

    public void setTitre(String statut) {
        this.statut = statut;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

