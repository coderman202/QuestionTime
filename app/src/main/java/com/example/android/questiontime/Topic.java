package com.example.android.questiontime;

/**
 * This enum file stores the Topic choices for all possible questions.
 */

enum Topic {
    SPORTS(0), GEOGRAPHY(1), HISTORY(2), SCIENCE(3), MUSIC(4),
    ARTS(5), GENERAL_KNOWLEDGE(6), ANIMALS(7), FOOD_AND_DRINK(8);

    private int value;

    Topic(int value){
        this.value = value;
    }

    public static Topic setValue(int val){
        switch (val){
            case 0:
                return SPORTS;
            case 1:
                return GEOGRAPHY;
            case 2:
                return HISTORY;
            case 3:
                return SCIENCE;
            case 4:
                return MUSIC;
            case 5:
                return ARTS;
            case 6:
                return GENERAL_KNOWLEDGE;
            case 7:
                return ANIMALS;
            case 8:
                return FOOD_AND_DRINK;

        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
