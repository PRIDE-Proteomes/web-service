package uk.ac.ebi.pride.proteomes.web.service.statistics;

import uk.ac.ebi.pride.proteomes.web.service.sample.Species;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class DatasetStats {

    private int taxid;
    private String speciesName;
    private long proteinCount;
    private long peptiformCount;
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
