package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;

import org.json.*;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("BitGraph");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();


//        TableView<Transaction> tableView = (TableView) root.lookup("#tableview");
//        ObservableList<Transaction> data = tableView.getItems();
//
//        Button button = (Button) root.lookup("#button");
//
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("kliknieto!");
//                try {
//                    URL url = new URL("https://blockchain.info/pl/latestblock");
//                    URLConnection connection = url.openConnection();
//                    InputStream input = connection.getInputStream();
//                    InputStreamReader inputStreamReader = new InputStreamReader(input);
//
//                    BufferedReader reader = new BufferedReader(inputStreamReader);
//                    String line;
//                    String json = "";
//                    while ((line = reader.readLine()) != null){
//                        System.out.println(line);
//                        json = json + line + "\n";
//                    }
//
//                    JSONObject object = new JSONObject(json);
//                    String time = Instant.ofEpochSecond((Integer)object.get("time")).toString();
//                    System.out.println(String.valueOf(object.get("time")));
//                    System.out.println(Instant.ofEpochSecond((Integer)object.get("time")).toString());
//
//                    data.add(new Transaction("", "", time));
//                    //                    Map<String, String> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, String>>(){}.getType());
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                System.out.println();
//            }
//        });




    }


    public static void main(String[] args) {
        launch(args);
    }
}
