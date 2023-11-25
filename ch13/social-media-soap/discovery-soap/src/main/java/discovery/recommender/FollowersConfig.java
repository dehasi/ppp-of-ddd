package discovery.recommender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
class FollowersConfig {

    @Bean Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("socialmedia.accountmanagement");
        return marshaller;
    }

    @Bean FollowerDirectoryClient followersClient(Jaxb2Marshaller marshaller) {
        FollowerDirectoryClient client = new FollowerDirectoryClient();
        client.setDefaultUri("http://localhost:8080/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
