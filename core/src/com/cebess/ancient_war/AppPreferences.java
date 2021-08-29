package com.cebess.ancient_war;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.Date;

public class AppPreferences {
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "AncientWar";
    private static final String PREF_HIGHSCORE1 = "high.score.1";
    private static final String PREF_HIGHDATE1 = "high.date.1";
    private static final String PREF_HIGHSCORE2 = "high.score.2";
    private static final String PREF_HIGHDATE2 = "high.date.2";
    private static final String PREF_HIGHSCORE3 = "high.score.3";
    private static final String PREF_HIGHDATE3 = "high.date.3";
    private static final String PREF_HIGHSCORE4 = "high.score.4";
    private static final String PREF_HIGHDATE4 = "high.date.4";
    private static final String PREF_HIGHSCORE5 = "high.score.5";
    private static final String PREF_HIGHDATE5 = "high.date.5";

    private Preferences preferences = null; // this is required to work properly under android?!?!

    protected Preferences getPrefs() {
        if (preferences == null)
            preferences = Gdx.app.getPreferences("AncientWar");
        return preferences;
    }

    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

    public TopScore[] getTop5() {
        Date d1 = new Date();
        TopScore[]  scoreList = new TopScore[5];
        int tempScore = getPrefs().getInteger(PREF_HIGHSCORE1, 0);
        long tempDate = getPrefs().getLong(PREF_HIGHDATE1,d1.getTime());
        d1 = new Date(tempDate);
        scoreList[0] = new TopScore(tempScore,d1);
        tempScore = getPrefs().getInteger(PREF_HIGHSCORE2, 0);
        tempDate = getPrefs().getLong(PREF_HIGHDATE2,d1.getTime());
        d1 = new Date(tempDate);
        scoreList[1] = new TopScore(tempScore,d1);
        tempScore = getPrefs().getInteger(PREF_HIGHSCORE3, 0);
        tempDate = getPrefs().getLong(PREF_HIGHDATE3,d1.getTime());
        d1 = new Date(tempDate);
        scoreList[2] = new TopScore(tempScore,d1);
        tempScore = getPrefs().getInteger(PREF_HIGHSCORE4, 0);
        tempDate = getPrefs().getLong(PREF_HIGHDATE4,d1.getTime());
        d1 = new Date(tempDate);
        scoreList[3] = new TopScore(tempScore,d1);
        tempScore = getPrefs().getInteger(PREF_HIGHSCORE5, 0);
        tempDate = getPrefs().getLong(PREF_HIGHDATE5,d1.getTime());
        d1 = new Date(tempDate);
        scoreList[4] = new TopScore(tempScore,d1);
        return scoreList;
    }

    public void setTop5(TopScore[] newScores) {
        getPrefs().putInteger(PREF_HIGHSCORE1, newScores[0].getScoreValue());
        getPrefs().putLong(PREF_HIGHDATE1,newScores[0].getScoreDate().getTime());
        getPrefs().putInteger(PREF_HIGHSCORE2, newScores[1].getScoreValue());
        getPrefs().putLong(PREF_HIGHDATE2,newScores[1].getScoreDate().getTime());
        getPrefs().putInteger(PREF_HIGHSCORE3, newScores[2].getScoreValue());
        getPrefs().putLong(PREF_HIGHDATE3,newScores[2].getScoreDate().getTime());
        getPrefs().putInteger(PREF_HIGHSCORE4, newScores[3].getScoreValue());
        getPrefs().putLong(PREF_HIGHDATE4,newScores[3].getScoreDate().getTime());
        getPrefs().putInteger(PREF_HIGHSCORE5, newScores[4].getScoreValue());
        getPrefs().putLong(PREF_HIGHDATE5,newScores[4].getScoreDate().getTime());
        getPrefs().flush();
    }

    public void insertTopScore(TopScore[] scores,int newScore) {
        TopScore[] myScores = scores;
        int oldScore =0;
        Date oldDate = new Date(0l);
        Boolean foundScore =false;
        for (TopScore aScore:myScores) {
            if (!foundScore) {
                if (aScore.getScoreValue() < newScore) {
                    // looks like we have a new high score
                    oldScore = aScore.getScoreValue();
                    oldDate = aScore.getScoreDate();
                    Date now = new Date();
                    aScore.setScoreValue(newScore);
                    aScore.setScoreDate(now);
                    foundScore = true;
                }
                continue;
            } else if (oldScore != 0) { // push the scores down
                TopScore tempScore = new TopScore(oldScore,oldDate);
                oldScore = aScore.getScoreValue();
                oldDate = aScore.getScoreDate();
                aScore.setScoreValue(tempScore.getScoreValue());
                aScore.setScoreDate(tempScore.getScoreDate());
            }
        }
        setTop5(myScores);
    }
}