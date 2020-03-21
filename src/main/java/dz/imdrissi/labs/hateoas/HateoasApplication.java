package dz.imdrissi.labs.hateoas;

import dz.imdrissi.labs.hateoas.entity.Fridge;
import dz.imdrissi.labs.hateoas.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Arrays;

@Slf4j
@EnableJpaRepositories
@SpringBootApplication
public class HateoasApplication {


    private PagingAndSortingRepository<Fridge, Long> fridgeRepo;
    private PagingAndSortingRepository<Item, Long> itemRepo;

    public HateoasApplication(PagingAndSortingRepository<Fridge, Long> fridgeRepo, PagingAndSortingRepository<Item, Long> itemRepo) {
        this.fridgeRepo = fridgeRepo;
        this.itemRepo = itemRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(HateoasApplication.class, args);
    }


    @EventListener
    public void onReady(ApplicationReadyEvent e) {
        log.info("Init database with additional data..");
//        thePersonRepo.saveAll(
//                range(0, 10)
//                        .mapToObj(i -> new ThePerson("Person" + i, LocalDate.of(1970 + 5 * i, i + 1, i + 1)))
//                        .collect(toList())
//        );

        Fridge fridge1 = Fridge.builder().name("fridge 1").build();
        Fridge fridge2 = Fridge.builder().name("fridge 2").build();

        Item item1 = Item.builder().name("cola").fridge(fridge1).build();
        Item item2 = Item.builder().name("cola").fridge(fridge1).build();
        Item item3 = Item.builder().name("cola").fridge(fridge1).build();
        Item item4 = Item.builder().name("cola").fridge(fridge1).build();
        Item item5 = Item.builder().name("milk").fridge(fridge1).build();
        Item item6 = Item.builder().name("eggs").fridge(fridge1).build();
        Item item7 = Item.builder().name("eggs").fridge(fridge2).build();
        Item item8 = Item.builder().name("eggs").fridge(fridge2).build();
        Item item9 = Item.builder().name("cola").fridge(fridge2).build();

        fridgeRepo.saveAll(Arrays.asList(fridge1, fridge2));

        itemRepo.saveAll(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8, item9));
    }

}
