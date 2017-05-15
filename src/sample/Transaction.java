package sample;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;

public class Transaction {

    private final SimpleStringProperty transactionId;
    private final SimpleStringProperty valueOut;
    private final SimpleStringProperty valueIn;
    private final SimpleStringProperty time;
    private final SimpleStringProperty addressesIn;
    private final SimpleStringProperty addressesOut;
    private final SimpleStringProperty fees;


    public Transaction() {
        this("","","","","","","");
    }

    public Transaction(String transactionId, String valueOut, String valueIn, String time, String addressesIn, String addressesOut, String fees) {
        this.transactionId = new SimpleStringProperty(transactionId);
        this.valueOut = new SimpleStringProperty(valueOut);
        this.valueIn = new SimpleStringProperty(valueIn);
        this.time = new SimpleStringProperty(time);
        this.addressesIn = new SimpleStringProperty(addressesIn);
        this.addressesOut = new SimpleStringProperty(addressesOut);
        this.fees = new SimpleStringProperty(fees);
    }

    public Transaction(JSONObject json){
        this.transactionId = new SimpleStringProperty(json.getString("txid"));
        this.valueOut = new SimpleStringProperty(json.get("valueOut").toString());
        this.valueIn = new SimpleStringProperty(json.get("valueIn").toString());
        this.time = new SimpleStringProperty(Instant.ofEpochSecond((Integer) json.get("time")).toString());
        String in = "";
        JSONArray a = json.getJSONArray("vout");
        for (int i = 0; i < a.length(); i++){
            in += a.getJSONObject(i).getJSONObject("scriptPubKey").get("addresses").toString();
        }
        this.addressesIn = new SimpleStringProperty(in);
        String out = "";
        a = json.getJSONArray("vin");
        for (int i = 0; i < a.length(); i++){
            out += a.getJSONObject(i).get("addr").toString();
        }
        this.addressesOut = new SimpleStringProperty(out);
        this.fees = new SimpleStringProperty(json.get("fees").toString());
    }

    public String getTransactionId() {
        return transactionId.get();
    }

    public void setTransactionId(String transactionId) {
        this.transactionId.set(transactionId);
    }

    public String getValueOut() {
        return valueOut.get();
    }

    public void setValueOut(String valueOut) {
        this.valueOut.set(valueOut);
    }

    public String getValueIn() {
        return valueIn.get();
    }

    public void setValueIn(String valueIn) {
        this.valueIn.set(valueIn);
    }

    public String getAddressesIn() {
        return addressesIn.get();
    }

    public void setAddressesIn(String addressesIn) {
        this.addressesIn.set(addressesIn);
    }

    public String getAddressesOut() {
        return addressesOut.get();
    }

    public void setAddressesOut(String addressesOut) {
        this.addressesOut.set(addressesOut);
    }

    public String getFees() {
        return fees.get();
    }

    public void setFees(String fees) {
        this.fees.set(fees);
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

}
