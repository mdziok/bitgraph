package sample;


import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class DatabaseDriver implements AutoCloseable {
    protected final Driver driver;

    public DatabaseDriver() {
        driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "neo4j" ) );
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void addTransaction(String txid) {
        try ( Session session = driver.session() )
        {
            session.run("CREATE (a:Transaction {txid: $txid})", parameters( "txid", txid ) );
        }
    }

}
