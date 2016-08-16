package uk.ac.ebi.pride.proteomes.web.service.swagger;

import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiListingReference;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Modification;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Species;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;

import java.net.URL;
import java.util.Date;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author Florian Reisinger
 * @since 1.0.1
 */
@Configuration
@EnableWebMvc //NOTE: Only needed in a non-springboot application
@EnableSwagger2
@ComponentScan("uk.ac.ebi.pride.proteomes.web.service")
public class SwaggerConfig {


    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //Ignores controllers annotated with @CustomIgnore
                .apis(RequestHandlerSelectors.any()) //Selection by RequestHandler
                .paths(paths()) // and by paths
                .build()
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .apiListingReferenceOrdering( new ResourceListingPositionalOrdering())
                .apiDescriptionOrdering(new ApiDescriptionPositionOrdering())
                .directModelSubstitute(URL.class, String.class) // don't document URL as complex object, but as simple string
                .directModelSubstitute(Date.class, String.class) // keep the date a simple string
                .directModelSubstitute(Modification.class, String.class)
                .directModelSubstitute(Species.class, String.class)
                .directModelSubstitute(Tissue.class, String.class)
                ;
    }

    //Here is an example where we select any api that matches one of these paths
    private Predicate<String> paths() {
        return or(
                regex("/protein.*"),
                regex("/peptide.*"),
                regex("/group.*"),
                regex("/sample.*"),
                regex("/modification.*"),
                regex("/stats.*"),
                regex("/release.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "PRIDE Proteomes RESTful web service API",
                "For more details and examples please see the <a href=\"/pride/help/proteomes/access/webservice\">additional documentation pages</a>",
                "1.0",
                "http://www.ebi.ac.uk/about/terms-of-use",
                new Contact("PRIDE", "http://www.ebi.ac.uk/pride", "pride-support@ebi.ac.uk"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );
    }

    private class ApiDescriptionPositionOrdering extends Ordering<ApiDescription> {
        @Override
        public int compare(ApiDescription apiDescription, ApiDescription other) {
            int pos1 = apiDescription.getOperations().iterator().next().getPosition();
            int pos2 = other.getOperations().iterator().next().getPosition();
            return Integer.compare(pos1, pos2);
        }
    }

    private class ResourceListingPositionalOrdering extends Ordering<ApiListingReference> {
        @Override
        public int compare(ApiListingReference first, ApiListingReference second) {
            return Ints.compare(first.getPosition(), second.getPosition());
        }
    }

}
