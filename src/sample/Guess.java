package sample;

import javafx.scene.paint.Color;

import java.util.Random;


public class Guess {

    private int size;
    private Color[] colors;
    private boolean strikeout = false;
    private Color[] possibleColors;

    public Guess() {
        this(4);
    }

    public Guess(int size) {
        this.size = size;
        this.colors = new Color[size];
        possibleColors = new Color[] {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW,
                                Color.BROWN, Color.PINK, Color.TURQUOISE, Color.ORANGE};
    }

    public Guess(Color[] colors) {
        this(colors.length);
        this.colors = colors;
    }

    public Guess(Guess guess) {
        this(guess.getSize());
        this.colors = guess.getColors();

    }

    public void strikeout() {
        strikeout = true;
    }



    public void set(int index, Color color) {
        colors[index] = color;
    }

    public Color get(int index) {
        return colors[index];
    }

    public Color[] getColors() {
        return colors.clone();
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isStrikeout() {
        return strikeout;
    }

    public void fill( boolean allowDuplicates) {
        if(allowDuplicates){
            fillWithDuplicates();
        } else {
            fillWithoutDuplicates();
        }
    }

    private void fillWithoutDuplicates() {
        Random random = new Random();
        boolean ifDrawed[] = new boolean[possibleColors.length];
        for (int i = 0; i < possibleColors.length; i++) {
            ifDrawed[i] = false;
        }

        int drawed;
        for (int i = 0; i < size; i++) {
            boolean recap;
            do {
                drawed = random.nextInt(possibleColors.length);
                recap = !ifDrawed[drawed] ? false : true;
                ifDrawed[drawed] = true;

            } while (recap);

            colors[i] = possibleColors[drawed];
        }

    }

    private void fillWithDuplicates() {
        Random random = new Random();
        for(int i=0; i<size; i++) {
            int index = random.nextInt(possibleColors.length);
            colors[i] = possibleColors[index];
        }

    }

    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i < size; i++) {
            result += printColor(colors[i]);
            result += " ";
        }
        return result;
    }


    //for debugging
    public String printColor(Color color) {

        if(color == Color.BLUE)
            return "BLUE";
        if(color == Color.RED)
            return "RED";
        if(color == Color.GREEN)
            return "GREEN";
        if(color == Color.YELLOW)
            return "YELLOW";
        if(color == Color.BROWN)
            return "BROWN";
        if(color == Color.PINK)
            return "PINK";
        if(color == Color.TURQUOISE)
            return "TURQUOISE";
        if(color == Color.ORANGE)
            return "ORANGE";
        return "UNSPECIFIED";

    }

    @Override
    public int hashCode() {
        return Integer.parseInt(colors.toString());
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Guess) {
            Guess guess = (Guess) obj;
            if (this.getColors().equals(guess.getColors()))
                return true;
        }
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Guess guess = new Guess(size);
        guess.setColors(colors.clone());
        return guess;

    }

}