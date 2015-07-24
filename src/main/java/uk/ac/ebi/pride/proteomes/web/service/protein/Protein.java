package uk.ac.ebi.pride.proteomes.web.service.protein;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.pride.proteomes.web.service.feature.Feature;
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
@ApiModel(value = "Protein", description = "Information about a protein")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Protein implements HasTissues, HasModifications {

    @ApiModelProperty(value = "the protein's accession")
    private String accession = "";
    @ApiModelProperty(value = "the protein's associated gene")
    private String gene = "";
    @ApiModelProperty(value = "the protein's species (taxon id)")
    private int taxonID = -1;
    @ApiModelProperty(value = "the protein's sequence")
    private String sequence = "";
    @ApiModelProperty(value = "the protein's description")
    private String description = "";
    @ApiModelProperty(value = "the protein's reported modifications")
    private Set<ModifiedLocation> modifiedLocations; // there may be more than one modification on one position!!
    @ApiModelProperty(value = "the protein's reported tissue annotations")
    private Set<Tissue> tissues;
    @ApiModelProperty(value = "the protein's reported peptides")
    private Set<LocatedPeptide> peptides;
    @ApiModelProperty(value = "the protein's reported features")
    private Set<Feature> features;
    @ApiModelProperty(value = "number of peptides unique to this protein")
    private int uniquePeptideCount = -1;
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

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
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

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public int getUniquePeptideCount() {
        return uniquePeptideCount;
    }

    public void setUniquePeptideCount(int uniquePeptideCount) {
        this.uniquePeptideCount = uniquePeptideCount;
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
