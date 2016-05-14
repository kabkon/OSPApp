package MainPackage;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
/**
 * Created by Konrad on 01-03-2016.
 */
public class CreateScene {

    public static Stage prevStage;


    public static void setPrevStage(Stage Stage){
        prevStage=Stage;
    }

    public void swapScene(String fxml, String title) throws IOException {
        double x,y;
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = new Stage();
        stage.setTitle(title);
        x = prevStage.getX();
        y = prevStage.getY();
        stage.setX(x);
        stage.setY(y);
        Scene newScene = new Scene(root);
        newScene.getStylesheets().addAll(this.getClass().getResource("app.css").toExternalForm());
        newScene.setFill(null);
        stage.setResizable(false);
        stage.setScene(newScene);
        prevStage.close();
        stage.show();

    }

    public void createScene(String fxml, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = new Stage();
        stage.setTitle(title);
        Scene newScene = new Scene(root);
        newScene.getStylesheets().addAll(this.getClass().getResource("app.css").toExternalForm());
        newScene.setFill(null);
        stage.setResizable(false);
        stage.setScene(newScene);
        stage.show();



    }
}
