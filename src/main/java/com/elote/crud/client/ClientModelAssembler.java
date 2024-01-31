package com.elote.crud.client;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClientModelAssembler implements RepresentationModelAssembler<Client, EntityModel<Client>> {
    @Override
    public EntityModel<Client> toModel(Client client) {
        return EntityModel.of(client,
                linkTo(methodOn(ClientController.class).getOneClient(client.getId())).withSelfRel(),
                linkTo(methodOn(ClientController.class).getAllClients()).withRel("clients"));
    }
}
