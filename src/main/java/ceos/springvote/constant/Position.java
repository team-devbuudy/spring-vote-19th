package ceos.springvote.constant;

import lombok.Getter;

@Getter
public enum Position {
    TYPE1(0, "LEADER"),
    TYPE2(1, "FOLLOWER");

    private Integer idx;
    private String text;

    Position(Integer idx, String text) {
        this.idx = idx;
        this.text = text;
    }

    public static Position fromText(String text) {
        for (Position position : Position.values()) {
            if (position.getText().equals(text)) {
                return position;
            }
        }
        return null;
    }

    public static Position fromIdx(Integer idx) {
        for (Position position : Position.values()) {
            if (position.getIdx().equals(idx)) {
                return position;
            }
        }
        return null;
    }
}
