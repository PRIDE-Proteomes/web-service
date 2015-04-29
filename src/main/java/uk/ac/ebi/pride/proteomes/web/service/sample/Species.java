package uk.ac.ebi.pride.proteomes.web.service.sample;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Florian Reisinger
 * @since 0.4
 */
//@JsonSerialize(using = SpeciesSerializer.class)
@ApiModel(value = "Species")
public enum Species {

    ALL(1, "All", "All species"),
    HUMAN(9606, "Human", "Homo sapiens"),
    MOUSE(10090, "Mouse", "Mus musculus"),
    RAT(10116, "Rat", "Rattus norvegicus"),
    ARABIDOPSIS(3702, "Mouse-ear cress", "Arabidopsis thaliana");

    public static final String defaultValue = "9606";

    private Species(int taxid, String commonName, String scientificName) {
        this.taxid = taxid;
        this.commonName = commonName;
        this.scientificName = scientificName;
    }

    @ApiModelProperty(value = "taxid")
    private final int taxid;
    @ApiModelProperty(value = "common name")
    private final String commonName;
    @ApiModelProperty(value = "scientific name")
    private final String scientificName;

    public int getTaxid() {
        return taxid;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getName() {
        return scientificName + " (" + commonName + ")";
    }

    public static Species getByTaxid(int taxid) {
        for (Species species : Species.values()) {
            if (species.taxid == taxid) {
                return species;
            }
        }
        return null;
    }
    public static Species getByName(String speciesName) {
        for (Species species : Species.values()) {
            if (species.getName().equalsIgnoreCase(speciesName)
                    || species.getCommonName().equalsIgnoreCase(speciesName)
                    || species.getScientificName().equalsIgnoreCase(speciesName)) {
                return species;
            }
        }
        return null;
    }

    public static Species getFromString(String speciesString) {
        try {
            Integer id = Integer.parseInt(speciesString);
            return getByTaxid(id);
        } catch (NumberFormatException e) {
            return getByName(speciesString);
        }
    }


    @Override
    public String toString() {
        return "Species{" +
                "taxid=" + taxid +
                ", name='" + getName() + '\'' +
                '}';
    }
}
