/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.location.model;

import static gestion.location.model.Location.db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author digou
 */
public class Client {
    private String cin;
    private String nom;
    private String prenom;
    private String adresse;
    
    public static ArrayList<Client> listClients;

    public Client(String cin, String nom, String prenom, String adresse) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return getCin() + "";
    }
    
    public boolean add(){
        try {
            db = DatabaseConnection.getInstance();
            String req="INSERT INTO `client`(`cin`, `nom`, `prenom`, `adresse`) VALUES('"
            + getCin()+"','"+getNom()+"','"+getPrenom()+"','"+getAdresse()+"')";
            
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
    
    public static ArrayList<Client> getAll() throws SQLException{
        ArrayList<Client> list = new ArrayList<>();
        db = DatabaseConnection.getInstance();
        try {
            Connection con=db.getConnection();
            Statement st = con.createStatement();
            
            ResultSet rs = st.executeQuery("select * from client");
            
            while(rs.next()){
                Client c = new Client(rs.getString("cin"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"));
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public boolean contain(String cin){
        return cin.equals(this.getCin());
    }
    
    public static Client getClientByCin(String cin) throws SQLException{
        listClients=getAll();
        for(Client c: listClients)
            if(c.contain(cin))
                return c;
    return null;
    }
    
}
