package com.thevivek2.example.initdata;

import com.thevivek2.example.resource.client.Client;
import com.thevivek2.example.resource.client.ClientJpa;
import com.thevivek2.example.resource.client.ClientType;
import com.thevivek2.example.resource.client.ClientTypeJpa;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
@Profile("INIT_CLIENT_DATA")
public class ClientDataInit {

    private final ClientTypeJpa clientTypeJpa;
    private final ClientJpa clientJpa;

    @PostConstruct
    void createEntries() {
        log.info("**** creating few entries in DB ******");
        List<Integer> bootstrappingData = getThirtyThousand();
        bootstrappingData.parallelStream().forEach(this::extracted);
        log.info("**** done creating few entries in DB ******");
    }

    private static List<Integer> getThirtyThousand() {
        List<Integer> pointers = new ArrayList<>();
        for (int i = 0; i < 2_000; i++) {
            pointers.add(i);
        }
        return pointers;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    Client extracted(int i) {
        var type = new ClientType();
        type.setActiveStatus(i % 2 == 0 ? "A" : "I");
        type.setDescription("This is type " + i);
        type.setAddedBy(0);
        type.setName("Type " + i);
        var save = clientTypeJpa.save(type);
        Client client = new Client();
        client.setClientType(save);
        client.setContactNumber(i % 2 == 0 ? "(977)9841527953" : "+971566755481");
        client.setActiveStatus(i % 5 == 0 ? "A" : "I");
        client.setEmailId("thevivek2" + i + "@gmail.com");
        return clientJpa.save(client);
    }


}
