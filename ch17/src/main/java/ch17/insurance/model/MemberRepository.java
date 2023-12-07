package ch17.insurance.model;

import java.util.List;

public interface MemberRepository {
    List<Member> get(List<Integer> memberIds);
}
