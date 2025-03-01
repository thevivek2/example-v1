package com.thevivek2.example.common.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class TheVivek2PhysicalNamingStrategy implements PhysicalNamingStrategy {
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return name == null ? null : Identifier.toIdentifier(name.getText().toUpperCase());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return name == null ? null : Identifier.toIdentifier(name.getText().toUpperCase());
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return name == null ? null : Identifier.toIdentifier(name.getText().toUpperCase());
    }

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return name == null ? null : Identifier.toIdentifier(name.getText().toUpperCase());
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return name == null ? null : Identifier.toIdentifier(name.getText().toUpperCase());
    }
}
