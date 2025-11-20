# General Options

## Settings

### Generators

#### layer (Optional)

**Values:**
* Identifier of a layer

Set here an identifier of the application layer the generator should be related to (e. g. "core" or "jpa-persistence").

User the same identifiers to take influence of the generation process, e. g. to suppress the generation of a class for selected layers (see "Models / Classes / ignore-layers" for example).


## Models

### Classes

#### `ignore-layers`

**Values:**
* Name of a comma separated list of layer identifiers.

Sets all the layers which the generation process for the class is suppress for.