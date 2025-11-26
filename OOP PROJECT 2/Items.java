import java.util.HashSet;
import java.util.Set;

public class Items {

    // =========================================================
    //                     ITEM ENUM
    // =========================================================
    public enum Item {
        TORCH,
        TOTEM_SURVIVAL,
        ROPE,
        TOTEM_FLOW,
        CRYSTAL_KEY,
        TOTEM_WISDOM,
        ECHO_WHISTLE,
        TOTEM_BRAVERY,
        HEART_OF_GAME_SIGIL
    }


    // =========================================================
    //                  INVENTORY CLASS
    // =========================================================
    public static class Inventory {

        private final Set<Item> items = new HashSet<>();


        // ------------------------- ADD ITEM -------------------------
        public void addItem(Item item) {
            if (item == null) return;

            if (!items.contains(item)) {
                items.add(item);
                System.out.println("\n[NEW ITEM ACQUIRED] " + item);

                switch (item) {
                    case TORCH -> System.out.println("A flame that never dies. Useful in dark or frozen trials.");
                    case TOTEM_SURVIVAL -> System.out.println("Enhances intuition in nature-related puzzles.");
                    case ROPE -> System.out.println("Helps climb, stabilize footing, or cross small gaps.");
                    case TOTEM_FLOW -> System.out.println("Improves decisions in water or current-based puzzles.");
                    case CRYSTAL_KEY -> System.out.println("Unlocks and stabilizes ice-related mechanisms.");
                    case TOTEM_WISDOM -> System.out.println("Sharpens your mind for riddles and cipher puzzles.");
                    case ECHO_WHISTLE -> System.out.println("Reveals differences in echoes — useful for ravine puzzles.");
                    case TOTEM_BRAVERY -> System.out.println("Reduces damage from environmental and timing traps.");
                    case HEART_OF_GAME_SIGIL -> System.out.println("The final artifact — essence of the Key-otic Heart.");
                }

                System.out.println();
            } else {
                System.out.println("[INVENTORY] You already possess: " + item);
            }
        }


        // ------------------------- CHECK ITEM -------------------------
        public boolean hasItem(Item item) {
            return items.contains(item);
        }

        public boolean hasAll(Item... list) {
            for (Item i : list)
                if (!items.contains(i)) return false;
            return true;
        }


        // ------------------------- SHOW INVENTORY -------------------------
        public void showInventory() {
            System.out.println("\n===== CURRENT INVENTORY =====");
            if (items.isEmpty()) {
                System.out.println("You have no items yet.");
            } else {
                for (Item i : items)
                    System.out.println(" - " + i.name());
            }
            System.out.println("==============================\n");
        }


        // =========================================================
        //              NEW FEATURE: USABLE ITEM CHECK
        // =========================================================
        // This determines whether a player *can* use an item
        // during a puzzle based on context (puzzle intro text).
        public boolean isUsableInContext(Item item, String contextLower) {

            return switch (item) {

                case TORCH -> (contextLower.contains("dark") ||
                               contextLower.contains("ice") ||
                               contextLower.contains("frozen"));

                case ROPE -> (contextLower.contains("climb") ||
                              contextLower.contains("gap") ||
                              contextLower.contains("ledge"));

                case CRYSTAL_KEY -> contextLower.contains("ice") ||
                                    contextLower.contains("frozen") ||
                                    contextLower.contains("rune");

                case ECHO_WHISTLE -> contextLower.contains("echo") ||
                                     contextLower.contains("sound") ||
                                     contextLower.contains("tunnel");

                case TOTEM_WISDOM -> true;   // Always usable for riddles/ciphers (handled by Puzzles)
                case TOTEM_FLOW -> true;     // Logic handled inside puzzle type
                case TOTEM_BRAVERY -> true;  // Helps timing/environment challenges
                case TOTEM_SURVIVAL -> true; // Helps nature-related puzzles
                
                case HEART_OF_GAME_SIGIL -> false; // Final artifact, not usable
            };
        }


        // =========================================================
        //        NEW: GET ALL USABLE ITEMS FOR THIS PUZZLE
        // =========================================================
        public Set<Item> getUsableItemsForContext(String introText) {

            String txt = introText.toLowerCase();
            Set<Item> usable = new HashSet<>();

            for (Item i : items) {
                if (isUsableInContext(i, txt))
                    usable.add(i);
            }

            return usable;
        }

    }
}
