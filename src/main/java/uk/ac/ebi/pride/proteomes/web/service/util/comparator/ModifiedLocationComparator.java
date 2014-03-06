package uk.ac.ebi.pride.proteomes.web.service.util.comparator;

import uk.ac.ebi.pride.proteomes.web.service.modification.ModifiedLocation;

import java.util.Comparator;

/**
 * @author Florian Reisinger
 *         Date: 27/01/14
 * @since $version
 */
public class ModifiedLocationComparator implements Comparator<ModifiedLocation> {

    //TODO Add tests

    @Override
    public int compare(ModifiedLocation modifiedLocation, ModifiedLocation modifiedLocation2) {
        if (modifiedLocation == modifiedLocation2) return 0;

        if (modifiedLocation.equals(modifiedLocation2)) return 0;

        Integer comparison = modifiedLocation.getPosition().compareTo(modifiedLocation2.getPosition());

        if (comparison == 0) {
            return modifiedLocation.getModification().compareTo(modifiedLocation2.getModification());
        }
        return comparison;
    }

}
