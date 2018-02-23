package pl.com.bottega.inventory.domain.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface Validatable {

    void validate(ValidationErrors errors);

    class ValidationErrors {

        private Map<String, Set<String>> errors = new HashMap<>();

        public void add(String fieldName, String errorMessage) {
            Set<String> fieldErrors = errors.getOrDefault(fieldName, new HashSet<>());
            fieldErrors.add(errorMessage);
            errors.putIfAbsent(fieldName, fieldErrors);
        }

        public boolean isValid() {
            return errors.isEmpty();
        }

        public Map<String, Set<String>> getErrors() {
            return new HashMap<>(errors);
        }

    }

    default void validatePresenceOf(String value, String name, ValidationErrors errors) {
        if(value == null || value.length() == 0)
            errors.add(name, "can't be blank");
    }

    default void validatePresenceOf(Object value, String name, ValidationErrors errors) {
        if(value == null)
            errors.add(name, "can't be blank");
    }

}
