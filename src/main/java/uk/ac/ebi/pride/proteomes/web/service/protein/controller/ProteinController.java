package uk.ac.ebi.pride.proteomes.web.service.protein.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Modification;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Species;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.ProteomesService;
import uk.ac.ebi.pride.proteomes.web.service.ResourceNotFoundException;
import uk.ac.ebi.pride.proteomes.web.service.protein.Protein;
import uk.ac.ebi.pride.proteomes.web.service.protein.ProteinList;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;


/**
 * @author Florian Reisinger
 * @since 0.1
 */
@Api(value = "protein", description = "retrieve information about proteins", position = 1)
@Controller
@RequestMapping(value="protein")
public class ProteinController  extends ProteomesService {

    public static final Logger logger = LoggerFactory.getLogger(ProteinController.class);

    @Autowired
    DataRetriever dataRetriever;


    @ApiResponses( value = {
       @ApiResponse(code = 404, message = "No records found for provided parameters.")
     })
    @RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Protein getById(@PathVariable("id") String id,
                           @RequestParam(value = "includeDetails", defaultValue = "true") boolean includeDetails) {

        logger.info("Protein requested by id: " + id);
        long start = System.currentTimeMillis();
        Protein protein = dataRetriever.getProtein(id, includeDetails);
        logger.debug("Protein request by id took [ms]: " + (System.currentTimeMillis() - start));

        if (protein == null) {
            throw new ResourceNotFoundException("No records found for protein: " + id);
        }

        return protein;
    }


    @RequestMapping(value ="/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProteinList getProteinList(
            @RequestParam(value = "species", required = true, defaultValue = Species.defaultValue) String speciesName,
            @RequestParam(value = "tissue", required = false, defaultValue = Tissue.defaultValue) String tissueName,
            @RequestParam(value = "mod", required = false, defaultValue = Modification.defaultValue) String modName,
//            @RequestParam(value = "desc", defaultValue = "") String description,
            @RequestParam(value = "includeSequence", required = false, defaultValue = "false") boolean includeSequence,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "100") int pageSize) {

        logger.info("Protein: getProteinList request");
        ProteinList list = new ProteinList();

        Species species = Species.getFromString(speciesName);
        if (species == null) {
            // ToDo: proper error return!
            return null;
        }
        // since we are using paging, we cannot filter the results after retrieval, as we would only filter a part of the data
        // therefore we have to create queries that do all of the filtering and return the proper (paged) results,
        list.addAll( dataRetriever.getProteins(species.getTaxid(), tissueName, modName, includeSequence, page, pageSize));
//        list.addAll( dataRetriever.getProteins(species, description, includeSequence, page, pageSize));
        return list;
    }



    @RequestMapping(value ="/list/peptide/{sequence}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProteinList getProteinListForPeptide(@PathVariable("sequence") String sequence,
            @RequestParam(value = "species", required = true, defaultValue = Species.defaultValue) int species,
            @RequestParam(value = "includeSequence", defaultValue = "false") boolean includeSequence) {

        logger.info("Protein: getProteinListForPeptide request");
        ProteinList list = new ProteinList();
        list.addAll( dataRetriever.getProteinsByPeptideSequence(sequence, species, includeSequence));
        return list;
    }


    @RequestMapping(value ="/count", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Long getProteinCount(
            @RequestParam(value = "species", required = true, defaultValue = "9606") String speciesName,
            @RequestParam(value = "tissue", required = false, defaultValue = Tissue.defaultValue) String tissueName,
            @RequestParam(value = "mod", required = false, defaultValue = Modification.defaultValue) String modName
    ) {

        logger.info("Protein: getMappedProteinCount request");
        Species species = Species.getFromString(speciesName);
        if (species == null) {
            // ToDo: proper error return!
            return null;
        }
        return dataRetriever.countProteins(species.getTaxid(), tissueName, modName);
    }



}
