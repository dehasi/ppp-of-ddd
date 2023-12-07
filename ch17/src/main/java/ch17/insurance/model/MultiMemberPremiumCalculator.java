package ch17.insurance.model;

import java.util.List;

public interface MultiMemberPremiumCalculator {

    Quote calculatePremium(Policy mainPolicy, List<Member> additionalMembers);
}
