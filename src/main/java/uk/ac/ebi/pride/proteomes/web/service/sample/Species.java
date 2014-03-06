package uk.ac.ebi.pride.proteomes.web.service.sample;

/**
 * @author Florian Reisinger
 * @since 0.4
 */
//@JsonSerialize(using = SpeciesSerializer.class)
public enum Species {

    HUMAN(9606, "Homo sapiens (Human)"),
    MOUSE(10090, "Mus musculus (Mouse)"),
    RAT(10116, "Rattus norvegicus (Rat)"),
    ARABIDOPSIS(3702, "Arabidopsis thaliana (Mouse-ear cress)");

    public static final String defaultValue = "-1";

    private Species(int taxid, String name) {
        this.taxid = taxid;
        this.name = name;
    }

    private final int taxid;
    private final String name;

    public int getTaxid() {
        return taxid;
    }

    public String getName() {
        return name;
    }

    public static Species getByTaxid(int taxid) {
        for (Species species : Species.values()) {
            if (species.taxid == taxid) {
                return species;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Species{" +
                "taxid=" + taxid +
                ", name='" + name + '\'' +
                '}';
    }
}
