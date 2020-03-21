package dz.imdrissi.labs.hateoas.rest.fridge;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FridgeNotFoundException extends ResponseStatusException {
    private static final long serialVersionUID = 5332621523213800381L;

    public FridgeNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Could not find fridge " + id);
    }
}
