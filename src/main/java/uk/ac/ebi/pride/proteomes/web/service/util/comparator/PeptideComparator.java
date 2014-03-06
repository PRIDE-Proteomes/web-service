package uk.ac.ebi.pride.proteomes.web.service.util.comparator;

import uk.ac.ebi.pride.proteomes.web.service.peptide.Peptide;

import java.util.Comparator;

/**
 * @author Florian Reisinger
 *         Noemi del Toro
 *         Date: 27/01/14
 * @since $version
 */
public class PeptideComparator implements Comparator<Peptide> {

    //TODO Add tests

    @Override
    public int compare(Peptide peptide, Peptide peptide2) {
        if (peptide == peptide2) return 0;

        if (peptide.equals(peptide2)) return 0;

        return peptide.getId().compareTo(peptide2.getId());
    }
}
