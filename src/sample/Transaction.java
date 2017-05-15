package sample;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transaction {

    private final SimpleStringProperty transactionIdSP;
    private final SimpleStringProperty valueOutSP;
    private final SimpleStringProperty valueInSP;
    private final SimpleStringProperty timeSP;
    private final SimpleStringProperty addressesInSP;
    private final SimpleStringProperty addressesOutSP;
    private final SimpleStringProperty feesSP;

    private String id;
    private double valueOut;
    private double valueIn;
    private Date time;
    private List<String> addressesIn;
    private List<String> addressesOut;
    private double fees;


    public Transaction() {
        this("",0,0,new Date(),new ArrayList<>(),new ArrayList<>(),0);
    }

    public Transaction(String transactionId, double valueIn, double valueOut, Date time, List<String> addressesIn, List<String> addressesOut, double fees) {
        this.id = transactionId;
        this.transactionIdSP = new SimpleStringProperty(transactionId);
        this.valueIn = valueIn;
        this.valueOut = valueOut;
        this.valueOutSP = new SimpleStringProperty(String.valueOf(valueOut));
        this.valueInSP = new SimpleStringProperty(String.valueOf(valueIn));
        this.time = time;
        this.timeSP = new SimpleStringProperty(time.toString());
        this.addressesIn = addressesIn;
        this.addressesOut = addressesOut;
        this.addressesInSP = new SimpleStringProperty(addressesIn.toString());
        this.addressesOutSP = new SimpleStringProperty(addressesOut.toString());
        this.fees = fees;
        this.feesSP = new SimpleStringProperty(String.valueOf(fees));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValueOut() {
        return valueOut;
    }

    public void setValueOut(int valueOut) {
        this.valueOut = valueOut;
    }

    public double getValueIn() {
        return valueIn;
    }

    public void setValueIn(int valueIn) {
        this.valueIn = valueIn;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<String> getAddressesIn() {
        return addressesIn;
    }

    public void setAddressesIn(List<String> addressesIn) {
        this.addressesIn = addressesIn;
    }

    public List<String> getAddressesOut() {
        return addressesOut;
    }

    public void setAddressesOut(List<String> addressesOut) {
        this.addressesOut = addressesOut;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public String getTransactionIdSP() {
        return transactionIdSP.get();
    }

    public void setTransactionIdSP(String transactionId) {
        this.transactionIdSP.set(transactionId);
    }

    public String getValueOutSP() {
        return valueOutSP.get();
    }

    public void setValueOutSP(String valueOut) {
        this.valueOutSP.set(valueOut);
    }

    public String getValueInSP() {
        return valueInSP.get();
    }

    public void setValueInSP(String valueIn) {
        this.valueInSP.set(valueIn);
    }

    public String getAddressesInSP() {
        return addressesInSP.get();
    }

    public void setAddressesInSP(String addressesIn) {
        this.addressesInSP.set(addressesIn);
    }

    public String getAddressesOutSP() {
        return addressesOutSP.get();
    }

    public void setAddressesOutSP(String addressesOut) {
        this.addressesOutSP.set(addressesOut);
    }

    public String getFeesSP() {
        return feesSP.get();
    }

    public void setFeesSP(String fees) {
        this.feesSP.set(fees);
    }

    public String getTimeSP() {
        return timeSP.get();
    }

    public void setTimeSP(String time) {
        this.timeSP.set(time);
    }

}
