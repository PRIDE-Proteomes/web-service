package uk.ac.ebi.pride.proteomes.web.service.proteingroup.controller;

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
@Controller
@RequestMapping(value="group")
public class ProteinGroupController extends ProteomesService {

    public static final Logger logger = LoggerFactory.getLogger(ProteinGroupController.class);

    @Autowired
    DataRetriever dataRetriever;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String getDescription() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Protein Group Service Description\n");
        sb.append("\tAvailable services:\n");
        sb.append("\t\t/help\t\t\t\tto show a HTML help page\n");
        sb.append("\t\t/<id>\t\t\t\tto query for a protein group with its ID\n");
        sb.append("\t\t/list\t\t\t\tto list all protein groups\n");
        sb.append("\t\t/list/protein/<accession>\tto list all groups for the specified protein\n");
        sb.append("\tQueries can be restricted or customised applying filters and setting parameters:\n");
//        sb.append("\t\ttissue=<tissue>\t\t\ta string to limit for a specific tissue type (only a limited set of tissues is currently supported: blood, liver, brain)\n");
        sb.append("\t\tspecies=<NCBI taxon ID>\t\tan integer to limit for a specific species (only 9606 (human) and 10090 (mouse))\n");
        sb.append("\t\tuniquePeptides=<true|false>\ta boolean to indicate whether the result should contain a list of peptides unique to the group\n");
        sb.append("\t\tpage=<integer>\t\t\tan integer (zero based) to specify the page of the paged result\n");
        sb.append("\t\tpageSize=<integer>\t\tan integer (zero based) to specify the pageSize (number of results per page) of the paged result\n");
        sb.append("\tFilters available per service:\n");
        sb.append("\t\t/help\t\t\t\tno filters\n");
        sb.append("\t\t/<id>\t\t\t\tincludeUniquePeptides\n");
        sb.append("\t\t/list\t\t\t\tspecies, page, pageSize\n");
        sb.append("\t\t/list/protein/<accession>\tno filters\n");
        sb.append("\tExamples:\n");
        sb.append("\t\t/group/Q08648?includeUniquePeptides=true\n");
        sb.append("\t\t/group/list?species=10090\n");
//        sb.append("\t\t/group/list?tissue=blood&species=9606\n");
        sb.append("\n");
        return sb.toString();
    }


    @RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ProteinGroup getById(@PathVariable("id") String id,
                                @RequestParam(value = "uniquePeptides", defaultValue = "true") boolean uniquePeptides) {

        System.out.println("ProteinGroup " + id + " requested.");

        ProteinGroup group = dataRetriever.getProteinGroupById(id, uniquePeptides);

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
