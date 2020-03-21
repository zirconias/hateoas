package dz.imdrissi.labs.hateoas.rest.fridge;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class FridgeAssembler extends RepresentationModelAssemblerSupport<Fridge, FridgeModel> {

    public FridgeAssembler() {
        super(Fridge.class, FridgeModel.class);
    }

    @Override
    public FridgeModel toModel(Fridge entity) {
        FridgeModel resource = createModelWithId(entity.getId(), entity);
        resource.setName(entity.getName());
        resource.setItems(entity.getItems());
        return resource;
    }
}
