package uk.ac.ebi.pride.proteomes.web.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.pride.proteomes.db.core.api.param.CvParamProteomesRepository;
import uk.ac.ebi.pride.proteomes.db.core.api.peptide.PeptideRepository;
import uk.ac.ebi.pride.proteomes.db.core.api.peptide.Peptiform;
import uk.ac.ebi.pride.proteomes.db.core.api.peptide.SymbolicPeptide;
import uk.ac.ebi.pride.proteomes.db.core.api.peptide.group.PeptideGroup;
import uk.ac.ebi.pride.proteomes.db.core.api.peptide.group.PeptideGroupRepository;
import uk.ac.ebi.pride.proteomes.db.core.api.peptide.protein.PeptideProtein;
import uk.ac.ebi.pride.proteomes.db.core.api.peptide.protein.PeptideProteinRepository;
import uk.ac.ebi.pride.proteomes.db.core.api.protein.ProteinRepository;
import uk.ac.ebi.pride.proteomes.db.core.api.protein.groups.ProteinGroupRepository;
import uk.ac.ebi.pride.proteomes.web.service.modification.Modification;
import uk.ac.ebi.pride.proteomes.web.service.peptide.LocatedPeptide;
import uk.ac.ebi.pride.proteomes.web.service.peptide.Peptide;
import uk.ac.ebi.pride.proteomes.web.service.protein.Protein;
import uk.ac.ebi.pride.proteomes.web.service.proteingroup.ProteinGroup;
import uk.ac.ebi.pride.proteomes.web.service.sample.Species;
import uk.ac.ebi.pride.proteomes.web.service.sample.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.statistics.DatasetStats;
import uk.ac.ebi.pride.proteomes.web.service.statistics.Statistics;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.UniprotAccessionComparator;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * @author Florian Reisinger
 * @since 0.2
 */
@Controller
@Transactional(readOnly = true)
public class DataRetriever {

    public static final Logger logger = LoggerFactory.getLogger(DataRetriever.class);

    @Autowired
    PeptideRepository peptideRepository;
    @Autowired
    ProteinRepository proteinRepository;
    @Autowired
    ProteinGroupRepository proteinGroupRepository;
    @Autowired
    PeptideProteinRepository peptideProteinRepository;
    @Autowired
    PeptideGroupRepository peptideGroupRepository;
    @Autowired
    CvParamProteomesRepository cvParamRepository;

    @Autowired
    DataSource proteomesDataSource;

    // check peptide annotations match with provided criteria
    // Note: All criteria have to be met. Species values < 0 are ignored
    private static boolean checkPeptideMatches(Peptide peptide, String tissueName, String modName, int species) {

        if (peptide == null) {
            return false;
        }

        // check that if a species has been given, it has to match
        if (species > 0 && peptide.getTaxonID() != species) {
            return false;
        }

        // check a tissue, if selected
        if (!Tissue.containsTissue(peptide, tissueName)) {
            return false;
        }

        // check a modification, if selected
        if (!Modification.containsModification(peptide, modName)) {
            return false;
        }

        // peptide passed all checks
        return true;
    }

/**
    public boolean isAllOKDetailed() {
        Pageable pageable = new PageRequest(0, 5); // we request the first page with few entries
        try {
            if (peptideRepository.findAll(pageable).iterator().next() == null) {
                return false;
            }

            if (proteinRepository.findAll(pageable).iterator().next() == null) {
                return false;
            }

            if (proteinGroupRepository.findAll(pageable).iterator().next() == null) {
                return false;
            }

            if (peptideProteinRepository.findAll(pageable).iterator().next() == null) {
                return false;
            }

            if (peptideGroupRepository.findAll(pageable).iterator().next() == null) {
                return false;
            }
        } catch (Exception e) {
            logger.error("Error trying to retrieve data for service check.");
            return false;
        }

        return true;
    }
*/

