package com.joestewart.uts;

public class BooleanQuestion extends Question {
    public BooleanQuestion(int id, String text) {
        super(id, text, new String[] {"No", "Yes"});
    }
    public boolean match(int expected, int actual) {
        return expected == actual;
    }
}
