package com.cebess.ancient_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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

public class DisplayScreen implements Screen {

    private AncientWar gsm;
    private Stage stage;
    private Music backgroundMusic;
    //        public AssetManager myAssetManager;
    private String lineOneToDisplay;
    private String lineTwoToDisplay;

    public DisplayScreen(AncientWar gsm,String line1ToDisplay,String line2ToDisplay,String backgroundMusicString) {
        this.gsm = gsm;
        //           myAssetManager = gsm.getAssetManager();

        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        lineOneToDisplay = line1ToDisplay;
        lineTwoToDisplay = line2ToDisplay;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/"+backgroundMusicString+".mp3"));

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        int stageWidth = stage.getViewport().getScreenWidth();
        int stageHeight = stage.getViewport().getScreenHeight();

        AssetManager myAssetManager = AncientWar.gameMainMenu.myAssetManager;

        Skin skin = gsm.getAssetManager().get("skin/glassy-ui.json", Skin.class);
        //create the labels
        Label lineOneToLabel = new Label(lineOneToDisplay, skin);
        lineOneToLabel.setFontScale(1.2f);

        lineOneToLabel.setPosition((stageWidth-lineOneToLabel.getWidth())/2,stageHeight*0.75f);
        stage.addActor(lineOneToLabel);
        Label lineTwoToLabel = new Label(lineTwoToDisplay, skin);
        lineTwoToLabel.setPosition((stageWidth-lineTwoToLabel.getWidth())/2,stageHeight*0.30f);
        stage.addActor(lineTwoToLabel);


        Label leftSideLabel = new Label("Blue", skin);

        //create buttons
        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.setPosition((stageWidth-continueButton.getWidth())/2,30);

        //add the button
        stage.addActor(continueButton);

        // start the music
        AppPreferences myPref = gsm.getPreferences();
        float currentBackgroundVolumeLevel = myPref.getMusicVolume();
        backgroundMusic.setVolume(currentBackgroundVolumeLevel);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
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
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }

    @Override
    public void resume() {
        AppPreferences myPref = gsm.getPreferences();
        if (myPref.isMusicEnabled()) {
            backgroundMusic.play();
        }
    }

    @Override
    public void hide() {
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
        backgroundMusic.dispose();
    }

}