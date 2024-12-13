package com.arber.enums;

public enum League {
    // Soccer Leagues
    PREMIER_LEAGUE(Sport.SOCCER),
    LA_LIGA(Sport.SOCCER),
    SERIE_A(Sport.SOCCER),
    BUNDESLIGA(Sport.SOCCER),
    UEFA_CHAMPIONS_LEAGUE(Sport.SOCCER),

    // Basketball Leagues
    NBA(Sport.BASKETBALL),
    EUROLEAGUE(Sport.BASKETBALL),
    NCAA(Sport.BASKETBALL),

    // American Football Leagues
    NFL(Sport.AMERICAN_FOOTBALL),
    NCAA_FOOTBALL(Sport.AMERICAN_FOOTBALL),

    // Tennis (Grand Slam Events)
    WIMBLEDON(Sport.TENNIS),
    US_OPEN(Sport.TENNIS),
    FRENCH_OPEN(Sport.TENNIS),
    AUSTRALIAN_OPEN(Sport.TENNIS),

    // Horse Racing Events
    KENTUCKY_DERBY(Sport.HORSE_RACING),
    MELBOURNE_CUP(Sport.HORSE_RACING),
    GRAND_NATIONAL(Sport.HORSE_RACING),

    // Cricket Leagues
    IPL(Sport.CRICKET),
    BIG_BASH_LEAGUE(Sport.CRICKET),
    ICC_WORLD_CUP(Sport.CRICKET),
    THE_ASHES(Sport.CRICKET),

    // Baseball Leagues
    MLB(Sport.BASEBALL),
    NPB(Sport.BASEBALL),

    // MMA Organizations
    UFC(Sport.MMA),
    BELLATOR(Sport.MMA),

    // Hockey Leagues
    NHL(Sport.HOCKEY),
    KHL(Sport.HOCKEY),
    IIHF_WORLD_CHAMPIONSHIP(Sport.HOCKEY),

    // Rugby Leagues
    NRL(Sport.RUGBY),
    SUPER_RUGBY(Sport.RUGBY),
    SIX_NATIONS(Sport.RUGBY),

    // Australian Football League
    AFL(Sport.AUSTRALIAN_FOOTBALL),

    // Golf Tournaments
    MASTERS(Sport.GOLF),
    PGA_CHAMPIONSHIP(Sport.GOLF),
    US_OPEN_GOLF(Sport.GOLF),
    THE_OPEN_CHAMPIONSHIP(Sport.GOLF);

    private final Sport theSport;

    League(Sport theSport) {
        this.theSport = theSport;
    }

    public Sport getSport() {
        return theSport;
    }
}
