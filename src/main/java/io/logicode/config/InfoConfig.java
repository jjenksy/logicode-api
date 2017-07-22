package io.logicode.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by jenksy on 7/9/17.
 * @InfoConfig class Implements the InfoContributor Interface
 * and contibute method allowing us to write out information to the /info endpoint
 */
@Component
public class InfoConfig implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("Author",
                Collections.singletonMap("name", "John Jenkins"));
    }
}
