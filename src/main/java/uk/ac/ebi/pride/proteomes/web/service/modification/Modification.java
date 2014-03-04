package uk.ac.ebi.pride.proteomes.web.service.modification;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
//@JsonSerialize(using = ModificationSerializer.class)
public enum Modification {

    // ToDo: complete and perhaps extend with attributes like (description, full name, type, cv accession, etc...)

    ACETYLATION("1", "Acetylation", 42.010565, true),
    AMIDATION("2", "Amidation", -0.984016, true),
    BIOTINYLATION("3", "Biotinylation", 226.077599, true),
    PHOSPHORYLATION("4", "Phosphorylation", 79.966331, true),
    CARBAMIDOMETHYL("5", "Carbamidomethyl", 57.021464, false),
    CARBAMYLATION("6", "Carbamylation", 43.005814, false),
    CARBOXYMETHYL("7", "Carboxymethyl", 58.005479, false),
    DEAMIDATION("8", "Deamidation", 0.984016, true),
    HOMOSERINE("9", "Homoserine", -29.992806, false),
    HOMOSERINE_LACTONE("10", "Homoserine lactone", -48.003371, false),
    NIPCAM_C("11", "NIPCAM(C)", 99.068414, false),
    DEHYDRATATION("12", "Dehydratation", -18.010565, true),
    PROPIONAMIDE_C("13", "Propionamide(C)", 71.037114, false),
    PYRO_CARBAMIDOMETHYL("14", "Pyro-carbamidomethyl", 39.994915, false),
    OXIDATION("15", "Oxidation", 15.994915, true),
    DEAMINATION("16", "Deamination", -17.026549, true),
    SMA("17", "SMA", 127.063329, false),
    CATION_NA("18", "Cation:Na", 21.981944, false),
    PYRIDYLETHYL("19", "Pyridylethyl", 105.057849, false),
    MONOMETHYLATION("20", "Monomethylation", 14.01565, true),
    METHYLTHIO("21", "Methylthio", 45.987721, true),
    SULFO("22", "Sulfo", 79.956815, true),
    LIPOYL("23", "Lipoyl", 188.032957, true),
    FARNESYLATION("24", "falsearnesylation", 204.187801, true),
    MYRISTOYLATION("25", "Myristoylation", 210.198365, true),
    PYRIDOXAL_PHOSPHATE("26", "Pyridoxal phosphate", 229.014009, true),
    PALMITOYLATION("27", "Palmitoylation", 238.229666, true),
    GERANYL_GERANYL("28", "Geranyl-geranyl", 272.250401, true),
    PHOSPHOPANTETHEINE("29", "Phosphopantetheine", 340.085794, true),
    FLAVIN_ADENINE_DINUCLEOTIDE("30", "falselavin adenine dinucleotide", 783.141485, true),
    GUANIDINATION("31", "Guanidination", 42.021798, false),
    FORMYLATION("32", "falseormylation", 27.994915, true),
    ICAT_D_2H_8("33", "ICAT-D:2H(8)", 450.275205, false),
    ICAT_D("34", "ICAT-D", 442.224991, false),
    ICAT_C("35", "ICAT-C", 227.126991, false),
    ICAT_C_13C_9("36", "ICAT-C:13C(9)", 236.157185, false),
    SILAC_LABEL_13_C_6_("37", "13C(6) Silac label", 6.020129, false),
    LABEL_18O_2("38", "Label:18O(2)", 4.008493, false),
    CARBOXYLATION("39", "Carboxylation", 43.989829, true),
    DIOXIDATION("40", "Dioxidation", 31.989829, true),
    ITRAQ4PLEX("41", "iTRAQ4plex", 144.102062, false),
    ITRAQ8PLEX("42", "iTRAQ8plex", 304.205359, false),
    TMT6PLEX("43", "trueMT6plex", 229.162932, false),
    NONE("none", "none", 0, false), // mapped to null String or defaultValue (used to specify that no modification is selected)
    UNKNOWN("unknown", "unknown", 0, false); // anything that can not be mapped to the 'known' modifications above

    public static final String defaultValue = "none";
    private final String modId;
    private final String modName;
    private final double monoDelta;
    private final boolean biologicalSignificant;

    Modification(String modId, String modName, double monoDelta, boolean biologicalSignificant) {
        this.modId = modId;
        this.modName = modName;
        this.monoDelta = monoDelta;
        this.biologicalSignificant = biologicalSignificant;
    }

    public static Modification getModification(String modIdentifier) {

        if (modIdentifier == null || modIdentifier.equalsIgnoreCase(defaultValue)) {
            return NONE;
        }

        for (Modification mod : Modification.values()) {
            if (modIdentifier.equalsIgnoreCase(mod.getModId()) || modIdentifier.equalsIgnoreCase(mod.getModName())) {
                return mod;
            }
        }

        return UNKNOWN;

    }

    /**
     * A utility method that checks that the provided Object carries the specified Modification.
     *
     * @param object        an Object that implements HasModifications.
     * @param modIdentifier the String of the modification to check for.
     * @param <T>           Any class that implements the HasModifications interface.
     * @return true if the Object contains the specified Modification, false otherwise. Note: if the Modification is null, false will be returned.
     */
    public static <T extends HasModifications> boolean containsModification(T object, String modIdentifier) {

        if (object == null) {
            return false;
        }

        Modification modification = Modification.getModification(modIdentifier);

        // if we have no modification information, we match
        if (modification == NONE) {
            return true;
        }

        // if the modification is unknown or not present in the object, we fail the check
        if (modification == UNKNOWN) {
            return false;
        } // else criteria fulfilled, so we continue to other checks

        for (ModifiedLocation modifiedLocation : object.getModifiedLocations()) {
            // object carries the required modification
            if(modifiedLocation.getModification().equals(modification)){
                return true;
            }
        }
        return false;
    }

    public String getModId() {
        return modId;
    }

    public String getModName() {
        return modName;
    }

    public double getMonoDelta() {
        return monoDelta;
    }

    public boolean isBiologicalSignificant() {
        return biologicalSignificant;
    }

    @Override
    public String toString() {
        return "Modification{" +
                "modId='" + modId + '\'' +
                ", modName='" + modName + '\'' +
                ", monoDelta=" + monoDelta +
                ", biologicalSignificant=" + biologicalSignificant +
                '}';
    }
}
