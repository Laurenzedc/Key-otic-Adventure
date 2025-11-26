import java.util.Collections;
import java.util.List;

public class Levels {

    public abstract static class LevelBase {

        // Must return puzzle list
        public abstract List<Puzzles.Puzzle> getPuzzles();

        // Used by Game.java to select direction pool
        public abstract int getLevelNumber();

        // Opening narration
        public abstract String getNarration();

        // Note: Level rewards are handled in Game.java,
        // so this always returns null now.
        public Items.Item getLevelReward() { return null; }

        // Main system
        public boolean play(Game game) {

            System.out.println("\n" + getNarration());
            game.getInventory().showInventory();

            List<Puzzles.Puzzle> all = getPuzzles();
            Collections.shuffle(all);

            // Select 3 random puzzles per level
            List<Puzzles.Puzzle> selected = all.subList(0, 3);

            for (Puzzles.Puzzle puzzle : selected) {

                boolean solved = puzzle.play(game);

                if (!solved) {
                    // If hero died and no one is left
                    if (!game.getCurrentHero().isAlive()) {
                        if (!game.hasAnyLivingCharacter()) return false;
                    }

                    System.out.println("\nThe obstacle reforms… try again.\n");

                    // Retry puzzle using loop to avoid deep recursion
                    return play(game);
                }

                // After each puzzle, choose direction
                game.chooseDirection(getLevelNumber());
            }

            System.out.println("\nYou successfully complete Level " + getLevelNumber() + "!");
            return true;
        }
    }


    // ---------------- LEVELS 1–5 ---------------- //

    public static class Level1 extends LevelBase {
        @Override public List<Puzzles.Puzzle> getPuzzles() { return Puzzles.level1Puzzles(); }
        @Override public int getLevelNumber() { return 1; }
        @Override public String getNarration() {
            return """
                   🌿 The jungle awakens around you.
                   The air vibrates with ancient life.
                   """;
        }
    }

    public static class Level2 extends LevelBase {
        @Override public List<Puzzles.Puzzle> getPuzzles() { return Puzzles.level2Puzzles(); }
        @Override public int getLevelNumber() { return 2; }
        @Override public String getNarration() {
            return """
                   🌊 The River of Trials flows with shifting currents.
                   Reflections twist into unfamiliar shapes.
                   """;
        }
    }

    public static class Level3 extends LevelBase {
        @Override public List<Puzzles.Puzzle> getPuzzles() { return Puzzles.level3Puzzles(); }
        @Override public int getLevelNumber() { return 3; }
        @Override public String getNarration() {
            return """
                   ❄️ Frost and silence echo through the cavern.
                   Every surface glows with ancient runes.
                   """;
        }
    }

    public static class Level4 extends LevelBase {
        @Override public List<Puzzles.Puzzle> getPuzzles() { return Puzzles.level4Puzzles(); }
        @Override public int getLevelNumber() { return 4; }
        @Override public String getNarration() {
            return """
                   🪨 The Ravine of Echoes roars with unseen winds.
                   Sounds twist into warnings and illusions.
                   """;
        }
    }

    public static class Level5 extends LevelBase {
        @Override public List<Puzzles.Puzzle> getPuzzles() { return Puzzles.level5Puzzles(); }
        @Override public int getLevelNumber() { return 5; }
        @Override public String getNarration() {
            return """
                   🌀 The Heart of the Game pulses with cosmic energy.
                   Symbols float and rearrange in the air.
                   """;
        }
    }
}
