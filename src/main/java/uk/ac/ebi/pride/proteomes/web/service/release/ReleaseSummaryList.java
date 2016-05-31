package uk.ac.ebi.pride.proteomes.web.service.release;

import uk.ac.ebi.pride.proteomes.web.service.util.comparator.ReleaseSummaryComparator;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author ntoro
 * @since 07/04/2016 16:07
 */
public class ReleaseSummaryList {

    private Set<ReleaseSummary> releaseSummaryList;

    public ReleaseSummaryList() {
        this(new ReleaseSummaryComparator());
    }

    public ReleaseSummaryList(Comparator<ReleaseSummary> comparator) {
        this.releaseSummaryList = new TreeSet<ReleaseSummary>(comparator);
    }

    public Set<ReleaseSummary> getReleaseSummaryList() {
        return releaseSummaryList;
    }

    public void addAll(Collection<ReleaseSummary> releaseSummaryList) {
        this.releaseSummaryList.addAll(releaseSummaryList);
    }

    public void add(ReleaseSummary releaseSummary) {
        this.releaseSummaryList.add(releaseSummary);
    }
}
