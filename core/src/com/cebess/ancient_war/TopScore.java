package com.cebess.ancient_war;

import java.util.Date;

public class TopScore implements Comparable <TopScore> {

    private int scoreValue;
    private Date scoreDate;

    public TopScore(int score, Date dateOfScore) {
        scoreValue = score;
        scoreDate = dateOfScore;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public Date getScoreDate() {
        return scoreDate;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    public String toString() {
        String strdate = String.format("%03d",scoreValue) + " - " + String.format("%1$td %1$tb, %1$tY", scoreDate);
        return strdate;
    }

    @Override
    public int compareTo(TopScore topScore) {
        if (scoreValue < topScore.getScoreValue()) return -1;
        if (scoreValue > topScore.getScoreValue()) return 1;
        return 0;
    }
}
