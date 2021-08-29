package com.cebess.ancient_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PreferencesScreen implements Screen{

    private com.cebess.ancient_war.AncientWar gsm;
    private Stage stage;
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;


    public PreferencesScreen(com.cebess.ancient_war.AncientWar gsm){
        this.gsm = gsm;
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        //       AssetManager myAssetManager = AncientWar.gameMainMenu.myAssetManager;
        Skin skin = gsm.getAssetManager().get("skin/glassy-ui.json", Skin.class);

        // music volume
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(gsm.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gsm.getPreferences().setMusicVolume(volumeMusicSlider.getValue());

                return false;
            }
        });

        // sound volume
        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(gsm.getPreferences().getSoundVolume());
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gsm.getPreferences().setSoundVolume(soundMusicSlider.getValue());

                return false;
            }
        });

        // music on/off
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(gsm.getPreferences().isMusicEnabled());
        musicCheckbox.addListener( new InputListener () {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                boolean enabled = !musicCheckbox.isChecked(); // we want to toggle the value
                gsm.getPreferences().setMusicEnabled(enabled);
                return enabled;
            };

        });

        // sound on/off
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(gsm.getPreferences().isSoundEffectsEnabled()); // set the current value

        soundEffectsCheckbox.addListener( new InputListener () {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                boolean enabled = !soundEffectsCheckbox.isChecked(); // we want to toggle the value
                gsm.getPreferences().setSoundEffectsEnabled(enabled);
                return enabled;
            };

        });

        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
                dispose();
            }
        });

        titleLabel = new Label( "Preferences", skin );
        volumeMusicLabel = new Label( "Music Volume", skin );
        volumeSoundLabel = new Label( "Sound Volume", skin );
        musicOnOffLabel = new Label( "Music", skin );
        soundOnOffLabel = new Label( "Sound Effect", skin );

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,10);
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row().pad(10,0,0,10);
        table.add(backButton).colspan(2);

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


    }

}
