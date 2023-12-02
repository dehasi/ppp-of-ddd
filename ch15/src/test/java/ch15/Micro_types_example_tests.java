package ch15;

import ch15.MicroTypes.Hours;
import ch15.MicroTypes.Hours.ContractedHours;
import ch15.MicroTypes.Hours.HoursWorked;
import ch15.MicroTypes.Hours.OvertimeHours;
import ch15.MicroTypes.OvertimeCalculator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Micro_types_example_tests {

    @Test void Calculates_overtime_hours_as_hours_additional_to_contracted() {
        var hoursWorked = new Hours(40);
        var contractedHours = new Hours(35);

        // wrap with Micro Types for contextual explicitness
        var hoursWorkedx = new HoursWorked(hoursWorked);
        var contractedHoursx = new ContractedHours(contractedHours);

        var fiveHours = new Hours(5);
        var fiveHoursOvertime = new OvertimeHours(fiveHours);

        var result = new OvertimeCalculator().calculate(hoursWorkedx, contractedHoursx);
        assertThat(fiveHoursOvertime).isEqualTo(result);
    }
}
