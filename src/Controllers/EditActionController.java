package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.TableActions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
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
 * Created by Konrad on 04-03-2016.
 */
public class EditActionController implements Initializable {

    public TextField tfActionKind;
    public TextField tfActionTimeStart;
    public TextField tfActionTimeBack;
    public TextArea taActionInfo;
    public TextArea taActionPlace;
    public ComboBox cmbActionDriver;
    public ComboBox cmbActionLeader;
    public ComboBox cmbActionUnit1_1;
    public ComboBox cmbActionUnit1_2;
    public ComboBox cmbActionUnit2_1;
    public ComboBox cmbActionUnit2_2;
    public DatePicker dpActionDate;

    public ObservableList<String> data;
    public static int id;
    public static Stage mainStage;

    public CreateScene createScene = new CreateScene();

    private String rodzaj, adres, dataW, czasW, czasP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildData();
        rodzaj = tfActionKind.getText();
        adres = taActionPlace.getText();
        dataW = String.valueOf(dpActionDate.getValue());
        czasW = tfActionTimeStart.getText();
        czasP = tfActionTimeBack.getText();


    }

    public void buildData(){


        String sql1 = "SELECT * FROM czlonkowie";
        String sql2 = "SELECT * FROM wyjazdy WHERE id_wyjazdu = "+id;
        String sql3 = "SELECT * FROM uczestnicy_wyjazdu WHERE id_wyjazdu = "+id;

        try {

            data = FXCollections.observableArrayList();
            ResultSet rs = DbConnection.con.createStatement().executeQuery(sql1);
            while(rs.next()){
                data.add(rs.getString("imie") + " " + rs.getString("nazwisko"));
            }

            cmbActionDriver.setItems(data);
            cmbActionLeader.setItems(data);
            cmbActionUnit1_1.setItems(data);
            cmbActionUnit1_2.setItems(data);
            cmbActionUnit2_1.setItems(data);
            cmbActionUnit2_2.setItems(data);

            rs = DbConnection.con.createStatement().executeQuery(sql2);
            while(rs.next()){
                tfActionKind.setText(rs.getString("rodzaj"));
                tfActionTimeStart.setText(rs.getString("czas_wyjazdu"));
                tfActionTimeBack.setText(rs.getString("czas_powrotu"));
                dpActionDate.setValue(LocalDate.parse(rs.getString("data_wyjazdu")));
                taActionInfo.setText(rs.getString("dodatkowe_informacje"));
                taActionPlace.setText(rs.getString("miejsce_akcji"));
            }

            rs = DbConnection.con.createStatement().executeQuery(sql3);
            while(rs.next()){
                cmbActionDriver.setValue(rs.getString("kierowca"));
                cmbActionLeader.setValue(rs.getString("dowodca"));
                cmbActionUnit1_1.setValue(rs.getString("rota11"));
                cmbActionUnit1_2.setValue(rs.getString("rota12"));
                cmbActionUnit2_1.setValue(rs.getString("rota21"));
                cmbActionUnit2_2.setValue(rs.getString("rota22"));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionEditAction(ActionEvent actionEvent) {

        changeColorGray();
        if(checkFill()) {

            String czasPowrotu = tfActionTimeBack.getText();
            String czasWyjazdu = tfActionTimeStart.getText();
            String info = taActionInfo.getText();
            String place = taActionPlace.getText();
            String kierowca;
            String lider, rota11, rota12, rota21, rota22;

            if (cmbActionDriver.getValue() == null)
                kierowca = " ";
            else
                kierowca = String.valueOf(cmbActionDriver.getValue());

            if (cmbActionLeader.getValue() == null)
                lider = " ";
            else
                lider = String.valueOf(cmbActionLeader.getValue());

            if (cmbActionUnit1_1.getValue() == null)
                rota11 = " ";
            else
                rota11 = String.valueOf(cmbActionUnit1_1.getValue());

            if (cmbActionUnit1_2.getValue() == null)
                rota12 = " ";
            else
                rota12 = String.valueOf(cmbActionUnit1_2.getValue());

            if (cmbActionUnit2_1.getValue() == null)
                rota21 = " ";
            else
                rota21 = String.valueOf(cmbActionUnit2_1.getValue());

            if (cmbActionUnit2_2.getValue() == null)
                rota22 = " ";
            else
                rota22 = String.valueOf(cmbActionUnit2_2.getValue());

            String sql = "UPDATE wyjazdy SET rodzaj = ?, data_wyjazdu = ?, czas_wyjazdu = ?, czas_powrotu = ?, miejsce_akcji = ?, dodatkowe_informacje = ? WHERE id_wyjazdu = "+id;
            String sql2 = "UPDATE uczestnicy_wyjazdu SET kierowca = ?, dowodca = ?, rota11 = ?, rota12 = ?, rota21 = ?, rota22 = ? WHERE id_wyjazdu = "+id;

            try {
                PreparedStatement update1 = DbConnection.con.prepareStatement(sql);
                PreparedStatement update2 = DbConnection.con.prepareStatement(sql2);

                update1.setString(1,tfActionKind.getText());
                update1.setString(2, String.valueOf(dpActionDate.getValue()));
                update1.setString(3,tfActionTimeStart.getText());
                update1.setString(4,tfActionTimeBack.getText());
                update1.setString(5,taActionPlace.getText());
                update1.setString(6,taActionInfo.getText());

                update2.setString(1, String.valueOf(cmbActionDriver.getValue()));
                update2.setString(2, String.valueOf(cmbActionLeader.getValue()));
                update2.setString(3, String.valueOf(cmbActionUnit1_1.getValue()));
                update2.setString(4, String.valueOf(cmbActionUnit1_2.getValue()));
                update2.setString(5, String.valueOf(cmbActionUnit2_1.getValue()));
                update2.setString(6, String.valueOf(cmbActionUnit2_2.getValue()));

                update1.executeUpdate();
                update2.executeUpdate();


                update1.close();
                update2.close();

                if(!rodzaj.equals(tfActionKind.getText()) || !adres.equals(taActionPlace.getText()) || !dataW.equals(String.valueOf(dpActionDate.getValue())) || !czasW.equals(tfActionTimeStart.getText()) || !czasP.equals(tfActionTimeBack.getText())){
                    Stage stage = (Stage) tfActionTimeBack.getScene().getWindow();
                    createScene.setPrevStage(stage);
                    createScene.swapScene("../Windows/MainWindoww.fxml","Aplikacja OSP");
                    mainStage.close();
                }
                else{
                    Stage stage = (Stage) tfActionKind.getScene().getWindow();
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
        if(tfActionKind.getText().trim().isEmpty() || tfActionKind.getText() == null)
            return false;
        else if(dpActionDate.getValue() == null)
            return false;
        else
            return true;
    }

    public void changeColorGray(){
        String borderColor = "-fx-border-color: lightgray";
        tfActionKind.setStyle(borderColor);
        dpActionDate.setStyle(borderColor);
    }

    public void changeColorRed(){
        String borderColor = "-fx-border-color: red";
        if(tfActionKind.getText().trim().isEmpty() || tfActionKind.getText() == null)
            tfActionKind.setStyle(borderColor);
        if(dpActionDate.getValue() == null)
            dpActionDate.setStyle(borderColor);
    }
}
