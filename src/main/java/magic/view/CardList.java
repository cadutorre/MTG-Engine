package magic.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import magic.card.Card;

public class CardList extends JPanel {

	private static final long serialVersionUID = -417772062438438903L;

	public void updateCards() {
		cardModel.updateCards();
	}

	public void updateCard(Card c) {
		cardModel.updateCard(c);
	}

	public void addCard(CardView c) {
		synchronized (this) {
			cardModel.addElement(c);
		}
	}

	public void removeCard(Card card) {
		for (int i = 0; i < cardModel.size(); ++i) {
			if (cardModel.getElementAt(i).getCard() == card) {
				cardModel.remove(i);
				return;
			}
		}

		repaint();
	}

	public CardList(String name, final GameUI ui) {
		this.ui = ui;

		setLayout(new FlowLayout());

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

		JLabel nameLabel = new JLabel(name);
		nameLabel.setUI(VerticalLabelUI.INSTANCE);
		add(nameLabel);
		add(cardScroller);
	}

	private class CardRenderer implements ListCellRenderer<CardView> {

		@Override
		public Component getListCellRendererComponent(JList<? extends CardView> jList, CardView cardView, int i,
				boolean b, boolean b2) {
			return cardView;
		}
	}

	private class CardListModel extends DefaultListModel<CardView> {

		private static final long serialVersionUID = 1810713020588325979L;

		void updateCard(Card c) {
			for (int i = 0; i < size(); ++i) {
				if (getElementAt(i).card == c) {
					fireContentsChanged(CardList.this, i, i);
					return;
				}
			}
		}

		void updateCards() {
			fireContentsChanged(CardList.this, 0, size() - 1);
		}
	}

	private GameUI ui;
	private CardListModel cardModel;
	private JList<CardView> cards;
	private JScrollPane cardScroller;
}
