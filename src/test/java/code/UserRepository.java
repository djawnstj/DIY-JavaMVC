package code;

import com.djawnstj.mvcframework.annotation.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class UserRepository {

    public void save(String id, User user) {

    }

    public List<User> findAll() {
        return Collections.emptyList();
    }
}
