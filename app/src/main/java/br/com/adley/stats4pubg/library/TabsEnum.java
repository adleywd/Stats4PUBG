package br.com.adley.stats4pubg.library;

/**
 * Created by Adley on 13/10/2017.
 * Tabs Enum
 */

public enum TabsEnum {
    ALL(0), SOLO(1), DUO(2), SQUAD(3), SOLOFPP(4), DUOFPP(5), SQUADFPP(6);

    private final int value;
    TabsEnum(int i) {
        value = i;
    }

    public int getValue(){
        return value;
    }
}
