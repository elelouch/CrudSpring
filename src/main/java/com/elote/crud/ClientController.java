package com.elote.crud;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientRepository repo;
    private final ClientModelAssembler linksAssembler;

    // recordar inyectar cada servicio que se utiliza, en este caso el links assembler
    ClientController(ClientRepository repo, ClientModelAssembler linksAssembler) {
        this.repo = repo;
        this.linksAssembler = linksAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Client>> getAllClients() {
        List<EntityModel<Client>> clients = repo.findAll()
                .stream()
                .map(client -> linksAssembler.toModel(client))
                .collect(Collectors.toList());

        return CollectionModel.of(clients, linkTo(methodOn(ClientController.class).getAllClients()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Client> getOneClient(@PathVariable Long id) {
        Client client = repo.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        return linksAssembler.toModel(client);
    }

    // Response entity added, se utiliza para enviar un 201 CREATED
    // Iana link relations es una clase basada en los estandares iana
    // .created() devuelve un BodyBuilder que se va a utilizar con el model de entidad
    @PostMapping
    public ResponseEntity<?> storeClient(Client client) {
        EntityModel<Client> model = linksAssembler.toModel(repo.save(client));
        return ResponseEntity
                .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceClient(@RequestBody Client newClient, @PathVariable Long id) {
        Client updatedClient = repo.findById(id).map(client -> {
            client.setPhone(newClient.getPhone());
            client.setEmail(newClient.getEmail());
            client.setFirstName(newClient.getFirstName());
            client.setLastName(newClient.getLastName());
            return repo.save(client);
        }).orElseGet(() -> {
            newClient.setId(id);
            return repo.save(newClient);
        });
        EntityModel model = linksAssembler.toModel(updatedClient);
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
