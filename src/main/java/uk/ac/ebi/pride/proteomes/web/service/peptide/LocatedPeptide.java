package uk.ac.ebi.pride.proteomes.web.service.peptide;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class LocatedPeptide extends Peptide  {

    private Integer position = -1;
    private Integer uniqueness = -1;

    public LocatedPeptide(Peptide peptide) {
        this.setId(peptide.getId());
        this.setSymbolic(peptide.isSymbolic()); // mapped peptides are usually symbolic!
        this.setTaxonID(peptide.getTaxonID());
        this.setSequence(peptide.getSequence());
        this.setTissues(peptide.getTissues());
        this.setAssays(peptide.getAssays());
        this.setModifiedLocations(peptide.getModifiedLocations());
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getUniqueness() {
        return uniqueness;
    }

    public void setUniqueness(Integer uniqueness) {
        this.uniqueness = uniqueness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocatedPeptide)) return false;
        if (!super.equals(o)) return false;

        LocatedPeptide that = (LocatedPeptide) o;

        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
