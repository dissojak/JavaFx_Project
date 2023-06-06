/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.location.controler;

import application.Main;
import gestion.location.model.Client;
import gestion.location.model.Location;
import gestion.location.model.Park;
import gestion.location.model.Vehicule;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author digou
 */
public class FXMLNvLocationController implements Initializable {

    @FXML
    private ButtonBar barBtn;
    
    @FXML
    private TableView<Vehicule> tvVehicule;

    @FXML
    public TableView<Client> tvClient;
    
    @FXML
    private Button btnAjouter;
    
    @FXML
    private Label lblAlert;
    
    @FXML
    private AnchorPane formAnchorPane;

    @FXML
    private TextField txtDateDeb;

    @FXML
    private TextField txtDateFin;

    @FXML
    private TextField txtPrixTotal;

    @FXML
    private TextField txtPrixAvance;
    
    @FXML
    private Button btnAddClient;
    
    ArrayList<Vehicule> listVehicules;
    
    ObservableList<Client> clients = FXCollections.observableArrayList();

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            listVehicules =new Park().getListVehicule();
       
            SetVehicules();
            SetClients();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLNvLocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        tvClient.getSelectionModel().selectedItemProperty().addListener((observable) -> {
//            try {
//                tvClient.getItems().clear();
//                SetClients();
//            } catch (SQLException ex) {
//                Logger.getLogger(FXMLNvLocationController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
    }

    @FXML
    void ajoutLocation(ActionEvent event) {
        if(!btnAjouter.getText().equals("confirmer"))
            if(!test()){//test tableView non selectionné
                lblAlert.setVisible(true);
                lblAlert.setText("Il faut selectionner un client et une vehicule");
            }
            else{//passage au formulaire 2
                lblAlert.setVisible(false);
                btnAjouter.setText("confirmer");
                btnAddClient.setText("Retour");
                formAnchorPane.setPrefSize(226,264);
                formAnchorPane.setVisible(true);
                tvClient.setVisible(false);
                tvVehicule.setVisible(false);
            }
        else{
            if(!isEmpty()){//test les champs vides
            Client c = tvClient.getSelectionModel().selectedItemProperty().get();
            Vehicule v = tvVehicule.getSelectionModel().selectedItemProperty().get();
            String dateDeb = txtDateDeb.getText().trim();
            String dateFin = txtDateFin.getText().trim();
            int prixTotal=0;
            int prixAvance=0;
            try{
            prixTotal = Integer.parseInt(txtPrixTotal.getText().trim());
            prixAvance = Integer.parseInt(txtPrixAvance.getText().trim());
            }catch(NumberFormatException e){
                lblAlert.setText("inserer un entier");
                lblAlert.setVisible(true);
            }
            if(new Location(v,c,dateDeb,dateFin,prixTotal,prixAvance).addLocation()){
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.close();
                v.modifierVehicule(Vehicule.ETAT_LOUE);
            }
            }
            else{
                lblAlert.setText("vous devez remplir les champs");
                lblAlert.setVisible(true);
            }
        }
    }

     @FXML
    void addClient(ActionEvent event) {
        if(btnAddClient.getText().equals("Retour")){
            lblAlert.setVisible(false);
                btnAjouter.setText("Suivant");
                btnAddClient.setText("Nouveau Client");
//                formAnchorPane.setPrefSize(226,264);
                formAnchorPane.setVisible(false);
                tvClient.setVisible(true);
                tvVehicule.setVisible(true);
        }else
            try {
                 FXMLLoader fxmlloader = new FXMLLoader();
                 fxmlloader.setLocation(Main.class.getResource("view/FXMLNvClient.fxml"));
                 AnchorPane root =  fxmlloader.load();
                 Stage stage = new Stage();
                 stage.setTitle("Ajouter un client");
                 stage.setScene(new Scene(root));
                 stage.show();
             } catch (IOException ex) {
                 Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
                 System.out.println(ex.getStackTrace());
             }
    }
    
    private boolean test(){
        return tvVehicule.getSelectionModel().selectedItemProperty().get()!=null && 
                tvClient.getSelectionModel().selectedItemProperty().get()!=null;
    }
    
    private void SetVehicules() throws SQLException{
        tvVehicule.getItems().clear();
        ObservableList<Vehicule> vehicules = FXCollections.observableArrayList();
        
        TableColumn immatCol = new TableColumn("Immatricule");
        immatCol.setCellValueFactory(new PropertyValueFactory("immatricule"));
        TableColumn constuctCom = new TableColumn("Constructeur");
        constuctCom.setCellValueFactory(new PropertyValueFactory("constructeur"));
        TableColumn marqueCol = new TableColumn("Marque");
        marqueCol.setCellValueFactory(new PropertyValueFactory("marque"));
        TableColumn etatCol = new TableColumn("Etat du véhicule");
        etatCol.setCellValueFactory(new PropertyValueFactory("etat"));

        tvVehicule.getColumns().setAll(immatCol, constuctCom , marqueCol , etatCol);

        listVehicules.forEach((vehicule) -> {
            vehicules.add(vehicule);
        });
        
        tvVehicule.setItems(vehicules);
    }
    
    private void SetClients() throws SQLException{
        tvClient.getItems().clear();
        clients.clear();
        TableColumn cinCol = new TableColumn("CIN");
        cinCol.setCellValueFactory(new PropertyValueFactory("cin"));
        TableColumn nomCom = new TableColumn("Nom");
        nomCom.setCellValueFactory(new PropertyValueFactory("nom"));
        TableColumn prenomCol = new TableColumn("Prénom");
        prenomCol.setCellValueFactory(new PropertyValueFactory("prenom"));
        TableColumn adresseCol = new TableColumn("Adresse");
        adresseCol.setCellValueFactory(new PropertyValueFactory("adresse"));

        tvClient.getColumns().setAll(cinCol, nomCom , prenomCol , adresseCol);

        ArrayList<Client> listclients = Client.getAll();

        listclients.forEach((client) -> {
            clients.add(client);
        });

        tvClient.setItems(clients);
    }

    private boolean isEmpty(){
        return txtDateDeb.getText().trim().isEmpty() || txtDateFin.getText().trim().isEmpty()
                || txtPrixAvance.getText().trim().isEmpty() || txtPrixTotal.getText().trim().isEmpty();
    }

    @FXML
    void close(ActionEvent event) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
    }

}