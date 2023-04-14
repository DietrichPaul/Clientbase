package de.dietrichpaul.clientbase.feature.gui.api.font;

import com.google.gson.JsonObject;

public class Glyph {

    private int unicode;
    private float advance;

    private float pLeft;
    private float pBottom;
    private float pRight;
    private float pTop;

    private float aLeft;
    private float aBottom;
    private float aRight;
    private float aTop;

    public static Glyph parse(JsonObject object) {
        Glyph glyph = new Glyph();
        glyph.unicode = object.get("unicode").getAsInt();
        glyph.advance = object.get("advance").getAsFloat();

        if (object.has("planeBounds")) {
            glyph.pLeft = object.get("planeBounds").getAsJsonObject().get("left").getAsFloat();
            glyph.pBottom = object.get("planeBounds").getAsJsonObject().get("bottom").getAsFloat();
            glyph.pRight = object.get("planeBounds").getAsJsonObject().get("right").getAsFloat();
            glyph.pTop = object.get("planeBounds").getAsJsonObject().get("top").getAsFloat();
        }

        if (object.has("atlasBounds")) {
            glyph.aLeft = object.get("atlasBounds").getAsJsonObject().get("left").getAsFloat();
            glyph.aBottom = object.get("atlasBounds").getAsJsonObject().get("bottom").getAsFloat();
            glyph.aRight = object.get("atlasBounds").getAsJsonObject().get("right").getAsFloat();
            glyph.aTop = object.get("atlasBounds").getAsJsonObject().get("top").getAsFloat();
        }

        return glyph;
    }

    public int getUnicode() {
        return unicode;
    }

    public float getAdvance() {
        return advance;
    }

    public float getpLeft() {
        return pLeft;
    }

    public float getpBottom() {
        return pBottom;
    }

    public float getpRight() {
        return pRight;
    }

    public float getpTop() {
        return pTop;
    }

    public float getaLeft() {
        return aLeft;
    }

    public float getaBottom() {
        return aBottom;
    }

    public float getaRight() {
        return aRight;
    }

    public float getaTop() {
        return aTop;
    }
}
