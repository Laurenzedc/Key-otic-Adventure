import java.util.*;

public class Game {

    private final Scanner scanner;
    private final List<Characters.Character> heroes;
    private Characters.Character currentHero;
    private final Items.Inventory inventory;
    private final Random random;

    // Direction pools (each consumed only once)
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
    //                       CONSTRUCTOR
    // ============================================================

    public Game() {
        this.scanner = new Scanner(System.in);
        this.heroes = Characters.createDefaultCharacters();
        this.inventory = new Items.Inventory();
        this.random = new Random();
    }


    // ============================================================
    //                         START GAME
    // ============================================================

    public void start() {
        printIntroStory();

        System.out.print("\nDo you want to enter the game? (yes/no): ");
        if (!readLine().trim().toLowerCase().startsWith("y")) {
            System.out.println("\nYou step away from the mystical board...");
            System.out.println("The drums fade into silence.");
            return;
        }

        showMap();
        System.out.print("\nPress ENTER to continue...");
        readLine();

        printCentered("==========================================");
        printCentered("           CHOOSE YOUR CHARACTER          ");
        printCentered("==========================================");

        chooseInitialCharacter();

        Levels.LevelBase[] levels = {
                new Levels.Level1(),
                new Levels.Level2(),
                new Levels.Level3(),
                new Levels.Level4(),
                new Levels.Level5()
        };

        for (int i = 0; i < levels.length; i++) {

            resetAllCharacterAbilities();

            if (!currentHero.isAlive()) {
                if (!forceSwitchToAliveHero()) {
                    gameOver();
                    return;
                }
            }

            int levelNumber = i + 1;

            printCentered("==========================================");
            printCentered("             ENTERING LEVEL " + levelNumber);
            printCentered("==========================================");

            boolean survived = levels[i].play(this);

            if (!survived) {
                gameOver();
                return;
            }

            giveLevelReward(levelNumber);

            askToShowMap("choosing your next destination");

            if (i < levels.length - 1)
                offerOptionalCharacterSwitch();
        }

        printGameCompletion();
    }


    // ============================================================
    //                          INTRO
    // ============================================================

    private void printIntroStory() {
        printCentered("==========================================");
        printCentered("            KEY-OTIC ADVENTURE            ");
        printCentered("==========================================");

        System.out.println("\nThe board unfolds before you — glowing symbols rising like fireflies.");
        System.out.println("Drums echo from the depths of an unseen jungle…");
        printCentered("“SURVIVE MY TRIALS… AND CLAIM THE KEYS.”");
    }


    // ============================================================
    //                             MAP
    // ============================================================

    public void showMap() {
        printCentered("============== GAME MAP ==============");
        System.out.println();
        System.out.println("1. The Awakening Jungle");
        System.out.println("2. The River of Trials");
        System.out.println("3. The Frozen Cavern");
        System.out.println("4. The Ravine of Echoes");
        System.out.println("5. The Heart of the Game");
        System.out.println();
        printCentered("======================================");
    }

    public void askToShowMap(String reason) {
        System.out.print("\nDo you want to view the map before " + reason + "? (yes/no): ");
        if (readLine().trim().toLowerCase().startsWith("y")) showMap();
    }


    // ============================================================
    //                   CHARACTER SELECTION
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
        if (!readLine().trim().toLowerCase().startsWith("y")) return;

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
    //                 ABILITY RESET PER LEVEL
    // ============================================================

    private void resetAllCharacterAbilities() {
        for (Characters.Character c : heroes)
            c.resetAbilityForNewLevel();
    }


    // ============================================================
    //                  INVENTORY ITEM USAGE  (★ NEW)
    // ============================================================

    /**
     * Allows the player to activate an item before answering a puzzle.
     * Returns the activated item, or null if none.
     */
    public Items.Item askUseInventoryItem(Puzzles.Puzzle puzzle) {

        if (inventory.isEmpty()) return null;

        System.out.println("\nDo you want to use an item from your inventory? (yes/no)");
        if (!readLine().trim().toLowerCase().startsWith("y"))
            return null;

        inventory.showInventory();

        System.out.print("Type the name of the item you want to use: ");
        String itemName = readLine().trim().toUpperCase();

        try {
            Items.Item item = Items.Item.valueOf(itemName);

            if (!inventory.hasItem(item)) {
                System.out.println("You don't have that item.");
                return null;
            }

            System.out.println("\nYou activated: " + itemName + "!");
            return item;

        } catch (Exception e) {
            System.out.println("Invalid item.");
            return null;
        }
    }


    // ============================================================
    //          ITEM EFFECT CALCULATION  (★ DAMAGE REDUCTION C)
    // ============================================================

