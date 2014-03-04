package uk.ac.ebi.pride.proteomes.web.service.statistics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.pride.proteomes.web.service.statistics.Statistics;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@Controller
@RequestMapping(value="stats")
public class StatisticsController {

    public static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    DataRetriever dataRetriever;

    private Statistics statisticsCache = null;
    private long lastStatsUpdate;


    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "text/plain")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getDescription() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Statistics Service Description\n");
        sb.append("\tAvailable services:\n");
        sb.append("\t\t/summary\t\t\t\tto retrieve counts of proteins and peptides\n");
        sb.append("\n");
        return sb.toString();
    }


    @RequestMapping(value ="/summary", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Statistics getSummary() {
        // ToDo: maybe there is a better way of updating the cache
        long hoursSinceUpdate = (System.currentTimeMillis() - lastStatsUpdate) / (3600000);
        // update the stats if we have no values yet, or every ~ 24 hours
        if (statisticsCache == null || hoursSinceUpdate > 24) {
            statisticsCache = dataRetriever.getStatistics(dataRetriever.getSpecies());
            lastStatsUpdate = System.currentTimeMillis();
            logger.info("Statistics updated!");
        }

        return statisticsCache;
    }


}
