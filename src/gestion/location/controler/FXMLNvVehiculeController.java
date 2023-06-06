/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.location.controler;

import gestion.location.model.Vehicule;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author digou
 */
public class FXMLNvVehiculeController implements Initializable {

    @FXML
    private TextField txtImmat;

    @FXML
    private TextField txtMarque;

    @FXML
    private TextField txtConstructeur;

    @FXML
    private Button btnEnregistrer;
    
    @FXML
    private Label lblAlert;
    
    Vehicule vehicule;
    
    @FXML
    void Enregistrer(ActionEvent event) {
        int immat;
        String constructeur;
        String marque;
        
        if(!isEmpty()){
            immat = Integer.valueOf(txtImmat.getText().trim());
            constructeur = txtConstructeur.getText().trim();
            marque = txtMarque.getText().trim();
            vehicule = new Vehicule(immat, constructeur, marque);
            if(vehicule.AjouterVehicule()){
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
            else{
                lblAlert.setText("erreur de connexion");
                lblAlert.setVisible(true);
            }
        }
        else{
            lblAlert.setText("Il faut remplir les champs");
            lblAlert.setVisible(true);
       }
   }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private boolean isEmpty(){
        return txtConstructeur.getText().trim().isEmpty() || txtImmat.getText().trim().isEmpty()
                || txtMarque.getText().trim().isEmpty();
    }
    
    @FXML
    void close(ActionEvent event) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
    }
}