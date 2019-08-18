package lars.junit5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionTest {

    @Test
    void shouldBuyBread() {
        me.goToBakery(bakery);
        bakery.setBreadAmount(0);

        assertThrows(BreadOutOfStockException.class, () -> me.buyBread());
    }

    private Buyer me = new Buyer();
    private Bakery bakery = new Bakery();

    private class BreadOutOfStockException extends Exception {

    }

    private class Buyer {

        public void goToBakery(Bakery bakery) {

        }

        public void buyBread() throws BreadOutOfStockException {
            throw new BreadOutOfStockException();
        }
    }

    private class Bakery {

        public void setBreadAmount(int i) {

        }
    }
}
