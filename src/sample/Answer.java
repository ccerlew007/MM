package sample;


public class Answer {

    public Answer() {

        this(0, 0);
    }

    public Answer(int whiteDots, int blackDots) {
        this.whiteDots = whiteDots;
        this.blackDots = blackDots;
    }

    private int whiteDots = 0;
    private int blackDots = 0;

    public int getWhiteDots() {
        return whiteDots;
    }

    public void setWhiteDots(int whiteDots) {
        this.whiteDots = whiteDots;
    }

    public int getBlackDots() {
        return blackDots;
    }

    public void setBlackDots(int blackDots) {
        this.blackDots = blackDots;
    }

    public boolean isWinning() {
        if (blackDots == 4) {
            return true;
        }
        return false;

    }

    @Override
    public int hashCode() {
        return whiteDots * 10 + blackDots;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Answer) {
            Answer a = (Answer) obj;
            if (this.whiteDots == a.whiteDots && this.blackDots == a.blackDots)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Answer{ " + "whiteDots= " + whiteDots + ", blackDots= " + blackDots + " }";
    }
}
