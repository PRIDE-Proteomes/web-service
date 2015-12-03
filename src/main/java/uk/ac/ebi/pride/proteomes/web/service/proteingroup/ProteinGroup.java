package uk.ac.ebi.pride.proteomes.web.service.proteingroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.sample.HasTissues;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.UniprotAccessionComparator;

import java.util.*;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@ApiModel(value = "ProteinGroup", description = "A protein group (for example proteins grouped by gene)")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProteinGroup implements HasTissues {

    private String id;
    @ApiModelProperty(value = "the species for this group (taxon id)")
    private int taxonID;
    @ApiModelProperty(value = "a description for the group")
    private String description;
    @ApiModelProperty(value = "a list of tissues (aggregated from the proteins/peptides associated to this group)")
    private Set<Tissue> tissues;
    @ApiModelProperty(value = "the proteins belonging to this group")
    private Set<String> memberProteins;
    @ApiModelProperty(value = "peptides unique to this group (not necessarily unique to a single protein!)")
    private Map<String, Set<String>> uniquePeptides;


    public ProteinGroup() {
        this.memberProteins = new TreeSet<String>(new UniprotAccessionComparator());
        this.tissues = new TreeSet<Tissue>();
        this.uniquePeptides = new TreeMap<String, Set<String>>(); // map of peptide sequences to a set of protein accessions
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(int taxonID) {
        this.taxonID = taxonID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Set<Tissue> getTissues() {
        return tissues;
    }

    public void setTissues(Set<Tissue> tissues) {
        this.tissues = tissues;
    }

    public Set<String> getMemberProteins() {
        return memberProteins;
    }

    public void setMemberProteins(Set<String> memberProteins) {
        this.memberProteins = memberProteins;
    }

    public Map<String, Set<String>> getUniquePeptides() {
        return uniquePeptides;
    }

    public void setUniquePeptides(Map<String, Set<String>> uniquePeptides) {
        this.uniquePeptides = uniquePeptides;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProteinGroup)) return false;

        ProteinGroup that = (ProteinGroup) o;

        if (taxonID != that.taxonID) return false;
        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + taxonID;
        return result;
    }
}