    public int applyItemDamageReduction(Items.Item item, int baseDamage, Puzzles.Puzzle puzzle) {

        if (item == null) return baseDamage;

        String txt = puzzle.getIntro().toLowerCase();
        Puzzles.PuzzleKind kind = puzzle.getKind();

        // TORCH reduces damage only if dark or frozen
        if (item == Items.Item.TORCH &&
                (txt.contains("dark") || txt.contains("frozen"))) {
            System.out.println("🔥 TORCH reduces the danger.");
            return Math.max(0, baseDamage - 1);
        }

        // ROPE reduces damage when climbing or crossing gaps
        if (item == Items.Item.ROPE &&
                (txt.contains("climb") || txt.contains("gap"))) {
            System.out.println("🪢 ROPE helps you avoid the worst.");
            return Math.max(0, baseDamage - 1);
        }

        // CRYSTAL KEY reduces damage for ice-related hazards
        if (item == Items.Item.CRYSTAL_KEY &&
                txt.contains("ice")) {
            System.out.println("🔷 CRYSTAL KEY absorbs the cold impact.");
            return Math.max(0, baseDamage - 1);
        }

        // ECHO WHISTLE reduces damage when the puzzle involves echoes
        if (item == Items.Item.ECHO_WHISTLE &&
                txt.contains("echo")) {
            System.out.println("📣 ECHO WHISTLE stabilizes the vibrations.");
            return Math.max(0, baseDamage - 1);
        }

        // TOTEM OF BRAVERY reduces damage on TIMING or ENVIRONMENT
        if (item == Items.Item.TOTEM_BRAVERY &&
                (kind == Puzzles.PuzzleKind.TIMING || kind == Puzzles.PuzzleKind.ENVIRONMENT)) {
            System.out.println("🛡️ TOTEM OF BRAVERY shields you!");
            return Math.max(0, baseDamage - 1);
        }

        // TOTEM OF WISDOM reduces damage on riddle or cipher puzzles
        if (item == Items.Item.TOTEM_WISDOM &&
                (kind == Puzzles.PuzzleKind.RIDDLE || kind == Puzzles.PuzzleKind.CIPHER)) {
            System.out.println("📘 TOTEM OF WISDOM softens the mistake.");
            return Math.max(0, baseDamage - 1);
        }

        // TOTEM OF FLOW reduces decision puzzle danger
        if (item == Items.Item.TOTEM_FLOW &&
                kind == Puzzles.PuzzleKind.DECISION) {
            System.out.println("🌊 TOTEM OF FLOW stabilizes the currents.");
            return Math.max(0, baseDamage - 1);
        }

        return baseDamage;
    }


    // ============================================================
    //                        LIFE LOSS
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
    //                     CHOOSE DIRECTION
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
    //                     LEVEL REWARD BANNER
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

        boolean alreadyOwned = inventory.hasItem(reward);

        System.out.println();
        printCentered("==========================================");
        printCentered("         LEVEL " + levelNumber + " REWARD");
        printCentered("==========================================");
        printCentered(formatItemName(reward) + " ACQUIRED!");
        System.out.println();

        if (!alreadyOwned)
            inventory.addItem(reward);

        printCentered("==========================================");
        System.out.println();
    }

    private String formatItemName(Items.Item item) {
        String name = item.name().replace('_', ' ').toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (String p : name.split(" "))
            sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1)).append(" ");
        return sb.toString().trim();
    }


    // ============================================================
    //                    HERO DIALOGUE SYSTEM
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
        // (same as your working dialogue system — unchanged)
        Characters.Character hero = currentHero;
        List<String> lines = DialogueLibrary.getLines(hero.getType(), situation);
        if (!lines.isEmpty())
            System.out.println("\n" + lines.get(random.nextInt(lines.size())) + "\n");
    }


    // ============================================================
    //                           ENDINGS
    // ============================================================

    private void printGameCompletion() {
        printCentered("\n==========================================");
        printCentered("         YOU HAVE FINISHED THE GAME       ");
        printCentered("==========================================\n");

        System.out.println("The board folds into darkness...");
        System.out.println("But a glow remains within the Heart of the Game.");
    }

    private void gameOver() {
        printCentered("\n==========================================");
        printCentered("                 GAME OVER                ");
        printCentered("==========================================\n");
        System.out.println("The board seals shut.");
        System.out.println("Your journey ends here.");
    }


    // ============================================================
    //                             UTILITY
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

    public Characters.Character getCurrentHero() { return currentHero; }
    public Items.Inventory getInventory() { return inventory; }
}