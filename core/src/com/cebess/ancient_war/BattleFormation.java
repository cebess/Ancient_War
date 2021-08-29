package com.cebess.ancient_war;

public class BattleFormation {
    public static enum FillMethod {CenterOut,TypePriority};
    public String[] formationNames = {
            "fire wedge",
            "deep spear",
            "row",
            "phalanx",
            "squad",
            "scout support"
    };
    private String[][] formationLayout =
            {
                    {
                            "                    ",
                            "                    ",
                            "       k    k       ",
                            "      wkw  wkw      ",
                            "     awwwaawwwa     "
                    },
                    {
                            "          k         ",
                            "         kkk        ",
                            "        wwkww       ",
                            "       w  w  w      ",
                            "     aaa     aaa    "
                    },
                    {
                            "                    ",
                            "                    ",
                            "                    ",
                            "     wwwkkkkkwww    ",
                            "      aaa w aaa     "
                    },
                    {
                            "                    ",
                            "        wwwww       ",
                            "        wkkkw       ",
                            "        wkkkw       ",
                            "         aaa        "
                    },
                    {
                            "                    ",
                            "          k         ",
                            "        wkkkw       ",
                            "        wwwww       ",
                            "       aaaaaaa      "
                    },
                    {
                            "                    ",
                            "                    ",
                            "       wwwwwww      ",
                            "a       kkkkk      a",
                            "aa                aa"
                    }
            };

    private String myType = "";
    private String[] myFormationSource;
    private String[] myFormationResult =
            {
                    "                    ",
                    "                    ",
                    "                    ",
                    "                    ",
                    "                    "
            };

    private int myArcherCount;
    private int myWarriorCount;
    private int myKnightCount;

    public String[] getMyFormationSource() {
        return myFormationSource;
    }
    public String[] getMyFormationResult() {
        return myFormationResult;
    }

    public int getMyArcherCount() {
        return myArcherCount;
    }

    public int getMyWarriorCount() {
        return myWarriorCount;
    }

    public int getMyKnightCount() {
        return myKnightCount;
    }

    public void debugSetMyArcherCount(int myArcherCount) {
        this.myArcherCount = myArcherCount;
    }

    public void debugSetMyWarriorCount(int myWarriorCount) {
        this.myWarriorCount = myWarriorCount;
    }

    public void debugSetMyKnightCount(int myKnightCount) {
        this.myKnightCount = myKnightCount;
    }

    public BattleFormation(int archerCount,int warriorCount, int knightCount, String formationName,BattleFormation.FillMethod fillType) throws IllegalArgumentException {
        int looper = 0;
        for (String testString:formationNames)
            if (testString.equals(formationName)) {
                // we found it
                myType = testString;
                myFormationSource = formationLayout[looper];
                myArcherCount = archerCount;
                myWarriorCount = warriorCount;
                myKnightCount = knightCount;
                populateFormation(fillType);
                break;
            } else {
                looper++;
            }
        if (looper>=formationNames.length) {
            // someone gave us a bad formation name
            throw new IllegalArgumentException("Formation name: '" + formationName + "' is invalid " );
        }

    }

    @Override
    public String toString()
    {
        return ("Battle formation: " + myType);
    }

