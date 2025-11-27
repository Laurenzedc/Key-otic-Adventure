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

---

## 🟥 **CHARACTER 1 — Draven Stoneforge (The Bravestone-Inspired Hero)**
**Role:** Powerhouse / Fighter

### 🛡️ Strengths
- Very high Power  
- Immune to animal attacks once per level  
- Breaks physical barriers (vines, rocks, doors)  

### 💀 Weaknesses
- VERY low Intelligence  
- Struggles with riddles, codes, or logic puzzles  
- Takes double damage from mental traps  

### ⚡ Special Ability — **"Titan Smash"**
Breaks any one obstacle without solving the puzzle (usable once per level).

### 📊 Stats
- **Power:** ⭐⭐⭐⭐⭐ (5/5)  
- **Intelligence:** ⭐ (1/5)  
- **Agility:** ⭐⭐⭐ (3/5)  
- **Survival:** ⭐⭐⭐⭐ (4/5)

**❤️ Lives:** 3

---

## 🟦 **CHARACTER 2 — Selene Quickstep (Agility Specialist)**
**Role:** Speed / Escape Artist

### 🛡️ Strengths
- Dodges traps automatically  
- Perfect for timing puzzles  
- Crosses unstable terrain without falling  

### 💀 Weaknesses
- Low Power  
- Cannot lift heavy items or break doors  
- Fails strength-based tasks unless using an item  

### ⚡ Special Ability — **"Flash Step"**
Skip one timing-based puzzle (falling rocks, moving platforms, quick patterns).

### 📊 Stats
- **Power:** ⭐⭐ (2/5)  
- **Intelligence:** ⭐⭐⭐ (3/5)  
- **Agility:** ⭐⭐⭐⭐⭐ (5/5)  
- **Survival:** ⭐⭐ (2/5)

**❤️ Lives:** 3

---

## 🟩 **CHARACTER 3 — Orion Mindweaver (Puzzle Master)**
**Role:** Intelligence / Decoder

### 🛡️ Strengths
- Excels at riddles, clues, and cipher puzzles  
- Automatically gains an extra hint for any puzzle  
- Analyzes rooms to reduce trap danger  

### 💀 Weaknesses
- Fragile — low survival and power  
- Takes double damage from physical traps  

### ⚡ Special Ability — **"Mental Overclock"**
Instantly solves ONE riddle or cipher (once per level).

### 📊 Stats
- **Power:** ⭐ (1/5)  
- **Intelligence:** ⭐⭐⭐⭐⭐ (5/5)  
- **Agility:** ⭐⭐ (2/5)  
- **Survival:** ⭐⭐ (2/5)

**❤️ Lives:** 3

---

## 🟨 **CHARACTER 4 — Kaya Wildroot (Tracker / Survivalist)**
**Role:** Survival / Navigator / Nature Expert

### 🛡️ Strengths
- Knows safe paths automatically  
- Immune to poison, plants, and natural traps  
- Gains extra items from nature-themed puzzles  

### 💀 Weaknesses
- Low Intelligence for complex logic puzzles  
- Cannot decode advanced symbols without help  

### ⚡ Special Ability — **"Nature’s Insight"**
Reveals one hidden clue or safe path per level  
(helps in maze, echo tests, jungle paths).

### 📊 Stats
- **Power:** ⭐⭐⭐ (3/5)  
- **Intelligence:** ⭐⭐ (2/5)  
- **Agility:** ⭐⭐⭐⭐ (4/5)  
- **Survival:** ⭐⭐⭐⭐⭐ (5/5)

**❤️ Lives:** 3

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

