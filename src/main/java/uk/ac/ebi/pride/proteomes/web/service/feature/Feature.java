package uk.ac.ebi.pride.proteomes.web.service.feature;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author ntoro
 * @since 23/07/15 11:03
 */
@ApiModel(value = "Feature", description = "Information about a protein feature")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {

    private Long id;

    @ApiModelProperty(value = "type of the feature (e.g. signal peptide, transmembrane regions...)")
    private String type;

    @ApiModelProperty(value = "the start position of the feature on the mapped protein")
    private Integer start;

    @ApiModelProperty(value = "the end position of the feature on the mapped protein")
    private Integer end;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feature)) return false;
        Feature feature = (Feature) o;
        return Objects.equals(id, feature.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
