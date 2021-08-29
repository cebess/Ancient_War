package com.cebess.ancient_war;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;


public class Man {
    private float x;
    private float y;
    private int size;
    private Boolean amIleftSide;
    private Sprite[] mySpriteArray;
    private com.badlogic.gdx.utils.Array<com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion> myRegions;
    int looper = 0;

    public Array<TextureAtlas.AtlasRegion> getMyRegions() {
        return myRegions;
    }

    public Man(TextureAtlas myTextureAtlas, int initialX, int initialY, Boolean leftSide) {
        x = (float)initialX;
        y = (float)initialY;
        myRegions = myTextureAtlas.getRegions();
        size = myRegions.size;
        mySpriteArray = new Sprite [size];
        amIleftSide = leftSide;
        looper = MathUtils.random(1, size-1);

        Array.ArrayIterator<TextureAtlas.AtlasRegion> myTexture = myRegions.iterator();
        int loop = 0;
        while (myTexture.hasNext()) {
            String tempName = myTexture.next().name;
            mySpriteArray[loop] = myTextureAtlas.createSprite(tempName);
            if (!leftSide)
                mySpriteArray[loop].flip(true,false);
            loop++;
        }
    }

    public void update(int xUpdate,int yUpdate) {
        if (amIleftSide) {
            x+=xUpdate;
        } else {
            x-=xUpdate;
        }
        y+=yUpdate;
        if (x>AncientWar.WORLD_WIDTH) {
            x = 0;
        } else if (x<0) {
            x = AncientWar.WORLD_WIDTH;
        }
        if (y>AncientWar.WORLD_HEIGHT) {
            y = 0;
        } else if (y<0) {
            y = AncientWar.WORLD_HEIGHT;
        }
    }

    private float calculateScale() {
        return 0.3f + 1f-(AncientWar.WORLD_HEIGHT/((AncientWar.WORLD_HEIGHT*2f)-2f*y));
    }

    private float worldAspectRatio() {
        return (float)AncientWar.WORLD_WIDTH/(float)AncientWar.WORLD_HEIGHT;
    }


    public void draw(SpriteBatch mySpriteBatch,Boolean attack,Boolean retreat,Boolean dead) {
        // find the correct sprite
        Sprite spriteToDraw;
        if (dead) {
            spriteToDraw = mySpriteArray[1]; //this pose shows they are dead
        } else if (attack) {
            spriteToDraw = mySpriteArray[0]; //the attack pose
        } else {
            spriteToDraw = mySpriteArray[looper++];
            if (looper>=size) looper = 2; //two is the start of the animations
        }
        if (retreat) spriteToDraw.flip(true,false); // if they are retreating, flip them

        float spriteAspectRatio = spriteToDraw.getHeight()/spriteToDraw.getWidth();
        spriteToDraw.setPosition(x, y);

        float xScale = calculateScale();
        float temp = worldAspectRatio();
        temp = temp / spriteAspectRatio;
        float yScale = calculateScale()/temp;
        spriteToDraw.setScale(xScale,yScale);

        if (!amIleftSide)  {
            spriteToDraw.setColor(Color.RED);
        } else {
            spriteToDraw.setColor(Color.BLUE);
        }
        spriteToDraw.draw(mySpriteBatch);

        if (retreat) spriteToDraw.flip(true,false); // if they are retreating, flip back

    }


    public void setLocation(float newX, float newY) throws IllegalArgumentException {
        if (newX < 0) x = 0;
        else if (newX > AncientWar.WORLD_WIDTH) x = AncientWar.WORLD_WIDTH;
        else x = newX;

        if (newY < 0) y = 0;
        else if (newY > AncientWar.WORLD_HEIGHT) y = AncientWar.WORLD_HEIGHT;
        else y = newY;
    }

}
