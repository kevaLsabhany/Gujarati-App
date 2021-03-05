package com.example.gujarati;

public class Word {

    private static final int NO_IMAGE_PROVIDED = -1;
    private String defaultTranslation;
    private String gujaratiTranslation;
    private int imageResourceId = NO_IMAGE_PROVIDED;
    private int audioResourceId;

    public Word(String defaultTranslation, String gujaratiTranslation, int audioResourceId) {
        this.defaultTranslation = defaultTranslation;
        this.gujaratiTranslation = gujaratiTranslation;
        this.audioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String gujaratiTranslation, int imageResourceId, int audioResourceId) {
        this(defaultTranslation, gujaratiTranslation, audioResourceId);
        this.imageResourceId = imageResourceId;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getGujaratiTranslation() {
        return gujaratiTranslation;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public boolean hasImage() {
        return imageResourceId != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Word{" +
                "defaultTranslation='" + defaultTranslation + '\'' +
                ", gujaratiTranslation='" + gujaratiTranslation + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", audioResourceId=" + audioResourceId +
                '}';
    }
}
