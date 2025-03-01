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

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT_TYPE_ID")
    private ClientType clientType;

    public static Client of(ClientDTO dto) {
        Client client = new Client();
        client.setContactNumber(dto.getContactNumber());
        client.setEmailId(dto.getContactNumber());
        return client;
    }
}
