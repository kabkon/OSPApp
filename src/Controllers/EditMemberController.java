package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.sqlite.core.DB;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.StringJoiner;

/**
 * Created by Konrad on 04-03-2016.
 */
public class EditMemberController implements Initializable {
    public static int id;
    public static int idAdresu;
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

    public TableView<TableActions> tableActions = new TableView<>();
    public TableColumn tcActionKind;
    public TableColumn tcActionPlace;
    public TableColumn tcActionDate;
    public static ObservableList<TableActions> dataActions;

    public TableView<TableSubscriptions> tableSubscriptions = new TableView<>();
    public TableColumn tcSubscriptionsDate;
    public TableColumn tcSubscriptionsValue;
    public static ObservableList<TableSubscriptions> dataSubscriptions;

    private String nameInventorySelected;
    private String nameTrainingSelected;
    private String nameMedalSelected;

    public static Stage mainStage;

    CreateScene createScene = new CreateScene();

    private String imie,nazwisko,adres, nrTel,dataUr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            buildData();
        } catch (SQLException e) {
           e.printStackTrace();
        }
        getSelectedInventoryName();
        getSelectedMedalName();
        getSelectedTrainingName();

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
        imie = tfMemberFirstName.getText();
        nazwisko = tfMemberSurName.getText();
        adres = tfMemberCity.getText() + " " + tfMemberStreet.getText();
        nrTel = tfMemberTel.getText();
        dataUr = String.valueOf(dpBirthDate.getValue());

    }

    public void buildData() throws SQLException {
        String sql1 = "SELECT * FROM czlonkowie cz INNER JOIN czlonkowie2 cz2 INNER JOIN adresy a WHERE cz.id_adresu AND a.id_adresu AND cz.id_czlonka = cz2.id_czlonka AND cz.id_czlonka = " + id +" AND cz2.id_czlonka = " + id +" AND a.id_adresu = "+id;
        ResultSet rs = DbConnection.con.createStatement().executeQuery(sql1);

        while (rs.next()) {
            tfMemberFirstName.setText(rs.getString("imie"));
            tfMemberSurName.setText(rs.getString("nazwisko"));
            dpBirthDate.setValue(LocalDate.parse(rs.getString("data_urodzenia")));
            cmbMemberSex.setValue(rs.getString("plec"));
            cmbMemberStatus.setValue(rs.getString("rodzaj"));
            tfMemberIdNumber.setText(rs.getString("pesel"));
            tfMemberFathersName.setText(rs.getString("imie_ojca"));
            tfMemberWorkPlace.setText(rs.getString("miejsce_pracy"));
            tfMemberEmail.setText(rs.getString("email"));
            tfMemberCity.setText(rs.getString("miejscowosc"));
            tfMemberStreet.setText(rs.getString("ulica"));
            tfMemberRegion.setText(rs.getString("gmina"));
            tfMemberPostCode.setText(rs.getString("kod_pocztowy"));
            dpMemberStartService.setValue(LocalDate.parse(rs.getString("poczatek_sluzby")));
            cmbMemberDriverLicense.setValue(rs.getString("prawo_jazdy"));
            cmbMemberJOT.setValue(rs.getString("jot"));
            tfMemberTel.setText(rs.getString("telefon"));
            idAdresu = rs.getInt("id_adresu");
            if (!rs.getString("badania_lekarskie").equals(" "))
                dpMemberMedicalCheckUp.setValue(LocalDate.parse(rs.getString("badania_lekarskie")));


            String sql2 = "SELECT * FROM inwentarz_czlonka WHERE id_czlonka = " + id;

            dataInventory = FXCollections.observableArrayList();
            rs = DbConnection.con.createStatement().executeQuery(sql2);
            while (rs.next()) {
                dataInventory.add(new TableInventory(rs.getString("nazwa"), id, rs.getInt("ilosc_sztuk"), rs.getString("info")));
            }
            tableEquipment.setPlaceholder(new Label(" "));
            tcEquipmentName.setCellValueFactory(new PropertyValueFactory<TableInventory, String>("name"));
            tcEquipmentQuantity.setCellValueFactory(new PropertyValueFactory<TableInventory, String>("quantity"));
            tcEquipmentInfo.setCellValueFactory(new PropertyValueFactory<TableInventory, String>("info"));
            tableEquipment.setItems(dataInventory);

            String sql3 = "SELECT * FROM szkolenia WHERE id_czlonka = " + id;
            dataTrainings = FXCollections.observableArrayList();
            rs = DbConnection.con.createStatement().executeQuery(sql3);
            while (rs.next()) {
                dataTrainings.add(new TableTrainings(id, rs.getString("nazwa"), rs.getString("data_szkolenia"), rs.getString("miejsce_szkolenia")));
            }
            tableTrainings.setPlaceholder(new Label(" "));
            tcTrainingKind.setCellValueFactory(new PropertyValueFactory<TableTrainings, String>("kind"));
            tcTrainingDate.setCellValueFactory(new PropertyValueFactory<TableTrainings, String>("date"));
            tcTrainingPlace.setCellValueFactory(new PropertyValueFactory<TableTrainings, String>("place"));
            tableTrainings.setItems(dataTrainings);

            String sql4 = "SELECT * FROM odznaczenia WHERE id_czlonka = " + id;
            dataMedals = FXCollections.observableArrayList();
            rs = DbConnection.con.createStatement().executeQuery(sql4);
            while (rs.next()) {
                dataMedals.add(new TableMedals(id, rs.getString("nazwa"), rs.getString("data_odznaczenia"), rs.getString("info")));
            }
            tableMedals.setPlaceholder(new Label(" "));
            tcMedalsName.setCellValueFactory(new PropertyValueFactory<TableMedals, String>("name"));
            tcMedalsDate.setCellValueFactory(new PropertyValueFactory<TableMedals, String>("date"));
            tcMedalsInfo.setCellValueFactory(new PropertyValueFactory<TableMedals, String>("info"));
            tableMedals.setItems(dataMedals);

            String imieNazwisko = tfMemberFirstName.getText() + " " + tfMemberSurName.getText();
            String sql5 = "SELECT * FROM wyjazdy w INNER JOIN uczestnicy_wyjazdu uw WHERE w.id_wyjazdu = uw.id_wyjazdu AND dowodca = '" + imieNazwisko + "' OR kierowca = '" + imieNazwisko + "' OR rota11 = '" + imieNazwisko + "' OR rota12 = '" + imieNazwisko + "' OR rota21 = '" + imieNazwisko + "' OR rota22 = '" + imieNazwisko + "'";
            dataActions = FXCollections.observableArrayList();
            rs = DbConnection.con.createStatement().executeQuery(sql5);
            while (rs.next()) {
                dataActions.add(new TableActions(rs.getString("rodzaj"), rs.getString("miejsce_akcji"), rs.getString("data_wyjazdu")));
            }
            tableActions.setPlaceholder(new Label(" "));
            tcActionKind.setCellValueFactory(new PropertyValueFactory<TableActions, String>("kind"));
            tcActionDate.setCellValueFactory(new PropertyValueFactory<TableActions, String>("data"));
            tcActionPlace.setCellValueFactory(new PropertyValueFactory<TableActions, String>("address"));
            tableActions.setItems(dataActions);

            String sql6 = "SELECT * FROM skladki WHERE id_czlonka = " + id;
            dataSubscriptions = FXCollections.observableArrayList();
            rs = DbConnection.con.createStatement().executeQuery(sql6);
            while (rs.next()) {
                dataSubscriptions.add(new TableSubscriptions(rs.getInt("id_czlonka"), rs.getInt("kwota"), rs.getInt("rok")));
            }
            tableSubscriptions.setPlaceholder(new Label(" "));
            tcSubscriptionsDate.setCellValueFactory(new PropertyValueFactory<TableSubscriptions, Integer>("year"));
            tcSubscriptionsValue.setCellValueFactory(new PropertyValueFactory<TableSubscriptions, Integer>("fee"));
            tableSubscriptions.setItems(dataSubscriptions);

            tableEquipment.setPlaceholder(new Label(" "));
            tableMedals.setPlaceholder(new Label(" "));
            tableTrainings.setPlaceholder(new Label(" "));
            tableSubscriptions.setPlaceholder(new Label(" "));
            tableActions.setPlaceholder(new Label(" "));


        }
        rs.close();
    }
    public void actionAddEquipment(ActionEvent actionEvent) {
        try {
            createScene.createScene("/Windows/AddInventoryMemberWindow2.fxml","Dodaj sprzęt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveEquipment(ActionEvent actionEvent) {
        String sql1 = "DELETE FROM inwentarz_czlonka WHERE id_czlonka = "+id + " AND nazwa = '"+getNameInventorySelected()+"'";
        try {
            DbConnection.con.createStatement().executeUpdate(sql1);
            buildData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionAddTraining(ActionEvent actionEvent) {
        try {
            createScene.createScene("/Windows/AddTrainingMemberWindow2.fxml","Dodaj szkolenie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveTraining(ActionEvent actionEvent) {
        String sql1 = "DELETE FROM szkolenia WHERE id_czlonka = "+id + " AND nazwa = '"+getNameTrainingSelected()+"'";
        try {
            DbConnection.con.createStatement().executeUpdate(sql1);
            buildData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void actionAddMedal(ActionEvent actionEvent) {
        try {
            createScene.createScene("/Windows/AddMedalsMemberWindow2.fxml","Dodaj odznaczenie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveMedals(ActionEvent actionEvent) {
        String sql1 = "DELETE FROM odznaczenia WHERE id_czlonka = "+id + " AND nazwa = '"+getNameMedalSelected()+"'";
        try {
            DbConnection.con.createStatement().executeUpdate(sql1);
            buildData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionEditMember(ActionEvent actionEvent) {
        changeColorGray();
        if(checkFill()){
            String tel = tfMemberTel.getText();
            String imieOjca = tfMemberFathersName.getText();
            String miejscePracy = tfMemberWorkPlace.getText();
            String email = tfMemberEmail.getText();
            String ulica = tfMemberStreet.getText();
            String gmina = tfMemberRegion.getText();
            String kodPocztowy = tfMemberPostCode.getText();
            String prawoJazdy;
            String jot;
            String badaniaMedyczne = String.valueOf(dpMemberMedicalCheckUp.getValue());
            if(tel.trim().isEmpty() || tel == null)
                tel = " ";
            if(imieOjca.trim().isEmpty()|| imieOjca == null)
                imieOjca = " ";
            if(miejscePracy.trim().isEmpty() || miejscePracy == null)
                miejscePracy = " ";
            if(email.trim().isEmpty() || email == null)
                email = " ";
            if(ulica.trim().isEmpty() || ulica == null)
                ulica = " ";
            if(gmina.trim().isEmpty() || gmina == null)
                gmina = " ";
            if(kodPocztowy.trim().isEmpty() || kodPocztowy == null)
                kodPocztowy = " ";
            if(cmbMemberDriverLicense.getSelectionModel().getSelectedIndex() < 0 || cmbMemberDriverLicense.getSelectionModel().getSelectedIndex() > 4)
                prawoJazdy = " ";
            else
                prawoJazdy = String.valueOf(cmbMemberDriverLicense.getValue());
            if(cmbMemberJOT.getSelectionModel().getSelectedIndex() < 0 || cmbMemberJOT.getSelectionModel().getSelectedIndex() > 1 )
                jot = " ";
            else
                jot = String.valueOf(cmbMemberJOT.getValue());
            if(dpMemberMedicalCheckUp.getValue() == null)
                badaniaMedyczne = " ";



            String sql1 = "UPDATE czlonkowie SET imie = ?, nazwisko = ?, data_urodzenia = ?, plec = ?, rodzaj = ?, pesel = ?, imie_ojca = ?, miejsce_pracy = ? WHERE id_czlonka = "+id;
            String sql2 = "UPDATE czlonkowie2 SET poczatek_sluzby = ?, badania_lekarskie = ?, prawo_jazdy = ?, email = ?, telefon = ?, jot = ? WHERE id_czlonka = "+id;
            String sql3 = "UPDATE adresy SET miejscowosc = ?, ulica = ?, kod_pocztowy = ?, gmina = ? WHERE id_adresu = "+ EditMemberController.idAdresu;

            try {
                PreparedStatement updateCzlonkowie = DbConnection.con.prepareStatement(sql1);
                updateCzlonkowie.setString(1,tfMemberFirstName.getText());
                updateCzlonkowie.setString(2,tfMemberSurName.getText());
                updateCzlonkowie.setString(3, String.valueOf(dpBirthDate.getValue()));
                updateCzlonkowie.setString(4, String.valueOf(cmbMemberSex.getValue()));
                updateCzlonkowie.setString(5, String.valueOf(cmbMemberStatus.getValue()));
                updateCzlonkowie.setString(6, tfMemberIdNumber.getText());
                updateCzlonkowie.setString(7,imieOjca);
                updateCzlonkowie.setString(8,miejscePracy);

                PreparedStatement updateCzlonkowie2 = DbConnection.con.prepareStatement(sql2);
                updateCzlonkowie2.setString(1, String.valueOf(dpMemberStartService.getValue()));
                updateCzlonkowie2.setString(2, badaniaMedyczne);
                updateCzlonkowie2.setString(3, prawoJazdy);
                updateCzlonkowie2.setString(4,email);
                updateCzlonkowie2.setString(5,tel);
                updateCzlonkowie2.setString(6, jot);

                PreparedStatement updateAdresy = DbConnection.con.prepareStatement(sql3);
                updateAdresy.setString(1,tfMemberCity.getText());
                updateAdresy.setString(2,ulica);
                updateAdresy.setString(3,kodPocztowy);
                updateAdresy.setString(4,gmina);

                updateAdresy.executeUpdate();
                updateCzlonkowie.executeUpdate();
                updateCzlonkowie2.executeUpdate();


                updateAdresy.close();
                updateCzlonkowie.close();
                updateCzlonkowie2.close();

                if(!imie.equals(tfMemberFirstName.getText()) || !nazwisko.equals(tfMemberSurName.getText()) || !adres.equals(tfMemberCity.getText()+ " " + tfMemberStreet.getText()) || !nrTel.equals(tfMemberTel.getText()) || !dataUr.equals(String.valueOf(dpBirthDate.getValue())) ){
                    Stage stage = (Stage) tfMemberSurName.getScene().getWindow();
                    createScene.setPrevStage(stage);
                    createScene.swapScene("../Windows/MainWindoww.fxml","Aplikacja OSP");

                    mainStage.close();
                }
                else {
                    Stage stage = (Stage)tfMemberStreet.getScene().getWindow();
                    stage.close();
                }


                } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
            changeColorRed();
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
        else if(tfMemberCity.getText().trim().isEmpty() || tfMemberCity.getText() == null)
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
        if(tfMemberCity.getText().trim().isEmpty() || tfMemberCity.getText() == null)
            tfMemberCity.setStyle(borderColor);
    }

    public void changeColorGray(){
        String borderColor = "-fx-border-color:lightgray";
        tfMemberFirstName.setStyle(borderColor);
        tfMemberSurName.setStyle(borderColor);
        dpBirthDate.setStyle(borderColor);
        tfMemberIdNumber.setStyle(borderColor);
        tfMemberCity.setStyle(borderColor);
        dpMemberStartService.setStyle(borderColor);

    }

    public String getNameInventorySelected() {
        return nameInventorySelected;
    }

    public void setNameInventorySelected(String nameInventorySelected) {
        this.nameInventorySelected = nameInventorySelected;
    }

    public void getSelectedInventoryName(){
        tableEquipment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableEquipment.getSelectionModel().getSelectedItem() != null)
                setNameInventorySelected(newValue.getName());
        });
    }

    public String getNameTrainingSelected() {
        return nameTrainingSelected;
    }

    public void setNameTrainingSelected(String nameTrainingSelected) {
        this.nameTrainingSelected = nameTrainingSelected;
    }

    public void getSelectedTrainingName(){
        tableTrainings.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableTrainings.getSelectionModel().getSelectedItem() != null)
                setNameTrainingSelected(newValue.getKind());
        });
    }

    public String getNameMedalSelected() {
        return nameMedalSelected;
    }

    public void setNameMedalSelected(String nameMedalSelected) {
        this.nameMedalSelected = nameMedalSelected;
    }

    public void getSelectedMedalName(){
        tableMedals.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableMedals.getSelectionModel().getSelectedItem() != null)
                setNameMedalSelected(newValue.getName());
        });
    }
}
