package uk.ac.ebi.pride.proteomes.web.service.peptide.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.pride.proteomes.web.service.modification.Modification;
import uk.ac.ebi.pride.proteomes.web.service.peptide.LocatedPeptide;
import uk.ac.ebi.pride.proteomes.web.service.peptide.Peptide;
import uk.ac.ebi.pride.proteomes.web.service.peptide.PeptideList;
import uk.ac.ebi.pride.proteomes.web.service.sample.Species;
import uk.ac.ebi.pride.proteomes.web.service.sample.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.LocatedPeptideComparator;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@Controller
@RequestMapping(value="peptide")
public class PeptideController {


    public static final Logger logger = LoggerFactory.getLogger(PeptideController.class);


    @Autowired
    DataRetriever dataRetriever;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Peptide Service Description\n");
        sb.append("\tAvailable services:\n");
        sb.append("\t\t/help\t\t\t\tto show a HTML help page\n");
        sb.append("\t\t/<sequence>\t\t\tto query for a peptide with its AA sequence (capital case!)\n");
        sb.append("\t\t/list\t\t\t\tto list all peptides\n");
        sb.append("\t\t/list/protein/<accession>\tto list all peptides for a particular protein (using its UniProt accession)\n");
        sb.append("\tQueries can be restricted or customised applying filters and setting parameters:\n");
        sb.append("\t\ttissue=<tissue>\t\t\ta string to limit for a specific BTO tissue term (only a limited set of tissues is currently supported: blood, liver, brain)\n");
        sb.append("\t\tspecies=<NCBI taxon ID>\t\tan integer to limit for a specific species (only 9606 (human) and 10090 (mouse))\n");
        sb.append("\t\tmod=<modification type>\t\ta keyword to limit by pride modification type\n");
        sb.append("\t\tpage=<integer>\t\t\tan integer (zero based) to specify the page of the paged result\n");
        sb.append("\t\tpageSize=<integer>\t\tan integer (zero based) to specify the pageSize (number of results per page) of the paged result\n");
        sb.append("\t\tincludeDetails=<true/false>\tincludes additional details, tissues and assay numbers, for each peptide\n");
        sb.append("\tFilters available per service:\n");
        sb.append("\t\t/help\t\t\t\tno filters\n");
        sb.append("\t\t/<sequence>\t\t\tspecies (default all species), tissue, mod, includeDetails (default true)\n");
        sb.append("\t\t/list\t\t\t\tspecies (default 9606), page, pageSize\n");
        sb.append("\t\t/list/protein/<accession>\ttissue, mod\n");
        sb.append("\tExamples:\n");
        sb.append("\t\t/peptide/MQQGICR\n");
        sb.append("\t\t/peptide/list?tissue=brain\n");
        sb.append("\t\t/peptide/list?species=10090&page=100&pageSize=30\n");
        sb.append("\t\t/peptide/list?species=9606&tissue=BTO:0000089&mod=4\n");
        sb.append("\t\t/peptide/list/protein/Q08648-4\n");
        sb.append("\n");
        return sb.toString();
    }


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
