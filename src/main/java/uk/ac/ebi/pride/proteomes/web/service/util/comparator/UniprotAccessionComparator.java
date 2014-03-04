package uk.ac.ebi.pride.proteomes.web.service.util.comparator;

import java.util.Comparator;

/**
 * @author Florian Reisinger
 *         Date: 27/01/14
 * @since $version
 */
public class UniprotAccessionComparator implements Comparator<String> {

    @Override
        public int compare(String s1, String s2) {
            int comparation = s1.compareTo(s2);

            if(comparation == 0) return 0;

            // We assume the protein names have the format <string>(-<integer>)
            // If the string don't follow this formatting some operations will
            // throw an exception, for example, if a string is empty or what
            // is after the dash is not an integer.

            String[] ss1 = s1.split("-");
            String[] ss2 = s2.split("-");

            // Compare the bases
            comparation = ss1[0].compareTo(ss2[0]);
            if(comparation != 0) return comparation;

            // The base is the same, now we should compare the rest.
            // A protein without a number is less than one with it.
            if(ss1.length == 1) return -1;
            if(ss2.length == 1) return 1;

            Integer one = Integer.parseInt(ss1[1]);
            Integer two = Integer.parseInt(ss2[1]);
            return one.compareTo(two);
        }

}
