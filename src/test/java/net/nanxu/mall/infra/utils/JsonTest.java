package net.nanxu.mall.infra.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

class JsonTest {
    String name = "name val";

    @Test
    void mapToObject() {
        Map<String, Object> map = Map.of("name", name);
        ToObject object = Json.mapToObject(map, ToObject.class);
        Assertions.assertEquals(name, object.getName());
    }

    @Test
    void objectToJson() {
        ToObject object = new ToObject();
        object.setName(name);
        String json = Json.objectToJson(object);
        Assertions.assertEquals("""
                {"name":"name val"}""", json);
    }

    @Test
    void jsonToObject() {
        ToObject object = Json.jsonToObject("""
                {"name":"name val"}
                """, ToObject.class);
        Assertions.assertEquals(name, object.getName());
    }

    @Test
    void jsonToObjectTryNull() {
        ToObject object = Json.jsonToObjectTryNull("""
                {"name":"name val"}
                """, ToObject.class);
        Assertions.assertNotNull(object);
        Assertions.assertEquals(name, object.getName());
    }

    @Test
    void testJsonToObject() {
        TypeReference<Map<String, String>> reference = new TypeReference<>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
        Map<String, String> object = Json.jsonToObject("""
                {"name":"name val"}
                """, reference);
        
        Assertions.assertEquals(name, object.get("name"));
    }

    @Test
    void deepCopy() {
        ToObject object = new ToObject();
        object.setName(name);
        ToObject copy = Json.deepCopy(object);
        Assertions.assertNotSame(object, copy);
    }

    @Test
    void jsonToArrayObject() {
        List<ToObject> objects = Json.jsonToArrayObject("""
                [
                  {
                    "name": "name 1"
                  },
                  {
                    "name": "name 2"
                  }
                ]
                """, ToObject.class);
        Assertions.assertEquals(2, objects.size());
        Assertions.assertEquals("name 1", objects.get(0).getName());
        Assertions.assertEquals("name 2", objects.get(1).getName());
    }

    private static class ToObject {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}