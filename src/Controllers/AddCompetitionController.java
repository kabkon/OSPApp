package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import MainPackage.Main;
import Tables.TableCompetitions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Konrad on 05-03-2016.
 */
public class AddCompetitionController implements Initializable {
    public DatePicker dpCompetitionDate;
    public TextField tfCompetitionKind;
    public TextField tfCompetitionPlace;
    public TextField tfCompetitionPosition;
    public TextField tfCompetitionDirector;
    public ComboBox cmbCompetitionPosition1;
    public ComboBox cmbCompetitionPosition2;
    public ComboBox cmbCompetitionPosition3;
    public ComboBox cmbCompetitionPosition4;
    public ComboBox cmbCompetitionPosition5;
    public ComboBox cmbCompetitionPosition6;
    public ComboBox cmbCompetitionPosition7;
    public ComboBox cmbCompetitionPosition11;
    public ComboBox cmbCompetitionPosition21;
    public ComboBox cmbCompetitionPosition31;
    public ComboBox cmbCompetitionPosition41;
    public ComboBox cmbCompetitionPosition51;
    public ComboBox cmbCompetitionPosition61;
    public ComboBox cmbCompetitionPosition71;

    public ObservableList<String> data;

    public CreateScene createScene = new CreateScene();

    public static int id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            buildData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buildData() throws SQLException {
        String sql = "SELECT * FROM czlonkowie";
        data = FXCollections.observableArrayList();

        ResultSet rs = DbConnection.con.createStatement().executeQuery(sql);
        while (rs.next()){
            data.add(rs.getString("imie") + " " + rs.getString("nazwisko"));
        }

        cmbCompetitionPosition1.setItems(data);
        cmbCompetitionPosition2.setItems(data);
        cmbCompetitionPosition3.setItems(data);
        cmbCompetitionPosition4.setItems(data);
        cmbCompetitionPosition5.setItems(data);
        cmbCompetitionPosition6.setItems(data);
        cmbCompetitionPosition7.setItems(data);
        cmbCompetitionPosition11.setItems(data);
        cmbCompetitionPosition21.setItems(data);
        cmbCompetitionPosition31.setItems(data);
        cmbCompetitionPosition41.setItems(data);
        cmbCompetitionPosition51.setItems(data);
        cmbCompetitionPosition61.setItems(data);
        cmbCompetitionPosition71.setItems(data);

        rs.close();
    }

