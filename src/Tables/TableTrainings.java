package Tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Konrad on 04-03-2016.
 */
public class TableTrainings {

    private SimpleIntegerProperty id;
    private SimpleStringProperty kind;
    private SimpleStringProperty date;
    private SimpleStringProperty place;

    public TableTrainings(int id, String kind, String date, String place){
        this.id = new SimpleIntegerProperty(id);
        this.kind = new SimpleStringProperty(kind);
        this.date = new SimpleStringProperty(date);
        this.place = new SimpleStringProperty(place);
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
}
