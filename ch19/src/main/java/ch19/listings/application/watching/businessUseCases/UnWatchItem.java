package ch19.listings.application.watching.businessUseCases;

import java.util.UUID;

record UnWatchItem(UUID memberId, UUID watchedItemId) {}
