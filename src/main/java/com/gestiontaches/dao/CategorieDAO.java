package com.gestiontaches.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {
    
    // CREATE - Ajouter une catégorie
    public boolean ajouterCategorie(String nom) {
        String sql = "INSERT INTO categorie (nom) VALUES (?)";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nom);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de catégorie: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Lister toutes les catégories
    public List<String[]> listerCategories() {
        List<String[]> categories = new ArrayList<>();
        String sql = "SELECT Categorie_ID, nom FROM categorie ORDER BY nom";
        
        try (Connection conn = ConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String[] categorie = new String[2];
                categorie[0] = String.valueOf(rs.getInt("Categorie_ID"));
                categorie[1] = rs.getString("nom");
                categories.add(categorie);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des catégories: " + e.getMessage());
        }
        
        return categories;
    }
    
    // READ - Récupérer une catégorie par ID
    public String[] obtenirCategorie(int id) {
        String sql = "SELECT Categorie_ID, nom FROM categorie WHERE Categorie_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String[] categorie = new String[2];
                categorie[0] = String.valueOf(rs.getInt("Categorie_ID"));
                categorie[1] = rs.getString("nom");
                return categorie;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de catégorie: " + e.getMessage());
        }
        
        return null;
    }
    
    // UPDATE - Modifier une catégorie
    public boolean modifierCategorie(int id, String nom) {
        String sql = "UPDATE categorie SET nom = ? WHERE Categorie_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nom);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de catégorie: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE - Supprimer une catégorie
    public boolean supprimerCategorie(int id) {
        String sql = "DELETE FROM categorie WHERE Categorie_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de catégorie: " + e.getMessage());
            return false;
        }
    }
}