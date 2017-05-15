package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.json.JSONObject;
import prefux.FxDisplay;
import prefux.Visualization;
import prefux.action.ActionList;
import prefux.action.RepaintAction;
import prefux.action.assignment.NodeDegreeSizeAction;
import prefux.action.layout.graph.ForceDirectedLayout;
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

public class Controller {

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
            URL url = new URL("https://blockchain.info/pl/latestblock");
            URLConnection connection = url.openConnection();
            InputStream input = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(input);

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            String json = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                json = json + line + "\n";
            }

            JSONObject object = new JSONObject(json);
            String time = Instant.ofEpochSecond((Integer) object.get("time")).toString();
            System.out.println(String.valueOf(object.get("time")));
            System.out.println(Instant.ofEpochSecond((Integer) object.get("time")).toString());

            data.add(new Transaction("", "", time));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private static final String GROUP = "graph";

    @FXML
    public void graph() {
        Graph graph = null;
        try {
            if (display == null) {
                tableView.setVisible(false);
                graph = new GraphMLReader().readGraph("sample/data/socialnet.xml");
                Visualization vis = new Visualization();
                vis.add(GROUP, graph);

                ShapeRenderer female = new ShapeRenderer();
                female.setFillMode(ShapeRenderer.GRADIENT_SPHERE);
                LabelRenderer lr = new LabelRenderer("name");
                ShapeRenderer male = new ShapeRenderer();
                male.setFillMode(ShapeRenderer.GRADIENT_SPHERE);
                CombinedRenderer r = new CombinedRenderer();
                r.add(lr);
                RendererFactory rfa = new DefaultRendererFactory(r);
                vis.setRendererFactory(rfa);

                ActionList layout = new ActionList(Activity.INFINITY,30);
                layout.add(new ForceDirectedLayout("graph"));
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
            }

        } catch (DataIOException e) {
            e.printStackTrace();
            System.err.println("Error loading graph. Exiting...");
            System.exit(1);
        }

    }


}
