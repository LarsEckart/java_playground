package ee.lars.springmvc.spittr.web;

import ee.lars.springmvc.spittr.Spittle;
import ee.lars.springmvc.spittr.data.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

    private static final String MAX_LONG_AS_STRING = "9223372036854775807"; //Long.toString(Long.MAX_VALUE);

    private SpittleRepository repository;

    @Autowired
    public SpittleController(SpittleRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Spittle> spittles(
            @RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING) long max,
            @RequestParam(value = "count", defaultValue = "20") int count)
    {
        return this.repository.findSpittles(max, count);
    }

    @RequestMapping(value = "/{spittleId}", method = RequestMethod.GET)
    public String spittle(@PathVariable long spittleId, Model model) {
        final Spittle spittle = this.repository.findOne(spittleId);
        model.addAttribute(spittle);
        return "spittle";
    }

    /*
    @RequestMapping(method = RequestMethod.GET)
    public String spittles(Model model) {
        model.addAttribute(repository.findSpittles(Long.MAX_VALUE, 20));
        return "spittles";
    }

    /*
    @RequestMapping(method = RequestMethod.GET)
    public String spittles(Map model) {
        model.put("spittleList", repository.findSpittles(Long.MAX_VALUE, 20));
        return "spittles";
    }
    */
}
