package br.com.youtube.productms.controller;

import br.com.youtube.productms.dto.ProductDTO;
import br.com.youtube.productms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO request) {
        Optional<ProductDTO> response = service.create(request);

        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable("id") Long id) {
        Optional<ProductDTO> response = service.getById(id);

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable("id") Long id,
                                             @RequestBody @Valid ProductDTO request) {
        Optional<ProductDTO> response = service.update(id, request);

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inactive(@PathVariable("id") Long id) {
        boolean inactive = service.inactive(id);
        return inactive
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
