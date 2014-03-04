package uk.ac.ebi.pride.proteomes.web.service.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class SpeciesList {

    private Collection<Species> speciesList;


    public SpeciesList() {
        this.speciesList = new TreeSet<Species>();
    }

    public SpeciesList(Comparator<Species> comparator) {
        this.speciesList = new TreeSet<Species>(comparator);
    }

    public SpeciesList(Collection<Species> speciesList) {
        this.speciesList = new ArrayList<Species>(speciesList.size());
        this.speciesList.addAll(speciesList);
    }

    public void add(Species species) {
        this.speciesList.add(species);
    }

    public void addAll(Collection<Species> species) {
        this.speciesList.addAll(species);
    }

    public Collection<Species> getSpeciesList() {
        return speciesList;
    }

}
