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
<table width="100%" style="table-layout: fixed; border-collapse: collapse;" border="1">
  <thead>
    <tr style="background-color: #f0f0f0;">
      <th width="12%">Character</th>
      <th width="12%">Role</th>
      <th width="25%">Strengths</th>
      <th width="25%">Weaknesses</th>
      <th width="15%">Special Ability</th>
      <th width="5%">Power</th>
      <th width="5%">Intelligence</th>
      <th width="5%">Agility</th>
      <th width="5%">Survival</th>
      <th width="5%">Lives</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Draven Stoneforge</b><br>(Bravestone Hero)</td>
      <td>Powerhouse / Fighter</td>
      <td>Very high Power<br>Immune to animal attacks once per level<br>Breaks physical barriers</td>
      <td>VERY low Intelligence<br>Struggles with riddles, codes, logic puzzles<br>Takes double damage from mental traps</td>
      <td><b>Titan Smash</b>: Breaks one obstacle without solving puzzle (once per level)</td>
      <td>⭐⭐⭐⭐⭐</td>
      <td>⭐</td>
      <td>⭐⭐⭐</td>
      <td>⭐⭐⭐⭐</td>
      <td>3</td>
    </tr>
    <tr>
      <td><b>Selene Quickstep</b><br>(Agility Specialist)</td>
      <td>Speed / Escape Artist</td>
      <td>Dodges traps automatically<br>Perfect for timing puzzles<br>Crosses unstable terrain without falling</td>
      <td>Low Power<br>Cannot lift heavy items or break doors<br>Fails strength-based tasks unless using an item</td>
      <td><b>Flash Step</b>: Skip one timing-based puzzle</td>
      <td>⭐⭐</td>
      <td>⭐⭐⭐</td>
      <td>⭐⭐⭐⭐⭐</td>
      <td>⭐⭐</td>
      <td>3</td>
    </tr>
    <tr>
      <td><b>Orion Mindweaver</b><br>(Puzzle Master)</td>
      <td>Intelligence / Decoder</td>
      <td>Excels at riddles, clues, cipher puzzles<br>Gains extra hint for any puzzle<br>Analyzes rooms to reduce trap danger</td>
      <td>Fragile — low survival and power<br>Takes double damage from physical traps</td>
      <td><b>Mental Overclock</b>: Instantly solves ONE riddle or cipher (once per level)</td>
      <td>⭐</td>
      <td>⭐⭐⭐⭐⭐</td>
      <td>⭐⭐</td>
      <td>⭐⭐</td>
      <td>3</td>
    </tr>
    <tr>
      <td><b>Kaya Wildroot</b><br>(Tracker / Survivalist)</td>
      <td>Survival / Navigator / Nature Expert</td>
      <td>Knows safe paths automatically<br>Immune to poison, plants, and natural traps<br>Gains extra items from nature-themed puzzles</td>
      <td>Low Intelligence for complex logic puzzles<br>Cannot decode advanced symbols without help</td>
      <td><b>Nature’s Insight</b>: Reveals one hidden clue or safe path per level</td>
      <td>⭐⭐⭐</td>
      <td>⭐⭐</td>
      <td>⭐⭐⭐⭐</td>
      <td>⭐⭐⭐⭐⭐</td>
      <td>3</td>
    </tr>
  </tbody>
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

