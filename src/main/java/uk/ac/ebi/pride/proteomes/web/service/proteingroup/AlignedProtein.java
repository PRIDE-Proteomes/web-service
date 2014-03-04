package uk.ac.ebi.pride.proteomes.web.service.proteingroup;

import uk.ac.ebi.pride.proteomes.web.service.protein.Protein;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class AlignedProtein extends Protein {

    private int proteinLength;

    public AlignedProtein() {
    }

    public int getProteinLength() {
        return proteinLength;
    }

    public void setProteinLength(int proteinLength) {
        this.proteinLength = proteinLength;
    }

}
