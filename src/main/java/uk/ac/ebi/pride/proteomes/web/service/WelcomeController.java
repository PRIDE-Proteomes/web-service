package uk.ac.ebi.pride.proteomes.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
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

//    TODO Fix the redirection
    @ApiIgnore
    @RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
    protected String gotoIndex() throws Exception {
        return "forward:/swagger-ui.html";
    }

    @ApiIgnore
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
