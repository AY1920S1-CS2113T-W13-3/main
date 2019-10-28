package duke.model.profile;

import duke.commons.exceptions.DukeDateTimeParseException;
import duke.logic.parsers.ParserTimeUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileCardTest {
    private ProfileCard profileCard = new ProfileCard();

    @Test
    void execute() throws DukeDateTimeParseException {
        LocalDateTime birthday = ParserTimeUtil.parseStringToDate("01/01/01");

        profileCard.setPerson("Danny", birthday);
        assertEquals(profileCard.getPersonName(), "Danny");
        assertEquals(profileCard.getPersonBirthday(), birthday);
    }
}
