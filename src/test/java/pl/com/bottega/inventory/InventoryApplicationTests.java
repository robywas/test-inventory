package pl.com.bottega.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.data.MapEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.data.MapEntry.entry;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InventoryApplicationTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private DbCleaner dbCleaner;

  @Autowired
  private ObjectMapper objectMapper;

  @Before
  public void cleanDb() {
    dbCleaner.clean();
  }

  @Test
  public void succesfulPurchase() throws Exception {
    inflate("Socks", 10);
    inflate("Shoes", 20);
    inflate("Trousers", 50);

    mvc.perform(post("/purchase").
        content(
            toBuy(
                entry("Socks", 2),
                entry("Shoes", 2),
                entry("Trousers", 14)
            )
        ).
        contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isOk()).
        andExpect(jsonPath("$.success").value(true)).
        andExpect(jsonPath("$.purchasedProducts.Socks").value(2)).
        andExpect(jsonPath("$.purchasedProducts.Shoes").value(2)).
        andExpect(jsonPath("$.purchasedProducts.Trousers").value(14));
  }

  private String toBuy(MapEntry<String, Integer>... items) throws Exception {
    Map<String, Integer> map = new HashMap<>();
    for (MapEntry<String, Integer> item : items)
      map.put(item.getKey(), item.getValue());
    return objectMapper.writeValueAsString(map);
  }


  private void inflate(String skuCode, Integer count) throws Exception {
    Map<String, Integer> content = new HashMap<>();
    content.put("skuCode", count);
    mvc.perform(post("/inventory").
        content(objectMapper.writeValueAsString(content)).
        contentType(MediaType.APPLICATION_JSON)).
        andExpect(status().isOk());
  }
}