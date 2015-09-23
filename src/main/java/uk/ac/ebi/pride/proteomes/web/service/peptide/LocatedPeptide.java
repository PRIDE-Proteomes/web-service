package uk.ac.ebi.pride.proteomes.web.service.peptide;

import com.wordnik.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.Uniqueness;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class LocatedPeptide extends Peptide  {

    @ApiModelProperty(value = "the position of the peptide on the mapped protein")
    private Integer position = -1;

    @ApiModelProperty(value = "represent the most restricted uniqueness level")
    private Integer uniqueness = Uniqueness.NON_UNIQUE.ordinal() ;

    @ApiModelProperty(value = "protein accessions shared with this peptide")
    private Set<String> sharedProteins = new HashSet<String>();

    @ApiModelProperty(value = "protein entry accessions shared with this peptide")
    private Set<String> sharedUpEntries = new HashSet<String>();

    @ApiModelProperty(value = "gene accessions shared with this peptide")
    private Set<String> sharedGenes = new HashSet<String>();

    public LocatedPeptide(Peptide peptide) {
        this.setId(peptide.getId());
        this.setSymbolic(peptide.isSymbolic()); // mapped peptides are usually symbolic!
        this.setTaxonID(peptide.getTaxonID());
        this.setSequence(peptide.getSequence());
        this.setTissues(peptide.getTissues());
        this.setAssays(peptide.getAssays());
        this.setClusters(peptide.getClusters());
        this.setModifiedLocations(peptide.getModifiedLocations());
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getUniqueness() {
        return uniqueness;
    }

    public void setUniqueness(Integer uniqueness) {
        this.uniqueness = uniqueness;
    }

    public Set<String> getSharedProteins() {
        return sharedProteins;
    }

    public void setSharedProteins(Set<String> sharedProteins) {
        this.sharedProteins = sharedProteins;
    }

    public Set<String> getSharedUpEntries() {
        return sharedUpEntries;
    }

    public void setSharedUpEntries(Set<String> sharedUpEntries) {
        this.sharedUpEntries = sharedUpEntries;
    }

    public Set<String> getSharedGenes() {
        return sharedGenes;
    }

    public void setSharedGenes(Set<String> sharedGenes) {
        this.sharedGenes = sharedGenes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocatedPeptide)) return false;
        if (!super.equals(o)) return false;

        LocatedPeptide that = (LocatedPeptide) o;

        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

}
