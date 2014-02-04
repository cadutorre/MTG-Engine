package magic.view;

import magic.card.Card;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class CardList extends JPanel {

    public void updateCards() {
        cardModel.updateCards();
    }

    public void updateCard(Card c) {
        cardModel.updateCard(c);
    }

    public void addCard(CardView c) {
        synchronized(this) {
            cardModel.addElement(c);
        }
    }

    public void removeCard(Card card) {
        for (int i = 0; i<cardModel.size(); ++i) {
            if (cardModel.getElementAt(i).getCard() == card) {
                cardModel.remove(i);
                return;
            }
        }

        repaint();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }

    public CardList(String name, final GameUI ui) {
        this.ui = ui;
        setBorder(new TitledBorder(name));

        cardModel = new CardListModel();

        cards = new JList<>(cardModel);
        cards.setCellRenderer(new CardRenderer());
        cards.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        cards.setVisibleRowCount(1);

        cards.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (cards.getSelectedValue() != null) {
                    ui.cardClicked(cards.getSelectedValue().card);
                    cards.setSelectedIndex(-1);
                }
            }
        });

        cardScroller = new JScrollPane(cards);
        cardScroller.setPreferredSize(new Dimension(700, CardView.HEIGHT + 24));

        add(cardScroller);
    }

    private class CardRenderer implements ListCellRenderer<CardView> {

        @Override
        public Component getListCellRendererComponent(JList<? extends CardView> jList, CardView cardView, int i, boolean b, boolean b2) {
            return cardView;
        }
    }

    private class CardListModel extends DefaultListModel<CardView> {
        void updateCard(Card c) {
            for (int i = 0; i<size(); ++i) {
                if (getElementAt(i).card == c) {
                    fireContentsChanged(CardList.this, i, i);
                    return;
                }
            }
        }
        void updateCards() {
            fireContentsChanged(CardList.this, 0, size()-1);
        }
    }

    private GameUI ui;
    private CardListModel cardModel;
    private JList<CardView> cards;
    private JScrollPane cardScroller;
}
