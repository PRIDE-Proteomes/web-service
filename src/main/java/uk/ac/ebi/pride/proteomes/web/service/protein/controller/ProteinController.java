package uk.ac.ebi.pride.proteomes.web.service.protein.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.pride.proteomes.web.service.ProteomesService;
import uk.ac.ebi.pride.proteomes.web.service.ResourceNotFoundException;
import uk.ac.ebi.pride.proteomes.web.service.modification.Modification;
import uk.ac.ebi.pride.proteomes.web.service.protein.Protein;
import uk.ac.ebi.pride.proteomes.web.service.protein.ProteinList;
import uk.ac.ebi.pride.proteomes.web.service.sample.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;


/**
 * @author Florian Reisinger
 * @since 0.1
 */
@Controller
@RequestMapping(value="protein")
public class ProteinController  extends ProteomesService {

    public static final Logger logger = LoggerFactory.getLogger(ProteinController.class);

    @Autowired
    DataRetriever dataRetriever;


    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "text/plain")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getDescription() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Protein Service Description\n");
        sb.append("\tAvailable services:\n");
        sb.append("\t\t/help\t\t\t\tto show a HTML help page\n");
        sb.append("\t\t/<id>\t\t\t\tto query for a protein with its UniProt accession number\n");
        sb.append("\t\t/list\t\t\t\tto list all proteins\n");
        sb.append("\t\t/list/peptide/<sequence>\tto list all proteins that list a particular peptide (using the peptides AA sequence (capital case!))\n");
        sb.append("\t\t/count\t\t\t\tto count all proteins\n");
        sb.append("\tQueries can be restricted or customised applying filters and setting parameters:\n");
        sb.append("\t\tspecies=<NCBI taxon ID>\t\tan integer to limit for a specific species (9606 (human), 10090 (mouse), 10116 (rat), 3702 (arabidopsis))\n");
        sb.append("\t\ttissue=<tissue>\t\t\ta string to limit for a specific BTO tissue term (only a limited set of tissues is currently supported: blood, liver, brain)\n");
        sb.append("\t\tdesc=<string>\t\t\ta keyword to match against the protein's description\n");
        sb.append("\t\tmod=<modification type>\t\ta keyword to limit by pride modification type\n");
        sb.append("\t\tincludeDetails=<true|false>\ta boolean to indicate whether the result should contain additional protein details (peptides, tissues)\n");
        sb.append("\t\tincludeSequence=<true|false>\ta boolean to indicate whether the result should contain the protein sequence or not\n");
        sb.append("\t\tpage=<integer>\t\t\tan integer (zero based) to specify the page of the paged result\n");
        sb.append("\t\tpageSize=<integer>\t\tan integer (zero based) to specify the pageSize (number of results per page) of the paged result\n");
        sb.append("\tFilters available per service:\n");
        sb.append("\t\t/help\t\t\t\tno filters\n");
        sb.append("\t\t/<id>\t\t\t\tincludeDetails (default: true)\n");
        sb.append("\t\t/list\t\t\t\tspecies (default 9606), desc, includeSequence (default: false), page (default: 0), pageSize (default: 100)\n");
        sb.append("\t\t/list/peptide/<sequence>\tspecies (default 9606), includeSequence (default: false)\n");
        sb.append("\t\t/count\t\t\t\tspecies (default 9606), desc\n");
        sb.append("\tExamples:\n");
        sb.append("\t\t/protein/Q08648\n");
        sb.append("\t\t/protein/list?species=9606&page=3&pageSize=20\n");
        sb.append("\t\t/protein/list?species=9606&tissue=BTO:0000089\n");
        sb.append("\t\t/protein/list?species=9606&tissue=BTO:0000089&mod=4\n");
        sb.append("\t\t/protein/list/peptide/MQQGICR\n");
        sb.append("\t\t/protein/list/peptide/MQQGICR?includeSequence=true\n");
        sb.append("\t\t/protein/count?species=10090&desc=kinase\n");
        sb.append("\n");
        // ToDo: perhaps add explanation of what values are allowed for each filter
        return sb.toString();
    }


    @RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Protein getById(@PathVariable("id") String id,
                           @RequestParam(value = "includeDetails", defaultValue = "true") boolean includeDetails) {

        Protein protein = dataRetriever.getProtein(id, includeDetails);

        if (protein == null) {
            throw new ResourceNotFoundException("No records found for protein: " + id);
        }

        return protein;
    }


    @RequestMapping(value ="/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProteinList getProteinList(
            @RequestParam(value = "species", defaultValue = "9606") int species,
            @RequestParam(value = "tissue", defaultValue = Tissue.defaultValue) String tissueName,
            @RequestParam(value = "mod", defaultValue = Modification.defaultValue) String modName,
//            @RequestParam(value = "desc", defaultValue = "") String description,
            @RequestParam(value = "includeSequence", defaultValue = "false") boolean includeSequence,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "100") int pageSize) {

        ProteinList list = new ProteinList();
        // since we are using paging, we cannot filter the results after retrieval, as we would only filter a part of the data
        // therefore we have to create queries that do all of the filtering and return the proper (paged) results,
        list.addAll( dataRetriever.getProteins(species, tissueName, modName, includeSequence, page, pageSize));
//        list.addAll( dataRetriever.getProteins(species, description, includeSequence, page, pageSize));
        return list;
    }



    @RequestMapping(value ="/list/peptide/{sequence}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProteinList getProteinListForPeptide(@PathVariable("sequence") String sequence,
            @RequestParam(value = "species", defaultValue = "9606") int species,
            @RequestParam(value = "includeSequence", defaultValue = "false") boolean includeSequence) {

        ProteinList list = new ProteinList();
        list.addAll( dataRetriever.getProteinsByPeptideSequence(sequence, species, includeSequence));
        return list;
    }


    @RequestMapping(value ="/count", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getProteinCount(
            @RequestParam(value = "species", defaultValue = "9606") int species,
            @RequestParam(value = "desc", defaultValue = "") String description) {

        return dataRetriever.getProteinCount(species, description);
    }



}
