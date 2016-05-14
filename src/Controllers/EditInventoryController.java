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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Konrad on 04-03-2016.
 */
public class EditInventoryController implements Initializable {

    public TextField tfEquipmentName;
    public TextField tfEquipmentBrand;
    public TextField tfEquipmentQuanitiy;
    public TextArea tfEquipmentInfo;
    public ComboBox cmbEquipmentPlace;

    public CreateScene createScene = new CreateScene();

    public static int id;
    public static Stage mainStage;

    private String nazwa,marka,ilosc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> miejsce = FXCollections.observableArrayList(
                "Garaż", "Samochód", "Remiza", "Kuchnia", "Scena"
        );

        cmbEquipmentPlace.setItems(miejsce);

        buildData();

        nazwa = tfEquipmentName.getText();
        marka = tfEquipmentBrand.getText();
        ilosc = tfEquipmentQuanitiy.getText();
    }

    public void buildData() {



        String sql1 = "SELECT * FROM inwentarz WHERE id_sprzetu = " + id;
        try {
            ResultSet rs = DbConnection.con.createStatement().executeQuery(sql1);
            while (rs.next()) {
                tfEquipmentName.setText(rs.getString("nazwa"));
                tfEquipmentBrand.setText(rs.getString("marka"));
                tfEquipmentQuanitiy.setText(String.valueOf(rs.getInt("ilosc")));
                tfEquipmentInfo.setText(rs.getString("dodatkowe_info"));
                cmbEquipmentPlace.setValue(rs.getString("miejsce"));

            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionEditEquipment(ActionEvent actionEvent) {

        changeColorGray();
        if(checkFill()){

            String sql1 = "UPDATE inwentarz SET nazwa = ?, marka = ?, ilosc = ?, miejsce = ?, dodatkowe_info = ? WHERE id_sprzetu = "+id;
            try {
                String brand = tfEquipmentBrand.getText();
                String info = tfEquipmentInfo.getText();
                if(tfEquipmentBrand.getText().trim().isEmpty() || tfEquipmentBrand.getText() == null)
                    brand = " ";
                if(tfEquipmentInfo.getText().trim().isEmpty() || tfEquipmentInfo.getText() == null)
                    info = " ";

                PreparedStatement update = DbConnection.con.prepareStatement(sql1);

                update.setString(1,tfEquipmentName.getText());
                update.setString(2,brand);
                update.setInt(3, Integer.parseInt(tfEquipmentQuanitiy.getText()));
                update.setString(4, String.valueOf(cmbEquipmentPlace.getValue()));
                update.setString(5,info);

                update.executeUpdate();

                update.close();

                if(!nazwa.equals(tfEquipmentName.getText()) || marka.equals(tfEquipmentBrand.getText()) || ilosc.equals(tfEquipmentQuanitiy.getText())){
                    Stage stage = (Stage) tfEquipmentInfo.getScene().getWindow();
                    createScene.setPrevStage(stage);
                    createScene.swapScene("../Windows/MainWindoww.fxml","Aplikacja OSP");
                    mainStage.close();
                }
                else{
                    Stage stage = (Stage) tfEquipmentBrand.getScene().getWindow();
                    stage.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            tfEquipmentName.setStyle("-fx-border-color: red");
            tfEquipmentQuanitiy.setStyle("-fx-border-color: red");
            cmbEquipmentPlace.setStyle("-fx-border-color: red");
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
}
