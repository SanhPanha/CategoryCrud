package istad.co.samplespringmvc.repository;


import istad.co.samplespringmvc.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository {
    private List<Category> categories = new ArrayList<>(){{
        add(Category.builder()
                .id(1)
                .title("Electronic")
                .description("All electronic compartment!")
                .build());
        add(Category.builder()
                .id(2)
                .title("Food")
                .description("Stuff that you can eat!")
                .build());
    }};

    public List<Category> getAllCategories(){
        return categories;
    }

    public Category addCategory(Category category){
        categories.add(category);
        return category;
    }
}
