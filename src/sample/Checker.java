package sample;

import javafx.scene.paint.Color;

public class Checker {

    private final static Color neutralColor = Color.SILVER;

    public static Answer check(Guess guess, Guess code) {

        Guess guessTemp = new Guess(guess), codeTemp = new Guess(code);
        int blackDots = 0, whiteDots = 0;

        for(int i = 0; i < guessTemp.getSize(); i++) {
            if(guessTemp.get(i) == codeTemp.get(i)){
                blackDots++;
                guessTemp.set(i, neutralColor);
                codeTemp.set(i,neutralColor);
            }
        }

        for(int i = 0; i < guessTemp.getSize(); i++) {
            if(guessTemp.get(i) != neutralColor){
                for(int j = 0; j < codeTemp.getSize(); j++) {
                    if(guessTemp.get(i) == codeTemp.get(j)) {
                        whiteDots++;
                        codeTemp.set(j, neutralColor);
                    }
                }
            }
        }

        return new Answer(whiteDots, blackDots);

    }

}
