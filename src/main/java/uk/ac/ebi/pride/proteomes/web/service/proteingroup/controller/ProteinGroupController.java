package uk.ac.ebi.pride.proteomes.web.service.proteingroup.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.pride.proteomes.web.service.ProteomesService;
import uk.ac.ebi.pride.proteomes.web.service.ResourceNotFoundException;
import uk.ac.ebi.pride.proteomes.web.service.proteingroup.ProteinGroup;
import uk.ac.ebi.pride.proteomes.web.service.proteingroup.ProteinGroupList;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@Api(value = "proteinGroup", description = "retrieve information about proteinGroups", position = 3)
@Controller
@RequestMapping(value="group")
public class ProteinGroupController extends ProteomesService {

    public static final Logger logger = LoggerFactory.getLogger(ProteinGroupController.class);

    @Autowired
    DataRetriever dataRetriever;


    @RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ProteinGroup getById(@PathVariable("id") String id,
                                @RequestParam(value = "uniquePeptides", defaultValue = "true") boolean uniquePeptides) {

        logger.debug("ProteinGroup " + id + " requested.");

        long start = System.currentTimeMillis();
        ProteinGroup group = dataRetriever.getProteinGroupById(id, uniquePeptides);
        logger.debug("ProteinGroup retrieval took [ms]: " + (System.currentTimeMillis() - start));

        if (group == null) {
            throw new ResourceNotFoundException("No records found for protein group: " + id);
        }

        return group;
    }


    @RequestMapping(value ="/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ProteinGroupList getProteinGroupList(
            @RequestParam(value = "species", defaultValue = "9606") int species,
//            @RequestParam(value = "tissue", defaultValue = Tissue.defaultValue) String tissueName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "100") int pageSize
            ) throws Exception {

        ProteinGroupList list = new ProteinGroupList();
//        list.addAll(dataRetriever.getProteinGroups(species, tissueName, page, pageSize));
        list.addAll(dataRetriever.getProteinGroups(species, page, pageSize));
        return list;
    }


    @RequestMapping(value ="/list/protein/{acc}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ProteinGroupList getProteinGroupListForProtein(@PathVariable("acc") String acc
            ) throws Exception {

        ProteinGroupList list = new ProteinGroupList();
        list.addAll(dataRetriever.getProteinGroupsForProtein(acc));
        return list;
    }



}
