package uk.ac.ebi.pride.proteomes.web.service.util.comparator;

import uk.ac.ebi.pride.proteomes.web.service.protein.Protein;

import java.util.Comparator;

/**
 * @author Florian Reisinger
 *         Date: 27/01/14
 * @since $version
 */
public class ProteinComparator implements Comparator<Protein> {

    @Override
    public int compare(Protein protein, Protein protein2) {
        if (protein == protein2) return 0;

        if (protein.equals(protein2)) return 0;
        Comparator<String> uniprotAccessionCmp = new UniprotAccessionComparator();
        return uniprotAccessionCmp.compare(protein.getAccession(), protein2.getAccession());
    }
}
