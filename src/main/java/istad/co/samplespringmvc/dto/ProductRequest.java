package istad.co.samplespringmvc.dto;

import lombok.Data;
import lombok.ToString;

// for user input
public record ProductRequest(  String title,
         String description,
         float price,
         String imageUrl, int categoryId) {
}
