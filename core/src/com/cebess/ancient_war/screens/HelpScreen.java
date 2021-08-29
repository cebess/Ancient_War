package com.cebess.ancient_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cebess.ancient_war.AncientWar;
import com.cebess.ancient_war.AppPreferences;
import com.cebess.ancient_war.TopScore;

public class HelpScreen implements Screen {

    private AncientWar gsm;
    private Stage stage;
    //        public AssetManager myAssetManager;
    TopScore[] myScores;
    AppPreferences myPref;
    Skin skin;
    private final String mainHelp = "Ancient War is a small squad-based simulation, where both sides are given a similar amount of resources at the start of the game.\n\nSquad formations as well as the type and number of each resource are defined before each battle.\n\nAfter the battle is complete, surviving resources are recovered before the next round. The first side that runs out resources loses the game.";
    private final String formationText = "You can select from a number of formations for your squad:\n\n* Fire wedge: A three level formation with  Knights in front of two pronged attack with archers in the back.\n* Deep spear: A five level formation with knights in the front and archers in the back.\n* Row: A row of warriors and knight followed by a row of archers.\n* Phalanx: Warriors surrounding a core of knights with three archers in the back row.\n* Squad: A four level formation with a core of knights and 7 archers in the back.\n* Scout support: A three level formation with a row of warriors followed by a row of knight and 6 archers in the outer edges.\n";
    private final String fillText = "Each formation has slots allocated for certain times of members. If you do not have enough of a certain type, you can select how the holes should be filled.\n\nCenter: ensures that the center of the formation is filled first.\nCharacter Type: ensures that all the holes of a certain character type are filled first and then the remain holes are filled in with whatever roles remain. \n\nFor example, if the Scout support formation is select, it has warriors and knights at the center and archers on the outer edges. If center fill with a squad of all archers is chosen, the center slots would all be filled with the archers first. If Character Type priority is selected, the archer roles on the outside would be filled first and the center filled in with only left-over archers.";
    TextArea textAreaInFront;


    public HelpScreen(AncientWar gsm) {
        this.gsm = gsm;
//            myAssetManager = gsm.getAssetManager();
        myPref = gsm.getPreferences();
        skin = gsm.getAssetManager().get("skin/glassy-ui.json", Skin.class);
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        final int stageWidth = stage.getViewport().getScreenWidth();
        final int stageHeight = stage.getViewport().getScreenHeight();
        float bottomOfPrevious = 0;

        //create the labels
        final Label titleLabel = new Label("Help",skin);
        titleLabel.setFontScale(1.4f);
        titleLabel.setAlignment(Align.center);
        titleLabel.setPosition((stageWidth-titleLabel.getWidth())/2,stageHeight - titleLabel.getHeight() );
//            titleLabel.setPosition((stageWidth-titleLabel.getWidth())/2, stageHeight - titleLabel.getHeight());

        stage.addActor(titleLabel);
        bottomOfPrevious = stageHeight - titleLabel.getHeight();

        final TextArea helpText = new TextArea(mainHelp,skin);
        helpText.setWidth(stageWidth);
        helpText.setHeight(stageHeight-titleLabel.getHeight()*4-20);
        helpText.setPosition(10 , bottomOfPrevious - helpText.getHeight()-20);
        stage.addActor(helpText);
        textAreaInFront = helpText;

        final TextArea formationTextArea = new TextArea(formationText,skin);
        formationTextArea.setWidth(stageWidth-20);
        formationTextArea.setHeight(stageHeight-titleLabel.getHeight()*4-20);
        formationTextArea.setPosition(10 , bottomOfPrevious - helpText.getHeight()-20);

        final TextArea fillTextArea = new TextArea(fillText,skin);
        fillTextArea.setWidth(stageWidth-20);
        fillTextArea.setHeight(stageHeight-titleLabel.getHeight()*4-20);
        fillTextArea.setPosition(10 , bottomOfPrevious - helpText.getHeight()-20);

        //create buttons
        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.setPosition((stageWidth-continueButton.getWidth())/2,0f);
        //add the button
        stage.addActor(continueButton);

        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
            }
        });

        // add button row to change text
        TextButton formationButton = new TextButton("Formation", skin);
        formationButton.setPosition(10,continueButton.getHeight());
        stage.addActor(formationButton);

        formationButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                textAreaInFront.remove();
                stage.addActor(formationTextArea);
                textAreaInFront = formationTextArea;
                titleLabel.setText("Formation Help");
                //                   titleLabel.setPosition((stageWidth-titleLabel.getWidth())/2, stageHeight - titleLabel.getHeight());
            }
        });

        // add button row to change text
        TextButton fillButton = new TextButton("Fill Priority", skin);
        fillButton.setPosition(stageWidth-fillButton.getWidth()-10,continueButton.getHeight());
        stage.addActor(fillButton);

        fillButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                textAreaInFront.remove();
                stage.addActor(fillTextArea);
                textAreaInFront = fillTextArea;
                titleLabel.setText("Fill Priority Help");
                //                   titleLabel.setPosition((stageWidth-titleLabel.getWidth())/2, stageHeight - titleLabel.getHeight());

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
