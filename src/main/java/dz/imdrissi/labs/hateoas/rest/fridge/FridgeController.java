package dz.imdrissi.labs.hateoas.rest.fridge;

import dz.imdrissi.labs.hateoas.rest.item.ItemController;
import dz.imdrissi.labs.hateoas.repository.FridgeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FridgeController {
    private final FridgeRepository fridgeRepository;
    private final FridgeModelAssembler fridgeAssembler;

    public FridgeController(FridgeRepository fridgeRepository, FridgeModelAssembler fridgeAssembler) {
        this.fridgeRepository = fridgeRepository;
        this.fridgeAssembler = fridgeAssembler;
    }


    @GetMapping("/fridges/{id}")
    public EntityModel<Fridge> one(@PathVariable Long id) {

        Fridge fridge = fridgeRepository.findById(id)
                .orElseThrow(() -> new FridgeNotFoundException(id));

        return fridgeAssembler.toModel(fridge);
    }

    @GetMapping("/fridges")
    public CollectionModel<EntityModel<FridgeProjection>> all(@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("id"));
        Page<FridgeProjection> pagedResult = fridgeRepository.findAllBasicData(paging);
        List<EntityModel<FridgeProjection>> items;

        if (pagedResult.hasContent()) {
            items = pagedResult.getContent().stream()
                    .map(fridgeAssembler::toModel)
                    .collect(Collectors.toList());
        } else {
            items = new ArrayList<EntityModel<FridgeProjection>>();
        }
        return new CollectionModel<EntityModel<FridgeProjection>>(items, linkTo(methodOn(ItemController.class).all(pageNum, pageSize)).withSelfRel());
    }

}
