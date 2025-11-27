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

<small>
<table width="100%" style="border-collapse: collapse;" border="1">
  <tr style="background-color: #f0f0f0;">
    <th colspan="2">Draven Stoneforge (Bravestone Hero)</th>
    <th>Image</th>
  </tr>
  <tr>
    <td><b>Role</b></td>
    <td>Powerhouse / Fighter</td>
    <td rowspan="5" align="center">
      <img src="images/draven.png" alt="Draven Stoneforge" width="180">
    </td>
  </tr>
  <tr>
    <td><b>Strengths</b></td>
    <td>Very high Power<br>Immune to animal attacks once per level<br>Breaks physical barriers</td>
  </tr>
  <tr>
    <td><b>Weaknesses</b></td>
    <td>VERY low Intelligence<br>Struggles with riddles, codes, logic puzzles<br>Takes double damage from mental traps</td>
  </tr>
  <tr>
    <td><b>Special Ability</b></td>
    <td><b>Titan Smash</b>: Breaks one obstacle without solving puzzle (once per level)</td>
  </tr>
  <tr>
    <td><b>Stats</b></td>
    <td>Power: ⭐⭐⭐⭐⭐ | Intelligence: ⭐ | Agility: ⭐⭐⭐ | Survival: ⭐⭐⭐⭐ | Lives: 3</td>
  </tr>
</table>
</small>




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

