package dz.imdrissi.labs.hateoas.util;

import dz.imdrissi.labs.hateoas.controller.ItemController;
import dz.imdrissi.labs.hateoas.entity.Item;
import dz.imdrissi.labs.hateoas.entity.ItemProjection;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//todo: use controller which return CollectionModel and EntityModel
@Component
public class ItemModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {

    @Override
    public EntityModel<Item> toModel(Item item) {
        return new EntityModel<>(item,
//used with data-rest
//                linkTo(methodOn(ItemRepository.class).findById(item.getId())).withSelfRel(),
//                linkTo(methodOn(ItemRepository.class).findAll()).withRel("itemz_findAll_linkto"),
//used with controller
                linkTo(methodOn(ItemController.class).one(item.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).all(null, null)).withRel("itemsFromAssembler")
        );
    }

    public EntityModel<ItemProjection> toModel(ItemProjection itemProjection) {
        return new EntityModel<>(itemProjection,
//used with data-rest
//                linkTo(methodOn(ItemRepository.class).findById(itemProjection.getId())).withSelfRel(),
//                linkTo(methodOn(ItemRepository.class).findAll()).withRel("itemProjections"),
//used with controller
                linkTo(methodOn(ItemController.class).one(itemProjection.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).all(null, null)).withRel("itemsFromAssembler"))
                ;
    }
}
