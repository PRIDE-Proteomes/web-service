package uk.ac.ebi.pride.proteomes.web.service.swagger;

import com.google.common.collect.Ordering;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.ordering.ResourceListingPositionalOrdering;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiDescription;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Modification;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Species;
import uk.ac.ebi.pride.proteomes.db.core.api.utils.param.Tissue;

import java.net.URL;
import java.util.Date;

/**
 * @author Florian Reisinger
 * @since 1.0.1
 */
@Configuration
@EnableSwagger
@ComponentScan("uk.ac.ebi.pride.proteomes.web.service")
public class SwaggerConfig {


    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation(){
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                // PRIDE Proteomes RESTful API version
                .apiVersion("1.0")
                // try the default RelativeSwaggerPathProvider
                .apiListingReferenceOrdering(new ResourceListingPositionalOrdering())
                // direct overwrites of model classes
                .directModelSubstitute(URL.class, String.class) // don't document URL as complex object, but as simple string
                .directModelSubstitute(Date.class, String.class) // keep the date a simple string
                .directModelSubstitute(Modification.class, String.class)
                .directModelSubstitute(Species.class, String.class)
                .directModelSubstitute(Tissue.class, String.class)
                // overwrite the default ordering of description annotations
                .apiDescriptionOrdering(new ApiDescriptionPositionOrdering())
                .includePatterns("/.*");
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "PRIDE Proteomes RESTful web service API",
                "For more details and examples please see the <a href=\"/pride/help/proteomes/access/webservice\">additional documentation pages</a>",
                "http://www.ebi.ac.uk/about/terms-of-use",
                "pride-support@ebi.ac.uk",
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );
    }

    private class ApiDescriptionPositionOrdering extends Ordering<ApiDescription> {
        @Override
        public int compare(ApiDescription apiDescription, ApiDescription other) {
            int pos1 = apiDescription.operations().iterator().next().position();
            int pos2 = other.operations().iterator().next().position();
            return Integer.compare(pos1, pos2);
        }
    }


























