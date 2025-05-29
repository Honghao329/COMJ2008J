package team.group.myforbidden.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TreasureTest {

    @Test
    void testConstructorSetsType() throws Exception {
        // Use reflection to access the private treasureType field
        Field typeField = Treasure.class.getDeclaredField("treasureType");
        typeField.setAccessible(true);

        // Verify that constructor correctly assigns each TreasureType
        for (TreasureType expectedType : TreasureType.values()) {
            Treasure treasure = new Treasure(expectedType);
            Object actualType = typeField.get(treasure);
            assertEquals(expectedType, actualType,
                    "Constructor should set treasureType to the provided value");
        }
    }
}
