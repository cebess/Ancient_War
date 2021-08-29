package com.cebess.ancient_war.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cebess.ancient_war.*;

public class GameFieldScreen implements Screen {

    //screen
    private OrthographicCamera camera;
    private Viewport viewport;
    private AncientWar gsm;
    private Skin skin;

    //graphics
    private Texture background;
    private Stage stage;

    public static TextureAtlas swordTextureAtlas;
    public static TextureAtlas spearTextureAtlas;
    public static TextureAtlas bowTextureAtlas;

    //timing
    private long lastTime;
    private final long sleepTime = 200; //150;

    static final String[] backgrounds= new String[] {
            "desert background1",
            "desert background2",
            "desert background3",
            "field background1",
            "field background2",
            "field background3",
            "wooded1",
            "wooded2"};
    private final Color[] backgroundLabelColors = new Color[] {
            Color.BLACK,
            Color.BLACK,
            Color.RED,
            Color.BLACK,
            Color.BLACK,
            Color.WHITE,
            Color.WHITE,
            Color.WHITE
    };
    private int currentBackground;
    private Music backgroundMusic;

    //class variables
    private Field myField;
    private float currentBackgroundVolumeLevel;

    //#todo add scoring information on the top of the screen?
    public GameFieldScreen(AncientWar gsm,int archerCount,int warriorCount,int knightCount,BattleFormation.FillMethod centerPriority, String formationSelected ) {

        /// create stage
        stage = new Stage(new ScreenViewport());

        lastTime = System.currentTimeMillis()*1000;
        camera = new OrthographicCamera();

        viewport = new StretchViewport(AncientWar.WORLD_WIDTH,AncientWar.WORLD_HEIGHT,camera);
        this.gsm = gsm;

        skin = gsm.getAssetManager().get("skin/glassy-ui.json", Skin.class);

        currentBackground = MathUtils.random(0, backgrounds.length-1);

        String baseBackgroundString = backgrounds[currentBackground];
        background = new Texture("backgrounds/" +  baseBackgroundString + ".jpg");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/"+baseBackgroundString+".mp3"));

        swordTextureAtlas = new TextureAtlas("walking man with sword.txt");
        spearTextureAtlas = new TextureAtlas("walking man with spear.txt");
        bowTextureAtlas = new TextureAtlas("walking man with bow.txt");
        myField = new Field();
        // create some test people for the field
        // create left side

        BattleFormation leftFormation = new BattleFormation(archerCount,warriorCount,knightCount,formationSelected,centerPriority);

        // create right side
        int totalOpposed = archerCount+warriorCount+knightCount;
        int myKnightCount = MathUtils.random(0, totalOpposed);

        if (gsm.rightResource<myKnightCount*gsm.knightResourceValue) {
            myKnightCount = gsm.rightResource/ gsm.knightResourceValue;
        }
        gsm.rightResource -= myKnightCount*gsm.knightResourceValue;
        totalOpposed -= myKnightCount;
        int myArcherCount = MathUtils.random(0, totalOpposed);
        if (gsm.rightResource<myArcherCount*gsm.archerResourceValue) {
            myArcherCount = gsm.rightResource/gsm.archerResourceValue;
        }
        gsm.rightResource -= myArcherCount*gsm.archerResourceValue;
        totalOpposed -= myArcherCount;
        if ((totalOpposed+myArcherCount+myKnightCount<18) && gsm.rightResource>2) {
            if (Math.random()>0.75f) totalOpposed++; // we can give the right side one more warrior
        }
        gsm.rightResource -= totalOpposed;

        BattleFormation rightFormation = new BattleFormation(myArcherCount,totalOpposed,myKnightCount,leftFormation.formationNames[MathUtils.random(0, 5)],BattleFormation.FillMethod.TypePriority);

        myField.formationAdder(leftFormation,true);
        myField.formationAdder(rightFormation,false);
        //       myAssetManager.finishLoading();
        AppPreferences myPref = gsm.getPreferences();

        currentBackgroundVolumeLevel = myPref.getMusicVolume();
        backgroundMusic.setVolume(currentBackgroundVolumeLevel);
        backgroundMusic.setLooping(true);

    }

