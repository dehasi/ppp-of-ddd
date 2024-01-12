package ch26reporting.application.dealiership.model;

public class DealershipPerformanceStatus {
    public String DealershipName;
    public int TotalRevenue;
    public int TargetRevenue;
    public int NetProfit;
    public int TargetProfit;

    public DealershipPerformanceStatus() {}

    public DealershipPerformanceStatus(String dealershipName, int totalRevenue, int targetRevenue, int netProfit, int targetProfit) {
        DealershipName = dealershipName;
        TotalRevenue = totalRevenue;
        TargetRevenue = targetRevenue;
        NetProfit = netProfit;
        TargetProfit = targetProfit;
    }
}
