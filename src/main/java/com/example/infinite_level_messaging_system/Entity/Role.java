package com.example.infinite_level_messaging_system.Entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("roles")
public class Role {
    @Id
    private String id;
    private String name;
}
