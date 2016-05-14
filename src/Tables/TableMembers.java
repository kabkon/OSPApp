package Tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Konrad on 02-03-2016.
 */
public class TableMembers {

    private SimpleIntegerProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty surName;
    private SimpleStringProperty adress;
    private SimpleStringProperty phone;
    private SimpleStringProperty birthDate;

    public TableMembers(int fId, String fFirstName, String fSurName, String fAdress, String fPhone, String birthDate){
        id = new SimpleIntegerProperty(fId);
        firstName = new SimpleStringProperty(fFirstName);
        surName = new SimpleStringProperty(fSurName);
        adress = new SimpleStringProperty(fAdress);
        phone = new SimpleStringProperty(fPhone);
        this.birthDate = new SimpleStringProperty(birthDate);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getSurName() {
        return surName.get();
    }

    public SimpleStringProperty surNameProperty() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName.set(surName);
    }

    public String getAdress() {
        return adress.get();
    }

    public SimpleStringProperty adressProperty() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress.set(adress);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public SimpleStringProperty birthDateProperty() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }
}
