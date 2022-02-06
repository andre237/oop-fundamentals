package com.utility.api;

import com.utility.service.PlaceholderResolverService;
import com.utility.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {

    private final TemplateService templateService;
    private final PlaceholderResolverService resolverService;

    @Autowired
    public EmailController(TemplateService templateService,
                           PlaceholderResolverService resolverService) {
        this.templateService = templateService;
        this.resolverService = resolverService;
    }

    @GetMapping("test")
    public ResponseEntity<String> testService() {
        System.out.println(templateService.getTemplate("test"));
        return ResponseEntity.ok("test");
    }

}
