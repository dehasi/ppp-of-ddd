package hibernateexample.application.businessUseCases;

import java.time.LocalDateTime;

public record NewAuctionRequest(
        double startingPrice,
        LocalDateTime endsAt
) {}
