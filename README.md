# Key-otic Adventure – A Console-Based OOP Puzzle Game
Key-otic Adventure is a story-driven Java console game inspired by escape rooms and Jumanji-style game worlds.
You play as a trapped adventurer who must complete five levels, solve puzzles, choose paths, and switch between characters to survive. Each level contains obstacles and directional choices that lead to different story outcomes, eventually allowing you to collect keys needed to open the Final Chest containing the Ultimate Gem—your only way home.

This project showcases practical applications of Object-Oriented Programming (OOP) principles, modular coding structure, puzzle classes, character management, level logic, and randomization.

## OOP Concepts Applied
### Encapsulation
- Sensitive data such as character stats, inventory, and game direction lists are declared private.
- Accessed and modified only through methods (e.g., getHP(), useAbility(), addItem()), protecting data integrity.
### Abstraction
- Each main feature is separated into its own class:
           Puzzle.java hides puzzle logic.
           Levels.java abstracts level progression.
           Items.java handles inventory operations.
- The main game does not need to know the internal details—only the methods it calls.
### Polymorphism
### Abstraction
