package gambling.controllers;

import javax.validation.Valid;

class RecommendAFriendController {

    private ControllerDomain.ICustomerDirectory directory;
    private ControllerDomain.IReferAFriendPolicy policy;

    // all infrastructure concerns handled by framework
    String index(int referrerId, @Valid ControllerDomain.NewAccount friend) {
        var referrer = directory.Find(referrerId);
        var newAcct = directory.Create(friend);
        policy.Apply(referrer, newAcct);

        return "redirect:/";
    }
}
