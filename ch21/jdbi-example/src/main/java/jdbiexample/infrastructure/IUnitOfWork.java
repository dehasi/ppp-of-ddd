package jdbiexample.infrastructure;

public interface IUnitOfWork {

    void registerAmended(IAggregateDataModel entity, IUnitOfWorkRepository unitOfWorkRepository);

    void registerNew(IAggregateDataModel entity, IUnitOfWorkRepository unitOfWorkRepository);

    void commit();

    void clear();
}
