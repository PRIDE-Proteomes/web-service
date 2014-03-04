package uk.ac.ebi.pride.proteomes.web.service.util.comparator;

import uk.ac.ebi.pride.proteomes.web.service.proteingroup.ProteinGroup;

import java.util.Comparator;

/**
 * User: ntoro
 * Date: 19/02/2014
 * Time: 14:15
 */
public class ProteinGroupComparator implements Comparator<ProteinGroup> {
    @Override
    public int compare(ProteinGroup proteinGroup, ProteinGroup proteinGroup2) {
        return proteinGroup.getId().compareTo(proteinGroup2.getId());
    }
}
