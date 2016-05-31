package uk.ac.ebi.pride.proteomes.web.service.sample.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;

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
