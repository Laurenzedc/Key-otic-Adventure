import java.util.*;

public class DialogueLibrary {

    private static final Random random = new Random();

    public static String getDialogue(Characters.Character hero, String situation) {
        List<String> lines = switch (hero.getType()) {
            case DRAVEN -> dravenLines(situation);
            case SELENE -> seleneLines(situation);
            case ORION -> orionLines(situation);
            case KAYA -> kayaLines(situation);
        };

        if (lines == null || lines.isEmpty()) return "";
        return lines.get(random.nextInt(lines.size()));
    }

    // ===== DRAVEN =====
    private static List<String> dravenLines(String situation) {
        return switch (situation) {
            case "pre-obstacle" -> List.of(
                    "You crack your knuckles. “If it stands in my way, it’s getting smashed.”",
                    "You huff. “Finally, something to hit.”",
                    "You mutter, “If this puzzle thinks it can stop me, it’s wrong.”",
                    "You roll your shoulders. “Alright… what needs breaking today?”",
                    "A grin forms. “Hope this one puts up a fight.”"
            );
            case "success" -> List.of(
                    "You laugh. “Knew I’d crush that.”",
                    "You nod. “Too easy. What’s next?”",
                    "You nod firmly. “Easy work.”",
                    "You let out a satisfied grunt. “Next one.”",
                    "You crack your neck. “And that’s how it’s done.”"
            );
            case "fail" -> List.of(
                    "You grunt in frustration. “Tch—annoying thing.”",
                    "You rub your head. “Why do puzzles even exist?”",
                    "You narrow your eyes. “I’m gonna break you next attempt.”",
                    "You breathe heavily. “Lucky shot… try that again.”",
                    "You glare. “Alright, now I’m mad.”"
            );
            case "direction" -> List.of(
                    "You roll your shoulders. “Whatever’s ahead, I’ll punch through it.”",
                    "You crack your fists. “Hope something strong shows up.”",
                    "You step forward. “Alright. Let’s see what’s waiting for me.”",
                    "You nod. “Solid path. I’ll smash through whatever’s ahead.”",
                    "You stomp forward. “Let’s keep moving.”"
            );
            default -> Collections.emptyList();
        };
    }

    // ===== SELENE =====
    private static List<String> seleneLines(String situation) {
        return switch (situation) {
            case "pre-obstacle" -> List.of(
                    "You grin. “Looks dangerous. I like it.”",
                    "You stretch lightly. “Let’s dance through this one.”",
                    "You stretch. “Alright puzzle… let’s dance.”",
                    "You giggle softly. “Let’s see if you can keep up.”",
                    "You tilt your head. “Fast or tricky? Either way, I win.”"
            );
            case "success" -> List.of(
                    "You smile. “Told you I’d glide through that.”",
                    "You wink at the air. “Smooth as always.”",
                    "You wink. “Too easy.”",
                    "You laugh lightly. “Told you I’d nail it.”",
                    "You spin around. “Grace, speed, perfection.”"
            );
            case "fail" -> List.of(
                    "You wince. “Ouch… okay, that one got me.”",
                    "You shake it off. “Next time, I move faster.”",
                    "You sigh. “Fine… that one caught me off guard.”",
                    "You pout. “Rude puzzle.”",
                    "You stretch your leg. “Round two. Let’s go.”"
            );
            case "direction" -> List.of(
                    "You bounce on your feet. “Nice path. Feels light.”",
                    "You take off with a light step. “Let’s sprint ahead.”",
                    "You tap your foot. “Momentum is key—let’s keep it.”",
                    "You grin. “Fast path? My specialty.”",
                    "You take a deep breath. “Feels like a smooth run.”"
            );
            default -> Collections.emptyList();
        };
    }

    // ===== ORION =====
    private static List<String> orionLines(String situation) {
        return switch (situation) {
            case "pre-obstacle" -> List.of(
                    "You narrow your eyes. “Quiet… I need to analyze this.”",
                    "You adjust your posture. “Logic reveals all truths.”",
                    "You observe quietly. “Interesting configuration.”",
                    "You tap your chin. “Let’s approach this logically.”",
                    "You nod to yourself. “Time to solve another mystery.”"
            );
            case "success" -> List.of(
                    "You nod calmly. “Expected outcome.”",
                    "You murmur. “Knowledge triumphs again.”",
                    "You exhale calmly. “Solved.”",
                    "You nod. “Correct approach confirmed.”",
                    "You mutter, “Another equation resolved.”"
            );
            case "fail" -> List.of(
                    "You frown. “My calculations… off? Interesting.”",
                    "You tap your chin. “This puzzle is more complex than anticipated.”",
                    "You sigh softly. “I miscalculated. Annoying.”",
                    "You press your lips together. “Retry required.”",
                    "You shake your head. “Unacceptable. I’ll correct it.”"
            );
            case "direction" -> List.of(
                    "You feel a quiet certainty. “That direction aligns with order.”",
                    "You walk with purpose. “A meaningful choice.”",
                    "You adjust your robe. “Order guides us onward.”",
                    "You walk calmly. “Statistically safe enough.”",
                    "You breathe steadily. “A reasonable choice.”"
            );
            default -> Collections.emptyList();
        };
    }

    // ===== KAYA =====
    private static List<String> kayaLines(String situation) {
        return switch (situation) {
            case "pre-obstacle" -> List.of(
                    "You breathe deeply. “The land is whispering… I’ll listen.”",
                    "You rest your hand on the ground. “Nature will guide me through this.”",
                    "You run your hand along the ground. “I can feel the path.”",
                    "You murmur, “Another trial… another chance to learn.”",
                    "You steady yourself. “Let the world guide me.”"
            );
            case "success" -> List.of(
                    "You smile softly. “The world feels at ease again.”",
                    "You whisper. “Harmony restored.”",
                    "You breathe out. “Balance achieved.”",
                    "You nod gently. “A peaceful outcome.”",
                    "You touch the ground. “Thank you, earth.”"
            );
            case "fail" -> List.of(
                    "You sigh. “I misread the signs… I’ll listen more carefully next time.”",
                    "You look around. “The earth is testing my intentions.”",
                    "You clutch your arm. “The land tests my resolve.”",
                    "You murmur, “This path is harsher than I sensed.”",
                    "You straighten. “I will try again with clearer mind.”"
            );
            case "direction" -> List.of(
                    "You nod. “Yes… this path feels alive.”",
                    "You step gently. “Nature approves of this choice.”",
                    "You listen. “The earth hums softly toward this path.”",
                    "You feel the breeze. “A gentle push… onward.”",
                    "You nod. “This way resonates with peace.”"
            );
            default -> Collections.emptyList();
        };
    }
}
