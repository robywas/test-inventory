package pl.com.bottega.inventory.domain.commands;

public interface Command {

    default void validate(Validatable.ValidationErrors errors){

    }

    default void validatePresence(Validatable.ValidationErrors errors, String field, Object value){
        if (value == null) {
            errors.add(field, "is required");
        }
    }

    default void validateMinLength(Validatable.ValidationErrors errors, String field, String value, int minLength) {
        if (value != null && value.length() < minLength) {
            errors.add(field, "has invalid format");
        }

    }

    default void validatePresenceOf(String value, String name, Validatable.ValidationErrors errors) {
        if(value == null || value.length() == 0)
            errors.add(name, "can't be blank");
    }

    default void validatePresenceOf(Object value, String name, Validatable.ValidationErrors errors) {
        if(value == null)
            errors.add(name, "can't be blank");
    }



}
