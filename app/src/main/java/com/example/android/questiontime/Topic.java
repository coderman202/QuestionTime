package com.example.android.questiontime;

/**
 * Created by Reggie on 09-Apr-17.
 */

enum Topic {
    SPORTS(0), GEOGRAPHY(1), HISTORY(2), SCIENCE(3), MUSIC(4),
    ARTS(5), GENERAL_KNOWLEDGE(6), ANIMALS(7), FOOD_AND_DRINK(8);

    private int value;

    Topic(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
