package uk.ac.ebi.pride.proteomes.web.service.protein;

import uk.ac.ebi.pride.proteomes.web.service.util.comparator.ProteinComparator;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class ProteinList {

    private Set<Protein> proteinList;

    public ProteinList() {
        this(new ProteinComparator());
    }

    public ProteinList(Comparator<Protein> comparator) {
        this.proteinList = new TreeSet<Protein>(comparator);
    }

    public Collection<Protein> getProteinList() {
        return proteinList;
    }

    public void addAll(Collection<Protein> proteinList) {
        this.proteinList.addAll(proteinList);
    }

    public void add(Protein protein) {
        this.proteinList.add(protein);
    }
}
