package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.TableInventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Konrad on 04-03-2016.
 */
public class AddInventoryController implements Initializable {
    public TextField tfEquipmentName;
    public TextField tfEquipmentBrand;
    public TextField tfEquipmentQuanitiy;
    public TextArea tfEquipmentInfo;
    public ComboBox cmbEquipmentPlace;

    public CreateScene createScene = new CreateScene();

    public static int id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> miejsce = FXCollections.observableArrayList(
                "Garaż", "Samochód", "Remiza", "Kuchnia", "Scena"
        );

        cmbEquipmentPlace.setItems(miejsce);
        tfEquipmentQuanitiy.setText("1");

    }

    public void actionAddEquipment(ActionEvent actionEvent) {

        changeColorGray();
        if(checkFill()){
            try {

                String brand = tfEquipmentBrand.getText();
                String quantity = tfEquipmentQuanitiy.getText();
                String info = tfEquipmentInfo.getText();
                String place = String.valueOf(cmbEquipmentPlace.getValue());

                if(brand.trim().isEmpty() || brand == null)
                    brand = " ";
                if(info.trim().isEmpty() || info == null)
                    info = " ";

                String sql1 = "INSERT INTO inwentarz " +
                        "VALUES(?,?,?,?,?,?)";

                PreparedStatement insert = DbConnection.con.prepareStatement(sql1);

                insert.setInt(1,id);
                insert.setString(2,tfEquipmentName.getText());
                insert.setString(3,brand);
                insert.setString(4,quantity);
                insert.setString(5,place);
                insert.setString(6,info);

                insert.executeUpdate();

                MainWindowController.dataInventory.add(new TableInventory(id++, tfEquipmentName.getText(), brand, Integer.parseInt(quantity)));
                MainWindowController.liczbaSprzetu++;
                insert.close();
                MainWindowController.lastEquipmmentId++;
                Stage stage =  (Stage) tfEquipmentQuanitiy.getScene().getWindow();
                createScene.setPrevStage(stage);
                createScene.swapScene("/Windows/AddInventoryWindow.fxml","Dodaj sprzęt");



            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{
            changeColorRed();
        }



    }

    public boolean checkFill(){
        if(tfEquipmentName.getText().trim().isEmpty() || tfEquipmentName.getText() == null)
            return false;
        else if(tfEquipmentQuanitiy.getText().trim().isEmpty() || tfEquipmentQuanitiy.getText() == null)
            return false;
        else if(cmbEquipmentPlace.getValue() == null)
            return false;
        else
            return true;
    }

    public void changeColorGray(){
        String borderColor = "-fx-border-color: lightgray";
        tfEquipmentName.setStyle(borderColor);
        tfEquipmentQuanitiy.setStyle(borderColor);
        cmbEquipmentPlace.setStyle(borderColor);
    }

    public void changeColorRed(){
        String borderColor = "-fx-border-color:red";
        if(tfEquipmentName.getText().trim().isEmpty() || tfEquipmentName.getText() == null)
            tfEquipmentName.setStyle(borderColor);
        if(tfEquipmentQuanitiy.getText().trim().isEmpty() || tfEquipmentName.getText() == null)
            tfEquipmentQuanitiy.setStyle(borderColor);
        if(cmbEquipmentPlace.getValue() == null)
            cmbEquipmentPlace.setStyle(borderColor);
    }

}
