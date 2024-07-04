import java.util.Random;


public class SlotMachine {
    private static final char[] VALUES = {'💸', '7', '🍒', '🔔', '🀄'};
    private static SlotMachine instance;

    private SlotMachine() {}

    public static SlotMachine getInstance() {
        if (instance == null) {
            instance = new SlotMachine();
        }
        return instance;
    }

    
    private int spinSlot() {
        Random random = new Random();
        return random.nextInt(VALUES.length);
    }

    
    public void spinAndCheckWin(int betAmount) {
        int slot1 = spinSlot();
        int slot2 = spinSlot();
        int slot3 = spinSlot();

        WinStrategy strategy = WinStrategyFactory.createWinStrategy(slot1, slot2, slot3);
        int winnings = strategy.calculateWinnings(betAmount);
        System.out.println(strategy.getResultMessage(winnings));
    }
}


interface WinStrategy {
    int calculateWinnings(int betAmount);
    String getResultMessage(int winnings);
}


class WinStrategyFactory {      //Factory Design Pattern
    public static WinStrategy createWinStrategy(int slot1, int slot2, int slot3) {
        if (slot1 == slot2 && slot2 == slot3) {
            return new AllMatchWinStrategy();
        } else if (slot1 == slot2 || slot2 == slot3 || slot1 == slot3) {
            return new PartialMatchWinStrategy();
        } else {
            return new NoMatchWinStrategy();
        }
    }
}


class AllMatchWinStrategy implements WinStrategy {
    @Override
    public int calculateWinnings(int betAmount) {
        return betAmount * 10;
    }

    @Override
    public String getResultMessage(int winnings) {
        return "Congratulations! You won " + winnings + "!";
    }
}


class PartialMatchWinStrategy implements WinStrategy {
    @Override
    public int calculateWinnings(int betAmount) {
        return (int) (-1 * betAmount + Math.floor(betAmount * 0.2));
    }

    @Override
    public String getResultMessage(int winnings) {
        return "You almost won! You got " + winnings + ".";
    }
}


class NoMatchWinStrategy implements WinStrategy {
    @Override
    public int calculateWinnings(int betAmount) {
        return -1 * betAmount;
    }

    @Override
    public String getResultMessage(int winnings) {
        return "Sorry, you lost. You lost " + (-1 * winnings) + ".";
    }
}