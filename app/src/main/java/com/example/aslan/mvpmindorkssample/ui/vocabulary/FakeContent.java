package com.example.aslan.mvpmindorkssample.ui.vocabulary;

public class FakeContent {

    private String word;
    private boolean isFake;

    public FakeContent(String word, boolean isFake) {
        this.word = word;
        this.isFake = isFake;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isFake() {
        return isFake;
    }

    public void setFake(boolean fake) {
        isFake = fake;
    }
}
