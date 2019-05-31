package ch.sebooom.blockchain.application;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.security.Security;
import java.util.Arrays;
import java.util.List;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
@SpringBootApplication
@ComponentScan(basePackages = "ch.sebooom.blockchain")
@EnableScheduling
public class Application {


    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());



    @Bean
    public List<String> portsToScans () {
        return Arrays.asList("9090","9091","9092","9093","9094","9095",
                "9096","9097","9098","9099");
    }


    public  static void main(String args[]) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider

         SpringApplication.run(Application.class);
    }

}
