package ch15.MicroTypes;

import ch15.MicroTypes.Hours.ContractedHours;
import ch15.MicroTypes.Hours.HoursWorked;
import ch15.MicroTypes.Hours.OvertimeHours;

// Domain service that operates on Value Objects
public class OvertimeCalculator {

    public OvertimeHours calculate(HoursWorked worked, ContractedHours contracted) {
        var overtimeHours = worked.hours.minus(contracted.hours);
        return new OvertimeHours(overtimeHours);
    }
}
