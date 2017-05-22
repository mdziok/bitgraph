package sample.domain;


import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class WalletAddress {

    @GraphId
    private Long graphId;

    private String addr;
    private double balance;
    private double totalReceived;


    private double totalSent;

    @Relationship(type="TRANSFERED")
    private Set<Transaction> transactions= new HashSet();

    public WalletAddress() {

    }
    public WalletAddress(String addr, Transaction t) {
        this.addr =addr;
        this.balance = 0.0;
        this.totalReceived = 0.0;
        this.totalSent = 0.0;
        this.transactions.add(t) ;
    }
    public WalletAddress(String addr, double balance, double totalReceived, double totalSent, Set<Transaction> transactions) {
        this.addr = addr;
        this.balance = balance;
        this.totalReceived = totalReceived;
        this.totalSent = totalSent;
        this.transactions = transactions;
    }

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(double totalReceived) {
        this.totalReceived = totalReceived;
    }

    public double getTotalSent() {
        return totalSent;
    }

    public void setTotalSent(double totalSent) {
        this.totalSent = totalSent;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    public void addTransaction(Transaction t) {
        this.transactions.add(t);
    }



}
