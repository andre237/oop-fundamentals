package com.utility.service.impl;

import com.utility.service.contracts.TemplateService;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class LocalFileTemplateService implements TemplateService {

    private static final String TEMPLATES_LOCATION = "templates/%s.txt";
    private static final String SUB_TEMPLATES_LOCATION = "templates/sub/%s.txt";

    @Override
    public String getTemplate(String templateKey) {
        // look up from sub templates first
        final var possibilities = Arrays.asList(
                String.format(SUB_TEMPLATES_LOCATION, templateKey),
                String.format(TEMPLATES_LOCATION, templateKey)
        );

        for (String t : possibilities) {
            try {
                URL resource = this.getClass().getClassLoader().getResource(t);
                if (resource != null) {
                    return Files.readString(Paths.get(resource.toURI()), StandardCharsets.UTF_8);
                }
            } catch (Exception ex) {/*continue*/}
        }

        return null;
    }

    @Override
    public String getImplIdentifier() {
        return "local-file";
    }
}
