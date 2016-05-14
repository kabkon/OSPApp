package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.TableInventory;
import Tables.TableMedals;
import Tables.TableMembers;
import Tables.TableTrainings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by Konrad on 04-03-2016.
 */
public class AddMemberController implements Initializable {
    public DatePicker dpBirthDate;
    public TextField tfMemberFirstName;
    public TextField tfMemberSurName;
    public TextField tfMemberIdNumber;
    public TextField tfMemberFathersName;
    public TextField tfMemberWorkPlace;
    public ComboBox cmbMemberSex;
    public ComboBox cmbMemberStatus;
    public TextField tfMemberTel;
    public TextField tfMemberEmail;
    public TextField tfMemberCity;
    public TextField tfMemberStreet;
    public TextField tfMemberPostCode;
    public TextField tfMemberRegion;
    public DatePicker dpMemberStartService;
    public DatePicker dpMemberMedicalCheckUp;
    public ComboBox cmbMemberDriverLicense;
    public ComboBox cmbMemberJOT;
    public TableView<TableInventory> tableEquipment = new TableView<>();
    public TableColumn tcEquipmentName;
    public TableColumn tcEquipmentQuantity;
    public TableColumn tcEquipmentInfo;
    public static ObservableList<TableInventory> dataInventory;
    public TableView<TableTrainings> tableTrainings = new TableView<>();
    public TableColumn tcTrainingKind;
    public TableColumn tcTrainingDate;
    public TableColumn tcTrainingPlace;
    public static ObservableList<TableTrainings> dataTrainings;
    public TableView<TableMedals> tableMedals = new TableView<>();
    public TableColumn tcMedalsName;
    public TableColumn tcMedalsDate;
    public TableColumn tcMedalsInfo;
    public static ObservableList<TableMedals> dataMedals;

    CreateScene createScene = new CreateScene();

    private static String nameInventorySelected;
    private static String nameMedalSelected;
    private static String nameTrainingSelected;

