package duke.ui;

import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.commands.results.PanelResult;
import duke.ui.map.PointCard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the side panel of the main window.
 */
public class SidePanel extends UiPart<AnchorPane> {
    @FXML
    private StackPane taskContainer;
    @FXML
    private AnchorPane miniMap;
    @FXML
    private Label description;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String FXML = "SidePanel.fxml";

    private SidePanel(PanelResult result) {
        super(FXML);
        if (result.isReady()) {
            for (int i = 0; i < result.size(); ++i) {
                miniMap.getChildren().add(
                        PointCard.getCard(result.getVenue(i), result.getVenueColor(i)));
            }
            description.setText(result.getDescription());
            description.setVisible(true);
            startDate.setText(result.getStartDate());
            startDate.setVisible(true);
            endDate.setText(result.getEndDate());
            endDate.setVisible(true);
            setHighlight(result);
        }
    }

    private void setHighlight(PanelResult result) {
        try {
            switch (result.getField()) {
            case 0:
                description.getStyleClass().add("highlight");
                break;
            case 1:
                startDate.getStyleClass().add("highlight");
                break;
            case 2:
                endDate.getStyleClass().add("highlight");
                break;
            default:
            }
        } catch (QueryOutOfBoundsException e) {
            logger.log(Level.FINE, "Nothing needs to be highlighted.");
        }
    }

    public static AnchorPane getPanel(PanelResult result) {
        return new SidePanel(result).getRoot();
    }
}
