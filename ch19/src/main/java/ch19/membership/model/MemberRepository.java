package ch19.membership.model;

import java.util.UUID;

interface MemberRepository {

    Member findBy(UUID memberId);
}
