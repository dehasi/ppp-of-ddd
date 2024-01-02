package jdbiexample.infrastructure;

import java.util.HashMap;
import java.util.Map;

public class UnitOfWork implements IUnitOfWork {

    private final Map<IAggregateDataModel, IUnitOfWorkRepository> addedEntities = new HashMap<>();
    private final Map<IAggregateDataModel, IUnitOfWorkRepository> changedEntities = new HashMap<>();

    private final System system;

    public UnitOfWork(System system) {this.system = system;}

    @Override public void registerAmended(IAggregateDataModel entity, IUnitOfWorkRepository unitOfWorkRepository) {
        changedEntities.putIfAbsent(entity, unitOfWorkRepository);
    }

    @Override public void registerNew(IAggregateDataModel entity, IUnitOfWorkRepository unitOfWorkRepository) {
        // Actually IAggregateDataModel doesn't implement equals/hashCode, can be a problem in prod
        addedEntities.putIfAbsent(entity, unitOfWorkRepository);
    }

    @Override public void commit() {
        system.beginTransaction();
        for (IAggregateDataModel entity : addedEntities.keySet()) {
            addedEntities.get(entity).persistCreationOf(entity);
        }

        for (IAggregateDataModel entity : changedEntities.keySet()) {
            changedEntities.get(entity).persistUpdateOf(entity);
        }
        system.commit();
        clear();
    }

    @Override public void clear() {
        addedEntities.clear();
        changedEntities.clear();
    }
}
