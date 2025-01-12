package com.arber.api.impl.theoddsapi;

import com.arber.api.exception.SchemaMappingException;
import com.arber.datamodel.*;

public class TheOddsApiSchemaConverter {
    public static Sport mapGroupToSport(String aGroup) {
        if (aGroup == null) {
            return null;
        }

        return switch (aGroup.toLowerCase()) {
            case "soccer" -> Sport.SOCCER;
            case "basketball" -> Sport.BASKETBALL;
            case "american football" -> Sport.AMERICAN_FOOTBALL;
            case "tennis" -> Sport.TENNIS;
            case "horse racing" -> Sport.HORSE_RACING;
            case "cricket" -> Sport.CRICKET;
            case "baseball" -> Sport.BASEBALL;
            case "mixed martial arts" -> Sport.MMA;
            case "ice hockey" -> Sport.ICE_HOCKEY;
            case "rugby league" -> Sport.RUGBY;
            case "aussie rules" -> Sport.AUSTRALIAN_FOOTBALL;
            case "golf" -> Sport.GOLF;
            case "boxing" -> Sport.BOXING;
            default -> throw new SchemaMappingException(String.format("Unable to map Group '%s' to a Sport", aGroup));
        };
    }

    public static League mapTitleToLeague(String aTitle) {
        if (aTitle == null) {
            return null;
        }

        String myNormalizedTitle = aTitle.toLowerCase();

        return switch (myNormalizedTitle) {
            // American Football
            case "ncaaf" -> League.NCAAF;
            case "ncaaf championship winner" -> League.NCAAF;
            case "nfl" -> League.NFL;
            case "nfl super bowl winner" -> League.NFL_SUPER_BOWL;

            // Aussie Rules
            case "afl" -> League.AFL;

            // Baseball
            case "mlb world series winner" -> League.MLB;

            // Basketball
            case "nba" -> League.NBA;
            case "nba championship winner" -> League.NBA;
            case "basketball euroleague" -> League.EUROLEAGUE;
            case "nbl" -> League.NBL;
            case "ncaab", "ncaab championship winner" -> League.NCAAB;

            // Boxing
            case "boxing" -> League.BOXING;

            // Cricket
            case "big bash" -> League.BIG_BASH;
            case "test matches" -> League.TEST_MATCHES;
            case "international twenty20" -> League.INTERNATIONAL_TWENTY20;

            // Golf
            case "masters tournament winner" -> League.MASTERS;
            case "pga championship winner" -> League.PGA_CHAMPIONSHIP;
            case "the open winner" -> League.THE_OPEN_CHAMPIONSHIP;
            case "us open winner" -> League.US_OPEN_GOLF;

            // Ice Hockey
            case "nhl" -> League.NHL;
            case "nhl championship winner" -> League.NHL;
            case "hockeyallsvenskan" -> League.HOCKEY_ALLSVENSKAN;
            case "shl" -> League.SHL;

            // MMA
            case "mma" -> League.MMA;

            // Rugby
            case "nrl" -> League.NRL;

            // Soccer
            case "a-league" -> League.A_LEAGUE;
            case "austrian football bundesliga" -> League.AUSTRIAN_BUNDESLIGA;
            case "belgium first div" -> League.BELGIUM_FIRST_DIV;
            case "championship" -> League.EFL_CHAMPIONSHIP;
            case "efl cup" -> League.EFL_CUP;
            case "league 1" -> League.EFL_LEAGUE_ONE;
            case "league 2" -> League.EFL_LEAGUE_TWO;
            case "epl" -> League.PREMIER_LEAGUE;
            case "fa cup" -> League.FA_CUP;
            case "fifa world cup winner" -> League.FIFA_WORLD_CUP;
            case "ligue 1 - france" -> League.LIGUE_ONE;
            case "ligue 2 - france" -> League.LIGUE_TWO;
            case "bundesliga - germany" -> League.BUNDESLIGA;
            case "bundesliga 2 - germany" -> League.BUNDESLIGA_TWO;
            case "super league - greece" -> League.SUPER_LEAGUE_GREECE;
            case "serie a - italy" -> League.SERIE_A;
            case "serie b - italy" -> League.SERIE_B;
            case "league of ireland" -> League.LEAGUE_OF_IRELAND;
            case "liga mx" -> League.LIGA_MX;
            case "dutch eredivisie" -> League.DUTCH_EREDEVISIE;
            case "primeira liga - portugal" -> League.PRIMEIRA_LIGA;
            case "la liga - spain" -> League.LA_LIGA;
            case "la liga 2 - spain" -> League.LA_LIGA_TWO;
            case "premiership - scotland" -> League.SCOTTISH_PREMIERSHIP;
            case "allsvenskan - sweden" -> League.ALLSVENSKAN;
            case "swiss superleague" -> League.SWISS_SUPER_LEAGUE;
            case "uefa champions league" -> League.UEFA_CHAMPIONS_LEAGUE;
            case "uefa europa league" -> League.UEFA_EUROPA_LEAGUE;
            case "turkey super league" -> League.TURKEY_SUPER_LEAGUE;
            case "uefa europa conference league" -> League.UEFA_EUROPA_CONFERENCE_LEAGUE;
            default -> throw new SchemaMappingException(String.format("Unable to map Title '%s' to a League", aTitle));
        };
    }

    public static MarketType mapMarketKeyToMarketType(String aMarketKey) {
        return switch (aMarketKey.toLowerCase()) {
            case "h2h" -> MarketType.H2H;
            case "spreads" -> MarketType.SPREADS;
            case "totals" -> MarketType.TOTALS;
            case "outrights" -> MarketType.OUTRIGHTS;
            case "h2h_lay" -> MarketType.H2H_LAY;
            case "outrights_lay" -> MarketType.OUTRIGHTS_LAY;
            default -> throw new SchemaMappingException(String.format("Unable to map MarketKey '%s' to a MarketType", aMarketKey));
        };
    }
}
