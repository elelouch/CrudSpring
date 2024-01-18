package com.elote.crud;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("users/")
public class UserController {
    private final UserRepository repo;

    UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<User> getUsers() {
        return repo.findAll();
    }

    @GetMapping("{id}")
    public User getOnlyOneUser(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping
    public User postUser(User user) {
        return repo.save(user);
    }

    @PutMapping("{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return repo.findById(id).map(user -> {
            user.setPhone(newUser.phone);
            user.setEmail(newUser.email);
            user.setUsername(newUser.username);
            return repo.save(user);
        }).orElseGet(() -> {
            newUser.setId(id);
            return repo.save(newUser);
        });
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        repo.deleteById(id);
    }

}
