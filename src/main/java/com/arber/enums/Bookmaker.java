package com.arber.enums;

import java.util.HashMap;
import java.util.Map;

public enum Bookmaker {
    // US Bookmakers
    BETONLINEAG("BetOnline.ag", "betonlineag", Region.US),
    BETMGM("BetMGM", "betmgm", Region.US),
    BETRIVERS("BetRivers", "betrivers", Region.US),
    BETUS("BetUS", "betus", Region.US),
    BOVADA("Bovada", "bovada", Region.US),
    WILLIAMHILL("Caesars", "williamhill_us", Region.US),
    DRAFTKINGS("DraftKings", "draftkings", Region.US),
    FANDUEL("FanDuel", "fanduel", Region.US),
    LOWVIG("LowVig.ag", "lowvig", Region.US),
    MYBOOKIEAG("MyBookie.ag", "mybookieag", Region.US),

    // US2 Bookmakers
    BALLYBET("Bally Bet", "ballybet", Region.US2),
    BETANYSPORTS_US2("BetAnySports", "betanysports", Region.US2),
    BETPARX("betPARX", "betparx", Region.US2),
    ESPNBET("ESPN BET", "espnbet", Region.US2),
    FLIFF("Fliff", "fliff", Region.US2),
    HARDROCKBET("Hard Rock Bet", "hardrockbet", Region.US2),
    WINDCREEK("Wind Creek (Betfred PA)", "windcreek", Region.US2),

    // UK Bookmakers
    SPORT888_UK("888sport", "sport888", Region.UK),
    BETFAIR_EX_UK("Betfair Exchange", "betfair_ex_uk", Region.UK),
    BETFAIR_SB_UK("Betfair Sportsbook", "betfair_sb_uk", Region.UK),
    BETVICTOR_UK("Bet Victor", "betvictor", Region.UK),
    BETWAY("Betway", "betway", Region.UK),
    BOYLESPORTS("BoyleSports", "boylesports", Region.UK),
    CASUMO("Casumo", "casumo", Region.UK),
    CORAL("Coral", "coral", Region.UK),
    GROSVENOR("Grosvenor", "grosvenor", Region.UK),
    LADBROKES_UK("Ladbrokes", "ladbrokes_uk", Region.UK),
    LEOVEGAS("LeoVegas", "leovegas", Region.UK),
    LIVESCOREBET_UK("LiveScore Bet", "livescorebet", Region.UK),
    MATCHBOOK_UK("Matchbook", "matchbook", Region.UK),
    PADDYPOWER("Paddy Power", "paddypower", Region.UK),
    SKYBET("Sky Bet", "skybet", Region.UK),
    SMARKETS("Smarkets", "smarkets", Region.UK),
    UNIBET_UK("Unibet", "unibet_uk", Region.UK),
    VIRGINBET("Virgin Bet", "virginbet", Region.UK),
    WILLIAMHILL_UK("William Hill (UK)", "williamhill", Region.UK),

    // EU Bookmakers
    ONEXBET("1xBet", "onexbet", Region.EU),
    SPORT888_EU("888sport", "sport888", Region.EU),
    BETCLIC("Betclic", "betclic", Region.EU),
    BETANYSPORTS_EU("BetAnySports", "betanysports", Region.EU),
    BETFAIR_EX_EU("Betfair Exchange", "betfair_ex_eu", Region.EU),
    BETONLINEAG_EU("BetOnline.ag", "betonlineag", Region.EU),
    BETSSON("Betsson", "betsson", Region.EU),
    BETVICTOR_EU("Bet Victor", "betvictor", Region.EU),
    COOLBET("Coolbet", "coolbet", Region.EU),
    EVERYGAME("Everygame", "everygame", Region.EU),
    GTBETS("GTbets", "gtbets", Region.EU),
    LIVESCOREBET_EU("Livescorebet (EU)", "livescorebet_eu", Region.EU),
    MARATHONBET("Marathon Bet", "marathonbet", Region.EU),
    MATCHBOOK_EU("Matchbook", "matchbook", Region.EU),
    MYBOOKIEAG_EU("MyBookie.ag", "mybookieag", Region.EU),
    NORDICBET("NordicBet", "nordicbet", Region.EU),
    PINNACLE("Pinnacle", "pinnacle", Region.EU),
    SUPRABETS("Suprabets", "suprabets", Region.EU),
    TIPICO_DE("Tipico (DE)", "tipico_de", Region.EU),
    UNIBET_EU("Unibet", "unibet_eu", Region.EU),
    WILLIAMHILL_EU("William Hill", "williamhill", Region.EU),

    // AU Bookmakers
    BETFAIR_EX_AU("Betfair Exchange", "betfair_ex_au", Region.AU),
    BETR_AU("Betr", "betr_au", Region.AU),
    BETRIGHT("Bet Right", "betright", Region.AU),
    LADBROKES_AU("Ladbrokes", "ladbrokes_au", Region.AU),
    NEDS("Neds", "neds", Region.AU),
    PLAYUP("PlayUp", "playup", Region.AU),
    POINTSBET_AU("PointsBet (AU)", "pointsbetau", Region.AU),
    SPORTSBET("SportsBet", "sportsbet", Region.AU),
    TAB("TAB", "tab", Region.AU),
    TABTOUCH("TABtouch", "tabtouch", Region.AU),
    TOPSPORT("TopSport", "topsport", Region.AU),
    UNIBET_AU("Unibet", "unibet", Region.AU);

    private final String theBookmakerName;
    private final String theBookmakerKey;
    private final Region theRegion;

    private static final Map<String, Bookmaker> theBookmakerKeyMap = new HashMap<>();

    static {
        for (Bookmaker myType : Bookmaker.values()) {
            theBookmakerKeyMap.put(myType.getBookmakerKey(), myType);
        }
    }

    Bookmaker(String aBookmakerName, String aBookmakerKey, Region aBookmakerRegion) {
        this.theBookmakerName = aBookmakerName;
        this.theBookmakerKey = aBookmakerKey;
        this.theRegion = aBookmakerRegion;
    }

    public final String getBookmakerName() {
        return theBookmakerName;
    }

    public final String getBookmakerKey() {
        return theBookmakerKey;
    }

    public final Region getRegion() {
        return theRegion;
    }

    public static Bookmaker fromBookmakerKey(String aBookmakerKey) {
        return theBookmakerKeyMap.get(aBookmakerKey);
    }
}