    public static int id;
    public static int dateMemberIndex;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildData();
    }

    public void buildData(){
        ObservableList<String> plec = FXCollections.observableArrayList(
                "Kobieta", "Mężczyzna"
        );
        ObservableList<String> status = FXCollections.observableArrayList(
                "Zwyczajny", "Młodzieżowy", "Honorowy"
        );
        ObservableList<String> prawoJazdy = FXCollections.observableArrayList(
                "A","B","A+B","C","B+C"
        );
        ObservableList<String> jot = FXCollections.observableArrayList(
                "TAK", "NIE"
        );

        cmbMemberSex.setItems(plec);
        cmbMemberStatus.setItems(status);
        cmbMemberDriverLicense.setItems(prawoJazdy);
        cmbMemberJOT.setItems(jot);

        tableEquipment.setPlaceholder(new Label(" "));
        tableMedals.setPlaceholder(new Label(" "));
        tableTrainings.setPlaceholder(new Label(" "));
        dataInventory = FXCollections.observableArrayList();
        dataMedals = FXCollections.observableArrayList();
        dataTrainings = FXCollections.observableArrayList();

        tcEquipmentName.setCellValueFactory(new PropertyValueFactory<TableInventory,String>("name"));
        tcEquipmentQuantity.setCellValueFactory(new PropertyValueFactory<TableInventory,Integer>("quantity"));
        tcEquipmentInfo.setCellValueFactory(new PropertyValueFactory<TableInventory,String>("info"));
        tableEquipment.setItems(dataInventory);

        tcMedalsName.setCellValueFactory(new PropertyValueFactory<TableMedals,String>("name"));
        tcMedalsDate.setCellValueFactory(new PropertyValueFactory<TableMedals,String>("date"));
        tcMedalsInfo.setCellValueFactory(new PropertyValueFactory<TableMedals,String>("info"));
        tableMedals.setItems(dataMedals);

        tcTrainingKind.setCellValueFactory(new PropertyValueFactory<TableTrainings,String>("kind"));
        tcTrainingDate.setCellValueFactory(new PropertyValueFactory<TableTrainings,String>("date"));
        tcTrainingPlace.setCellValueFactory(new PropertyValueFactory<TableTrainings,String>("place"));
        tableTrainings.setItems(dataTrainings);


        getSelectedTrainingName();
        getSelectedMedalName();
        getSelectedInventoryName();

    }

    public void actionAddEquipment(ActionEvent actionEvent) {
        try{
            createScene.createScene("/Windows/AddInventoryMemberWindow.fxml","Dodaj wyposażenie członka");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveEquipment(ActionEvent actionEvent) {
        String sql = "DELETE FROM inwentarz_czlonka WHERE nazwa = '"+getNameInventorySelected()+"' AND id_czlonka = "+id;
        try {
            DbConnection.con.createStatement().executeUpdate(sql);
            buildData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionAddTraining(ActionEvent actionEvent) {
        try{
            createScene.createScene("/Windows/AddTrainingMemberWindow.fxml","Dodaj szkolenie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveTraining(ActionEvent actionEvent) {
        String sql = "DELETE FROM szkolenia WHERE id_czlonka = "+id+" AND nazwa = '"+getNameTrainingSelected()+"'";
        try {
            DbConnection.con.createStatement().executeUpdate(sql);
            buildData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionAddMedal(ActionEvent actionEvent) {
        try{
            createScene.createScene("/Windows/AddMedalsMemberWindow.fxml","Dodaj medal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveMedals(ActionEvent actionEvent) {
        String sql = "DELETE FROM odznaczenia WHERE id_czlonka = "+id+" AND nazwa = '"+getNameMedalSelected()+"'";
        try {
            DbConnection.con.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionAddMember(ActionEvent actionEvent) {

        changeColorGray();
        if(checkFill()){
            String tel = tfMemberTel.getText();
            String imieOjca = tfMemberFathersName.getText();
            String miejscePracy = tfMemberWorkPlace.getText();
            String email = tfMemberEmail.getText();
            String ulica = tfMemberStreet.getText();
            String gmina = tfMemberRegion.getText();
            String kodPocztowy = tfMemberPostCode.getText();
            String prawoJazdy = " ";
            String jot;
            String badaniaMedyczne = String.valueOf(dpMemberMedicalCheckUp.getValue());
            int idAdresu;
            if(tel.trim().isEmpty() || tel == null)
                tel = " ";
            if(imieOjca.trim().isEmpty()|| imieOjca == null)
                imieOjca = " ";
            if(miejscePracy.trim().isEmpty() || miejscePracy== null)
                miejscePracy = " ";
            if(email.trim().isEmpty() || email == null)
                email = " ";
            if(ulica.trim().isEmpty() || ulica == null)
                ulica = " ";
            if(gmina.trim().isEmpty() || gmina == null)
                gmina = " ";
            if(kodPocztowy.trim().isEmpty() || kodPocztowy == null)
                kodPocztowy = " ";
            if(cmbMemberDriverLicense.getSelectionModel().getSelectedIndex() < 1 || cmbMemberDriverLicense.getSelectionModel().getSelectedIndex() > 3)
                prawoJazdy = " ";
            else
                prawoJazdy = String.valueOf(cmbMemberDriverLicense.getValue());
            if(cmbMemberJOT.getSelectionModel().getSelectedIndex() < 1 || cmbMemberJOT.getSelectionModel().getSelectedIndex() > 2 )
                jot = " ";
            else
                jot = String.valueOf(cmbMemberJOT.getValue());
            if(dpMemberMedicalCheckUp.getValue() == null)
                badaniaMedyczne = " ";
            try {
                Statement st = DbConnection.con.createStatement();
                String sql1 = "INSERT INTO adresy " +
                        "VALUES("+id+",'"+tfMemberCity.getText()+"','"+ulica+"','"+kodPocztowy+"','"+gmina+"');";

                String sql2 = "INSERT INTO czlonkowie "+
                        "VALUES("+id+",'"+tfMemberFirstName.getText()+"','"+tfMemberSurName.getText()+"','"+dpBirthDate.getValue()+"','"+cmbMemberSex.getValue()+
                        "','"+cmbMemberStatus.getValue()+"',"+tfMemberIdNumber.getText()+",'"+imieOjca+"','"+miejscePracy+"',"+id+");";

                String sql3 = "INSERT INTO czlonkowie2 " +
                        "VALUES("+id+",'"+dpMemberStartService.getValue()+"','"+badaniaMedyczne+"','"+prawoJazdy+"','" +
                        email+"','"+tel+"','"+jot+"');";

                st.executeUpdate(sql2);
                st.executeUpdate(sql3);
                st.executeUpdate(sql1);

                MainWindowController.dataMembers.add(new TableMembers(id,tfMemberFirstName.getText(),tfMemberSurName.getText(), tfMemberCity.getText() + " " + ulica, tel,String.valueOf(dpBirthDate.getValue())));
                MainWindowController.liczbaCzlonkow++;
                id++;
                MainWindowController.lastMemberId++;
                st.close();
                Stage stage = (Stage) tfMemberCity.getScene().getWindow();
                createScene.setPrevStage(stage);
                createScene.swapScene("/Windows/AddMemberWindow.fxml", "Dodaj członka");




            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else{
            changeColorRed();
        }



    }

    public static String getNameInventorySelected() {
        return nameInventorySelected;
    }

    public static void setNameInventorySelected(String nameInventorySelected) {
        AddMemberController.nameInventorySelected = nameInventorySelected;
    }

    public void getSelectedInventoryName(){
        tableEquipment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableEquipment.getSelectionModel().getSelectedItem() != null)
                setNameInventorySelected(newValue.getName());
        });
    }

    public static String getNameMedalSelected() {
        return nameMedalSelected;
    }

    public static void setNameMedalSelected(String nameMedalSelected) {
        AddMemberController.nameMedalSelected = nameMedalSelected;
    }

    public void getSelectedMedalName(){
        tableMedals.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableMedals.getSelectionModel().getSelectedItem() != null)
                setNameMedalSelected(newValue.getName());
        });
    }

    public static String getNameTrainingSelected() {
        return nameTrainingSelected;
    }

    public static void setNameTrainingSelected(String nameTrainingSelected) {
        AddMemberController.nameTrainingSelected = nameTrainingSelected;
    }

    public void getSelectedTrainingName(){
        tableTrainings.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableTrainings.getSelectionModel().getSelectedItem() != null)
                setNameTrainingSelected(newValue.getKind());
        });
    }

    public boolean checkFill(){
        if(tfMemberFirstName.getText().trim().isEmpty() || tfMemberFirstName.getText() == null)
            return false;
        else if(tfMemberSurName.getText().trim().isEmpty() || tfMemberSurName.getText() == null)
            return false;
        else if(dpBirthDate.getValue() == null)
            return false;
        else if(tfMemberIdNumber.getText().trim().isEmpty() || tfMemberIdNumber.getText() == null)
            return false;
        else if(cmbMemberStatus.getValue() == null)
            return false;
        else if(tfMemberCity.getText().trim().isEmpty() || tfMemberCity.getText() == null)
            return false;
        else if(cmbMemberSex.getValue() == null)
            return false;
        else if(dpMemberStartService.getValue() == null)
            return false;
        else
            return true;
    }

    public void changeColorRed(){
        String borderColor = "-fx-border-color:red";
        if(tfMemberFirstName.getText().trim().isEmpty() || tfMemberFirstName.getText() == null)
            tfMemberFirstName.setStyle(borderColor);
        if(tfMemberSurName.getText().trim().isEmpty() || tfMemberSurName.getText() == null)
            tfMemberSurName.setStyle(borderColor);
        if(dpBirthDate.getValue() == null)
            dpBirthDate.setStyle(borderColor);
        if(tfMemberIdNumber.getText().trim().isEmpty() || tfMemberIdNumber.getText() == null)
            tfMemberIdNumber.setStyle(borderColor);
        if(cmbMemberStatus.getValue() == null)
            cmbMemberStatus.setStyle(borderColor);
        if(tfMemberCity.getText().trim().isEmpty() || tfMemberCity.getText() == null)
            tfMemberCity.setStyle(borderColor);
        if(cmbMemberSex.getValue() == null)
            cmbMemberSex.setStyle(borderColor);
        if(dpMemberStartService.getValue() == null)
            dpMemberStartService.setStyle(borderColor);
    }

    public void changeColorGray(){
        String borderColor = "-fx-background-color: #a9a9a9 , white , white;\n" +
                "    -fx-background-insets: 0 -1 -1 -1, 0 0 0 0, 0 -1 3 -1;";
        String borderColor2 = "-fx-border-color:lightgray";
        tfMemberFirstName.setStyle(borderColor);
        tfMemberSurName.setStyle(borderColor);
        dpBirthDate.setStyle(borderColor);
        tfMemberIdNumber.setStyle(borderColor);
        tfMemberCity.setStyle(borderColor);
        dpMemberStartService.setStyle(borderColor);
        cmbMemberSex.setStyle(borderColor2);
        cmbMemberStatus.setStyle(borderColor2);

    }
}
