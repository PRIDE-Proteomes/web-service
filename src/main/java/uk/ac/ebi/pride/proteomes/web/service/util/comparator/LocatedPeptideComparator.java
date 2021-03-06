package uk.ac.ebi.pride.proteomes.web.service.util.comparator;

import uk.ac.ebi.pride.proteomes.web.service.peptide.LocatedPeptide;

import java.util.Comparator;

/**
 * @author Florian Reisinger
 *         Date: 27/01/14
 * @since $version
 */
public class LocatedPeptideComparator implements Comparator<LocatedPeptide> {

    //TODO Add tests

    @Override
    public int compare(LocatedPeptide locatedPeptide, LocatedPeptide locatedPeptide2) {
        if (locatedPeptide == locatedPeptide2) return 0;

        if (locatedPeptide.equals(locatedPeptide2)) return 0;

        Integer comparison = locatedPeptide.getId().compareTo(locatedPeptide2.getId());
        if (comparison == 0) {
            return locatedPeptide.getPosition().compareTo(locatedPeptide2.getPosition());
        }

        return comparison;
    }
}
