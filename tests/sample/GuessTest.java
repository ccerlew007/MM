package sample;

import javafx.scene.paint.Color;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class GuessTest {
    @Test
    public void fill() throws Exception {

        int size = 4;
        Guess guess = new Guess(size);
        guess.fill(true);
        boolean isGood = true;
        for (int i = 0; i < size; i++) {
            if (!(guess.getColors()[i] instanceof Color)) {
                if(!(guess.printColor(guess.getColors()[i]).equals("UNSPECIFIED"))) {
                    isGood = false;
                }
            }
           // System.out.println(printColor(guess.getColors()[i]) );
        }
        assertTrue(isGood);

        System.out.println();

        guess = new Guess(size);
        guess.fill(false);
        isGood = true;
        for (int i = 0; i < size; i++) {
            if (!(guess.getColors()[i] instanceof Color)) {
                if(!(guess.printColor(guess.getColors()[i]).equals("UNSPECIFIED"))) {
                    isGood = false;
                }
            }
           // System.out.println(printColor(guess.getColors()[i]) );
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                if (guess.getColors()[i] == guess.getColors()[j] && i != j) {
                    isGood = false;
                }
        }

        assertTrue(isGood);

    }

}