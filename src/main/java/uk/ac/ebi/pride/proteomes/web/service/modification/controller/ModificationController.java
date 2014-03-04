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

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "text/plain")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getDescription() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Modification Service Description\n");
        sb.append("\tAvailable services:\n");
        sb.append("\t\t/list\t\t\t\tto retrieve a list of supported modifications\n");
        sb.append("\n");
        return sb.toString();
    }


    @RequestMapping(value ="/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ModificationList getModifications() {
        return new ModificationList(dataRetriever.getModifications());
    }

}
