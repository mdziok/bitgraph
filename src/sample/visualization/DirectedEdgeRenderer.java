package sample.visualization;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import prefux.data.util.Point2D;
import prefux.render.AbstractShapeRenderer;
import prefux.render.Renderer;
import prefux.visual.EdgeItem;
import prefux.visual.VisualItem;

public class DirectedEdgeRenderer  extends AbstractShapeRenderer implements Renderer {

    public static final String DEFAULT_STYLE_CLASS = "prefux-edge";

    @Override
    public boolean locatePoint(Point2D p, VisualItem item) {
        return false;
    }

    @Override
    protected Node getRawShape(VisualItem item, boolean bind) {
        EdgeItem edge = (EdgeItem) item;
        Arrow arrow = new Arrow();
        if (bind) {
            Platform.runLater(() -> {
                arrow.startXProperty().bind(edge.getSourceItem().xProperty());
                arrow.startYProperty().bind(edge.getSourceItem().yProperty());
                arrow.endXProperty().bind(edge.getTargetItem().xProperty());
                arrow.endYProperty().bind(edge.getTargetItem().yProperty());
            });
        }
        return arrow;
    }

    @Override
    public String getDefaultStyle() {
        return DEFAULT_STYLE_CLASS;
    }

}
