package com.cebess.ancient_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.cebess.ancient_war.AncientWar;
import com.cebess.ancient_war.BattleElement;

public class MainMenuScreen implements Screen{

    private AncientWar gsm;
    private Stage stage;
    public AssetManager myAssetManager;

    public MainMenuScreen(AncientWar gsm) {
        this.gsm = gsm;
        myAssetManager = gsm.getAssetManager();
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        BattleElement.setGSM(gsm);
    }

    @Override
    public void show() {
        stage.clear(); //clear off the stage -- getting rid of anything from a previous orientation
        Gdx.input.setInputProcessor(stage);
        int stageWidth = stage.getViewport().getScreenWidth();
        int stageHeight = stage.getViewport().getScreenHeight();

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false); // change to false when design is finalized
        stage.addActor(table);

        Skin skin = gsm.getAssetManager().get("skin/glassy-ui.json", Skin.class);

        //create buttons
        TextButton newGameButton = new TextButton("New Game", skin);
        TextButton preferencesButton = new TextButton("Preferences", skin);
        TextButton topScoresButton = new TextButton("Top Scores", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        TextButton helpButton = new TextButton( "Help",skin);

        //add buttons to table
        //set up the defaults
        table.columnDefaults(0).width(Math.round(stageWidth*0.8));
        long cellHeight = Math.round((stageHeight*0.8)/7)+10; // where 7 is the number of buttons + 2;
        float fontScale = stageHeight/(cellHeight*3);
        table.row().height(cellHeight);
        newGameButton.getLabel().setFontScale(fontScale, fontScale);
        table.add(newGameButton);
        table.row().height(cellHeight);
        table.row().pad(10,0,10,0);
        preferencesButton.getLabel().setFontScale(fontScale, fontScale);
        table.add(preferencesButton);
        table.row().height(cellHeight);
        table.row().pad(0,0,10,0);
        topScoresButton.getLabel().setFontScale(fontScale, fontScale);
        table.add(topScoresButton);
        table.row().height(cellHeight);
        table.row().pad(0,0,10,0);
        helpButton.getLabel().setFontScale(fontScale, fontScale);
        table.add(helpButton);
        table.row().height(cellHeight);
        table.row().pad(0,0,10,0);
        exitButton.getLabel().setFontScale(fontScale, fontScale);
        table.add(exitButton);

        // create button listeners
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FormationSelectionScreen formationSelectionScreen = new FormationSelectionScreen(gsm);
                gsm.push(formationSelectionScreen);
            }
        });

        topScoresButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                TopScoreScreen topScoreScreen = new TopScoreScreen(gsm);
                gsm.push(topScoreScreen);
            }
        });

        preferencesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PreferencesScreen preferencesScreen = new PreferencesScreen(gsm);
                gsm.push(preferencesScreen);
            }
        });

        helpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                HelpScreen helpScreen = new HelpScreen(gsm);
                gsm.push(helpScreen);
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
        myAssetManager.dispose();
    }
}