package org.mfp.softball.dcusssa.util;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by gawarho on 2/21/18.
 */
public class EntryValidator {
    /**
     * verify team is right class and right age to play in C or RAS tournaments
     *
     * @param teamAge
     * @param teamClass
     * @param divisionAge
     * @param divisionClass
     * @return
     */
    public static Pair<Boolean, String> validateTeamInTournament(String teamAge, String teamClass, String divisionAge, String divisionClass) {
        return validateTeamInTournament(Long.parseLong(teamAge), teamClass, Long.parseLong(divisionAge), divisionClass);
    }


    /**
     * verify team is right class and right age to play in C or RAS tournaments
     *
     * @param teamAge
     * @param teamClass
     * @param divisionAge
     * @param divisionClass
     * @return
     */
    public static Pair<Boolean, String> validateTeamInTournament(long teamAge, String teamClass, long divisionAge, String divisionClass) {
        Pair<Boolean, String>  retVal = new ImmutablePair<>(true, "");
        if(divisionClass.equals("RAS")) {
            if(teamClass.equals("C") || teamClass.equals("B") || teamClass.equals("A") || teamClass.equals("O") || teamClass.equals("")) {
                retVal = new ImmutablePair<>(false, "RAS tournament but team is higher level");
            }

            if(divisionAge == 18) {
                if(teamAge <15) {
                    retVal = new ImmutablePair<>(false, "Team playing up in the RAS Division");
                }
            } else {
                long age = teamAge;
                if((teamAge %2) == 1) {
                    age++;
                }
                if(age < divisionAge) {
                    retVal = new ImmutablePair<>(false, "Team playing up in the RAS Division");
                }
            }
        } else if(divisionClass.equals("C")) {
            if(teamClass.equals("B") || teamClass.equals("A") || teamClass.equals("O") || teamClass.equals("")) {
                retVal = new ImmutablePair<>(false, "C tournament but team is higher level");
            }

            if(divisionAge == 18) {
                if(teamAge <15) {
                    retVal = new ImmutablePair<>(false, "Team playing up in the C Division");
                }
            } else {
                long age = teamAge;
                if((teamAge %2) == 1) {
                    age++;
                }
                if(age < divisionAge) {
                    retVal = new ImmutablePair<>(false, "Team playing up in the C Division");
                }
            }
        }

        return retVal;
    }



}
