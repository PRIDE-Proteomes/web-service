package uk.ac.ebi.pride.proteomes.web.service.modification;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class ModifiedLocation {

    private Integer position;
    private Modification modification;

    public ModifiedLocation() {

    }

    public ModifiedLocation(int position, Modification modification) {
        this.position = position;
        this.modification = modification;
    }


    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Modification getModification() {
        return modification;
    }

    public void setModification(Modification modification) {
        this.modification = modification;
    }

}
