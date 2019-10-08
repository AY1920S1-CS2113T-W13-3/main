package duke;

import duke.commands.Command;
import duke.commands.ExitCommand;
import duke.commons.DukeException;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The Duke program is a simple Personal Assistant Chatbot
 * that helps a person to keep track of various things.
 *
 * @author  Jefferson111
 */
public class Duke {
    private static  final String FILE_PATH = "data/tasks.txt";
    private Main main;
    private Ui ui;
    private Storage storage;
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private static final int COMMAND_TIMEOUT_PERIOD = 1000;

    /**
     * Creates Duke instance.
     */
    public Duke(Main main, Ui ui) throws InterruptedException, ExecutionException, TimeoutException, DukeException {
        this.ui = ui;
        this.main = main;
        ui.showWelcome();
        storage = new Storage(FILE_PATH, ui);
    }

    /**
     * Get response from parser.
     * @param userInput The user input
     */
    public Future<Command> getResponse(String userInput) {
        Future<Command> future = executor.submit(() -> {
            Command c = Parser.parse(userInput);
            return c;
        });

        return future;
    }

    /**
     * Try to shut down Duke.
     */
    public void tryExitApp() {
        try {
            main.stop();
        } catch (Exception e) {
            ui.showError("Exit app failed" + e.getMessage());
        }
    }
}
