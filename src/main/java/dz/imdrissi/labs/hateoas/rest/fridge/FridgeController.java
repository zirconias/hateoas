package dz.imdrissi.labs.hateoas.rest.fridge;

import dz.imdrissi.labs.hateoas.repository.FridgeRepository;
import dz.imdrissi.labs.hateoas.rest.item.ItemController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URISyntaxException;
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

    @GetMapping("/fridgez")
    public CollectionModel<FridgeModel> allz(@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
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
        FridgeAssembler iass = new FridgeAssembler();
        CollectionModel<FridgeModel> collection = iass.toCollectionModel(fridgeRepository.findAll());
        return collection;
    }

    @PostMapping("/fridges")
    public ResponseEntity<?> newFridge(@RequestBody Fridge newFridge) throws URISyntaxException {

        EntityModel<Fridge> entityModel = fridgeAssembler.toModel(fridgeRepository.save(newFridge));

        //Return HTTP 201 Created status message
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/fridges/{id}")
    public EntityModel<Fridge> one(@PathVariable Long id) {

        Fridge fridge = fridgeRepository.findById(id)
                .orElseThrow(() -> new FridgeNotFoundException(id));

        return fridgeAssembler.toModel(fridge);
    }

    @PutMapping("/fridges/{id}")
    public ResponseEntity<?> editFridge(@RequestBody Fridge newFridge, @PathVariable Long id) throws URISyntaxException {
        Fridge updatedFridge = fridgeRepository.findById(id)
                .map(item -> {
                    item.setName(newFridge.getName());
                    item.setItems(newFridge.getItems());
                    return fridgeRepository.save(item);
                })
                .orElseGet(() -> {
                    newFridge.setId(id);
                    return fridgeRepository.save(newFridge);
                });

        EntityModel<Fridge> entityModel = fridgeAssembler.toModel(updatedFridge);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/fridges/{id}")
    public ResponseEntity<?> deleteFridge(@PathVariable Long id) {
        if (!fridgeRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find fridge " + id);

        fridgeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
