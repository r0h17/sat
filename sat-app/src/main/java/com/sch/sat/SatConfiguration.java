package com.sch.sat;

import io.codemonastery.dropwizard.rabbitmq.ConnectionFactory;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SatConfiguration extends Configuration   {
    private String defaultName = "SAT App";

    @Valid
    @NotNull
    private String smtpHost;
    @Valid
    @NotNull
    private String smtpPort;
    @Valid
    @NotNull
    private String template;
    
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty("smtpHost")
    public String getSmtpHost() {
        return smtpHost;
    }
    @JsonProperty("smtpHost")
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }
    @JsonProperty("smtpPort")
    public String getSmtpPort() {
        return smtpPort;
    }
    @JsonProperty("smtpPort")
    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }
    @JsonProperty("template")
    public String getTemplate() {
        return template;
    }
    @JsonProperty("template")
    public void setTemplate(String template) {
        this.template = template;
    }

    public DataSourceFactory getDatabase() {
        return database;
    }

    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }
}
