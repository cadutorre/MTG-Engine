package magic.view;

import magic.card.Card;
import magic.card.creature.Creature;
import magic.mana.ManaColor;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void paint(Graphics g) {
        if (rotate) {
            Graphics2D g2 = (Graphics2D)g;
            g2.rotate(-Math.PI / 2, WIDTH / 2, WIDTH / 2);
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

        // Mana cost
        int numManas = 0;
        int manaWidth = 10;
        for (ManaColor c : ManaColor.COLORS) {
            if (card.getCost().containsColor(c))
                numManas += card.getCost().getColorAmount(c);
        }

        if (numManas == 0) {
            int x = WIDTH - inset - manaWidth;
            g.setColor(ManaColor.COLORLESS.color);
            g.fillOval(x, inset, manaWidth, manaWidth);
            g.setColor(Color.black);
            g.drawOval(x, inset, manaWidth, manaWidth);
            int textX = x + manaWidth/2 - fontMetrics.stringWidth("0")/2;
            g.drawString("0", textX, inset+manaWidth);
            x += manaWidth;
        } else {
            if (card.getCost().containsColor(ManaColor.COLORLESS))
                ++numManas;
            int x = WIDTH - inset - numManas*manaWidth;

            for (ManaColor c : ManaColor.values()) {
                if (card.getCost().containsColor(c)) {
                    if (c == ManaColor.COLORLESS) {
                        g.setColor(c.color);
                        g.fillOval(x, inset, manaWidth, manaWidth);
                        g.setColor(Color.black);
                        g.drawOval(x, inset, manaWidth, manaWidth);
                        int textX = x + manaWidth/2 - fontMetrics.stringWidth(""+card.getCost().getColorAmount(ManaColor.COLORLESS))/2;
                        g.drawString(""+card.getCost().getColorAmount(ManaColor.COLORLESS), textX, inset+manaWidth);
                        x += manaWidth;
                    } else {
                        for (int i = 0; i<card.getCost().getColorAmount(c); ++i) {
                            g.setColor(c.color);
                            g.fillOval(x, inset, manaWidth, manaWidth);
                            g.setColor(Color.black);
                            g.drawOval(x, inset, manaWidth, manaWidth);

                            x += manaWidth;
                        }
                    }
                }
            }
        }

        drawContent(g);

        if (!enabled) {
            g.setColor(new Color(0, 0, 0, 50));
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }
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
    private boolean enabled;
}
