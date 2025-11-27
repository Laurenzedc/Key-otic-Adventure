<h1 align="center">✨ Key-otic Adventure – A Console-Based OOP Puzzle Game ✨</h1>

<p align="center">
  <img src="images/1.png">
</p>

---

## **Overview**
**Key-otic Adventure** is a story-driven Java console game inspired by escape rooms and Jumanji-style game worlds.  
You play as a trapped adventurer who must complete five levels, solve puzzles, choose paths, and switch between unique characters to survive.

Each level contains:
- 🧩 **Puzzles**
- 🧭 **Directional choices**
- 🔑 **Keys to collect**
- 📜 **Multiple outcomes**

Your ultimate goal: **Unlock the Final Chest containing the Ultimate Gem**—your only way home.

---

<h1 align="center">
  <font color="#ff6600">✨ Character Showcase ✨</font>
</h1>

<p align="center">
  <img src="images/2.png">
</p>
<h1 align="center">🧙‍♂️ Character Descriptions</h1>

| **Character** | **Role** | **Strengths** | **Weaknesses** | **Special Ability** | **Power** | **Intelligence** | **Agility** | **Survival** | **Lives** |
|---------------|---------|---------------|----------------|------------------|-----------|----------------|------------|-------------|-----------|
| **Draven Stoneforge**<br>(The Powerhouse) | Powerhouse / Fighter | - Very high Power<br>- Immune to animal attacks once per level<br>- Breaks physical barriers | - VERY low Intelligence<br>- Struggles with riddles, codes, logic puzzles<br>- Takes double damage from mental traps | **Titan Smash**: Breaks any one obstacle without solving the puzzle (once per level) | ⭐⭐⭐⭐⭐ | ⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ | 3 |
| **Selene Quickstep**<br>(The Agility Specialist) | Speed / Escape Artist | - Dodges traps automatically<br>- Perfect for timing puzzles<br>- Crosses unstable terrain without falling | - Low Power<br>- Cannot lift heavy items or break doors<br>- Fails strength-based tasks unless using an item | **Flash Step**: Skip one timing-based puzzle (falling rocks, moving platforms, quick patterns) | ⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐ | 3 |
| **Orion Mindweaver**<br>(The Puzzle Master) | Intelligence / Decoder | - Excels at riddles, clues, cipher puzzles<br>- Gains extra hint for any puzzle<br>- Analyzes rooms to reduce trap danger | - Fragile — low survival and power<br>- Takes double damage from physical traps | **Mental Overclock**: Instantly solves ONE riddle or cipher (once per level) | ⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ | 3 |
| **Kaya Wildroot**<br>(The Navigator) | Survival / Navigator / Nature Expert | - Knows safe paths automatically<br>- Immune to poison, plants, and natural traps<br>- Gains extra items from nature-themed puzzles | - Low Intelligence for complex logic puzzles<br>- Cannot decode advanced symbols without help | **Nature’s Insight**: Reveals one hidden clue or safe path per level (maze, echo tests, jungle paths) | ⭐⭐⭐ | ⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | 3 |

---

## 🧱 **OOP Concepts Applied**

### 🔒 **1. Encapsulation**
- Private data such as HP, inventory, and stats.
- Controlled access via getters/setters (`getHP()`, `addItem()`, etc.).

### 🎭 **2. Polymorphism**
- Characters override methods for unique interactions.
- Puzzle responses and abilities differ per character.

### 🧩 **3. Abstraction**
- `Puzzle.java` → Handles puzzle logic  
- `Levels.java` → Manages progression  
- `Items.java` → Inventory and item handling  
- `Game` only interacts with exposed methods.

### 🧬 **4. Inheritance**
- Character classes inherit from a base template.
- Easily extendable for future characters or abilities.

---

## ▶️ **How to Run the Program**

### **Compile**
```bash
javac Main.java


## 📚 Other Sections

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

