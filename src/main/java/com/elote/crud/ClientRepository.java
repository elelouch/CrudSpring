package com.elote.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ClientRepository extends JpaRepository<Client,Long> {
}
