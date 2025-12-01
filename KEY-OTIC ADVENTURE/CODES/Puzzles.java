import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Puzzles {

    public enum PuzzleKind {
        RIDDLE, PATTERN, DECISION, LOGIC, CIPHER, ENVIRONMENT, TIMING, MATCHING, MATH
    }

    public static class Puzzle {

        private final int id;
        private final String name;
        private final String intro;
        private final String prompt;
        private final String successText;
        private final String failureText;
        private final List<String> acceptableAnswers;
        private final PuzzleKind kind;
        private final boolean natureRelated;
        private final Items.Item rewardItem;

        public Puzzle(
                int id,
                String name,
                String intro,
                String prompt,
                String successText,
                String failureText,
                PuzzleKind kind,
                boolean natureRelated,
                Items.Item rewardItem,
                String... acceptableAnswers
        ) {
            this.id = id;
            this.name = name;
            this.intro = intro;
            this.prompt = prompt;
            this.successText = successText;
            this.failureText = failureText;
            this.kind = kind;
            this.natureRelated = natureRelated;
            this.rewardItem = rewardItem;

            this.acceptableAnswers = new ArrayList<>();
            for (String a : acceptableAnswers)
                this.acceptableAnswers.add(a.toLowerCase(Locale.ROOT));
        }

        public PuzzleKind getKind() { return kind; }
        public String getIntro() { return intro; }

        private boolean isCorrect(String input) {
            if (input == null) return false;
            return acceptableAnswers.contains(input.trim().toLowerCase(Locale.ROOT));
        }

        private String askValidatedAnswer(Game game) {
            System.out.print("> ");
            return game.readLine().trim().toLowerCase(Locale.ROOT);
        }

        private int computeLifeLoss(Characters.Character c) {

            int base = 1;

            return switch (c.getType()) {

                case DRAVEN -> {
                    if (kind == PuzzleKind.RIDDLE ||
                        kind == PuzzleKind.CIPHER ||
                        kind == PuzzleKind.LOGIC)
                        yield 2;
                    yield base;
                }

                case SELENE -> {
                    if (kind == PuzzleKind.ENVIRONMENT &&
                        intro.toLowerCase(Locale.ROOT).contains("heavy"))
                        yield 2;
                    yield base;
                }

                case ORION -> {
                    if (kind == PuzzleKind.TIMING ||
                        kind == PuzzleKind.ENVIRONMENT)
                        yield 2;
                    yield base;
                }

                case KAYA -> {
                    if (!natureRelated &&
                        (kind == PuzzleKind.CIPHER ||
                         kind == PuzzleKind.LOGIC))
                        yield 2;
                    yield base;
                }
            };
        }

        private boolean useAbility(Game game, Characters.Character hero) {

            switch (hero.getType()) {

                case DRAVEN -> {
                    game.printCentered(hero.getName() + " uses TITAN SMASH!");
                    game.displayText("The entire obstacle cracks apart!");
                    return true;
                }

                case SELENE -> {
                    if (kind == PuzzleKind.TIMING || kind == PuzzleKind.PATTERN) {
                        game.printCentered(hero.getName() + " activates FLASH STEP!");
                        game.displayText("She glides through the obstacle instantly.");
                        return true;
                    }
                    return false;
                }

                case ORION -> {
                    if (kind == PuzzleKind.RIDDLE || kind == PuzzleKind.CIPHER) {
                        game.printCentered(hero.getName() + " uses MENTAL OVERCLOCK!");
                        game.displayText("Symbols rearrange into clarity.");
                        return true;
                    }
                    return false;
                }

                case KAYA -> {
                    if (natureRelated || kind == PuzzleKind.DECISION) {
                        game.displayText("");
                        game.displayText("Kaya whispers a nature hint:");
                        game.displayText("\"The gentle path avoids extremes.\"");
                        game.displayText("");
                    }
                    return false;
                }
            }

            return false;
        }

        private void applyItemEffects(Game game) {
            var inv = game.getInventory();
            String txt = intro.toLowerCase(Locale.ROOT);

            if (inv.hasItem(Items.Item.TORCH) &&
                    (txt.contains("dark") || txt.contains("frozen")))
                game.displayText("TORCH reveals hidden details.");

            if (inv.hasItem(Items.Item.ROPE) &&
                    (txt.contains("climb") || txt.contains("gap")))
                game.displayText("ROPE helps steady your footing.");

            if (inv.hasItem(Items.Item.TOTEM_FLOW) &&
                    kind == PuzzleKind.DECISION && txt.contains("river"))
                game.displayText("TOTEM OF FLOW guides your thinking.");

            if (inv.hasItem(Items.Item.CRYSTAL_KEY) &&
                    txt.contains("ice"))
                game.displayText("CRYSTAL KEY glows faintly.");

            if (inv.hasItem(Items.Item.TOTEM_WISDOM) &&
                    (kind == PuzzleKind.RIDDLE || kind == PuzzleKind.CIPHER))
                game.displayText("TOTEM OF WISDOM sharpens your thoughts.");

            if (inv.hasItem(Items.Item.ECHO_WHISTLE) &&
                    txt.contains("echo"))
                game.displayText("ECHO WHISTLE vibrates with the surroundings.");

            if (inv.hasItem(Items.Item.TOTEM_BRAVERY) &&
                    (kind == PuzzleKind.TIMING || kind == PuzzleKind.ENVIRONMENT))
                game.displayText("TOTEM OF BRAVERY strengthens your resolve.");
        }

        /** MAIN PUZZLE LOGIC */
        public boolean play(Game game) {

            Characters.Character hero = game.getCurrentHero();

            // Scene reset
            game.clearConsole();
            game.printCentered("=== OBSTACLE: " + name + " ===");
            System.out.println();

            game.displayText(intro);
            game.heroDialogue("pre-obstacle", this);

            applyItemEffects(game);

            // Item use
            Items.Item activeItem = game.askUseInventoryItem(this);

            // Ability option
            if (hero.canUseAbility()) {

                game.displayText("");
                game.displayText("Use ability [" + hero.getAbilityName() + "]? (yes/no)");

                String abilityChoice;
                while (true) {
                    abilityChoice = game.readLine().trim().toLowerCase(Locale.ROOT);
                    if (abilityChoice.equals("yes") || abilityChoice.equals("no")) break;
                    game.displayText("Invalid answer. Please type yes or no.");
                }

                if (abilityChoice.equals("yes")) {

                    hero.markAbilityUsed();

                    if (useAbility(game, hero)) {
                        game.displayText(successText);
                        grantReward(game);
                        game.heroDialogue("success", this);
                        game.reactionScene("success");
                        game.pause(); // pause after success via ability
                        return true;
                    }
                }
            }

            // Ask main puzzle question
            System.out.println();
            game.displayText(prompt);

            String ans = askValidatedAnswer(game);

            // Correct
            if (isCorrect(ans)) {
                SoundPlayer.playSound("correct.wav");
                game.displayText(successText);
                grantReward(game);
                game.heroDialogue("success", this);
                game.reactionScene("success");
                game.pause(); // pause after success
                return true;
            }

            // Incorrect
            game.displayText(failureText);

            int dmg = computeLifeLoss(hero);
            dmg = game.applyItemDamageReduction(activeItem, dmg, this);
            game.loseLives(dmg, this);

            game.heroDialogue("fail", this);

            if (hero.isAlive()) {
                game.reactionScene("fail");
                game.pause(); // pause if still alive to feel the failure scene
            }

            return false;
        }

        private void grantReward(Game game) {
            if (rewardItem != null)
                game.getInventory().addItem(rewardItem);
        }

        /** Item usefulness logic */
        public boolean isItemUseful(Items.Item item) {

            String txt = intro.toLowerCase(Locale.ROOT);

            return switch (item) {
                case TORCH ->
                        txt.contains("dark") || txt.contains("frozen") || txt.contains("ice");
                case ROPE ->
                        txt.contains("climb") || txt.contains("gap") || txt.contains("bridge");
                case CRYSTAL_KEY ->
                        txt.contains("ice") || txt.contains("frozen") || txt.contains("rune");
                case ECHO_WHISTLE ->
                        txt.contains("echo") || txt.contains("sound") || txt.contains("tunnel");
                case TOTEM_WISDOM ->
                        kind == PuzzleKind.RIDDLE || kind == PuzzleKind.CIPHER;
                case TOTEM_FLOW ->
                        kind == PuzzleKind.DECISION;
                case TOTEM_BRAVERY ->
                        kind == PuzzleKind.TIMING || kind == PuzzleKind.ENVIRONMENT;
                case TOTEM_SURVIVAL ->
                        txt.contains("forest") || txt.contains("roots") || txt.contains("nature");
                case HEART_OF_GAME_SIGIL -> false;
            };
        }
    }

    // ---------------------------------------------------------
    // LEVEL 1 PUZZLES
    // ---------------------------------------------------------

    public static List<Puzzle> level1Puzzles() {
        List<Puzzle> list = new ArrayList<>();

        list.add(new Puzzle(
                1, "Animal Footprint Match",
                "Three trails cross your path: clawed prints, hand-like prints, winding trail.",
                "Which animal left the smallest prints? (tiger/monkey/snake)",
                "Correct - the small monkey prints lead you forward.",
                "A vine snaps near your foot.",
                PuzzleKind.MATCHING, true, null,
                "monkey"
        ));

        list.add(new Puzzle(
                2, "Parrot Riddle",
                "A glowing parrot asks: \"I have cities but no houses... what am I?\"",
                "Answer the riddle:",
                "Correct - the parrot leaves behind a torch.",
                "Wrong.",
                PuzzleKind.RIDDLE, true, Items.Item.TORCH,
                "map"
        ));

        list.add(new Puzzle(
                3, "Jungle Totem Pattern",
                "A totem spins: angry -> calm -> happy -> angry -> calm -> happy...",
                "What face appears on the 7th turn?",
                "Correct.",
                "Wrong.",
                PuzzleKind.PATTERN, true, null,
                "angry"
        ));

        list.add(new Puzzle(
                4, "Vine Lock",
                "Vines coil around a chest: \"I speak without a mouth...\"",
                "What am I? (echo/wind/silence)",
                "Correct.",
                "Wrong.",
                PuzzleKind.RIDDLE, true, null,
                "echo"
        ));

        list.add(new Puzzle(
                5, "Leaf-Fall Sequence",
                "Leaves fall in LRLLR repeating pattern.",
                "What is the 11th leaf? (left/right)",
                "Correct.",
                "Wrong.",
                PuzzleKind.PATTERN, true, null,
                "left"
        ));

        return list;
    }

    // ---------------------------------------------------------
    // LEVEL 2 PUZZLES
    // ---------------------------------------------------------

    public static List<Puzzle> level2Puzzles() {
        List<Puzzle> list = new ArrayList<>();

        list.add(new Puzzle(
                6, "Stone Symbols",
                "Stones show symbols: sun -> moon -> star -> spiral...",
                "Type the order: sun-moon-star-spiral",
                "Correct.",
                "Wrong.",
                PuzzleKind.PATTERN, false, null,
                "sun-moon-star-spiral"
        ));

        list.add(new Puzzle(
                7, "River Flow Decision",
                "Wind blows east. River splits left and right.",
                "Which branch is safer? (left/right)",
                "Correct.",
                "Wrong.",
                PuzzleKind.DECISION, false, null,
                "left"
        ));

        list.add(new Puzzle(
                8, "Bottle Cipher",
                "Bottles spell: F L I E. Unscramble the word.",
                "Type the word:",
                "Correct.",
                "Wrong.",
                PuzzleKind.CIPHER, false, null,
                "life"
        ));

        list.add(new Puzzle(
                9, "Crocodile Logic",
                "Logs drift: A (sun), B (shade), C (still in deep shade).",
                "Which is safe? (a/b/c)",
                "Correct.",
                "Wrong.",
                PuzzleKind.LOGIC, false, null,
                "b"
        ));

        list.add(new Puzzle(
                10, "Reflection Word",
                "A reversed word glows in the water: EROP.",
                "What is the real word?",
                "Correct - the river leaves behind a rope.",
                "Wrong.",
                PuzzleKind.CIPHER, false, Items.Item.ROPE,
                "rope"
        ));

        return list;
    }

    // ---------------------------------------------------------
    // LEVEL 3 PUZZLES
    // ---------------------------------------------------------

    public static List<Puzzle> level3Puzzles() {
        List<Puzzle> list = new ArrayList<>();

        list.add(new Puzzle(
                11, "Ice Rune Cipher",
                "Frozen runes pulse faintly.",
                "Type the meaning: (life/ice/path)",
                "Correct - the runes release a crystal key.",
                "Wrong.",
                PuzzleKind.CIPHER, false, Items.Item.CRYSTAL_KEY,
                "life"
        ));

        list.add(new Puzzle(
                12, "Crystal Resonance",
                "Crystals emit tones: low -> mid -> high -> low...",
                "What tone comes 10th? (low/mid/high)",
                "Correct.",
                "Wrong.",
                PuzzleKind.PATTERN, false, null,
                "mid"
        ));

        list.add(new Puzzle(
                13, "Mirror Reflection",
                "A mirrored ice wall shows reversed symbols.",
                "Decipher the reversed pattern: sun/moon/star",
                "Correct.",
                "Wrong.",
                PuzzleKind.PATTERN, false, null,
                "star"
        ));

        list.add(new Puzzle(
                14, "Frozen Shadows",
                "Shadows resemble numbers: 1 -> 3 -> 6 -> 10 -> ...",
                "What comes next? (number)",
                "Correct.",
                "Wrong.",
                PuzzleKind.PATTERN, false, null,
                "15"
        ));

        list.add(new Puzzle(
                15, "Blizzard Direction",
                "Winds swirl: strong north, weaker south.",
                "Which way is safe? (north/south)",
                "Correct.",
                "Wrong.",
                PuzzleKind.DECISION, false, null,
                "south"
        ));

        return list;
    }

    // ---------------------------------------------------------
    // LEVEL 4 PUZZLES
    // ---------------------------------------------------------

    public static List<Puzzle> level4Puzzles() {
        List<Puzzle> list = new ArrayList<>();

        list.add(new Puzzle(
                16, "Echo Tunnel",
                "Tunnels respond: A (weak), B (none), C (strong).",
                "Which tunnel leads forward? (a/b/c)",
                "Correct.",
                "Wrong.",
                PuzzleKind.DECISION, true, null,
                "c"
        ));

        list.add(new Puzzle(
                17, "Falling Rock Rhythm",
                "Rocks fall: 3 -> pause -> 5 -> pause -> repeat.",
                "Cross after which fall? (3/5)",
                "Correct.",
                "Wrong.",
                PuzzleKind.TIMING, true, null,
                "3"
        ));

        list.add(new Puzzle(
                18, "Rope Bridge Weight",
                "Bridge supports only 2 units.",
                "Which pair can cross? (draven+selene / selene+orion / selene+kaya)",
                "Correct.",
                "Wrong.",
                PuzzleKind.LOGIC, true, null,
                "selene+orion", "selene+kaya"
        ));

        list.add(new Puzzle(
                19, "Eagle Shadows",
                "Shadows: circle -> triangle -> repeat.",
                "What is the 12th shape?",
                "Correct.",
                "Wrong.",
                PuzzleKind.PATTERN, true, null,
                "circle"
        ));

        list.add(new Puzzle(
                20, "Echo Creature Mimic",
                "Creature mimics RAWR-HISS-RAWR.",
                "Type: rawr-hiss-rawr",
                "Correct.",
                "Wrong.",
                PuzzleKind.PATTERN, true, null,
                "rawr-hiss-rawr"
        ));

        list.add(new Puzzle(
                21, "Boulder Blocking Path",
                "A massive boulder blocks your escape.",
                "How do you clear it? (draven/rope/torch)",
                "Correct - the creature rewards you with an ECHO WHISTLE.",
                "Wrong.",
                PuzzleKind.ENVIRONMENT, true, Items.Item.ECHO_WHISTLE,
                "draven", "rope", "torch"
        ));

        return list;
    }

    // ---------------------------------------------------------
    // LEVEL 5 PUZZLES
    // ---------------------------------------------------------

    public static List<Puzzle> level5Puzzles() {
        List<Puzzle> list = new ArrayList<>();

        list.add(new Puzzle(
                22, "Totem Alignment",
                "Totems: Earth -> Water -> Ice -> Air.",
                "Type order: survival,flow,wisdom,bravery",
                "Correct.",
                "Wrong.",
                PuzzleKind.LOGIC, false, null,
                "survival,flow,wisdom,bravery"
        ));

        list.add(new Puzzle(
                23, "Drum Beat Code",
                "Drums echo: short-short-long.",
                "Type: 1-1-2",
                "Correct.",
                "Wrong.",
                PuzzleKind.PATTERN, false, null,
                "1-1-2"
        ));

        list.add(new Puzzle(
                24, "Final Word Cipher",
                "Symbols swirl into a meaningful word.",
                "Type the word:",
                "Correct.",
                "Wrong.",
                PuzzleKind.CIPHER, false, null,
                "life"
        ));

        list.add(new Puzzle(
                25, "Moral Choice",
                "A voice whispers: help or alone?",
                "Choose wisely.",
                "Correct.",
                "Wrong.",
                PuzzleKind.DECISION, false, null,
                "help"
        ));

        list.add(new Puzzle(
                26, "Dice of Fate",
                "Predict: HIGH (4-6) or LOW (1-3).",
                "Type: high or low",
                "Correct.",
                "Wrong.",
                PuzzleKind.LOGIC, false, null,
                "high"
        ));

        list.add(new Puzzle(
                27, "Symbol Pyramid",
                "Symbols: leaf -> wave -> crystal -> echo.",
                "Type: leaf,wave,crystal,echo",
                "Correct.",
                "Wrong.",
                PuzzleKind.LOGIC, false, null,
                "leaf,wave,crystal,echo"
        ));

        list.add(new Puzzle(
                28, "Element Reaction",
                "Use elements: fire -> water -> ice -> wind.",
                "Type: torch,flow,crystal,whistle",
                "Correct - you earn the Heart of the Game Sigil.",
                "Wrong.",
                PuzzleKind.ENVIRONMENT, false, Items.Item.HEART_OF_GAME_SIGIL,
                "torch,flow,crystal,whistle"
        ));

        return list;
    }
}
