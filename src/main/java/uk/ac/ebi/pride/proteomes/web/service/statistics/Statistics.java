package uk.ac.ebi.pride.proteomes.web.service.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@ApiModel(value = "Statistics", description = "Basic statistics PRIDE Proteomes data")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Statistics {

    @ApiModelProperty(value = "list of statistics for each species (one dataset per species)")
    private Set<DatasetStats> datasetStatistics;
    @ApiModelProperty(value = "total protein count in Proteomes")
    private long proteinCount;
    @ApiModelProperty(value = "total peptiform count in Proteomes")
    private long peptiformCount;
    @ApiModelProperty(value = "total unique peptides (species specific) count in Proteomes")
    private long symbolicPeptideCount;


    public Statistics() {
        // create a set that allows only one DatasetStats entry per species
        this.datasetStatistics = new TreeSet<DatasetStats>(new Comparator<DatasetStats>() {
            @Override
            public int compare(DatasetStats o1, DatasetStats o2) {
                return Integer.compare(o1.getTaxid(), o2.getTaxid());
            }
        });
    }


    @ApiModelProperty(value = "total species (e.g. dataset) count in Proteomes")
    public int getSpeciesCount() {
        return datasetStatistics.size();
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


    public long sumProteinCount() {
        long cnt = 0;
        for (DatasetStats stats : datasetStatistics) {
            cnt += stats.getProteinCount();
        }
        return cnt;
    }

    public long sumPeptiformCount() {
        long cnt = 0;
        for (DatasetStats stats : datasetStatistics) {
            cnt += stats.getPeptiformCount();
        }
        return cnt;
    }

    public long sumSymbolicPeptideCount() {
        long cnt = 0;
        for (DatasetStats stats : datasetStatistics) {
            cnt += stats.getSymbolicPeptideCount();
        }
        return cnt;
    }

    public boolean addDatasetStats(DatasetStats stats) {
        return this.datasetStatistics.add(stats);
    }

    public void setDatasetStatistics(Set<DatasetStats> stats) {
        this.datasetStatistics.clear();
        this.datasetStatistics.addAll(stats);
    }

    public Set<DatasetStats> getDatasetStatistics() {
        return datasetStatistics;
    }

    public DatasetStats getDatasetStatistics(int taxid) {
        for (DatasetStats stats : datasetStatistics) {
            if (stats.getTaxid() == taxid) {
                return stats;
            }
        }
        return null;
    }


}
