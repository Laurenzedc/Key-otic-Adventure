import java.util.*;

public class Game {

    private final Scanner scanner;
    private final List<Characters.Character> heroes;
    private Characters.Character currentHero;
    private final Items.Inventory inventory;
    private final Random random;

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
        this.scanner = new Scanner(System.in);
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

    // ============================================================
    // INTRO STORY (NO SOUND HERE, ONLY TEXT)
    // ============================================================

    private void printIntroStory() {

        System.out.println();
        printCentered("==========================================");
        printCentered("            KEY-OTIC ADVENTURE            ");
        printCentered("==========================================");

        System.out.println("\nThe board unfolds before you — glowing symbols rising like fireflies.");
        System.out.println("Drums echo from the depths of an unseen jungle…");
        System.out.println();
        printCentered("“SURVIVE MY TRIALS… AND CLAIM THE KEYS.”");
    }

    // ============================================================
    // START GAME
    // ============================================================

    public void start() {
        // 🔊 Play intro music right away
        SoundPlayer.playLoop("intro_music.wav");

        printIntroStory();

        System.out.print("\nDo you want to enter the game? (yes/no): ");
        if (!readLine().trim().toLowerCase().startsWith("y")) {
            System.out.println("\nYou step away from the mystical board...");
            System.out.println("The drums fade into silence.");
            SoundPlayer.stopLoop(); // stop intro if player quits
            return;
        }

        // Show map while intro music is still playing
        showMap();
        System.out.print("\nPress ENTER to continue...");
        readLine();

        // 🔇 Stop intro music after map + ENTER
        SoundPlayer.stopLoop();

        // 🎵 Start menu music for character selection + hub area
        SoundPlayer.playLoop("menu_music.wav");

        System.out.println();
        printCentered("==========================================");
        printCentered("         CHOOSE YOUR CHARACTER          ");
        printCentered("==========================================");

        chooseInitialCharacter();

        Levels.LevelBase[] levels = {
                new Levels.Level1(),
                new Levels.Level2(),
                new Levels.Level3(),
                new Levels.Level4(),
                new Levels.Level5()
        };

        // ========================================================
        // MAIN LEVEL LOOP (1–5)
        // ========================================================
        for (int i = 0; i < levels.length; i++) {

            // Before entering a level, stop menu music
            // (Option B: menu music continues until we reach this point)
            SoundPlayer.stopLoop();

            resetAllCharacterAbilities();

            if (!currentHero.isAlive()) {
                if (!forceSwitchToAliveHero()) {
                    gameOver();
                    return;
                }
            }

            int levelNumber = i + 1;

            System.out.println();
            printCentered("==========================================");
            printCentered("ENTERING LEVEL " + levelNumber);
            printCentered("==========================================");

            boolean survived = levels[i].play(this);

            if (!survived) {
                gameOver();
                return;
            }

            // Level finished successfully
            giveLevelReward(levelNumber);

            // If NOT the last level → go to hub (map + character switch)
            if (i < levels.length - 1) {

                // 🎵 Turn menu music back on while in hub (map + switching)
                SoundPlayer.playLoop("menu_music.wav");

                askToShowMap("choosing your next destination");
                offerOptionalCharacterSwitch();

                // Do NOT stop here — menu music keeps playing
                // until the next loop iteration, where we call stopLoop()
                // right before entering the next level.

            } else {
                // FINAL LEVEL (Level 5) → do NOT ask map / switch
                // go straight to final banner + sound
                printGameCompletion();
                return;
            }
        }
    }

    // ============================================================
    // MAP
    // ============================================================

    public void showMap() {
        System.out.println();
        printCentered("==========================================");
        printCentered("           GAME MAP            ");
        printCentered("==========================================");
        System.out.println();
        System.out.println("1. The Awakening Jungle: Where the forest itself seems alive—roots shift, creatures mimic patterns, and nature tests your instincts.\n");
        System.out.println("2. The River of Trials: Currents twist unpredictably, reflections hide secrets, and choices flow like water—one wrong branch changes everything.\n");
        System.out.println("3. The Frozen Cavern: A silent world of runes and mirrors—patterns freeze in time, and sound itself becomes a puzzle.\n");
        System.out.println("4. The Ravine of Echoes: Every noise returns altered. Shadows deceive, vibrations guide, and timing becomes your only ally.\n");
        System.out.println("5. The Heart of the Game: The final sanctuary where elements converge—logic, rhythm, and destiny merge into one last challenge.");
        System.out.println();
        printCentered("==========================================");
    }

