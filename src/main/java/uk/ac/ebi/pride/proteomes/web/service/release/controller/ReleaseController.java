package uk.ac.ebi.pride.proteomes.web.service.release.controller;

import com.wordnik.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Species;
import uk.ac.ebi.pride.proteomes.web.service.release.ReleaseSummary;
import uk.ac.ebi.pride.proteomes.web.service.release.ReleaseSummaryList;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;

/**
 * @author ntoro
 * @since 07/04/2016 16:06
 */
@Api(value = "release", description = "retrieve information about the release process", position = 7)
@Controller
@RequestMapping(value = "release")
public class ReleaseController {


    public static final Logger logger = LoggerFactory.getLogger(ReleaseController.class);

    @Autowired
    DataRetriever dataRetriever;

    @RequestMapping(value = "/summary/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReleaseSummaryList getReleaseSummary() {

        logger.info("Release: getReleaseSummaryList request");
        ReleaseSummaryList list = new ReleaseSummaryList();
        list.addAll(dataRetriever.getReleaseSummary());
        return list;
    }

    @RequestMapping(value = "/summary/species/${species}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReleaseSummary getReleaseSummaryForSpecies(@RequestParam(value = "species", required = false, defaultValue = Species.defaultValue) String speciesName){

        Species species = Species.getFromString(speciesName);
        logger.info("Release: getReleaseSummaryForSpecies request");
        return dataRetriever.getReleaseSummary(species);
    }
}
