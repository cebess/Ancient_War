package com.cebess.ancient_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cebess.ancient_war.BattleFormation;

public class FormationSelectionScreen implements Screen {

    private com.cebess.ancient_war.AncientWar gsm;
    private Stage stage;
    private final float LABEL_SCALE = 0.8f;
    private final float BUTTON_LABEL_SCALE = 0.6f;
    private Label remainingResourcesLabel;
    public static int oldArcherValue;
    public static int oldWarriorValue;
    public static int oldKnightValue ;
    public static int currentArmySize;


    public String[] formationNames = {
            "fire wedge",
            "deep spear",
            "row",
            "phalanx",
            "squad",
            "scout support"
    };

    public FormationSelectionScreen(com.cebess.ancient_war.AncientWar gsm){

        this.gsm = gsm;
        /// create stage
        stage = new Stage(new ScreenViewport());
        // initialize the class variables
        oldArcherValue = 0;
        oldWarriorValue = 0;
        oldKnightValue = 0;
        currentArmySize = 0;
        //       AssetManager myAssetManager = AncientWar.gameMainMenu.myAssetManager;


    }

    @Override
    public void show() {
        stage.clear(); //clear off the stage
        Gdx.input.setInputProcessor(stage);
        int stageWidth = stage.getViewport().getScreenWidth();
        int stageHeight = stage.getViewport().getScreenHeight();
        float longestWidth = 0;

        Skin skin = gsm.getAssetManager().get("skin/glassy-ui.json", Skin.class);

        // screen label
        Label titleLabel = new Label("Formations", skin);
        titleLabel.setPosition((stageWidth - titleLabel.getWidth()) / 2, stageHeight - titleLabel.getHeight() );
        stage.addActor(titleLabel);
        float bottomOfPrevious = stageHeight - titleLabel.getHeight();

        // Create a table
        // check boxes for formations
        final CheckBox formation0 = new CheckBox(formationNames[0], skin,"radio");
        formation0.setChecked(true);
        formation0.getLabel().setFontScale(BUTTON_LABEL_SCALE);


        final CheckBox formation1 = new CheckBox(formationNames[1], skin,"radio");
        formation1.setChecked(false);
        formation1.getLabel().setFontScale(BUTTON_LABEL_SCALE);


        final CheckBox formation2 = new CheckBox(formationNames[2], skin,"radio");
        formation2.setChecked(false);
        formation2.getLabel().setFontScale(BUTTON_LABEL_SCALE);


        final CheckBox formation3 = new CheckBox(formationNames[3], skin,"radio");
        formation3.setChecked(false);
        formation3.getLabel().setFontScale(BUTTON_LABEL_SCALE);


        final CheckBox formation4 = new CheckBox(formationNames[4], skin,"radio");
        formation4.setChecked(false);
        formation4.getLabel().setFontScale(BUTTON_LABEL_SCALE);


        final CheckBox formation5 = new CheckBox(formationNames[5], skin,"radio");
        formation5.setChecked(false);
        formation5.getLabel().setFontScale(BUTTON_LABEL_SCALE);

        Table table = new Table();
        table.setDebug(false);

        longestWidth = formation3.getWidth();
        table.row().height(0);
        table.add().width(longestWidth);
        table.add().width(longestWidth);
        table.add().width(longestWidth);
        table.add().width(longestWidth);
        table.add().width(longestWidth);
        table.add().width(longestWidth);
        table.row();
        table.add(formation0).left();
        table.add(formation1).left();
        table.add(formation2).left();
        table.add(formation3).left();
        table.add(formation4).left();
        table.add(formation5).left();

        float newBottom = bottomOfPrevious - (formation5.getHeight());
        table.setPosition(stageWidth/2,newBottom);
        table.bottom();
        bottomOfPrevious = newBottom;

        stage.addActor(table);

        Label fillLabel = new Label("Character Fill Priority", skin);

        newBottom = bottomOfPrevious - fillLabel.getHeight();
        fillLabel.setPosition((stageWidth - fillLabel.getWidth()) / 2, newBottom);
        bottomOfPrevious = newBottom;
        stage.addActor(fillLabel);

        final CheckBox centerPriority = new CheckBox("Center", skin,"radio");
        centerPriority.setChecked(true);
        centerPriority.getLabel().setFontScale(BUTTON_LABEL_SCALE);

        final CheckBox characterPriority = new CheckBox("Character Type", skin,"radio");
        characterPriority.setChecked(false);
        characterPriority.getLabel().setFontScale(BUTTON_LABEL_SCALE);

        // Create a table
        Table table2 = new Table();
        table2.setDebug(false);

        table2.row().height(0);
        table2.add().width(longestWidth);
        table2.add().width(longestWidth);
        table2.row();
        table2.add(centerPriority);
        table2.add(characterPriority);
        table2.center();
        table2.bottom();
        newBottom = bottomOfPrevious - characterPriority.getHeight();
        table2.setPosition(stageWidth/2 , newBottom );
        bottomOfPrevious = newBottom;

        stage.addActor(table2);

        Label armyDefinitionLabel = new Label("Army Population", skin);
        newBottom = bottomOfPrevious - armyDefinitionLabel.getHeight();
        armyDefinitionLabel.setPosition((stageWidth - armyDefinitionLabel.getWidth()) / 2, newBottom);
        bottomOfPrevious = newBottom;

        stage.addActor(armyDefinitionLabel);
        // define the slider tables and the sliders
        final Label archerValueLabel = new Label("0", skin);
        archerValueLabel.setFontScale(LABEL_SCALE);
        final Label warriorValueLabel = new Label("0", skin);
        warriorValueLabel.setFontScale(LABEL_SCALE);
        final Label knightValueLabel = new Label("0", skin);
        knightValueLabel.setFontScale(LABEL_SCALE);

        final Slider archerSlider = new Slider(0f, 10f, 1f, false, skin);
        archerSlider.setValue(0);
        FormationSelectionScreen.oldArcherValue = 0;

//
        archerSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldArcherValue = sliderControlManager( archerSlider, archerValueLabel, oldArcherValue, gsm.archerResourceValue);
            }

        });

        final Slider warriorSlider = new Slider(0f, 10f, 1f, false, skin);
        warriorSlider.setValue(0);
        FormationSelectionScreen.oldWarriorValue = 0;
        warriorSlider.addListener(new ChangeListener() {
                                      @Override
                                      public void changed(ChangeEvent event, Actor actor) {
                                          oldWarriorValue = sliderControlManager(warriorSlider, warriorValueLabel, oldWarriorValue, gsm.warriorResourceValue);

                                      }
                                  }
        );


        final Slider knightSlider = new Slider(0f, 10f, 1f, false, skin);
        knightSlider.setValue(0);
        FormationSelectionScreen.oldKnightValue = 0;
        knightSlider.addListener(new ChangeListener() {
                                     @Override
                                     public void changed(ChangeEvent event, Actor actor) {
                                         oldKnightValue=sliderControlManager(knightSlider, knightValueLabel, oldKnightValue, gsm.knightResourceValue);

                                     }
                                 }
        );

        Label archerLabel = new Label("Archer", skin);
        archerLabel.setFontScale(LABEL_SCALE);
        Label warriorLabel = new Label("Warrior", skin);
        warriorLabel.setFontScale(LABEL_SCALE);
        Label knightLabel = new Label("Knight", skin);
        knightLabel.setFontScale(LABEL_SCALE);
        Label resourcesLabel = new Label("Resources",skin);
        resourcesLabel.setFontScale(LABEL_SCALE);

        remainingResourcesLabel = new Label(String.valueOf(gsm.leftResource),skin);
        remainingResourcesLabel.setFontScale(LABEL_SCALE);

        Table table3 = new Table();
        table3.setDebug(false);
        longestWidth = archerSlider.getWidth()*2;
        float rowHeight = archerSlider.getHeight()*2;

        table3.row().height(0);
        table3.add().width(longestWidth);
        table3.add().width(longestWidth);
        table3.add().width(longestWidth);
        table3.row().height(rowHeight);
        table3.add(archerLabel);
        archerSlider.setHeight(rowHeight);
        table3.add(archerSlider);
        table3.add(archerValueLabel);
        table3.row().height(rowHeight);
        table3.add(warriorLabel);
        table3.add(warriorSlider);
        table3.add(warriorValueLabel);
        table3.row().height(rowHeight);
        table3.add(knightLabel);
        table3.add(knightSlider);
        table3.add(knightValueLabel);
        table3.row().padTop(10f);
        table3.add(resourcesLabel);
        table3.add();
        table3.add(remainingResourcesLabel);

        newBottom = bottomOfPrevious - remainingResourcesLabel.getHeight()*4;
        table3.setPosition(stageWidth/2 - table3.getWidth(), newBottom );
        table3.bottom();
        bottomOfPrevious = newBottom;

        stage.addActor(table3);


        //create the button group
        final ButtonGroup buttonGroupFormation = new ButtonGroup(formation0, formation1, formation2, formation3, formation4, formation5);
        //next set the max and min amount to be checked
        buttonGroupFormation.setMaxCheckCount(1);
        buttonGroupFormation.setMinCheckCount(1);

        //create the button group
        ButtonGroup buttonGroupCharFill = new ButtonGroup(centerPriority, characterPriority);
        //next set the max and min amount to be checked
        buttonGroupCharFill.setMaxCheckCount(1);
        buttonGroupCharFill.setMinCheckCount(1);

        //create buttons
        TextButton continueButton = new TextButton("Attack", skin);

        continueButton.setX(stageWidth/2 - continueButton.getWidth()*2);
        continueButton.setY(20.0f);

        //add the button
        stage.addActor(continueButton);

        // create button listener
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (FormationSelectionScreen.currentArmySize>0) {
                    Button formationSelected = buttonGroupFormation.getChecked();
                    String formationName = formationSelected.toString();
                    formationName = formationName.substring(10);
                    GameFieldScreen gameFieldScreen;
                    BattleFormation.FillMethod fillType = BattleFormation.FillMethod.TypePriority;
                    if (centerPriority.isChecked()) fillType = BattleFormation.FillMethod.CenterOut;
                    gameFieldScreen = new GameFieldScreen(gsm, Math.round(archerSlider.getValue()), Math.round(warriorSlider.getValue()), Math.round(knightSlider.getValue()), fillType, formationName);
                    gsm.set(gameFieldScreen);
                    dispose();
                }
            }
        });
        TextButton surrenderButton = new TextButton("Surrender", skin);

        surrenderButton.setX(stageWidth/2.0f + continueButton.getWidth());
        surrenderButton.setY(20.0f);

        //add the button
        stage.addActor(surrenderButton);

        // create button listener
        surrenderButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.pop();
                gsm.gameReset();
                dispose();
            }
        });


    }

    private void consumeResource(int resourceAmount) {
        gsm.leftResource -= resourceAmount;
        remainingResourcesLabel.setText(String.valueOf(gsm.leftResource));

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

    private int sliderControlManager(Slider sliderToCheck,Label labelToUpdate,int oldValue,int elementResourceValue) {
        int value = Math.round(sliderToCheck.getValue());
        if (value<0 || currentArmySize<0) {
            throw new IllegalArgumentException("X invalid slider value " + value + " or Army size " + currentArmySize);
        }
        if (value != oldValue) { // looks like we have some work to do
            int tempArmy = currentArmySize;
            tempArmy -= oldValue; // remove the old type from the current army, since the value included them
            // check to see if we can handle that many
            int armySize = value + tempArmy; // how big would the new army be?
            if (armySize>18) { // we can only have 18 in the army
                value = 18-tempArmy; // we can only add people until we hit 18
            }
            int tempResource = gsm.leftResource + (oldValue*elementResourceValue);//back out the old resources
            if (tempResource < value * elementResourceValue ) { // there are only so many resources left
                value = tempResource/elementResourceValue; // see what the maximum resources we can consume.
            }
            CharSequence labelText = String.valueOf(value); // determine the new label
            consumeResource((value - oldValue) * elementResourceValue); // consume the resources required for adding the new personne
            currentArmySize += (value-oldValue);
            labelToUpdate.setText(labelText);
            sliderToCheck.setValue(value);
        }
        return value;
    }

}

