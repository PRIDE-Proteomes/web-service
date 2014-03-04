package uk.ac.ebi.pride.proteomes.web.service.sample.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import uk.ac.ebi.pride.proteomes.web.service.sample.Tissue;

import java.io.IOException;

/**
 * @author Florian Reisinger
 * @since 0.4
 */
public class TissueSerializer extends JsonSerializer<Tissue> {

      @Override
      public void serialize(Tissue tissue, JsonGenerator generator,
                SerializerProvider provider) throws IOException {

          generator.writeStartObject();
          generator.writeFieldName("id");
          generator.writeString(tissue.getCvTerm());
          generator.writeFieldName("name");
          generator.writeString(tissue.getCvName());
          generator.writeEndObject();
      }

}
