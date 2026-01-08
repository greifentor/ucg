package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import de.ollie.ucg.core.model.Model;
import java.util.List;
import lombok.Generated;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Generated
@Getter
@RequiredArgsConstructor
public class ModelWrapper {

	@NonNull
	private final Model model;

	@NonNull
	private List<ClassModelWrapper> classes;
}
