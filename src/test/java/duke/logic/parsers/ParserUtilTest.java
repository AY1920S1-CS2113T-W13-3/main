package duke.logic.parsers;

import duke.commons.exceptions.InputNotIntException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.model.planning.Todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserUtilTest {

    @Test
    void createTodo() throws Exception {
        assertTrue(ParserUtil.createTodo("todo Homework") instanceof Todo);
    }

    @Test
    void getFieldInList() throws Exception {
        String inputString = "done 1";
        String expected = "1";

        assertEquals(expected, ParserUtil.getFieldInList(0, 1, inputString.strip().split(" ", 2)[1]));

        expected = "done";
        assertEquals(expected, ParserUtil.getFieldInList(0, 2, inputString));

        expected = "1";
        assertEquals(expected, ParserUtil.getFieldInList(1, 2, inputString));
    }

    @Test
    void getIndexInList() throws Exception {
        String inputString = "done 1";
        int expected = 1;
        assertEquals(expected, ParserUtil.getIntegerInList(0, 1, inputString.strip().split(" ", 2)[1]));

        //test for zero
        inputString = "done 0";
        expected = 0;
        assertEquals(expected, ParserUtil.getIntegerInList(0, 1, inputString.strip().split(" ", 2)[1]));

        //test for negative integers
        inputString = "done -1";
        expected = -1;
        assertEquals(expected, ParserUtil.getIntegerInList(0, 1, inputString.strip().split(" ", 2)[1]));

        //test for empty input
        assertThrows(InputNotIntException.class, ()-> {
            ParserUtil.getIntegerInList(0, 1, "");
        });

        //negative test, input is not integer
        String inputString3 = "done NotInt";
        assertThrows(InputNotIntException.class, ()-> {
            ParserUtil.getIntegerInList(0, 1, inputString3.strip().split(" ", 2)[1]);
        });
    }

    @Test
    void getIntegerIndexInList() throws Exception {
        String inputString = "done 1";
        int expected = 0;

        assertEquals(expected, ParserUtil.getIntegerIndexInList(0, 1, inputString.strip().split(" ", 2)[1]));

        //test for zero
        inputString = "done 0";
        expected = -1;
        assertEquals(expected, ParserUtil.getIntegerIndexInList(0, 1, inputString.strip().split(" ", 2)[1]));

        //test for negative integers
        inputString = "done -1";
        expected = -2;
        assertEquals(expected, ParserUtil.getIntegerIndexInList(0, 1, inputString.strip().split(" ", 2)[1]));

        //test for empty input
        assertThrows(InputNotIntException.class, ()-> {
            ParserUtil.getIntegerIndexInList(0, 1, "");
        });

        //negative test, input is not integer
        String inputString3 = "done NotInt";
        assertThrows(InputNotIntException.class, ()-> {
            ParserUtil.getIntegerIndexInList(0, 1, inputString3.strip().split(" ", 2)[1]);
        });

        //negative test, test for out of bounds
        String finalInputString1 = "done 1";
        assertThrows(QueryOutOfBoundsException.class, ()-> {
            ParserUtil.getIntegerIndexInList(-1, 1, finalInputString1.strip().split(" ", 2)[1]);
        });

        //negative test, test for out of bounds
        String finalInputString2 = "done 1";
        assertThrows(QueryOutOfBoundsException.class, ()-> {
            ParserUtil.getIntegerIndexInList(2, 1, finalInputString2.strip().split(" ", 2)[1]);
        });
    }


}
