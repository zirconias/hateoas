package dz.imdrissi.labs.hateoas.rest.fridge;

import dz.imdrissi.labs.hateoas.rest.item.Item;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
public class FridgeModel extends RepresentationModel<FridgeModel> {
    private String name;
    private List<Item> items;
}
