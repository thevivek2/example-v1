create table dbo.WORKSTATION_SCHEMA_HISTORY
(
    installed_rank int                                not null
        primary key,
    version        nvarchar(50),
    description    nvarchar(200)                      not null,
    type           nvarchar(20)                       not null,
    script         nvarchar(1000)                     not null,
    checksum       int,
    installed_by   nvarchar(100)                      not null,
    installed_on   datetime2 default sysutcdatetime() not null,
    execution_time int                                not null,
    success        bit                                not null
)
go

create index WORKSTATION_SCHEMA_HISTORY_S_IDX
    on dbo.WORKSTATION_SCHEMA_HISTORY (success)
go
