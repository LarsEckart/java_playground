package ee.lars.mocking;

public class Performer {

    private Collaborator collaborator;

    public Performer(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public void perform(Model model) {
        boolean value = collaborator.collaborate(model.getInfo());
        collaborator.receive(value);
    }
}
