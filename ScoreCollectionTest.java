import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.*;

public class ScoreCollectionTest {
    private ScoreCollection collection;
    @Before
    public void setup() {
        collection = new ScoreCollection();
    }
    @Test
    public void arithmeticMeanOfTwoNumbers() {
        collection.add(() -> 5);
        collection.add(() -> 7);

        int actual = collection.arithmeticMean();

        assertThat(actual, equalTo(6));
    }
    @Test(expected=IllegalArgumentException.class)
    public void throwExceptionWhenAddingNull() {
        collection.add(null);
    }
    @Test
    public void answersZeroWhenNoElementsAdded() {
        assertThat(collection.arithmeticMean(), equalTo(0));
    }
    @Test
    public void doesNotProperlyHandleIntegerOverflow() {
        collection.add(() -> Integer.MAX_VALUE);
        collection.add(() -> 1);

        assertTrue(collection.arithmeticMean() < 0);
    }
}
