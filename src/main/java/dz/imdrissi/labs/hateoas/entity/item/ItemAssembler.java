package dz.imdrissi.labs.hateoas.entity.item;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

//todo: understand this model of representationModelAssembler/..support
@Component
public class ItemAssembler extends RepresentationModelAssemblerSupport<Item, ItemModel> {

    public ItemAssembler() {
        super(Item.class, ItemModel.class);
    }

    @Override
    public ItemModel toModel(Item entity) {
//        ItemModel resource = new ItemModel();
//        resource.setName(entity.getName());
//        resource.add(
////                linkTo(methodOn(ItemController.class).one(itemProjection.getId())).withSelfRel()
//                new Link("https://people/42")
//        );
        ItemModel resource = createModelWithId(entity.getId(), entity);
        resource.setName(entity.getName());
        resource.setFridge(entity.getFridge());

        return resource;
    }
}
