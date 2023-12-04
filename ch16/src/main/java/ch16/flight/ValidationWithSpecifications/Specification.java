package ch16.flight.ValidationWithSpecifications;

abstract class Specification<T> {

    Specification<T> Or(Specification<T> specification) {
        return new OrSpecification<T>(this, specification);
    }

    abstract boolean isSatisfiedBy(T entity);
}
