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
@SuppressWarnings("unused")
@ApiModel(value = "Statistics", description = "Basic statistics PRIDE Proteomes data")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Statistics {

    @ApiModelProperty(value = "list of statistics for each species (e.g. dataset)")
    private Set<DatasetStats> datasetStatistics;


    public Statistics() {
        // create a set that allows only one DatasetStats entry per species
        this.datasetStatistics = new TreeSet<DatasetStats>(new Comparator<DatasetStats>() {
            @Override
            public int compare(DatasetStats o1, DatasetStats o2) {
                return (o1.getScientificName().compareToIgnoreCase(o2.getScientificName()));
            }
        });
    }


    @ApiModelProperty(value = "total species (e.g. dataset) count in Proteomes")
    public int getSpeciesCount() {
        // it's the number of datasets -1 (since we have a 'ALL' species)
        return datasetStatistics.size()-1;
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
