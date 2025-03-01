package com.thevivek2.example.common.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;

public class TheVivek2ImplicitNamingStrategy extends SpringImplicitNamingStrategy {

    @Override
    public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
        return toIdentifier("FK_" + source.getTableName() + "_" +
                source.getReferencedTableName(), source.getBuildingContext());
    }

}
