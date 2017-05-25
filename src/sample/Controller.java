package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import prefux.FxDisplay;
import prefux.Visualization;
import prefux.action.ActionList;
import prefux.action.RepaintAction;
import prefux.action.assignment.NodeDegreeSizeAction;
import prefux.action.layout.graph.ForceDirectedLayout;
import prefux.activity.Activity;
import prefux.controls.DragControl;
import prefux.data.*;
import prefux.data.expression.Predicate;
import prefux.data.expression.parser.ExpressionParser;
import prefux.render.*;
import prefux.util.PrefuseLib;
import prefux.util.collections.IntIterator;
import sample.domain.WalletAddress;
import sample.visualization.ClickControl;
import sample.visualization.DirectedEdgeRenderer;
import sample.visualization.EdgeLabelRenderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.*;

public class Controller {

    private static final String URL = "https://blockexplorer.com/";
    private static final String API_TX = "api/tx/";
    private static org.neo4j.ogm.session.Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

    @FXML private TableView<Transaction> tableView;
    @FXML private GridPane grid;
    @FXML private TextField addressField;

    private FxDisplay display;

    @FXML
    protected void addTransaction() {
        ObservableList<Transaction> data = tableView.getItems();
        try {
//            URL url = new URL(URL + API_TX + addressField.getText());
            URL url = new URL(URL + API_TX + "a01b32246795ca47ed77ef78d56736677ec2f2aae7b400ebbcc95cd784492dc2");
            URLConnection connection = url.openConnection();
            InputStream input = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(input);

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
               builder.append(line);
            }
            String toJson = builder.toString();

            JSONObject json = new JSONObject(toJson);
            String id = json.getString("txid");
            double valueOut = json.getDouble("valueOut");
            double valueIn = json.getDouble("valueIn");
            Date time = Date.from(Instant.ofEpochSecond((Integer) json.get("time")));
            List<String> out = new ArrayList<>();
            List<String> in = new ArrayList<>();

            double fees = json.getDouble("fees");
            sample.domain.Transaction transaction = new sample.domain.Transaction(id, valueOut,valueIn, time,fees);
            //session.save(transaction);

            JSONArray a = json.getJSONArray("vout");
            for (int i = 0; i < a.length(); i++){
                out.add(a.getJSONObject(i).getJSONObject("scriptPubKey").get("addresses").toString());
                WalletAddress addr = new WalletAddress(a.getJSONObject(i).getJSONObject("scriptPubKey").get("addresses").toString(), transaction);
                //session.save(addr);
                transaction.addressesOut.add(addr);
            }
            a = json.getJSONArray("vin");
            for (int i = 0; i < a.length(); i++){
                in.add(a.getJSONObject(i).get("addr").toString());
                WalletAddress addr = new WalletAddress(a.getJSONObject(i).get("addr").toString(), transaction);
                //session.save(addr);
                transaction.addressesIn.add(addr);
            }
            data.add(new Transaction(id, valueOut, valueIn, time, in, out, fees));
            session.save(transaction);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private static final String GROUP = "graph";
    private static final String ID         = "id";
    private static final String SRC = Graph.DEFAULT_SOURCE_KEY;
    private static final String TRG = Graph.DEFAULT_TARGET_KEY;
    private static final String SRCID = SRC+'_'+ID;
    private static final String TRGID = TRG+'_'+ID;

    private HashMap<String, Integer> m_nodeMap = new HashMap<>();

    private List<Transaction> transactions = new ArrayList<>();

    private Graph generateGraph(){
        Schema nschema = new Schema();
        nschema.addColumn("id",String.class);
        nschema.addColumn("type", int.class);
        Table m_nodes = nschema.instantiate();

        Schema eschema = new Schema();

        eschema.addColumn(SRC, int.class);
        eschema.addColumn(TRG, int.class);
        eschema.addColumn(SRCID, String.class);
        eschema.addColumn(TRGID, String.class);
        eschema.addColumn("value", int.class, 0);

        Table m_edges = eschema.instantiate();


        //
//        Transaction transaction = newTransaction(findTransaction("a01b32246795ca47ed77ef78d56736677ec2f2aae7b400ebbcc95cd784492dc2"));
//        findInTransactions(transaction, 1);
//
//
//        int n_row, e_row;
//
//        for (Transaction t : transactions){
//            n_row = m_nodes.addRow();
//            m_nodes.set(n_row, "id", t.getId());
//            m_nodes.set(n_row, "type", 2);
//            m_nodeMap.put(t.getId(), n_row);
//
//            for (String nodeInId : t.getAddressesIn()){
//                if (!m_nodeMap.containsKey(nodeInId)){
//                    n_row = m_nodes.addRow();
//                    m_nodes.set(n_row, "id", nodeInId);
//                    m_nodes.set(n_row, "type", 1);
//                    m_nodeMap.put(nodeInId, n_row);
//                }
//                e_row = m_edges.addRow();
//                m_edges.setString(e_row, SRCID, nodeInId);
//                m_edges.setString(e_row, TRGID, t.getId());
//            }
//        }

        //Dodawanie wierzchołków
        int nsize = m_nodes.addRow();
        m_nodes.set(nsize, "id", "1vFwH9nxXi2gmfZidWXJ2Ce774R4rb6ae");
        m_nodes.set(nsize, "type", 1);
        m_nodeMap.put("1vFwH9nxXi2gmfZidWXJ2Ce774R4rb6ae", nsize);
        nsize = m_nodes.addRow();
        m_nodes.set(nsize, "id", "16mk32aisAgXiebNiMpWj2QQGPtRmbkGQQ");
        m_nodes.set(nsize, "type", 1);
        m_nodeMap.put("16mk32aisAgXiebNiMpWj2QQGPtRmbkGQQ", nsize);
        nsize = m_nodes.addRow();
        m_nodes.set(nsize, "id", "13RdQJQEXBnuNnF9CenYAjxPs87qhAKRKv");
        m_nodes.set(nsize, "type", 1);
        m_nodeMap.put("13RdQJQEXBnuNnF9CenYAjxPs87qhAKRKv", nsize);
        nsize = m_nodes.addRow();
        m_nodes.set(nsize, "id", "b3e7bb1628094ccda32deb594d57511289d2a0228fc097179e7cec550217136c");
        m_nodes.set(nsize, "type", 2);
        m_nodeMap.put("b3e7bb1628094ccda32deb594d57511289d2a0228fc097179e7cec550217136c", nsize);

        // Dodawanie krawędzi
        int esize = m_edges.addRow();
        m_edges.setString(esize, SRCID, "1vFwH9nxXi2gmfZidWXJ2Ce774R4rb6ae");
        m_edges.setString(esize, TRGID, "b3e7bb1628094ccda32deb594d57511289d2a0228fc097179e7cec550217136c");
        esize = m_edges.addRow();
        m_edges.setString(esize, SRCID, "b3e7bb1628094ccda32deb594d57511289d2a0228fc097179e7cec550217136c");
        m_edges.setString(esize, TRGID, "16mk32aisAgXiebNiMpWj2QQGPtRmbkGQQ");
        esize = m_edges.addRow();
        m_edges.setString(esize, SRCID, "b3e7bb1628094ccda32deb594d57511289d2a0228fc097179e7cec550217136c");
        m_edges.setString(esize, TRGID, "13RdQJQEXBnuNnF9CenYAjxPs87qhAKRKv");

        IntIterator rows = m_edges.rows();
        while (rows.hasNext()) {
            int r = rows.nextInt();

            String src = m_edges.getString(r, SRCID);
            if (!m_nodeMap.containsKey(src)) {
                System.out.println("asdasda");
            }
            int s = m_nodeMap.get(src);
            m_edges.setInt(r, SRC, s);

            String trg = m_edges.getString(r, TRGID);
            if (!m_nodeMap.containsKey(trg)) {
                System.out.println("asda" + trg);
            }
            int t = m_nodeMap.get(trg);
            m_edges.setInt(r, TRG, t);
        }
        m_edges.removeColumn(SRCID);
        m_edges.removeColumn(TRGID);

        return new Graph(m_nodes, m_edges, true);
    }



    private void findInTransactions(Transaction transaction, int depth){
        if (depth > 0) {
            for (String s : transaction.getTxIn()) {
                System.out.println(s);
                Transaction inTx = newTransaction(findTransaction(s));
                transactions.add(inTx);
                if (!inTx.isCoinBase()) {
                    findInTransactions(inTx, depth - 1);
                }
            }
        }
    }

    private JSONObject findTransaction(String id){
        try {
            URL url = new URL(URL + API_TX + id);
            URLConnection connection = url.openConnection();
            InputStream input = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(input);

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;

            String toJson = "";
            while ((line = reader.readLine()) != null) {
                toJson = toJson + line + "\n";
            }

            return new JSONObject(toJson);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Transaction newTransaction(JSONObject json){
        boolean isCoinbase = false;
        String id = json.getString("txid");
        JSONArray a = json.getJSONArray("vout");
        List<String> out = new ArrayList<>();
        List<String> in = new ArrayList<>();
        for (int i = 0; i < a.length(); i++){
            out.add(a.getJSONObject(i).getJSONObject("scriptPubKey").get("addresses").toString());
        }
        a = json.getJSONArray("vin");
        List<String> txIn = new ArrayList<>();
        for (int i = 0; i < a.length(); i++) {
            if (a.getJSONObject(i).has("coinbase")) {
                System.out.println("COINBASE");
                isCoinbase = true;
            } else {
                in.add(a.getJSONObject(i).get("addr").toString());
                txIn.add(a.getJSONObject(i).get("txid").toString());
            }
        }
        double valueOut = json.getDouble("valueOut");
        double valueIn = json.optDouble("valueIn", 0);
        Date time = Date.from(Instant.ofEpochSecond((Integer) json.get("time")));

        double fees = json.optDouble("fees", 0);
        Transaction transaction = new Transaction(id, valueIn, valueOut, time, in, out, fees);
        transaction.setTxIn(txIn);
        transaction.setCoinBase(isCoinbase);

        return transaction;
    }

    @FXML
    public void graph(ActionEvent event) {
        Graph graph;
        Button b = (Button) event.getSource();
        try {
            if (display == null) {
                tableView.setVisible(false);
                b.setText("Switch to table");

//                graph = new GraphMLReader().readGraph("sample/data/graph.xml");
                graph = generateGraph();
                Visualization vis = new Visualization();

                vis.add(GROUP, graph);

                Predicate addressPred = ExpressionParser.predicate("type=1");
                Predicate transactionPred = ExpressionParser.predicate("type=2");

                ShapeRenderer address = new ShapeRenderer();
                address.setFillMode(ShapeRenderer.SOLID);

                LabelRenderer labelRenderer = new LabelRenderer("id");
                labelRenderer.translate(5,5);

                CombinedRenderer combinedRenderer = new CombinedRenderer();
                combinedRenderer.add(address);
//                combinedRenderer.add(labelRenderer);

                CombinedRenderer edgeCombinedRenderer = new CombinedRenderer();

                DirectedEdgeRenderer edgeRenderer = new DirectedEdgeRenderer();
                edgeCombinedRenderer.add(edgeRenderer);

                EdgeLabelRenderer edgeLabelRenderer = new EdgeLabelRenderer(TRG);
                edgeCombinedRenderer.add(edgeLabelRenderer);

                DefaultRendererFactory rfa = new DefaultRendererFactory(combinedRenderer, edgeRenderer);
//                rfa.add(addressPred, address);
//                rfa.add(transactionPred, address);

                vis.setRendererFactory(rfa);

                ActionList layout = new ActionList(Activity.INFINITY,30);
                layout.add(new ForceDirectedLayout("graph"));
                layout.add(new RepaintAction());
                vis.putAction("layout", layout);

                ActionList nodeActions = new ActionList();
                final String NODES = PrefuseLib.getGroupName(GROUP, Graph.NODES);
                final String EDGES = PrefuseLib.getGroupName(GROUP, Graph.EDGES);
                NodeDegreeSizeAction size = new NodeDegreeSizeAction(NODES);
                size.setMaximumSize(3);
                size.setMinimumSize(3);
                nodeActions.add(size);

//                int[] addressPalette = new int[] { ColorLib.rgb(255, 255, 0)};
//                int[] transactionPalette = new int[] { ColorLib.rgb(255, 0, 255)};

//                DataColorAction addressColor = new DataColorAction(NODES,addressPred,"type", Constants.NUMERICAL, VisualItem.FILLCOLOR, addressPalette);
//                DataColorAction transactionColor = new DataColorAction(NODES,transactionPred,"type", Constants.NUMERICAL, VisualItem.FILLCOLOR, transactionPalette);
//                nodeActions.add(addressColor);
//                nodeActions.add(transactionColor);
//                vis.putAction("nodes", nodeActions);

                display = new FxDisplay(vis);
                display.resize(300,300);
                display.addControlListener(new ClickControl(display));
                vis.run("nodes");
                vis.run("edges");
                vis.run("layout");

                grid.add(display, 1,1);
                grid.add(buildControlPanel(display), 0, 1);

            } else {
                display.setVisible(false);
                display = null;
                tableView.setVisible(true);
                b.setText("Switch to graph");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading graph. Exiting...");
            System.exit(1);
        }
    }


    private javafx.scene.Node buildControlPanel(FxDisplay display) {
        VBox vbox = new VBox();
        Label txt = new Label("Zoom Factor");
        Slider slider = new Slider(0.0, 10.0, 1.0);
        Label txt2 = new Label("");
        display.zoomFactorProperty().bind(slider.valueProperty());
        vbox.getChildren().addAll(txt, slider, txt2);
        return vbox;

    }
}
