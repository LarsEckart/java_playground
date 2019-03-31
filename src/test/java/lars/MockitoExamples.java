package lars;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static lars.MockitoExtension.stub;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MockitoExamples {

    @Test
    void default_way_of_using_mockito() {
        LinkedList mockedList = mock(LinkedList.class);
        when(mockedList.get(0)).thenReturn("first");

        assertThat(mockedList.get(0)).isEqualTo("first");

        verify(mockedList).get(0);
    }

    @Test
    void improved() {
        LinkedList mockedList = stub(LinkedList.class, l -> l.get(0), "first");

        assertThat(mockedList.get(0)).isEqualTo("first");

        verify(mockedList).get(0);
    }
}
