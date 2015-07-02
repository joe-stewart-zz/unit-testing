package com.joestewart.uts;

import static org.junit.Assert.*;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;

public class MatchSetTest {
    private Criteria criteria;
    private AnswerCollection answers;

    private Question questionReimbursesTuition;
    private Answer answerReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;

    private Question questionIsThereRelocation;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNoRelocation;

    private Question questionOnsiteDaycare;
    private Answer answerNoOnsiteDaycare;
    private Answer answerHasOnsiteDaycare;

    @Before
    public void createAnswers() {
        answers = new AnswerCollection();
    }

    @Before
    public void createCriteria() {
        criteria = new Criteria();
    }

    @Before
    public void createQuestionsAndAnswers() {
        questionReimbursesTuition = new BooleanQuestion(1, "Reimburses Tuition?");
        answerReimbursesTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
        answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);

        questionIsThereRelocation = new BooleanQuestion(1, "Relocation Package?");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNoRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);

        questionOnsiteDaycare = new BooleanQuestion(1, "Onsite Daycare?");
        answerHasOnsiteDaycare = new Answer(questionOnsiteDaycare, Bool.TRUE);
        answerNoOnsiteDaycare = new Answer(questionOnsiteDaycare, Bool.FALSE);
    }

    private MatchSet createMatchSet() {
        return new MatchSet(answers, criteria);
    }

    @Test
    public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
        answers.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.MustMatch));

        assertFalse(createMatchSet().matches());
    }
    @Test
    public void matchAnswersTrueForAnyDontCareCriteria() {
        answers.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.DontCare));

        assertTrue(createMatchSet().matches());
    }
    @Test
    public void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        answers.add(answerThereIsRelocation);
        answers.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

        assertTrue(createMatchSet().matches());
    }
    @Test
    public void matchAnswersFalseWhenNoneOfMultipleCriteriaMatch() {
        answers.add(answerThereIsNoRelocation);
        answers.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

        assertFalse(createMatchSet().matches());
    }
    @Test
    public void scoreIsZeroWhenThereAreNoMatches() {
        answers.add(answerThereIsNoRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        assertThat(createMatchSet().getScore(), equalTo(0));
    }
    @Test
    public void scoreIsCriterionValueForSingleMatch() {
        answers.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        assertThat(createMatchSet().getScore(), equalTo(Weight.Important.getValue()));
    }
    @Test
    public void scoreAccumulatesCriterionValuesForMatches() {
        answers.add(answerThereIsRelocation);
        answers.add(answerReimbursesTuition);
        answers.add(answerNoOnsiteDaycare);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.WouldPrefer));
        criteria.add(new Criterion(answerHasOnsiteDaycare, Weight.VeryImportant));

        int expectedScore = Weight.Important.getValue() + Weight.WouldPrefer.getValue();
        assertThat(createMatchSet().getScore(), equalTo(expectedScore));
    }
}
