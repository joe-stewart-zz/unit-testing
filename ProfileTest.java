import static org.junit.Assert.*;
import org.junit.*;

public class ProfileTest {
    private Profile profile;
    private BooleanQuestion questionIsThereRelocation;
    private Answer answerThereIsRelocation;

    @Before
    public void createProfile() {
        profile = new Profile();
    }
    @Before
    public void createQuestionAndAnswer() {
        questionIsThereRelocation = new BooleanQuestion(1, "Relocation package?");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
    }
    @Test
    public void matchesNothingWhenProfileEmpty() {
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.DontCare);

        boolean result = profile.matches(criterion);

        assertFalse(result);
    }
    @Test
    public void matchesWhenProfileContainsMatchingAnswer() {
        Answer answer = new Answer(questionIsThereRelocation, Bool.TRUE);
        profile.add(answer);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

        boolean result = profile.matches(criterion);

        assertTrue(result);
    }
}
