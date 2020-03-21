package dz.imdrissi.labs.hateoas.rest.fridge;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FridgeModelAssembler implements RepresentationModelAssembler<Fridge, EntityModel<Fridge>> {
    @Override
    public EntityModel<Fridge> toModel(Fridge entity) {
        return new EntityModel<>(entity,
                linkTo(methodOn(FridgeController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(FridgeController.class).all(null, null)).withRel("fridgesFromAssembler")
        );
    }

    public EntityModel<FridgeProjection> toModel(FridgeProjection fridgeProjection) {
        return new EntityModel<>(fridgeProjection,
                linkTo(methodOn(FridgeController.class).one(fridgeProjection.getId())).withSelfRel(),
                linkTo(methodOn(FridgeController.class).all(null, null)).withRel("fridgesFromAssembler")
        );
    }

}
