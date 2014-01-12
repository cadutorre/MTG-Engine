package magic.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CardList extends JPanel {

    public void addCard(CardView c) {
        cardModel.addElement(c);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        cardScroller.setPreferredSize(new Dimension(width, height));
    }

    public CardList(String name) {
        setBorder(new TitledBorder(name));

        cardModel = new DefaultListModel<>();

        cards = new JList<>(cardModel);
        cards.setCellRenderer(new CardRenderer());
        cards.setFixedCellHeight(CardView.HEIGHT + 20);
        cards.setFixedCellWidth(CardView.WIDTH + 20);
        cards.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        cards.setVisibleRowCount(1);

        cardScroller = new JScrollPane(cards);
        cardScroller.setSize(750, CardView.HEIGHT + 50);

        add(cardScroller);
    }

    private class CardRenderer implements ListCellRenderer<CardView> {

        @Override
        public Component getListCellRendererComponent(JList<? extends CardView> jList, CardView cardView, int i, boolean b, boolean b2) {
            return cardView;
        }
    }

    private DefaultListModel<CardView> cardModel;
    private JList<CardView> cards;
    private JScrollPane cardScroller;
}
