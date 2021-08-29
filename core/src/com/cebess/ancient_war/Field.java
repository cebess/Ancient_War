package com.cebess.ancient_war;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Comparator;
import java.util.LinkedList;

import com.cebess.ancient_war.screens.GameFieldScreen;

public class Field {
    // variables
    static final int MAX_HEIGHT =20;
    static final int MAX_WIDTH = 60;
    static float XRatio ;
    static float YRatio ;


    private LinkedList <BattleElement> field;
    static int getMaxFieldHeight() {
        return MAX_HEIGHT -1;
    }
    static int getMaxFieldWidth() {
        return MAX_WIDTH -1;
    }
    public static enum SideAction {retreat,standstill,normal}
    // these variable contain the side behavior
    SideAction leftSideAction;
    SideAction rightSideAction;
    private int leftDesertingArcherCount = 0;
    private int leftDesertingWarriorCount = 0;
    private int leftDesertingKnightCount = 0;
    private int rightDesertingArcherCount = 0;
    private int rightDesertingWarriorCount = 0;
    private int rightDesertingKnightCount = 0;

    public Field()
    {
        // initialize the field
        field = new LinkedList <BattleElement> ();
        leftSideAction = SideAction.normal;
        rightSideAction = SideAction.normal;
        XRatio = (float) MAX_WIDTH / (float)AncientWar.WORLD_WIDTH;
        YRatio = (float) MAX_HEIGHT / ((float)AncientWar.WORLD_HEIGHT/2); // only use the bottom half of the screen
    }
    public Boolean getBattleComplete() {

        int leftCount = 0;
        int rightCount = 0;
        for (BattleElement testElement:field) {
            if (testElement.getAlive()) {
                if (testElement.getLeftSide()) leftCount++;
                else rightCount++;
            }
        }
        if (leftCount<=0 || rightCount<=0) return true;
        return false;
    }

    public BattleElement getFieldValue(int x, int y) throws IllegalArgumentException {
        if (y<0 || y>= MAX_HEIGHT)
            throw new IllegalArgumentException("Y location invalid: " + y);
        if (x<0 || x>= MAX_WIDTH)
            throw new IllegalArgumentException("y location invalid: " + x);
        for (BattleElement testElement:field) {
            // check for a value at the location
            if (testElement.getLoc_X() == x  && testElement.getLoc_Y() == y) {
                return (testElement);
            }
        }
        return(null);
    }

    public void checkForDeserters() {
        LinkedList <BattleElement> testField = (LinkedList <BattleElement>) field.clone();
        // run through and remove the dead elements
        for (BattleElement testElement:testField) {
            if (testElement.getAlive()) { // we only need to check the ones that are alive
                if (testElement.exitedTheField()) {
                    Boolean left = testElement.getLeftSide();
                    String elementTypeString = testElement.getType();
                    char charType = elementTypeString.charAt(0);
                    if (left) {
                        switch (charType) {
                            case 'a': {
                                leftDesertingArcherCount++;
                                break;
                            }
                            case 'w': {
                                leftDesertingWarriorCount++;
                                break;
                            }
                            case 'k': {
                                leftDesertingKnightCount++;
                                break;
                            }
                            default:
                                throw new IllegalArgumentException("left char type illegal: " + charType);
                        }
                    } else {
                        switch (charType) {
                            case 'a': {
                                rightDesertingArcherCount++;
                                break;
                            }
                            case 'w': {
                                rightDesertingWarriorCount++;
                                break;
                            }
                            case 'k': {
                                rightDesertingKnightCount++;
                                break;
                            }
                            default:
                                throw new IllegalArgumentException("right char type illegal: " + charType);
                        }
                    }
                    field.remove(testElement);
                    // remove them, but they live on to the next battle
                }
            }
        }
    }

    public void add(BattleElement newElement) throws IllegalArgumentException {

        // check to make sure it is in a unique location
        int test_xloc = newElement.getLoc_X();
        int test_yloc = newElement.getLoc_Y();
        BattleElement tempElement = getFieldValue(test_xloc,test_yloc);
        if (tempElement == null) {
            field.add(newElement);
        } else throw new IllegalArgumentException("BattleElement already at this location: " + test_xloc + "," +test_yloc+": "+tempElement);
    }

    public int size()
    {
        return field.size();
    }

