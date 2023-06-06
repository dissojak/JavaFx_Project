/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.location.controler;

import gestion.location.model.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author digou
 */
public class FXMLNvClientController implements Initializable {

     @FXML
    private TextField txtCin;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtAdresse;

    @FXML
    private Label lblAlert;

    @FXML
    void Ajouter(ActionEvent event) {
        if(isEmpty()){
            lblAlert.setText("Il faut remplir les champs");
            lblAlert.setVisible(true);
        }else{
            String cin=txtCin.getText().trim();
            String nom=txtNom.getText().trim();
            String prenom=txtPrenom.getText().trim();
            String adresse=txtAdresse.getText().trim();
            if(new Client(cin,nom,prenom,adresse).add()){
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
            
    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private boolean isEmpty(){
        return txtCin.getText().trim().isEmpty() && txtNom.getText().trim().isEmpty()
                && txtPrenom.getText().trim().isEmpty() && txtAdresse.getText().trim().isEmpty();
    }
}
