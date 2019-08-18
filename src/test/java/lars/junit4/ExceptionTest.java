package lars.junit4;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;

public class ExceptionTest {

    @Test
    public void shouldBuyBread() throws Exception {
        //given
        me.goToBakery(bakery);
        bakery.setBreadAmount(0);

        try {
            //when
            me.buyBread();
            //then
            fail();
        } catch (BreadOutOfStockException expected) {
        }
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
