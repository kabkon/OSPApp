package Controllers;

import MainPackage.CreateScene;
import MainPackage.DbConnection;
import Tables.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by Konrad on 01-03-2016.
 */
public class MainWindowController implements Initializable {

    public TableView<TableMembers> tableMembers = new TableView<>();
    public TableColumn tcMembersFirstName;
    public TableColumn tcMembersSurname;                                   //Sekcja członkowie
    public TableColumn tcMembersAdress;
    public TableColumn tcMembersPhone;
    public TableColumn tcMemberBirthDate;
    public TextField tfQuantityOfMembers;
    public static ObservableList<TableMembers> dataMembers;
    public RadioButton rbHonoured;
    public RadioButton rbYoung;
    public RadioButton rbNormal;
    public RadioButton rbAll;


    public TableView<TableInventory> tableInventory = new TableView<>();
    public TableColumn tcInventoryName;                                     // Sekcja sprzętu
    public TableColumn tcInventoryBrand;
    public TableColumn tcInventoryQuantity;
    public static ObservableList<TableInventory> dataInventory;
    public RadioButton rbGaraż;
    public RadioButton rbSamochod;
    public RadioButton rbRemiza;
    public RadioButton rbKuchnia;
    public RadioButton rbScena;
    public RadioButton rbInventoryAll;

    public TableView<TableActions> tableActions = new TableView<>();
    public TableColumn tcActionsKind;
    public TableColumn tcActionsAddress;
    public TableColumn tcActionsDate;                                       //Sekcja wyjazdów
    public TableColumn tcActionsTimeStart;
    public TableColumn tcActionsTimeBack;
    public static ObservableList<TableActions> dataActions;

    public TableView<TableCars> tableCars = new TableView<>();
    public TableColumn tcCarsBrand;
    public TableColumn tcCarsType;
    public TableColumn tcCarsRegistrationNumber;                            //Sekcja aut
    public TableColumn tcCarsServiceTerm;
    public TableColumn tcCarsProductionDate;
    public static ObservableList<TableCars> dataCars;

    public TableView<TableCompetitions> tableCompetitions = new TableView<>();
    public TableColumn tcCompetitionsKind;
    public TableColumn tcCompetitionsDate;                                  //Sekcja zawodów
    public TableColumn tcCompetitionsPlace;
    public TableColumn tcCompetitionsPosition;
    public static ObservableList<TableCompetitions> dataCompetitions;

    public TableView<TableSubscriptions> tableSubscriptions = new TableView<>();
    public TableColumn tcSubscriptionsMember;
    public TableColumn tcSubscriptionsFee;                                  //Sekcja składek
    public TableColumn tcSubscriptionsDate;
    public TableColumn tcSubscriptionsYear;
    public static ObservableList<TableSubscriptions> dataSubscriptions;
    public RadioButton rbSub2016;
    public RadioButton rbSub2015;
    public RadioButton rbSub2014;
    public RadioButton rbSubAll;

    private static int idMemberSelected;
    private static int idInventorySelected;
    private static int idActionSelected;
    private static int idCarSelected;
    private static int yearSubscriptionSelected;
    private static int idCompetitionSelected;
    private static int idMemberSubscriptionSelected;

    public static int liczbaCzlonkow;
    public static int liczbaSprzetu;
    public static int liczbaWyjazdow;
    public static int liczbaZawodow;
    public static int liczbaSkladek;

    public static int lastMemberId;
    public static int lastEquipmmentId;
    public static int lastActionId;
    public static int lastCompetitionId;
    public static int lastCarId;

    public Tab tabMembers;
    public Tab tabInventory;
    public Tab tabActions;
    public Tab tabCars;
    public Tab tabCompetitions;
    public Tab tabSubscriptions;


    ToggleGroup members = new ToggleGroup();
    ToggleGroup inventory = new ToggleGroup();
    ToggleGroup supscription = new ToggleGroup();

