package uk.ac.ebi.pride.proteomes.web.service.swagger;

import com.mangofactory.swagger.core.SwaggerPathProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletContext;

/**
 * @author Florian Reisinger
 * @since 1.0.1
 */
public class DocuPathProvider implements SwaggerPathProvider {

    private SwaggerPathProvider defaultSwaggerPathProvider;
    @Autowired
    private ServletContext servletContext;


    @Value("${swagger.app.base.url}")
    private String appBaseUrl;


    @Override
    public String getApiResourcePrefix() {
        return defaultSwaggerPathProvider.getApiResourcePrefix();
    }

    @Override
    public String getAppBasePath() {
        return UriComponentsBuilder
                .fromHttpUrl(appBaseUrl)
                .path(servletContext.getContextPath())
                .build()
                .toString();
    }

    @Override
    public String sanitizeRequestMappingPattern(String requestMappingPattern) {
        return defaultSwaggerPathProvider.sanitizeRequestMappingPattern(requestMappingPattern);
    }

    public void setDefaultSwaggerPathProvider(SwaggerPathProvider defaultSwaggerPathProvider) {
        this.defaultSwaggerPathProvider = defaultSwaggerPathProvider;
    }
}

