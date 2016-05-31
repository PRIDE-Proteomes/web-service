package uk.ac.ebi.pride.proteomes.web.service.peptide;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.modification.HasModifications;
import uk.ac.ebi.pride.proteomes.web.service.modification.ModifiedLocation;
import uk.ac.ebi.pride.proteomes.web.service.sample.HasTissues;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.AssayComparator;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.ModifiedLocationComparator;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@ApiModel(value = "Peptide", description = "Information about a peptide")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Peptide implements HasTissues, HasModifications {

    private String id;
    @ApiModelProperty(value = "flag whether this peptide is 'symbolic' (represents a AA sequence only, no modifications)")
    private boolean symbolic;
    @ApiModelProperty(value = "the peptide's AA sequence")
    private String sequence;
    @ApiModelProperty(value = "the peptide's species (taxon id)")
    private int taxonID;
    @ApiModelProperty(value = "positioned modifications of the peptide (if any and if not symbolic)")
    private Set<ModifiedLocation> modifiedLocations;
    @ApiModelProperty(value = "a list of reported tissues")
    private Set<Tissue> tissues;
    @ApiModelProperty(value = "a list of assays the peptide has been reported in")
    // this should be deprecated, Proteomes data should be independent of Archive assays/projects
    // but needed for the current implementation
    private Set<String> assays;

    @ApiModelProperty(value = "a list of cluster the peptide has been reported in")
    // this should be deprecated, Proteomes data should be independent of Archive assays/projects
    // but needed for the current implementation
    private Set<String> clusters;

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

    public Set<String> getClusters() {
        return clusters;
    }

    public void setClusters(Set<String> clusters) {
        this.clusters = clusters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Peptide)) return false;

        Peptide peptide = (Peptide) o;

        return id.equals(peptide.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
