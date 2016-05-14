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
import java.util.ResourceBundle;

/**
 * Created by Konrad on 04-03-2016.
 */
public class AddActionController implements Initializable {
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

    public CreateScene createScene = new CreateScene();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildData();
    }

    public void buildData(){
        String sql = "SELECT * FROM czlonkowie";
        try {
            data = FXCollections.observableArrayList();
            ResultSet rs = DbConnection.con.createStatement().executeQuery(sql);
            while(rs.next()){
                data.add(rs.getString("imie") + " " + rs.getString("nazwisko"));
            }

            cmbActionDriver.setItems(data);
            cmbActionLeader.setItems(data);
            cmbActionUnit1_1.setItems(data);
            cmbActionUnit1_2.setItems(data);
            cmbActionUnit2_1.setItems(data);
            cmbActionUnit2_2.setItems(data);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void actionAddAction(ActionEvent actionEvent) {

        changeColorGray();
        if(checkFill()){

            String czasPowrotu = tfActionTimeBack.getText();
            String czasWyjazdu = tfActionTimeStart.getText();
            String info = taActionInfo.getText();
            String place = taActionPlace.getText();
            String kierowca;
            String lider, rota11,rota12,rota21,rota22;

            if(cmbActionDriver.getValue() == null)
                kierowca = " ";
            else
                kierowca = String.valueOf(cmbActionDriver.getValue());

            if(cmbActionLeader.getValue() == null)
                lider = " ";
            else
                lider = String.valueOf(cmbActionLeader.getValue());

            if(cmbActionUnit1_1.getValue() == null)
                rota11 = " ";
            else
                rota11 = String.valueOf(cmbActionUnit1_1.getValue());

            if(cmbActionUnit1_2.getValue() == null)
                rota12 = " ";
            else
                rota12 = String.valueOf(cmbActionUnit1_2.getValue());

            if(cmbActionUnit2_1.getValue() == null)
                rota21 = " ";
            else
                rota21 = String.valueOf(cmbActionUnit2_1.getValue());

            if(cmbActionUnit2_2.getValue() == null)
                rota22 = " ";
            else
                rota22 = String.valueOf(cmbActionUnit2_2.getValue());


            try {
                String sql1 = "INSERT INTO wyjazdy " +
                        "VALUES(?,?,?,?,?,?,?);";
                String sql2 = "INSERT INTO uczestnicy_wyjazdu " +
                        "VALUES(?,?,?,?,?,?,?)";
                PreparedStatement insert1 = DbConnection.con.prepareStatement(sql1);
                PreparedStatement insert2 = DbConnection.con.prepareStatement(sql2);

                insert1.setInt(1,id);
                insert1.setString(2,tfActionKind.getText());
                insert1.setString(3, String.valueOf(dpActionDate.getValue()));
                insert1.setString(4,czasWyjazdu);
                insert1.setString(5,czasPowrotu);
                insert1.setString(6,place);
                insert1.setString(7,info);

                insert2.setInt(1,id);
                insert2.setString(2,kierowca);
                insert2.setString(3,lider);
                insert2.setString(4,rota11);
                insert2.setString(5,rota12);
                insert2.setString(6,rota21);
                insert2.setString(7,rota22);

                insert1.executeUpdate();
                insert2.executeUpdate();

                MainWindowController.dataActions.add(new TableActions(id++,tfActionKind.getText(),taActionPlace.getText(),String.valueOf(dpActionDate.getValue()), czasWyjazdu,czasPowrotu));
                MainWindowController.liczbaWyjazdow++;
                MainWindowController.lastActionId++;
                Stage stage = (Stage) tfActionKind.getScene().getWindow();
                insert1.close();
                insert2.close();
                createScene.setPrevStage(stage);
                createScene.swapScene("/Windows/AddActionsWindow.fxml","Dodaj wyjazd");



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
