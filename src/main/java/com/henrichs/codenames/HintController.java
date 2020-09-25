package com.henrichs.codenames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class HintController {

    private AssociationClient associationClient;

    @Autowired
    public HintController(AssociationClient associationClient) {
        this.associationClient = associationClient;
    }

    @GetMapping("/hint")
    public boolean getHint() throws Exception {
        List<Association> wordAssociations = this.associationClient.getAssociations("horse");
        return true;
    }
}