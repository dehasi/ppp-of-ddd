package ch17.insurance.application;

import ch17.insurance.model.MemberRepository;
import ch17.insurance.model.MultiMemberPremiumCalculator;
import ch17.insurance.model.PolicyRepository;
import ch17.insurance.model.Quote;

import java.util.List;

// Application Service
class MultiMemberInsurancePremium {

    private final PolicyRepository policyRepository;
    private final MemberRepository memberRepository;

    // Domain Service
    private final MultiMemberPremiumCalculator calculator;

    public MultiMemberInsurancePremium(PolicyRepository policyRepository, MemberRepository memberRepository, MultiMemberPremiumCalculator calculator) {
        this.policyRepository = policyRepository;
        this.memberRepository = memberRepository;
        this.calculator = calculator;
    }

    public Quote GetQuote(int policyId, List<Integer> memberIds) {
        var existingMainPolicy = policyRepository.get(policyId);
        var additionalMembers = memberRepository.get(memberIds);
        // pass entities into Domain Service
        var multiMemberQuote = calculator.calculatePremium(existingMainPolicy, additionalMembers);

        return multiMemberQuote;
    }
}
