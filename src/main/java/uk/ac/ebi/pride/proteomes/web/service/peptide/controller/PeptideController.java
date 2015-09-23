package uk.ac.ebi.pride.proteomes.web.service.peptide.controller;

import com.wordnik.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Modification;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Species;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.peptide.LocatedPeptide;
import uk.ac.ebi.pride.proteomes.web.service.peptide.Peptide;
import uk.ac.ebi.pride.proteomes.web.service.peptide.PeptideList;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.LocatedPeptideComparator;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@Api(value = "peptide", description = "retrieve information about peptides", position = 2)
@Controller
@RequestMapping(value="peptide")
public class PeptideController {


    public static final Logger logger = LoggerFactory.getLogger(PeptideController.class);


    @Autowired
    DataRetriever dataRetriever;


    @RequestMapping(value ="/{sequence}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public PeptideList getById(@PathVariable("sequence") String sequence,
            @RequestParam(value = "species", defaultValue = Species.defaultValue) int species,
            @RequestParam(value = "tissue", defaultValue = Tissue.defaultValue) String tissueName,
            @RequestParam(value = "mod", defaultValue = Modification.defaultValue) String modName,
            @RequestParam(value = "includeDetails", defaultValue = "true") boolean includeDetails) {

        PeptideList<Peptide> list = new PeptideList<Peptide>();
        list.addAll(dataRetriever.getPeptidesForSequence(sequence, species, tissueName, modName, includeDetails));
        return list;
    }



    @RequestMapping(value ="/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public PeptideList getPeptideList(
            @RequestParam(value = "species", defaultValue = "9606") int species,
            @RequestParam(value = "tissue", defaultValue = Tissue.defaultValue) String tissueName,
            @RequestParam(value = "mod", defaultValue = Modification.defaultValue) String modName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "100") int pageSize) {

        PeptideList<Peptide> list = new PeptideList<Peptide>();
        list.addAll(dataRetriever.getPeptides(species, tissueName, modName, page, pageSize));
//        list.addAll(dataRetriever.getPeptides(species, page, pageSize));
        return list;
    }



    @RequestMapping(value ="/list/protein/{acc}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public PeptideList getPeptideListForProtein(@PathVariable("acc") String acc,
            @RequestParam(value = "tissue", defaultValue = Tissue.defaultValue) String tissueName,
            @RequestParam(value = "mod", defaultValue = Modification.defaultValue) String modName) {

        PeptideList<LocatedPeptide> list = new PeptideList<LocatedPeptide>(new LocatedPeptideComparator());
        list.addAll(dataRetriever.getProteinPeptides(acc, tissueName, modName));
        return list;
    }




}
