drop table stats cascade;
create table stats (
                       id bigint primary key,
                       total decimal
);

drop type gender cascade;
create type gender as enum('MALE', 'FEMALE', 'GENDERLESS');

drop table individuality;
create table individuality (
                               id bigint primary key,
                               name text,
                               shiny boolean,
                               happiness decimal,
                               gender gender
);

drop type category cascade;
create type category as enum('HP', 'ATTACK', 'SPECIAL_ATTACK', 'DEFENSE', 'SPECIAL_DEFENSE', 'SPEED');

drop function get_max_stats_id() cascade;
CREATE FUNCTION get_max_stats_id()
    RETURNS bigint
    LANGUAGE sql
    AS $$
SELECT max(id) FROM stats;
$$;

drop table base_stat;
create table base_stat (
                           id bigint primary key,
                           stats_id bigint default get_max_stats_id(),
                           category category,
                           effort_value integer,
                           individual_value integer,
                           actual_value decimal,
                           base_value decimal,
                           stage_multiplier integer,
                           is_last_modified boolean,
                           foreign key (stats_id) references stats (id)
);
