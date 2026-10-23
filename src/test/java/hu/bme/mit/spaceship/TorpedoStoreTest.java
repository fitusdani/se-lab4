package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    private TorpedoStore store;

    @BeforeEach
    void setUp() {
        // Minden teszt előtt egy friss, 10 torpedóval rendelkező, 0.0-s hibarátájú raktárat hozunk létre
        store = new TorpedoStore(10, 0.0);
    }

    @Test
    void fire_Success() {
        // Act
        boolean result = store.fire(1);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void testInitialization() {
        assertEquals(10, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    @DisplayName("Garantált meghibásodás: 1.0-s hibaráta esetén a tüzelés sikertelen, de a torpedó elhasználódik")
    void testFire_Failure() {
        TorpedoStore faultyStore = new TorpedoStore(10, 1.0);

        boolean result = faultyStore.fire(1);

        assertFalse(result);
        assertEquals(10, faultyStore.getTorpedoCount());
    }

    @Test
    @DisplayName("Üres raktár ellenőrzése: 0 torpedó esetén az isEmpty() értéke true")
    void testIsEmpty_WhenEmpty() {
        TorpedoStore emptyStore = new TorpedoStore(0, 0.0);

        assertTrue(emptyStore.isEmpty());
        assertEquals(0, emptyStore.getTorpedoCount());
    }

    @Test
    @DisplayName("Érvénytelen paraméter: negatív számmal történő tüzelés IllegalArgumentException-t dob")
    void testFire_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> store.fire(-1));
    }
}
