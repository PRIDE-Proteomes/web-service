package uk.ac.ebi.pride.proteomes.web.service.proteingroup;

import uk.ac.ebi.pride.proteomes.web.service.util.comparator.ProteinGroupComparator;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class ProteinGroupList {

    private Set<ProteinGroup> proteinGroupList;

    public ProteinGroupList() {
        this(new ProteinGroupComparator());
    }

    public ProteinGroupList(Comparator<ProteinGroup> comparator) {
        this.proteinGroupList = new TreeSet<ProteinGroup>(comparator);
    }

    public Set<ProteinGroup> getProteinGroupList() {
        return proteinGroupList;
    }

    public void addAll(Collection<ProteinGroup> proteinGroupList) {
        this.proteinGroupList.addAll(proteinGroupList);
    }

    public void add(ProteinGroup proteinGroup) {
        this.proteinGroupList.add(proteinGroup);
    }
}
