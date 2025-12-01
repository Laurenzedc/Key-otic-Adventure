import java.util.*;

public class Game {

    private final InputHandler inputHandler;
    private final List<Characters.Character> heroes;
    private Characters.Character currentHero;
    private final Items.Inventory inventory;
    private final Random random;

    // Animation speeds (ms)
    private static final int TEXT_SPEED = 30;    // smooth main text

    // ============================================================
    // DIRECTION POOLS
    // ============================================================

    private final List<String> dirPoolLvl1 = new ArrayList<>(List.of(
            "a twisting forest path where shadows dance between ancient trees",
            "a line of moss-covered roots weaving like veins through the soil",
            "a firefly grove glowing softly like a scattered constellation",
            "a vine-covered hill overlooking the wild jungle below",
            "an ancient leaf-carved archway leading into dim greenery",
            "distant animal calls echoing from the deep canopy"
    ));

    private final List<String> dirPoolLvl2 = new ArrayList<>(List.of(
            "a peaceful glowing riverbank with slow-moving water",
            "a path of mossy stones carved with spirals in the shallows",
            "a hidden cove where drifting water spirits swirl in silence",
            "a shimmering point where the current splits in two directions",
            "a shallow reflective pool that mirrors the sky perfectly",
            "a stretch of river filled with bobbing bottles and faint messages"
    ));

    private final List<String> dirPoolLvl3 = new ArrayList<>(List.of(
            "a shimmering ice tunnel humming faintly with trapped frost",
            "a crystal bridge suspended over a pale, endless void",
            "a trail of radiant blue crystals pulsing like quiet heartbeats",
            "frozen ridges etched with glowing runes under your feet",
            "tall ice pillars that sing softly when the wind brushes past",
            "a chamber where snowflakes hang unmoving in the frozen air"
    ));

    private final List<String> dirPoolLvl4 = new ArrayList<>(List.of(
            "a narrow cliff ledge skimming the edge of a deep ravine",
            "a descending tunnel filled with layered, overlapping whispers",
            "a swaying wooden walkway stretched across a roaring abyss",
            "jagged ridges that thrum with distant rumbling echoes",
            "a low crawlspace where every sound bounces back distorted",
            "a hollow chamber whose walls repeat your breathing"
    ));

    private final List<String> dirPoolLvl5 = new ArrayList<>(List.of(
            "orbiting runes that drift around you, forming half-shaped words",
            "golden platforms shifting like pieces of a living puzzle",
            "a swirling elemental core pulsing at the center of the chamber",
            "a silver corridor lined with rotating symbols and sigils",
            "floating stones that vibrate with quiet, hidden energy",
            "a cosmic hall tangled with glowing, star-fed vines"
    ));

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public Game() {
        this.inputHandler = new InputHandler();
        this.heroes = Characters.createDefaultCharacters();
        this.inventory = new Items.Inventory();
        this.random = new Random();
    }

    public Items.Inventory getInventory() {
        return inventory;
    }

    public Characters.Character getCurrentHero() {
        return currentHero;
    }

    // Small helper so other classes can trigger pauses without touching inputHandler
    public void pause() {
        inputHandler.waitForEnter();
    }

