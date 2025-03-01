package com.thevivek2.example.resource.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientJpa extends
        JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
}
