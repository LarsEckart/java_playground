package lars.executearound;

public class Sample {

  public static void main(String[] args) {
    Resource.use(Resource::fn1);
    int result = Resource.get(Resource::fn2);
    System.out.println(result);
  }
}
