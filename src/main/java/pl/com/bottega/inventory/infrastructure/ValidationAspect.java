package pl.com.bottega.inventory.infrastructure;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.Validatable;

@Component
@Aspect
public class ValidationAspect {

	@Before( "execution(* pl.com.bottega.inventory.api..*.*(..)) && args(validatable,..)" )
	public void validate(Validatable validatable) {
		Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
		validatable.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);
	}

}
