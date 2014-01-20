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

    public void setTapped(boolean tapped) {
        rotate = tapped;
        if (rotate)
            setPreferredSize(new Dimension(HEIGHT, WIDTH));
        else
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void paint(Graphics g) {
        if (rotate) {
            Graphics2D g2 = (Graphics2D)g;
            g2.rotate(-Math.PI/2, WIDTH/2, WIDTH/2);
        }

        if (fontMetrics == null) {
            fontMetrics = g.getFontMetrics(font);
            fontHeight = fontMetrics.getHeight();
        }

        g.setColor(Color.BLACK);
        g.fillRoundRect(0, 0, WIDTH, HEIGHT, inset, inset);

        g.setColor(Color.WHITE);
        g.fillRect(inset, inset, WIDTH-inset*2, HEIGHT-inset*2);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(card.name, inset + 2, inset + fontHeight + 2);

        drawContent(g);
    }

    protected void drawContent(Graphics g) {

    }

    public CardView(Card card) {
        this.card = card;
        inset = 5;
        font = new Font("Ariel", Font.PLAIN, 12);

        setTapped(false);
    }

    protected Card card;
    protected int inset;

    private boolean rotate;
}
