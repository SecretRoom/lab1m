package org.example;

public class Price {
    int adult;
    int child;

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public void setChild(int child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "Price: " +
                "\n\t\tadult = " + adult +
                "\n\t\tchild = " + child;
    }
}