    public void action() {
        for (BattleElement testElement:field) {
            if (testElement.getAlive()) {
                BattleElement nearestOpponentElement = nearestOpponent(testElement);
                if (nearestOpponentElement != null) {
                    //determine which side this element is part of
                    SideAction actionType;
                    if (testElement.getLeftSide())
                        actionType = leftSideAction;
                    else
                        actionType = rightSideAction;
                    // tell the element to take action
                    testElement.action(actionType, nearestOpponentElement);
                    //testElement.checkOffTheField(); //included in the movement of the element
                }
            }
        }
        collisionCheck();
        checkForDeserters();

    }

    public void collisionCheck() {
        // check for to elements on the same location
        // copy the field
        BattleElement testElement;
        LinkedList <BattleElement> firstTestField = (LinkedList) field.clone();
        try {
            testElement = firstTestField.pop();
        } catch (Exception e) {
            return;
        }
        while(firstTestField.size()>0) {
            BattleElement  testElement2 = firstTestField.pop();
            if (testElement.getAlive()) { // can only need to check things that are alive
                if (testElement2.getAlive()) { // can only collide with things that are alive
                    if (testElement != testElement2 && (testElement.getLoc_X() == testElement2.getLoc_X() &&
                            (testElement.getLoc_Y() == testElement2.getLoc_Y()))) {
                        // see if they are on the same side
                        if (testElement.getLeftSide() != testElement2.getLeftSide()) {
                            // we have a collision
                            int health1 = testElement.getHealth();
                            int health2 = testElement2.getHealth();
                            testElement.damage(health2);
                            testElement2.damage(health1);
                        }
                    }
                }
            }
        }
    }

    public BattleElement nearestOpponent(BattleElement me) {
        int myX = me.getLoc_X();
        int myY = me.getLoc_Y();
        Boolean mySide = me.getLeftSide();
        BattleElement nearestElement = null;
        double shortestLengthSoFar = Double.MAX_VALUE; // a very long distance
        for (BattleElement testElement:field) {
            if (mySide != testElement.getLeftSide() && testElement.getAlive() ) { // only check the opponents that are alive
                double testLength = lineLength(myX, myY, testElement.getLoc_X(), testElement.getLoc_Y());
                if (testLength < shortestLengthSoFar) {
                    nearestElement = testElement;
                    shortestLengthSoFar = testLength;
                }
            }
        }
        return (nearestElement);
    }
    static public Double lineLength(int x1, int y1, int x2, int y2) {
        double floatLength;

        floatLength = Math.sqrt(Math.pow (x1 - x2,2) + Math.pow(y1 - y2,2));
        return(floatLength);
    }

    public int sideSize(boolean left) {
        int count = 0;
        for (BattleElement testElement:field) {
            if (left) {
                if (testElement.getLeftSide()) count += 1 ;
            } else {
                if (!testElement.getLeftSide()) count +=1;
            }
        }
        return count;
    }

    //    @Override
    public String toString() {
        String lineSep = System.getProperty("line.separator");
        // load up an empty field char array
        String tempString = "";
        char [] [] charArray = new char[MAX_HEIGHT][MAX_WIDTH];
        for (int y = 0; y < MAX_HEIGHT; y++) {
            for (int x = 0; x < MAX_WIDTH; x++) {
                charArray[y][x] = ' ';
            }
        }
        for (BattleElement testElement:field) {
            int xLocation = testElement.getLoc_X();
            int yLocation = testElement.getLoc_Y();
            // place the element in the right location
            if ((xLocation<0) || (xLocation >= MAX_WIDTH) ||
                    (yLocation<0) || (yLocation >= MAX_HEIGHT))
                charArray[0][0] = '!';
            else
            if (testElement.getLeftSide()) {
                char charValue = testElement.getType().charAt(0);
                charValue = (char) ((int) charValue - 32); // capitalize the left size
                charArray[yLocation] [xLocation] =  charValue;
            } else
                charArray[yLocation] [xLocation] =  testElement.getType().charAt(0);
        }
        // put a row across the top
        for (int i = -2; i< MAX_WIDTH; i++) {
            tempString = tempString.concat("_");
        }
        tempString = tempString.concat(lineSep);
        // now that we have the chararray loaded, let's turn it into a string
        for (int y = 0; y< MAX_HEIGHT; y++) {
            tempString = tempString.concat("|");
            for (int x = 0; x < MAX_WIDTH; x++) {
                String s = String.valueOf(charArray[y][x]);
                tempString = tempString.concat(s);
            }
            tempString = tempString.concat("|");
            tempString = tempString.concat(lineSep);
        }
        // put a row across the bottom
        for (int i = -2; i< MAX_WIDTH; i++) {
            tempString = tempString.concat("_");
        }
        return tempString;
    }

