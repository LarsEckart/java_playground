package ee.lars.mocking;

import java.util.List;

public interface ExpectationsCollaborator {

    String methodForAny1(String s, int i, Boolean b);

    void methodForAny2(Long l, List<String> lst);
}
