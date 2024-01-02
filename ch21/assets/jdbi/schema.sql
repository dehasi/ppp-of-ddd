CREATE TABLE auctions (
    id               VARCHAR(255) NOT NULL PRIMARY KEY,
    version          INTEGER NOT NULL,
    auction_ends     DATETIME(6)  NULL,
    bidder_member_id VARCHAR(255) NULL,
    starting_price   DOUBLE       NULL,
    current_price    DOUBLE       NULL,
    maximum_bid      DOUBLE       NULL,
    time_of_bid      DATETIME(6)  NULL
);

CREATE TABLE bids (
    id          VARCHAR(255) NOT NULL PRIMARY KEY,
    bid         DOUBLE       NULL,
    auction_id  VARCHAR(255) NULL,
    bidder_id   VARCHAR(255) NULL,
    time_of_bid DATETIME(6)  NULL,
    FOREIGN KEY (auction_id) REFERENCES auctions(id)
);

-- For debug
-- DELETE FROM auctions WHERE 1=1;
-- DELETE FROM bids WHERE 1=1;
