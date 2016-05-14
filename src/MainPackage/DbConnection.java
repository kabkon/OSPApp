package MainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Konrad on 01-03-2016.
 */
public class DbConnection {
    public static Connection con = null;

    public static Connection getConnect() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:Database.db";
            con = DriverManager.getConnection(url);
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }



    public static boolean isConnection() {
        if (con == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void createTables() throws SQLException {

        String createCzlonkowie = "CREATE TABLE IF NOT EXISTS czlonkowie\n"+
                "(\n"+
                " id_czlonka integer NOT NULL PRIMARY KEY,\n"+
                " imie text NOT NULL,\n"+
                " nazwisko text NOT NULL,\n"+
                " data_urodzenia text NOT NULL,\n"+
                " plec text NOT NULL,\n"+
                " rodzaj text NOT NULL,\n"+
                " pesel text NOT NULL ,\n"+
                " imie_ojca text,\n"+
                " miejsce_pracy text,\n"+
                " id_adresu integer NOT NULL,\n"+
                " FOREIGN KEY (id_adresu) REFERENCES adresy(id_adresu)\n"+
                ");";

        String createAdresy = "CREATE TABLE IF NOT EXISTS adresy\n"+
                "(\n"+
                " id_adresu integer NOT NULL PRIMARY KEY,\n"+
                " miejscowosc text NOT NULL,\n"+
                " ulica text,\n"+
                " kod_pocztowy text,\n"+
                " gmina text \n"+
                ");";

        String createCzlonkowie2 = "CREATE TABLE IF NOT EXISTS czlonkowie2\n"+
                "(\n"+
                " id_czlonka integer NOT NULL PRIMARY KEY,\n"+
                " poczatek_sluzby text NOT NULL,\n"+
                " badania_lekarskie text NOT NULL,\n"+
                " prawo_jazdy text,\n"+
                " email text, \n"+
                " telefon text,\n"+
                " jot text\n"+
                ");";


        String createInwentarz = "CREATE TABLE IF NOT EXISTS inwentarz\n"+
                "(\n"+
                " id_sprzetu integer NOT NULL PRIMARY KEY,\n"+
                " nazwa text NOT NULL,\n"+
                " marka text,\n"+
                " ilosc integer NOT NULL,\n"+
                " miejsce text NOT NULL,\n"+
                " dodatkowe_info text\n"+
                ");";

        String createInwentarzCzlonka = "CREATE TABLE IF NOT EXISTS inwentarz_czlonka\n"+
                "(\n"+
                " id_czlonka integer NOT NULL,\n"+
                " nazwa text NOT NULL,\n"+
                " ilosc_sztuk integer NOT NULL,\n"+
                " info text,\n"+
                " FOREIGN KEY (id_czlonka) REFERENCES czlonkowie(id_czlonka)"+
                ");";

        String createOdznaczenia = "CREATE TABLE IF NOT EXISTS odznaczenia\n"+
                "(\n"+
                " id_czlonka integer NOT NULL,\n"+
                " nazwa text NOT NULL,\n"+
                " data_odznaczenia text NOT NULL,"+
                " info text,\n"+
                " FOREIGN KEY(id_czlonka) REFERENCES czlonkowie(id_czlonka)"+
                ");";

        String createWyjazdy = "CREATE TABLE IF NOT EXISTS wyjazdy\n"+
                "(\n"+
                " id_wyjazdu integer NOT NULL PRIMARY KEY,\n"+
                " rodzaj text NOT NULL,\n"+
                " data_wyjazdu text NOT NULL,\n"+
                " czas_wyjazdu text,\n"+
                " czas_powrotu text,\n"+
                " miejsce_akcji text, \n"+
                " dodatkowe_informacje text\n"+
                ");";

        String createUczestnicyWyjazdu = "CREATE TABLE IF NOT EXISTS uczestnicy_wyjazdu\n"+
                "(\n"+
                " id_wyjazdu integer NOT NULL PRIMARY KEY,\n"+
                " kierowca text,\n"+
                " dowodca text,\n"+
                " rota11 text,\n"+
                " rota12 text,\n"+
                " rota21 text,\n"+
                " rota22 text\n"+
                ");";

        String createSamochody = "CREATE TABLE IF NOT EXISTS samochody\n"+
                "(\n"+
                " id_samochodu integer NOT NULL PRIMARY KEY,\n"+
                " marka text NOT NULL,\n"+
                " typ text,\n"+
                " data_produkcji NOT NULL,\n"+
                " numer_rejestracyjny text NOT NULL,\n"+
                " termin_przegladu text NOT NULL,\n"+
                " zbiornik_paliwa text,\n"+
                " zbiornik_wodny text\n"+
                ");";

        String createZawody = "CREATE TABLE IF NOT EXISTS zawody\n"+
                "(\n"+
                " id_zawodow integer NOT NULL PRIMARY KEY,\n"+
                " rodzaj text NOT NULL,\n"+
                " miejsce text NOT NULL,\n"+
                " data_zawodow text NOT NULL,\n"+
                " pozycja int NOT NULL,\n"+
                " kierownik text "+
                " );";

        String createUczestnicyZawodow = "CREATE TABLE IF NOT EXISTS uczestnicy_sztafeta\n"+
                "(\n"+
                " id_zawodow integer NOT NULL PRIMARY KEY,\n"+
                " uczestnik1 text,"+
                " uczestnik2 text,"+
                " uczestnik3 text,"+
                " uczestnik4 text,"+
                " uczestnik5 text,"+
                " uczestnik6 text,"+
                " uczestnik7 text"+
                ");";

        String createUczestnicyZawodow2 = "CREATE TABLE IF NOT EXISTS uczestnicy_bojowka\n"+
                "(\n"+
                " id_zawodow integer NOT NULL PRIMARY KEY,\n"+
                " uczestnik1 text,"+
                " uczestnik2 text,"+
                " uczestnik3 text,"+
                " uczestnik4 text,"+
                " uczestnik5 text,"+
                " uczestnik6 text,"+
                " uczestnik7 text"+
                ");";

        String createSzkolenia = "CREATE TABLE IF NOT EXISTS szkolenia\n"+
                "(\n"+
                " id_czlonka integer NOT NULL,\n"+
                " nazwa text NOT NULL,\n"+
                " data_szkolenia text NOT NULL,\n"+
                " miejsce_szkolenia text NOT NULL,\n"+
                " dodatkowe_info text,\n"+
                " FOREIGN KEY (id_czlonka) REFERENCES czlonkowie (id_czlonka)\n"+
                ");";

        String createSkladki = "CREATE TABLE IF NOT EXISTS skladki\n"+
                "(\n"+
                " id_czlonka integer NOT NULL,\n"+
                " data_skladki text NOT NULL,\n"+
                " kwota integer NOT NULL,\n"+
                " rok integer NOT NULL,\n "+
                " FOREIGN KEY (id_czlonka) REFERENCES czlonkowie (id_czlonka)"+
                ");";

        con.createStatement().execute(createCzlonkowie);
        con.createStatement().execute(createCzlonkowie2);
        con.createStatement().execute(createAdresy);
        con.createStatement().execute(createInwentarz);
        con.createStatement().execute(createInwentarzCzlonka);
        con.createStatement().execute(createOdznaczenia);
        con.createStatement().execute(createWyjazdy);
        con.createStatement().execute(createUczestnicyWyjazdu);
        con.createStatement().execute(createSamochody);
        con.createStatement().execute(createZawody);
        con.createStatement().execute(createUczestnicyZawodow);
        con.createStatement().execute(createUczestnicyZawodow2);
        con.createStatement().execute(createSzkolenia);
        con.createStatement().execute(createSkladki);


    }


}
