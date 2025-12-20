# Velocity Templates

## Options

Options kann be set via properties in the model YML file.

### Model

#### Properties

##### `core-model-package` (Optional)

**Layer:**
* core, jpa-persistence

**Values:**
* A path to the 

Sets an alternative field name for the database access differing from the attribute name.


#### Attribute

##### `alternative-db-field-name` (Optional)

**Layer:**
* jpa-persistence

**Values:**
* *alternative field name*

Sets an alternative field name for the database access differing from the attribute name.


##### `key-generation` (Optional)

**Layer:**
* jpa-persistence

**Values:**
* `sequence`

Marks an id element as to generate with sequence annotations.


##### `id` (Optional)

**Layer:**
* jpa-persistence

**Values:**
* None (to set: `true`)

Shows, that the attribute is a key attribute in the database.


##### `nullable` (Optional)

**Layer:**
* jpa-persistence

**Values:**
* None (to set: `false`)

Marks an attribute as not nullable, when set to `false`.


##### `project-type` (Optional)

**Layer:**
* jpa-persistence

**Values:**
* None (to set: `true`)

Marks an attribute as a type of the project defined also in the model file.