    // ============================================================
    // ANIMATION HELPERS
    // ============================================================

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /** Smooth typewriter text with flush to avoid buffered instant lines. */
    public void animateText(String text, int delayMs) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            sleep(delayMs);
        }
        System.out.println();
    }

    public void displayText(String text) {
        animateText(text, TEXT_SPEED);
    }

    /**
     * Clears the console so only the current scene is visible.
     * In some IDEs this just pushes old text away.
     */
    public void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("\n".repeat(80));
        }
    }

    /** Small flash line (used for life loss and rewards). */
    public void flashLine(String text, int flashes) {
        for (int i = 0; i < flashes; i++) {
            System.out.println(text);
            sleep(140);
        }
    }

    /** Animated level intro banner. */
    public void showLevelIntroAnimation(int levelNumber) {
        clearConsole();
        String title = "LEVEL " + levelNumber;
        String base = " " + title + " ";

        for (int i = 1; i <= 3; i++) {
            clearConsole();
            String arrows = ">".repeat(i);
            String trail = "<".repeat(i);
            printCentered(arrows + base + trail);
            sleep(160);
        }

        clearConsole();
        printCentered("========== " + title + " ==========");
        sleep(260);
        System.out.println();
        pause(); // pause before the level starts
    }

    /** Travel animation between puzzles / paths. */
    public void showTravelAnimation() {
        System.out.println();
        displayText("Moving to the next path...");

        String[] frames = {
                "[=         ]",
                "[===       ]",
                "[=====     ]",
                "[=======   ]",
                "[========= ]",
                "[==========]"
        };

        System.out.print("  ");
        for (String f : frames) {
            System.out.print("\r  " + f);
            System.out.flush();
            sleep(90);
        }
        System.out.println();
        sleep(160);
        pause(); // pause after travel
    }

    /** Little "opening" animation for inventory. */
    public void showInventoryOpenAnimation() {
        System.out.println();
        displayText("Opening inventory...");

        String[] frames = {
                "[#     ]",
                "[##    ]",
                "[###   ]",
                "[####  ]",
                "[##### ]",
                "[######]"
        };

        System.out.print("  ");
        for (String f : frames) {
            System.out.print("\r  " + f);
            System.out.flush();
            sleep(70);
        }
        System.out.println("\n");
    }

    /** Level-specific ambient animations (ASCII only). */
    public void playLevelAmbientAnimation(int levelNumber) {
        switch (levelNumber) {
            case 1 -> jungleAmbient();
            case 2 -> riverAmbient();
            case 3 -> iceAmbient();
            case 4 -> echoAmbient();
            case 5 -> heartAmbient();
        }
    }

    private void jungleAmbient() {
        String[] frames = {
                "[JUNGLE RUSTLES...]",
                "[LEAVES SHIFT IN THE WIND...]",
                "[SOMETHING WATCHES FROM THE TREES...]"
        };
        for (String f : frames) {
            System.out.println(f);
            sleep(180);
        }
        System.out.println();
        pause();
    }

    private void riverAmbient() {
        String[] frames = {
                "[WATER FLOWS SOFTLY...]",
                "[RIPPLES DISTORT YOUR REFLECTION...]",
                "[THE CURRENT CHANGES DIRECTION...]"
        };
        for (String f : frames) {
            System.out.println(f);
            sleep(180);
        }
        System.out.println();
        pause();
    }

    private void iceAmbient() {
        String[] frames = {
                "[COLD AIR SWIRLS AROUND YOU...]",
                "[FROST CREEPS ALONG THE GROUND...]",
                "[ICE CRACKS SOMEWHERE FAR AWAY...]"
        };
        for (String f : frames) {
            System.out.println(f);
            sleep(180);
        }
        System.out.println();
        pause();
    }

    private void echoAmbient() {
        String[] frames = {
            "[A DISTANT ECHO ANSWERS YOUR BREATH...]",
            "[ROCKS VIBRATE WITH HIDDEN SOUND...]",
            "[EVERY STEP RETURNS AS A WHISPER...]"
        };
        for (String f : frames) {
            System.out.println(f);
            sleep(180);
        }
        System.out.println();
        pause();
    }

    private void heartAmbient() {
        String[] frames = {
                "[THE AIR HUMS SOFTLY...]",
                "[LIGHT PULSES FROM AN INVISIBLE SOURCE...]",
                "[THE HEART OF THE GAME BEATS SLOWLY...]"
        };
        for (String f : frames) {
            System.out.println(f);
            sleep(180);
        }
        System.out.println();
        pause();
    }

    // ============================================================
    // INTRO STORY
    // ============================================================

    private void printIntroStory() {
        clearConsole();
        System.out.println();
        printCentered("==========================================");
        printCentered("            KEY-OTIC ADVENTURE            ");
        printCentered("==========================================");

        displayText("");
        displayText("The board unfolds before you - glowing symbols rising like fireflies.");
        displayText("Drums echo from the depths of an unseen jungle...");
        System.out.println();
        printCentered("\"SURVIVE MY TRIALS... AND CLAIM THE KEYS.\"");
        pause(); // pause after intro story
    }

    // ============================================================
    // RANDOM FLAVOR EVENTS
    // ============================================================

    public void randomEvent() {
        int roll = random.nextInt(100);

        if (roll < 20) {
            displayText("A warm breeze swirls around you... a quiet presence seems to guide your steps.");
            pause();
        } else if (roll < 40) {
            displayText("A cold shimmer ripples through the air... your breath fogs for a moment.");
            pause();
        } else if (roll < 60) {
            displayText("You hear faint whispers... but when you turn, there is nothing.");
            pause();
        } else if (roll < 80) {
            displayText("The ground pulses beneath your feet... as if the world reacts to your arrival.");
            pause();
        }
    }

    // ============================================================
    // START GAME
    // ============================================================

    public void start() {
        clearConsole();
        SoundPlayer.playLoop("intro_music.wav");

        printIntroStory();

        String choice = inputHandler.readValidOption(
                "\nDo you want to enter the game? (yes/no):",
                "yes", "no"
        );

        if (choice.equals("no")) {
            clearConsole();
            displayText("You step away from the mystical board...");
            displayText("The drums fade into silence.");
            SoundPlayer.stopLoop();
            pause();
            return;
        }

        showMap();
        pause(); // after map

        SoundPlayer.stopLoop();
        SoundPlayer.playLoop("menu_music.wav");

        clearConsole();
        printCentered("==========================================");
        printCentered("         CHOOSE YOUR CHARACTER            ");
        printCentered("==========================================");

        chooseInitialCharacter();
        pause(); // pause after choosing character

        Levels.LevelBase[] levels = {
                new Levels.Level1(),
                new Levels.Level2(),
                new Levels.Level3(),
                new Levels.Level4(),
                new Levels.Level5()
        };

        for (int i = 0; i < levels.length; i++) {

            SoundPlayer.stopLoop();
            resetAllCharacterAbilities();

            if (!currentHero.isAlive()) {
                if (!forceSwitchToAliveHero()) {
                    gameOver();
                    return;
                }
                pause();
            }

            int levelNumber = i + 1;

            showLevelIntroAnimation(levelNumber);
            randomEvent();

            boolean survived = levels[i].play(this);

            if (!survived) {
                gameOver();
                return;
            }

            giveLevelReward(levelNumber);

            if (i < levels.length - 1) {
                SoundPlayer.playLoop("menu_music.wav");
                askToShowMap("choosing your next destination");
                offerOptionalCharacterSwitch();
                pause();
            } else {
                printGameCompletion();
                return;
            }
        }
    }

    // ============================================================
    // MAP
    // ============================================================

    public void showMap() {
        clearConsole();
        printCentered("==========================================");
        printCentered("                 GAME MAP                 ");
        printCentered("==========================================");
        System.out.println();
        System.out.println("1. The Awakening Jungle: Where the forest itself seems alive - roots shift, creatures mimic patterns, and nature tests your instincts.\n");
        System.out.println("2. The River of Trials: Currents twist unpredictably, reflections hide secrets, and choices flow like water - one wrong branch changes everything.\n");
        System.out.println("3. The Frozen Cavern: A silent world of runes and mirrors - patterns freeze in time, and sound itself becomes a puzzle.\n");
        System.out.println("4. The Ravine of Echoes: Every noise returns altered. Shadows deceive, vibrations guide, and timing becomes your only ally.\n");
        System.out.println("5. The Heart of the Game: The final sanctuary where elements converge - logic, rhythm, and destiny merge into one last challenge.\n");
        printCentered("==========================================");
    }

    public void askToShowMap(String reason) {
        String mapChoice = inputHandler.readValidOption(
                "\nDo you want to view the map before " + reason + "? (yes/no):",
                "yes", "no"
        );

        if (mapChoice.equals("yes")) {
            showMap();
            pause();
        }
    }

    // ============================================================
    // CHARACTER SELECTION
    // ============================================================

    private void chooseInitialCharacter() {
        clearConsole();
        System.out.println("\nAvailable Characters:\n");

        for (Characters.Character c : heroes)
            System.out.println(c + "\n");

        while (true) {
            int id = inputHandler.readIntRange("Enter number (1-4):", 1, 4);

            Characters.Character chosen = heroes.stream()
                    .filter(h -> h.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (chosen != null) {
                currentHero = chosen;
                clearConsole();
                printCentered("You are now playing as: " + chosen.getName());
                sleep(400);
                return;
            }

            System.out.println("Invalid choice.");
        }
    }

    private boolean forceSwitchToAliveHero() {
        if (!hasAnyLivingCharacter()) return false;

        clearConsole();
        System.out.println("\nChoose a new character to continue:\n");

        while (true) {
            for (Characters.Character c : heroes)
                if (c.isAlive()) System.out.println(c + "\n");

            int id = inputHandler.readIntRange("Enter character number:", 1, 4);

            Characters.Character chosen = heroes.stream()
                    .filter(h -> h.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (chosen != null && chosen.isAlive()) {
                currentHero = chosen;
                clearConsole();
                printCentered("You now take on the role of: " + chosen.getName());
                sleep(400);
                return true;
            }

            System.out.println("Invalid choice (Character might be dead or ID does not exist).");
        }
    }

    private void offerOptionalCharacterSwitch() {
        String switchChoice = inputHandler.readValidOption(
                "\nDo you want to switch characters before the next level? (yes/no):",
                "yes", "no"
        );

        if (switchChoice.equals("no")) return;

        clearConsole();
        System.out.println("\nAvailable living characters:\n");
        for (Characters.Character c : heroes)
            if (c.isAlive()) System.out.println(c + "\n");

        while (true) {
            String ans = inputHandler.readString("Enter number or press ENTER to stay:");
            if (ans.isBlank()) return;

            try {
                int id = Integer.parseInt(ans.trim());
                Characters.Character hero = heroes.stream()
                        .filter(h -> h.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (hero != null && hero.isAlive()) {
                    currentHero = hero;
                    clearConsole();
                    printCentered("You now take on the role of: " + hero.getName());
                    sleep(400);
                    return;
                }
            } catch (Exception ignored) {}

            System.out.println("Invalid choice.");
        }
    }

    // ============================================================
    // ABILITY RESET
    // ============================================================

    private void resetAllCharacterAbilities() {
        for (Characters.Character c : heroes)
            c.resetAbilityForNewLevel();
    }

    // ============================================================
    // INVENTORY ITEM USAGE
    // ============================================================

    public Items.Item askUseInventoryItem(Puzzles.Puzzle puzzle) {

        if (inventory.isEmpty()) return null;

        String choice = inputHandler.readValidOption(
                "\nDo you want to use an item from your inventory? (yes/no)",
                "yes", "no"
        );

        if (choice.equals("no")) return null;

        clearConsole();
        showInventoryOpenAnimation();
        inventory.showInventory();
        pause();

        String itemName = inputHandler.readString("Type the name of the item you want to use:")
                .toUpperCase(Locale.ROOT);

        try {
            Items.Item item = Items.Item.valueOf(itemName);

            if (!inventory.hasItem(item)) {
                System.out.println("You do not have that item.");
                pause();
                return null;
            }

            if (!puzzle.isItemUseful(item)) {
                System.out.println();
                System.out.println("This item cannot help with this obstacle.");
                System.out.println();
                pause();
                return null;
            }

            System.out.println();
            System.out.println("You activated: " + itemName + "!");

            switch (item) {
                case TORCH ->
                        System.out.println("The torch flickers, revealing hidden shapes around you.");
                case ROPE ->
                        System.out.println("The rope tightens as you secure your footing.");
                case CRYSTAL_KEY ->
                        System.out.println("The Crystal Key hums, reacting to the frozen air.");
                case ECHO_WHISTLE ->
                        System.out.println("A sharp tone echoes, uncovering hidden vibrations.");
                case TOTEM_FLOW ->
                        System.out.println("The totem glows, helping your thoughts flow smoothly.");
                case TOTEM_WISDOM ->
                        System.out.println("Ancient wisdom fills your mind.");
                case TOTEM_BRAVERY ->
                        System.out.println("Courage surges through your spirit.");
                case TOTEM_SURVIVAL ->
                        System.out.println("Nature guides your senses.");
                case HEART_OF_GAME_SIGIL ->
                        System.out.println("The sigil pulses, but nothing happens...");
            }

            pause();
            return item;

        } catch (Exception e) {
            System.out.println("Invalid item.");
            pause();
            return null;
        }
    }

    // ============================================================
    // DAMAGE REDUCTION
    // ============================================================

    public int applyItemDamageReduction(Items.Item item, int baseDamage, Puzzles.Puzzle puzzle) {

        if (item == null) return baseDamage;

        String txt = puzzle.getIntro().toLowerCase(Locale.ROOT);
        Puzzles.PuzzleKind kind = puzzle.getKind();

        if (item == Items.Item.TORCH &&
                (txt.contains("dark") || txt.contains("frozen"))) {
            System.out.println("TORCH reduces the danger.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.ROPE &&
                (txt.contains("climb") || txt.contains("gap"))) {
            System.out.println("ROPE helps you avoid the worst.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.CRYSTAL_KEY && txt.contains("ice")) {
            System.out.println("CRYSTAL KEY absorbs the cold impact.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.ECHO_WHISTLE && txt.contains("echo")) {
            System.out.println("ECHO WHISTLE stabilizes the vibrations.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.TOTEM_BRAVERY &&
                (kind == Puzzles.PuzzleKind.TIMING || kind == Puzzles.PuzzleKind.ENVIRONMENT)) {
            System.out.println("TOTEM OF BRAVERY shields you.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.TOTEM_WISDOM &&
                (kind == Puzzles.PuzzleKind.RIDDLE || kind == Puzzles.PuzzleKind.CIPHER)) {
            System.out.println("TOTEM OF WISDOM softens the mistake.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.TOTEM_FLOW && kind == Puzzles.PuzzleKind.DECISION) {
            System.out.println("TOTEM OF FLOW stabilizes the currents.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.TOTEM_SURVIVAL &&
                (txt.contains("forest") || txt.contains("nature") ||
                        txt.contains("wild") || txt.contains("jungle") ||
                        txt.contains("roots"))) {
            System.out.println("TOTEM OF SURVIVAL protects you.");
            return Math.max(0, baseDamage - 1);
        }

        return baseDamage;
    }

    // ============================================================
    // LIFE LOSS
    // ============================================================

    public void loseLives(int amount, Puzzles.Puzzle puzzle) {
        currentHero.loseLives(amount);

        if (currentHero.isAlive()) {
            SoundPlayer.playSound("life_lost.wav");
            System.out.println();
            System.out.println("You lost " + amount + " life!");
            flashLine("*** LIFE -" + amount + " ***", 1);
            pause();
            return;
        }

        SoundPlayer.playSound("character_dead.wav");
        System.out.println();
        System.out.println("You lost " + amount + " life!");
        System.out.println(currentHero.getName() + " has fallen!");
        flashLine("*** " + currentHero.getName().toUpperCase(Locale.ROOT) + " DOWN ***", 1);

        if (!hasAnyLivingCharacter()) {
            printCentered("NO CHARACTERS LEFT.");
            pause();
            return;
        }

        forceSwitchToAliveHero();
        pause();
    }

    public boolean hasAnyLivingCharacter() {
        return heroes.stream().anyMatch(Characters.Character::isAlive);
    }

    // ============================================================
    // CHOOSE DIRECTION
    // ============================================================

    public void chooseDirection(int level) {
        clearConsole();

        List<String> pool = switch (level) {
            case 1 -> dirPoolLvl1;
            case 2 -> dirPoolLvl2;
            case 3 -> dirPoolLvl3;
            case 4 -> dirPoolLvl4;
            case 5 -> dirPoolLvl5;
            default -> throw new IllegalStateException("Unknown level");
        };

        if (pool.isEmpty()) {
            displayText("The paths fall silent.");
            pause();
            return;
        }

        int optionsCount = Math.min(3, pool.size());
        List<String> tempPool = new ArrayList<>(pool);
        List<String> options = new ArrayList<>();

        for (int i = 0; i < optionsCount; i++) {
            options.add(tempPool.remove(random.nextInt(tempPool.size())));
        }

        displayText("");
        displayText("Ancient paths unfold:");
        char label = 'a';
        List<String> validLabels = new ArrayList<>();

        for (String opt : options) {
            System.out.println("  " + label + ") " + opt);
            validLabels.add(String.valueOf(label));
            label++;
        }

        String[] validArray = validLabels.toArray(new String[0]);
        String input = inputHandler.readValidOption(
                "Choose (" + validLabels.get(0) + "-" + validLabels.get(validLabels.size() - 1) + "):",
                validArray
        );

        int idx = input.charAt(0) - 'a';
        String chosenPath = options.get(idx);

        pool.remove(chosenPath);

        clearConsole();
        displayText("You walk toward " + chosenPath + ".");
        heroDialogue("direction", null);
        pause();
    }

    // ============================================================
    // LEVEL REWARDS
    // ============================================================

    private void giveLevelReward(int levelNumber) {

        Items.Item reward = switch (levelNumber) {
            case 1 -> Items.Item.TORCH;
            case 2 -> Items.Item.ROPE;
            case 3 -> Items.Item.CRYSTAL_KEY;
            case 4 -> Items.Item.ECHO_WHISTLE;
            case 5 -> Items.Item.HEART_OF_GAME_SIGIL;
            default -> null;
        };

        if (reward == null) return;

        inventory.addItemSilent(reward);

        clearConsole();
        SoundPlayer.playSound("item_received.wav");

        printCentered("==========================================");
        printCentered("             LEVEL " + levelNumber + " REWARD");
        printCentered("==========================================");

        String displayName = formatItemName(reward);
        flashLine(">> " + displayName + " ACQUIRED! <<", 2);
        System.out.println();
        printItemDescription(reward);
        System.out.println();
        printCentered("==========================================");
        pause();
    }

    private void printItemDescription(Items.Item item) {
        String desc = switch (item) {
            case TORCH -> "A flame that never dies. Useful in dark or frozen trials.";
            case TOTEM_SURVIVAL -> "Enhances intuition in nature-related puzzles.";
            case ROPE -> "Helps climb, stabilize footing, or cross small gaps.";
            case TOTEM_FLOW -> "Improves decisions in water or current-based puzzles.";
            case CRYSTAL_KEY -> "Unlocks and stabilizes ice-related mechanisms.";
            case TOTEM_WISDOM -> "Sharpens your mind for riddles and cipher puzzles.";
            case ECHO_WHISTLE -> "Reveals differences in echoes - useful for ravine puzzles.";
            case TOTEM_BRAVERY -> "Reduces damage from environmental and timing traps.";
            case HEART_OF_GAME_SIGIL -> "The final artifact - essence of the Key-otic Heart.";
        };

        printCentered(desc);
    }

    private String formatItemName(Items.Item item) {
        String name = item.name().replace('_', ' ').toLowerCase(Locale.ROOT);
        StringBuilder sb = new StringBuilder();
        for (String p : name.split(" ")) {
            sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    // ============================================================
    // HERO DIALOGUE + REACTIONS
    // ============================================================

    public void reactionScene(String context) {
        List<String> reactions = List.of(
                "You steady yourself and breathe.",
                "You shake off tension.",
                "You gather your courage.",
                "You calm your heartbeat.",
                "You push forward with focus."
        );

        displayText(reactions.get(random.nextInt(reactions.size())));
        System.out.println();
    }

    public void heroDialogue(String situation, Puzzles.Puzzle puzzle) {
        Characters.Character hero = currentHero;
        String line = DialogueLibrary.getDialogue(hero, situation);

        if (line != null && !line.isEmpty()) {
            displayText("");
            displayText(line);
            displayText("");
        }
    }

    // ============================================================
    // ENDINGS
    // ============================================================

    private void printGameCompletion() {
        clearConsole();
        SoundPlayer.stopLoop();
        SoundPlayer.playSound("win_game.wav");

        System.out.println();
        printCentered("=========================================");
        printCentered("         YOU HAVE FINISHED THE GAME       ");
        printCentered("=========================================\n");

        displayText("The board folds into darkness...");
        displayText("But a glow remains within the Heart of the Game.");
        System.out.println();
        pause();
    }

    private void gameOver() {
        clearConsole();
        SoundPlayer.stopLoop();
        SoundPlayer.playSound("lose_game.wav");

        printCentered("=========================================");
        printCentered("                GAME OVER                ");
        printCentered("=========================================\n");
        displayText("The board seals shut.");
        displayText("Your journey ends here, forever trapped in this game.");
        System.out.println();
        pause();
    }

    // ============================================================
    // UTILITY
    // ============================================================

    public void printCentered(String text) {
        int width = 80;
        int pad = (width - text.length()) / 2;
        System.out.println(" ".repeat(Math.max(0, pad)) + text);
    }

    // Used by Puzzles
    public String readLine() {
        return inputHandler.readString("");
    }

    public static void printBoxedCentered(String title, Set<Items.Item> items) {

        int width = 80;
        int boxWidth = 60;
        int leftPad = (width - boxWidth) / 2;

        String borderTop = "+" + "-".repeat(boxWidth - 2) + "+";
        String borderMid = "+" + "-".repeat(boxWidth - 2) + "+";
        String borderBottom = "+" + "-".repeat(boxWidth - 2) + "+";

        System.out.println();
        System.out.println(" ".repeat(leftPad) + borderTop);

        String lineTitle = " " + title + " ";
        int pad = (boxWidth - 2 - lineTitle.length()) / 2;

        System.out.println(" ".repeat(leftPad) + "|" +
                " ".repeat(pad) + lineTitle +
                " ".repeat(boxWidth - 2 - pad - lineTitle.length()) +
                "|");

        System.out.println(" ".repeat(leftPad) + borderMid);

        if (items.isEmpty()) {
            String msg = "You have no items yet.";
            int pad2 = (boxWidth - 2 - msg.length()) / 2;
            System.out.println(" ".repeat(leftPad) + "|" +
                    " ".repeat(pad2) + msg +
                    " ".repeat(boxWidth - 2 - pad2 - msg.length()) +
                    "|");
        } else {
            for (Items.Item i : items) {
                String name = "- " + i.name();
                int pad2 = (boxWidth - 2 - name.length());
                if (pad2 < 0) pad2 = 0;
                System.out.println(" ".repeat(leftPad) + "| " +
                        name + " ".repeat(pad2 - 1) + "|");
            }
        }

        System.out.println(" ".repeat(leftPad) + borderBottom);
        System.out.println();
    }
}
