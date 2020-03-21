package dz.imdrissi.labs.hateoas.entity.item;

import dz.imdrissi.labs.hateoas.entity.Fridge;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class ItemModel extends RepresentationModel<ItemModel> {
    private String name;
    private Fridge fridge;
}
