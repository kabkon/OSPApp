package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.TableInventory;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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
public class AddInventoryMemberController2 implements Initializable {
    public TextField tfInventoryMemberName;
    public TextField tfInventoryMemberQuantity;
    public TextArea taInventoryMemberInfo;

    public CreateScene createScene = new CreateScene();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfInventoryMemberQuantity.setText("1");

    }

    public void actionAddInventoryMember(ActionEvent actionEvent) {
        changeColorGray();
        if(checkFill()){

            String info;
            if(taInventoryMemberInfo.getText().trim().isEmpty() || taInventoryMemberInfo.getText() == null)
                info = " ";
            else
                info = taInventoryMemberInfo.getText();

            try {
                String sql = "INSERT INTO inwentarz_czlonka " +
                        "VALUES(?,?,?,?)";

                PreparedStatement insert = DbConnection.con.prepareStatement(sql);
                insert.setInt(1,EditMemberController.id);
                insert.setString(2,tfInventoryMemberName.getText());
                insert.setInt(3, Integer.parseInt(tfInventoryMemberQuantity.getText()));
                insert.setString(4,info);

                insert.executeUpdate();

                EditMemberController.dataInventory.add(new TableInventory(tfInventoryMemberName.getText(),EditMemberController.id,Integer.parseInt(tfInventoryMemberQuantity.getText()),taInventoryMemberInfo.getText()));

                Stage stage = (Stage) tfInventoryMemberName.getScene().getWindow();
                insert.close();
                createScene.setPrevStage(stage);
                createScene.swapScene("/Windows/AddInventoryMemberWindow2.fxml","Dodaj wyposażenie członka");


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else
            changeColorRed();
    }

    public boolean checkFill(){
       if(tfInventoryMemberName.getText().trim().isEmpty() || tfInventoryMemberName.getText() == null)
           return false;
        else if(tfInventoryMemberQuantity.getText().trim().isEmpty() || tfInventoryMemberQuantity.getText() == null)
           return false;
        else return true;
    }

    public void changeColorGray(){
        String borderColor = "-fx-border-color:lightgray";
        tfInventoryMemberName.setStyle(borderColor);
        tfInventoryMemberQuantity.setStyle(borderColor);
    }

    public void changeColorRed(){
        String borderColor = "-fx-border-color:red";
        if(tfInventoryMemberName.getText().trim().isEmpty() || tfInventoryMemberName.getText() == null)
            tfInventoryMemberName.setStyle(borderColor);
        if(tfInventoryMemberQuantity.getText().trim().isEmpty() || tfInventoryMemberQuantity.getText() == null)
            tfInventoryMemberQuantity.setStyle(borderColor);
    }
}
