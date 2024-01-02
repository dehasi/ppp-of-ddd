package jdbiexample.infrastructure;

public interface IUnitOfWorkRepository {

    void persistCreationOf(IAggregateDataModel entity);

    void persistUpdateOf(IAggregateDataModel entity);
}
