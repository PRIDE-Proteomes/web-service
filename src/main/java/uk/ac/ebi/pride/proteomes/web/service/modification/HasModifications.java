package uk.ac.ebi.pride.proteomes.web.service.modification;

import java.util.Collection;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public interface HasModifications {

    Collection<ModifiedLocation> getModifiedLocations();
}
