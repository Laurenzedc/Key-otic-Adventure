import java.util.Collections;
import java.util.List;

public class Levels {

    public abstract static class LevelBase {

        public abstract List<Puzzles.Puzzle> getPuzzles();
        public abstract int getLevelNumber();
        public abstract String getNarration();
        public Items.Item getLevelReward() { return null; }

        public boolean play(Game game) {

            System.out.println("\n" + getNarration());
            game.getInventory().showInventory();

            SoundPlayer.playLoop("obstacle_level" + getLevelNumber() + ".wav");

            List<Puzzles.Puzzle> all = getPuzzles();
            Collections.shuffle(all);

            int count = Math.min(3, all.size());
            List<Puzzles.Puzzle> selected = all.subList(0, count);

            for (Puzzles.Puzzle puzzle : selected) {

                boolean solved = puzzle.play(game);

                if (!solved) {
                    if (!game.getCurrentHero().isAlive()) {
                        if (!game.hasAnyLivingCharacter()) return false;
                    }

                    System.out.println("\nThe obstacle reforms… try again.\n");

                    return play(game);
                }

                SoundPlayer.stopLoop(); 
                SoundPlayer.playLoop("travel_loop.wav");

                game.chooseDirection(getLevelNumber());

                SoundPlayer.stopLoop();

                SoundPlayer.playLoop("obstacle_level" + getLevelNumber() + ".wav");
            }

            SoundPlayer.stopLoop();     
            SoundPlayer.playSound("level_complete.wav");

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
