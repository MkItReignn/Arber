package com.arber.model;

public enum League {
    // American Football Leagues
    NCAAF(Sport.AMERICAN_FOOTBALL),
    NFL(Sport.AMERICAN_FOOTBALL),
    NFL_SUPER_BOWL(Sport.AMERICAN_FOOTBALL),

    // Aussie Rules Football
    AFL(Sport.AUSTRALIAN_FOOTBALL),

    // Baseball Leagues
    MLB(Sport.BASEBALL),

    // Basketball Leagues
    NBA(Sport.BASKETBALL),
    EUROLEAGUE(Sport.BASKETBALL),
    NBL(Sport.BASKETBALL),
    NCAAB(Sport.BASKETBALL),

    // Boxing
    BOXING(Sport.BOXING),

    // Cricket Leagues
    BIG_BASH(Sport.CRICKET),
    TEST_MATCHES(Sport.CRICKET),
    INTERNATIONAL_TWENTY20(Sport.CRICKET),
    ONE_DAY_INTERNATIONALS(Sport.CRICKET),

    // Golf Tournaments
    MASTERS(Sport.GOLF),
    PGA_CHAMPIONSHIP(Sport.GOLF),
    US_OPEN_GOLF(Sport.GOLF),
    THE_OPEN_CHAMPIONSHIP(Sport.GOLF),

    // Ice Hockey Leagues
    NHL(Sport.ICE_HOCKEY),
    HOCKEY_ALLSVENSKAN(Sport.ICE_HOCKEY),
    SHL(Sport.ICE_HOCKEY),

    // MMA
    MMA(Sport.MMA),

    // Rugby Leagues
    NRL(Sport.RUGBY),

    // Soccer Leagues
    A_LEAGUE(Sport.SOCCER),
    AUSTRIAN_BUNDESLIGA(Sport.SOCCER),
    BELGIUM_FIRST_DIV(Sport.SOCCER),
    EFL_CHAMPIONSHIP(Sport.SOCCER),
    EFL_CUP(Sport.SOCCER),
    EFL_LEAGUE_ONE(Sport.SOCCER),
    EFL_LEAGUE_TWO(Sport.SOCCER),
    PREMIER_LEAGUE(Sport.SOCCER),
    FA_CUP(Sport.SOCCER),
    FIFA_WORLD_CUP(Sport.SOCCER),
    LIGUE_ONE(Sport.SOCCER),
    LIGUE_TWO(Sport.SOCCER),
    BUNDESLIGA(Sport.SOCCER),
    BUNDESLIGA_TWO(Sport.SOCCER),
    SUPER_LEAGUE_GREECE(Sport.SOCCER),
    SERIE_A(Sport.SOCCER),
    SERIE_B(Sport.SOCCER),
    LEAGUE_OF_IRELAND(Sport.SOCCER),
    LIGA_MX(Sport.SOCCER),
    DUTCH_EREDEVISIE(Sport.SOCCER),
    PRIMEIRA_LIGA(Sport.SOCCER),
    LA_LIGA(Sport.SOCCER),
    LA_LIGA_TWO(Sport.SOCCER),
    SCOTTISH_PREMIERSHIP(Sport.SOCCER),
    ALLSVENSKAN(Sport.SOCCER),
    SWISS_SUPER_LEAGUE(Sport.SOCCER),
    UEFA_CHAMPIONS_LEAGUE(Sport.SOCCER),
    UEFA_EUROPA_LEAGUE(Sport.SOCCER),
    UEFA_EUROPA_CONFERENCE_LEAGUE(Sport.SOCCER),
    TURKEY_SUPER_LEAGUE(Sport.SOCCER),
    COPA_LIBERTADORES(Sport.SOCCER)
    ;

    private final Sport theSport;

    League(Sport theSport) {
        this.theSport = theSport;
    }

    public Sport getSport() {
        return theSport;
    }
}
