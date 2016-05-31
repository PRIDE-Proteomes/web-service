package uk.ac.ebi.pride.proteomes.web.service.modification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Modification;

/**
 * @author Florian Reisinger
 * @since 0.1
 */
@ApiModel(value = "ModifiedLocation", description = "A localised peptide modification")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModifiedLocation {

    @ApiModelProperty(value = "the modified sequence position")
    private Integer position;
    @ApiModelProperty(value = "the modification name")
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
