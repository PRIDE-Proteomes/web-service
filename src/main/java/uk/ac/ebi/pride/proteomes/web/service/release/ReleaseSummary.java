package uk.ac.ebi.pride.proteomes.web.service.release;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author ntoro
 * @since 07/04/2016 16:07
 */
public class ReleaseSummary {

    @ApiModelProperty(value = "the release summary's species (taxon id)")
    private Integer taxonID;

    @ApiModelProperty(value = "")
    private String referenceDatabase;

    @ApiModelProperty(value = "")
    private String referenceDatabaseVersion;

    @ApiModelProperty(value = "")
    private Long numPeptiforms;

    @ApiModelProperty(value = "")
    private Long numPeptides;

    @ApiModelProperty(value = "")
    private Long numProteins;

    @ApiModelProperty(value = "")
    private Long numIsoformProteins;

    @ApiModelProperty(value = "")
    private Long numCanonicalProteins;

    @ApiModelProperty(value = "")
    private Long numGenes;

    @ApiModelProperty(value = "")
    private Long numMappedProteins;

    @ApiModelProperty(value = "")
    private Long numMappedCanonicalProteins;

    @ApiModelProperty(value = "")
    private Long numMappedIsoformProteins;

    @ApiModelProperty(value = "")
    private Long numMappedGenes;

    @ApiModelProperty(value = "")
    private Long numMappedPeptidesToProteins;

    @ApiModelProperty(value = "")
    private Long numMappedUniquePeptidesToProteins;

    @ApiModelProperty(value = "")
    private Long numMappedUniquePeptidesToIsoformProteins;

    @ApiModelProperty(value = "")
    private Long numMappedUniquePeptidesToCanonicalProteins;

    @ApiModelProperty(value = "")
    private Long numMappedUniquePeptidesToGenes;

    @ApiModelProperty(value = "")
    private Long numMappedProteinsWithUniquePeptides;

    @ApiModelProperty(value = "")
    private Long numMappedCanonicalProteinsWithUniquePeptides;

    @ApiModelProperty(value = "")
    private Long numMappedIsoformProteinsWithUniquePeptides;

    @ApiModelProperty(value = "")
    private Long numMappedGenesWithUniquePeptides;

    @ApiModelProperty(value = "")
    private Long numMappedProteinsWithExpEvidenceAtTranscript;

    @ApiModelProperty(value = "")
    private Long numMappedProteinsWithEvidenceInferredByHomology;

    @ApiModelProperty(value = "")
    private Long numMappedProteinWithEvidencePredicted;

    @ApiModelProperty(value = "")
    private Long numMappedProteinWithEvidenceUncertain;

    public Integer getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(Integer taxonID) {
        this.taxonID = taxonID;
    }

    public String getReferenceDatabase() {
        return referenceDatabase;
    }

    public void setReferenceDatabase(String referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
    }

    public String getReferenceDatabaseVersion() {
        return referenceDatabaseVersion;
    }

    public void setReferenceDatabaseVersion(String referenceDatabaseVersion) {
        this.referenceDatabaseVersion = referenceDatabaseVersion;
    }

    public Long getNumPeptiforms() {
        return numPeptiforms;
    }

    public void setNumPeptiforms(Long numPeptiforms) {
        this.numPeptiforms = numPeptiforms;
    }

    public Long getNumPeptides() {
        return numPeptides;
    }

    public void setNumPeptides(Long numPeptides) {
        this.numPeptides = numPeptides;
    }

    public Long getNumProteins() {
        return numProteins;
    }

    public void setNumProteins(Long numProteins) {
        this.numProteins = numProteins;
    }

    public Long getNumIsoformProteins() {
        return numIsoformProteins;
    }

    public void setNumIsoformProteins(Long numIsoformProteins) {
        this.numIsoformProteins = numIsoformProteins;
    }

    public Long getNumCanonicalProteins() {
        return numCanonicalProteins;
    }

    public void setNumCanonicalProteins(Long numCanonicalProteins) {
        this.numCanonicalProteins = numCanonicalProteins;
    }

    public Long getNumGenes() {
        return numGenes;
    }

    public void setNumGenes(Long numGenes) {
        this.numGenes = numGenes;
    }

    public Long getNumMappedProteins() {
        return numMappedProteins;
    }

    public void setNumMappedProteins(Long numMappedProteins) {
        this.numMappedProteins = numMappedProteins;
    }

    public Long getNumMappedCanonicalProteins() {
        return numMappedCanonicalProteins;
    }

    public void setNumMappedCanonicalProteins(Long numMappedCanonicalProteins) {
        this.numMappedCanonicalProteins = numMappedCanonicalProteins;
    }

    public Long getNumMappedIsoformProteins() {
        return numMappedIsoformProteins;
    }

    public void setNumMappedIsoformProteins(Long numMappedIsoformProteins) {
        this.numMappedIsoformProteins = numMappedIsoformProteins;
    }

    public Long getNumMappedGenes() {
        return numMappedGenes;
    }

