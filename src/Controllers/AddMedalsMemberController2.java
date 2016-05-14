package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.TableMedals;
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
public class AddMedalsMemberController2 implements Initializable {
    public TextField tfMedalsName;
    public TextArea taMedalsInfo;
    public DatePicker dpMedalsDate;

    public CreateScene createScene = new CreateScene();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void actionAddMedalMember(ActionEvent actionEvent) {

        changeColorGray();
        if(checkFill()){

            String info = taMedalsInfo.getText();
            if(info.trim().isEmpty() || info == null)
                info = " ";

            String sql = "INSERT INTO odznaczenia " +
                    "VALUES(?,?,?,?)";
            try {
                PreparedStatement insert = DbConnection.con.prepareStatement(sql);
                insert.setInt(1,EditMemberController.id);
                insert.setString(2,tfMedalsName.getText());
                insert.setString(3, String.valueOf(dpMedalsDate.getValue()));
                insert.setString(4,info);

                insert.executeUpdate();

                EditMemberController.dataMedals.add(new TableMedals(EditMemberController.id,tfMedalsName.getText(),String.valueOf(dpMedalsDate.getValue()),taMedalsInfo.getText()));

                Stage stage = (Stage) tfMedalsName.getScene().getWindow();
                insert.close();
                createScene.setPrevStage(stage);
                createScene.swapScene("/Windows/AddMedalsMemberWindow2.fxml","Dodaj odznaczenie");


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            changeColorRed();
    }

    public boolean checkFill(){
        if(tfMedalsName.getText().trim().isEmpty() || tfMedalsName.getText() == null)
            return false;
        else if(dpMedalsDate.getValue() == null)
            return false;
        else
            return true;
    }

    public void changeColorGray(){
        String borderColor = "-fx-border-color:lightgray";
        tfMedalsName.setStyle(borderColor);
        dpMedalsDate.setStyle(borderColor);
    }

    public void changeColorRed(){
        String borderColor = "-fx-border-color:red";
        if(tfMedalsName.getText().trim().isEmpty() || tfMedalsName.getText() == null)
            tfMedalsName.setStyle(borderColor);
        if(dpMedalsDate.getValue() == null)
            dpMedalsDate.setStyle(borderColor);
    }
}
