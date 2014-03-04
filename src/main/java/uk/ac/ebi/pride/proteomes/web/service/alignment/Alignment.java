package uk.ac.ebi.pride.proteomes.web.service.alignment;

import uk.ac.ebi.pride.proteomes.web.service.proteingroup.AlignedProtein;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class Alignment {

    private List<AlignedProtein> proteins;
    private List<MisMatch> alignmentMisMatches;

    public Alignment() {
        this.proteins = new ArrayList<AlignedProtein>();
        this.alignmentMisMatches = new ArrayList<MisMatch>();
    }

    public List<AlignedProtein> getProteins() {
        return this.proteins;
    }

    public void setProteins(List<AlignedProtein> proteins) {
        this.proteins = proteins;
    }

    public List<MisMatch> getAlignmentMisMatches() {
        return this.alignmentMisMatches;
    }

    public void setAlignmentMisMatches(List<MisMatch> alignmentMisMatches) {
        this.alignmentMisMatches = alignmentMisMatches;
    }
}
