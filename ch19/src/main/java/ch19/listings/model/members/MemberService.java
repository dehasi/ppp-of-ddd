package ch19.listings.model.members;

import java.util.UUID;

public interface MemberService {

    Member getMember(UUID memberId);
}
