package com.example.infinite_level_messaging_system.Repository;

import com.example.infinite_level_messaging_system.Entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Role getRoleByName(String name);

}
