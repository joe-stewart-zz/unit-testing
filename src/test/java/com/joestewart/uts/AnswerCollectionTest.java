package com.joestewart.uts;

import static org.junit.Assert.*;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import java.util.*;

public class AnswerCollectionTest {
    private AnswerCollection answers;

    @Before
    public void createProfile() {
        answers = new AnswerCollection();
    }

    int[] ids(Collection<Answer> answers) {
        return answers.stream().mapToInt(a -> a.getQuestion().getId()).toArray();
    }

    @Test
    public void findAnswersBasedOnPredicate() {
        answers.add(new Answer(new BooleanQuestion(1, "1"), Bool.FALSE));
        answers.add(new Answer(new PercentileQuestion(2, "2", new String[]{}), 0));
        answers.add(new Answer(new PercentileQuestion(3, "3", new String[]{}), 0));

        List<Answer> result = answers.find(a -> a.getQuestion().getClass() == PercentileQuestion.class);

        assertThat(ids(result), equalTo(new int[] {2, 3}));
    }
}
