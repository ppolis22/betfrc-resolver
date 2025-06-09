package com.buzz.betfrcresolver.external;

public class MatchEndEvent {
    private String eventId;
    private Integer matchNum;
    private Integer startTime;
    private Integer redScore;
    private Integer blueScore;
    private Integer redAuto;
    private Integer blueAuto;
    private Integer redEndgame;
    private Integer blueEndgame;

    public MatchEndEvent() {}

    public MatchEndEvent(String eventId, Integer matchNum, Integer startTime, Integer redScore, Integer blueScore,
                         Integer redAuto, Integer blueAuto, Integer redEndgame, Integer blueEndgame) {
        this.eventId = eventId;
        this.matchNum = matchNum;
        this.startTime = startTime;
        this.redScore = redScore;
        this.blueScore = blueScore;
        this.redAuto = redAuto;
        this.blueAuto = blueAuto;
        this.redEndgame = redEndgame;
        this.blueEndgame = blueEndgame;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Integer getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(Integer matchNum) {
        this.matchNum = matchNum;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getRedScore() {
        return redScore;
    }

    public void setRedScore(Integer redScore) {
        this.redScore = redScore;
    }

    public Integer getBlueScore() {
        return blueScore;
    }

    public void setBlueScore(Integer blueScore) {
        this.blueScore = blueScore;
    }

    public Integer getRedAuto() {
        return redAuto;
    }

    public void setRedAuto(Integer redAuto) {
        this.redAuto = redAuto;
    }

    public Integer getBlueAuto() {
        return blueAuto;
    }

    public void setBlueAuto(Integer blueAuto) {
        this.blueAuto = blueAuto;
    }

    public Integer getRedEndgame() {
        return redEndgame;
    }

    public void setRedEndgame(Integer redEndgame) {
        this.redEndgame = redEndgame;
    }

    public Integer getBlueEndgame() {
        return blueEndgame;
    }

    public void setBlueEndgame(Integer blueEndgame) {
        this.blueEndgame = blueEndgame;
    }
}
