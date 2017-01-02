package ge.tsu.handwriting_recognition.model;

public class CharSequence {

    private int numberOfChars;

    private int firstCharASCI;

    public CharSequence() {
    }

    public CharSequence(char firstSymbol, char lastSymbol) {
        numberOfChars = lastSymbol - firstSymbol + 1;
        firstCharASCI = (int)firstSymbol;
    }

    public int getNumberOfChars() {
        return numberOfChars;
    }

    public void setNumberOfChars(int numberOfChars) {
        this.numberOfChars = numberOfChars;
    }

    public int getFirstCharASCI() {
        return firstCharASCI;
    }

    public void setFirstCharASCI(int firstCharASCI) {
        this.firstCharASCI = firstCharASCI;
    }
}
