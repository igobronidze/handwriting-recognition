package ge.tsu.handwritin_recognition.model;

public class CharsSet {

    private int numberOfChars;

    private int firstCharASCI;

    public CharsSet() {
    }

    public CharsSet(int numberOfChars, int firstCharASCI) {
        this.numberOfChars = numberOfChars;
        this.firstCharASCI = firstCharASCI;
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
