package ee.lars.springmvc.spittr.api;

import ee.lars.springmvc.spittr.Spittle;
import ee.lars.springmvc.spittr.data.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spittles")
public class SpittleApiController {

    private static final String MAX_LONG_AS_STRING = "9223372036854775807";

    private final SpittleRepository spittleRepository;

    @Autowired
    public SpittleApiController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Spittle> spittles(
            @RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING) long max,
            @RequestParam(value = "count", defaultValue = "20") int count)
    {
        return this.spittleRepository.findSpittles(max, count);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Spittle saveSpittle(@RequestBody Spittle spittle) {
        return spittleRepository.save(spittle);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> spittleById(@PathVariable long id) {
        Spittle spittle = this.spittleRepository.findOne(id);
        if (spittle == null) {
            Error error = new Error(4, "Spittle [" + id + "] not found");
            return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Spittle>(spittle, HttpStatus.OK);
    }
}
