package ceos.springvote.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Team {
    TYPE1(0, "Azito"),
    TYPE2(1, "BeatBuddy"),
    TYPE3(2, "Buldog"),
    TYPE4(3, "Couplelog"),
    TYPE5(4, "TIG");

    private int idx;
    private String text;

    Team(int idx, String text) {
        this.idx = idx;
        this.text = text;
    }

    public int getIdx() {
        return idx;
    }

    @JsonValue
    public String getText() {
        return text;
    }

    @JsonCreator
    public static Team fromText(String text) {
        for (Team role : Team.values()) {
            if (role.getText().equals(text)) {
                return role;
            }
        }
        return null;
    }

    public static Team fromIdx(int idx) {
        for (Team role : Team.values()) {
            if (role.getIdx() == idx) {
                return role;
            }
        }
        return null;
    }
}
