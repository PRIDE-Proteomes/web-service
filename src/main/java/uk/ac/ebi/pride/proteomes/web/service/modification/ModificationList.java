package uk.ac.ebi.pride.proteomes.web.service.modification;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author  Noemi del Toro
 * @since 0.1
 */
public class ModificationList {

    private Set<Modification> modificationList;

    public ModificationList() {
        this.modificationList = new TreeSet<Modification>();
    }

    public ModificationList(Comparator<Modification> comparator) {
        this.modificationList = new TreeSet<Modification>(comparator);
    }

    public ModificationList(Collection<Modification> mods) {
        this.modificationList = new TreeSet<Modification>();
        this.modificationList.addAll(mods);
    }

    public void add(Modification modification) {
        this.modificationList.add(modification);
    }

    public void addAll(Collection<Modification> tissues) {
        this.modificationList.addAll(tissues);
    }

    public Set<Modification> getModificationList() {
        return modificationList;
    }

}
