package uk.ac.ebi.pride.proteomes.web.service.protein;

import uk.ac.ebi.pride.proteomes.web.service.modification.HasModifications;
import uk.ac.ebi.pride.proteomes.web.service.modification.ModifiedLocation;
import uk.ac.ebi.pride.proteomes.web.service.peptide.LocatedPeptide;
import uk.ac.ebi.pride.proteomes.web.service.sample.HasTissues;
import uk.ac.ebi.pride.proteomes.web.service.sample.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.LocatedPeptideComparator;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.ModifiedLocationComparator;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
//@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class Protein implements HasTissues, HasModifications {

    private String accession = "";
    private int taxonID = -1;
    private String sequence = "";
    private String description = "";
    private Set<ModifiedLocation> modifiedLocations; // there may be more than one modification on one position!!
    private Set<Tissue> tissues;
    private Set<LocatedPeptide> peptides;
    // for web front-end only
    private String coverage; // not used any longer!
    private int[][] regions; // array of coverage regions represented by integer triplets [start pos, length, coverage value]

    public Protein() {
        modifiedLocations = new TreeSet<ModifiedLocation>(new ModifiedLocationComparator());
        peptides = new TreeSet<LocatedPeptide>(new LocatedPeptideComparator());
        tissues = new TreeSet<Tissue>();
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public int getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(int taxonID) {
        this.taxonID = taxonID;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public int[][] getRegions() {
        return regions;
    }

    public void setRegions(int[][] regions) {
        this.regions = regions;
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
        return this.tissues;
    }

    public void setTissues(Set<Tissue> tissues) {
        this.tissues = tissues;
    }

    public Set<LocatedPeptide> getPeptides() {
        return peptides;
    }

    public void setPeptides(Set<LocatedPeptide> peptides) {
        this.peptides = peptides;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Protein)) return false;

        Protein protein = (Protein) o;

        if (taxonID != protein.taxonID) return false;
        if (!accession.equals(protein.accession)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accession.hashCode();
        result = 31 * result + taxonID;
        return result;
    }
}
