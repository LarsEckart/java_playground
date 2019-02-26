package lars.katas.pos;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PointOfSaleTest {

    private Display display;
    private PriceRepository repo;
    private PointOfSale pos;

    @BeforeEach
    void setUp() {
        display = mock(Display.class);
        repo = mock(PriceRepository.class);
        pos = new PointOfSale(display, repo);
    }

    @Test
    void on_barcode_scanned_should_trigger_display_of_price() {
        // given
        given(repo.getFor("123456789")).willReturn(Optional.of(199L));

        // when
        pos.onBarcode("123456789");

        // then
        verify(display).show("1.99€");
    }

    @Test
    void on_barcode_scanned_should_trigger_display_of_error_when_no_price_for_item() {
        // given
        given(repo.getFor("123456789")).willReturn(Optional.empty());

        // when
        pos.onBarcode("123456789");

        // then
        verify(display).show("No price for 123456789");
    }
}
