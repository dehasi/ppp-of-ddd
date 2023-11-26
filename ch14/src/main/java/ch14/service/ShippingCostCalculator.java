package ch14.service;

import ch14.valueobject.Money;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Comparator.comparing;

class ShippingCostCalculator {

    private List<WeightBand> weightBands;
    private final WeightInKg boxWeightInKg;

    public ShippingCostCalculator(List<WeightBand> weightBands, WeightInKg boxWeightInKg) {
        this.weightBands = weightBands;
        this.boxWeightInKg = boxWeightInKg;
    }

    public Money CalculateCostToShip(List<Consignment> consignments) {
        var weight = getTotalWeight(consignments);
        // Reverse sort list
        weightBands = weightBands.stream()
                .sorted(comparing(x -> x.forConsignmentsUpToThisWeightInKg().value()))
                .toList();

        // Get first match
        var weightBand = weightBands.stream()
                .filter(x -> x.isWithinBand(weight))
                .findFirst().get();

        return weightBand.price();
    }

    private WeightInKg getTotalWeight(List<Consignment> consignments) {
        var totalWeight = new WeightInKg(BigDecimal.ZERO);
        // Calculate the weight of the items
        for (Consignment consignment : consignments)
            totalWeight = totalWeight.add(consignment.consignmentWeight());

        // Add a box weight
        totalWeight = totalWeight.add(boxWeightInKg);
        return totalWeight;
    }

    private record WeightInKg(BigDecimal value) {
        WeightInKg add(WeightInKg weightInKg) {
            return new WeightInKg(this.value().add(weightInKg.value()));
        }
    }

    private record WeightBand(Money price, WeightInKg forConsignmentsUpToThisWeightInKg) {
        public boolean isWithinBand(WeightInKg weight) {
            return false;
        }
    }

    private record Consignment(WeightInKg consignmentWeight) {}
}
