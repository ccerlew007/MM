package sample;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GameManagerTest {
    @Test
    public void makeVariation() throws Exception {
        GameManager gameManager = new GameManager(4);
        List<Guess> variations = new ArrayList<>();
        Color[] defaultColors = {Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE};
        Guess temp = new Guess(defaultColors.clone());
        gameManager.makeVariation(variations,temp ,0);

        for(int i = 0; i < variations.size();i++) {
            System.out.println(variations.get(i));
        }
    }

}