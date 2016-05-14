package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.TableTrainings;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Konrad on 05-03-2016.
 */
public class AddTrainingMemberController2 implements Initializable {
    public TextField tfTrainingName;
    public TextArea taTrainingInfo;
    public DatePicker dpTrainingDate;
    public TextField tfTrainingPlace;

    public CreateScene createScene = new CreateScene();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void actionAddTraining(ActionEvent actionEvent) {

        changeColorGray();
        if(checkFill()){

            String info = taTrainingInfo.getText();
            if(info.trim().isEmpty() || info == null)
                info = " ";

            String sql = "INSERT INTO szkolenia " +
                    "VALUES(?,?,?,?,?)";
            try {
                PreparedStatement insert = DbConnection.con.prepareStatement(sql);

                insert.setInt(1,EditMemberController.id);
                insert.setString(2,tfTrainingName.getText());
                insert.setString(3, String.valueOf(dpTrainingDate.getValue()));
                insert.setString(4,tfTrainingPlace.getText());
                insert.setString(5,info);

                insert.executeUpdate();

                EditMemberController.dataTrainings.add(new TableTrainings(AddMemberController.id,tfTrainingName.getText(),String.valueOf(dpTrainingDate.getValue()),tfTrainingPlace.getText()));

                Stage stage = (Stage) tfTrainingName.getScene().getWindow();
                insert.close();
                createScene.setPrevStage(stage);
                createScene.swapScene("/Windows/AddTrainingMemberWindow2.fxml","Dodaj szkolenie");


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else
            changeColorRed();
    }

    public boolean checkFill(){
        if(tfTrainingName.getText().trim().isEmpty() || tfTrainingName.getText() == null)
            return false;
        else if(dpTrainingDate.getValue() == null)
            return false;
        else if(tfTrainingPlace.getText().trim().isEmpty() || tfTrainingPlace.getText() == null)
            return false;
        else
            return true;
    }

    public void changeColorGray(){
        String borderColor = "-fx-border-color:lightgray";
        tfTrainingName.setStyle(borderColor);
        dpTrainingDate.setStyle(borderColor);
        tfTrainingPlace.setStyle(borderColor);
    }

    public void changeColorRed(){
        String borderColor = "-fx-border-color:red";
        if(tfTrainingName.getText().trim().isEmpty() || tfTrainingName.getText() == null)
            tfTrainingName.setStyle(borderColor);
        if(dpTrainingDate.getValue() == null)
            dpTrainingDate.setStyle(borderColor);
        if(tfTrainingPlace.getText().trim().isEmpty() || tfTrainingPlace.getText() == null)
            tfTrainingPlace.setStyle(borderColor);
    }
}
