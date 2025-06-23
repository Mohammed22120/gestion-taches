package com.gestiontaches.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {
    
    // CREATE - Ajouter une tâche
    public boolean ajouterTache(String titre, String description, String dateDebut, 
                               String dateFin, int userId, int categorieId) {
        String sql = "INSERT INTO tache (Titre, Description, Date_debut, Date_fin, User_ID, Categorie_ID) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, titre);
            pstmt.setString(2, description);
            pstmt.setDate(3, Date.valueOf(dateDebut)); // Format: "2024-12-25"
            pstmt.setDate(4, Date.valueOf(dateFin));
            pstmt.setInt(5, userId);
            pstmt.setInt(6, categorieId);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de tâche: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Lister toutes les tâches
    public List<String[]> listerTaches() {
        List<String[]> taches = new ArrayList<>();
        String sql = "SELECT t.Tache_ID, t.Titre, t.Description, t.Date_debut, t.Date_fin, " +
                    "u.Nom, u.Prenom, c.nom as categorie " +
                    "FROM tache t " +
                    "JOIN utilisateur u ON t.User_ID = u.User_ID " +
                    "JOIN categorie c ON t.Categorie_ID = c.Categorie_ID " +
                    "ORDER BY t.Date_debut";
        
        try (Connection conn = ConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String[] tache = new String[8];
                tache[0] = String.valueOf(rs.getInt("Tache_ID"));
                tache[1] = rs.getString("Titre");
                tache[2] = rs.getString("Description");
                tache[3] = rs.getDate("Date_debut").toString();
                tache[4] = rs.getDate("Date_fin").toString();
                tache[5] = rs.getString("Nom");
                tache[6] = rs.getString("Prenom");
                tache[7] = rs.getString("categorie");
                taches.add(tache);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des tâches: " + e.getMessage());
        }
        
        return taches;
    }
    
    // READ - Lister les tâches d'un utilisateur
    public List<String[]> listerTachesUtilisateur(int userId) {
        List<String[]> taches = new ArrayList<>();
        String sql = "SELECT t.Tache_ID, t.Titre, t.Description, t.Date_debut, t.Date_fin, " +
                    "c.nom as categorie " +
                    "FROM tache t " +
                    "JOIN categorie c ON t.Categorie_ID = c.Categorie_ID " +
                    "WHERE t.User_ID = ? " +
                    "ORDER BY t.Date_debut";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String[] tache = new String[6];
                tache[0] = String.valueOf(rs.getInt("Tache_ID"));
                tache[1] = rs.getString("Titre");
                tache[2] = rs.getString("Description");
                tache[3] = rs.getDate("Date_debut").toString();
                tache[4] = rs.getDate("Date_fin").toString();
                tache[5] = rs.getString("categorie");
                taches.add(tache);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des tâches utilisateur: " + e.getMessage());
        }
        
        return taches;
    }
    
    // READ - Récupérer une tâche par ID
    public String[] obtenirTache(int id) {
        String sql = "SELECT t.Tache_ID, t.Titre, t.Description, t.Date_debut, t.Date_fin, " +
                    "t.User_ID, t.Categorie_ID, c.nom as categorie " +
                    "FROM tache t " +
                    "JOIN categorie c ON t.Categorie_ID = c.Categorie_ID " +
                    "WHERE t.Tache_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String[] tache = new String[8];
                tache[0] = String.valueOf(rs.getInt("Tache_ID"));
                tache[1] = rs.getString("Titre");
                tache[2] = rs.getString("Description");
                tache[3] = rs.getDate("Date_debut").toString();
                tache[4] = rs.getDate("Date_fin").toString();
                tache[5] = String.valueOf(rs.getInt("User_ID"));
                tache[6] = String.valueOf(rs.getInt("Categorie_ID"));
                tache[7] = rs.getString("categorie");
                return tache;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tâche: " + e.getMessage());
        }
        
        return null;
    }
    
    // READ - Lister les tâches par catégorie
    public List<String[]> listerTachesParCategorie(int categorieId) {
        List<String[]> taches = new ArrayList<>();
        String sql = "SELECT t.Tache_ID, t.Titre, t.Description, t.Date_debut, t.Date_fin, " +
                    "u.Nom, u.Prenom " +
                    "FROM tache t " +
                    "JOIN utilisateur u ON t.User_ID = u.User_ID " +
                    "WHERE t.Categorie_ID = ? " +
                    "ORDER BY t.Date_debut";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, categorieId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String[] tache = new String[7];
                tache[0] = String.valueOf(rs.getInt("Tache_ID"));
                tache[1] = rs.getString("Titre");
                tache[2] = rs.getString("Description");
                tache[3] = rs.getDate("Date_debut").toString();
                tache[4] = rs.getDate("Date_fin").toString();
                tache[5] = rs.getString("Nom");
                tache[6] = rs.getString("Prenom");
                taches.add(tache);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des tâches par catégorie: " + e.getMessage());
        }
        
        return taches;
    }
    
    // UPDATE - Modifier une tâche
    public boolean modifierTache(int id, String titre, String description, 
                                String dateDebut, String dateFin, int categorieId) {
        String sql = "UPDATE tache SET Titre = ?, Description = ?, Date_debut = ?, Date_fin = ?, Categorie_ID = ? WHERE Tache_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, titre);
            pstmt.setString(2, description);
            pstmt.setDate(3, Date.valueOf(dateDebut));
            pstmt.setDate(4, Date.valueOf(dateFin));
            pstmt.setInt(5, categorieId);
            pstmt.setInt(6, id);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de tâche: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE - Supprimer une tâche
    public boolean supprimerTache(int id) {
        String sql = "DELETE FROM tache WHERE Tache_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de tâche: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Compter les tâches d'un utilisateur
    public int compterTachesUtilisateur(int userId) {
        String sql = "SELECT COUNT(*) FROM tache WHERE User_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des tâches: " + e.getMessage());
        }
        
        return 0;
    }
    
    // READ - Lister les tâches avec leurs étapes
    public List<String[]> listerTachesAvecEtapes() {
        List<String[]> taches = new ArrayList<>();
        String sql = "SELECT t.Tache_ID, t.Titre, t.Description, t.Date_debut, t.Date_fin, " +
                    "u.Nom, u.Prenom, c.nom as categorie, " +
                    "COUNT(et.Etape_ID) as nb_etapes, " +
                    "SUM(CASE WHEN et.statut = 'Terminé' THEN 1 ELSE 0 END) as etapes_terminees " +
                    "FROM tache t " +
                    "JOIN utilisateur u ON t.User_ID = u.User_ID " +
                    "JOIN categorie c ON t.Categorie_ID = c.Categorie_ID " +
                    "LEFT JOIN etape_tache et ON t.Tache_ID = et.Tache_ID " +
                    "GROUP BY t.Tache_ID " +
                    "ORDER BY t.Date_debut";
        
        try (Connection conn = ConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String[] tache = new String[10];
                tache[0] = String.valueOf(rs.getInt("Tache_ID"));
                tache[1] = rs.getString("Titre");
                tache[2] = rs.getString("Description");
                tache[3] = rs.getDate("Date_debut").toString();
                tache[4] = rs.getDate("Date_fin").toString();
                tache[5] = rs.getString("Nom");
                tache[6] = rs.getString("Prenom");
                tache[7] = rs.getString("categorie");
                tache[8] = String.valueOf(rs.getInt("nb_etapes"));
                tache[9] = String.valueOf(rs.getInt("etapes_terminees"));
                taches.add(tache);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des tâches avec étapes: " + e.getMessage());
        }
        
        return taches;
    }
}