    public void setNumMappedGenes(Long numMappedGenes) {
        this.numMappedGenes = numMappedGenes;
    }

    public Long getNumMappedPeptidesToProteins() {
        return numMappedPeptidesToProteins;
    }

    public void setNumMappedPeptidesToProteins(Long numMappedPeptidesToProteins) {
        this.numMappedPeptidesToProteins = numMappedPeptidesToProteins;
    }

    public Long getNumMappedUniquePeptidesToProteins() {
        return numMappedUniquePeptidesToProteins;
    }

    public void setNumMappedUniquePeptidesToProteins(Long numMappedUniquePeptidesToProteins) {
        this.numMappedUniquePeptidesToProteins = numMappedUniquePeptidesToProteins;
    }

    public Long getNumMappedUniquePeptidesToIsoformProteins() {
        return numMappedUniquePeptidesToIsoformProteins;
    }

    public void setNumMappedUniquePeptidesToIsoformProteins(Long numMappedUniquePeptidesToIsoformProteins) {
        this.numMappedUniquePeptidesToIsoformProteins = numMappedUniquePeptidesToIsoformProteins;
    }

    public Long getNumMappedUniquePeptidesToCanonicalProteins() {
        return numMappedUniquePeptidesToCanonicalProteins;
    }

    public void setNumMappedUniquePeptidesToCanonicalProteins(Long numMappedUniquePeptidesToCanonicalProteins) {
        this.numMappedUniquePeptidesToCanonicalProteins = numMappedUniquePeptidesToCanonicalProteins;
    }

    public Long getNumMappedUniquePeptidesToGenes() {
        return numMappedUniquePeptidesToGenes;
    }

    public void setNumMappedUniquePeptidesToGenes(Long numMappedUniquePeptidesToGenes) {
        this.numMappedUniquePeptidesToGenes = numMappedUniquePeptidesToGenes;
    }

    public Long getNumMappedProteinsWithUniquePeptides() {
        return numMappedProteinsWithUniquePeptides;
    }

    public void setNumMappedProteinsWithUniquePeptides(Long numMappedProteinsWithUniquePeptides) {
        this.numMappedProteinsWithUniquePeptides = numMappedProteinsWithUniquePeptides;
    }

    public Long getNumMappedCanonicalProteinsWithUniquePeptides() {
        return numMappedCanonicalProteinsWithUniquePeptides;
    }

    public void setNumMappedCanonicalProteinsWithUniquePeptides(Long numMappedCanonicalProteinsWithUniquePeptides) {
        this.numMappedCanonicalProteinsWithUniquePeptides = numMappedCanonicalProteinsWithUniquePeptides;
    }

    public Long getNumMappedIsoformProteinsWithUniquePeptides() {
        return numMappedIsoformProteinsWithUniquePeptides;
    }

    public void setNumMappedIsoformProteinsWithUniquePeptides(Long numMappedIsoformProteinsWithUniquePeptides) {
        this.numMappedIsoformProteinsWithUniquePeptides = numMappedIsoformProteinsWithUniquePeptides;
    }

    public Long getNumMappedGenesWithUniquePeptides() {
        return numMappedGenesWithUniquePeptides;
    }

    public void setNumMappedGenesWithUniquePeptides(Long numMappedGenesWithUniquePeptides) {
        this.numMappedGenesWithUniquePeptides = numMappedGenesWithUniquePeptides;
    }

    public Long getNumMappedProteinsWithExpEvidenceAtTranscript() {
        return numMappedProteinsWithExpEvidenceAtTranscript;
    }

    public void setNumMappedProteinsWithExpEvidenceAtTranscript(Long numMappedProteinsWithExpEvidenceAtTranscript) {
        this.numMappedProteinsWithExpEvidenceAtTranscript = numMappedProteinsWithExpEvidenceAtTranscript;
    }

    public Long getNumMappedProteinsWithEvidenceInferredByHomology() {
        return numMappedProteinsWithEvidenceInferredByHomology;
    }

    public void setNumMappedProteinsWithEvidenceInferredByHomology(Long numMappedProteinsWithEvidenceInferredByHomology) {
        this.numMappedProteinsWithEvidenceInferredByHomology = numMappedProteinsWithEvidenceInferredByHomology;
    }

    public Long getNumMappedProteinWithEvidencePredicted() {
        return numMappedProteinWithEvidencePredicted;
    }

    public void setNumMappedProteinWithEvidencePredicted(Long numMappedProteinWithEvidencePredicted) {
        this.numMappedProteinWithEvidencePredicted = numMappedProteinWithEvidencePredicted;
    }

    public Long getNumMappedProteinWithEvidenceUncertain() {
        return numMappedProteinWithEvidenceUncertain;
    }

    public void setNumMappedProteinWithEvidenceUncertain(Long numMappedProteinWithEvidenceUncertain) {
        this.numMappedProteinWithEvidenceUncertain = numMappedProteinWithEvidenceUncertain;
    }
}
