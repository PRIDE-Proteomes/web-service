package uk.ac.ebi.pride.proteomes.web.service.sample;

import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class TissueList {

    private Set<Tissue> tissueList;

    public TissueList() {
        this.tissueList = new TreeSet<Tissue>();
    }

    public TissueList(Comparator<Tissue> comparator) {
        this.tissueList = new TreeSet<Tissue>(comparator);
    }

    public TissueList(Collection<Tissue> tissues) {
        this.tissueList = new TreeSet<Tissue>();
        this.tissueList.addAll(tissues);
    }

    public void add(Tissue tissue) {
        this.tissueList.add(tissue);
    }

    public void addAll(Collection<Tissue> tissues) {
        this.tissueList.addAll(tissues);
    }

    public Set<Tissue> getTissueList() {
        return tissueList;
    }

}
