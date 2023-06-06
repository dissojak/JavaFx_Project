/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.location.controler;

import gestion.location.model.Park;
import gestion.location.model.Vehicule;
import gestion.location.model.Client;
import gestion.location.model.Location;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author digou
 */
public class FXMLHomeController implements Initializable {
    //les bouttons
    @FXML
    private Button btnNvAcquisition;

    @FXML
    private Button btnModifVehicule;

    @FXML
    private Button btnSuppVehicule;

    //bouttons Location
    @FXML
    private Button btnNewLocation;

    @FXML
    private Button btnFinLocation;

    @FXML
    private Button btnConsultLocation;

    @FXML
    private Button btnConsultByClient;

    //tableview
    @FXML
    private TableView<Vehicule> tvConsult;

    @FXML
    private TableView<Location> tvConsultLocation;

    Park park=new Park();
    ArrayList<Location> locationList;
    
    @FXML
    private ComboBox<String> cBoxClients;

    //functions
    @FXML
    void AjoutLocation(ActionEvent event) {
        try {
             FXMLLoader fxmlloader = new FXMLLoader();
             fxmlloader.setLocation(Main.class.getResource("view/FXMLNvLocation.fxml"));
             AnchorPane root =  fxmlloader.load();
             Stage stage = new Stage();
             stage.setTitle("Ajouter une location");
             stage.setScene(new Scene(root));
             stage.show();
         } catch (IOException ex) {
             Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println(ex.getStackTrace());
         }
    }

     @FXML
    void ajouterVehicule(ActionEvent event) throws SQLException {
         try {
             FXMLLoader fxmlloader = new FXMLLoader();
             fxmlloader.setLocation(Main.class.getResource("view/FXMLNvVehicule.fxml"));
             AnchorPane root =  fxmlloader.load();
             Stage stage = new Stage();
             stage.setTitle("Ajouter une véhicule");
             stage.setScene(new Scene(root));
             stage.show();
         } catch (IOException ex) {
             Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println(ex.getStackTrace());
         }
    }

    @FXML
    void ConsultationPark(ActionEvent event) throws SQLException {
        tvConsult.setVisible(true);
        tvConsult.setEditable(true);

        SetVehicules();
        System.out.println(tvConsult.getItems().toString());
    }

    @FXML
    void modifierEtat(ActionEvent event) throws SQLException {
        tvConsult.getSelectionModel().selectedItemProperty().get().modifierVehicule(Vehicule.ETAT_EN_COURS);
        repaintVehiculesTable();
    }

    @FXML
    void supprimerVehicule(ActionEvent event) throws SQLException {
        Vehicule v = tvConsult.getSelectionModel().selectedItemProperty().get();
        v.supprimerVehicule();
        park.getListVehicule().remove(v);
        repaintVehiculesTable();
    }

    @FXML
    void finLocation(ActionEvent event) {
         Location location = tvConsultLocation.getSelectionModel().selectedItemProperty().get();
         location.deleteLocation();
         location.getV().modifierVehicule(Vehicule.ETAT_DISPONIBLE);
         repaintLocationTable();
         repaintVehiculesTable();
    }
    
    @FXML
    void getVehiculeLoue(ActionEvent event) {
        tvConsultLocation.setVisible(true);
        tvConsultLocation.setEditable(true);
        try {
            setLocations(Location.getLocationList());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void getVehiculeLoueByClient(ActionEvent event) {
        tvConsultLocation.setVisible(true);
        tvConsultLocation.setEditable(true);
        cBoxClients.setVisible(true);
        ArrayList<Client> listClient = null;
        try {
            listClient = Client.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String> list = new ArrayList<>();
        for(Client cin : listClient)
            list.add(cin.toString());
        cBoxClients.getItems().setAll(list);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvConsult.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnModifVehicule.setDisable(false);
            btnSuppVehicule.setDisable(false);
        });
        tvConsultLocation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnFinLocation.setDisable(false);
        });
        
        cBoxClients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tvConsultLocation.getItems().clear();
            ArrayList<Location> locationList;
            try {
                locationList = Location.getLocationList();
                ArrayList<Location> newList=new ArrayList<>();
                for(Location l : locationList)
                    if(l.contain(newValue))
                        newList.add(l);
                setLocations(newList);

            } catch (SQLException ex) {
                Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
    }


    private void SetVehicules() throws SQLException{
        ObservableList<Vehicule> vehicules = FXCollections.observableArrayList();
        
        TableColumn immatCol = new TableColumn("Immatricule");
        immatCol.setCellValueFactory(new PropertyValueFactory("immatricule"));
        TableColumn constuctCom = new TableColumn("Constructeur");
        constuctCom.setCellValueFactory(new PropertyValueFactory("constructeur"));
        TableColumn marqueCol = new TableColumn("Marque");
        marqueCol.setCellValueFactory(new PropertyValueFactory("marque"));
        TableColumn etatCol = new TableColumn("Etat du véhicule");
        etatCol.setCellValueFactory(new PropertyValueFactory("etat"));

        tvConsult.getColumns().setAll(immatCol, constuctCom , marqueCol , etatCol);
        ArrayList<Vehicule> list= park.getListVehicule();

        list.forEach((vehicule) -> {
            vehicules.add(vehicule);
        });
        
        tvConsult.setItems(vehicules);
    }

    private void setLocations(ArrayList<Location> locationList) throws SQLException{
        ObservableList<Location> locations = FXCollections.observableArrayList();

        TableColumn immatCol = new TableColumn("Immatricule");
        immatCol.setCellValueFactory(new PropertyValueFactory("v"));
        TableColumn clientCol = new TableColumn("Client");
        clientCol.setCellValueFactory(new PropertyValueFactory("c"));
        TableColumn dateDebCol = new TableColumn("Date Debut");
        dateDebCol.setCellValueFactory(new PropertyValueFactory("datedeb"));
        TableColumn dateFinCol = new TableColumn("Date Fin");
        dateFinCol.setCellValueFactory(new PropertyValueFactory("datefin"));
        TableColumn prixTotalCol = new TableColumn("Prix Total");
        prixTotalCol.setCellValueFactory(new PropertyValueFactory("prixTotal"));
        TableColumn prixAvanceCol = new TableColumn("Prix Avance");
        prixAvanceCol.setCellValueFactory(new PropertyValueFactory("prixAvance"));
        
        
        tvConsultLocation.getColumns().setAll(immatCol, clientCol , dateDebCol , dateFinCol , prixTotalCol , prixAvanceCol);
//        locationList= Location.getAll();

        locationList.forEach((location) -> {
            locations.add(location);
            System.out.println(location);
        });

        tvConsultLocation.setItems(locations);
    }

    private void repaintVehiculesTable(){
        tvConsult.getItems().clear();
        try {
            SetVehicules();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void repaintLocationTable(){
        tvConsultLocation.getItems().clear();
        try {
            setLocations(Location.getLocationList());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}