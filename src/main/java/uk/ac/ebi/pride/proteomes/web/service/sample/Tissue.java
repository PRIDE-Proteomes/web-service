package uk.ac.ebi.pride.proteomes.web.service.sample;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
//@JsonSerialize(using = TissueSerializer.class)
public enum Tissue {

    // ToDo: find better solution for tissue annotation!
    // For example use ontology accessions. Default to BTO.
    // Provide a string to 'known' type mapping function

    AORTA_THORACICA("BTO:0000157", "aorta thoracica"),
    ASCITES("BTO:0000091", "ascites"),
    B_LYMPHOMA_CELL_LINE("BTO:0001518", "B_lymphoma cell line"),
    BILE("BTO:0000121", "bile"),
    BLOOD_PLASMA("BTO:0000131", "blood plasma"),
    BLOOD_PLATELET("BTO:0000132", "blood platelet"),
    BLOOD_SERUM("BTO:0000133", "blood serum"),
    BLOOD("BTO:0000089", "blood"),
    BONE_MARROW("BTO:0000141", "bone marrow"),
    BRAIN("BTO:0000142", "brain"),
    BREAST_CANCER_CELL_LINE("BTO:0000356", "breast cancer cell line"),
    BURKITT_LYMPHOMA_CELL("BTO:0000164", "Burkitt lymphoma cell"),
    CELL_CULTURE("BTO:0000214", "cell culture"),
    CEREBROSPINAL_FLUID("BTO:0000237", "cerebrospinal fluid"),
    CULTURE_SUPERNATANT("BTO:0002217", "culture supernatant"),
    DENDRITIC_CELL("BTO:0002042", "dendritic cell"),
    EMBRYONIC_CELL_LINE("BTO:0000669", "embryonic cell line"),
    ERYTHROCYTE("BTO:0000424", "erythrocyte"),
    ES_E14_CELL("BTO:0005136", "ES_E14 cell"),
    HEART_VENTRICLE("BTO:0000862", "heart ventricle"),
    HEART("BTO:0000562", "heart"),
    HEK_293_CELL("BTO:0000007", "HEK_293 cell"),
    HEK_293T_CELL("BTO:0002181", "HEK_293T cell"),
    HELA_CELL("BTO:0000567", "HeLa cell"),
    HEP_3B_CELL("BTO:0000972", "HEP_3B cell"),
    HEPG2_CELL("BTO:0000599", "HepG2 cell"),
    HIPPOCAMPUS("BTO:0000601", "hippocampus"),
    HL_60_CELL("BTO:0000738", "HL_60 cell"),
    HUVEC_CELL("BTO:0001949", "HUVEC cell"),
    JURKAT_CELL("BTO:0000661", "JURKAT cell"),
    JURKAT_E_6_1_CELL("BTO:0001948", "JURKAT E_6.1 cell"),
    K_562_CELL("BTO:0000664", "K_562 cell"),
    KIDNEY("BTO:0000671", "kidney"),
    LEAF("BTO:0000713", "leaf"),
    LIVER("BTO:0000759", "liver"),
    LUNG("BTO:0000763", "lung"),
    LYMPH_NODE("BTO:0000784", "lymph node"),
    MACROPHAGE_CELL_LINE("BTO:0002278", "macrophage cell line"),
    MELANOMA_CELL_LINE("BTO:0000849", "melanoma cell line"),
    MONONUCLEAR_CELL("BTO:0000878", "mononuclear cell"),
    NEUROBLASTOMA_CELL_LINE("BTO:0000932", "neuroblastoma cell line"),
    OOCYTE("BTO:0000964", "oocyte"),
    OSTEOSARCOMA_CELL_LINE("BTO:0000407", "osteosarcoma cell line"),
    PEYERS_GLAND("BTO:0001784", "Peyers gland"),
    PLACENTA("BTO:0001078", "placenta"),
    PRIMARY_CELL("BTO:0001413", "primary cell"),
    RENAL_CELL_CARCINOMA_CELL_LINE("BTO:0000383", "renal cell carcinoma cell line"),
    SEED("BTO:0001226", "seed"),
    SKELETAL_MUSCLE("BTO:0001103", "skeletal muscle"),
    SKIN("BTO:0001253", "skin"),
    SMALL_INTESTINE_EPITHELIUM("BTO:0001258", "small intestine epithelium"),
    SPLEEN("BTO:0001281", "spleen"),
    TESTIS("BTO:0001363", "testis"),
    TH1_CELL("BTO:0001678", "Th1_cell"),
    TH2_CELL("BTO:0001679", "Th2_cell"),
    THP_1_CELL("BTO:0001370", "THP_1 cell"),
    VASCULAR_SMOOTH_MUSCLE_CELL("BTO:0002488", "vascular smooth muscle cell"),
    WEHI_231_CELL("BTO:0001093", "WEHI_231 cell"),
    NONE("none", "none"),
    UNKNOWN("unknown", "unknown");

    public static final String defaultValue = "none";
    private final String cvTerm;
    private final String cvName;

    private Tissue(String cvTerm, String cvName) {
        this.cvTerm = cvTerm;
        this.cvName = cvName;
    }

    public static Tissue getTissue(String tissueIdentifier) {

        if (tissueIdentifier == null || tissueIdentifier.equalsIgnoreCase(defaultValue)) {
            return NONE;
        }

        for (Tissue tissue : Tissue.values()) {
            if (tissueIdentifier.equalsIgnoreCase(tissue.getCvTerm()) || tissueIdentifier.equalsIgnoreCase(tissue.getCvName())) {
                return tissue;
            }
        }

        return UNKNOWN;

    }

    /**
     * A utility method that checks that the provided Object carries the specified Tissue.
     *
     * @param object           an Object that implements HasTissues.
     * @param tissueIdentifier the String of the tissue to check for.
     * @param <T>              Any class that implements the HasTissues interface.
     * @return true if the Object contains the specified Tissue, false otherwise. Note: if the Tissue is null, false will be returned.
     */
    public static <T extends HasTissues> boolean containsTissue(T object, String tissueIdentifier) {

        if (object == null) {
            return false;
        }

        Tissue tissue = Tissue.getTissue(tissueIdentifier);

        // if we have no tissue information, we match
        if (tissue == NONE) {
            return true;
        }

        // if the tissue is unknown or not present in the object, we fail the check
        if (tissue == UNKNOWN || !object.getTissues().contains(tissue)) {
            return false;
        } // else criteria fulfilled, so we continue to other checks

        // object carries the required tissue
        return true;
    }

    public String getCvTerm() {
        return cvTerm;
    }

    public String getCvName() {
        return cvName;
    }

    @Override
    public String toString() {
        return "Tissue{" +
                "cvTerm='" + cvTerm + '\'' +
                ", cvName='" + cvName + '\'' +
                '}';
    }
}
