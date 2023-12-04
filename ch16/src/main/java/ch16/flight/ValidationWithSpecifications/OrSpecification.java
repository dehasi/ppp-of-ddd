package ch16.flight.ValidationWithSpecifications;

class OrSpecification<T> extends Specification<T> {

    private final Specification<T> first;
    private final Specification<T> second;

    OrSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override boolean isSatisfiedBy(T entity) {
        return first.isSatisfiedBy(entity) || second.isSatisfiedBy(entity);
    }
}
