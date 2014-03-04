package uk.ac.ebi.pride.proteomes.web.service;

/**
 * @author Florian Reisinger
 * @since 0.2
 */
public final class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
