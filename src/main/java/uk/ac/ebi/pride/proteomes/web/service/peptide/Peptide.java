package uk.ac.ebi.pride.proteomes.web.service.peptide;

import uk.ac.ebi.pride.proteomes.web.service.modification.HasModifications;
import uk.ac.ebi.pride.proteomes.web.service.modification.ModifiedLocation;
import uk.ac.ebi.pride.proteomes.web.service.sample.HasTissues;
import uk.ac.ebi.pride.proteomes.web.service.sample.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.AssayComparator;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.ModifiedLocationComparator;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class Peptide implements HasTissues, HasModifications {

    private String id;
    private boolean symbolic;
    private String sequence;
    private int taxonID;
    private Set<ModifiedLocation> modifiedLocations;
    private Set<Tissue> tissues;
    private Set<String> assays;

    public Peptide() {
        this.modifiedLocations = new TreeSet<ModifiedLocation>(new ModifiedLocationComparator());
        this.tissues = new TreeSet<Tissue>();
        this.assays = new TreeSet<String>(new AssayComparator());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSymbolic() {
        return symbolic;
    }

    public void setSymbolic(boolean symbolic) {
        this.symbolic = symbolic;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public int getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(int taxonID) {
        this.taxonID = taxonID;
    }

    @Override
    public Set<ModifiedLocation> getModifiedLocations() {
        return modifiedLocations;
    }

    public void setModifiedLocations(Set<ModifiedLocation> modifiedLocations) {
        this.modifiedLocations = modifiedLocations;
    }

    @Override
    public Set<Tissue> getTissues() {
        return tissues;
    }

    public void setTissues(Set<Tissue> tissues) {
        this.tissues = tissues;
    }

    public Set<String> getAssays() {
        return assays;
    }

    public void setAssays(Set<String> assays) {
        this.assays = assays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Peptide)) return false;

        Peptide peptide = (Peptide) o;

        if (!id.equals(peptide.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
