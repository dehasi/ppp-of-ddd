package ch16.GlobalCounter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RandomEntityFactoryTest {

    @Test void each_new_entity_gets_the_next_sequential_id() {
        var entity1 = RandomEntityFactory.createEntity();
        var entity2 = RandomEntityFactory.createEntity();
        var entity3 = RandomEntityFactory.createEntity();

        assertThat(1).isEqualTo(entity1.id());
        assertThat(2).isEqualTo(entity2.id());
        assertThat(3).isEqualTo(entity3.id());
    }
}