    public void actionAddCompetition(ActionEvent actionEvent) {

        changeColorGray();
        if(checkFill()){

            String pos1,pos2,pos3,pos4,pos5,pos6,pos7,pos11,pos21,pos31,pos41,pos51,pos61,pos71,kierownik;
            if(cmbCompetitionPosition1.getValue() == null)
                pos1 = " ";
            else
                pos1 = String.valueOf(cmbCompetitionPosition1.getValue());
            if(cmbCompetitionPosition2.getValue() == null)
                pos2 = " ";
            else
                pos2 = String.valueOf(cmbCompetitionPosition2.getValue());
            if(cmbCompetitionPosition3.getValue() == null)
                pos3 = " ";
            else
                pos3 = String.valueOf(cmbCompetitionPosition3.getValue());
            if(cmbCompetitionPosition4.getValue() == null)
                pos4 = " ";
            else
                pos4 = String.valueOf(cmbCompetitionPosition4.getValue());
            if(cmbCompetitionPosition5.getValue() == null)
                pos5 = " ";
            else
                pos5 = String.valueOf(cmbCompetitionPosition5.getValue());
            if(cmbCompetitionPosition6.getValue() == null)
                pos6 = " ";
            else
                pos6 = String.valueOf(cmbCompetitionPosition6.getValue());
            if(cmbCompetitionPosition7.getValue() == null)
                pos7 = " ";
            else
                pos7 = String.valueOf(cmbCompetitionPosition7.getValue());
            if(cmbCompetitionPosition11.getValue() == null)
                pos11 = " ";
            else
                pos11 = String.valueOf(cmbCompetitionPosition11.getValue());
            if(cmbCompetitionPosition21.getValue() == null)
                pos21 = " ";
            else
                pos21 = String.valueOf(cmbCompetitionPosition21.getValue());
            if(cmbCompetitionPosition31.getValue() == null)
                pos31 = " ";
            else
                pos31 = String.valueOf(cmbCompetitionPosition31.getValue());
            if(cmbCompetitionPosition41.getValue() == null)
                pos41 = " ";
            else
                pos41 = String.valueOf(cmbCompetitionPosition41.getValue());
            if(cmbCompetitionPosition51.getValue() == null)
                pos51 = " ";
            else
                pos51 = String.valueOf(cmbCompetitionPosition51.getValue());
            if(cmbCompetitionPosition61.getValue() == null)
                pos61 = " ";
            else
                pos61 = String.valueOf(cmbCompetitionPosition61.getValue());
            if(cmbCompetitionPosition71.getValue() == null)
                pos71 = " ";
            else
                pos71 = String.valueOf(cmbCompetitionPosition71.getValue());
            if(tfCompetitionDirector.getText().trim().isEmpty() || tfCompetitionDirector.getText() == null)
                kierownik = " ";
            else
                kierownik = tfCompetitionDirector.getText();

            String sql1 = "INSERT INTO zawody " +
                    "VALUES(?,?,?,?,?,?)";

            String sql2 = "INSERT INTO uczestnicy_sztafeta " +
                    "VALUES(?,?,?,?,?,?,?,?)";
            String sql3 = "INSERT INTO uczestnicy_bojowka " +
                    "VALUES(?,?,?,?,?,?,?,?)";

            try {
                PreparedStatement insert1 = DbConnection.con.prepareStatement(sql1);
                PreparedStatement insert2 = DbConnection.con.prepareStatement(sql2);
                PreparedStatement insert3 = DbConnection.con.prepareStatement(sql3);

                insert1.setInt(1,id);
                insert1.setString(2,tfCompetitionKind.getText());
                insert1.setString(3,tfCompetitionPlace.getText());
                insert1.setString(4, String.valueOf(dpCompetitionDate.getValue()));
                insert1.setString(5,tfCompetitionPosition.getText());
                insert1.setString(6,kierownik);

                insert2.setInt(1,id);
                insert2.setString(2,pos1);
                insert2.setString(3,pos2);
                insert2.setString(4,pos3);
                insert2.setString(5,pos4);
                insert2.setString(6,pos5);
                insert2.setString(7,pos6);
                insert2.setString(8,pos7);

                insert3.setInt(1,id);
                insert3.setString(2,pos11);
                insert3.setString(3,pos21);
                insert3.setString(4,pos31);
                insert3.setString(5,pos41);
                insert3.setString(6,pos51);
                insert3.setString(7,pos61);
                insert3.setString(8,pos71);

                insert1.executeUpdate();
                insert2.executeUpdate();
                insert3.executeUpdate();

                MainWindowController.dataCompetitions.add(new TableCompetitions(id++,tfCompetitionKind.getText(),String.valueOf(dpCompetitionDate.getValue()),tfCompetitionPlace.getText(),Integer.parseInt(tfCompetitionPosition.getText())));
                MainWindowController.liczbaZawodow++;
                MainWindowController.lastCompetitionId++;
                Stage stage = (Stage) tfCompetitionKind.getScene().getWindow();
                insert1.close();
                insert2.close();
                insert3.close();
                createScene.setPrevStage(stage);
                createScene.swapScene("/Windows/AddCompetitionWindow.fxml","Dodaj zawody");



            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            changeColorRed();


    }

    public boolean checkFill(){
        if(tfCompetitionKind.getText().trim().isEmpty() || tfCompetitionKind.getText() == null)
            return false;
        else if(tfCompetitionPlace.getText().trim().isEmpty() || tfCompetitionPlace.getScene() == null)
            return false;
        else if(dpCompetitionDate.getValue() == null)
            return false;
        else if(tfCompetitionPosition.getText().trim().isEmpty() || tfCompetitionPlace.getText() == null)
            return false;
        else
            return true;
    }

    public void changeColorGray(){
        String borderColor = "-fx-border-color:lightgray";
        tfCompetitionKind.setStyle(borderColor);
        tfCompetitionPlace.setStyle(borderColor);
        tfCompetitionPosition.setStyle(borderColor);
        dpCompetitionDate.setStyle(borderColor);
    }

    public void changeColorRed(){
        String borderColor = "-fx-border-color:red";
        if(tfCompetitionKind.getText().trim().isEmpty() || tfCompetitionKind.getText() == null)
            tfCompetitionKind.setStyle(borderColor);
        if(tfCompetitionPlace.getText().trim().isEmpty() || tfCompetitionPlace.getScene() == null)
            tfCompetitionPlace.setStyle(borderColor);
        if(dpCompetitionDate.getValue() == null)
            dpCompetitionDate.setStyle(borderColor);
        if(tfCompetitionPosition.getText().trim().isEmpty() || tfCompetitionPlace.getText() == null)
            tfCompetitionPosition.setStyle(borderColor);
    }
}
