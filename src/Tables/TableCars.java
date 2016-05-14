package Tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Konrad on 03-03-2016.
 */
public class TableCars {

    private SimpleIntegerProperty id;
    private SimpleStringProperty brand;
    private SimpleStringProperty type;
    private SimpleStringProperty registrationNumber;
    private SimpleStringProperty serviceTerm;
    private SimpleStringProperty productionDate;

    public TableCars(int fId, String fBrand, String fType, String fRegistrationNumber, String fServiceTerm, String fProductionDate){
        id = new SimpleIntegerProperty(fId);
        brand = new SimpleStringProperty(fBrand);
        type = new SimpleStringProperty(fType);
        registrationNumber = new SimpleStringProperty(fRegistrationNumber);
        serviceTerm = new SimpleStringProperty(fServiceTerm);
        productionDate = new SimpleStringProperty(fProductionDate);
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

    public String getBrand() {
        return brand.get();
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getRegistrationNumber() {
        return registrationNumber.get();
    }

    public SimpleStringProperty registrationNumberProperty() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber.set(registrationNumber);
    }

    public String getServiceTerm() {
        return serviceTerm.get();
    }

    public SimpleStringProperty serviceTermProperty() {
        return serviceTerm;
    }

    public void setServiceTerm(String serviceTerm) {
        this.serviceTerm.set(serviceTerm);
    }

    public String getProductionDate() {
        return productionDate.get();
    }

    public SimpleStringProperty productionDateProperty() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate.set(productionDate);
    }
}

