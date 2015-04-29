package uk.ac.ebi.pride.proteomes.web.service.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.pride.proteomes.web.service.sample.Species;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@SuppressWarnings("unused")
@ApiModel(value = "DatasetStats", description = "Basic statistics about a dataset (e.g. per species)")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetStats {

    @ApiModelProperty(value = "species taxon id defining the dataset (1 = all species)")
    private int taxid;
    @ApiModelProperty(value = "common species name")
    private String commonName;
    @ApiModelProperty(value = "scientific species name")
    private String scientificName;
    @ApiModelProperty(value = "number of proteins in the dataset")
    private long proteinCount;
    @ApiModelProperty(value = "number of peptiforms in the dataset")
    private long peptiformCount;
    @ApiModelProperty(value = "number of UniProt Entry groups in the dataset")
    private long upGroupCount;
    @ApiModelProperty(value = "number of gene groups in the dataset")
    private long geneGroupCount;

    public DatasetStats() {
    }

    public DatasetStats(int taxid) {
        this(Species.getByTaxid(taxid));
    }

    public DatasetStats(Species s) {
        this.taxid = s.getTaxid();
        this.scientificName = s.getScientificName();
        this.commonName = s.getCommonName();
    }

    public int getTaxid() {
        return taxid;
    }

    public void setTaxid(int taxid) {
        this.taxid = taxid;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
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

    public long getUpGroupCount() {
        return upGroupCount;
    }

    public void setUpGroupCount(long upGroupCount) {
        this.upGroupCount = upGroupCount;
    }

    public long getGeneGroupCount() {
        return geneGroupCount;
    }

    public void setGeneGroupCount(long geneGroupCount) {
        this.geneGroupCount = geneGroupCount;
    }
}
