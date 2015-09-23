package uk.ac.ebi.pride.proteomes.web.service.modification.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Modification;

import java.io.IOException;

/**
 * @author Florian Reisinger
 * @since 0.4
 */
public class ModificationSerializer extends JsonSerializer<Modification> {

      @Override
      public void serialize(Modification mod, JsonGenerator generator,
                SerializerProvider provider) throws IOException {

          generator.writeStartObject();
          generator.writeFieldName("modId");
          generator.writeString(mod.getModId());
          generator.writeFieldName("modName");
          generator.writeString(mod.getModName());
          generator.writeFieldName("monoDelta");
          generator.writeNumber(mod.getMonoDelta());
          generator.writeFieldName("significant");
          generator.writeBoolean(mod.isBiologicalSignificant());
          generator.writeEndObject();
      }

}
