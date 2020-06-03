package lars.refactoring.replacaeinheritancewithdelegation;

class DelegationStudent {

  private final Person person;

  public DelegationStudent(String name) {
    person = new Person(name);
  }

  public String getName() {
    return person.getName();
  }
}
