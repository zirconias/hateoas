package dz.imdrissi.labs.hateoas.rest.item;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ItemNotFoundException extends ResponseStatusException {

    private static final long serialVersionUID = 5332621523213800382L;

    public ItemNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Could not find item " + id);
    }
}
