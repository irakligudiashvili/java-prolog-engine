# PROLOG Engine

## Terms

`Term` - Common interface for all terms to implement

`Atom` - Represents a constant fixed value like `john` or `bob`

`Variable` - Represents placeholders like `X` or `Y`
















## Handlers

`InputHandler` - Reads queries or facts from the user

`StorageHandler` - Saves and loads facts or rules to and from a file





## asd

`Core.Clause` - Turns terms into a string representation. Example usage:

```java
import Core.Clause;

Clause c = new Clause("parent", new Atom("john"), new Atom("bob"));
```

Converted to:

```commandline
parent(john, bob)
```

