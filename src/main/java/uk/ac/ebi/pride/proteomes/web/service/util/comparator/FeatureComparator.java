package uk.ac.ebi.pride.proteomes.web.service.util.comparator;

import uk.ac.ebi.pride.proteomes.web.service.feature.Feature;

import java.util.Comparator;

/**
 * @author Florian Reisinger
 *         Noemi del Toro
 *         Date: 27/01/14
 * @since $version
 */
public class FeatureComparator implements Comparator<Feature> {

    //TODO Add tests

    @Override
    public int compare(Feature feature, Feature feature2) {
        if (feature == feature2) return 0;

        if (feature.equals(feature2)) return 0;

        return feature.getId().compareTo(feature2.getId());
    }
}
