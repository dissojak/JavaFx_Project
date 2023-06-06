/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.location.model;

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
public class Location {
    private Vehicule v;
    private Client c;
    private String datedeb;
    private String datefin;
    private int prixTotal;
    private int prixAvance;

    static DatabaseConnection db;
    
    private static ArrayList<Location> locationList;

    public Location(Vehicule v, Client c, String datedeb, String datefin, int prixTotal, int prixAcance) {
        this.v = v;
        this.c = c;
        this.datedeb = datedeb;
        this.datefin = datefin;
        this.prixTotal = prixTotal;
        this.prixAvance = prixAcance;
    }

    public Vehicule getV() {
        return v;
    }

    public void setV(Vehicule v) {
        this.v = v;
    }

    public Client getC() {
        return c;
    }

    public void setC(Client c) {
        this.c = c;
    }

    public String getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(String datedeb) {
        this.datedeb = datedeb;
    }

    public String getDatefin() {
        return datefin;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public int getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(int prixTotal) {
        this.prixTotal = prixTotal;
    }

    public int getPrixAcance() {
        return prixAvance;
    }

    public void setPrixAcance(int prixAcance) {
        this.prixAvance = prixAcance;
    }

    @Override
    public String toString() {
        return "Location : \n Véhicule[" + v + "] \n Client[" + c + "]\n datedeb:" + datedeb + ", datefin=" 
                + datefin + ", prixTotal=" + prixTotal + ", prixAcance=" + prixAvance;
    }
    
    public boolean addLocation(){
        try {
            db = DatabaseConnection.getInstance();
            String req="INSERT INTO `location`(`immatricule`, `cin`, `datedeb`, `datefin`, `prix_total`, `prix_avance`) VALUES ("
            + getV().getImmatricule()+",'"+getC().getCin()+"','"+getDatedeb()+"','"+getDatefin()+"', "+getPrixTotal()+" , "+getPrixAcance()+")";
            
            Connection con=db.getConnection();
            Statement st = con.createStatement();
            
            int rs=st.executeUpdate(req);
            if(rs>0)
                System.out.println("ajout terminer");
            return true;
        } catch (SQLException ex) {
            if(ex instanceof SQLIntegrityConstraintViolationException) {
                System.out.println("duplicate entry "+ex.getMessage());
                return false;
        } else
            Logger.getLogger(Park.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public void deleteLocation(){
        try {
            db = DatabaseConnection.getInstance();
            String req="DELETE FROM `location` WHERE `immatricule` = "+getV().getImmatricule()
                    +" AND `cin` LIKE "+getC().getCin();
            
            Connection con=db.getConnection();
            Statement st = con.createStatement();
            
            int rs=st.executeUpdate(req);
            if(rs>0){
                System.out.println("suppression terminer");
                getV().modifierVehicule(Vehicule.ETAT_DISPONIBLE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Park.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Location> getAll() throws SQLException{
        ArrayList<Location> list = new ArrayList<>();
        db = DatabaseConnection.getInstance();
        try {
            Connection con=db.getConnection();
            Statement st = con.createStatement();
            
            ResultSet rs = st.executeQuery("select * from location");
            
            while(rs.next()){
                Vehicule v = new Park().getVehByImmat(rs.getInt("immatricule"));
                Client c=Client.getClientByCin(rs.getString("cin"));
                System.out.println("Client "+ c.toString());
                Location l = new Location(v, c, rs.getString("datedeb"), rs.getString("datefin"),
                        rs.getInt("prix_total"), rs.getInt("prix_avance"));
                //System.out.println(l);
                list.add(l);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Park.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static ArrayList<Location> getLocationList() throws SQLException{
        if(locationList!=null)
            return locationList;
        else
            return getAll();
    }
    
    
    public boolean contain(String cin){
        return cin.equals(this.getC().getCin());
    }
}
