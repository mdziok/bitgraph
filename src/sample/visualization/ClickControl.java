package sample.visualization;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prefux.FxDisplay;
import prefux.controls.ControlAdapter;
import prefux.controls.DragControl;
import prefux.data.Table;
import prefux.data.event.TableListener;
import prefux.util.FxGraphicsLib;
import prefux.visual.NodeItem;
import prefux.visual.VisualItem;

public class ClickControl extends ControlAdapter implements TableListener {

    private VisualItem activeItem;
    protected String action;
    private FxDisplay display;

    private Label label;

    private static final Logger log = LogManager.getLogger(DragControl.class);

    public ClickControl(){}

    public ClickControl(String action){this.action = action;}

    public ClickControl(FxDisplay display){
        this.display = display;
    }

    @Override
    public void tableChanged(Table t, int start, int end, int col, int type) {

    }

    @Override
    public void itemEvent(VisualItem item, Event e) {
        MouseEvent ev = (MouseEvent) e;
        activeItem = item;

        if (ev.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (activeItem.isInGroup("graph.nodes")) {
                System.out.println("kliknieto! " + activeItem.getString("id"));

                NodeItem node = (NodeItem) item;

                label = new Label("Asdasda");

                Platform.runLater(() -> {
                    label.layoutXProperty().bind(node.xProperty());
                    label.layoutYProperty().bind(node.yProperty());
                });

                FxGraphicsLib.addToParent(display, label);

            }
        }
        if (ev.getEventType() == MouseEvent.MOUSE_ENTERED){
            if (activeItem.isInGroup("graph.edges")) {
                System.out.println("entered! ");
            }

            label.setVisible(false);

        }
//
//        if (ev.getEventType() == MouseEvent.MOUSE_EXITED){
//            if (activeItem.isInGroup("graph.nodes")) {
//                System.out.println("exited! " + activeItem.getString("name"));
//            }
//        }

    }

    public HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button buttonCurrent = new Button("Current");
        buttonCurrent.setPrefSize(100, 20);

        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);
        hbox.getChildren().addAll(buttonCurrent, buttonProjected);

        return hbox;
    }

    @Override
    public EventType<? extends Event> getEventType() {
        return MouseEvent.ANY;
    }
}