//
//    public static final List<String> DEFAULT_INCLUDE_PATTERNS = Arrays.asList("/.*");
//
//    public static final String SWAGGER_GROUP = "";
//
//
//    /**
//     * Autowire the bundled swagger config
//     */
//    @Autowired
//    private SpringSwaggerConfig springSwaggerConfig;
//
//
//    /**
//     * Adds the jackson scala module to the MappingJackson2HttpMessageConverter registered with spring
//     * Swagger core models are scala so we need to be able to convert to JSON
//     * Also registers some custom serializers needed to transform swagger models to swagger-ui required json format
//     */
//    @Bean
//    public JacksonScalaSupport jacksonScalaSupport() {
//        JacksonScalaSupport jacksonScalaSupport = new JacksonScalaSupport();
//        //Set to false to disable
//        jacksonScalaSupport.setRegisterScalaModule(true);
//        return jacksonScalaSupport;
//    }
//
//
//    /**
//     * Global swagger settings
//     */
//    @Bean
//    public SwaggerGlobalSettings swaggerGlobalSettings() {
//        SwaggerGlobalSettings swaggerGlobalSettings = new SwaggerGlobalSettings();
//        swaggerGlobalSettings.setGlobalResponseMessages(springSwaggerConfig.defaultResponseMessages());
//        swaggerGlobalSettings.setIgnorableParameterTypes(springSwaggerConfig.defaultIgnorableParameterTypes());
//        AlternateTypeProvider alternateTypeProvider = springSwaggerConfig.defaultAlternateTypeProvider();
//        TypeResolver typeResolver = new TypeResolver();
//        alternateTypeProvider.addRule(newRule(typeResolver.resolve(ResponseEntity.class),typeResolver.resolve(Void.class)));
//        alternateTypeProvider.addRule(newRule(typeResolver.resolve(ResponseEntity.class, WildcardType.class),typeResolver.resolve(WildcardType.class)));
//        alternateTypeProvider.addRule(newRule(typeResolver.resolve(HttpEntity.class, WildcardType.class),typeResolver.resolve(WildcardType.class)));
//        swaggerGlobalSettings.setAlternateTypeProvider(alternateTypeProvider);
//        return swaggerGlobalSettings;
//    }
//
//    /**
//     * API Info as it appears on the swagger-ui page
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfo(
//                "PRIDE Proteomes web service API",
//                "Programmatic access to the PRIDE Proteomes data via RESTful Web Services.",
//                "http://www.ebi.ac.uk/about/terms-of-use",
//                "pride-support@ebi.ac.uk",
//                "Apache 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0.html"
//        );
//    }
//
//    /**
//     * Configure a SwaggerApiResourceListing for each swagger instance within your app. e.g. 1. private 2. external apis
//     * Required to be a spring bean as spring will call the postConstruct method to bootstrap swagger scanning.
//     *
//     * @return the SwaggerApiResourceListing to use.
//     */
//    @Bean
//    public SwaggerApiResourceListing swaggerApiResourceListing() {
//        //The group name is important and should match the group set on ApiListingReferenceScanner
//        //Note that swaggerCache() is by DefaultSwaggerController to serve the swagger json
//        SwaggerApiResourceListing swaggerApiResourceListing = new SwaggerApiResourceListing(springSwaggerConfig.swaggerCache(), SWAGGER_GROUP);
//
//        //Set the required swagger settings
//        swaggerApiResourceListing.setSwaggerGlobalSettings(swaggerGlobalSettings());
//
//        //Use a custom path provider or springSwaggerConfig.defaultSwaggerPathProvider()
//        swaggerApiResourceListing.setSwaggerPathProvider(demoPathProvider());
//
//        //Supply the API Info as it should appear on swagger-ui web page
//        swaggerApiResourceListing.setApiInfo(apiInfo());
//
//        //Every SwaggerApiResourceListing needs an ApiListingReferenceScanner to scan the spring request mappings
//        swaggerApiResourceListing.setApiListingReferenceScanner(apiListingReferenceScanner());
//        return swaggerApiResourceListing;
//    }
//
//
//    /**
//     * The ApiListingReferenceScanner does most of the work.
//     * Scans the appropriate spring RequestMappingHandlerMappings
//     * Applies the correct absolute paths to the generated swagger resources
//     */
//    @Bean
//    public ApiListingReferenceScanner apiListingReferenceScanner() {
//        ApiListingReferenceScanner apiListingReferenceScanner = new ApiListingReferenceScanner();
//
//        //Picks up all of the registered spring RequestMappingHandlerMappings for scanning
//        apiListingReferenceScanner.setRequestMappingHandlerMapping(springSwaggerConfig.swaggerRequestMappingHandlerMappings());
//
//        //Excludes any controllers with the supplied annotations
//        apiListingReferenceScanner.setExcludeAnnotations(springSwaggerConfig.defaultExcludeAnnotations());
//
//        //How to group request mappings to ApiResource's typically by spring controller clesses or @Api.value()
//        apiListingReferenceScanner.setResourceGroupingStrategy(springSwaggerConfig.defaultResourceGroupingStrategy());
//
//        //Path provider used to generate the appropriate uri's
//        apiListingReferenceScanner.setSwaggerPathProvider(demoPathProvider());
//
//        //Must match the swagger group set on the SwaggerApiResourceListing
//        apiListingReferenceScanner.setSwaggerGroup(SWAGGER_GROUP);
//
//        //Only include paths that match the supplied regular expressions
//        apiListingReferenceScanner.setIncludePatterns(DEFAULT_INCLUDE_PATTERNS);
//
//        return apiListingReferenceScanner;
//    }
//
//    /**
//     * Example of a custom path provider
//     */
//    @Bean
//    public SwaggerPathProvider demoPathProvider() {
//        DocuPathProvider demoPathProvider = new DocuPathProvider();
//        demoPathProvider.setDefaultSwaggerPathProvider(springSwaggerConfig.defaultSwaggerPathProvider());
//        return demoPathProvider;
//    }
//

}
