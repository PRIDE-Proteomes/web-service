package uk.ac.ebi.pride.proteomes.web.service.sample.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Species;

import java.io.IOException;

/**
 * @author Florian Reisinger
 * @since 0.4
 */
public class SpeciesSerializer extends JsonSerializer<Species> {

      @Override
      public void serialize(Species species, JsonGenerator generator,
                SerializerProvider provider) throws IOException {

          generator.writeStartObject();
          generator.writeFieldName("taxid");
          generator.writeNumber(species.getTaxid());
          generator.writeFieldName("name");
          generator.writeString(species.getName());
          generator.writeEndObject();
      }

}
