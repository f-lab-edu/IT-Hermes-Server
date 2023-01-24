package com.hermes.ithermes.domain.util;

public enum GradeType {
    BEGINNER("BEGINNER"),
    JUNIOR("JUNIOR"),
    INTERMEDIATE("INTERMEDIATE"),
    SENIOR("SENIOR");

    private String name;

    GradeType(String name) {
        this.name = name;
    }

    public GradeType checkGradleType(int experience){
        if(experience == 0) return GradeType.BEGINNER;
        else if(experience>0 && experience<=4) return GradeType.JUNIOR;
        else if(experience>4 && experience<10) return GradeType.INTERMEDIATE;
        else return GradeType.SENIOR;
    }
}
