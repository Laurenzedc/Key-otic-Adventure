import java.util.ArrayList;
import java.util.List;

public class Characters {

    public enum CharacterType {
        DRAVEN, SELENE, ORION, KAYA
    }

    public enum Role {
        POWERHOUSE, AGILITY, INTELLECT, SURVIVAL
    }

    public static class Character {
        private final int id;
        private final String name;
        private final CharacterType type;
        private final Role role;

        // Stats
        private final int power;
        private final int intelligence;
        private final int agility;
        private final int survival;

        // Lives
        private int lives;

        // Ability
        private final String abilityName;
        private final String abilityDescription;
        private boolean abilityUsedThisLevel;

        // Characteristics
        private final String strengths;
        private final String weaknesses;

        public Character(
                int id,
                String name,
                CharacterType type,
                Role role,
                int power,
                int intelligence,
                int agility,
                int survival,
                int lives,
                String abilityName,
                String abilityDescription,
                String strengths,
                String weaknesses
        ) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.role = role;
            this.power = power;
            this.intelligence = intelligence;
            this.agility = agility;
            this.survival = survival;
            this.lives = lives;
            this.abilityName = abilityName;
            this.abilityDescription = abilityDescription;
            this.strengths = strengths;
            this.weaknesses = weaknesses;
            this.abilityUsedThisLevel = false;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public CharacterType getType() { return type; }
        public Role getRole() { return role; }
        public int getLives() { return lives; }
        public boolean isAlive() { return lives > 0; }

        public void loseLives(int amt) {
            lives -= amt;
            if (lives < 0) lives = 0;
        }

        public boolean canUseAbility() { return !abilityUsedThisLevel; }
        public void markAbilityUsed() { abilityUsedThisLevel = true; }
        public void resetAbilityForNewLevel() { abilityUsedThisLevel = false; }

        public String getAbilityName() { return abilityName; }
        public String getAbilityDescription() { return abilityDescription; }
        public String getStrengths() { return strengths; }
        public String getWeaknesses() { return weaknesses; }

        @Override
        public String toString() {
            return id + ". " + name + " [" + role + "]"
                    + "\n   Lives: " + lives
                    + "\n   Strengths: " + strengths
                    + "\n   Weaknesses: " + weaknesses
                    + "\n   Special Ability: " + abilityName + " — " + abilityDescription;
        }
    }

    public static List<Character> createDefaultCharacters() {
        List<Character> list = new ArrayList<>();

        list.add(new Character(
                1,
                "Draven Stoneforge",
                CharacterType.DRAVEN,
                Role.POWERHOUSE,
                5, 1, 3, 4, 3,
                "Titan Smash",
                "Breaks one obstacle instantly (once per level).",
                "Exceptional strength; ideal for physical/environment challenges.",
                "Terrible at riddles, symbols, and logic puzzles."
        ));

        list.add(new Character(
                2,
                "Selene Quickstep",
                CharacterType.SELENE,
                Role.AGILITY,
                2, 3, 5, 2, 3,
                "Flash Step",
                "Skip timing/pattern traps (once per level).",
                "Extremely agile; great with moving-pattern puzzles.",
                "Low power; cannot handle heavy objects."
        ));

        list.add(new Character(
                3,
                "Orion Mindweaver",
                CharacterType.ORION,
                Role.INTELLECT,
                1, 5, 2, 2, 3,
                "Mental Overclock",
                "Instantly solves riddles/cipher puzzles (once per level).",
                "Brilliant with patterns, logic, and mental puzzles.",
                "Fragile; weak to physical traps."
        ));

        list.add(new Character(
                4,
                "Kaya Wildroot",
                CharacterType.KAYA,
                Role.SURVIVAL,
                3, 2, 4, 5, 3,
                "Nature’s Insight",
                "Reveal a hidden clue/safe path once per level.",
                "Excellent with nature, environment, echo puzzles.",
                "Weak at complex symbols and logic ciphers."
        ));

        return list;
    }
}