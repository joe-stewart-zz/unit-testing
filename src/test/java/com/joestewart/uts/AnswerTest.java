package com.joestewart.uts;

import static org.junit.Assert.*;
import org.junit.*;

public class AnswerTest {
    @Test
    public void matchAgainstNullAnswerReturnsFalse() {
        assertFalse(new Answer(new BooleanQuestion(0, ""), Bool.TRUE).match(null));
    }
}
