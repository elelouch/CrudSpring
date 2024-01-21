package com.elote.crud;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientRepository repo;

    ClientController(ClientRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public CollectionModel<EntityModel<Client>> getAllClients() {
        List<EntityModel<Client>> clients = repo.findAll().stream().map(client -> EntityModel.of(client,
                        linkTo(methodOn(ClientController.class).getOneClient(client.getId())).withSelfRel(),
                        linkTo(methodOn(ClientController.class).getAllClients()).withRel("employees")))
                .collect(Collectors.toList());

        return CollectionModel.of(clients, linkTo(methodOn(ClientController.class).getAllClients()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Client> getOneClient(@PathVariable Long id) {
        Client client = repo.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        // Obtiene el metodo del controlador y arma a partir de este un link hacia
        // el mismo cliente, linkTo tambien funciona con metodos
        return EntityModel.of(client,
                linkTo(methodOn(ClientController.class).getOneClient(id)).withSelfRel(),
                linkTo(methodOn(ClientController.class).getAllClients()).withRel("clients"));
    }

    @PostMapping
    public Client storeClient(Client client) {
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
