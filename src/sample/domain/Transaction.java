package sample.domain;


import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import java.util.Date;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Transaction {

    @GraphId
    private Long id;
    private String txid;
    private double valueOut;
    private double valueIn;
    @DateLong
    private Date time;

    @Relationship(type="TRANSFERED_FROM", direction = Relationship.INCOMING)
    public Set<WalletAddress> addressesIn;

    @Relationship(type="TRANSFERED_TO", direction = Relationship.OUTGOING)
    public Set<WalletAddress> addressesOut;
    private double fees;

    public Transaction() {

    }

    public Transaction(String txid, double valueOut, double valueIn, Date time, double fees)  {
        this.txid = txid;
        this.valueOut =valueOut;
        this.valueIn = valueIn;
        this.time = time;
        this.addressesIn = new HashSet<>();
        this.addressesOut = new HashSet<>();
        this.fees = fees;
    }

    public Long getId() {
        return id;
    }

    public String getTxid() {
        return txid;
    }

    public double getValueOut() {
        return valueOut;
    }

    public double getValueIn() {
        return valueIn;
    }

    public Date getTime() {
        return time;
    }

    public double getFees() {
        return fees;
    }



}
