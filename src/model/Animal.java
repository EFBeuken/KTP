package model;

public class Animal {

    private String type;
    private String energy;
    private String avWeight;
    private String maleHuntingSeasonStart;
    private String maleHuntingSeasonEnd;
    private String femaleHuntingSeasonStart;
    private String femaleHuntingSeasonEnd;
    private String bestTime;
    private String food;
    private String callers;
    private String environment;

    public Animal(String type, String energy, String avWeight, String maleHuntingSeasonStart, String maleHuntingSeasonEnd, String femaleHuntingSeasonStart, String femaleHuntingSeasonEnd, String bestTime, String food, String callers, String environment) {
        this.type = type;
        this.energy = energy;
        this.avWeight = avWeight;
        this.maleHuntingSeasonStart = maleHuntingSeasonStart;
        this.maleHuntingSeasonEnd = maleHuntingSeasonEnd;
        this.femaleHuntingSeasonStart = femaleHuntingSeasonStart;
        this.femaleHuntingSeasonEnd = femaleHuntingSeasonEnd;
        this.bestTime = bestTime;
        this.food = food;
        this.callers = callers;
        this.environment = environment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getAvWeight() {
        return avWeight;
    }

    public void setAvWeight(String avWeight) {
        this.avWeight = avWeight;
    }

    public String getMaleHuntingSeasonStart() {
        return maleHuntingSeasonStart;
    }

    public void setMaleHuntingSeasonStart(String maleHuntingSeasonStart) {
        this.maleHuntingSeasonStart = maleHuntingSeasonStart;
    }

    public String getMaleHuntingSeasonEnd() {
        return maleHuntingSeasonEnd;
    }

    public void setMaleHuntingSeasonEnd(String maleHuntingSeasonEnd) {
        this.maleHuntingSeasonEnd = maleHuntingSeasonEnd;
    }

    public String getFemaleHuntingSeasonStart() {
        return femaleHuntingSeasonStart;
    }

    public void setFemaleHuntingSeasonStart(String femaleHuntingSeasonStart) {
        this.femaleHuntingSeasonStart = femaleHuntingSeasonStart;
    }

    public String getFemaleHuntingSeasonEnd() {
        return femaleHuntingSeasonEnd;
    }

    public void setFemaleHuntingSeasonEnd(String femaleHuntingSeasonEnd) {
        this.femaleHuntingSeasonEnd = femaleHuntingSeasonEnd;
    }

    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCallers() {
        return callers;
    }

    public void setCallers(String callers) {
        this.callers = callers;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