    CreateScene createScene = new CreateScene();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DbConnection.getConnect();
        try {
            buildDataMembers();
            buildDataInventory();
            buildDataActions();
            buildDataCars();
            buildDataCompetitions();
            buildDataSubscriptions();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        rbHonoured.setToggleGroup(members);
        rbYoung.setToggleGroup(members);
        rbNormal.setToggleGroup(members);
        rbAll.setToggleGroup(members);
        rbAll.setSelected(true);

        rbGaraż.setToggleGroup(inventory);
        rbSamochod.setToggleGroup(inventory);
        rbKuchnia.setToggleGroup(inventory);
        rbRemiza.setToggleGroup(inventory);
        rbScena.setToggleGroup(inventory);
        rbInventoryAll.setToggleGroup(inventory);
        rbInventoryAll.setSelected(true);

        rbSub2016.setToggleGroup(supscription);
        rbSub2015.setToggleGroup(supscription);
        rbSub2014.setToggleGroup(supscription);
        rbSubAll.setToggleGroup(supscription);
        rbSubAll.setSelected(true);

        getSelectedIdMember();
        getSelectedIdInventory();
        getSelectedIdCar();
        getSelectedIdAction();
        getSelectedYearSubscription();
        getSelectedIdMemberSubscription();
        getSelectedIdCompetition();

        tfQuantityOfMembers.setEditable(false);

        tabMembers.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                try {
                    buildDataMembers();
                    rbAll.setSelected(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        tabInventory.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                try {
                    buildDataInventory();
                    rbInventoryAll.setSelected(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        tabSubscriptions.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                try {
                    buildDataSubscriptions();
                    rbSubAll.setSelected(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        tabCars.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                try {
                    buildDataCars();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        tabCompetitions.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                try {
                    buildDataCompetitions();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        tabActions.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                try {
                    buildDataActions();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });



        }



    public void buildDataMembers() throws SQLException {
        tableMembers.setPlaceholder(new Label(" "));
        int idCzlonka = 1;
        liczbaCzlonkow = 0;
        lastMemberId = 0;
        Statement st = DbConnection.con.createStatement();
        ResultSet rs;

        String sql1 = "SELECT * FROM czlonkowie cz inner join czlonkowie2 cz2 inner join adresy a WHERE cz.id_czlonka = cz2.id_czlonka  AND cz.id_adresu = a.id_adresu";

        dataMembers = FXCollections.observableArrayList();
        String imie, nazwisko, adres, tel, data;
        rs = st.executeQuery(sql1);

        while(rs.next()){
            lastMemberId = rs.getInt("id_czlonka");
            imie = rs.getString("imie");
            nazwisko = rs.getString("nazwisko");
            adres = rs.getString("miejscowosc") + " " + rs.getString("ulica");
            tel = rs.getString("telefon");
            data = rs.getString("data_urodzenia");
            dataMembers.add(new TableMembers(lastMemberId,imie,nazwisko,adres,tel,data));
            liczbaCzlonkow++;

        }

        tcMembersFirstName.setCellValueFactory(new PropertyValueFactory<TableMembers,String>("firstName"));
        tcMembersSurname.setCellValueFactory(new PropertyValueFactory<TableMembers,String>("surName"));
        tcMembersAdress.setCellValueFactory(new PropertyValueFactory<TableMembers,String>("adress"));
        tcMembersPhone.setCellValueFactory(new PropertyValueFactory<TableMembers,String>("phone"));
        tcMemberBirthDate.setCellValueFactory(new PropertyValueFactory<TableMembers,String>("birthDate"));
        tableMembers.setItems(dataMembers);
        tableMembers.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    actionEditMember(new ActionEvent());
                }
            }
        });

        rs.close();
        st.close();

        tfQuantityOfMembers.setText(String.valueOf(liczbaCzlonkow));

    }

    public void buildDataMembersRadio(String mode) throws SQLException {
        tableMembers.setPlaceholder(new Label(" "));
        int liczbaCzlonkowS = 0;
        int idCzlonka = 1;
        Statement st = DbConnection.con.createStatement();
        ResultSet rs;

        String sql1 = "SELECT * FROM czlonkowie cz inner join czlonkowie2 cz2 inner join adresy a WHERE cz.id_czlonka = cz2.id_czlonka  AND cz.id_adresu = a.id_adresu AND rodzaj = '" + mode +"'";

        dataMembers = FXCollections.observableArrayList();
        String imie, nazwisko, adres, tel, data;
        rs = st.executeQuery(sql1);

        while(rs.next()){
            imie = rs.getString("imie");
            nazwisko = rs.getString("nazwisko");
            adres = rs.getString("miejscowosc") + " " + rs.getString("ulica");
            tel = rs.getString("telefon");
            data = rs.getString("data_urodzenia");
            dataMembers.add(new TableMembers(idCzlonka++,imie,nazwisko,adres,tel,data));
            liczbaCzlonkowS++;
        }

        tcMembersFirstName.setCellValueFactory(new PropertyValueFactory<TableMembers,String>("firstName"));
        tcMembersSurname.setCellValueFactory(new PropertyValueFactory<TableMembers,String>("surName"));
        tcMembersAdress.setCellValueFactory(new PropertyValueFactory<TableMembers,String>("adress"));
        tcMembersPhone.setCellValueFactory(new PropertyValueFactory<TableMembers,String>("phone"));
        tcMemberBirthDate.setCellValueFactory(new PropertyValueFactory<TableMembers,String>("birthDate"));
        tableMembers.setItems(dataMembers);
        tableMembers.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    actionEditMember(new ActionEvent());
                }
            }
        });
        rs.close();
        st.close();

