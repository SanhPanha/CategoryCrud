package istad.co.samplespringmvc.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Category {
    private int id;
    private String title;
    private String description;
}
