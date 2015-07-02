package com.joestewart.uts;

import java.util.*;

public class Profile {
    private Map<String, Answer> answers = new HashMap<>();
    private Answer answer;

    private Answer getMatchingProfileAnswer(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }
    public boolean matches(Criterion criterion) {
        return criterion.getWeight() == Weight.DontCare || criterion.getAnswer().match(getMatchingProfileAnswer(criterion));
    }
    public boolean matches(Criteria criteria) {
        boolean matches = false;
        for(Criterion criterion : criteria) {
            if(matches(criterion))
                matches = true;
            else if(criterion.getWeight() == Weight.MustMatch)
                return false;
        }
        return matches;
    }
    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }
    public ProfileMatch match(Criteria criteria) {
        return new ProfileMatch();
    }
}
