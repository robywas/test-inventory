package pl.com.bottega.inventory.domain.commands;


public class InvalidCommandException extends RuntimeException {

	private Validatable.ValidationErrors errors;

	public InvalidCommandException(Validatable.ValidationErrors errors) {
		this.errors = errors;
	}

	public Validatable.ValidationErrors getErrors() {
		return errors;
	}

}
