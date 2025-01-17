package sgtravel.ui.dialogbox;

import sgtravel.ui.UiPart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Collections;

/**
 * Dialog Box containing image.
 */
public class DialogBoxImage extends UiPart<HBox> {
    private static final String FXML = "DialogBoxImage.fxml";

    @FXML
    private Label dialog;
    @FXML
    private HBox dialogBoxImage;
    @FXML
    private Circle circle;
    @FXML
    private HBox miniBox;
    @FXML
    private ImageView imageBox;

    /**
     * Constructs the DialogBoxImage object.
     *
     * @param text The text to display.
     * @param user The picture of the user.
     * @param picture The picture to display.
     */
    private DialogBoxImage(String text, Image user, Image picture) {
        super(FXML);
        dialog.setText(text);
        dialog.setMinHeight(Label.USE_PREF_SIZE);
        roundImageView(user);
        setPictureView(picture);
    }

    /**
     * Rounds an image.
     *
     * @param image The image to round.
     */
    private void roundImageView(Image image) {
        ImagePattern pattern = new ImagePattern(image);
        circle.setFill(pattern);
    }

    /**
     * Sets the image to display in the imageBox.
     *
     * @param image The image to display
     */
    private void setPictureView(Image image) {
        if (image != null) {
            imageBox.setImage(image);
        }  else {
            imageBox.setFitHeight(0);
            dialogBoxImage.setMaxHeight(80);
        }
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        miniBox.getStyleClass().remove("dialog");
        miniBox.getStyleClass().add("dialogFlip");
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getRoot().getChildren());
        flipNode(tmp, this.getRoot());
        ObservableList<Node> tmp2 = FXCollections.observableArrayList(miniBox.getChildren());
        flipNode(tmp2, miniBox);
    }

    /**
     * Flips the node.
     *
     * @param tmp The ObservableList of Nodes.
     * @param root The HBox to interact with.
     */
    private void flipNode(ObservableList<Node> tmp, HBox root) {
        Collections.reverse(tmp);
        root.getChildren().setAll(tmp);
        root.setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Gets a DialogBoxImage for display.
     *
     * @param text The text to display.
     * @param user The picture of the user.
     * @param picture The picture to display.
     * @return The DialogBoxImage created.
     */
    public static HBox getDialog(String text, Image user, Image picture) {
        DialogBoxImage db = new DialogBoxImage(text, user, picture);
        db.flip();
        return db.getRoot();
    }
}
