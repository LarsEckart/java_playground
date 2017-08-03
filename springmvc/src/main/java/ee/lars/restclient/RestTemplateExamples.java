package ee.lars.restclient;

import ee.lars.springmvc.spittr.Spitter;
import ee.lars.springmvc.spittr.Spittle;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class RestTemplateExamples {

    public Spittle fetchFacebookProfile(String id) {
        RestTemplate rest = new RestTemplate();
        return rest.getForObject("http://localhost:8080/spittr-api/spittles/{id}",
                Spittle.class, id);
    }

    public Spittle fetchSpittle(long id) {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Spittle> response = rest.getForEntity(
                "http://localhost:8080/spittr-api/spittles/{id}",
                Spittle.class, id);
        if (response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
            //throw new NotModifiedException();
        }
        return response.getBody();
    }

    public Spitter postSpitterForObject(Spitter spitter) {
        RestTemplate rest = new RestTemplate();
        return rest.postForObject("http://localhost:8080/spittr-api/spitters",
                spitter, Spitter.class);
    }

    public Spitter postSpitterForResponseEntity(Spitter newSpitter) {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Spitter> response = rest.postForEntity(
                "http://localhost:8080/spittr-api/spitters",
                newSpitter, Spitter.class);
        Spitter spitter = response.getBody();
        URI url = response.getHeaders().getLocation();
        return spitter;
    }

    public String postSpitter(Spitter spitter) {
        RestTemplate rest = new RestTemplate();
        return rest.postForLocation(
                "http://localhost:8080/spittr-api/spitters",
                spitter).toString();
    }

    public Spitter useExchangeGet(long spitterId) {
        RestTemplate rest = new RestTemplate();

        ResponseEntity<Spitter> response = rest.exchange(
                "http://localhost:8080/spittr-api/spitters/{spitter}",
                HttpMethod.GET, null, Spitter.class, spitterId);
        Spitter spitter = response.getBody();
        return spitter;
    }

    public Spitter useExchangeGetWithHeader(long spitterId) {
        RestTemplate rest = new RestTemplate();

        MultiValueMap<String, String> headers =
                new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Spitter> response = rest.exchange(
                "http://localhost:8080/spittr-api/spitters/{spitter}",
                HttpMethod.GET, requestEntity, Spitter.class, spitterId);
        Spitter spitter = response.getBody();
        return spitter;
    }
}
