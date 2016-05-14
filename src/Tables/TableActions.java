package Tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Konrad on 03-03-2016.
 */
public class TableActions {

    private SimpleIntegerProperty id;
    private SimpleStringProperty kind;
    private SimpleStringProperty address;
    private SimpleStringProperty date;
    private SimpleStringProperty timeStart;
    private SimpleStringProperty timeBack;

    public TableActions(int fId, String fKind, String place, String fDate, String fTimeStart, String fTimeBack){
        id = new SimpleIntegerProperty(fId);
        kind = new SimpleStringProperty(fKind);
        this.address = new SimpleStringProperty(place);
        date = new SimpleStringProperty(fDate);
        timeStart = new SimpleStringProperty(fTimeStart);
        timeBack = new SimpleStringProperty(fTimeBack);

    }

    public TableActions(String kind, String place, String date){
        this.kind = new SimpleStringProperty(kind);
        this.date = new SimpleStringProperty(date);
        this.address = new SimpleStringProperty(place);
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

    public String getKind() {
        return kind.get();
    }

    public SimpleStringProperty kindProperty() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind.set(kind);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTimeStart() {
        return timeStart.get();
    }

    public SimpleStringProperty timeStartProperty() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart.set(timeStart);
    }

    public String getTimeBack() {
        return timeBack.get();
    }

    public SimpleStringProperty timeBackProperty() {
        return timeBack;
    }

    public void setTimeBack(String timeBack) {
        this.timeBack.set(timeBack);
    }
}
