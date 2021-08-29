package com.cebess.ancient_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cebess.ancient_war.AncientWar;
import com.cebess.ancient_war.AppPreferences;

public class BattleStatusScreen implements Screen {

    private AncientWar gsm;
    private Stage stage;
    private final int numberOfColumns = 8;
    private final int numberOfRows = 5;

    private int[][][] battleStatus;

    public BattleStatusScreen(AncientWar gsm) {
        this.gsm = gsm;
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    public void setBattleStatus(int[][][] status) {
        battleStatus = status;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Create a table that fills the screen.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false); // change to false when design is finalized
        stage.addActor(table);
        int stageWidth = stage.getViewport().getScreenWidth();
        int stageHeight = stage.getViewport().getScreenHeight();

//        AssetManager myAssetManager = AncientWar.gameMainMenu.myAssetManager;
        Skin skin = gsm.getAssetManager().get("skin/glassy-ui.json", Skin.class);

        //set up the defaults for column width and height
        long cellHeight = Math.round((stageHeight*0.8)/(numberOfRows*2));
        float fontScale = 0.8f;

        //create the labels
        Label battleStatusLabel = new Label("Battle Status", skin);
        Label leftSideLabel = new Label("Blue", skin);
        Label rightSideLabel = new Label ("Red", skin);
        Label blankLabel = new Label ("    ", skin);
        Label archerLabel = new Label ("Archer", skin);
        Label warriorLabel = new Label ("Warrior", skin);
        Label knightLabel = new Label ("Knight", skin);
        Label liveLabel1 = new Label ("Live", skin);
        Label retiredLabel1 = new Label ("Retreat", skin);
        Label deadLabel1 = new Label ("Dead", skin);
        Label liveLabel2 = new Label ("Live", skin);
        Label retiredLabel2 = new Label ("Retreat", skin);
        Label deadLabel2 = new Label ("Dead", skin);
// add the header
        battleStatusLabel.setPosition((stageWidth-battleStatusLabel.getWidth())/2,stageHeight-battleStatusLabel.getHeight()-60);
        stage.addActor(battleStatusLabel);
// populate the table
        table.row(); // this is my placeholder row
        table.row().height(0);
        table.add().width(160);
        table.add().width(140);
        table.add().width(140);
        table.add().width(140);
        table.add().width(10);
        table.add().width(140);
        table.add().width(140);
        table.add().width(140);
        table.add().width(10);

        table.row();
        table.row().height(cellHeight);
        table.add();
        table.add();
        leftSideLabel.setFontScale(fontScale, fontScale);
        table.add(leftSideLabel).center();
        table.add();
        table.add();
        table.add();
        rightSideLabel.setFontScale(fontScale, fontScale);
        table.add(rightSideLabel).center();

        table.row();
        table.row().height(cellHeight);
        table.add();
        liveLabel1.setFontScale(fontScale, fontScale);
        table.add(liveLabel1).center();
        retiredLabel1.setFontScale(fontScale, fontScale);
        table.add(retiredLabel1).center();
        deadLabel1.setFontScale(fontScale, fontScale);
        table.add(deadLabel1).center();
        table.add();
        liveLabel2.setFontScale(fontScale, fontScale);
        table.add(liveLabel2).center();
        retiredLabel2.setFontScale(fontScale, fontScale);
        table.add(retiredLabel2).center();
        deadLabel2.setFontScale(fontScale, fontScale);
        table.add(deadLabel2).center();
        table.row();

        archerLabel.setFontScale(fontScale, fontScale);
        table.add(archerLabel).right();
        Label archerLiveLeft = new Label (String.valueOf(battleStatus[0][0][0]), skin);
        archerLiveLeft.setFontScale(fontScale, fontScale);
        table.add(archerLiveLeft).center();
        Label archerRetiredLeft = new Label (String.valueOf(battleStatus[0][0][1]), skin);
        archerRetiredLeft.setFontScale(fontScale, fontScale);
        table.add(archerRetiredLeft).center();
        Label archerDeadLeft = new Label (String.valueOf(battleStatus[0][0][2]), skin);
        archerDeadLeft.setFontScale(fontScale, fontScale);
        table.add(archerDeadLeft).center();
        table.add();
        Label archerLiveRight = new Label (String.valueOf(battleStatus[1][0][0]), skin);
        archerLiveRight.setFontScale(fontScale, fontScale);
        table.add(archerLiveRight).center();
        Label archerRetiredRight = new Label (String.valueOf(battleStatus[1][0][1]), skin);
        archerRetiredRight.setFontScale(fontScale, fontScale);
        table.add(archerRetiredRight).center();
        Label archerDeadRight = new Label (String.valueOf(battleStatus[1][0][2]), skin);
        archerDeadRight.setFontScale(fontScale, fontScale);
        table.add(archerDeadRight).center();

        table.row();

        warriorLabel.setFontScale(fontScale, fontScale);
        table.add(warriorLabel).right();
        Label warriorLiveLeft = new Label (String.valueOf(battleStatus[0][1][0]), skin);
        warriorLiveLeft.setFontScale(fontScale, fontScale);
        table.add(warriorLiveLeft).center();
        Label warriorRetiredLeft = new Label (String.valueOf(battleStatus[0][1][1]), skin);
        warriorRetiredLeft.setFontScale(fontScale, fontScale);
        table.add(warriorRetiredLeft).center();
        Label warriorDeadLeft = new Label (String.valueOf(battleStatus[0][1][2]), skin);
        warriorDeadLeft.setFontScale(fontScale, fontScale);
        table.add(warriorDeadLeft).center();
        table.add();
        Label warriorLiveRight = new Label (String.valueOf(battleStatus[1][1][0]), skin);
        warriorLiveRight.setFontScale(fontScale, fontScale);
        table.add(warriorLiveRight).center();
        Label warriorRetiredRight = new Label (String.valueOf(battleStatus[1][1][1]), skin);
        warriorRetiredRight.setFontScale(fontScale, fontScale);
        table.add(warriorRetiredRight).center();
        Label warriorDeadRight = new Label (String.valueOf(battleStatus[1][1][2]), skin);
        warriorDeadRight.setFontScale(fontScale, fontScale);
        table.add(warriorDeadRight).center();

        table.row();
        knightLabel.setFontScale(fontScale, fontScale);
        table.add(knightLabel).right();
        Label knightLiveLeft = new Label (String.valueOf(battleStatus[0][2][0]), skin);
        knightLiveLeft.setFontScale(fontScale, fontScale);
        table.add(knightLiveLeft).center();
        Label knightRetiredLeft = new Label (String.valueOf(battleStatus[0][2][1]), skin);
        knightRetiredLeft.setFontScale(fontScale, fontScale);
        table.add(knightRetiredLeft).center();
        Label knightDeadLeft = new Label (String.valueOf(battleStatus[0][2][2]), skin);
        knightDeadLeft.setFontScale(fontScale, fontScale);
        table.add(knightDeadLeft).center();
        table.add();
        Label knightLiveRight = new Label (String.valueOf(battleStatus[1][2][0]), skin);
        knightLiveRight.setFontScale(fontScale, fontScale);
        table.add(knightLiveRight).center();
        Label knightRetiredRight = new Label (String.valueOf(battleStatus[1][2][1]), skin);
        knightRetiredRight.setFontScale(fontScale, fontScale);
        table.add(knightRetiredRight).center();
        Label knightDeadRight = new Label (String.valueOf(battleStatus[1][2][2]), skin);
        knightDeadRight.setFontScale(fontScale, fontScale);
        table.add(knightDeadRight).center();

        //create buttons
        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.setPosition((stageWidth-continueButton.getWidth())/2,30);

        //add the button
        stage.addActor(continueButton);

        // create button listeners
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //give right an advantage
                if (gsm.rightResource<0) gsm.rightResource=0;
                // look for the survivors
                gsm.leftResource += (battleStatus[0][0][0]+battleStatus[0][0][1])*gsm.archerResourceValue; //archers live and retreated
                gsm.leftResource += (battleStatus[0][1][0]+battleStatus[0][1][1])*gsm.warriorResourceValue; //warriors live and retreated
                gsm.leftResource += (battleStatus[0][2][0]+battleStatus[0][2][1])*gsm.knightResourceValue; // knights live and retreated
                // look for the survivors on the right side
                gsm.rightResource += (battleStatus[1][0][0]+battleStatus[1][0][1])*gsm.archerResourceValue; //archers live and retreated
                gsm.rightResource += (battleStatus[1][1][0]+battleStatus[1][1][1])*gsm.warriorResourceValue;//warriors live and retreated
                gsm.rightResource += (battleStatus[1][2][0]+battleStatus[1][2][1])*gsm.knightResourceValue; // knights live and retreated
                //              System.err.println("Left Resource: " + gsm.leftResource + " Right Resource: "+ gsm.rightResource); //just so I can see something
                if (gsm.leftResource <= 0) { // looks like we lost
                    if (gsm.rightResource<0) gsm.rightResource=1;

                    DisplayScreen defeatScreen = new DisplayScreen(gsm,"You've lost!","They still had " + gsm.rightResource+ " resources.","Defeat Loop");
                    gsm.gameReset();
                    gsm.set(defeatScreen);
                } else if (gsm.rightResource <= 0) { // looks like we won
                    AppPreferences myPref = gsm.getPreferences();
                    myPref.insertTopScore(myPref.getTop5(),gsm.leftResource);
                    DisplayScreen victoryScreen = new DisplayScreen(gsm,"You've won!","You still had " + gsm.leftResource + " resources.","Victory Loop");
                    gsm.gameReset();
                    gsm.set(victoryScreen);
                } else { // go on to next round
                    FormationSelectionScreen formationSelectionScreen = new FormationSelectionScreen(gsm);
                    gsm.set(formationSelectionScreen);
                }
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