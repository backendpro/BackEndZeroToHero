package br.com.youtube.productms.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.youtube.productms.dto.ProductDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:clear-database.sql"})
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @BeforeAll
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.youtube.productms.fixture");
    }

    @Test
    public void shouldCreateProduct() {
        ProductDTO request = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> response = service.create(request);
        assertNotNull(response.get());
        assertEquals(request.getName(), response.get().getName());
        assertEquals(request.getDescription(), response.get().getDescription());
        assertEquals(request.getPrice(), response.get().getPrice());
        assertTrue(response.get().isAvailable());
    }

    @Test
    public void shouldGetAllProducts() {
        ProductDTO request = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> response = service.create(request);
        List<ProductDTO> responses = service.getAll();

        assertNotNull(responses);
        assertEquals(responses.get(0).getName(), response.get().getName());
        assertEquals(responses.get(0).getDescription(), response.get().getDescription());
        assertEquals(responses.get(0).getPrice(), response.get().getPrice());
    }

    @Test
    public void shouldGetById() {
        ProductDTO request = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> response = service.create(request);

        Long id = response.get().getId();

        Optional<ProductDTO> responseById = service.getById(id);
        assertNotNull(responseById.get());

        assertEquals(request.getName(), responseById.get().getName());
        assertEquals(request.getDescription(), responseById.get().getDescription());
        assertEquals(request.getPrice(), responseById.get().getPrice());
        assertTrue(responseById.get().isAvailable());
    }

    @Test
    public void shouldUpdateProduct() {
        ProductDTO request = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> response = service.create(request);
        Long id = response.get().getId();

        String newDescription = "Esse com certeza é o melhor aparelho de telefone móvel já criado na história";
        request.setDescription(newDescription);

        double newPrice = 789.32;
        request.setPrice(newPrice);

        Optional<ProductDTO> updatedProductDTO = service.update(id, request);

        assertNotNull(updatedProductDTO.get());
        assertEquals(newDescription, updatedProductDTO.get().getDescription());
        assertEquals(newPrice, updatedProductDTO.get().getPrice());
    }

    @Test
    public void shouldInactivateProduct() {
        ProductDTO request = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> response = service.create(request);
        Long id = response.get().getId();

        boolean inactive = service.inactive(id);
        assertTrue(inactive);
    }

}
