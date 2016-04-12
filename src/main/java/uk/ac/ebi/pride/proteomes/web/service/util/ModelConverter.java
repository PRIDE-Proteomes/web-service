package uk.ac.ebi.pride.proteomes.web.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.proteomes.db.core.api.assay.Assay;
import uk.ac.ebi.pride.proteomes.db.core.api.cluster.Cluster;
import uk.ac.ebi.pride.proteomes.db.core.api.modification.ModificationLocation;
import uk.ac.ebi.pride.proteomes.db.core.api.peptide.Peptiform;
import uk.ac.ebi.pride.proteomes.db.core.api.protein.groups.GeneGroup;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Modification;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Species;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.feature.Feature;
import uk.ac.ebi.pride.proteomes.web.service.modification.ModifiedLocation;
import uk.ac.ebi.pride.proteomes.web.service.peptide.Peptide;
import uk.ac.ebi.pride.proteomes.web.service.protein.Protein;
import uk.ac.ebi.pride.proteomes.web.service.proteingroup.ProteinGroup;
import uk.ac.ebi.pride.proteomes.web.service.release.ReleaseSummary;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.AssayComparator;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.FeatureComparator;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.ModifiedLocationComparator;

import java.util.Collection;
import java.util.Set;
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
        serviceProtein.setName(dbProtein.getName());

        final Species byTaxid = Species.getByTaxid(dbProtein.getTaxid());
        if (byTaxid != null) {
            serviceProtein.setSpecies(byTaxid.getName());
        } else {
            serviceProtein.setSpecies("");
        }
        serviceProtein.setAlternativeName(dbProtein.getAlternativeName());
        serviceProtein.setGeneSymbol(dbProtein.getGeneSymbol());
        serviceProtein.setProteinEvidence(proteinEvidenceDescription(dbProtein.getEvidence()));

        Set<GeneGroup> geneGroups = dbProtein.getGeneGroups();
        if (geneGroups != null && !geneGroups.isEmpty()) {
            GeneGroup geneGroup = geneGroups.iterator().next();
            String geneId = geneGroup.getId();
            serviceProtein.getGenes().add(geneId);
        }

        // modifications would have to be mapped to protein positions and therefore are
        // retrieved from the protein, rather than being mapped from the peptide level
        serviceProtein.setModifiedLocations(convertModificationLocations(dbProtein.getModificationLocations()));
        serviceProtein.setTissues(convertTissues(dbProtein.getTissues()));
        serviceProtein.setFeatures(convertFeatures(dbProtein.getFeatures()));

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
            Modification temp = Modification.getModification(location.getModId());
            if (temp == Modification.NONE || temp == Modification.UNKNOWN) {
                logger.warn("Could not map modification: " + location.getModId());
            } else {
                convertedMods.add( new ModifiedLocation(location.getPosition(), temp) );
            }
        }

        return convertedMods;
    }

    public static SortedSet<Feature> convertFeatures(Collection<uk.ac.ebi.pride.proteomes.db.core.api.feature.Feature> features) {
        SortedSet<Feature> convertedFeatures = new TreeSet<Feature>(new FeatureComparator());
        for (uk.ac.ebi.pride.proteomes.db.core.api.feature.Feature feature : features) {
            Feature  serviceFeature = new Feature();
            serviceFeature.setId(feature.getFeatureId());
            serviceFeature.setStart(feature.getStartPosition());
            serviceFeature.setEnd(feature.getEndPosition());
            serviceFeature.setType(feature.getFeatureType().getCvName());

            convertedFeatures.add(serviceFeature);
        }
        return convertedFeatures;
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

    public static ReleaseSummary convertReleaseSummary(uk.ac.ebi.pride.proteomes.db.core.api.release.ReleaseSummary releaseSummary) {

        ReleaseSummary convertedReleaseSummary = new ReleaseSummary();

        convertedReleaseSummary.setTaxonID(releaseSummary.getId().getTaxid());
        convertedReleaseSummary.setReferenceDatabase(releaseSummary.getReferenceDatabase());
        convertedReleaseSummary.setReferenceDatabaseVersion(releaseSummary.getReferenceDatabaseVersion());
        convertedReleaseSummary.setNumPeptiforms(releaseSummary.getNumPeptiforms());
        convertedReleaseSummary.setNumPeptides(releaseSummary.getNumPeptides());
        convertedReleaseSummary.setNumProteins(releaseSummary.getNumProteins());
        convertedReleaseSummary.setNumIsoformProteins(releaseSummary.getNumIsoformProteins());
        convertedReleaseSummary.setNumCanonicalProteins(releaseSummary.getNumCanonicalProteins());
        convertedReleaseSummary.setNumGenes(releaseSummary.getNumGenes());
        convertedReleaseSummary.setNumMappedProteins(releaseSummary.getNumMappedProteins());
        convertedReleaseSummary.setNumMappedCanonicalProteins(releaseSummary.getNumMappedCanonicalProteins());
        convertedReleaseSummary.setNumMappedIsoformProteins(releaseSummary.getNumMappedIsoformProteins());
        convertedReleaseSummary.setNumMappedGenes(releaseSummary.getNumMappedGenes());
        convertedReleaseSummary.setNumMappedPeptidesToProteins(releaseSummary.getNumMappedPeptidesToProteins());
        convertedReleaseSummary.setNumMappedUniquePeptidesToProteins(releaseSummary.getNumMappedUniquePeptidesToProteins());
        convertedReleaseSummary.setNumMappedUniquePeptidesToIsoformProteins(releaseSummary.getNumMappedUniquePeptidesToIsoformProteins());
        convertedReleaseSummary.setNumMappedUniquePeptidesToCanonicalProteins(releaseSummary.getNumMappedUniquePeptidesToCanonicalProteins());
        convertedReleaseSummary.setNumMappedUniquePeptidesToGenes(releaseSummary.getNumMappedUniquePeptidesToGenes());
        convertedReleaseSummary.setNumMappedProteinsWithUniquePeptides(releaseSummary.getNumMappedProteinsWithUniquePeptides());
        convertedReleaseSummary.setNumMappedCanonicalProteinsWithUniquePeptides(releaseSummary.getNumMappedCanonicalProteinsWithUniquePeptides());
        convertedReleaseSummary.setNumMappedIsoformProteinsWithUniquePeptides(releaseSummary.getNumMappedIsoformProteinsWithUniquePeptides());
        convertedReleaseSummary.setNumMappedGenesWithUniquePeptides(releaseSummary.getNumMappedGenesWithUniquePeptides());
        convertedReleaseSummary.setNumMappedProteinsWithExpEvidenceAtTranscript(releaseSummary.getNumMappedProteinsWithExpEvidenceAtTranscript());
        convertedReleaseSummary.setNumMappedProteinsWithEvidenceInferredByHomology(releaseSummary.getNumMappedProteinsWithEvidenceInferredByHomology());
        convertedReleaseSummary.setNumMappedProteinWithEvidencePredicted(releaseSummary.getNumMappedProteinWithEvidencePredicted());
        convertedReleaseSummary.setNumMappedProteinWithEvidenceUncertain(releaseSummary.getNumMappedProteinWithEvidenceUncertain());

        return convertedReleaseSummary;
    }

    public static String getIdFromPeptideRepresentation(String representation) {
        // ToDo: this should use the official way of generating a peptiform ID
        return representation.replace(",", ":").replace("PMOD:", "");
    }

    private static String proteinEvidenceDescription(int proteinEvidence) {
        //TODO This information should be parse in the web service or stored properly in the DB.

        //Try to substitute the right description by value
        String aux = "";

            switch (proteinEvidence) {
                case 1:
                    aux = "Protein Level";
                    break;
                case 2:
                    aux = "Transcript Level";
                    break;
                case 3:
                    aux = "Inferred from Homology";
                    break;
                case 4:
                    aux = "Predicted";
                    break;
                case 5:
                    aux = "Uncertain";
                    break;
                default:
                    aux = "Unavailable";
                    //We return the unparsed String
            }

        return aux;
    }
}
