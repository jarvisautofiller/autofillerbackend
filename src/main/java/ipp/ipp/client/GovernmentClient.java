package ipp.ipp.client;

import org.springframework.stereotype.Component;
import ipp.ipp.model.User;

@Component
public class GovernmentClient {

    public User getUserDetails(String id) {

        User user = new User();
        user.setFirstName("Jarvis");
        user.setLastName("Doe");
        user.setPhoneNumber("123-456-7890");
        user.setAge(30);
        user.setAddress("123 Main St, Springfield");
        user.setEmail("jarvis@gmail.com");

        return user;
        // TODO Auto-generated method stub
        
    }

}
