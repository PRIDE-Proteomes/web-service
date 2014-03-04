package uk.ac.ebi.pride.proteomes.web.service.alignment;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
public class MisMatch {

    private int position;
    private String type;

    public MisMatch(){}

    public MisMatch(int position, String type){
        this.position = position;
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
