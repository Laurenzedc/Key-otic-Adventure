# Key-otic Adventure – A Console-Based OOP Puzzle Game

## Overview
Key-otic Adventure is a story-driven Java console game inspired by escape rooms and Jumanji-style game worlds. You play as a trapped adventurer who must complete five levels, solve puzzles, choose paths, and switch between characters to survive. Each level includes obstacles and directional choices that lead to different story outcomes, allowing you to collect keys required to open the Final Chest containing the Ultimate Gem—your only way home.

This project demonstrates Object-Oriented Programming (OOP) concepts, modular class structure, puzzle and level logic, and randomization.

## OOP Concepts Applied

### 1. Encapsulation
- Sensitive data such as character stats, inventory, and direction lists are kept private.
- Access and modification are controlled through methods like `getHP()`, `useAbility()`, and `addItem()`.

### 2. Abstraction
- Major game features are separated into dedicated classes:
  - `Puzzle.java` handles puzzle logic.
  - `Levels.java` manages level progression.
  - `Items.java` manages inventory actions.
- The main game only interacts with exposed methods and does not see internal implementations.

### 3. Polymorphism
- Methods behave differently depending on the active character.
- Overridden methods allow characters to respond uniquely to puzzles or events.
- The game engine uses generalized method names that trigger personalized behavior at runtime.

### 4. Inheritance
- The character system is designed to support extended character types.
- Subclasses can inherit a base structure to override or enhance abilities.
- This shows potential for future expansion without rewriting core logic.

## ▶️ How to Run the Program

### **Compilation**
Open a terminal inside the project folder and type:

```bash
javac Main.java



## 📚 8. Other Sections

### **a. Future Enhancements**
- Add saving/loading system  
- Add GUI version (JavaFX)  
- Improve AI behavior for enemies  
- Add more puzzle types (logic grid, sequence decoding)  
- Add achievements and secret endings  

### **b. References**
- Oracle Java Documentation  
- StackOverflow community discussions  
- W3Schools Java tutorials  
- GeeksForGeeks OOP resources  


