package sample;

import javafx.beans.property.SimpleStringProperty;

public class Transaction {

    private final SimpleStringProperty address;
    private final SimpleStringProperty value;
    private final SimpleStringProperty time;

    public Transaction() {
        this("","","");
    }

    public Transaction(String address, String value, String time) {
        this.address = new SimpleStringProperty(address);
        this.value = new SimpleStringProperty(value);
        this.time = new SimpleStringProperty(time);
    }


    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

}
