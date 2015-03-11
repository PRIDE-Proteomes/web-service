package uk.ac.ebi.pride.proteomes.web.service.modification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wordnik.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.pride.proteomes.web.service.modification.Modification;
import uk.ac.ebi.pride.proteomes.web.service.modification.ModificationList;
import uk.ac.ebi.pride.proteomes.web.service.modification.json.ModificationSerializer;
import uk.ac.ebi.pride.proteomes.web.service.util.DataRetriever;

import java.util.List;

/**
 * @author ntoro@ebi.ac.uk
 * @author florian@ebi.ac.uk
 * @since 1.0.1
 */
@Api(value = "modification", description = "retrieve available modification annotations", position = 5)
@Controller
@RequestMapping(value="mods")
public class ModificationController {

    public static final Logger logger = LoggerFactory.getLogger(ModificationController.class);

    private static final ObjectMapper defaultMapper = new ObjectMapper();
    private static final ObjectMapper customMapper = new ObjectMapper();
    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Modification.class, new ModificationSerializer());
        customMapper.registerModule(module);
    }

    @Autowired
    DataRetriever dataRetriever;


    @RequestMapping(value ="/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getModifications(
            @RequestParam(value = "detail", defaultValue = "false", required = false) boolean detail)
            throws JsonProcessingException {

        logger.info("Request for modification list" + (detail? " with details" : ""));
        List<Modification> modificationList = dataRetriever.getModifications();

        if (detail) {
            // use ObjectMapper configured with custom Modification Serializer
            return customMapper.writeValueAsString(new ModificationList(modificationList));
        } else {
            return defaultMapper.writeValueAsString(new ModificationList(modificationList));
        }
    }

}
