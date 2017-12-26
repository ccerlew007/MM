package sample;

import javafx.scene.paint.Color;
import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * Created by Krystian on 20/12/2017.
 */
public class CheckerTest {
    @Test
    public void check() throws Exception {

        Color color1[] = new Color[] {Color.TURQUOISE, Color.RED, Color. ORANGE, Color.BLUE};
        Guess guess1 = new Guess(color1);
        Color color2[] = new Color[] {Color.TURQUOISE, Color.RED, Color. BLUE, Color.ORANGE};
        Guess guess2 = new Guess(color2);

        Answer rightAnswer = new Answer();
        rightAnswer.setBlackDots(2);
        rightAnswer.setWhiteDots(2);

        Answer checkerAnswer = Checker.check(guess1,guess2);
        assertEquals(rightAnswer, checkerAnswer);

    }

}