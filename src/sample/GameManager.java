package sample;


import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private Guess secretGuess;
    private int guessSize;
    private int tries;
    Guess currentGuess = null;
    Color possibleColors[];
    Color startColors[];
    private List<Guess> variations;
    private List<Guess> toStrikeout;


    public GameManager(int guessSize) {
        this.guessSize = guessSize;
        possibleColors = new Color[] {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW,
                Color.BROWN, Color.PINK, Color.TURQUOISE, Color.ORANGE};
        startColors = new Color[]{Color.BLUE, Color.BLUE, Color.RED, Color.RED};
        reset();
    }

    private void reset() {
        secretGuess = new Guess(guessSize);
        secretGuess.fill(false);
        tries = 0;

    }

    public void shuffle(boolean allowDuplicates) {
        secretGuess.fill(allowDuplicates);

    }

    public Answer getAnswer(Guess guess) {
        return Checker.check(guess, secretGuess);

    }

    //temporary, for debugging
    public Guess getSecretGuess() {
        return secretGuess;
    }

    public int getTries() {
        return tries;
    }

    public void startAlgorithm() {

        variations = new ArrayList<>();
        toStrikeout = new ArrayList<>();

        Color[] defaultColors = {Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE};

        Guess temp = new Guess(defaultColors.clone());
        makeVariation(variations, temp,0);

        temp = new Guess(defaultColors.clone());
        makeVariation(toStrikeout,temp,0);

        Color[] startColors = {Color.BLUE, Color.BLUE, Color.RED, Color.RED};
        Guess guess = new Guess(startColors);

    }

    public Guess getGuess (Answer answer) {
        Answer strikeoutAnswer;
        if(answer == null) {
            currentGuess = new Guess(startColors.clone());
        } else {
            for( Guess element: toStrikeout) {
                strikeoutAnswer = Checker.check(currentGuess, element);
                if(!strikeoutAnswer.equals(answer)) {
                    element.strikeout();
                }
            }
            HittingManager hittingManager = new HittingManager(variations,toStrikeout);
            currentGuess = hittingManager.nextGuess();
        }
        return currentGuess;
    }

    public void makeVariation (List<Guess> variations, Guess guess, int currentSlot) {

        if(currentSlot >= guessSize) {
            variations.add(new Guess(guess));
        } else {
            for(int i = 1; i < possibleColors.length; i++) {
                guess.set ( currentSlot, possibleColors[i] ) ;
                makeVariation(variations, guess, currentSlot + 1);
            }
        }
    }



}
