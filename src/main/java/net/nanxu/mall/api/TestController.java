package net.nanxu.mall.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController.
 *
 * @author: pan
 **/
@RestController
@RequestMapping("/test")
@Tag(name = "Test")
public class TestController {

    @Operation(summary = "test method")
    @GetMapping("/test")
    public String test() {
        return "Hello";
    }
}
