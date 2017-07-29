package ee.lars.springmvc.spittr.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class HomeControllerTest {

    private HomeController controller;

    @Before
    public void initialize() throws Exception {
        this.controller = new HomeController();
    }

    @Test
    public void foo() throws Exception {
        assertThat(this.controller.home()).isEqualTo("home");
    }

    @Test
    public void test_homepage() throws Exception {
        // given
        MockMvc mockMvc = standaloneSetup(this.controller).build();

        mockMvc.perform(get("/")).andExpect(view().name("home"));
    }
}