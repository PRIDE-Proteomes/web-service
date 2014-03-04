package uk.ac.ebi.pride.proteomes.web.service.modification.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import uk.ac.ebi.pride.proteomes.web.service.modification.Modification;

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
