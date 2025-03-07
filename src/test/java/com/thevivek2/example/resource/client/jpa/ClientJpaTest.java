package com.thevivek2.example.resource.client.jpa;

import com.thevivek2.example.common.constant.Constants;
import com.thevivek2.example.resource.client.Client;
import com.thevivek2.example.resource.client.ClientJpa;
import com.thevivek2.example.resource.client.ClientType;
import com.thevivek2.example.resource.client.ClientTypeJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


class ClientJpaTest extends JpaTest {

    @Autowired
    private ClientTypeJpa clientTypeJpa;
    @Autowired
    private ClientJpa jpa;

    @BeforeEach
    void setUp() {
        final ClientType save = clientTypeJpa.save(type());
        final Client sample = sample();
        sample.setClientType(save);
        jpa.save(sample);
    }

    @Test
    void findAll() {
        assertThat(jpa.findAll()).hasSize(1);
    }

    private static Client sample() {
        Client clientDealerMaster = new Client();
        clientDealerMaster.setAddedDate(LocalDateTime.now());
        clientDealerMaster.setEmail("vivek.adhikari@esharesolution.com");
        return clientDealerMaster;
    }

    private static ClientType type() {
        ClientType type = new ClientType();
        type.setName("Test");
        type.setDescription("this is test client type");
        type.setActiveStatus(Constants.ACTIVE);

        return type;
    }
}