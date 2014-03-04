package uk.ac.ebi.pride.proteomes.web.service.statistics;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class Statistics {

    private Set<DatasetStats> datasetStatistics;
    private long proteinCount;
    private long peptiformCount;
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
