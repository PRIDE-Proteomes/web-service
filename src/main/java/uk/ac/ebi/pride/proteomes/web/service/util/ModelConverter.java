package uk.ac.ebi.pride.proteomes.web.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.proteomes.db.core.api.assay.Assay;
import uk.ac.ebi.pride.proteomes.db.core.api.cluster.Cluster;
import uk.ac.ebi.pride.proteomes.db.core.api.modification.ModificationLocation;
import uk.ac.ebi.pride.proteomes.db.core.api.peptide.Peptiform;
import uk.ac.ebi.pride.proteomes.web.service.modification.Modification;
import uk.ac.ebi.pride.proteomes.web.service.modification.ModifiedLocation;
import uk.ac.ebi.pride.proteomes.web.service.peptide.Peptide;
import uk.ac.ebi.pride.proteomes.web.service.protein.Protein;
import uk.ac.ebi.pride.proteomes.web.service.proteingroup.ProteinGroup;
import uk.ac.ebi.pride.proteomes.web.service.sample.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.AssayComparator;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.ModifiedLocationComparator;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class ModelConverter {

    private static final Logger logger = LoggerFactory.getLogger(ModelConverter.class);



    public static Protein convertProtein(uk.ac.ebi.pride.proteomes.db.core.api.protein.Protein dbProtein, boolean withSequence) {

        Protein serviceProtein = new Protein();

        serviceProtein.setAccession(dbProtein.getProteinAccession());
        serviceProtein.setTaxonID(dbProtein.getTaxid());
        if (withSequence) {
            serviceProtein.setSequence(dbProtein.getSequence());
        }
        serviceProtein.setDescription(dbProtein.getDescription());

        // modifications would have to be mapped to protein positions and therefore are
        // retrieved from the protein, rather than being mapped from the peptide level
        serviceProtein.setModifiedLocations(convertModificationLocations(dbProtein.getModificationLocations()));
        serviceProtein.setTissues(convertTissues(dbProtein.getTissues()));

        return serviceProtein;
    }


    public static Peptide convertPeptide(uk.ac.ebi.pride.proteomes.db.core.api.peptide.Peptide peptide, boolean includeDetails) {

        Peptide convertedPeptide = new Peptide();

        convertedPeptide.setTaxonID(peptide.getTaxid());
        convertedPeptide.setSequence(peptide.getSequence());
        convertedPeptide.setId( getIdFromPeptideRepresentation(peptide.getPeptideRepresentation()) );
        convertedPeptide.setModifiedLocations( convertModificationLocations(peptide.getModificationLocations()) );

        if (peptide instanceof Peptiform)
            // this is not a symbolic peptide representation, this is an observed peptide variant
            convertedPeptide.setSymbolic(false);
        else
            convertedPeptide.setSymbolic(true);

        if (includeDetails) {
            convertedPeptide.setTissues( convertTissues(peptide.getTissues()) );
            convertedPeptide.setAssays( convertAssays(peptide.getAssays()) );
            convertedPeptide.setClusters( convertClusters(peptide.getClusters()) );

        }

        return convertedPeptide;
    }

    public static SortedSet<Tissue> convertTissues(Collection<uk.ac.ebi.pride.proteomes.db.core.api.param.Tissue> tissues) {
        SortedSet<Tissue> convertedTissues = new TreeSet<Tissue>();

        // try to map all the provided tissues to known Tissue types
        for (uk.ac.ebi.pride.proteomes.db.core.api.param.Tissue tissue : tissues) {
            Tissue temp = Tissue.getTissue( tissue.getCvTerm() );
            if (temp == Tissue.NONE || temp == Tissue.UNKNOWN) {
                logger.warn("Could not map tissue: " + tissue.getCvTerm() + " - " + tissue.getCvName());
            } else {
                convertedTissues.add(temp);
            }
        }

        return convertedTissues;
    }

    public static SortedSet<String> convertAssays(Collection<Assay> assays) {
        SortedSet<String> convertedAssays = new TreeSet<String>(new AssayComparator());
        for (Assay assay : assays) {
            convertedAssays.add(assay.getAssayAccession());
        }
        return convertedAssays;
    }

    public static SortedSet<String> convertClusters(Collection<Cluster> clusters) {
        SortedSet<String> convertedClusters = new TreeSet<String>(new AssayComparator());
        for (Cluster cluster : clusters) {
            convertedClusters.add(cluster.getClusterId().toString());
        }
        return convertedClusters;
    }

    public static SortedSet<ModifiedLocation> convertModificationLocations(Collection<ModificationLocation> modificationLocations) {
        SortedSet<ModifiedLocation> convertedMods = new TreeSet<ModifiedLocation>(new ModifiedLocationComparator());

        for (ModificationLocation location : modificationLocations) {
            Modification temp = Modification.getModification( location.getModId() );
            if (temp == Modification.NONE || temp == Modification.UNKNOWN) {
                logger.warn("Could not map modification: " + location.getModId());
            } else {
                convertedMods.add( new ModifiedLocation(location.getPosition(), temp) );
            }
        }

        return convertedMods;
    }

    public static ProteinGroup convertProteinGroup(uk.ac.ebi.pride.proteomes.db.core.api.protein.groups.ProteinGroup dbGroup) {

        ProteinGroup serviceProteinGroup = new ProteinGroup();

        serviceProteinGroup.setId(dbGroup.getId());
        serviceProteinGroup.setTaxonID(dbGroup.getTaxid());
        serviceProteinGroup.setDescription(dbGroup.getDescription());

        // add the member protein accession
        for (uk.ac.ebi.pride.proteomes.db.core.api.protein.Protein protein : dbGroup.getProteins()) {
            serviceProteinGroup.getMemberProteins().add(protein.getProteinAccession());
            serviceProteinGroup.getTissues().addAll( convertTissues(protein.getTissues()));
        }


        return serviceProteinGroup;
    }

    public static String getIdFromPeptideRepresentation(String representation) {
        // ToDo: this should use the official way of generating a peptiform ID
        return representation.replace(",", ":").replace("PMOD:", "");
    }

}
