package uk.ac.ebi.pride.proteomes.web.service.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.pride.proteomes.web.service.sample.Species;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@ApiModel(value = "DatasetStats", description = "Basic statistics about a dataset (e.g. per species)")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetStats {

    @ApiModelProperty(value = "the species (taxon id) defining this dataset")
    private int taxid;
    @ApiModelProperty(value = "the species name")
    private String speciesName;
    @ApiModelProperty(value = "the number of proteins belonging to this dataset")
    private long proteinCount;
    @ApiModelProperty(value = "the number of peptiforms belonging to this dataset")
    private long peptiformCount;
    @ApiModelProperty(value = "the number of unique sequences belonging to this dataset")
    private long symbolicPeptideCount;

    public DatasetStats() {
    }

    public DatasetStats(int taxid) {
        this.taxid = taxid;
        this.speciesName = Species.getByTaxid(taxid).getName();
    }

    public DatasetStats(Species s) {
        this.taxid = s.getTaxid();
        this.speciesName = s.getName();
    }

    public int getTaxid() {
        return taxid;
    }

    public void setTaxid(int taxid) {
        this.taxid = taxid;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public long getProteinCount() {
        return proteinCount;
    }

    public void setProteinCount(long proteinCount) {
        this.proteinCount = proteinCount;
    }

    public long getPeptiformCount() {
        return peptiformCount;
    }

    public void setPeptiformCount(long peptiformCount) {
        this.peptiformCount = peptiformCount;
    }

    public long getSymbolicPeptideCount() {
        return symbolicPeptideCount;
    }

    public void setSymbolicPeptideCount(long symbolicPeptideCount) {
        this.symbolicPeptideCount = symbolicPeptideCount;
    }
}
