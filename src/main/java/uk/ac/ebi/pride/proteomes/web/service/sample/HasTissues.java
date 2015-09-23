package uk.ac.ebi.pride.proteomes.web.service.sample;

import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;

import java.util.Collection;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public interface HasTissues {

    Collection<Tissue> getTissues();

}
