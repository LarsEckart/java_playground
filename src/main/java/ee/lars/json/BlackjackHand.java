package ee.lars.json;

import java.util.List;

public class BlackjackHand {

    public final Card hidden_card;
    public final List<Card> visible_cards;

    public BlackjackHand(Card hidden_card, List<Card> visible_cards) {
        this.hidden_card = hidden_card;
        this.visible_cards = visible_cards;
    }
}
