package uk.ac.ebi.pride.proteomes.web.service.proteingroup;

import uk.ac.ebi.pride.proteomes.web.service.sample.HasTissues;
import uk.ac.ebi.pride.proteomes.web.service.sample.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.UniprotAccessionComparator;

import java.util.*;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class ProteinGroup implements HasTissues {

    private String id;
    private int taxonID;
    private String description;
    private Collection<Tissue> tissues;
    private Collection<String> memberProteins;
    private Map<String, Set<String>> uniquePeptides;


    public ProteinGroup() {
        this.memberProteins = new TreeSet<String>(new UniprotAccessionComparator());
        this.tissues = new TreeSet<Tissue>();
        this.uniquePeptides = new TreeMap<String, Set<String>>(); // map of peptide sequences to a set of protein accessions
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(int taxonID) {
        this.taxonID = taxonID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Collection<Tissue> getTissues() {
        return tissues;
    }

    public void setTissues(Collection<Tissue> tissues) {
        this.tissues = tissues;
    }

    public Collection<String> getMemberProteins() {
        return memberProteins;
    }

    public void setMemberProteins(Collection<String> memberProteins) {
        this.memberProteins = memberProteins;
    }

    public Map<String, Set<String>> getUniquePeptides() {
        return uniquePeptides;
    }

    public void setUniquePeptides(Map<String, Set<String>> uniquePeptides) {
        this.uniquePeptides = uniquePeptides;
    }
}
