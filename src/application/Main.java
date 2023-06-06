package application;
	
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author digou
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            
            StackPane root = new StackPane();
          Scene scene = new Scene(FXMLLoader.load(getClass().getResource("view/FXMLNvVehicule.fxml")));
//            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("view/FXMLNvLocation.fxml")));
//          Scene scene = new Scene(FXMLLoader.load(getClass().getResource("view/FXMLHome.fxml")));
//            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("view/FXMLNvClient.fxml")));
            
            primaryStage.setTitle("Home");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