    public boolean isAllOK() {

        // request a page of results from each repository to check if the query returns results
        // return false if any query does not return results or if there is any exception

        boolean ok;
        Connection connection = null;
        try {
            connection = proteomesDataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            logger.debug("Retrieved meta-data for DB: " + metaData.getDatabaseProductName());
            ok = !connection.isClosed(); // after retrieving of the meta-data we should have a working connection!
        } catch (SQLException e) {
            ok = false;
            logger.error("SQLException during service check." , e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Failed to close connection during service check." , e);
                }
            }
        }

        return ok;

    }

    @Transactional(readOnly = true)
    public ProteinGroup getProteinGroupById(String id, boolean addUniquePeptides) {
        // ToDo: add all peptides and flag unique ones rather than listing only unique ones?
        uk.ac.ebi.pride.proteomes.db.core.api.protein.groups.ProteinGroup dbGroup = proteinGroupRepository.findById(id);

        if (dbGroup == null) {
            logger.warn("Could not find protein group " + id);
            return null;
        }

        ProteinGroup serviceGroup = ModelConverter.convertProteinGroup(dbGroup);
        // check if we should add a list of unique peptides mapped to their protein accessions
        if (addUniquePeptides) {
            List<uk.ac.ebi.pride.proteomes.db.core.api.peptide.Peptide> dbPeptides = findPeptideByProtein(id, true);
            for (uk.ac.ebi.pride.proteomes.db.core.api.peptide.Peptide dbPeptide : dbPeptides) {
                // we are only interested in peptides that are unique to this group (which may have multiple proteins)
                String peptideSequence = dbPeptide.getSequence();

                // populate a map of peptide sequences to their protein origins
                Set<String> tmpProteins = serviceGroup.getUniquePeptides().get(peptideSequence);
                if (tmpProteins == null) {
                    tmpProteins = new TreeSet<String>(new UniprotAccessionComparator());
                }
                // map the unique peptide to the protein(s) of the group
                for (uk.ac.ebi.pride.proteomes.db.core.api.protein.Protein protein : dbGroup.getProteins()) {
                    for (PeptideProtein tmpProtein : dbPeptide.getProteins()) {
                        if (tmpProtein.getProtein().equals(protein)) {
                            tmpProteins.add(protein.getProteinAccession());
                        }
                    }
                }
                serviceGroup.getUniquePeptides().put(peptideSequence, tmpProteins);
            }
        }
        return serviceGroup;
    }
    private List<uk.ac.ebi.pride.proteomes.db.core.api.peptide.Peptide> findPeptideByProtein(String accession, boolean uniqueOnly) {
        List<uk.ac.ebi.pride.proteomes.db.core.api.peptide.Peptide> peptides = new ArrayList<uk.ac.ebi.pride.proteomes.db.core.api.peptide.Peptide>();

        // get unique pepitdes by first querying all and then applying a filter
        // currently faster than the alternative below
        List<PeptideGroup> allPepGroups = peptideGroupRepository.findByProteinGroupId(accession);
        for (PeptideGroup pepGroup : allPepGroups) {
            if (!uniqueOnly || pepGroup.getUniqueness() == 1) {
                peptides.add(pepGroup.getPeptide());
            }
        }

        // get unique peptides with dedicated DB repo query
        // ToDo: this is not efficient at all! Possibly a missing index in the DB...
//        List<PeptideGroup> uniquePepGroups = peptideGroupRepository.findByProteinGroupIdAndUniqueness(accession, 1);
//        for (PeptideGroup pepGroup : uniquePepGroups) {
//            peptides.add(pepGroup.getPeptide());
//        }

        return peptides;
    }

    @Transactional(readOnly = true)
    public List<ProteinGroup> getProteinGroups(int species, int page, int pageSize) {
        List<uk.ac.ebi.pride.proteomes.db.core.api.protein.groups.ProteinGroup> dbGroups = proteinGroupRepository.findByTaxid(species, new PageRequest(page, pageSize));

        List<ProteinGroup> list = new ArrayList<ProteinGroup>();

        for (uk.ac.ebi.pride.proteomes.db.core.api.protein.groups.ProteinGroup dbGroup : dbGroups) {
            ProteinGroup serviceGroup = ModelConverter.convertProteinGroup(dbGroup);
            list.add(serviceGroup);
        }

        return list;
    }

    @Transactional(readOnly = true)
    public List<ProteinGroup> getProteinGroupsForProtein(String acc) {
        List<ProteinGroup> list = new ArrayList<ProteinGroup>();

        uk.ac.ebi.pride.proteomes.db.core.api.protein.Protein protein = proteinRepository.findByProteinAccession(acc);
        Collection<uk.ac.ebi.pride.proteomes.db.core.api.protein.groups.ProteinGroup> dbGroups = protein.getProteinGroups();
        logger.info("Found " + dbGroups.size() + " protein groups for protein " + acc);

        // convert the protein groups from DB format into service format
        for (uk.ac.ebi.pride.proteomes.db.core.api.protein.groups.ProteinGroup dbGroup : dbGroups) {
            ProteinGroup serviceGroup = ModelConverter.convertProteinGroup(dbGroup);
            list.add(serviceGroup);
        }

        return list;
    }

    @Transactional(readOnly = true)
    public Protein getProtein(String accession, boolean includeDetails) {

        uk.ac.ebi.pride.proteomes.db.core.api.protein.Protein dbProtein = proteinRepository.findByProteinAccession(accession);

        if (dbProtein == null) {
            logger.warn("No protein match found for accession: " + accession);
            return null;
        }

        uk.ac.ebi.pride.proteomes.web.service.protein.Protein protein = ModelConverter.convertProtein(dbProtein, true);
        if (includeDetails) {
            // find the mapped peptides
            protein.getPeptides().addAll(this.getProteinPeptides(accession, null, null));
        }

        return protein;
    }

    @Transactional(readOnly = true)
    public List<Protein> getProteins(int species, String tissueName, String modName, boolean includeSequence, int page, int pageSize) {
        List<Protein> list = new ArrayList<Protein>();

        PageRequest pageRequest = new PageRequest(page, pageSize);
        // quick species check, negative values are not allowed for taxids (a species is mandatory for now)
        if (species < 0) {
            return list;
        }

        List<uk.ac.ebi.pride.proteomes.db.core.api.protein.Protein> dbProteins;

        if (tissueName.equals(Tissue.defaultValue) && modName.equals(Modification.defaultValue)) {
            dbProteins = proteinRepository.findAllByTaxid(species, pageRequest);
        } else if (!tissueName.equals(Tissue.defaultValue) && modName.equals(Modification.defaultValue)) {
            // The repo only accepts cvTerm accessions, not the names, so we have to convert the tissue name
            // ToDo: make the repo accept both
            String tissueCv = Tissue.getTissue(tissueName).getCvTerm();
            dbProteins = proteinRepository.findAllByTaxidAndTissue(species, tissueCv, pageRequest);
        } else if (tissueName.equals(Tissue.defaultValue) && !modName.equals(Modification.defaultValue)) {
            dbProteins = proteinRepository.findAllByTaxidAndModification(species, modName, pageRequest);
        } else {
            // The repo only accepts cvTerm accessions, not the names, so we have to convert the tissue name
            String tissueCv = Tissue.getTissue(tissueName).getCvTerm();
            dbProteins = proteinRepository.findAllByTaxidAndTissueAndModification(species, tissueCv, modName, pageRequest);
        }

        logger.info("Initial protein list request retrieved " + dbProteins.size() + " proteins.");


        for (uk.ac.ebi.pride.proteomes.db.core.api.protein.Protein dbProtein : dbProteins) {
            uk.ac.ebi.pride.proteomes.web.service.protein.Protein tempProtein = ModelConverter.convertProtein(dbProtein, includeSequence);
            list.add(tempProtein);
        }

        logger.info("After filtering " + list.size() + " proteins remain.");


        return list;
    }
    @Transactional(readOnly = true)
    public Long countProteins(int species, String tissueName, String modName) {
        long resultCount;

        if (tissueName.equals(Tissue.defaultValue) && modName.equals(Modification.defaultValue)) {
            resultCount = proteinRepository.countByTaxid(species);
        } else if (!tissueName.equals(Tissue.defaultValue) && modName.equals(Modification.defaultValue)) {
            // The repo only accepts cvTerm accessions, not the names, so we have to convert the tissue name
            // ToDo: make the repo accept both
            String tissueCv = Tissue.getTissue(tissueName).getCvTerm();
            resultCount = proteinRepository.countByTaxidAndTissue(species, tissueCv);
        } else if (tissueName.equals(Tissue.defaultValue) && !modName.equals(Modification.defaultValue)) {
            resultCount = proteinRepository.countByTaxidAndModification(species, modName);
        } else {
            // The repo only accepts cvTerm accessions, not the names, so we have to convert the tissue name
            String tissueCv = Tissue.getTissue(tissueName).getCvTerm();
            resultCount = proteinRepository.countByTaxidAndTissueAndModification(species, tissueCv, modName);
        }

        return resultCount;
    }

