package magic.view;

import magic.card.creature.Creature;

import java.awt.*;

public class CreatureCardView extends CardView {

    protected void drawContent(Graphics g) {
        String powerToughness = creature.getCurrentPower() + "/" + creature.getCurrentToughness();
        g.setFont(font);
        g.drawString(powerToughness, inset + 2, getHeight() - inset - 2);
    }

    public CreatureCardView(Creature creature) {
        super(creature);
        this.creature = creature;
    }

    private Creature creature;
}