    public void draw(SpriteBatch mySpriteBatch) {
        sortBattleElements();
        // draw each of the men on the field
        for (BattleElement testElement:field) {
            SideAction actionType;
            if (testElement.getLeftSide())
                actionType = leftSideAction;
            else
                actionType = rightSideAction;
            testElement.draw(mySpriteBatch,actionType);
        }
    }

    public void setLeftSideAction(SideAction action){
        leftSideAction = action;
    }

    private void sortBattleElements () {
        field.sort(new Comparator<BattleElement>() {
            @Override
            public int compare(BattleElement battleElement, BattleElement t1) {
                return t1.getLoc_Y()-battleElement.getLoc_Y();
            }
        });
    }

    public void formationAdder(BattleFormation myFormation,Boolean left){
        String[] formationString = myFormation.getMyFormationResult();
        int x;
        for (int loop = 0; loop<5;loop++) {
            if (left) {
                x = (4-loop)*4;
            } else {
                x = MAX_WIDTH - ((4-loop)*4)-5;
            }
            for (int yloop = 0;yloop<MAX_HEIGHT;yloop++) {
                char tempChar = formationString[loop].charAt(yloop);
                switch (tempChar){
                    case 'a':
                        BattleElement archerElement = new BattleElement(BattleElement.ElementType.archer, x, yloop, left, GameFieldScreen.bowTextureAtlas);
                        this.add(archerElement);

                        break;
                    case 'k':
                        BattleElement knightElement = new BattleElement(BattleElement.ElementType.knight, x, yloop, left,GameFieldScreen.swordTextureAtlas);
                        this.add(knightElement);

                        break;
                    case 'w':
                        BattleElement warriorElement = new BattleElement(BattleElement.ElementType.warrior, x, yloop, left,GameFieldScreen.spearTextureAtlas);
                        this.add(warriorElement);

                        break;
                }
            }
        }
    }

    public int battleElementCounter(Boolean leftSide,char testCharType,Boolean alive) {
        int count = 0;
        // run through the Elements
        for (BattleElement testElement : field) {
            char charType = testElement.getType().charAt(0);
            if ((charType == testCharType) &&
                    (testElement.getLeftSide() == leftSide) &&
                    (alive == testElement.getAlive())) {
                count++;
            }
        }
        return count;
    }

    public int[][][] battleStatus() {
        int[][][] myStatusTable = new int[2][3][3];
        myStatusTable[0][0][0] = battleElementCounter(true,'a',true);//count of live archers on left side
        myStatusTable[0][0][1] = leftDesertingArcherCount;//count of retired archers on left side
        myStatusTable[0][0][2] = battleElementCounter(true,'a',false);//count of dead archers on left side
        myStatusTable[1][0][0] = battleElementCounter(false,'a',true);//count of live archers on right side
        myStatusTable[1][0][1] = rightDesertingArcherCount;//count of retired archers on right side
        myStatusTable[1][0][2] = battleElementCounter(false,'a',false);//count of dead archers on right side
        //
        myStatusTable[0][1][0] = battleElementCounter(true,'w',true);//count of live warrior on left side
        myStatusTable[0][1][1] = leftDesertingWarriorCount;//count of retired warrior on left side
        myStatusTable[0][1][2] = battleElementCounter(true,'w',false);//count of dead warrior on left side
        myStatusTable[1][1][0] = battleElementCounter(false,'w',true);//count of live warrior on right side
        myStatusTable[1][1][1] = rightDesertingWarriorCount;//count of retired warrior on right side
        myStatusTable[1][1][2] = battleElementCounter(false,'w',false);//count of dead warrior on right side
        //
        myStatusTable[0][2][0] = battleElementCounter(true,'k',true);//count of live knight on left side
        myStatusTable[0][2][1] = leftDesertingKnightCount;//count of retired knight on left side
        myStatusTable[0][2][2] = battleElementCounter(true,'k',false);//count of dead knight on left side
        myStatusTable[1][2][0] = battleElementCounter(false,'k',true);//count of live knight on right side
        myStatusTable[1][2][1] = rightDesertingKnightCount;//count of retired knight on right side
        myStatusTable[1][2][2] = battleElementCounter(false,'k',false);//count of dead knight on right side

        return myStatusTable;
    }
}