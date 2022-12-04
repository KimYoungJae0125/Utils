package utils.restdocs.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest-docs")
public class RestDocsController {

    @GetMapping("/{path}")
    public Map<String, Object> getApi(@PathVariable String path) {
        System.out.println(path);
        return new HashMap<>(){{
            put("String sample", "hello");
            put("Number sample", 1);
            put("Array sample", new ArrayList<>());
            put("Boolean sample", true);
            put("Object sample", new HashMap<>());
            put("Null sample", null);
        }};
    }
    @PostMapping
    public Map<String, Object> postApi(@RequestBody HashMap<String, Object> body) {
        return body;
    }
    @PutMapping
    public Map<String, Object> putApi(@RequestBody HashMap<String, Object> body) {
        return body;
    }
    @PatchMapping
    public Map<String, Object> patchApi(@RequestBody HashMap<String, Object> body) {
        return body;
    }
    @DeleteMapping
    public Map<String, Object> deleteApi(@RequestBody HashMap<String, Object> body) {
        return body;
    }
}
