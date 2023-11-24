package br.com.youtube.productms.fixture;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.youtube.productms.dto.ProductDTO;
import br.com.youtube.productms.model.Product;

public class ProductTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(ProductDTO.class).addTemplate("valid", new Rule() {{
            add("name", random("Iphone 14 Pro Max", "Iphone 13 Pro Max", "Samsumg S23 Ultra"));
            add("description", "Celular de última geração e tals. Parte da frente em Ceramic Shield, Parte de trás em vidro e estrutura de alumínio");
            add("price", random(Double.class, range(0.01, 7997.48)));
        }});
    }
}
