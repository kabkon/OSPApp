package Tables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Konrad on 03-03-2016.
 */
public class TableSubscriptions {

    private SimpleStringProperty member;
    private SimpleIntegerProperty idMember;
    private SimpleIntegerProperty fee;
    private SimpleStringProperty date;
    private SimpleIntegerProperty year;

    public TableSubscriptions(int idMember, String member, int fee, String date, int year){
        this.idMember = new SimpleIntegerProperty(idMember);
        this.member = new SimpleStringProperty(member);
        this.fee = new SimpleIntegerProperty(fee);
        this.date = new SimpleStringProperty(date);
        this.year = new SimpleIntegerProperty(year);
    }

    public TableSubscriptions(int idMember,int fee,int year){
        this.idMember = new SimpleIntegerProperty(idMember);
        this.fee = new SimpleIntegerProperty(fee);
        this.year = new SimpleIntegerProperty(year);
    }

    public String getMember() {
        return member.get();
    }

    public SimpleStringProperty memberProperty() {
        return member;
    }

    public void setMember(String member) {
        this.member.set(member);
    }

    public int getFee() {
        return fee.get();
    }

    public SimpleIntegerProperty feeProperty() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee.set(fee);
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

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public int getIdMember() {
        return idMember.get();
    }

    public SimpleIntegerProperty idMemberProperty() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember.set(idMember);
    }
}