    public void askToShowMap(String reason) {
        System.out.print("\nDo you want to view the map before " + reason + "? (yes/no): ");

        String mapChoice;
        while (true) {
            mapChoice = readLine().trim().toLowerCase();
            if (mapChoice.equals("yes") || mapChoice.equals("no")) break;
            System.out.println("Invalid answer. Please type yes or no.");
        }

        if (mapChoice.equals("yes")) showMap();
    }

    // ============================================================
    // CHARACTER SELECTION
    // ============================================================

    private void chooseInitialCharacter() {
        System.out.println("\nAvailable Characters:\n");

        for (Characters.Character c : heroes)
            System.out.println(c + "\n");

        while (true) {
            System.out.print("Enter number (1–4): ");
            try {
                int id = Integer.parseInt(readLine().trim());
                Characters.Character chosen = heroes.stream()
                        .filter(h -> h.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (chosen != null) {
                    currentHero = chosen;
                    printCentered("You are now playing as: " + chosen.getName());
                    // ❌ Removed SoundPlayer.stopLoop() here
                    // Menu music should continue into hub before Level 1
                    return;
                }

            } catch (Exception ignored) {}

            System.out.println("Invalid choice.");
        }
    }

    private boolean forceSwitchToAliveHero() {
        if (!hasAnyLivingCharacter()) return false;

        System.out.println("\nChoose a new character to continue:");

        while (true) {
            for (Characters.Character c : heroes)
                if (c.isAlive()) System.out.println(c + "\n");

            System.out.print("Enter character number: ");
            try {
                int id = Integer.parseInt(readLine().trim());
                Characters.Character chosen = heroes.stream()
                        .filter(h -> h.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (chosen != null && chosen.isAlive()) {
                    currentHero = chosen;
                    printCentered("You now take on the role of: " + chosen.getName());
                    return true;
                }

            } catch (Exception ignored) {}

            System.out.println("Invalid choice.");
        }
    }

    private void offerOptionalCharacterSwitch() {
        System.out.print("\nDo you want to switch characters before the next level? (yes/no): ");

        String switchChoice;
        while (true) {
            switchChoice = readLine().trim().toLowerCase();
            if (switchChoice.equals("yes") || switchChoice.equals("no")) break;
            System.out.println("Invalid answer. Please type yes or no.");
        }

        if (switchChoice.equals("no")) return;

        System.out.println("\nAvailable living characters:\n");
        for (Characters.Character c : heroes)
            if (c.isAlive()) System.out.println(c + "\n");

        while (true) {
            System.out.print("Enter number or press ENTER to stay: ");
            String ans = readLine();
            if (ans.isBlank()) return;

            try {
                int id = Integer.parseInt(ans.trim());
                Characters.Character hero = heroes.stream()
                        .filter(h -> h.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (hero != null && hero.isAlive()) {
                    currentHero = hero;
                    printCentered("You now take on the role of: " + hero.getName());
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

        System.out.println("\nDo you want to use an item from your inventory? (yes/no)");

        String choice;
        while (true) {
            choice = readLine().trim().toLowerCase();
            if (choice.equals("yes") || choice.equals("no")) break;
            System.out.println("Invalid answer. Please type yes or no.");
        }

        if (choice.equals("no")) return null;

        inventory.showInventory();

        System.out.print("Type the name of the item you want to use: ");
        String itemName = readLine().trim().toUpperCase();

        try {
            Items.Item item = Items.Item.valueOf(itemName);

            if (!inventory.hasItem(item)) {
                System.out.println("You don't have that item.");
                return null;
            }

            if (!puzzle.isItemUseful(item)) {
                System.out.println("\n❌ This item cannot help with this obstacle.\n");
                return null;
            }

            System.out.println("\nYou activated: " + itemName + "!");

            switch (item) {
                case TORCH ->
                        System.out.println("🔥 The torch flickers, revealing hidden shapes around you.");
                case ROPE ->
                        System.out.println("🪢 The rope tightens as you secure your footing.");
                case CRYSTAL_KEY ->
                        System.out.println("🔷 The Crystal Key hums, reacting to the frozen air.");
                case ECHO_WHISTLE ->
                        System.out.println("📣 A sharp tone echoes, uncovering hidden vibrations.");
                case TOTEM_FLOW ->
                        System.out.println("🌊 The totem glows, helping your thoughts flow smoothly.");
                case TOTEM_WISDOM ->
                        System.out.println("📘 Ancient wisdom fills your mind.");
                case TOTEM_BRAVERY ->
                        System.out.println("🛡️ Courage surges through your spirit.");
                case TOTEM_SURVIVAL ->
                        System.out.println("🌿 Nature guides your senses.");
                case HEART_OF_GAME_SIGIL ->
                        System.out.println("The sigil pulses, but nothing happens…");
            }

            return item;

        } catch (Exception e) {
            System.out.println("Invalid item.");
            return null;
        }
    }

    // ============================================================
    // DAMAGE REDUCTION
    // ============================================================

    public int applyItemDamageReduction(Items.Item item, int baseDamage, Puzzles.Puzzle puzzle) {

        if (item == null) return baseDamage;

        String txt = puzzle.getIntro().toLowerCase();
        Puzzles.PuzzleKind kind = puzzle.getKind();

        if (item == Items.Item.TORCH &&
                (txt.contains("dark") || txt.contains("frozen"))) {
            System.out.println("🔥 TORCH reduces the danger.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.ROPE &&
                (txt.contains("climb") || txt.contains("gap"))) {
            System.out.println("🪢 ROPE helps you avoid the worst.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.CRYSTAL_KEY && txt.contains("ice")) {
            System.out.println("🔷 CRYSTAL KEY absorbs the cold impact.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.ECHO_WHISTLE && txt.contains("echo")) {
            System.out.println("📣 ECHO WHISTLE stabilizes the vibrations.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.TOTEM_BRAVERY &&
                (kind == Puzzles.PuzzleKind.TIMING || kind == Puzzles.PuzzleKind.ENVIRONMENT)) {
            System.out.println("🛡️ TOTEM OF BRAVERY shields you!");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.TOTEM_WISDOM &&
                (kind == Puzzles.PuzzleKind.RIDDLE || kind == Puzzles.PuzzleKind.CIPHER)) {
            System.out.println("📘 TOTEM OF WISDOM softens the mistake.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.TOTEM_FLOW && kind == Puzzles.PuzzleKind.DECISION) {
            System.out.println("🌊 TOTEM OF FLOW stabilizes the currents.");
            return Math.max(0, baseDamage - 1);
        }

        if (item == Items.Item.TOTEM_SURVIVAL &&
                (txt.contains("forest") || txt.contains("nature") ||
                        txt.contains("wild") || txt.contains("jungle") ||
                        txt.contains("roots"))) {
            System.out.println("🌿 TOTEM OF SURVIVAL protects you!");
            return Math.max(0, baseDamage - 1);
        }

        return baseDamage;
    }

    // ============================================================
    // LIFE LOSS
    // ============================================================

    public void loseLives(int amount, Puzzles.Puzzle puzzle) {
        currentHero.loseLives(amount);
        System.out.println("\nYou lost " + amount + " life!");

        if (!currentHero.isAlive()) {
            System.out.println(currentHero.getName() + " has fallen!");

            if (!hasAnyLivingCharacter()) {
                printCentered("NO CHARACTERS LEFT.");
                return;
            }

            forceSwitchToAliveHero();
        }
    }

    public boolean hasAnyLivingCharacter() {
        return heroes.stream().anyMatch(Characters.Character::isAlive);
    }

    // ============================================================
    // CHOOSE DIRECTION
    // ============================================================

    public void chooseDirection(int level) {
        List<String> pool = switch (level) {
            case 1 -> dirPoolLvl1;
            case 2 -> dirPoolLvl2;
            case 3 -> dirPoolLvl3;
            case 4 -> dirPoolLvl4;
            case 5 -> dirPoolLvl5;
            default -> throw new IllegalStateException("Unknown level");
        };

        if (pool.isEmpty()) {
            System.out.println("\nThe paths fall silent.\n");
            return;
        }

        int optionsCount = Math.min(3, pool.size());
        List<String> tempPool = new ArrayList<>(pool);
        List<String> options = new ArrayList<>();

        for (int i = 0; i < optionsCount; i++) {
            options.add(tempPool.remove(random.nextInt(tempPool.size())));
        }

        System.out.println("\nAncient paths unfold:");
        char label = 'a';
        for (String opt : options) {
            System.out.println("  " + label + ") " + opt);
            label++;
        }

        String chosenPath;

        while (true) {
            System.out.print("Choose (a-" + (char) ('a' + optionsCount - 1) + "): ");
            String input = readLine().trim().toLowerCase();

            if (input.length() == 1) {
                int idx = input.charAt(0) - 'a';
                if (idx >= 0 && idx < optionsCount) {
                    chosenPath = options.get(idx);
                    break;
                }
            }

            System.out.println("Invalid choice.\n");
        }

        pool.remove(chosenPath);
        System.out.println("\nYou walk toward " + chosenPath + ".\n");
        heroDialogue("direction", null);
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

        SoundPlayer.playSound("item_received.wav");

        System.out.println();
        printCentered("==========================================");
        printCentered("LEVEL " + levelNumber + " REWARD");
        printCentered("==========================================");

        String displayName = formatItemName(reward);
        printCentered(displayName + " ACQUIRED!");
        System.out.println();
        printItemDescription(reward);
        System.out.println();
        printCentered("==========================================");
        System.out.println();
    }

    private void printItemDescription(Items.Item item) {
        String desc = switch (item) {
            case TORCH -> "A flame that never dies. Useful in dark or frozen trials.";
            case TOTEM_SURVIVAL -> "Enhances intuition in nature-related puzzles.";
            case ROPE -> "Helps climb, stabilize footing, or cross small gaps.";
            case TOTEM_FLOW -> "Improves decisions in water or current-based puzzles.";
            case CRYSTAL_KEY -> "Unlocks and stabilizes ice-related mechanisms.";
            case TOTEM_WISDOM -> "Sharpens your mind for riddles and cipher puzzles.";
            case ECHO_WHISTLE -> "Reveals differences in echoes — useful for ravine puzzles.";
            case TOTEM_BRAVERY -> "Reduces damage from environmental and timing traps.";
            case HEART_OF_GAME_SIGIL -> "The final artifact — essence of the Key-otic Heart.";
        };

        printCentered(desc);
    }

    private String formatItemName(Items.Item item) {
        String name = item.name().replace('_', ' ').toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (String p : name.split(" "))
            sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1)).append(" ");
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

        System.out.println(reactions.get(random.nextInt(reactions.size())) + "\n");
    }

    public void heroDialogue(String situation, Puzzles.Puzzle puzzle) {
        Characters.Character hero = currentHero;
        String line = DialogueLibrary.getDialogue(hero, situation);

        if (line != null && !line.isEmpty()) {
            System.out.println("\n" + line + "\n");
        }
    }

    // ============================================================
    // ENDINGS
    // ============================================================

    private void printGameCompletion() {
        // Stop any leftover looping music
        SoundPlayer.stopLoop();

        // Play win SFX once
        SoundPlayer.playSound("win_game.wav");

        printCentered("=========================================");
        printCentered("         YOU HAVE FINISHED THE GAME       ");
        printCentered("=========================================\n");

        System.out.println("The board folds into darkness...");
        System.out.println("But a glow remains within the Heart of the Game.");
        System.out.println();
    }

    private void gameOver() {
        // Stop any leftover looping music
        SoundPlayer.stopLoop();

        // Play lose SFX once
        SoundPlayer.playSound("lose_game.wav");

        printCentered("=========================================");
        printCentered("                 GAME OVER                ");
        printCentered("==========================================\n");
        System.out.println("The board seals shut.");
        System.out.println("Your journey ends here, forever trap in this game.");
        System.out.println();
    }

    // ============================================================
    // UTILITY
    // ============================================================

    public void printCentered(String text) {
        int width = 80;
        int pad = (width - text.length()) / 2;
        System.out.println(" ".repeat(Math.max(0, pad)) + text);
    }

    public String readLine() {
        try { return scanner.nextLine(); }
        catch (Exception e) { return ""; }
    }

    public static void printBoxedCentered(String title, Set<Items.Item> items) {

        int width = 80;
        int boxWidth = 60;
        int leftPad = (width - boxWidth) / 2;

        String border = "┌" + "─".repeat(boxWidth - 2) + "┐";
        String middle = "├" + "─".repeat(boxWidth - 2) + "┤";
        String bottom = "└" + "─".repeat(boxWidth - 2) + "┘";

        System.out.println();
        System.out.println(" ".repeat(leftPad) + border);

        String lineTitle = " " + title + " ";
        int pad = (boxWidth - 2 - lineTitle.length()) / 2;

        System.out.println(" ".repeat(leftPad) + "│" +
                " ".repeat(pad) + lineTitle +
                " ".repeat(boxWidth - 2 - pad - lineTitle.length()) +
                "│");

        System.out.println(" ".repeat(leftPad) + middle);

        if (items.isEmpty()) {
            String msg = "You have no items yet.";
            int pad2 = (boxWidth - 2 - msg.length()) / 2;
            System.out.println(" ".repeat(leftPad) + "│" +
                    " ".repeat(pad2) + msg +
                    " ".repeat(boxWidth - 2 - pad2 - msg.length()) +
                    "│");
        } else {
            for (Items.Item i : items) {
                String name = "- " + i.name();
                int pad2 = (boxWidth - 2 - name.length());
                if (pad2 < 0) pad2 = 0;
                System.out.println(" ".repeat(leftPad) + "│ " +
                        name + " ".repeat(pad2 - 1) + "│");
            }
        }

        System.out.println(" ".repeat(leftPad) + bottom);
        System.out.println();
    }
}
