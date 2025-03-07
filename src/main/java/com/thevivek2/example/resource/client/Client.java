package com.thevivek2.example.resource.client;

import com.thevivek2.example.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name = "CLIENT")
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class Client extends BaseEntity {

    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;

    @Column(name = "EMAIL")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_TYPE_ID")
    private ClientType clientType;

    private String address;

    public static Client of(ClientDTO dto) {
        Client client = new Client();
        client.setContactNumber(dto.getContactNumber());
        return client;
    }
}
