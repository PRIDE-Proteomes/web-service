package uk.ac.ebi.pride.proteomes.web.service.util.comparator;

import java.util.Comparator;

/**
 * User: ntoro
 * Date: 19/02/2014
 * Time: 14:59
 */
public class AssayComparator implements Comparator<String> {

    //TODO Add tests
    @Override
    public int compare(String s1, String s2) {
        if (s1.equals(s2)) return 0;
        return Integer.valueOf(s1).compareTo(Integer.valueOf(s2));
    }
}