//    @Transactional(readOnly = true)
//    public List<Protein> getProteins(int species, String description, boolean includeSequence, int page, int pageSize) {
//        List<Protein> list = new ArrayList<Protein>();
//
//        PageRequest pageRequest = new PageRequest(page, pageSize);
//        // quick species check, negative values are not allowed for taxids (a species is mandatory for now)
//        if (species < 0) {
//            return list;
//        }
//
//        List<uk.ac.ebi.pride.proteomes.db.core.api.protein.Protein> dbProteins;
//
//        if (description != null && !description.trim().isEmpty()) {
//            dbProteins = proteinRepository.findByTaxidAndDescriptionContaining(species, description, pageRequest);
//        } else {
//            dbProteins = proteinRepository.findAllByTaxid(species, pageRequest);
//        }
//
//
//        for (uk.ac.ebi.pride.proteomes.db.core.api.protein.Protein dbProtein : dbProteins) {
//            uk.ac.ebi.pride.proteomes.web.service.protein.Protein tempProtein = ModelConverter.convertProtein(dbProtein, includeSequence);
//            list.add(tempProtein);
//        }
//
//        return list;
//    }

    @Transactional(readOnly = true)
    public List<Protein> getProteinsByPeptideSequence(String sequence, int species, boolean includeSequence) {
        List<Protein> list = new ArrayList<uk.ac.ebi.pride.proteomes.web.service.protein.Protein>();

        List<SymbolicPeptide> dbPeptides = peptideRepository.findSymbolicPeptideBySequence(sequence);
        for (SymbolicPeptide dbPeptide : dbPeptides) {
            for (PeptideProtein peptideProtein : dbPeptide.getProteins()) {
                uk.ac.ebi.pride.proteomes.db.core.api.protein.Protein dbProtein = peptideProtein.getProtein();
                Protein tempProtein = ModelConverter.convertProtein(dbProtein, includeSequence);
                if (species < 0 || species == tempProtein.getTaxonID()) {
                    list.add(tempProtein);
                }
            }
        }
        return list;
    }

