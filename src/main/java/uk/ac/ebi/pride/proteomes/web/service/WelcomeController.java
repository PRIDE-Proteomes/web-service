package uk.ac.ebi.pride.proteomes.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;

/**
 * @author Florian Reisinger
 * @since 0.2
 */
@Controller
public class WelcomeController extends ProteomesService {

    public static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @Autowired
    DataRetriever dataRetriever;

    @RequestMapping(value ="/", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Description of the PRIDE Proteomes web service.\n");
        sb.append("Available end points are :\n");
        sb.append("\t/protein\t to query for proteins\n");
        sb.append("\t/peptide\t to query for peptides\n");
        sb.append("\t/group\t\t to query for protein groups\n");
        sb.append("\t/sample\t\t to retrieve known tissue annotations\n");
        sb.append("\t/mods\t\t to retrieve supported modifications\n");
        sb.append("\t/stats\t\t to retrieve protein/peptide statistics\n");
        sb.append("\t\n");

        return sb.toString();
    }


    @RequestMapping(value ="/check", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String getQuickCheck() {

        boolean allOK = dataRetriever.isAllOK();

        StringBuilder sb = new StringBuilder();
        sb.append("PRIDE Proteomes Web Service check: ");
        sb.append(allOK ? "OK" : "FAILED");
        sb.append("\n");

        return sb.toString();
    }



}
