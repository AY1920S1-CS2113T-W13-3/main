package duke.logic.selectors;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EventFieldSelector implements Selector {
    private static final int FIELD_SIZE = 3;
    private int index = 0;

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void feedKeyCode(KeyCode keyCode) {
        if (keyCode.equals(KeyCode.UP)) {
            index++;
        } else if (keyCode.equals(KeyCode.DOWN)) {
            index--;
            index += FIELD_SIZE;
        }
        index %= FIELD_SIZE;
    }
}
