package com.gestiontaches.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {
    
    // CREATE - Ajouter un utilisateur
    public boolean ajouterUtilisateur(String nom, String prenom, String telephone, 
                                     String email, String sexe, String motDePasse) {
        String sql = "INSERT INTO utilisateur (Nom, Prenom, Telephone, Email, Sexe, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, telephone);
            pstmt.setString(4, email);
            pstmt.setString(5, sexe);
            pstmt.setString(6, motDePasse);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'utilisateur: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Lister tous les utilisateurs
    public List<String[]> listerUtilisateurs() {
        List<String[]> utilisateurs = new ArrayList<>();
        String sql = "SELECT User_ID, Nom, Prenom, Telephone, Email, Sexe FROM utilisateur ORDER BY Nom, Prenom";
        
        try (Connection conn = ConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String[] utilisateur = new String[6];
                utilisateur[0] = String.valueOf(rs.getInt("User_ID"));
                utilisateur[1] = rs.getString("Nom");
                utilisateur[2] = rs.getString("Prenom");
                utilisateur[3] = rs.getString("Telephone");
                utilisateur[4] = rs.getString("Email");
                utilisateur[5] = rs.getString("Sexe");
                utilisateurs.add(utilisateur);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs: " + e.getMessage());
        }
        
        return utilisateurs;
    }
    
    // READ - Récupérer un utilisateur par ID
    public String[] obtenirUtilisateur(int id) {
        String sql = "SELECT User_ID, Nom, Prenom, Telephone, Email, Sexe FROM utilisateur WHERE User_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String[] utilisateur = new String[6];
                utilisateur[0] = String.valueOf(rs.getInt("User_ID"));
                utilisateur[1] = rs.getString("Nom");
                utilisateur[2] = rs.getString("Prenom");
                utilisateur[3] = rs.getString("Telephone");
                utilisateur[4] = rs.getString("Email");
                utilisateur[5] = rs.getString("Sexe");
                return utilisateur;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération d'utilisateur: " + e.getMessage());
        }
        
        return null;
    }
    
    // READ - Connexion utilisateur
    public String[] connecterUtilisateur(String email, String motDePasse) {
        String sql = "SELECT User_ID, Nom, Prenom, Email FROM utilisateur WHERE Email = ? AND mot_de_passe = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, motDePasse);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String[] utilisateur = new String[4];
                utilisateur[0] = String.valueOf(rs.getInt("User_ID"));
                utilisateur[1] = rs.getString("Nom");
                utilisateur[2] = rs.getString("Prenom");
                utilisateur[3] = rs.getString("Email");
                return utilisateur;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion: " + e.getMessage());
        }
        
        return null;
    }
    
    // READ - Vérifier si email existe
    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM utilisateur WHERE Email = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification d'email: " + e.getMessage());
        }
        
        return false;
    }
    
    // UPDATE - Modifier un utilisateur
    public boolean modifierUtilisateur(int id, String nom, String prenom, 
                                      String telephone, String email, String sexe) {
        String sql = "UPDATE utilisateur SET Nom = ?, Prenom = ?, Telephone = ?, Email = ?, Sexe = ? WHERE User_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, telephone);
            pstmt.setString(4, email);
            pstmt.setString(5, sexe);
            pstmt.setInt(6, id);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification d'utilisateur: " + e.getMessage());
            return false;
        }
    }
    
    // UPDATE - Changer mot de passe
    public boolean changerMotDePasse(int id, String nouveauMotDePasse) {
        String sql = "UPDATE utilisateur SET mot_de_passe = ? WHERE User_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nouveauMotDePasse);
            pstmt.setInt(2, id);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du changement de mot de passe: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE - Supprimer un utilisateur
    public boolean supprimerUtilisateur(int id) {
        String sql = "DELETE FROM utilisateur WHERE User_ID = ?";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression d'utilisateur: " + e.getMessage());
            return false;
        }
    }
}