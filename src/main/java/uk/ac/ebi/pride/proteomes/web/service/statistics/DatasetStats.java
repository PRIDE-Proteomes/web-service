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
    @ApiModelProperty(value = "number of peptiforms in the dataset")
    private long peptiformCount;
    @ApiModelProperty(value = "number of mapped proteins in the dataset")
    private long mappedProteinCount;
    @ApiModelProperty(value = "number of mapped UniProt Entry groups in the dataset")
    private long mappedUpGroupCount;
    @ApiModelProperty(value = "number of mapped gene groups in the dataset")
    private long mappedGeneGroupCount;
    @ApiModelProperty(value = "total of proteins in the dataset")
    private long totalProteinCount;
    @ApiModelProperty(value = "total of UniProt Entry groups in the dataset")
    private long totalUpGroupCount;
    @ApiModelProperty(value = "total of gene groups in the dataset")
    private long totalGeneGroupCount;

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

    public long getMappedProteinCount() {
        return mappedProteinCount;
    }

    public void setMappedProteinCount(long mappedProteinCount) {
        this.mappedProteinCount = mappedProteinCount;
    }

    public long getPeptiformCount() {
        return peptiformCount;
    }

    public void setPeptiformCount(long peptiformCount) {
        this.peptiformCount = peptiformCount;
    }

    public long getMappedUpGroupCount() {
        return mappedUpGroupCount;
    }

    public void setMappedUpGroupCount(long mappedUpGroupCount) {
        this.mappedUpGroupCount = mappedUpGroupCount;
    }

    public long getMappedGeneGroupCount() {
        return mappedGeneGroupCount;
    }

    public void setMappedGeneGroupCount(long mappedGeneGroupCount) {
        this.mappedGeneGroupCount = mappedGeneGroupCount;
    }

    public long getTotalProteinCount() {
        return totalProteinCount;
    }

    public void setTotalProteinCount(long totalProteinCount) {
        this.totalProteinCount = totalProteinCount;
    }

    public long getTotalUpGroupCount() {
        return totalUpGroupCount;
    }

    public void setTotalUpGroupCount(long totalUpGroupCount) {
        this.totalUpGroupCount = totalUpGroupCount;
    }

    public long getTotalGeneGroupCount() {
        return totalGeneGroupCount;
    }

    public void setTotalGeneGroupCount(long totalGeneGroupCount) {
        this.totalGeneGroupCount = totalGeneGroupCount;
    }
}
