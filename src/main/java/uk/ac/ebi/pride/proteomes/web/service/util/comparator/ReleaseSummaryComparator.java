package uk.ac.ebi.pride.proteomes.web.service.util.comparator;

import uk.ac.ebi.pride.proteomes.web.service.release.ReleaseSummary;

import java.util.Comparator;

/**
 * @author Florian Reisinger
 *         Noemi del Toro
 *         Date: 27/01/14
 * @since $version
 */
public class ReleaseSummaryComparator implements Comparator<ReleaseSummary> {

    //TODO Add tests

    @Override
    public int compare(ReleaseSummary releaseSumary, ReleaseSummary releaseSummary2) {
        if (releaseSumary == releaseSummary2) return 0;

        if (releaseSumary.equals(releaseSummary2)) return 0;

        return releaseSumary.getTaxonID().compareTo(releaseSummary2.getTaxonID());
    }
}