        tfQuantityOfMembers.setText(String.valueOf(liczbaCzlonkowS));
    }





    public void buildDataInventory() throws SQLException{

        int idSprzetu = 1;
        liczbaSprzetu = 0;
        lastEquipmmentId = 0;
        tableInventory.setPlaceholder(new Label(" "));
        Statement st = DbConnection.con.createStatement();
        ResultSet rs;
        String sql1 = "SELECT * FROM inwentarz";

        String nazwa,marka;
        int ilosc;
        rs = st.executeQuery(sql1);
        dataInventory = FXCollections.observableArrayList();
        while(rs.next()){
            lastEquipmmentId = rs.getInt("id_sprzetu");
            nazwa = rs.getString("nazwa");
            marka = rs.getString("marka");
            ilosc = rs.getInt("ilosc");

            dataInventory.add(new TableInventory(lastEquipmmentId,nazwa,marka,ilosc));
            liczbaSprzetu++;
        }
        tcInventoryName.setCellValueFactory(new PropertyValueFactory<TableInventory,String>("name"));
        tcInventoryBrand.setCellValueFactory(new PropertyValueFactory<TableInventory,String>("brand"));
        tcInventoryQuantity.setCellValueFactory(new PropertyValueFactory<TableInventory,Integer>("quantity"));
        tableInventory.setItems(dataInventory);
        tableInventory.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2)
                    actionEditInventory(new ActionEvent());
            }
        });
        rs.close();
        st.close();

    }

    public void buildDataInventoryMode(String mode) throws SQLException{

        int idSprzetu = 1;
        liczbaSprzetu = 0;
        tableInventory.setPlaceholder(new Label(" "));
        Statement st = DbConnection.con.createStatement();
        ResultSet rs;
        String sql1 = "SELECT * FROM inwentarz WHERE miejsce = '"+mode+"'";

        String nazwa,marka;
        int ilosc;
        rs = st.executeQuery(sql1);
        dataInventory = FXCollections.observableArrayList();
        while(rs.next()){
            nazwa = rs.getString("nazwa");
            marka = rs.getString("marka");
            ilosc = rs.getInt("ilosc");

            dataInventory.add(new TableInventory(idSprzetu++,nazwa,marka,ilosc));
            liczbaSprzetu++;
        }
        tcInventoryName.setCellValueFactory(new PropertyValueFactory<TableInventory,String>("name"));
        tcInventoryBrand.setCellValueFactory(new PropertyValueFactory<TableInventory,String>("brand"));
        tcInventoryQuantity.setCellValueFactory(new PropertyValueFactory<TableInventory,Integer>("quantity"));
        tableInventory.setItems(dataInventory);
        tableInventory.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2)
                    actionEditInventory(new ActionEvent());
            }
        });
        rs.close();
        st.close();

    }

    public void buildDataActions() throws SQLException{
        int idWyjazdu = 1;
        liczbaWyjazdow = 0;
        lastActionId = 0;
        tableActions.setPlaceholder(new Label(" "));
        Statement st = DbConnection.con.createStatement();
        ResultSet rs;
        String sql1 = "SELECT * FROM wyjazdy";

        String rodzaj,adres,data,czasWyjazdu,czasPowrotu;
        dataActions = FXCollections.observableArrayList();
        rs = st.executeQuery(sql1);
        while(rs.next()){
            lastActionId = rs.getInt("id_wyjazdu");
            rodzaj = rs.getString("rodzaj");
            adres = rs.getString("miejsce_akcji");
            data = rs.getString("data_wyjazdu");
            czasWyjazdu = rs.getString("czas_wyjazdu");
            czasPowrotu = rs.getString("czas_powrotu");
            dataActions.add(new TableActions(lastActionId,rodzaj,adres,data,czasWyjazdu,czasPowrotu));
            liczbaWyjazdow++;
        }
        tcActionsKind.setCellValueFactory(new PropertyValueFactory<TableActions,String>("kind"));
        tcActionsAddress.setCellValueFactory(new PropertyValueFactory<TableActions,String >("address"));
        tcActionsDate.setCellValueFactory(new PropertyValueFactory<TableActions,String>("date"));
        tcActionsTimeStart.setCellValueFactory(new PropertyValueFactory<TableActions,String>("timeStart"));
        tcActionsTimeBack.setCellValueFactory(new PropertyValueFactory<TableActions,String>("timeBack"));
        tableActions.setItems(dataActions);
        tableActions.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2)
                    actionEditAction(new ActionEvent());
            }
        });
        rs.close();
        st.close();

    }

    public void buildDataCars() throws SQLException{
        int idSamochodu = 1;
        lastCarId = 0;
        tableCars.setPlaceholder(new Label(" "));
        Statement st = DbConnection.con.createStatement();
        ResultSet rs;
        String sql1 = "SELECT * FROM samochody";

        String marka,typ,numerRejestracyjny,dataPrzegladu,dataProdukcji;
        dataCars = FXCollections.observableArrayList();
        rs = st.executeQuery(sql1);
        while(rs.next()){
            lastCarId = rs.getInt("id_samochodu");
            marka = rs.getString("marka");
            typ = rs.getString("typ");
            numerRejestracyjny = rs.getString("numer_rejestracyjny");
            dataPrzegladu = rs.getString("termin_przegladu");
            dataProdukcji = rs.getString("data_produkcji");
            dataCars.add(new TableCars(lastCarId,marka,typ,numerRejestracyjny,dataPrzegladu,dataProdukcji));
        }
        tcCarsBrand.setCellValueFactory(new PropertyValueFactory<TableCars,String>("brand"));
        tcCarsType.setCellValueFactory(new PropertyValueFactory<TableCars,String >("type"));
        tcCarsRegistrationNumber.setCellValueFactory(new PropertyValueFactory<TableCars,String>("registrationNumber"));
        tcCarsServiceTerm.setCellValueFactory(new PropertyValueFactory<TableCars,String>("serviceTerm"));
        tcCarsProductionDate.setCellValueFactory(new PropertyValueFactory<TableCars,String>("productionDate"));
        tableCars.setItems(dataCars);
        tableCars.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2)
                    actionEditCar(new ActionEvent());
            }
        });
        rs.close();
        st.close();

    }

    public void buildDataCompetitions() throws SQLException{
        int idZawodow = 1;
        lastCompetitionId = 0;
        tableCompetitions.setPlaceholder(new Label(" "));
        liczbaZawodow = 0;
        Statement st = DbConnection.con.createStatement();
        ResultSet rs;
        String sql1 = "SELECT * FROM zawody";

        String rodzaj,data,miejsce;
        dataCompetitions = FXCollections.observableArrayList();
        int pozycja;
        rs = st.executeQuery(sql1);
        while(rs.next()){
            lastCompetitionId = rs.getInt("id_zawodow");
            rodzaj = rs.getString("rodzaj");
            data = rs.getString("data_zawodow");
            miejsce = rs.getString("miejsce");
            pozycja = rs.getInt("pozycja");
            dataCompetitions.add(new TableCompetitions(lastCompetitionId,rodzaj,data,miejsce,pozycja));
            liczbaZawodow++;
        }
        tcCompetitionsKind.setCellValueFactory(new PropertyValueFactory<TableCompetitions,String>("kind"));
        tcCompetitionsDate.setCellValueFactory(new PropertyValueFactory<TableCompetitions,String>("date"));
        tcCompetitionsPlace.setCellValueFactory(new PropertyValueFactory<TableCompetitions,String>("place"));
        tcCompetitionsPosition.setCellValueFactory(new PropertyValueFactory<TableCompetitions,Integer>("pos"));
        tableCompetitions.setItems(dataCompetitions);
        tableCompetitions.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2)
                actionEditCompetition(new ActionEvent());
            }
        });
        rs.close();
        st.close();
    }

    public void buildDataSubscriptions()throws SQLException{
        tableSubscriptions.setPlaceholder(new Label(" "));
        Statement st = DbConnection.con.createStatement();
        liczbaSkladek = 0;
        ResultSet rs;
        String sql1 = "SELECT * FROM skladki s INNER JOIN czlonkowie cz WHERE s.id_czlonka = cz.id_czlonka";
        dataSubscriptions = FXCollections.observableArrayList();
        String czlonek,date;
        int fee,year,id;
        rs = st.executeQuery(sql1);
        while(rs.next()){
            id = rs.getInt("id_czlonka");
            czlonek = rs.getString("imie") + " " + rs.getString("nazwisko");
            date = rs.getString("data_skladki");
            fee = rs.getInt("kwota");
            year = rs.getInt("rok");
            dataSubscriptions.add(new TableSubscriptions(id,czlonek,fee,date,year));
            liczbaSkladek++;
        }
        tcSubscriptionsMember.setCellValueFactory(new PropertyValueFactory<TableSubscriptions,String>("member"));
        tcSubscriptionsFee.setCellValueFactory(new PropertyValueFactory<TableSubscriptions,Integer>("fee"));
        tcSubscriptionsDate.setCellValueFactory(new PropertyValueFactory<TableSubscriptions,String>("date"));
        tcSubscriptionsYear.setCellValueFactory(new PropertyValueFactory<TableSubscriptions,Integer>("year"));
        tableSubscriptions.setItems(dataSubscriptions);

        rs.close();
        st.close();

    }

    public void buildDataSubscriptionsMode(int mode)throws SQLException{
        tableSubscriptions.setPlaceholder(new Label(" "));
        Statement st = DbConnection.con.createStatement();
        liczbaSkladek = 0;
        ResultSet rs;
        String sql1 = "SELECT * FROM skladki s INNER JOIN czlonkowie cz WHERE s.id_czlonka = cz.id_czlonka AND s.rok = "+mode;
        dataSubscriptions = FXCollections.observableArrayList();
        String czlonek,date;
        int fee,year,id;
        rs = st.executeQuery(sql1);
        while(rs.next()){
            id = rs.getInt("id_czlonka");
            czlonek = rs.getString("imie") + " " + rs.getString("nazwisko");
            date = rs.getString("data_skladki");
            fee = rs.getInt("kwota");
            year = rs.getInt("rok");
            dataSubscriptions.add(new TableSubscriptions(id,czlonek,fee,date,year));
            liczbaSkladek++;
        }
        tcSubscriptionsMember.setCellValueFactory(new PropertyValueFactory<TableSubscriptions,String>("member"));
        tcSubscriptionsFee.setCellValueFactory(new PropertyValueFactory<TableSubscriptions,Integer>("fee"));
        tcSubscriptionsDate.setCellValueFactory(new PropertyValueFactory<TableSubscriptions,String>("date"));
        tcSubscriptionsYear.setCellValueFactory(new PropertyValueFactory<TableSubscriptions,Integer>("year"));
        tableSubscriptions.setItems(dataSubscriptions);

        rs.close();
        st.close();
    }



    public void actionAddMember(ActionEvent actionEvent) {
        try {
            AddMemberController.id = lastMemberId+1;
            createScene.createScene("/Windows/AddMemberWindow.fxml", "Dodaj członka");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionEditMember(ActionEvent actionEvent) {
        if(idMemberSelected > 0 && idMemberSelected <= lastMemberId){
            try{
                EditMemberController.id = getIdMemberSelected();
                EditMemberController.mainStage = (Stage) tfQuantityOfMembers.getScene().getWindow();
                createScene.createScene("/Windows/EditMemberWindoww.fxml","Informacje o członku");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void actionRemoveMember(ActionEvent actionEvent) {
        String sql1 = "DELETE FROM czlonkowie WHERE id_czlonka = "+getIdMemberSelected();
        String sql2 = "DELETE FROM czlonkowie2 WHERE id_czlonka = "+getIdMemberSelected();
        String sql3 = "SELECT id_adresu FROM czlonkowie cz WHERE id_czlonka = "+getIdMemberSelected();
        int idAdresu = 0;
        try {
            ResultSet rs = DbConnection.con.createStatement().executeQuery(sql3);
            while (rs.next()){
                idAdresu = rs.getInt("id_adresu");
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql4 = "DELETE FROM adresy where id_adresu = "+idAdresu;
        try {
            DbConnection.con.createStatement().executeUpdate(sql1);
            DbConnection.con.createStatement().executeUpdate(sql2);
            DbConnection.con.createStatement().executeUpdate(sql4);
            buildDataMembers();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void actionSelectHonouredMembers(ActionEvent actionEvent) {
        try {
            buildDataMembersRadio("Honorowy");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelectYoungMembers(ActionEvent actionEvent) {
        try {
            buildDataMembersRadio("Młodzieżowy");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelectNormalMembers(ActionEvent actionEvent) {
        try {
            buildDataMembersRadio("Zwyczajny");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionAddInventory(ActionEvent actionEvent) {
        try{
            AddInventoryController.id = lastEquipmmentId+1;
            createScene.createScene("/Windows/AddInventoryWindow.fxml", "Dodaj sprzęt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionEditInventory(ActionEvent actionEvent) {
        try {
            EditInventoryController.id = getIdInventorySelected();
            EditInventoryController.mainStage = (Stage) tfQuantityOfMembers.getScene().getWindow();
            createScene.createScene("/Windows/EditInventoryWindow.fxml", "Informacje o inwentarzu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveInventory(ActionEvent actionEvent) {
        String sql1 = "DELETE FROM inwentarz WHERE id_sprzetu = "+getIdInventorySelected();
        try {
            DbConnection.con.createStatement().executeUpdate(sql1);
            buildDataInventory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void actionSelectGarage(ActionEvent actionEvent) {
        try {
            buildDataInventoryMode("Garaż");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelectCar(ActionEvent actionEvent) {
        try {
            buildDataInventoryMode("Samochód");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelectRemiza(ActionEvent actionEvent) {
        try {
            buildDataInventoryMode("Remiza");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelectKitchen(ActionEvent actionEvent) {
        try {
            buildDataInventoryMode("Kuchnia");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelectSquare(ActionEvent actionEvent) {
        try {
            buildDataInventoryMode("Scena");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelectInventoryAll(ActionEvent actionEvent){
        try {
            buildDataInventory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionAddAction(ActionEvent actionEvent) {
        try{
            AddActionController.id = lastActionId+1;
            createScene.createScene("/Windows/AddActionsWindow.fxml", "Dodaj wyjazd");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionEditAction(ActionEvent actionEvent) {

        try {
            EditActionController.id = getIdActionSelected();
            EditActionController.mainStage = (Stage) tfQuantityOfMembers.getScene().getWindow();
            createScene.createScene("/Windows/EditActionsWindow.fxml", "Informacje o zdarzeniu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveAction(ActionEvent actionEvent) {

        String sql1 = "DELETE FROM wyjazdy WHERE id_wyjazdu = "+getIdActionSelected();
        String sql2 = "DELETE FROM uczestnicy_wyjazdu WHERE id_wyjazdu = "+getIdActionSelected();

        try {
            DbConnection.con.createStatement().executeUpdate(sql1);
            DbConnection.con.createStatement().executeUpdate(sql2);
            buildDataActions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionAddCar(ActionEvent actionEvent) {
        try{
            AddCarController.id = lastCarId+1;
            createScene.createScene("/Windows/AddCarWindow.fxml", "Dodaj samochód");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionEditCar(ActionEvent actionEvent) {

        try {
            EditCarController.id = getIdCarSelected();
            EditCarController.mainStage = (Stage) tfQuantityOfMembers.getScene().getWindow();
            createScene.createScene("/Windows/EditCarWindow.fxml","Informacje o samochodzie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveCar(ActionEvent actionEvent) {
        String sql = "DELETE FROM samochody WHERE id_samochodu = "+getIdCarSelected();
        try {
            DbConnection.con.createStatement().executeUpdate(sql);
            buildDataCars();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionAddCompetition(ActionEvent actionEvent) {
        try{
            AddCompetitionController.id = lastCompetitionId+1;
            createScene.createScene("/Windows/AddCompetitionWindow.fxml","Dodaj zawody");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionEditCompetition(ActionEvent actionEvent) {

        try {
            EditCompetitionController.id = getIdCompetitionSelected();
            EditCompetitionController.mainStage = (Stage) tfQuantityOfMembers.getScene().getWindow();
            createScene.createScene("/Windows/EditCompetitionWindow.fxml","Informacje o zawodach");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveCompetition(ActionEvent actionEvent) {
        String sql1 = "DELETE FROM zawody WHERE id_zawodow = "+getIdCompetitionSelected();
        String sql2 = "DELETE FROM uczestnicy_sztafeta WHERE id_zawodow = "+getIdCompetitionSelected();
        String sql3 = "DELETE FROM uczestnicy_bojowka WHERE id_zawodow = "+getIdCompetitionSelected();

        try {
            DbConnection.con.createStatement().executeUpdate(sql1);
            DbConnection.con.createStatement().executeUpdate(sql2);
            DbConnection.con.createStatement().executeUpdate(sql3);
            buildDataCompetitions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionAddSubscription(ActionEvent actionEvent) {
        try{
            createScene.createScene("/Windows/AddSubscriptionWindow.fxml","Dodaj składkę");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionRemoveSubscription(ActionEvent actionEvent) {

        String sql = "DELETE FROM skladki WHERE id_czlonka = "+getIdMemberSubscriptionSelected() + " AND rok = "+getYearSubscriptionSelected();
        try {
            DbConnection.con.createStatement().executeUpdate(sql);
            buildDataSubscriptions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelecteSubAll(ActionEvent actionEvent){
        try {
            buildDataSubscriptions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelectSubscription2016(ActionEvent actionEvent) {

        try {
            buildDataSubscriptionsMode(2016);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelectSubscription2015(ActionEvent actionEvent) {

        try {
            buildDataSubscriptionsMode(2015);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionSelectSubscription2014(ActionEvent actionEvent) {

        try {
            buildDataSubscriptionsMode(2014);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getIdMemberSelected() {
        return idMemberSelected;
    }

    public static void setIdMemberSelected(int idMemberSelected) {
        MainWindowController.idMemberSelected = idMemberSelected;
    }

    public void getSelectedIdMember(){
        tableMembers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableMembers.getSelectionModel().getSelectedItem() != null)
                setIdMemberSelected(newValue.getId());
        });
    }

    public static int getIdInventorySelected() {
        return idInventorySelected;
    }

    public static void setIdInventorySelected(int idInventorySelected) {
        MainWindowController.idInventorySelected = idInventorySelected;
    }

    public void getSelectedIdInventory(){
        tableInventory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableInventory.getSelectionModel().getSelectedItem() != null)
                setIdInventorySelected(newValue.getId());
        });
    }

    public static int getIdActionSelected() {
        return idActionSelected;
    }

    public static void setIdActionSelected(int idActionSelected) {
        MainWindowController.idActionSelected = idActionSelected;
    }

    public void getSelectedIdAction(){
        tableActions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableActions.getSelectionModel().getSelectedItem() != null)
                setIdActionSelected(newValue.getId());
        });
    }

    public static int getIdCarSelected() {
        return idCarSelected;
    }

    public static void setIdCarSelected(int idCarSelected) {
        MainWindowController.idCarSelected = idCarSelected;
    }

    public void getSelectedIdCar(){
        tableCars.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableCars.getSelectionModel().getSelectedItem() != null){
                setIdCarSelected(newValue.getId());
            }
        });
    }

    public static int getYearSubscriptionSelected() {
        return yearSubscriptionSelected;
    }

    public static void setYearSubscriptionSelected(int yearSubscriptionSelected) {
        MainWindowController.yearSubscriptionSelected = yearSubscriptionSelected;
    }
    public void getSelectedYearSubscription(){
        tableSubscriptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableSubscriptions.getSelectionModel().getSelectedItem() != null){
                setYearSubscriptionSelected(newValue.getYear());
            }
        });
    }

    public static int getIdMemberSubscriptionSelected() {
        return idMemberSubscriptionSelected;
    }

    public static void setIdMemberSubscriptionSelected(int idMemberSelected) {
        MainWindowController.idMemberSubscriptionSelected = idMemberSelected;
    }

    public void getSelectedIdMemberSubscription(){
        tableSubscriptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableSubscriptions.getSelectionModel().getSelectedItem() != null)
                setIdMemberSubscriptionSelected(newValue.getIdMember());
        });
    }

    public static int getIdCompetitionSelected() {
        return idCompetitionSelected;
    }

    public static void setIdCompetitionSelected(int idCompetitionSelected) {
        MainWindowController.idCompetitionSelected = idCompetitionSelected;
    }

    public void getSelectedIdCompetition(){
        tableCompetitions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableCompetitions.getSelectionModel().getSelectedItem() != null)
                setIdCompetitionSelected(newValue.getId());
        });
    }

    public void actionAllMembers(ActionEvent actionEvent) {
        try {
            buildDataMembers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
