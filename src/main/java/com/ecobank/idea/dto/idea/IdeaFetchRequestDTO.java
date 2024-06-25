package com.ecobank.idea.dto.idea;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class IdeaFetchRequestDTO {
    private String filter = "";      // Default filter
    private String sortBy = "";   // Default sort
    private String sortDirection = "DESC";   // Default sort
    private int page = 0;  // default page number
    private int size = 10; // default page size
}
