package uk.ac.ebi.pride.proteomes.web.service.modification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.pride.proteomes.web.service.modification.ModificationList;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;

/**
 * User: ntoro
 * Date: 19/02/2014
 * Time: 16:58
 */
@Controller
@RequestMapping(value="mods")
public class ModificationController {

    public static final Logger logger = LoggerFactory.getLogger(ModificationController.class);


    @Autowired
    DataRetriever dataRetriever;


    @RequestMapping(value ="/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ModificationList getModifications() {
        return new ModificationList(dataRetriever.getModifications());
    }

}
