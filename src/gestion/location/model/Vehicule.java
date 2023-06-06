/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.location.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author digou
 */
public class Vehicule {
    private DatabaseConnection db;
    
    public static final String ETAT_DISPONIBLE="Disponnible";
    public static final String ETAT_LOUE="Loué";
    public static final String ETAT_EN_COURS="En cours de réparation";
    
    private int immatricule;
    private String constructeur;
    private String marque;
    private String etat;

    public Vehicule(int immatricule, String constructeur, String marque, String etat) {
        this.immatricule = immatricule;
        this.constructeur = constructeur;
        this.marque = marque;
        this.etat = etat;
    }

    public Vehicule(int immatricule, String constructeur, String marque) {
        this.immatricule = immatricule;
        this.constructeur = constructeur;
        this.marque = marque;
        this.etat = ETAT_DISPONIBLE;
    }
    
    public int getImmatricule() {
        return immatricule;
    }

    public void setImmatricule(int immatricule) {
        this.immatricule = immatricule;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return getImmatricule()+"";
    }
    
    public boolean AjouterVehicule(){
        try {
            db = DatabaseConnection.getInstance();
            String req="INSERT INTO `vehicule`(`immatricule`, `constructeur`, `marque`, `etat`) VALUES("
            + getImmatricule()+",'"+getConstructeur()+"','"+getMarque()+"','"+getEtat()+"')";
            
            Connection con=db.getConnection();
            Statement st = con.createStatement();
            
            int rs=st.executeUpdate(req);
            if(rs>0)
                System.out.println("ajout terminer");
            return true;
        } catch (SQLException ex) {
            if(ex instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("duplicate entry");
        } else
            Logger.getLogger(Park.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public void modifierVehicule(String etat){
            try {
                db = DatabaseConnection.getInstance();
                String req="UPDATE `vehicule` SET `etat`='"+etat+"' WHERE `immatricule`="+ getImmatricule()
                        +" AND `etat` LIKE 'Disponnible'";

                Connection con=db.getConnection();
                Statement st = con.createStatement();

                int rs=st.executeUpdate(req);
                if(rs>0){
                    System.out.println("Modification terminer");
                    this.setEtat(etat);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Park.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public void supprimerVehicule(){
        try {
            db = DatabaseConnection.getInstance();
            String req="DELETE FROM `vehicule` WHERE `immatricule`="
            +getImmatricule();
            
            Connection con=db.getConnection();
            Statement st = con.createStatement();
            
            int rs=st.executeUpdate(req);
            if(rs>0)
                System.out.println("suppression terminer");
        } catch (SQLException ex) {
            Logger.getLogger(Park.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean contain(int immat){
        return immat==this.getImmatricule();
    }
}
