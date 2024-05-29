package ceos.springvote.constant;

import lombok.Getter;

@Getter
public enum Part {
    TYPE1(0, "BACKEND"),
    TYPE2(1, "FRONTEND");

    private Integer idx;
    private String text;

    Part(Integer idx, String text) {
        this.idx = idx;
        this.text = text;
    }

    public static Part fromText(String text) {
        for (Part part : Part.values()) {
            if (part.getText().equals(text)) {
                return part;
            }
        }
        return null;
    }

    public static Part fromIdx(Integer idx) {
        for (Part part : Part.values()) {
            if (part.getIdx().equals(idx)) {
                return part;
            }
        }
        return null;
    }

}
