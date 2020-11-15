package com.flippingutilities.ui.gehistorytab;

import com.flippingutilities.OfferEvent;
import com.flippingutilities.ui.utilities.UIUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import java.util.function.BiConsumer;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import net.runelite.api.GrandExchangeOfferState;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.FontManager;

public class GeHistoryTabOfferPanel extends JPanel
{
	private int offerId;
	private BiConsumer<Integer, Boolean> onCheckBoxChangeCallback;


	public GeHistoryTabOfferPanel(OfferEvent offer, List<OfferEvent> matchingOffers, int offerId, BiConsumer<Integer, Boolean> onCheckBoxChangeCallback)
	{
		this.offerId = offerId;
		this.onCheckBoxChangeCallback = onCheckBoxChangeCallback;
		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARKER_GRAY_COLOR);
		setBorder(new EmptyBorder(0,0,0,5));
		JPanel checkBoxPanel = new JPanel(new BorderLayout());
		checkBoxPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR.darker());
		JCheckBox checkBox = new JCheckBox();
		checkBox.setForeground(ColorScheme.GRAND_EXCHANGE_PRICE);
		//checkBox.setBackground(ColorScheme.GRAND_EXCHANGE_PRICE);
		checkBox.setFocusPainted(false);
		//checkBox.setEnabled(false);
		checkBoxPanel.add(checkBox, BorderLayout.CENTER);
		checkBox.addItemListener(itemEvent -> onCheckBoxChangeCallback.accept(offerId, itemEvent.getStateChange() == itemEvent.SELECTED));
		add(checkBoxPanel, BorderLayout.WEST);
		add(createInfoPanel(offer, matchingOffers), BorderLayout.CENTER);
	}

	public JPanel createInfoPanel(OfferEvent offer, List<OfferEvent> matchingOffers)
	{
		JPanel infoPanel = new JPanel(new BorderLayout());
		infoPanel.setBorder(new EmptyBorder(3, 6, 3, 3));
		infoPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		JLabel itemNameLabel = new JLabel(offer.getItemName(), SwingConstants.CENTER);
		itemNameLabel.setFont(FontManager.getRunescapeBoldFont());
		itemNameLabel.setForeground(Color.WHITE);
		itemNameLabel.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		JPanel offerDetailsPanel = new JPanel(new DynamicGridLayout(3, 1, 0, 0));



		JPanel statePanel = new JPanel(new BorderLayout());
		JLabel leftStateLabel = new JLabel("State:");
		JLabel rightStateLabel = new JLabel(offer.getState() == GrandExchangeOfferState.BOUGHT ? "Bought" : "Sold");
		statePanel.add(leftStateLabel, BorderLayout.WEST);
		statePanel.add(rightStateLabel, BorderLayout.EAST);
		statePanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		JPanel quantityPanel = new JPanel(new BorderLayout());
		JLabel leftQuantityLabel = new JLabel("Quantity:");
		JLabel rightQuantityLabel = new JLabel(String.valueOf(offer.getCurrentQuantityInTrade()));
		quantityPanel.add(leftQuantityLabel, BorderLayout.WEST);
		quantityPanel.add(rightQuantityLabel, BorderLayout.EAST);
		quantityPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		JPanel pricePanel = new JPanel(new BorderLayout());
		JLabel leftPriceLabel = new JLabel("Price Ea:");
		JLabel rightPriceLabel = new JLabel(String.valueOf(offer.getPrice()));
		pricePanel.add(leftPriceLabel, BorderLayout.WEST);
		pricePanel.add(rightPriceLabel, BorderLayout.EAST);
		pricePanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		offerDetailsPanel.add(statePanel);
		offerDetailsPanel.add(quantityPanel);
		offerDetailsPanel.add(pricePanel);

		infoPanel.add(itemNameLabel, BorderLayout.NORTH);
		infoPanel.add(offerDetailsPanel, BorderLayout.CENTER);
		infoPanel.add(new MatchingOffersPanel(matchingOffers), BorderLayout.SOUTH);
		return infoPanel;
	}

	public void setAdded() {
		//disable checkbox, make checkbox panel ge price
	}
}
