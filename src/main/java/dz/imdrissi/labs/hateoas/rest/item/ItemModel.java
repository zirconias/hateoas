package dz.imdrissi.labs.hateoas.rest.item;

import dz.imdrissi.labs.hateoas.rest.fridge.Fridge;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemModel extends RepresentationModel<ItemModel> {
    private String name;
    private Fridge fridge;
}
