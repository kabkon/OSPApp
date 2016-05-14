package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.TableSubscriptions;
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
public class AddSubscriptionController implements Initializable {
    public ComboBox cmbSubscriptionMember;
    public DatePicker dpSubscriptionDate;
    public TextField tfSubscriptionValue;
    public TextField tfSubscriptionYear;

    public CreateScene createScene = new CreateScene();

    public ObservableList<String> data;


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
        while(rs.next()){
            data.add(rs.getString("imie") + " " + rs.getString("nazwisko"));
        }

        cmbSubscriptionMember.setItems(data);
        tfSubscriptionValue.setText("20");
        rs.close();
    }

    public void actionAddSubscription(ActionEvent actionEvent) {

        changeColorGray();
        if(checkFill()){

            int idMember = 0;
            String split[];
            split = String.valueOf(cmbSubscriptionMember.getValue()).split(" ");
            String sql1 = "SELECT * FROM czlonkowie WHERE imie = '"+split[0] +"' AND nazwisko = '"+split[1]+"';";
            try {
                ResultSet rs = DbConnection.con.createStatement().executeQuery(sql1);
                while (rs.next()){
                    idMember = rs.getInt("id_czlonka");
                }

                String sql2 = "INSERT INTO skladki VALUES(?,?,?,?)";
                PreparedStatement insert = DbConnection.con.prepareStatement(sql2);
                insert.setInt(1,idMember);
                insert.setString(2, String.valueOf(dpSubscriptionDate.getValue()));
                insert.setInt(3, Integer.parseInt(tfSubscriptionValue.getText()));
                insert.setInt(4, Integer.parseInt(tfSubscriptionYear.getText()));

                insert.executeUpdate();

                MainWindowController.dataSubscriptions.add(new TableSubscriptions(idMember,split[0] + " "+split[1],Integer.parseInt(tfSubscriptionValue.getText()),String.valueOf(dpSubscriptionDate.getValue()),Integer.parseInt(tfSubscriptionYear.getText())));
                MainWindowController.liczbaSkladek++;
                Stage stage = (Stage) tfSubscriptionValue.getScene().getWindow();
                rs.close();
                createScene.setPrevStage(stage);
                createScene.swapScene("/Windows/AddSubscriptionWindow.fxml","Dodaj składkę");


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else
            changeColorRed();
    }

    public boolean checkFill(){
        if(tfSubscriptionValue.getText().trim().isEmpty() || tfSubscriptionValue.getText() == null)
            return false;
        else if(dpSubscriptionDate.getValue() == null)
            return false;
        else if(tfSubscriptionYear.getText().trim().isEmpty() || tfSubscriptionYear.getText() == null)
            return false;
        else if(cmbSubscriptionMember.getValue() == null)
            return false;
        else
            return true;
    }

    public void changeColorGray(){
        String borderColor = "-fx-border-color:lightgray";
        tfSubscriptionYear.setStyle(borderColor);
        tfSubscriptionValue.setStyle(borderColor);
        dpSubscriptionDate.setStyle(borderColor);
        cmbSubscriptionMember.setStyle(borderColor);
    }

    public void changeColorRed(){
        String borderColor = "-fx-border-color:red";
        if(tfSubscriptionValue.getText().trim().isEmpty() || tfSubscriptionValue.getText() == null)
            tfSubscriptionValue.setStyle(borderColor);
        if(dpSubscriptionDate.getValue() == null)
            dpSubscriptionDate.setStyle(borderColor);
        if(tfSubscriptionYear.getText().trim().isEmpty() || tfSubscriptionYear.getText() == null)
            tfSubscriptionYear.setStyle(borderColor);
        if(cmbSubscriptionMember.getValue() == null)
            cmbSubscriptionMember.setStyle(borderColor);
    }
}
