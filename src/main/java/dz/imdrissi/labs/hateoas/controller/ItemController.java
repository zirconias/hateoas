package dz.imdrissi.labs.hateoas.controller;

import dz.imdrissi.labs.hateoas.entity.item.*;
import dz.imdrissi.labs.hateoas.repository.ItemRepository;
import dz.imdrissi.labs.hateoas.entity.item.ItemModelAssembler;
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

//todo: externalize exception messages (using resources)
//todo: hateoas collection name
@RestController
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemModelAssembler itemAssembler;

    public ItemController(ItemRepository itemRepository, ItemModelAssembler itemAssembler) {
        this.itemRepository = itemRepository;
        this.itemAssembler = itemAssembler;
    }

    @GetMapping("/items")
    public CollectionModel<EntityModel<ItemProjection>> all(@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("id"));
        Page<ItemProjection> pagedResult = itemRepository.findAllBasicData(paging);
        List<EntityModel<ItemProjection>> items;

        if (pagedResult.hasContent()) {
            items = pagedResult.getContent().stream()
                    .map(itemAssembler::toModel)
                    .collect(Collectors.toList());
        } else {
            items = new ArrayList<EntityModel<ItemProjection>>();
        }
        return new CollectionModel<EntityModel<ItemProjection>>(items, linkTo(methodOn(ItemController.class).all(pageNum, pageSize)).withSelfRel());
    }

    @GetMapping("/itemsz")
    public CollectionModel<ItemModel> allz(@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("id"));
        Page<ItemProjection> pagedResult = itemRepository.findAllBasicData(paging);
        List<EntityModel<ItemProjection>> items;

        if (pagedResult.hasContent()) {
            items = pagedResult.getContent().stream()
                    .map(itemAssembler::toModel)
                    .collect(Collectors.toList());
        } else {
            items = new ArrayList<EntityModel<ItemProjection>>();
        }
        ItemAssembler iass = new ItemAssembler();
        CollectionModel<ItemModel> collection = iass.toCollectionModel(itemRepository.findAll());
        return collection;
    }


    @PostMapping("/items")
    public ResponseEntity<?> newItem(@RequestBody Item newItem) throws URISyntaxException {

        EntityModel<Item> entityModel = itemAssembler.toModel(itemRepository.save(newItem));

        //Return HTTP 201 Created status message
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @GetMapping("/items/{id}")
    public EntityModel<Item> one(@PathVariable Long id) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        return itemAssembler.toModel(item);
    }


    @PutMapping("/items/{id}")
    public ResponseEntity<?> editItem(@RequestBody Item newItem, @PathVariable Long id) throws URISyntaxException {
        Item updatedItem = itemRepository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setFridge(newItem.getFridge());
                    return itemRepository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return itemRepository.save(newItem);
                });

        EntityModel<Item> entityModel = itemAssembler.toModel(updatedItem);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        if (!itemRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item " + id);

        itemRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
