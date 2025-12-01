import java.util.HashSet;
import java.util.Set;

public class Items {

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

    public static class Inventory {

        private final Set<Item> items = new HashSet<>();

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

        public void addItemSilent(Item item) {
            if (item == null) return;
            items.add(item); 
        }

        public boolean hasItem(Item item) {
            return items.contains(item);
        }

        public void showInventory() {
            Game.printBoxedCentered("CURRENT INVENTORY", items);
        }        
        
        public boolean isEmpty() {
            return items.isEmpty();
        }
    }
}
