package com.elote.crud;

import org.springframework.data.jpa.repository.JpaRepository;

interface ClientRepository extends JpaRepository<Client,Long> {
}
