package uk.ac.ebi.pride.proteomes.web.service.util.param;

import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Modification;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.modification.HasModifications;
import uk.ac.ebi.pride.proteomes.web.service.modification.ModifiedLocation;
import uk.ac.ebi.pride.proteomes.web.service.sample.HasTissues;

/**
 * @author ntoro
 * @since 20/08/15 09:05
 */
public class ParamHelper {


    /**
     * A utility method that checks that the provided Object carries the specified Modification.
     *
     * @param object        an Object that implements HasModifications.
     * @param modIdentifier the String of the modification to check for.
     * @param <T>           Any class that implements the HasModifications interface.
     * @return true if the Object contains the specified Modification, false otherwise. Note: if the Modification is null, false will be returned.
     */
    public static <T extends HasModifications> boolean containsModification(T object, String modIdentifier) {

        if (object == null) {
            return false;
        }

        Modification modification = Modification.getModification(modIdentifier);

        // if we have no modification information, we match
        if (modification == Modification.NONE) {
            return true;
        }

        // if the modification is unknown or not present in the object, we fail the check
        if (modification == Modification.UNKNOWN) {
            return false;
        } // else criteria fulfilled, so we continue to other checks

        for (ModifiedLocation modifiedLocation : object.getModifiedLocations()) {
            // object carries the required modification
            if(modifiedLocation.getModification().equals(modification)){
                return true;
            }
        }
        return false;
    }

    /**
     * A utility method that checks that the provided Object carries the specified Tissue.
     *
     * @param object           an Object that implements HasTissues.
     * @param tissueIdentifier the String of the tissue to check for.
     * @param <T>              Any class that implements the HasTissues interface.
     * @return true if the Object contains the specified Tissue, false otherwise. Note: if the Tissue is null, false will be returned.
     */
    public static <T extends HasTissues> boolean containsTissue(T object, String tissueIdentifier) {

        if (object == null) {
            return false;
        }

        Tissue tissue = Tissue.getTissue(tissueIdentifier);

        // if we have no tissue information, we match
        if (tissue == Tissue.NONE) {
            return true;
        }

        // if the tissue is unknown or not present in the object, we fail the check
        if (tissue == Tissue.UNKNOWN || !object.getTissues().contains(tissue)) {
            return false;
        } // else criteria fulfilled, so we continue to other checks

        // object carries the required tissue
        return true;
    }
}
