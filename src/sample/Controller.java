package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import org.json.JSONArray;
import org.json.JSONObject;

import prefux.FxDisplay;
import prefux.Visualization;
import prefux.action.ActionList;
import prefux.action.RepaintAction;
import prefux.action.assignment.NodeDegreeSizeAction;
import prefux.action.layout.*;
import prefux.action.layout.graph.*;
import prefux.activity.Activity;
import prefux.controls.DragControl;
import prefux.data.Graph;
import prefux.data.io.DataIOException;
import prefux.data.io.GraphMLReader;
import prefux.render.*;
import prefux.util.PrefuseLib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {

    private static final String URL = "https://blockexplorer.com/";

    @FXML private TableView<Transaction> tableView;
    @FXML private ImageView image;
    @FXML private GridPane grid;

    private FxDisplay display;


    @FXML
    protected void switchView(ActionEvent event){
        if(image.isVisible()){
            image.setVisible(false);
            tableView.setVisible(true);
        }
        else {
            tableView.setVisible(false);
            image.setVisible(true);
        }

    }

    @FXML
    protected void addTransaction(ActionEvent event) {
        ObservableList<Transaction> data = tableView.getItems();
        System.out.println("kliknieto!");
        try {
            URL url = new URL(URL + "api/tx/b3e7bb1628094ccda32deb594d57511289d2a0228fc097179e7cec550217136c");
            URLConnection connection = url.openConnection();
            InputStream input = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(input);

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            String toJson = "";
            while ((line = reader.readLine()) != null) {
                toJson = toJson + line + "\n";
            }

            JSONObject json = new JSONObject(toJson);
            String id = json.getString("txid");
            double valueOut = json.getDouble("valueOut");
            double valueIn = json.getDouble("valueIn");
            Date time = Date.from(Instant.ofEpochSecond((Integer) json.get("time")));
            List<String> out = new ArrayList<>();
            List<String> in = new ArrayList<>();

            JSONArray a = json.getJSONArray("vout");
            for (int i = 0; i < a.length(); i++){
                out.add(a.getJSONObject(i).getJSONObject("scriptPubKey").get("addresses").toString());
            }
            a = json.getJSONArray("vin");
            for (int i = 0; i < a.length(); i++){
                in.add(a.getJSONObject(i).get("addr").toString());
            }
            double fees = json.getDouble("fees");
            data.add(new Transaction(id, valueOut, valueIn, time, in, out, fees));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private static final String GROUP = "graph";

    @FXML
    public void graph(ActionEvent event) {
        Graph graph = null;
        Button b = (Button) event.getSource();
        try {
            if (display == null) {
                tableView.setVisible(false);
                b.setText("Switch to table");
                graph = new GraphMLReader().readGraph("sample/data/graph.xml");
                Visualization vis = new Visualization();
                vis.add(GROUP, graph);

                LabelRenderer lr = new LabelRenderer("address");
                CombinedRenderer r = new CombinedRenderer();
                r.add(lr);
                RendererFactory rfa = new DefaultRendererFactory(r);
                vis.setRendererFactory(rfa);

                ActionList layout = new ActionList(Activity.INFINITY,30);
                layout.add(new CircleLayout("graph", 100));
                layout.add(new RepaintAction());
                vis.putAction("layout", layout);

                ActionList nodeActions = new ActionList();
                final String NODES = PrefuseLib.getGroupName(GROUP, Graph.NODES);
                NodeDegreeSizeAction size = new NodeDegreeSizeAction(NODES);
                nodeActions.add(size);
                vis.putAction("nodes", nodeActions);

                display = new FxDisplay(vis);
                display.addControlListener(new DragControl());
                vis.run("nodes");
                vis.run("layout");

                grid.add(display, 1,1);

            } else {
                display.setVisible(false);
                display = null;
                tableView.setVisible(true);
                b.setText("Switch to graph");
            }

        } catch (DataIOException e) {
            e.printStackTrace();
            System.err.println("Error loading graph. Exiting...");
            System.exit(1);
        }

    }


}
