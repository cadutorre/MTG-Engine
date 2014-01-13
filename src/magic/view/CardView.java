package magic.view;

import magic.card.Card;
import magic.card.creature.Creature;

import javax.swing.*;
import java.awt.*;

public class CardView extends JComponent {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 150;

    protected static Font font;
    protected static FontMetrics fontMetrics;
    protected static int fontHeight;

    public static CardView create(Card card) {
        if (card instanceof Creature)
            return new CreatureCardView((Creature)card);

        return new CardView(card);
    }

    public Card getCard() {
        return card;
    }

    @Override
    public void paint(Graphics g) {
        if (fontMetrics == null) {
            fontMetrics = g.getFontMetrics(font);
            fontHeight = fontMetrics.getHeight();
        }

        g.setColor(Color.BLACK);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), inset, inset);

        g.setColor(Color.WHITE);
        g.fillRect(inset, inset, getWidth()-inset*2, getHeight()-inset*2);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(card.name, inset + 2, inset + fontHeight + 2);

        drawContent(g);
    }

    protected void drawContent(Graphics g) {

    }

    public CardView(Card card) {
        this.card = card;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        inset = 5;
        font = new Font("Ariel", Font.PLAIN, 12);
    }

    protected Card card;
    protected int inset;
}
