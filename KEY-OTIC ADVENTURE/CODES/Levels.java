import java.util.Collections;
import java.util.List;

public class Levels {

    public abstract static class LevelBase {

        public abstract List<Puzzles.Puzzle> getPuzzles();
        public abstract int getLevelNumber();
        public abstract String getNarration();
        public Items.Item getLevelReward() { return null; }

        public boolean play(Game game) {

            // Level narration (Game will pause inside ambient + narration)
            game.clearConsole();
            game.playLevelAmbientAnimation(getLevelNumber());
            game.displayText(getNarration());
            game.pause(); // pause after level narration

            // Show inventory
            game.showInventoryOpenAnimation();
            game.getInventory().showInventory();
            game.pause(); // pause after inventory

            // Start obstacle music
            SoundPlayer.playLoop("obstacle_level" + getLevelNumber() + ".wav");

            // Shuffle puzzles for variety
            List<Puzzles.Puzzle> all = getPuzzles();
            Collections.shuffle(all);

            int count = Math.min(3, all.size());
            List<Puzzles.Puzzle> selected = all.subList(0, count);

            for (Puzzles.Puzzle puzzle : selected) {

                // Each puzzle clears scene at start
                boolean solved = puzzle.play(game);

                if (!solved) {
                    // Hero died, check if any characters remain
                    if (!game.getCurrentHero().isAlive() && !game.hasAnyLivingCharacter()) {
                        SoundPlayer.stopLoop();
                        return false; // no one left, game over
                    }
                    // If hero is still alive or switched, continue to next puzzle
                }

                // After puzzle (solved or failed but still alive), travel
                SoundPlayer.stopLoop();
                SoundPlayer.playLoop("travel_loop.wav");

                game.showTravelAnimation(); // has its own pause
                game.chooseDirection(getLevelNumber()); // pauses at end

                SoundPlayer.stopLoop();
                SoundPlayer.playLoop("obstacle_level" + getLevelNumber() + ".wav");
            }

            // LEVEL COMPLETE
            SoundPlayer.stopLoop();
            SoundPlayer.playSound("level_complete.wav");

            game.clearConsole();
            game.displayText("");
            game.displayText("You successfully complete Level " + getLevelNumber() + "!");
            game.pause();

            return true;
        }
    }

    // ---------------- LEVELS 1-5 ---------------- //

    public static class Level1 extends LevelBase {
        @Override public List<Puzzles.Puzzle> getPuzzles() { return Puzzles.level1Puzzles(); }
        @Override public int getLevelNumber() { return 1; }
        @Override public String getNarration() {
            return """
                   The jungle awakens around you.
                   The air vibrates with ancient life.
                   """;
        }
    }

    public static class Level2 extends LevelBase {
        @Override public List<Puzzles.Puzzle> getPuzzles() { return Puzzles.level2Puzzles(); }
        @Override public int getLevelNumber() { return 2; }
        @Override public String getNarration() {
            return """
                   The River of Trials flows with shifting currents.
                   Reflections twist into unfamiliar shapes.
                   """;
        }
    }

    public static class Level3 extends LevelBase {
        @Override public List<Puzzles.Puzzle> getPuzzles() { return Puzzles.level3Puzzles(); }
        @Override public int getLevelNumber() { return 3; }
        @Override public String getNarration() {
            return """
                   Frost and silence echo through the cavern.
                   Every surface glows with ancient runes.
                   """;
        }
    }

    public static class Level4 extends LevelBase {
        @Override public List<Puzzles.Puzzle> getPuzzles() { return Puzzles.level4Puzzles(); }
        @Override public int getLevelNumber() { return 4; }
        @Override public String getNarration() {
            return """
                   The Ravine of Echoes roars with unseen winds.
                   Sounds twist into warnings and illusions.
                   """;
        }
    }

    public static class Level5 extends LevelBase {
        @Override public List<Puzzles.Puzzle> getPuzzles() { return Puzzles.level5Puzzles(); }
        @Override public int getLevelNumber() { return 5; }
        @Override public String getNarration() {
            return """
                   The Heart of the Game pulses with deep energy.
                   Symbols float and rearrange in the air.
                   """;
        }
    }
}