//    @Transactional(readOnly = true)
//    public List<Peptide> getPeptides(int species, int page, int pageSize) {
//        List<Peptide> list = new ArrayList<Peptide>();
//
//        // if no valid species id was provided, we don't even need to query
//        if (species < 0) {
//            return list;
//        }
//
//        List<SymbolicPeptide> dbPeptides = peptideRepository.findAllSymbolicPeptideByTaxid(species, new PageRequest(page, pageSize));
//        logger.info("Initial peptide list request retrieved " + dbPeptides.size() + " peptides.");
//
//        for (SymbolicPeptide dbPeptide : dbPeptides) {
//            // aggregate all peptide variant based tissue and mod information on the symbolic peptide in the conversion
//
//            Peptide servicePeptide = ModelConverter.convertPeptide(dbPeptide, true);
//            list.add(servicePeptide);
//        }
//        logger.info("After filtering " + list.size() + " peptides remain.");
//
//        return list;
//    }


    @Transactional(readOnly = true)
    public List<Peptide> getPeptides(int species, String tissueName, String modName, int page, int pageSize) {
        List<Peptide> list = new ArrayList<Peptide>();

        // if no valid species id was provided, we don't even need to query
        if (species < 0) {
            return list;
        }

        PageRequest pageRequest = new PageRequest(page, pageSize);

        List<SymbolicPeptide> dbPeptides;

        if (tissueName.equals(Tissue.defaultValue) && modName.equals(Modification.defaultValue)) {
            dbPeptides = peptideRepository.findAllSymbolicPeptideByTaxid(species, pageRequest);
        } else if (!tissueName.equals(Tissue.defaultValue) && modName.equals(Modification.defaultValue)) {
            dbPeptides = peptideRepository.findAllSymbolicPeptideByTaxidAndTissue(species, tissueName,pageRequest);
        } else if (tissueName.equals(Tissue.defaultValue) && !modName.equals(Modification.defaultValue)) {
            dbPeptides = peptideRepository.findAllSymbolicPeptideByTaxidAndModification(species, modName, pageRequest);
        } else {
            dbPeptides = peptideRepository.findAllSymbolicPeptideByTaxidAndTissueAndModification(species, tissueName, modName, pageRequest);
        }

        logger.info("Initial peptide list request retrieved " + dbPeptides.size() + " peptides.");

        for (SymbolicPeptide dbPeptide : dbPeptides) {
            Peptide servicePeptide = ModelConverter.convertPeptide(dbPeptide, true);
            list.add(servicePeptide);
        }

        logger.info("After filtering " + list.size() + " peptides remain.");

        return list;
    }


    @Transactional(readOnly = true)
    public List<Peptide> getPeptidesForSequence(String sequence, int species, String tissueName, String modName, boolean includeDetails) {
        List<Peptide> list = new ArrayList<Peptide>();

        List<Peptiform> dbPeptides = peptideRepository.findPeptiformBySequence(sequence);

        //TODO: Migrate the filters to a common search library
        for (Peptiform dbPeptide : dbPeptides) {
            Peptide servicePeptide = ModelConverter.convertPeptide(dbPeptide, includeDetails);
            if (DataRetriever.checkPeptideMatches(servicePeptide, tissueName, modName, species)) {
                list.add(servicePeptide);
            }

        }

        return list;
    }

    @Transactional(readOnly = true)
    public List<LocatedPeptide> getProteinPeptides(String acc, String tissueName, String modName) {
        List<LocatedPeptide> list = new ArrayList<LocatedPeptide>();
        //retrieve all peptide-protein matches from the protein record
        Collection<PeptideProtein> ppMatches = peptideProteinRepository.findByProteinProteinAccession(acc);

        for (PeptideProtein ppMatch : ppMatches) {
            uk.ac.ebi.pride.proteomes.db.core.api.peptide.Peptide dbPeptide = ppMatch.getPeptide();

            LocatedPeptide lPeptide = new LocatedPeptide(ModelConverter.convertPeptide(dbPeptide, true));

            if (ppMatch.getUniqueness() != null) {
                lPeptide.setUniqueness(ppMatch.getUniqueness());
            }
            if (ppMatch.getStartPosition() != null) {
                lPeptide.setPosition(ppMatch.getStartPosition());
            }

            // check if the peptide matches the criteria
            if (checkPeptideMatches(lPeptide, tissueName, modName, -1)) {
                list.add(lPeptide);
            }

        }
        logger.info("After filtering " + list.size() + " (of " + ppMatches.size() + ") peptides remain.");

        return list;
    }

    @Transactional(readOnly = true)
    public String getProteinCount(int species, String description) {
        long count;

        if (species < 0) {
            if (description != null && !description.trim().isEmpty()) {
                count = proteinRepository.countByDescriptionContaining(description);
            } else {
                count = proteinRepository.count();
            }
        } else {
            if (description != null && !description.trim().isEmpty()) {
                count = proteinRepository.countByTaxidAndDescriptionContaining(species, description);
            } else {
                count = proteinRepository.countByTaxid(species);
            }

        }
        // ToDo: (future) perhaps return a more verbose object that also lists the applied filter values
        return count + "";
    }

    @Transactional(readOnly = true)
    public Statistics getStatistics(Collection<Species> species) {
        Statistics proteomesStats = new Statistics();

        proteomesStats.setProteinCount(proteinRepository.count());
        proteomesStats.setPeptiformCount(peptideRepository.countPeptiforms());
        proteomesStats.setSymbolicPeptideCount(peptideRepository.countSymbolicPeptide());

        // for each provided species, get the counts
        for (Species s : species) {
            DatasetStats stats = new DatasetStats(s);
            stats.setProteinCount(proteinRepository.countByTaxid(s.getTaxid()));
            stats.setPeptiformCount(peptideRepository.countPeptiformsByTaxid(s.getTaxid()));
            stats.setSymbolicPeptideCount(peptideRepository.countSymbolicPeptideByTaxid(s.getTaxid()));
            proteomesStats.addDatasetStats(stats);
        }

        return proteomesStats;
    }

    public List<Species> getSpecies() {
        List<Species> list = new ArrayList<Species>();
        list.addAll(Arrays.asList(Species.values()));
        return list;
    }

    public List<Tissue> getTissues() {
        List<Tissue> list = new ArrayList<Tissue>();
        list.addAll(Arrays.asList(Tissue.values()));
        return list;
    }
    // ToDo: combine getTissues methods?
    public Set<Tissue> getTissues(Species species) {
        // ToDo: perhaps solve in a more elegant way with spring data
        // select distinct(cv_term) from prot_cv c, protein p where c.PROTEIN_ID = p.PROTEIN_ID AND CV_TERM like 'BTO:%' AND p.TAXID = 9606;
        if (species == null) {
            return null;
        }

        Set<Tissue> tissues = new HashSet<Tissue>();
        Connection connection = null;
        try {
            connection = proteomesDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("select distinct(cv_term) from prot_cv c, protein p where c.PROTEIN_ID=p.PROTEIN_ID AND CV_TERM like 'BTO:%' AND p.TAXID=?");
            statement.setInt(1, species.getTaxid());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String tissueAccession = rs.getString("cv_term");
                tissues.add( Tissue.getTissue(tissueAccession) );
            }
        } catch (SQLException e) {
            logger.error("SQLException during service check." , e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Failed to close connection during service check." , e);
                }
            }
        }

        return tissues;
    }

    public int countTissue(Species species) {
        // ToDo: perhaps solve in a more elegant way with spring data
        // select count(distinct(cv_term)) from prot_cv c, protein p where c.PROTEIN_ID = p.PROTEIN_ID AND CV_TERM like 'BTO:%' AND p.TAXID = 9606;
        int tissueCount = 0;
        Connection connection = null;
        try {
            connection = proteomesDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("select count(distinct(cv_term)) from prot_cv c, protein p where c.PROTEIN_ID = p.PROTEIN_ID AND CV_TERM like 'BTO:%' AND p.TAXID = ?");
            statement.setInt(1, species.getTaxid());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                tissueCount = rs.getInt(1);
                logger.info("Tissue count for species " + species.getName() + " : " + tissueCount);
            } else {
                logger.error("Could not retrieve tissue count for species: " +species.getName());
            }
        } catch (SQLException e) {
            logger.error("SQLException during service check." , e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Failed to close connection during service check." , e);
                }
            }
        }

        return tissueCount;
    }

    public List<Modification> getModifications() {
        List<Modification> list = new ArrayList<Modification>();
        list.addAll(Arrays.asList(Modification.values()));
        return list;

    }

}
