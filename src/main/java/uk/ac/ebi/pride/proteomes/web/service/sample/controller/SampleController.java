package uk.ac.ebi.pride.proteomes.web.service.sample.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Species;
import uk.ac.ebi.pride.proteomes.web.service.sample.SpeciesList;
import uk.ac.ebi.pride.proteomes.web.service.sample.TissueList;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@Api(value = "sample", description = "retrieve available sample annotations", position = 4)
@Controller
@RequestMapping(value="sample")
public class SampleController {

    public static final Logger logger = LoggerFactory.getLogger(SampleController.class);


    @Autowired
    DataRetriever dataRetriever;


    @RequestMapping(value ="/species", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SpeciesList getSpecies() {
        return new SpeciesList(dataRetriever.getSpecies());
    }

    @RequestMapping(value ="/tissues/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TissueList getTissues() {
        return new TissueList(dataRetriever.getTissues());
    }

    @RequestMapping(value ="/tissues/list/species/{species}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TissueList getTissues(@RequestParam(value = "species", required = false, defaultValue = Species.defaultValue) String speciesName
    ) {
        Species species = Species.getFromString(speciesName);
        return new TissueList(dataRetriever.getTissues(species));
    }

    @RequestMapping(value ="/tissues/count/species/{species}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public int countTissues(@RequestParam(value = "species", required = false, defaultValue = Species.defaultValue) String speciesName
    ) {

        Species species = Species.getFromString(speciesName);
        return dataRetriever.countTissue(species);
    }

    // ToDo: add other services, like disease and cell type


}
