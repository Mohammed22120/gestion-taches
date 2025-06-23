package com.gestiontaches.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtapeTacheDAO {
    
    // CREATE - Ajouter une étape à une tâche
    public boolean ajouterEtape(int tacheId, String description, String statut) {
        String sql = "INSERT INTO etape_tache (Tache_ID, description, statut) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tacheId);
            pstmt.setString(2, description);
            pstmt.setString(3, statut);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'étape: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Lister toutes les étapes
    public List<String[]> listerEtapes() {
        List<String[]> etapes = new ArrayList<>();
        String sql = "SELECT et.Etape_ID, et.Tache_ID, et.description, et.statut, " +
                    "t.Titre as titre_tache " +
                    "FROM etape_tache et " +
                    "JOIN tache t ON et.Tache_ID = t.Tache_ID " +
                    "ORDER BY et.Tache_ID, et.Etape_ID";
        
        try (Connection conn = ConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String[] etape = new String[5];
                etape[0] = String.valueOf(rs.getInt("Etape_ID"));
                etape[1] = String.valueOf(rs.getInt("Tache_ID"));
                etape[2] = rs.getString("description");
                etape[3] = rs.getString("statut");
                etape[4] = rs.getString("titre_tache");
                etapes.add(etape);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des étapes: " + e.getMessage());
        }
        
        return etapes;
    }
    
    // READ - Lister les étapes d'une tâche spécifique
    public List<String[]> listerEtapesParTache(int tacheId) {
        List<String[]> etapes = new ArrayList<>();
        String sql = "SELECT Etape_ID, description, statut FROM etape_tache WHERE Tache_ID = ? ORDER BY Etape_ID";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tacheId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String[] etape = new String[3];
                etape[0] = String.valueOf(rs.getInt("Etape_ID"));
                etape[1] = rs.getString("description");
                etape[2] = rs.getString("statut");
                etapes.add(etape);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des étapes par tâche: " + e.getMessage());
        }
        
        return etapes;
    }
    
    // READ - Récupérer une étape par ID
    public String[] obtenirEtape(int id) {
        String sql = "SELECT et.Etape_ID, et.Tache_ID, et.description, et.statut, " +
                    "t.Titre as titre_tache " +
                    "FROM etape_tache et " +
                    "JOIN tache t ON et.Tache_ID = t.Tache_ID " +
                    "WHERE et.Etape_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String[] etape = new String[5];
                etape[0] = String.valueOf(rs.getInt("Etape_ID"));
                etape[1] = String.valueOf(rs.getInt("Tache_ID"));
                etape[2] = rs.getString("description");
                etape[3] = rs.getString("statut");
                etape[4] = rs.getString("titre_tache");
                return etape;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération d'étape: " + e.getMessage());
        }
        
        return null;
    }
    
 // READ - Compter les étapes par statut pour une tâche
    public int[] compterEtapesParStatut(int tacheId) {
        int[] compteurs = new int[3]; // [En cours, Terminé, En attente]
        String sql = "SELECT statut, COUNT(*) as nb FROM etape_tache WHERE Tache_ID = ? GROUP BY statut";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tacheId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String statut = rs.getString("statut");
                int count = rs.getInt("nb");
                
                switch (statut) {
                    case "En cours":
                        compteurs[0] = count;
                        break;
                    case "Terminé":
                        compteurs[1] = count;
                        break;
                    case "En attente":
                        compteurs[2] = count;
                        break;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des étapes par statut: " + e.getMessage());
        }
        
        return compteurs;
    }
    
    // UPDATE - Modifier une étape
    public boolean modifierEtape(int id, String description, String statut) {
        String sql = "UPDATE etape_tache SET description = ?, statut = ? WHERE Etape_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, description);
            pstmt.setString(2, statut);
            pstmt.setInt(3, id);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification d'étape: " + e.getMessage());
            return false;
        }
    }
    
    // UPDATE - Changer le statut d'une étape
    public boolean changerStatutEtape(int id, String nouveauStatut) {
        String sql = "UPDATE etape_tache SET statut = ? WHERE Etape_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nouveauStatut);
            pstmt.setInt(2, id);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du changement de statut d'étape: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE - Supprimer une étape
    public boolean supprimerEtape(int id) {
        String sql = "DELETE FROM etape_tache WHERE Etape_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression d'étape: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE - Supprimer toutes les étapes d'une tâche
    public boolean supprimerEtapesParTache(int tacheId) {
        String sql = "DELETE FROM etape_tache WHERE Tache_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tacheId);
            return pstmt.executeUpdate() >= 0; // Peut être 0 si aucune étape à supprimer
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression des étapes par tâche: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Calculer le pourcentage de progression d'une tâche
    public double calculerProgressionTache(int tacheId) {
        String sql = "SELECT COUNT(*) as total, " +
                    "SUM(CASE WHEN statut = 'Terminé' THEN 1 ELSE 0 END) as terminees " +
                    "FROM etape_tache WHERE Tache_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tacheId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int total = rs.getInt("total");
                int terminees = rs.getInt("terminees");
                
                if (total == 0) return 0.0;
                return (terminees * 100.0) / total;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du calcul de progression: " + e.getMessage());
        }
        
        return 0.0;
    }
    
    // READ - Vérifier si une tâche est complètement terminée
    public boolean tacheEstTerminee(int tacheId) {
        String sql = "SELECT COUNT(*) as total, " +
                    "SUM(CASE WHEN statut = 'Terminé' THEN 1 ELSE 0 END) as terminees " +
                    "FROM etape_tache WHERE Tache_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tacheId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int total = rs.getInt("total");
                int terminees = rs.getInt("terminees");
                
                return total > 0 && total == terminees;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de tâche terminée: " + e.getMessage());
        }
        
        return false;
    }
}