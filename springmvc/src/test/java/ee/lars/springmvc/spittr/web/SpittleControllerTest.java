package ee.lars.springmvc.spittr.web;

import ee.lars.springmvc.spittr.Spittle;
import ee.lars.springmvc.spittr.data.SpittleRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SpittleControllerTest {

    private static final int MAX_ID = 238900;
    private static final int FITHTY = 50;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private SpittleRepository mockRepository;

    private MockMvc mockMvc;

    @Before
    public void initialize() throws Exception {
        SpittleController controller = new SpittleController(this.mockRepository);
        this.mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                .build();
    }

    @Test
    public void shouldShowRecentSpittles() throws Exception {
        // given
        List<Spittle> expectedSpittles = this.createSpittleList(20);
        given(this.mockRepository.findSpittles(Long.MAX_VALUE, 20))
                .willReturn(expectedSpittles);

        // when then
        this.mockMvc.perform(get("/spittles"))
                    .andExpect(view().name("spittles"))
                    .andExpect(model().attributeExists("spittleList"))
                    .andExpect(model().attribute("spittleList",
                       hasItems(expectedSpittles.toArray())));
    }

    @Test
    public void shouldShowPagedSpittles() throws Exception {
        // given
        List<Spittle> expectedSpittles = createSpittleList(50);
        given(this.mockRepository.findSpittles(MAX_ID, FITHTY))
                .willReturn(expectedSpittles);

        // when then
        this.mockMvc.perform(get("/spittles?max=" + MAX_ID + "&count=" + FITHTY))
                    .andExpect(view().name("spittles"))
                    .andExpect(model().attributeExists("spittleList"))
                    .andExpect(model().attribute("spittleList",
                       hasItems(expectedSpittles.toArray())));
    }

    private List<Spittle> createSpittleList(int count) {
        List<Spittle> spittles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            spittles.add(new Spittle("Spittle " + i, LocalDate.now()));
        }
        return spittles;
    }

    @Test
    public void testSpittle() throws Exception {
        // given
        Spittle expectedSpittle = new Spittle("Hello", LocalDate.now());
        given(this.mockRepository.findOne(12345)).willReturn(expectedSpittle);

        // when then
        this.mockMvc.perform(get("/spittles/12345"))
                    .andExpect(view().name("spittle"))
                    .andExpect(model().attributeExists("spittle"))
                    .andExpect(model().attribute("spittle", expectedSpittle));
    }
}
