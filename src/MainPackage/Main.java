package MainPackage;

import Controllers.MainWindowController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Windows/MainWindoww.fxml"));
        primaryStage.setTitle("Apikacja OSP");
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("app.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    DbConnection.con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
