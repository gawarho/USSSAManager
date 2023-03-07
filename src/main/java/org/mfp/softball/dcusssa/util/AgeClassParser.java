package org.mfp.softball.dcusssa.util;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gawarho on 2/21/18.
 */
public class AgeClassParser {
    /**
     * parse the ageClass values -- AACC (##CC)
     *
     * @param ageClass
     * @return
     */
    public static Pair<String, String> parseAgeClass(String ageClass) {
        Pattern ageClassPat = Pattern.compile("(\\d+)(\\D+)");
        Matcher m = ageClassPat.matcher(ageClass);
        Pair<String, String> retVal = new ImmutablePair<>(ageClass, ageClass);
        if(m.find()) {
            retVal = new ImmutablePair<>(m.group(1), m.group(2));
        }

        return retVal;
    }
}
