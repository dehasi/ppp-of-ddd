
CREATE TABLE event_sources (
  id        VARCHAR NOT NULL PRIMARY KEY,
  version   INTEGER NOT NULL,
  type      VARCHAR NOT NULL
);

CREATE TABLE events (
  id              VARCHAR NOT NULL PRIMARY KEY,
  version         INTEGER NOT NULL,
  name            VARCHAR NOT NULL,
  event_source_id VARCHAR NOT NULL,
  data            VARCHAR NOT NULL,
  created_at      DATETIME(6)  NOT NULL,

  FOREIGN KEY (event_source_id) REFERENCES event_sources(id),
  UNIQUE (id, version),
  UNIQUE (event_source_id, version)
);

CREATE TABLE snapshots (
   event_source_id  VARCHAR NOT NULL,
   version          INTEGER NOT NULL,

   type             VARCHAR NOT NULL,
   data             VARCHAR NOT NULL,
   created_at       DATETIME(6)  NOT NULL,

  FOREIGN KEY (event_source_id) REFERENCES event_sources(id)
);
