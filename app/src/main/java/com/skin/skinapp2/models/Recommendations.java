package com.skin.skinapp2.models;

public class Recommendations {
    String disease;
    String recommendations;

    public Recommendations(String disease) {
        this.disease = disease;
        //this.recommendations = recommendations;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

//    public String getRecommendations() {
//       // return recommendations;
//    }
//
//    public void setRecommendations(String recommendations) {
//        this.recommendations = recommendations;
//    }
}
