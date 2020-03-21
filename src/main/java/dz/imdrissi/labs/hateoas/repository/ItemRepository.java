package dz.imdrissi.labs.hateoas.repository;

import dz.imdrissi.labs.hateoas.entity.item.Item;
import dz.imdrissi.labs.hateoas.entity.item.ItemProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * The interface Item repository.
 */
@RepositoryRestResource(
        exported = true,
        path = "itemz",
        collectionResourceRel = "rel_itemz",
        collectionResourceDescription = @Description("collection_itemz"),
        itemResourceDescription = @Description("item_itemz"),
        itemResourceRel = "_item_itemz"
//        ,excerptProjection =
)
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

    @Query(value = "select id, name from item", nativeQuery = true)
    Page<ItemProjection> findAllBasicData(Pageable pageable);
}