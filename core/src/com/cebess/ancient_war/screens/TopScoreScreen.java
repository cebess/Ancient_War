package com.cebess.ancient_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cebess.ancient_war.AncientWar;
import com.cebess.ancient_war.AppPreferences;
import com.cebess.ancient_war.TopScore;
import java.util.Date;

public class TopScoreScreen implements Screen {

    private AncientWar gsm;
    private Stage stage;

    private TopScore[] myScores;
    private AppPreferences myPref;


    public TopScoreScreen(AncientWar gsm) {
        this.gsm = gsm;

        myPref = gsm.getPreferences();
        myScores = myPref.getTop5();

        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        int stageWidth = stage.getViewport().getScreenWidth();
        int stageHeight = stage.getViewport().getScreenHeight();
        float bottomOfPrevious = 0;

        Skin skin = gsm.getAssetManager().get("skin/glassy-ui.json", Skin.class);

        //create the labels
        Label titleLabel = new Label("Top Scores",skin);
        titleLabel.setFontScale(1.4f);
        titleLabel.setPosition((stageWidth-titleLabel.getWidth())/2, stageHeight - titleLabel.getHeight());
        stage.addActor(titleLabel);
        bottomOfPrevious = stageHeight - titleLabel.getHeight();

        Label line1ToLabel = new Label(myScores[0].toString(), skin);
        line1ToLabel.setPosition((stageWidth-line1ToLabel.getWidth())/2, bottomOfPrevious - line1ToLabel.getHeight());
        stage.addActor(line1ToLabel);
        bottomOfPrevious = line1ToLabel.getY();

        Label line2ToLabel = new Label(myScores[1].toString(), skin);
        line2ToLabel.setPosition((stageWidth-line2ToLabel.getWidth())/2, bottomOfPrevious - line2ToLabel.getHeight());
        stage.addActor(line2ToLabel);
        bottomOfPrevious = line2ToLabel.getY();

        Label line3ToLabel = new Label(myScores[2].toString(), skin);
        line3ToLabel.setPosition((stageWidth-line3ToLabel.getWidth())/2, bottomOfPrevious - line3ToLabel.getHeight());
        stage.addActor(line3ToLabel);
        bottomOfPrevious = line3ToLabel.getY();

        Label line4ToLabel = new Label(myScores[3].toString(), skin);
        line4ToLabel.setPosition((stageWidth-line4ToLabel.getWidth())/2, bottomOfPrevious - line4ToLabel.getHeight());
        stage.addActor(line4ToLabel);
        bottomOfPrevious = line4ToLabel.getY();

        Label line5ToLabel = new Label(myScores[4].toString(), skin);
        line5ToLabel.setPosition((stageWidth-line5ToLabel.getWidth())/2, bottomOfPrevious - line5ToLabel.getHeight());
        stage.addActor(line5ToLabel);

        //create buttons
        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.setPosition((stageWidth-continueButton.getWidth())/2,continueButton.getHeight()+30);

        //add the button
        stage.addActor(continueButton);

        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
            }
        });

        //reset high scores buttons
        TextButton resetButton = new TextButton("Reset High Scores", skin);
        resetButton.setPosition((stageWidth-resetButton.getWidth())/2,0);

        //add the button
        stage.addActor(resetButton);

        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (TopScore aScore:myScores) {
                    aScore.setScoreValue(0);
                    aScore.setScoreDate(new Date());
                }
                myPref.setTop5(myScores);
                gsm.pop();
                dispose();
            }
        });


    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }

}