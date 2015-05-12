package uk.ac.ebi.pride.proteomes.web.service.statistics.controller;

import com.wordnik.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.pride.proteomes.web.service.sample.Species;
import uk.ac.ebi.pride.proteomes.web.service.statistics.DatasetStats;
import uk.ac.ebi.pride.proteomes.web.service.statistics.Statistics;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;

import java.util.List;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@Api(value = "stats", description = "retrieve basic statistics", position = 6)
@Controller
@RequestMapping(value="stats")
public class StatisticsController {

    public static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    DataRetriever dataRetriever;

    private Statistics statisticsCache = null;
    private long lastStatsUpdate;


    @RequestMapping(value ="/summary", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Statistics getSummary() {
        // ToDo: maybe there is a better way of updating the cache
        long hoursSinceUpdate = (System.currentTimeMillis() - lastStatsUpdate) / (3600000);
        // update the stats if we have no values yet, or every ~ 24 hours
        if (statisticsCache == null || hoursSinceUpdate > 24) {
            List<Species> speciesList;
            speciesList = dataRetriever.getSpecies(); // all currently supported species
//            speciesList = Arrays.asList(Species.values()); // all available species
            statisticsCache = generateStats(speciesList);
            lastStatsUpdate = System.currentTimeMillis();
            logger.info("Statistics updated!");
        }

        return statisticsCache;
    }

    private Statistics generateStats(List<Species> species) {
        // retrieve the species specific statistics
        Statistics tempStats = dataRetriever.getStatistics(species);

        // generate total counts for the retrieved species
        // (note: this may not be all available species)
        long peptiformCnt = 0;
        long mappedProteinCnt = 0;
        long mappedUpGroupCnt = 0;
        long mappedGeneGroupCnt = 0;
        long totalProteinCnt = 0;
        long totalUpGroupCnt = 0;
        long totalGeneGroupCnt = 0;
        for (DatasetStats stats : tempStats.getDatasetStatistics()) {
            peptiformCnt += stats.getPeptiformCount();
            mappedProteinCnt += stats.getMappedProteinCount();
            mappedUpGroupCnt += stats.getMappedUpGroupCount();
            mappedGeneGroupCnt += stats.getMappedGeneGroupCount();
            totalProteinCnt += stats.getTotalProteinCount();
            totalUpGroupCnt += stats.getTotalUpGroupCount();
            totalGeneGroupCnt += stats.getTotalGeneGroupCount();
        }
        // add the total counts as new DatasetStats
        DatasetStats allStats = new DatasetStats();
        allStats.setTaxid(1);
        allStats.setCommonName("All");
        allStats.setScientificName("aggregated statistics");
        allStats.setPeptiformCount(peptiformCnt);
        allStats.setMappedProteinCount(mappedProteinCnt);
        allStats.setMappedUpGroupCount(mappedUpGroupCnt);
        allStats.setMappedGeneGroupCount(mappedGeneGroupCnt);
        allStats.setTotalProteinCount(totalProteinCnt);
        allStats.setTotalUpGroupCount(totalUpGroupCnt);
        allStats.setTotalGeneGroupCount(totalGeneGroupCnt);

        // add the total to the stats
        tempStats.addDatasetStats(allStats);
        return tempStats;
    }


}
