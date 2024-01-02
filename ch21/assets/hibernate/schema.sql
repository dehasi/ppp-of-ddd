CREATE SCHEMA ch21;

CREATE TABLE auctions (
    id               VARCHAR(255) NOT NULL PRIMARY KEY,
    auction_ends     DATETIME(6)  NULL,
    bidder_member_id VARCHAR(255) NULL,
    bid              DOUBLE       NULL,
    current_price    DOUBLE       NULL,
    maximum_bid      DOUBLE       NULL,
    time_of_bid      DATETIME(6)  NULL
);

CREATE TABLE bids (
    id          VARCHAR(255) NOT NULL PRIMARY KEY,
    bid         DOUBLE       NULL,
    auction_id  VARCHAR(255) NULL,
    bidder_id   VARCHAR(255) NULL,
    time_of_bid DATETIME(6)  NULL
);

