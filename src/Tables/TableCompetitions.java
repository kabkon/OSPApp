package Tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Konrad on 03-03-2016.
 */
public class TableCompetitions {

    private SimpleIntegerProperty id;
    private SimpleStringProperty kind;
    private SimpleStringProperty date;
    private SimpleStringProperty place;
    private SimpleIntegerProperty pos;

    public TableCompetitions(int id, String kind, String date, String place, int pos){
        this.id = new SimpleIntegerProperty(id);
        this.kind = new SimpleStringProperty(kind);
        this.date = new SimpleStringProperty(date);
        this.place = new SimpleStringProperty(place);
        this.pos = new SimpleIntegerProperty(pos);
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

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getPlace() {
        return place.get();
    }

    public SimpleStringProperty placeProperty() {
        return place;
    }

    public void setPlace(String place) {
        this.place.set(place);
    }

    public int getPos() {
        return pos.get();
    }

    public SimpleIntegerProperty posProperty() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos.set(pos);
    }
}
