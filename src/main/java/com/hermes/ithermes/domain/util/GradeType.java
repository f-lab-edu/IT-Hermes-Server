package com.hermes.ithermes.domain.util;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GradeType {
    BEGINNER("BEGINNER",0,0),
    JUNIOR("JUNIOR",1,4),
    INTERMEDIATE("INTERMEDIATE",5,9),
    SENIOR("SENIOR",10,30);

    private String name;
    private int minExperience;
    private int maxExperience;

    GradeType(String name, int minExperience, int maxExperience) {
        this.name = name;
        this.minExperience = minExperience;
        this.maxExperience = maxExperience;
    }

    public GradeType checkGradleType(int experience){
        if(experience< BEGINNER.minExperience) return GradeType.BEGINNER;
        else if(JUNIOR.minExperience>0 && JUNIOR.maxExperience<=4) return GradeType.JUNIOR;
        else if(INTERMEDIATE.minExperience>4 && INTERMEDIATE.maxExperience<10) return GradeType.INTERMEDIATE;
        else return GradeType.SENIOR;
    }

    @JsonCreator
    public static JobType fromValue(String grade) {
        return JobType.valueOf(grade.toUpperCase());
    }
}
