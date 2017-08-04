package ee.lars.contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ContactController {

    private final ContactRepository repository;

    @Autowired
    public ContactController(ContactRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public String home(Map<String, Object> model) {
        List<Contact> contacts = this.repository.findAll();
        model.put("contacts", contacts);
        return "home";
    }

    @PostMapping
    public String submit(Contact contact) {
        this.repository.save(contact);
        return "redirect:/";
    }

}
