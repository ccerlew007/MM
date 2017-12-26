package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HittingManager {
    private List<Answer> possibilities;
    private List<Guess> variations;
    private List<Guess> toStrikeout;
    private List<Score> scores;


    public HittingManager(List<Guess> variations, List<Guess> toStrikeOut) {
        this.variations = variations;
        this.toStrikeout = toStrikeOut;
        scores = new ArrayList<>();

        possibilities = new ArrayList<>();
        possibilities.add(new Answer(0,0));
        possibilities.add(new Answer(0,1));
        possibilities.add(new Answer(0,2));
        possibilities.add(new Answer(0,3));
        possibilities.add(new Answer(0,4));
        possibilities.add(new Answer(1,0));
        possibilities.add(new Answer(1,1));
        possibilities.add(new Answer(1,2));
        possibilities.add(new Answer(1,3));
        possibilities.add(new Answer(2,0));
        possibilities.add(new Answer(2,1));
        possibilities.add(new Answer(2,2));
        possibilities.add(new Answer(3,0));
        possibilities.add(new Answer(3,1));
        possibilities.add(new Answer(4,0));
    }

    private class Score implements Comparable {

        private Guess guess;
        private int index;
        private int score;

        public Score (int index, int score) {
            this.index = index;
            this.score = score;
            guess = variations.get(index);
        }

        public Guess getGuess() {
            return guess;
        }

        public int getIndex() {
            return index;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(Object obj) {
            Score s = (Score)obj;
            if(this.score == s.score) {
                if(toStrikeout.get(this.index).isStrikeout() && !toStrikeout.get(s.index).isStrikeout()) {
                    return -1;
                } else if(!toStrikeout.get(this.index).isStrikeout() && toStrikeout.get(s.index).isStrikeout()) {
                    return 1;
                } else {
                    return s.index - this.index;
                }
            } else {
                return this.score - s.score;
            }
        }

        @Override
        public String toString() {
            return " {" + guess + ", index=" + index + ", score=" + score + " } ";
        }
    }


    public Guess nextGuess() {

        for(int i = 0; i < variations.size(); i++) {
            Guess guess = variations.get(i);
            int minHitCount = variations.size();

            for(int j = 0; j < possibilities.size(); j++) {
                int counter = 0;
                for(Guess code: toStrikeout) {
                    Answer answer = Checker.check(guess, code);
                    if(!answer.equals(possibilities.get(j))){
                        counter++;
                    }
                }

                if(minHitCount > counter) {
                    minHitCount = counter;
                }
            }

            scores.add(new Score(i,minHitCount));
        }

        Collections.sort(scores);
        return scores.get(scores.size() - 1).getGuess();
    }



}
