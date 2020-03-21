package dz.imdrissi.labs.hateoas.repository;

import dz.imdrissi.labs.hateoas.entity.Fridge;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * The interface Fridge repository.
 */
@RepositoryRestResource(
        exported = true,
        path = "fridgez",
        collectionResourceRel = "fridgez_rel",
        collectionResourceDescription = @Description("collection_fridgez"),
        itemResourceDescription = @Description("item_fridgez"),
        itemResourceRel = "_item_fridgez"
//        ,excerptProjection =
)
public interface FridgeRepository extends PagingAndSortingRepository<Fridge, Long> {
}