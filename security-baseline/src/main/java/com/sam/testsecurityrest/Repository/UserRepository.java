package com.sam.testsecurityrest.Repository;

import com.sam.testsecurityrest.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {

    public User findUsersById(String id);

    public Optional<User> findUsersByUsername(String username);

    public User insert(User user);

    public Long removeUserById(String id);

    public User removeUserByUsername(String username);

    public List<User> findAll();

}
