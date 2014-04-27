package blockamon.controllers;

import blockamon.io.Load;
import blockamon.io.Save;
import blockamon.items.Item;
import blockamon.objects.Blockamon;
import blockamon.objects.Player;
import blockamon.objects.buildings.HealingCenter;
import blockamon.objects.buildings.ItemShop;
import blockamon.objects.encounters.Grass;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.*;

public class ControlPanel extends JPanel {

    private enum MenuItemType {
        ATTACK("Attack"), FLEE("Flee"), SWITCH("Switch"),
        HEAL("Heal"), ITEM("Item"), MONEY("Money"), INFO("Info"),
        BUY("Buy"), SAVE("Save"), LOAD("Load"),
        BACK("Back");

        private String text;

        MenuItemType(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private enum MenuType {
        EXPLORE(), BATTLE(), SHOP("Shop"), HEAL("Heal"),
        SWITCH("Switch"), INFO("Info"), ITEM("Items");

        private String text;

        MenuType() {
            this("Actions");
        }

        MenuType(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public Map<MenuItemType, JMenuItem> menuItems;
    public Map<MenuType, JMenu> menus;
    public JMenuBar menuBar;

    public ControlPanel() {
        menuItems = getMenuItems();
        menus = getMenus();
        attachMenuItems();
        menuBar = createMenuBar(menus);
    }

    private JMenuBar createMenuBar(Map<MenuType, JMenu> menuMap) {
        final JMenuBar jMenuBar = new JMenuBar();
        Collection<JMenu> menus = menuMap.values();
        for(final JMenu menu : menus) {
            jMenuBar.add(menu);
        }
        return jMenuBar;
    }
    private Map<MenuItemType, JMenuItem> getMenuItems() {
        final Map<MenuItemType, JMenuItem> menuItems = new HashMap<MenuItemType, JMenuItem>();
        final MenuItemType[] menuItemTypeses = MenuItemType.values();
        for(final MenuItemType menuItemType : menuItemTypeses) {
            menuItems.put(menuItemType, new JMenuItem(menuItemType.getText()));
        }
        return menuItems;
    }
    private Map<MenuType, JMenu> getMenus() {
        final Map<MenuType, JMenu> menus = new HashMap<MenuType, JMenu>();
        final MenuType[] menuTypes = MenuType.values();
        for(final MenuType menuType : menuTypes) {
            menus.put(menuType, new JMenu(menuType.getText()));
        }
        return menus;
    }
    private void attachMenuItems() {
        addToMenu(MenuType.BATTLE, MenuItemType.ATTACK, MenuItemType.SWITCH,
                         MenuItemType.FLEE, MenuItemType.ITEM, MenuItemType.INFO);
        addToMenu(MenuType.HEAL, MenuItemType.HEAL);
        addToMenu(MenuType.SHOP, MenuItemType.BUY);

    }
    private void addToMenu(MenuType menuType, MenuItemType... menuItemTypes) {
        final JMenu menu = getMenu(menuType);
        for(final MenuItemType menuItemType : menuItemTypes) {
            menu.add(getMenuItem(menuItemType));
        }
        setMenu(menuType, menu);
    }
    private void removeFromMenu(MenuType menuType, MenuItemType... menuItemTypes) {
        final JMenu menu = getMenu(menuType);
        for(final MenuItemType menuItemType : menuItemTypes) {
            menu.remove(getMenuItem(menuItemType));
        }
        setMenu(menuType, menu);
    }
    private JMenuItem getMenuItem(MenuItemType menuItemType) {
        return menuItems.get(menuItemType);
    }
    private JMenu getMenu(MenuType menuType) {
        return menus.get(menuType);
    }
    private void setMenu(MenuType menuType, JMenu menu) {
        menus.put(menuType, menu);
    }

    private void setMenuEnabled(MenuType menuType, boolean isEnabled) {
        final JMenu menu = getMenu(menuType);
        menu.setEnabled(isEnabled);
        setMenu(menuType, menu);
    }
    private void disableMenu(MenuType menuType) {
        setMenuEnabled(menuType, false);
    }
    private void enableMenu(MenuType menuType) {
        setMenuEnabled(menuType, true);
    }






	/**
	 * Author: Jack Gilliam Date: 4/17/2011
	 */
	private JMenuItem attackButton, fleeButton, healingButton,
            itemButton, moneyButton,
            switchButton, OOBswitchButton,
            buyButton, saveButton, loadButton,
            showInfoButton, OOBshowInfoButton,
            switchBackButton, storeBackButton, inventoryBackButton, infoBackButton, OOBinfoBackButton, OOBswitchBackButton;
	private ItemShop itemShop;
    private HealingCenter healingCenter;
	private Blockamon blockamon;
	private Grass wildGrass;
    private ArrayList<JMenuItem> _inventoryButtons;
	private JMenuItem[] partyButtons, storeButtons, infoButtons;
	private Player player;
	private JMenu OutOfBattle;
	private JMenu Battle;
	private JMenu Shop;
	private JMenu Heal;
	private JMenu ItemsInShop;
	private JMenu PlayerItems;
	private JMenu info;
	private JMenu switchMenu;
	private static final int CHANCETOCATCHWHENHIGHEST = 500;
	private static final int CHANCETOCATCHWHENHIGH = 400;
	private static final int CHANCETOCATCHWHENLOW = 300;
	private static final int CHANCETOCATCHWHENLOWEST = 250;
	private static final int CHANCETOCATCH = 200;

    /**
     * FORMATS
     */
    private static final String STATUS_FORMAT = "%nStatus: %s";
    private static final String LEVEL_FORMAT = "Level: %d";
    private static final String ATTACK_FORMAT = "%nAttack: %d/TotalAttack: %d";
    private static final String TYPE_FORMAT = "%nBlockamonType: %s";
    private static final String EXP_FORMAT = "%nCurrent Experience: %d/Needed Experience: %d";
    private static final String HEALTH_FORMAT = "Current Health: %d/Max Health: %d";
    private static final String INFO_FORMAT = LEVEL_FORMAT + ATTACK_FORMAT + STATUS_FORMAT + TYPE_FORMAT + EXP_FORMAT;

	public ControlPanel(Player player, ItemShop itemShop, HealingCenter healingCenter, Grass wildGrass) {
		super();
		this.setLayout(new FlowLayout());
		this.player = player;
		this.itemShop = itemShop;
        this.wildGrass = wildGrass;
        this.healingCenter = healingCenter;
        _inventoryButtons = new ArrayList<JMenuItem>();
		partyButtons = new JMenuItem[this.player.getPartyLimit()];
		storeButtons = new JMenuItem[this.itemShop.getNumberOfItems()];
		infoButtons = new JMenuItem[this.player.getPartyLimit()];
		createMenu();
	}
	private void createMenu() {
		JMenuBar theMenu = new JMenuBar();
		OutOfBattle = addMenus("Actions", theMenu);
		Battle = addMenus("Battle", theMenu);
		Shop = addMenus("ItemShop", theMenu);
		Heal = addMenus("HealCenter", theMenu);
		switchMenu = addMenus("Switching", theMenu);
		ItemsInShop = addMenus("Shop", theMenu);
		PlayerItems = addMenus("Your Items", theMenu);
		info = addMenus("Info", theMenu);

		attackButton = addItems("Attack", Battle);
		switchButton = addItems("Switch", Battle);
		fleeButton= addItems("Flee", Battle);
		itemButton = addItems("Item", Battle);
		showInfoButton = addItems("Info", Battle);

		healingButton = addItems("Heal", Heal);

		buyButton = addItems("Buy", Shop);

		inventoryBackButton = addItems("Back", null);
		switchBackButton = addItems("Back", null);
		infoBackButton = addItems("Back", null);
		storeBackButton = addItems("Back", null);
		OOBswitchBackButton = addItems("Back", null);
		OOBinfoBackButton = addItems("Back", null);

		moneyButton = addItems("Money", OutOfBattle);
		saveButton = addItems("Save", OutOfBattle);
		loadButton = addItems("Load", OutOfBattle);
		OOBswitchButton = addItems("Switch", OutOfBattle);
		OOBshowInfoButton = addItems("Info", OutOfBattle);
		this.add(theMenu);
	}
	private JMenu addMenus(String text, JMenuBar theBar) {
		JMenu aMenu = new JMenu(text);
		theBar.add(aMenu);
		aMenu.setVisible(false);
		return aMenu;
	}
	private JMenuItem addItems(String text, JMenu aMenu) {
		JMenuItem anItem = new JMenuItem(text);
		actions(anItem);
		if(aMenu != null)
		{
			aMenu.add(anItem);
		}
		return anItem;
	}
    //TODO: Needs big time refactoring
	private void actions(JMenuItem... items) {
		for(JMenuItem item: items)
		{
			item.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    // converts the source into a JButton
                    JMenuItem source = (JMenuItem) e.getSource();
                    // if the fleeButton was pressed
                    if (source == fleeButton) {
                        System.out.println("fleeing");
                        wildGrass.tryToFlee(player);
                        // if the attackButton was pressed
                    } else if (source == attackButton) {
                        wildGrass.doAttack(player);
                        // if the healingButton was pressed
                    } else if (source == healingButton) {
                        player.healAllBlockamon();
                        // if the itemButton was pressed
                    } else if (source == itemButton) {
                        createItemButtons(true);
                    } else if (source == switchButton) {
                        createPartyButtons(true);
                    } else if (source == OOBswitchButton) {
                        createPartyButtons(false);
                    } else if (source == switchBackButton) {
                        removeButtons("Switch");
                        removeButtons("SwitchButton");
                        addButtons("Battle");
                    } else if (source == OOBswitchBackButton) {
                        removeButtons("Switch");
                        removeButtons("OOBSwitchButton");
                        addButtons("OutOfBattle");
                    } else if (source == inventoryBackButton) {
                        removeButtons("Items");
                        removeButtons("ItemsButton");
                        addButtons("Battle");
                    } else if (source == buyButton) {
                        printText("Amount of money you have: "
                                          + player.getMoney(),
                                         "Total Money to Spend",
                                         JOptionPane.INFORMATION_MESSAGE, "info");
                        createStoreButtons();
                    } else if (source == storeBackButton) {
                        removeButtons("Store");
                        addButtons("ItemShop");
                    } else if (source == moneyButton) {
                        printText("Your total Money: " + player.getMoney(),
                                         "Total Money", JOptionPane.INFORMATION_MESSAGE,
                                         "info");
                    } else if (source == saveButton) {
                        @SuppressWarnings("unused")
                        Save aNewSaveFile = new Save(player);
                    } else if (source == loadButton) {
                        @SuppressWarnings("unused")
                        Load loadAGame = new Load(player);
                    } else if (source == showInfoButton) {
                        createInfoButtons(true);
                    } else if (source == OOBshowInfoButton) {
                        createInfoButtons(false);
                    } else if (source == infoBackButton) {
                        removeButtons("Info");
                        removeButtons("infoButton");
                        addButtons("Battle");
                    } else if (source == OOBinfoBackButton) {
                        removeButtons("Info");
                        removeButtons("OOBinfoBackButton");
                        addButtons("OutOfBattle");
                    }
                }
            });
		}
	}
	private void buyThatItem(JMenuItem theButton) {
		for (int position = 0; position < storeButtons.length; position++) {
			if (storeButtons[position] == theButton) {
				// if the item in the specified position is not null
				if (itemShop.getSpecificItem(position) != null) {
					Item storeItem = itemShop.getSpecificItem(position);
					int input = printText2("Are you sure you want this item?",
							"Do want?", JOptionPane.QUESTION_MESSAGE, "info");
					if (input == 0) {
						player.addItem(storeItem);
					} else {
						printText("Item was not bought", "Inform",
								JOptionPane.INFORMATION_MESSAGE, "info");
					}
				} else {
					printText("That item does not exist", "Item no Exist",
							JOptionPane.ERROR_MESSAGE, "error");
				}
			}
		}
	}
	private void createStoreButtons() {
		// there is not item
		boolean thereIsAnItem = false;
		// for however long the itemButton array is
		for (int storeSlotNum = 0; storeSlotNum < storeButtons.length; storeSlotNum++) {
			Item storeItem = itemShop.getSpecificItem(storeSlotNum);
			if (storeItem != null) {
				if(storeButtons[storeSlotNum] != null)
				{
					ItemsInShop.remove(storeButtons[storeSlotNum]);
				}
					// puts a button inside the itemButtons array
				storeButtons[storeSlotNum] = createStoreButtons("$"
						+ (storeItem.getPrice()) + " "
						+ storeItem);
				ItemsInShop.add(storeButtons[storeSlotNum]);
				// there is an item
				thereIsAnItem = true;
			}
		}
		ItemsInShop.add(storeBackButton);
		// if there are items add the buttons
		if (thereIsAnItem) {
			removeButtons("ItemShop");
			addButtons("Store");
			// if there are no items tell the user that
		} else {
			printText("No items in the store at this time",
					"Items not Available", JOptionPane.ERROR_MESSAGE, "error");
		}
	}
	private JMenuItem createStoreButtons(String text) {
		// sets the text that appears on the button
		JMenuItem button = new JMenuItem(text);
		// adds an ActionListener to the button
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JMenuItem theButton = (JMenuItem) e.getSource();
				buyThatItem(theButton);
			}
		});
		return button;
	}
	private void createInfoButtons(boolean inBattle) {
		// there is not item
		boolean thereIsABlockamon = false;
		// for however long the itemButton array is
		for (int partySlotNum = 0; partySlotNum < infoButtons.length; partySlotNum++) {
			Blockamon playerBlockamon = player.getBlockamonAt(partySlotNum);
			if (playerBlockamon != null) {
				if(infoButtons[partySlotNum] != null)
				{
					info.remove(infoButtons[partySlotNum]);
				}
					// puts a button inside the partyButtons array
					infoButtons[partySlotNum] = createInfoButtons( ""
							+ playerBlockamon.getName(), inBattle);
					info.add(infoButtons[partySlotNum]);
					// there is an item
					thereIsABlockamon = true;
			} else {
				partySlotNum = partyButtons.length;
				this.repaint();
			}
		}
		info.add(infoBackButton);
		info.add(OOBinfoBackButton);
		infoBackButton.setVisible(false);
		OOBinfoBackButton.setVisible(false);
		if (thereIsABlockamon) {
			removeButtons("Battle");
			removeButtons("OutOfBattle");
			addButtons("Info");
			if (inBattle) {
				addButtons("InfoButton");
			} else {
				addButtons("OOBinfoButton");
			}
		} else {
			printText("You dont have any Blockamon", "Blocks Not here",
					JOptionPane.ERROR_MESSAGE, "error");
		}
	}
	private JMenuItem createInfoButtons(String text, boolean inBattle) {
		final boolean amInBattle = inBattle;
		// sets the text that appears on the button
		JMenuItem button = new JMenuItem(text);
		// adds an ActionListener to the button
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JMenuItem theButton = (JMenuItem) e.getSource();
				displayInfo(theButton, amInBattle);
			}
		});
		// sets the bounds of the item
		return button;
	}
	private void displayInfo(JMenuItem theButton, boolean inBattle) {
		for (int position = 0; position < infoButtons.length; position++) {
			if (infoButtons[position] == theButton) {
				Blockamon chosenBlockamon = player.getBlockamonAt(position);
				if (chosenBlockamon != null) {
					printText(chosenBlockamon.toString(), "Info for " + chosenBlockamon.getName(), JOptionPane.INFORMATION_MESSAGE, "info");
					if (inBattle) {
						addButtons("Battle");
						removeButtons("Info");
						removeButtons("InfoButton");
					} else {
						addButtons("OutOfBattle");
						removeButtons("Info");
						removeButtons("OOBinfoButton");
					}
				}
			}
		}
	}
	public void storeTheBlockamon(Blockamon opponentBlockamon) {
		blockamon = opponentBlockamon;
	}
	public void createPartyButtons(boolean inBattle) {
		// first button number
		int buttonNumber = 1;
		// there is not item
		boolean thereIsABlockamon = false;
		// for however long the itemButton array is
		for (int partySlotNum = 0; partySlotNum < partyButtons.length; partySlotNum++) {
			Blockamon playerBlockamon = player.getBlockamonAt(partySlotNum);
			if (playerBlockamon != null) {
				if(partyButtons[partySlotNum] != null)
				{
					switchMenu.remove(partyButtons[partySlotNum]);
				}
				// puts a button inside the partyButtons array
				partyButtons[partySlotNum] = createPartyButtons(
						" " + (buttonNumber) + " "
								+ playerBlockamon.getName(), inBattle);
				switchMenu.add(partyButtons[partySlotNum]);
				// there is an item
				thereIsABlockamon = true;
			} else {
				partySlotNum = partyButtons.length;
				this.repaint();
			}
		}
		switchMenu.add(switchBackButton);
		switchBackButton.setVisible(false);
		switchMenu.add(OOBswitchBackButton);
		OOBswitchBackButton.setVisible(false);
		if (thereIsABlockamon) {
			removeButtons("Battle");
			removeButtons("OutOfBattle");
			addButtons("Switch");
			if (inBattle) {
				addButtons("SwitchButton");
			} else {
				addButtons("OOBSwitchButton");
			}
		} else {
			printText("You dont have any Blockamon", "Blocks Not here",
					JOptionPane.ERROR_MESSAGE, "error");
		}
	}
	// creates the individual buttons
	private JMenuItem createPartyButtons(String text, boolean inBattle) {
		final boolean amInBattle = inBattle;
		// sets the text that appears on the button
		JMenuItem button = new JMenuItem(text);
		// adds an ActionListener to the button
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JMenuItem theButton = (JMenuItem) e.getSource();
				switchBlockamon(theButton, amInBattle);
			}
		});
		return button;
	}
	private static String printText(String text, String title,
			int typeOfMessage, String methodToUse) {
		String input = null;
		if (methodToUse.equals("input")) {
			input = JOptionPane.showInputDialog(null, text, title,
					typeOfMessage);
		} else if (methodToUse.equals("info") || methodToUse.equals("error")) {
			JOptionPane.showMessageDialog(null, text, title, typeOfMessage);
		}
		return input;
	}
	private static int printText2(String text, String title, int typeOfMessage,
			String methodToUse) {
		int choice = -100;
		String[] options = { "Yes", "No" };
		choice = JOptionPane.showOptionDialog(null, text, title,
				JOptionPane.OK_CANCEL_OPTION, typeOfMessage, null, options,
				options[1]);
		return choice;
	}
	private void switchBlockamon(JMenuItem theButton, boolean inBattle) {
		for (int position = 0; position < partyButtons.length; position++) {
			if (partyButtons[position] == theButton) {
				Blockamon chosenBlockamon = player.getBlockamonAt(position);
				Blockamon activeBlockamon = player.getLeadBlockamon();
				if (!chosenBlockamon.equals(activeBlockamon)) {
                    activeBlockamon.isLead(false);
                    chosenBlockamon.isLead(true);
                    printText(chosenBlockamon.getName()
                                      + " is now the active Blockamon",
                                     "Active Blockamon has changed",
                                     JOptionPane.INFORMATION_MESSAGE, "info");
                    if (inBattle) {
                        addButtons("Battle");
                        removeButtons("Switch");
                        removeButtons("SwitchButton");
                        wildGrass.printStats(player);
                        wildGrass.addPlayersBlock(activeBlockamon,
                                chosenBlockamon);
                    } else {
                        addButtons("OutOfBattle");
                        removeButtons("Switch");
                        removeButtons("OOBSwitchButton");
                    }
				} else {
					printText("You cannot select the same Blockamon",
							"No can do", JOptionPane.ERROR_MESSAGE, "error");
				}
			}
		}
	}
	// does the player catch the blockamon
	private boolean wasCaught(Blockamon PBlock) {
		boolean amCaught = false;
		int maxCaughtChance = CHANCETOCATCHWHENHIGHEST;
		Random randNum = new Random();
		// if the enemy Blockamon is past 75% health left
		if (PBlock.getCurrentHealth() <= (PBlock.getTotalHealth() * .75)) {
			maxCaughtChance = CHANCETOCATCHWHENHIGH;
		}
		// if the enemy Blockamon is past 50% health left
		if (PBlock.getCurrentHealth() <= (PBlock.getTotalHealth() * .5)) {
			maxCaughtChance = CHANCETOCATCHWHENLOW;
		}
		// if the enemy Blockamon is past 25% health left
		if (PBlock.getCurrentHealth() <= (PBlock.getTotalHealth() * .25)) {
			maxCaughtChance = CHANCETOCATCHWHENLOWEST;
		}
		int catchValue = randNum.nextInt(maxCaughtChance);
		if (catchValue <= CHANCETOCATCH) {
			amCaught = true;
		}
		return amCaught;
	}
	private void createItemButtons(boolean inBattle) {
		Set<Item> items = player.getItems();
        for(JMenuItem menuItem : _inventoryButtons) {
            PlayerItems.remove(menuItem);
        }
        for(Item playerItem : items) {
            JMenuItem button = createItemButtons(playerItem);
            _inventoryButtons.add(button);
            PlayerItems.add(button);
        }
		PlayerItems.add(inventoryBackButton);
		// if there are items add the buttons
		if (items.size() > 0) {
			removeButtons("Battle");
			addButtons("Items");
			if (inBattle) {
				addButtons("ItemButton");
			} else {
				addButtons("OOBItemButton");
			}
			// if there are no items tell the user that
		} else {
			printText("You have no items", "Whatcha Doin?",
					JOptionPane.ERROR_MESSAGE, "error");
		}
	}
	// creates the individual buttons
	private JMenuItem createItemButtons(Item item) {
		// sets the text that appears on the button
		JMenuItem button = new JMenuItem(item.toString());
		// adds an ActionListener to the button
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JMenuItem theButton = (JMenuItem) e.getSource();
				activateThatItem(theButton);
			}
		});
		return button;
	}
	private void activateThatItem(JMenuItem theButton) {
        for(JMenuItem inventoryButton : _inventoryButtons) {
			if (inventoryButton == theButton) {
                    String itemName = theButton.getText();
                    Item playersItem = Item.valueOf(itemName);
					Blockamon PBlock = player.getLeadBlockamon();
					// if the item is a HealVial
					if (playersItem.equals(Item.HEALVIAL)) {
						// if the players Blockamon exists
						if (PBlock != null) {
							// heal the blockamon for 10 health
							PBlock.heal((int) playersItem.getHealAmount());
							// inform the user what their blockamons health is
							printText("Your Blockamon Now has "
									+ PBlock.getCurrentHealth() + " Health",
									"Current Health",
									JOptionPane.INFORMATION_MESSAGE, "info");
							// set the item in that position to null
							player.removeItem(playersItem);
							// add the battle buttons
							addButtons("Battle");
							// remove the item buttons
							removeButtons("Items");
							removeButtons("ItemsButton");
						} else {
							printText("You have no items",
									"No Items Currently",
									JOptionPane.ERROR_MESSAGE, "error");
						}
					}
					if (playersItem.equals(Item.BLOCKABALL)) {
						// if the players Blockamon exists
						if (PBlock != null) {
							if (wasCaught(PBlock)) {
								printText("The Blockamon was caught",
										"Congratulations",
										JOptionPane.PLAIN_MESSAGE, "info");
								String usersAnswer = printText(
										"would you like to name the caught Blockamon? Y for YES Anything else for NO",
										"Want Name?",
										JOptionPane.QUESTION_MESSAGE, "input");
								if (usersAnswer != null
										&& !usersAnswer.equals("")) {
									if (usersAnswer.equalsIgnoreCase("Y")
											|| usersAnswer
													.equalsIgnoreCase("yes")) {
										boolean correctName = false;
										while (!correctName) {
											String playerChosenName = printText(
													"what would you like to name it? ",
													"What Name?",
													JOptionPane.QUESTION_MESSAGE,
													"input");
											if (playerChosenName != null
													&& !playerChosenName
															.equals("")) {
												blockamon.setName(playerChosenName);
												correctName = true;
											} else {
												printText(
														"That name is incorrect please try again",
														"IncorrectName",
														JOptionPane.ERROR_MESSAGE,
														"info");
											}
										}
									}
								}
								PBlock.resetAttack();
								blockamon.resetAttack();
								player.addToParty(blockamon);
								removeButtons("Items");
								removeButtons("ItemsButton");
								wildGrass.battleOver();
							} else {
								printText("The Blockamon was almost caught",
										"Aw darn! Try again Later",
										JOptionPane.INFORMATION_MESSAGE, "info");
								addButtons("Battle");
								removeButtons("Items");
								removeButtons("ItemsButton");
							}
						}
					}
					player.removeItem(playersItem);
				} else {
					printText("That item does not exist", "Not an Item",
							JOptionPane.ERROR_MESSAGE, "error");
				}
			}
	}
	// adds each button
	public void addButtons(String type) {
		// if the control panel wants battle buttons
		if (type.equalsIgnoreCase("Battle")) {
//			// add the battle related buttons
			Battle.setVisible(true);
			// if it wants the Healing buttons
		} else if (type.equalsIgnoreCase("OutOfBattle")) {
			OutOfBattle.setVisible(true);
		} else if (type.equalsIgnoreCase("HealCenter")) {
			// add the healingCenter related buttons
			Heal.setVisible(true);
			// if something wants the item buttons this adds the item buttons
		} else if (type.equalsIgnoreCase("Items")) {
			PlayerItems.setVisible(true);
			inventoryBackButton.setVisible(true);
		} else if (type.equalsIgnoreCase("Info")) {
			info.setVisible(true);
		} else if (type.equalsIgnoreCase("InfoButton")) {
			infoBackButton.setVisible(true);
		} else if (type.equalsIgnoreCase("OOBinfoButton")) {
			OOBinfoBackButton.setVisible(true);
		} else if (type.equalsIgnoreCase("Switch")) {
			switchMenu.setVisible(true);
		} else if (type.equalsIgnoreCase("SwitchButton")) {
			switchBackButton.setVisible(true);
		} else if (type.equalsIgnoreCase("OOBSwitchButton")) {
			OOBswitchBackButton.setVisible(true);
		} else if (type.equalsIgnoreCase("ItemsButton")) {
		} else if (type.equalsIgnoreCase("OOBItemsButton")) {

		} else if (type.equalsIgnoreCase("ItemShop")) {
			Shop.setVisible(true);
		} else if (type.equalsIgnoreCase("store")) {
			ItemsInShop.setVisible(true);
			storeBackButton.setVisible(true);
		}
		this.repaint();
	}
	public void removeButtons(String type) {
		// if something wants to get rid of the battle buttons
		if (type.equalsIgnoreCase("Battle")) {
//			// remove all battle buttons
			Battle.setVisible(false);
			// if something wants to get rid of the healing buttons
		} else if (type.equalsIgnoreCase("OutOfBattle")) {
			OutOfBattle.setVisible(false);
		} else if (type.equalsIgnoreCase("HealCenter")) {
			// if the healingButton exists
			Heal.setVisible(false);
			// if something wants the items button
		} else if (type.equalsIgnoreCase("Items")) {
//			// for however many buttons there are
			PlayerItems.setVisible(false);
			inventoryBackButton.setVisible(false);
		} else if (type.equalsIgnoreCase("ItemsButton")) {
		} else if (type.equalsIgnoreCase("OOBItemsButton")) {
		} else if (type.equalsIgnoreCase("Info")) {
			for(JMenuItem aButton: infoButtons)
			{
				if(aButton != null)
				{
					info.remove(aButton);
				}
			}
			info.setVisible(false);
		} else if (type.equalsIgnoreCase("InfoButton")) {
			infoBackButton.setVisible(false);
		} else if (type.equalsIgnoreCase("OOBinfoBackButton")) {
			OOBinfoBackButton.setVisible(false);
		} else if (type.equalsIgnoreCase("Switch")) {
			switchBackButton.setVisible(false);
			switchMenu.setVisible(false);
		} else if (type.equalsIgnoreCase("SwitchButton")) {
			switchBackButton.setVisible(false);
		} else if (type.equalsIgnoreCase("OOBSwitchButton")) {
			OOBswitchBackButton.setVisible(false);
		} else if (type.equalsIgnoreCase("ItemShop")) {
			Shop.setVisible(false);
		} else if (type.equalsIgnoreCase("store")) {
			ItemsInShop.setVisible(false);
			storeBackButton.setVisible(false);
		}
		this.repaint();
	}
}
