package dz.imdrissi.labs.hateoas.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;

/**
 * The type Item which represents any item which can be contained within a {@link Fridge}
 */
@Builder
@Data
@Entity
@RepositoryRestResource(
        exported = true,
        path = "path_item",
        collectionResourceRel = "rel_item",
        collectionResourceDescription = @Description("collection_item"),
        itemResourceDescription = @Description("item_item"),
        itemResourceRel = "_item"
//        ,excerptProjection =
)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String name;

    @ManyToOne
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

}