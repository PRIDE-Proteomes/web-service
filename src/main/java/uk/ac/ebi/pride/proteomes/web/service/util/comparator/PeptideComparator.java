package uk.ac.ebi.pride.proteomes.web.service.util.comparator;

import uk.ac.ebi.pride.proteomes.web.service.peptide.Peptide;

import java.util.Comparator;

/**
 * @author Florian Reisinger
 *         Date: 27/01/14
 * @since $version
 */
public class PeptideComparator implements Comparator<Peptide> {

    @Override
    public int compare(Peptide peptide, Peptide peptide2) {
        if (peptide == peptide2) return 0;

        if (peptide.equals(peptide2)) return 0;

        return peptide.getSequence().compareTo(peptide2.getSequence());
    }
}
