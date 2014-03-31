package uk.ac.ebi.pride.proteomes.web.service.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.pride.proteomes.web.service.sample.SpeciesList;
import uk.ac.ebi.pride.proteomes.web.service.sample.TissueList;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
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

    @RequestMapping(value ="/tissues", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TissueList getTissues() {
        return new TissueList(dataRetriever.getTissues());
    }

    // ToDo: add other services, like disease and cell type


}
