package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Konrad on 05-03-2016.
 */
public class EditCompetitionController implements Initializable {

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
    public static Stage mainStage;

    private String rodzaj, dataR, miejsce, pozycja;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            buildData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rodzaj = tfCompetitionKind.getText();
        dataR = String.valueOf(dpCompetitionDate.getValue());
        miejsce = tfCompetitionPlace.getText();
        pozycja = tfCompetitionPosition.getText();
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

        String sql1 = "SELECT * FROM zawody WHERE id_zawodow = "+id;
        String sql2 = "SELECT * FROM uczestnicy_sztafeta WHERE id_zawodow = "+id;
        String sql3 = "SELECT * FROM uczestnicy_bojowka WHERE id_zawodow = "+id;

        rs = DbConnection.con.createStatement().executeQuery(sql1);
        while(rs.next()){
            tfCompetitionKind.setText(rs.getString("rodzaj"));
            tfCompetitionPlace.setText(rs.getString("miejsce"));
            dpCompetitionDate.setValue(LocalDate.parse(rs.getString("data_zawodow")));
            tfCompetitionPosition.setText(String.valueOf(rs.getInt("pozycja")));
            tfCompetitionDirector.setText(rs.getString("kierownik"));
        }

        rs = DbConnection.con.createStatement().executeQuery(sql2);
        while (rs.next()){
            cmbCompetitionPosition1.setValue(rs.getString("uczestnik1"));
            cmbCompetitionPosition2.setValue(rs.getString("uczestnik2"));
            cmbCompetitionPosition3.setValue(rs.getString("uczestnik3"));
            cmbCompetitionPosition4.setValue(rs.getString("uczestnik4"));
            cmbCompetitionPosition5.setValue(rs.getString("uczestnik5"));
            cmbCompetitionPosition6.setValue(rs.getString("uczestnik6"));
            cmbCompetitionPosition7.setValue(rs.getString("uczestnik7"));
        }

        rs = DbConnection.con.createStatement().executeQuery(sql3);
        while(rs.next()){
            cmbCompetitionPosition11.setValue(rs.getString("uczestnik1"));
            cmbCompetitionPosition21.setValue(rs.getString("uczestnik2"));
            cmbCompetitionPosition31.setValue(rs.getString("uczestnik3"));
            cmbCompetitionPosition41.setValue(rs.getString("uczestnik4"));
            cmbCompetitionPosition51.setValue(rs.getString("uczestnik5"));
            cmbCompetitionPosition61.setValue(rs.getString("uczestnik6"));
            cmbCompetitionPosition71.setValue(rs.getString("uczestnik7"));
        }

        rs.close();

    }

    public void actionEditCompetition(ActionEvent actionEvent) {
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

            String sql1 = "UPDATE zawody SET rodzaj = ?, miejsce = ?, data_zawodow = ?, pozycja = ?,kierownik = ? WHERE id_zawodow = "+id;
            String sql2 = "UPDATE uczestnicy_sztafeta SET uczestnik1 = ?, uczestnik2 = ?, uczestnik3 = ?, uczestnik4 = ?, uczestnik5 = ?, uczestnik6 = ?, uczestnik7 = ? WHERE id_zawodow = "+id;
            String sql3 = "UPDATE uczestnicy_bojowka SET uczestnik1 = ?, uczestnik2 = ?, uczestnik3 = ?, uczestnik4 = ?, uczestnik5 = ?, uczestnik6 = ?, uczestnik7 = ? WHERE id_zawodow = "+id;


            try {
                PreparedStatement insert1 = DbConnection.con.prepareStatement(sql1);
                PreparedStatement insert2 = DbConnection.con.prepareStatement(sql2);
                PreparedStatement insert3 = DbConnection.con.prepareStatement(sql3);

                insert1.setString(1,tfCompetitionKind.getText());
                insert1.setString(2,tfCompetitionPlace.getText());
                insert1.setString(3, String.valueOf(dpCompetitionDate.getValue()));
                insert1.setString(4,tfCompetitionPosition.getText());
                insert1.setString(5,kierownik);

                insert2.setString(1,pos1);
                insert2.setString(2,pos2);
                insert2.setString(3,pos3);
                insert2.setString(4,pos4);
                insert2.setString(5,pos5);
                insert2.setString(6,pos6);
                insert2.setString(7,pos7);

                insert3.setString(1,pos11);
                insert3.setString(2,pos21);
                insert3.setString(3,pos31);
                insert3.setString(4,pos41);
                insert3.setString(5,pos51);
                insert3.setString(6,pos61);
                insert3.setString(7,pos71);

                insert1.executeUpdate();
                insert2.executeUpdate();
                insert3.executeUpdate();

                insert1.close();
                insert2.close();
                insert3.close();


                if(!rodzaj.equals(tfCompetitionKind.getText()) || !dataR.equals(String.valueOf(dpCompetitionDate.getValue())) || !miejsce.equals(tfCompetitionPlace.getText()) || !pozycja.equals(tfCompetitionPosition.getText())){
                    Stage stage = (Stage) tfCompetitionDirector.getScene().getWindow();
                    createScene.setPrevStage(stage);
                    createScene.swapScene("../Windows/MainWindoww.fxml","Aplikacja OSP");
                    mainStage.close();
                }
                else{
                    Stage stage = (Stage) tfCompetitionDirector.getScene().getWindow();
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
