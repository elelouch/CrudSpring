package com.elote.crud;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientRepository repo;

    ClientController(ClientRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Client> getUsers() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Client oneClient(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    @PostMapping
    public Client oneClient(Client client) {
        return repo.save(client);
    }

    @PutMapping("/{id}")
    public Client replaceClient(@RequestBody Client newClient, @PathVariable Long id) {
        return repo.findById(id).map(client -> {
            client.setPhone(newClient.phone);
            client.setEmail(newClient.email);
            client.setUsername(newClient.username);
            return repo.save(client);
        }).orElseGet(() -> {
            newClient.setId(id);
            return repo.save(newClient);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        repo.deleteById(id);
    }

}
