import java.util.*;

public class Profile {
    private Map<String, Answer> answers = new HashMap<>();
    private Answer answer;

    private Answer getMatchingProfileAnswer(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }
    public boolean matches(Criterion criterion) {
        Answer answer = getMatchingProfileAnswer(criterion);
        return criterion.getAnswer().match(answer);
    }
    public boolean matches(Criteria criteria) {
        for(Criterion criterion : criteria)
            if(matches(criterion))
                return true;
        return false;
    }
    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }
}
