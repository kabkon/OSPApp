package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.TableCars;
import com.sun.org.apache.regexp.internal.RE;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Konrad on 05-03-2016.
 */
public class EditCarController implements Initializable {
    public TextField tfCarBrand;
    public TextField tfCarType;
    public DatePicker dpCarProductionDate;
    public TextField tfCarRegistrationNumber;
    public DatePicker dpCarServiceDate;
    public TextField tfCarFuelTank;
    public TextField tfCarWaterTank;

    public static int id;
    public static Stage mainStage;

    public CreateScene createScene = new CreateScene();

    private String marka, typ, nrRej, terPrze, dataP;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buildData();

        marka = tfCarBrand.getText();
        typ = tfCarType.getText();
        nrRej = tfCarRegistrationNumber.getText();
        terPrze = String.valueOf(dpCarServiceDate.getValue());
        dataP = String.valueOf(dpCarProductionDate.getValue());

    }

    public void buildData(){



        String sql1 = "SELECT * FROM samochody WHERE id_samochodu = "+id;
        try {
            ResultSet rs = DbConnection.con.createStatement().executeQuery(sql1);
            while (rs.next()){
                tfCarBrand.setText(rs.getString("marka"));
                tfCarType.setText(rs.getString("typ"));
                tfCarFuelTank.setText(rs.getString("zbiornik_paliwa"));
                tfCarWaterTank.setText(rs.getString("zbiornik_wodny"));
                tfCarRegistrationNumber.setText(rs.getString("numer_rejestracyjny"));
                dpCarProductionDate.setValue(LocalDate.parse(rs.getString("data_produkcji")));
                dpCarServiceDate.setValue(LocalDate.parse(rs.getString("termin_przegladu")));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void actionEditCar(ActionEvent actionEvent) {

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

            String sql1 = "UPDATE samochody SET marka = ?, typ = ?, data_produkcji = ?, numer_rejestracyjny = ?, termin_przegladu = ?, zbiornik_paliwa = ?, zbiornik_wodny = ? WHERE id_samochodu = "+id;

            try {
                PreparedStatement update = DbConnection.con.prepareStatement(sql1);
                update.setString(1,tfCarBrand.getText());
                update.setString(2,type);
                update.setString(3, String.valueOf(dpCarProductionDate.getValue()));
                update.setString(4,tfCarRegistrationNumber.getText());
                update.setString(5, String.valueOf(dpCarServiceDate.getValue()));
                update.setString(6,fuel);
                update.setString(7,water);

                update.executeUpdate();


                update.close();

                if(!marka.equals(tfCarBrand.getText()) || !typ.equals(tfCarType.getText()) || !nrRej.equals(tfCarRegistrationNumber.getText()) || !terPrze.equals(String.valueOf(dpCarServiceDate.getValue())) || !dataP.equals(String.valueOf(dpCarProductionDate.getValue()))){
                    Stage stage = (Stage) tfCarFuelTank.getScene().getWindow();
                    createScene.setPrevStage(stage);
                    createScene.swapScene("../Windows/MainWindoww.fxml","Aplikacja OSP");
                    mainStage.close();
                }
                else{
                    Stage stage = (Stage) tfCarBrand.getScene().getWindow();
                    stage.close();
                }

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