    @Override
    public void render(float delta) {
        try {
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException e) {
            System.out.println("Interrupted Exception Caught"+ e);
        }
        //update movement section
        myField.action();
        //render section
        gsm.batch.begin();
        gsm.batch.draw(background,0,0,AncientWar.WORLD_WIDTH,AncientWar.WORLD_HEIGHT);
        myField.draw(gsm.batch);
        gsm.batch.end();
        //check to see if anyone is left
        if (myField.getBattleComplete()) {
            if (currentBackgroundVolumeLevel>0){
                currentBackgroundVolumeLevel -= 0.05f;
                if (currentBackgroundVolumeLevel<0) currentBackgroundVolumeLevel=0;
                backgroundMusic.setVolume(currentBackgroundVolumeLevel);
            } else {
                //we are done with this screen
                if (backgroundMusic.isPlaying()) backgroundMusic.stop();
                BattleStatusScreen battleStatusScreenScreen = new BattleStatusScreen(gsm);
                int[][][] statusInfo = myField.battleStatus();
                battleStatusScreenScreen.setBattleStatus(statusInfo);
                gsm.set(battleStatusScreenScreen);
                dispose();
            }
        }
        // tell our stage to do actions and draw itself
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        gsm.batch.setProjectionMatrix(camera.combined);
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
    public void show() {
        AppPreferences myPref = gsm.getPreferences();
        if (myPref.isMusicEnabled()) {
            backgroundMusic.play();
        }
        stage.clear(); //clear off the stage
        Gdx.input.setInputProcessor(stage);
        int stageWidth = stage.getViewport().getScreenWidth();
        int stageHeight = stage.getViewport().getScreenHeight();


        // game control buttons
        final CheckBox retreatButton = new CheckBox("Retreat", skin,"radio");
        retreatButton.setChecked(false);
        retreatButton.getLabel().setColor(backgroundLabelColors[currentBackground]);
        retreatButton.getLabel().setFontScale(.9f);
        retreatButton.setX(20f);
        retreatButton.setY(stageHeight - retreatButton.getHeight());

        // create button listener
        retreatButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //               System.err.println("Retreat Event: " + event + " pointer " + pointer); //just so I can see something
                myField.setLeftSideAction(Field.SideAction.retreat);
                return true;
            }
        });
        stage.addActor(retreatButton);

        final CheckBox chargeButton = new CheckBox("Advance", skin,"radio");
        chargeButton.setChecked(true);
        chargeButton.getLabel().setColor(backgroundLabelColors[currentBackground]);
        chargeButton.getLabel().setFontScale(.9f);
        chargeButton.setX(stageWidth - chargeButton.getWidth()-20f);
        chargeButton.setY(stageHeight - chargeButton.getHeight());
        // create button listener
        chargeButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //               System.err.println("Charge Event: " + event + " pointer " + pointer); //just so I can see something
                myField.setLeftSideAction(Field.SideAction.normal);
                return true;
            }
        });
        stage.addActor(chargeButton);

        final CheckBox standButton = new CheckBox("Hold ground", skin,"radio");
        standButton.setChecked(false);
        standButton.getLabel().setColor(backgroundLabelColors[currentBackground]);
        standButton.getLabel().setFontScale(.9f);
        standButton.setX(stageWidth/2 - standButton.getWidth()/2);
        standButton.setY(stageHeight - standButton.getHeight());
        // create button listener
        standButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //               System.err.println("Stand Event: " + event + " pointer " + pointer); //just so I can see something
                myField.setLeftSideAction(Field.SideAction.standstill);
                return true;
            }
        });
        stage.addActor(standButton);



        //create the button group
        final ButtonGroup buttonGroupFormation = new ButtonGroup(retreatButton, chargeButton, standButton);
        //next set the max and min amount to be checked
        buttonGroupFormation.setMaxCheckCount(1);
        buttonGroupFormation.setMinCheckCount(1);

    }

    @Override
    public void dispose() {
        swordTextureAtlas.dispose();
        spearTextureAtlas.dispose();
        bowTextureAtlas.dispose();
        background.dispose();
        backgroundMusic.dispose();

    }
}
