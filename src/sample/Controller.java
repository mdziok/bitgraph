package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.ResourceBundle;

public class Controller {

    @FXML private TableColumn<ObservableList<String>, String> address;
    @FXML private TableView<Transaction> tableView;


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

}
