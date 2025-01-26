create table teams (
    id uuid primary key,
    name varchar not null,
    country varchar not null
);

create unique index teams_country_and_name_idx on teams (country, name);

create table players (
    id uuid primary key,
    first_name varchar not null,
    last_name varchar not null,
    preferred_jersey_number int not null,
    current_team uuid null references teams on delete set null,
    current_jersey_number int null
);

create index players_name_idx on players(first_name, last_name);
create index players_current_team_idx on players(current_team);