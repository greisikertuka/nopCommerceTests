package com.example.Projekti.utils;

public enum Gender {
    MALE("Male"),
    FEMALE("Feamale");

    private final String genderText;

    Gender(final String genderText) {
        this.genderText = genderText;
    }
    @Override
    public String toString() {
        return genderText;
    }
}
