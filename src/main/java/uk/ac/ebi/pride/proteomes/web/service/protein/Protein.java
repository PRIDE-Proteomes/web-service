package uk.ac.ebi.pride.proteomes.web.service.protein;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;
import uk.ac.ebi.pride.proteomes.web.service.feature.Feature;
import uk.ac.ebi.pride.proteomes.web.service.modification.HasModifications;
import uk.ac.ebi.pride.proteomes.web.service.modification.ModifiedLocation;
import uk.ac.ebi.pride.proteomes.web.service.peptide.LocatedPeptide;
import uk.ac.ebi.pride.proteomes.web.service.sample.HasTissues;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.LocatedPeptideComparator;
import uk.ac.ebi.pride.proteomes.web.service.util.comparator.ModifiedLocationComparator;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
//@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@ApiModel(value = "Protein", description = "Information about a protein")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Protein implements HasTissues, HasModifications {

    @ApiModelProperty(value = "the protein's accession")
    private String accession = "";
    @ApiModelProperty(value = "the protein's associated genes")
    private Set<String> genes ; //Genes associated during the process of grouping
    @ApiModelProperty(value = "the protein's species (taxon id)")
    private int taxonID = -1;
    @ApiModelProperty(value = "the protein's sequence")
    private String sequence = "";
    @ApiModelProperty(value = "the protein's description")
    private String description = "";
    @ApiModelProperty(value = "the protein's reported modifications")
    private Set<ModifiedLocation> modifiedLocations; // there may be more than one modification on one position!!
    @ApiModelProperty(value = "the protein's reported tissue annotations")
    private Set<Tissue> tissues;
    @ApiModelProperty(value = "the protein's reported peptides")
    private Set<LocatedPeptide> peptides;
    @ApiModelProperty(value = "the protein's reported features")
    private Set<Feature> features;
    @ApiModelProperty(value = "number of peptides unique to this protein")
    private int uniquePeptideToProteinCount = -1;
    @ApiModelProperty(value = "number of peptides unique to proteins encoded by the same gene")
    private int uniquePeptideToGeneCount = -1;
    @ApiModelProperty(value = "number of shared peptides")
    private int nonUniquePeptidesCount = -1;

    // for web front-end only. Uniprot enrichment for protein details.
    private String name;
    private String alternativeName;
    private String geneSymbol;
    private String proteinEvidence;
    private String species;

    public Protein() {
        modifiedLocations = new TreeSet<ModifiedLocation>(new ModifiedLocationComparator());
        peptides = new TreeSet<LocatedPeptide>(new LocatedPeptideComparator());
        tissues = new TreeSet<Tissue>();
        //TODO: Take into account that genes can be empty.
        genes = new TreeSet<String>();
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public Set<String> getGenes() {
        return genes;
    }

    public void setGenes(Set<String> genes) {
        this.genes = genes;
    }

    public int getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(int taxonID) {
        this.taxonID = taxonID;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Set<ModifiedLocation> getModifiedLocations() {
        return modifiedLocations;
    }

    public void setModifiedLocations(Set<ModifiedLocation> modifiedLocations) {
        this.modifiedLocations = modifiedLocations;
    }

    @Override
    public Set<Tissue> getTissues() {
        return this.tissues;
    }

    public void setTissues(Set<Tissue> tissues) {
        this.tissues = tissues;
    }

    public Set<LocatedPeptide> getPeptides() {
        return peptides;
    }

    public void setPeptides(Set<LocatedPeptide> peptides) {
        this.peptides = peptides;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public int getUniquePeptideToProteinCount() {
        return uniquePeptideToProteinCount;
    }

    public void setUniquePeptideToProteinCount(int uniquePeptideToProteinCount) {
        this.uniquePeptideToProteinCount = uniquePeptideToProteinCount;
    }

    public int getUniquePeptideToGeneCount() {
        return uniquePeptideToGeneCount;
    }

    public void setUniquePeptideToGeneCount(int uniquePeptideToGeneCount) {
        this.uniquePeptideToGeneCount = uniquePeptideToGeneCount;
    }

    public int getNonUniquePeptidesCount() {
        return nonUniquePeptidesCount;
    }

    public void setNonUniquePeptidesCount(int nonUniquePeptidesCount) {
        this.nonUniquePeptidesCount = nonUniquePeptidesCount;
    }

    //Fronted

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setProteinEvidence(String proteinEvidence) {
        this.proteinEvidence = proteinEvidence;
    }

    public String getProteinEvidence() {
        return proteinEvidence;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSpecies() {
        return species;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Protein)) return false;

        Protein protein = (Protein) o;

        if (taxonID != protein.taxonID) return false;
        return accession.equals(protein.accession);

    }

    @Override
    public int hashCode() {
        int result = accession.hashCode();
        result = 31 * result + taxonID;
        return result;
    }

}
