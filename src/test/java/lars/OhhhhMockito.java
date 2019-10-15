package lars;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OhhhhMockito {

    @Test
    void default_return_of_mock() {
        MyClass mock = mock(MyClass.class);
        assertThat(mock.isAdult()).isFalse();
        assertThat(mock.isBaby()).isFalse();
        assertThat(mock.getName()).isNull();
    }

    static class MyClass{

        private boolean isAdult;
        private Boolean isBaby;
        private String name;

        public MyClass(boolean isAdult, Boolean isBaby, String name) {
            this.isAdult = isAdult;
            this.isBaby = isBaby;
            this.name = name;
        }

        public boolean isAdult() {
            return isAdult;
        }

        public Boolean isBaby() {
            return isBaby;
        }

        public String getName() {
            return name;
        }
    }
}
