package sample;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;


public class Neo4jSessionFactory {

    private static SessionFactory sessionFactory;
    private static Neo4jSessionFactory factory = new Neo4jSessionFactory();

    public static Neo4jSessionFactory getInstance() {
        return factory;
    }

    // prevent external instantiation
    private Neo4jSessionFactory() {
        Configuration cfg = new Configuration();
        cfg.driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI("http://localhost:7474")
                .setCredentials("neo4j","123qwe");
        sessionFactory = new SessionFactory(cfg, "sample.domain");
    }

    public Session getNeo4jSession() {
        return sessionFactory.openSession();
    }
}
