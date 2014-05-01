package uk.ac.ebi.pride.proteomes.web.service.peptide;

import uk.ac.ebi.pride.proteomes.web.service.util.comparator.PeptideComparator;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since $version
 */
public class PeptideList<P extends Peptide> {

    private Set<P> peptideList;

    public PeptideList() {
        this(new PeptideComparator());
    }

    public PeptideList(Comparator<? super P> comparator) {
        this.peptideList = new TreeSet<P>(comparator);
    }

    public Set<P> getPeptideList() {
        return peptideList;
    }

    public void addAll(Collection<P> peptideList) {
        this.peptideList.addAll(peptideList);
    }

    public void add(P peptide) {
        this.peptideList.add(peptide);
    }
}