    private void populateFormation(BattleFormation.FillMethod fillType) {
        if (fillType == FillMethod.CenterOut) {
            for (int row = 0; row < 5; row++) {
                for (int looper = 1; looper < 11; looper++) {
                    char tempElement;
                    tempElement = myFormationSource[row].charAt(10 - looper);
                    myFormationResult[row] = replaceChar(myFormationResult[row], populateChar(tempElement), 10 - looper);
                    tempElement = myFormationSource[row].charAt(9 + looper);
                    myFormationResult[row] = replaceChar(myFormationResult[row], populateChar(tempElement), 9 + looper);
                }
            }
        } else {
            for (int row = 0; row < 5; row++) {
                for (int looper = 1; looper < 11; looper++) {
                    char tempElement;
                    tempElement = myFormationSource[row].charAt(10 - looper);
                    myFormationResult[row] = replaceChar(myFormationResult[row], typePriorityPopulate(tempElement), 10 - looper);
                    tempElement = myFormationSource[row].charAt(9 + looper);
                    myFormationResult[row] = replaceChar(myFormationResult[row], typePriorityPopulate(tempElement), 9 + looper);
                }
            }
            // run through and find all the ones that could not be filled
            for (int row = 0; row < 5; row++) {
                for (int looper = 0; looper < 11; looper++) {
                    char tempElement;
                    tempElement = myFormationResult[row].charAt(10 - looper);
                    if (tempElement == 'X') {
                        if (myKnightCount > 0) {
                            myKnightCount--;
                            myFormationResult[row] = replaceChar(myFormationResult[row], 'k', 10 - looper);
                        } else if (myWarriorCount > 0) {
                            myWarriorCount--;
                            myFormationResult[row] = replaceChar(myFormationResult[row], 'w', 10 - looper);
                        } else if (myArcherCount > 0) {
                            myArcherCount--;
                            myFormationResult[row] = replaceChar(myFormationResult[row], 'a', 10 - looper);
                        } else
                            myFormationResult[row] = replaceChar(myFormationResult[row], ' ', 10 - looper);
                    }
                    // now the other side
                    tempElement = myFormationResult[row].charAt(9 + looper);
                    if (tempElement == 'X') {

                        if (myKnightCount > 0) {
                            myKnightCount--;
                            myFormationResult[row] = replaceChar(myFormationResult[row], 'k', 9 + looper);
                        } else if (myWarriorCount > 0) {
                            myWarriorCount--;
                            myFormationResult[row] = replaceChar(myFormationResult[row], 'w', 9 + looper);
                        } else if (myArcherCount > 0) {
                            myArcherCount--;
                            myFormationResult[row] = replaceChar(myFormationResult[row], 'a', 9 + looper);
                        } else
                            myFormationResult[row] = replaceChar(myFormationResult[row], ' ', 9 + looper);

                    }
                }
            }
        }
    }

    public static String replaceChar(String str, char ch, int index) {
        String tempString = str.substring(0, index);
        tempString += String.valueOf(ch);
        tempString+= str.substring(index+1);
        return tempString;
    }

    public char typePriorityPopulate(char tempElement) throws IllegalArgumentException  {
        switch (tempElement) {
            case 'a':
                if (myArcherCount > 0) {
                    myArcherCount--;
                    return ('a');
                } else return ('X');
            case 'k':
                if (myKnightCount > 0) {
                    myKnightCount--;
                    return ('k');
                } else return ('X');
            case 'w':
                if (myWarriorCount > 0) {
                    myWarriorCount--;
                    return ('w');
                } else return ('X');
            case ' ': return (' ');
            default:
                throw new IllegalArgumentException("Invalid character '" + tempElement + "'");
        }
    }

    public char populateChar(char tempElement) throws IllegalArgumentException {
        switch (tempElement) {
            case 'a':
                if (myArcherCount > 0) {
                    myArcherCount--;
                    return ('a');
                } else if (myKnightCount > 0) {
                    myKnightCount--;
                    return ('k');
                } else if (myWarriorCount > 0) {
                    myWarriorCount--;
                    return ('w');
                } else return (' ');

            case 'w':
                if (myWarriorCount > 0) {
                    myWarriorCount--;
                    return ('w');
                } else if (myKnightCount > 0) {
                    myKnightCount--;
                    return ('k');
                } else if (myArcherCount > 0) {
                    myArcherCount--;
                    return ('a');
                } else return (' ');

            case 'k':
                if (myKnightCount > 0) {
                    myKnightCount--;
                    return ('k');
                } else if (myWarriorCount > 0) {
                    myWarriorCount--;
                    return ('w');
                } else if (myArcherCount > 0) {
                    myArcherCount--;
                    return ('a');
                } else return (' ');

            case ' ':
                return (' ');

            default:
                throw new IllegalArgumentException("Invalid character too '" + tempElement + "'");
        }
    }
}
