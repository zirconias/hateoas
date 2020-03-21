package dz.imdrissi.labs.hateoas.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import java.util.List;

/**
 * The type Fridge represents a Fridge entity that can house a collection of {@link Item} entities.
 */
@Builder
@Data
@Entity
@RepositoryRestResource(
        exported = true,
        path = "path_fridge",
        collectionResourceRel = "rel_fridge",
        collectionResourceDescription = @Description("collection_fridge"),
        itemResourceDescription = @Description("item_fridge"),
        itemResourceRel = "_fridge"
//        ,excerptProjection =
)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Fridge {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @JsonBackReference
    @OneToMany(mappedBy = "fridge", cascade = {CascadeType.ALL})
    @ToString.Exclude
    List<Item> items;
}
