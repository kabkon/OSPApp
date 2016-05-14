package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.TableCars;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
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
public class AddCarController implements Initializable {
    public TextField tfCarBrand;
    public TextField tfCarType;
    public DatePicker dpCarProductionDate;
    public TextField tfCarRegistrationNumber;
    public DatePicker dpCarServiceDate;
    public TextField tfCarFuelTank;
    public TextField tfCarWaterTank;

    public static int id;

    public CreateScene createScene = new CreateScene();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void actionAddCar(ActionEvent actionEvent) {

        changeColorGray();
        if(checkFill()){
            String type = tfCarType.getText();
            String fuel = tfCarFuelTank.getText();
            String water = tfCarWaterTank.getText();

            if(type.trim().isEmpty() || type == null)
                type = " ";
            if(fuel.trim().isEmpty() || fuel == null)
                fuel = " ";
            if(water.trim().isEmpty() || water == null)
                water = " ";

            String sql1 = "INSERT INTO samochody " +
                    "VALUES(?,?,?,?,?,?,?,?)";

            try {
                PreparedStatement insert = DbConnection.con.prepareStatement(sql1);
                insert.setInt(1, id);
                insert.setString(2,tfCarBrand.getText());
                insert.setString(3,type);
                insert.setString(4, String.valueOf(dpCarProductionDate.getValue()));
                insert.setString(5,tfCarRegistrationNumber.getText());
                insert.setString(6, String.valueOf(dpCarServiceDate.getValue()));
                insert.setString(7,fuel);
                insert.setString(8,water);

                insert.executeUpdate();

                MainWindowController.dataCars.add(new TableCars(id++,tfCarBrand.getText(),type,tfCarRegistrationNumber.getText(),String.valueOf(dpCarServiceDate.getValue()),String.valueOf(dpCarProductionDate.getValue())));

                MainWindowController.lastCarId++;
                Stage stage = (Stage) tfCarBrand.getScene().getWindow();
                insert.close();
                createScene.setPrevStage(stage);
                createScene.swapScene("/Windows/AddCarWindow.fxml", "Dodaj samochód");



            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else
            changeColorRed();

    }

    public boolean checkFill(){
        if(tfCarBrand.getText().trim().isEmpty() || tfCarBrand.getText() == null)
            return false;
        else if(dpCarProductionDate.getValue() == null)
            return false;
        else if(tfCarRegistrationNumber.getText().trim().isEmpty() || tfCarRegistrationNumber.getText() == null)
            return false;
        else if(dpCarServiceDate.getValue() == null)
            return false;
        else
            return true;
    }

    public void changeColorGray(){
        String borderColor = "-fx-border-color:lightgray";
        tfCarBrand.setStyle(borderColor);
        dpCarProductionDate.setStyle(borderColor);
        dpCarServiceDate.setStyle(borderColor);
        tfCarRegistrationNumber.setStyle(borderColor);
    }

    public void changeColorRed(){
        String borderColor = "-fx-border-color:red";
        if(tfCarBrand.getText().trim().isEmpty() || tfCarBrand.getText() == null)
            tfCarBrand.setStyle(borderColor);
        if(dpCarProductionDate.getValue() == null)
            dpCarProductionDate.setStyle(borderColor);
        if(tfCarRegistrationNumber.getText().trim().isEmpty() || tfCarRegistrationNumber.getText() == null)
            tfCarRegistrationNumber.setStyle(borderColor);
        if(dpCarServiceDate.getValue() == null)
            dpCarServiceDate.setStyle(borderColor);
    }
}
