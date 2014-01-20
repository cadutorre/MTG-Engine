package magic.view;

import magic.card.creature.Creature;

import java.awt.*;

public class CreatureCardView extends CardView {

    protected void drawContent(Graphics g) {
        g.setFont(font);
        String toughness = creature.getCurrentToughness() + "";
        int toughnessWidth = fontMetrics.stringWidth(toughness);
        g.setColor(creature.getCurrentToughness() < creature.printedToughness ? Color.RED : Color.BLACK);
        g.drawString(toughness, WIDTH-inset-2-toughnessWidth, HEIGHT-inset-2);

        String power = creature.getCurrentPower() + "/";
        int powerWidth = fontMetrics.stringWidth(power);
        g.setColor(Color.black);
        g.drawString(power, WIDTH-inset-2-toughnessWidth-powerWidth, HEIGHT-inset-2);
    }

    public CreatureCardView(Creature creature) {
        super(creature);
        this.creature = creature;
    }

    private Creature creature;
}
