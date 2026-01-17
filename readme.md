# Simple Java Prolog Engine

This project is a simple Prolog reasoning engine written in Java.
It lets users define relationships between objects using facts and rules, then ask logical questions about them.

For example, you can define who is a parent of whom, then use rules to infer more complex relationships like grandparents or ancestors.

The system supports variables, rule application and recursion.

## Features
- **Facts** - Users can add atomic facts to a knowledge base

- **Rules** - Supports Horn clauses of the form `head :- body`

- **Variables** - Allows querying the knowledge base with logical variables (e.g. `parent(X, bob)`)

- **Unification** - Matches predicates and finds valid substitutions

- **Recursion** - Supports recursion (e.g. defining `ancestor` using `parent`)

- **Interactive CLI** - Command-line interface with commands and examples (type `!help` to view)

- **Modular architecture** - Separate components for input handling, logic and output

- **Parser** - Custom parser for interpreting predicates and rules 

## Project Structure

```bash
src/
│
├── Handlers/
│   ├── InputHandler.java
│   ├── OutputHandler.java
│   └── KnowledgeBase.java
│
├── Logic/
│   ├── Predicate.java
│   ├── Rule.java
│   └── Unifier.java
│
├── Terms/
│   ├── Term.java
│   ├── Atom.java
│   └── Variable.java
│
└── Main.java
```

**Handlers/** - Contains classes responsible for user input, output formatting and interaction with the knowledge base.
- `InputHandler` - Parses user input and routes commands, facts, rules and queries
- `OutputHandler` - Formats and prints query results and error messages
- `KnowledgeBase` - Stores facts and rules, and processes queries

**Logic/** - Core reasoning engine of the project
- `Predicate` - Represents Prolog predicates with a name and `List` of arguments
- `Rule` - Represents logical rules with a head and body
- `Unifier` - Helper class with static methods that implement unification logic for matching predicates

**Terms/** - Represents logical terms used in predicates
- `Term` - Base interface
- `Atom` - Represents constant symbols (e.g. `john` or `bob`)
- `Variable` - Represents logic variables for querying and unification (e.g. `X` or `Z`)


**`Main`** - Entry point of the application that initializes the knowledge base and starts the interpreter

## How It Works

### 1. User Input Processing
- The system reads user input from the command line
- The `InputHandler` removes unnecessary spaces and determines whether the input is:
  - a fact
  - a rule
  - a query
  - or a command (`!facts`, `!rules`, `!help`, `!examples`, `!q`)
- Predicates and rules are parsed using a custom parser which:
  - Extracts predicate names and arguments
  - Distinguishes between `Atoms`(e.g. `john`) and `Variables` (e.g. `X` or `Z`)
  - Validates correct syntax before accepting input

### 2. Storing Knowledge
- Valid facts and rules are stored inside `KnowledgeBase` as a `List` of facts
- Facts are treated as unconditional truths
- Rules define logical relationships between predicates

### 3. Query Processing
- When the user provides a query (e.g. `ancestor(X, jane)`), the system recognizes that the input contains a `Variable`
- The query is passed to the reasoning engine in `KnowledgeBase`

### 4. Unification
- The `Unifier` class attempts to use unification to match predicates by:
  - Matching query predicates with stored facts and rule heads
  - Binding variables to concrete values where possible
- If a match is found, variable substitutions are recorded

### 5. Rule Evaluation & Recursion
- If a query matches a rule head, then the system will try to:
  - Satisfy all predicates in the rule body
  - Recursively evaluate other rules or facts

This allows for chain reasoning such as:

```commandline
ancestor(X, Z) :- parent(X, Y), ancestor(Y, Z)
```

### 6. Generating Answers
- All valid variable bindings are collected
- Results are formatted and printed using `OutputHandler`
- If no solutions are found, an appropriate message is displayed

## Examples

### Facts

Adding Facts

```commandline
parent(john, bob)
parent(bob, jane)
```

Querying Facts

```commandline
parent(X, bob)
parent(bob, X)
```

### Rules

Adding Rules

```commandline
grandparent(X, Z) :- parent(X, Y), parent(Y, Z)
```

Querying Rules

```commandline
grandparent(X, jane)
grandparent(john, Z)
```

### Recursion

Adding Recursion

```commandline
ancestor(X,Z) :- parent(X,Z)
ancestor(X,Z) :- parent(X,Y), ancestor(Y,Z)
```

Querying Recursion

```commandline
ancestor(X, jane)
ancestor(john, Z)
```

## Commands

- `!help`

```commandline
=== Available Commands ===

!facts       - show all facts
!rules       - show all rules
!examples    - show examples
!q           - quit
```

- `!examples`

```commandline
=== Usage Examples ===

Adding facts
    parent(john, bob)
Adding rules
    grandparent(X, Z) :- parent(X, Y), parent(Y, Z)
Adding recursion
    ancestor(X, Z) :- parent(X, Z)
    ancestor(X, Z) :- parent(X, Y), ancestor(Y, Z)
Querying
    parent(X, bob)
    parent(john, Z)
```
