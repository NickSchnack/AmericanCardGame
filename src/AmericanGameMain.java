// AmericanGameMain.java
// Nick Schnack   03-22-10

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

// This is the "American" Card Game
// There is a player and an opponent. The player is automatically the dealer.
public class AmericanGameMain extends JFrame implements ActionListener, MouseListener
{
    //////////////////////////////////////////////////////////////////////////////////////
    // IMPLEMENTATION NOTES
    // - The screen is sectioned off into areas with the GridBagLayout
    //    - opponent's hand
    //    - opponent's up cards
    //    - pile
    //    - deck
    //    - player's up cards
    //    - player's hand
    // - Images of each card are provided
    // - Each area will either display one of the provided images
    //   or will use a Card type to generate a response when an image is clicked
    // - Each area is a little different:
    //    - Opponent's Hand			Array of Card types
    //    - Opponent's Up Cards	Array of Card types
    //    - Pile						Image of card sliver used
    //    								Array of Card types
    //    - Deck						Image of card sliver used
    //    								Card type for top of card used
    //    - Player's Up Cards		Array of Card types
    //    - Player's Hand			Array of Card types
    // - An array of card images is avaliable to display any card according to its index
    //  (follows the same scheme as below)
    // - Each set of Card types is kept in an array to allow them to be accessed by index
    // - When it comes time to deal the cards out, indices are dealt out
    // - Each array of Card types will use the same index for a given card as follows:
    //    C2         Index 000					small C5   Index 055
    //    C3         Index 001					small C6   Index 056
    //    C4         Index 002					small C7   Index 057
    //    C5         Index 003					small C8   Index 058
    //    C6         Index 004					small C9   Index 059
    //    C7         Index 005					small C10  Index 060
    //    C8         Index 006					small C11  Index 061
    //    C9         Index 007					small C12  Index 062
    //    C10        Index 008					small C13  Index 063
    //    C11        Index 009					small C14  Index 064
    //    C12        Index 010					small D2   Index 065
    //    C13        Index 011					small D3   Index 066
    //    C14        Index 012					small D4   Index 067
    //    D2         Index 013					small D5   Index 068
    //    D3         Index 014					small D6   Index 069
    //    D4         Index 015					small D7   Index 070
    //    D5         Index 016					small D8   Index 071
    //    D6         Index 017					small D9   Index 072
    //    D7         Index 018					small D10  Index 073
    //    D8         Index 019					small D11  Index 074
    //    D9         Index 020					small D12  Index 075
    //    D10        Index 021					small D13  Index 076
    //    D11        Index 022					small D14  Index 077
    //    D12        Index 023					small H2   Index 078
    //    D13        Index 024					small H3   Index 079
    //    D14        Index 025					small H4   Index 080
    //    H2         Index 026					small H5   Index 081
    //    H3         Index 027					small H6   Index 082
    //    H4         Index 028					small H7   Index 083
    //    H5         Index 029					small H8   Index 084
    //    H6         Index 030					small H9   Index 085
    //    H7         Index 031					small H10  Index 086
    //    H8         Index 032					small H11  Index 087
    //    H9         Index 033					small H12  Index 088
    //    H10        Index 034					small H13  Index 089
    //    H11        Index 035					small H14  Index 090
    //    H12        Index 036					small S2   Index 091
    //    H13        Index 037					small S3   Index 092
    //    H14        Index 038					small S4   Index 093
    //    S2         Index 039					small S5   Index 094
    //    S3         Index 040					small S6   Index 095
    //    S4         Index 041					small S7   Index 096
    //    S5         Index 042					small S8   Index 097
    //    S6         Index 043					small S9   Index 098
    //    S7         Index 044					small S10  Index 099
    //    S8         Index 045					small S11  Index 100
    //    S9         Index 046					small S12  Index 101
    //    S10        Index 047					small S13  Index 102
    //    S11        Index 048					small S14  Index 103
    //    S12        Index 049					top card 1 Index 104
    //    S13        Index 050					top card 2 Index 105
    //    S14        Index 051					top card 3 Index 106
    //    small C2   Index 052					outline 1  Index 107
    //    small C3   Index 053					outline 2  Index 108
    //    small C4   Index 054					outline 3  Index 109
    //////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////
    // PROGRAM METHOD ORGANIZATION
    // - Global variable initialization
    //
    // - INTERFACE METHODS (sorted alphabetically)
    // - main
    // - AmericanGameMain
    // - addComp
    // - addIconsToPanel
    // - clearMessages
    // - createImageIcon
    // - registerCardListeners
    // - resizeCards
    // - resizeImageIcon
    // - sendMessage
    // - setWinLossPanel
    // - updateDisplayedCards
    // - updateSetDiffMenu
    //
    // - FILE METHODS (sorted alphabetically)
    // - createDataFile
    // - importDataFile
    // - openGameRulesPDF
    // - saveDataFile
    //
    // - BUTTON AND MOUSE METHODS (sorted alphabetically)
    // - actionPerformed
    // - mouseClicked
    // - mouseEntered
    // - mouseExited
    // - mousePressed
    // - mouseReleased
    //
    // - GAME LOGIC METHODS (sorted logically
    // - beginGame
    // - setupGameArea
    // - dealCardsAndDisplay
    // - getDeck
    // - shuffle
    // - dealDeck
    // - sortCardArrayList
    // - getIndexSortPosition
    // - getIndexValue
    // - selectLowestCardAndDisplay
    // - executePlayerTurn
    // - playerPickupPile
    // - playerCardLaidDownValid
    // - playerMultipleCardsCheck
    // - playerLayDownCard
    // - playerDrawCard	
    // - playerPickupUpDown
    // - executeOpponentTurn
    // - opponentBeatTopCardQuery
    // - opponentPickupQuery
    // - opponentPickupPile
    // - opponentLayDownCard
    // - opponentCardThrowPriority
    // - opponentCardLaidDownValid
    // - opponentDrawCard
    // - opponentPickupUpDown
    // - killPile
    // - checkWinCondition
    //////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //																												//
    // GLOBAL VARIABLE INITIALIZATION																	//
    //																												//
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    // Global Variables
    ArrayList opponentHandCards;
    ArrayList opponentUpCards;
    ArrayList opponentDownCards;
    ArrayList pileCards;
    ArrayList playerDownCards;
    ArrayList playerUpCards;
    ArrayList playerHandCards;
    ArrayList deckCards;
    double cardSizeRatio;
    int wins;
    int losses;
    int difficulty; // 0 = easy, 1 = hard

    // Global Interface Components
    JPanel opponentHandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
    JPanel opponentUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
    JPanel pilePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
    JPanel deckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
    JPanel emptyPanel = new JPanel(new CardLayout());
    JPanel emptyPanelBlnk = new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
    JPanel emptyPanelHide = new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));;
    JPanel playerUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
    JPanel playerHandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
    JLabel messagePanel1 = new JLabel(" ",JLabel.CENTER);
    JLabel messagePanel2 = new JLabel(" ",JLabel.CENTER);
    JLabel messagePanel3 = new JLabel(" ",JLabel.CENTER);
    JLabel messagePanel4 = new JLabel(" ",JLabel.CENTER);
    JLabel messagePanel5 = new JLabel(" ",JLabel.CENTER);
    JLabel cursorMessage = new JLabel(" ",JLabel.CENTER);
    JButton dealButton = new JButton("Deal!");
    JButton rulesButton = new JButton("Rules");
    JLabel winLossPanel = new JLabel("Wins: 0    Losses: 0 ");
    JRadioButtonMenuItem setDiffMenuItem_E = new JRadioButtonMenuItem("Easy");
    JRadioButtonMenuItem setDiffMenuItem_H = new JRadioButtonMenuItem("Hard");
    JRadioButtonMenuItem setColorMenuItem_G = new JRadioButtonMenuItem("Green");
    JRadioButtonMenuItem setColorMenuItem_B = new JRadioButtonMenuItem("Blue");
    JRadioButtonMenuItem setColorMenuItem_R = new JRadioButtonMenuItem("Red");

    // Global State Variables
    boolean playerTurn;
    boolean gameBegun;
    boolean killCardUsed;
    boolean playersFirstMove;
    int playerState;		// 0 = Begin turn
    // 1 = Card laid down
    // 2 = End turn / Opponents turn
    // 3 = End game

    // File names and current working directory
    String rulesFileName = "American Game Rules.pdf";
    String dataFileName = "agdata.txt";
    String curDir = System.getProperty("user.dir");

    // Files
    File dataFile = new File(curDir + "\\" + dataFileName);
    File rulesFile = new File(curDir + "\\" + rulesFileName);

    // Card Images
    ImageIcon topCardIcon = createImageIcon("images/top.jpg","");
    ImageIcon topRSCardIcon = createImageIcon("images/rightSide.jpg","");
    ImageIcon topRSCardIconSmall = createImageIcon("images/rightSideSmall.jpg","");
    ImageIcon cardOutlineIcon = createImageIcon("images/cardOutlineIcon.jpg","");
    ImageIcon imageC2  = createImageIcon("images/C2.jpg","");
    ImageIcon imageC3  = createImageIcon("images/C3.jpg","");
    ImageIcon imageC4  = createImageIcon("images/C4.jpg","");
    ImageIcon imageC5  = createImageIcon("images/C5.jpg","");
    ImageIcon imageC6  = createImageIcon("images/C6.jpg","");
    ImageIcon imageC7  = createImageIcon("images/C7.jpg","");
    ImageIcon imageC8  = createImageIcon("images/C8.jpg","");
    ImageIcon imageC9  = createImageIcon("images/C9.jpg","");
    ImageIcon imageC10 = createImageIcon("images/C10.jpg","");
    ImageIcon imageC11 = createImageIcon("images/C11.jpg","");
    ImageIcon imageC12 = createImageIcon("images/C12.jpg","");
    ImageIcon imageC13 = createImageIcon("images/C13.jpg","");
    ImageIcon imageC14 = createImageIcon("images/C14.jpg","");
    ImageIcon imageD2  = createImageIcon("images/D2.jpg","");
    ImageIcon imageD3  = createImageIcon("images/D3.jpg","");
    ImageIcon imageD4  = createImageIcon("images/D4.jpg","");
    ImageIcon imageD5  = createImageIcon("images/D5.jpg","");
    ImageIcon imageD6  = createImageIcon("images/D6.jpg","");
    ImageIcon imageD7  = createImageIcon("images/D7.jpg","");
    ImageIcon imageD8  = createImageIcon("images/D8.jpg","");
    ImageIcon imageD9  = createImageIcon("images/D9.jpg","");
    ImageIcon imageD10 = createImageIcon("images/D10.jpg","");
    ImageIcon imageD11 = createImageIcon("images/D11.jpg","");
    ImageIcon imageD12 = createImageIcon("images/D12.jpg","");
    ImageIcon imageD13 = createImageIcon("images/D13.jpg","");
    ImageIcon imageD14 = createImageIcon("images/D14.jpg","");
    ImageIcon imageH2  = createImageIcon("images/H2.jpg","");
    ImageIcon imageH3  = createImageIcon("images/H3.jpg","");
    ImageIcon imageH4  = createImageIcon("images/H4.jpg","");
    ImageIcon imageH5  = createImageIcon("images/H5.jpg","");
    ImageIcon imageH6  = createImageIcon("images/H6.jpg","");
    ImageIcon imageH7  = createImageIcon("images/H7.jpg","");
    ImageIcon imageH8  = createImageIcon("images/H8.jpg","");
    ImageIcon imageH9  = createImageIcon("images/H9.jpg","");
    ImageIcon imageH10 = createImageIcon("images/H10.jpg","");
    ImageIcon imageH11 = createImageIcon("images/H11.jpg","");
    ImageIcon imageH12 = createImageIcon("images/H12.jpg","");
    ImageIcon imageH13 = createImageIcon("images/H13.jpg","");
    ImageIcon imageH14 = createImageIcon("images/H14.jpg","");
    ImageIcon imageS2  = createImageIcon("images/S2.jpg","");
    ImageIcon imageS3  = createImageIcon("images/S3.jpg","");
    ImageIcon imageS4  = createImageIcon("images/S4.jpg","");
    ImageIcon imageS5  = createImageIcon("images/S5.jpg","");
    ImageIcon imageS6  = createImageIcon("images/S6.jpg","");
    ImageIcon imageS7  = createImageIcon("images/S7.jpg","");
    ImageIcon imageS8  = createImageIcon("images/S8.jpg","");
    ImageIcon imageS9  = createImageIcon("images/S9.jpg","");
    ImageIcon imageS10 = createImageIcon("images/S10.jpg","");
    ImageIcon imageS11 = createImageIcon("images/S11.jpg","");
    ImageIcon imageS12 = createImageIcon("images/S12.jpg","");
    ImageIcon imageS13 = createImageIcon("images/S13.jpg","");
    ImageIcon imageS14 = createImageIcon("images/S14.jpg","");
    ImageIcon imageC2_small  = createImageIcon("images/C2_small.jpg","");
    ImageIcon imageC3_small  = createImageIcon("images/C3_small.jpg","");
    ImageIcon imageC4_small  = createImageIcon("images/C4_small.jpg","");
    ImageIcon imageC5_small  = createImageIcon("images/C5_small.jpg","");
    ImageIcon imageC6_small  = createImageIcon("images/C6_small.jpg","");
    ImageIcon imageC7_small  = createImageIcon("images/C7_small.jpg","");
    ImageIcon imageC8_small  = createImageIcon("images/C8_small.jpg","");
    ImageIcon imageC9_small  = createImageIcon("images/C9_small.jpg","");
    ImageIcon imageC10_small = createImageIcon("images/C10_small.jpg","");
    ImageIcon imageC11_small = createImageIcon("images/C11_small.jpg","");
    ImageIcon imageC12_small = createImageIcon("images/C12_small.jpg","");
    ImageIcon imageC13_small = createImageIcon("images/C13_small.jpg","");
    ImageIcon imageC14_small = createImageIcon("images/C14_small.jpg","");
    ImageIcon imageD2_small  = createImageIcon("images/D2_small.jpg","");
    ImageIcon imageD3_small  = createImageIcon("images/D3_small.jpg","");
    ImageIcon imageD4_small  = createImageIcon("images/D4_small.jpg","");
    ImageIcon imageD5_small  = createImageIcon("images/D5_small.jpg","");
    ImageIcon imageD6_small  = createImageIcon("images/D6_small.jpg","");
    ImageIcon imageD7_small  = createImageIcon("images/D7_small.jpg","");
    ImageIcon imageD8_small  = createImageIcon("images/D8_small.jpg","");
    ImageIcon imageD9_small  = createImageIcon("images/D9_small.jpg","");
    ImageIcon imageD10_small = createImageIcon("images/D10_small.jpg","");
    ImageIcon imageD11_small = createImageIcon("images/D11_small.jpg","");
    ImageIcon imageD12_small = createImageIcon("images/D12_small.jpg","");
    ImageIcon imageD13_small = createImageIcon("images/D13_small.jpg","");
    ImageIcon imageD14_small = createImageIcon("images/D14_small.jpg","");
    ImageIcon imageH2_small  = createImageIcon("images/H2_small.jpg","");
    ImageIcon imageH3_small  = createImageIcon("images/H3_small.jpg","");
    ImageIcon imageH4_small  = createImageIcon("images/H4_small.jpg","");
    ImageIcon imageH5_small  = createImageIcon("images/H5_small.jpg","");
    ImageIcon imageH6_small  = createImageIcon("images/H6_small.jpg","");
    ImageIcon imageH7_small  = createImageIcon("images/H7_small.jpg","");
    ImageIcon imageH8_small  = createImageIcon("images/H8_small.jpg","");
    ImageIcon imageH9_small  = createImageIcon("images/H9_small.jpg","");
    ImageIcon imageH10_small = createImageIcon("images/H10_small.jpg","");
    ImageIcon imageH11_small = createImageIcon("images/H11_small.jpg","");
    ImageIcon imageH12_small = createImageIcon("images/H12_small.jpg","");
    ImageIcon imageH13_small = createImageIcon("images/H13_small.jpg","");
    ImageIcon imageH14_small = createImageIcon("images/H14_small.jpg","");
    ImageIcon imageS2_small  = createImageIcon("images/S2_small.jpg","");
    ImageIcon imageS3_small  = createImageIcon("images/S3_small.jpg","");
    ImageIcon imageS4_small  = createImageIcon("images/S4_small.jpg","");
    ImageIcon imageS5_small  = createImageIcon("images/S5_small.jpg","");
    ImageIcon imageS6_small  = createImageIcon("images/S6_small.jpg","");
    ImageIcon imageS7_small  = createImageIcon("images/S7_small.jpg","");
    ImageIcon imageS8_small  = createImageIcon("images/S8_small.jpg","");
    ImageIcon imageS9_small  = createImageIcon("images/S9_small.jpg","");
    ImageIcon imageS10_small = createImageIcon("images/S10_small.jpg","");
    ImageIcon imageS11_small = createImageIcon("images/S11_small.jpg","");
    ImageIcon imageS12_small = createImageIcon("images/S12_small.jpg","");
    ImageIcon imageS13_small = createImageIcon("images/S13_small.jpg","");
    ImageIcon imageS14_small = createImageIcon("images/S14_small.jpg","");

    // Create array of images
    ImageIcon [] cardImageArray;

    // Card Types
    // opponentHand
    Card opponentHand_C2  = new Card("C2",  "2 of Clubs",
            "opponentHandPanel",		2,  topCardIcon);
    Card opponentHand_C3  = new Card("C3",  "3 of Clubs",
            "opponentHandPanel",		3,  topCardIcon);
    Card opponentHand_C4  = new Card("C4",  "4 of Clubs",
            "opponentHandPanel",		4,  topCardIcon);
    Card opponentHand_C5  = new Card("C5",  "5 of Clubs",
            "opponentHandPanel",		5,  topCardIcon);
    Card opponentHand_C6  = new Card("C6",  "6 of Clubs",
            "opponentHandPanel",		6,  topCardIcon);
    Card opponentHand_C7  = new Card("C7",  "7 of Clubs",
            "opponentHandPanel",		7,  topCardIcon);
    Card opponentHand_C8  = new Card("C8",  "8 of Clubs",
            "opponentHandPanel",		8,  topCardIcon);
    Card opponentHand_C9  = new Card("C9",  "9 of Clubs",
            "opponentHandPanel",		9,  topCardIcon);
    Card opponentHand_C10 = new Card("C10", "10 of Clubs",
            "opponentHandPanel",		10, topCardIcon);
    Card opponentHand_C11 = new Card("C11", "Jack of Clubs",
            "opponentHandPanel",		11, topCardIcon);
    Card opponentHand_C12 = new Card("C12", "Queen of Clubs",
            "opponentHandPanel",		12, topCardIcon);
    Card opponentHand_C13 = new Card("C13", "King of Clubs",
            "opponentHandPanel",		13, topCardIcon);
    Card opponentHand_C14 = new Card("C14", "Ace of Clubs",
            "opponentHandPanel",		14, topCardIcon);
    Card opponentHand_D2  = new Card("D2",  "2 of Diamonds",
            "opponentHandPanel",		2,  topCardIcon);
    Card opponentHand_D3  = new Card("D3",  "3 of Diamonds",
            "opponentHandPanel",		3,  topCardIcon);
    Card opponentHand_D4  = new Card("D4",  "4 of Diamonds",
            "opponentHandPanel",		4,  topCardIcon);
    Card opponentHand_D5  = new Card("D5",  "5 of Diamonds",
            "opponentHandPanel",		5,  topCardIcon);
    Card opponentHand_D6  = new Card("D6",  "6 of Diamonds",
            "opponentHandPanel",		6,  topCardIcon);
    Card opponentHand_D7  = new Card("D7",  "7 of Diamonds",
            "opponentHandPanel",		7,  topCardIcon);
    Card opponentHand_D8  = new Card("D8",  "8 of Diamonds",
            "opponentHandPanel",		8,  topCardIcon);
    Card opponentHand_D9  = new Card("D9",  "9 of Diamonds",
            "opponentHandPanel",		9,  topCardIcon);
    Card opponentHand_D10 = new Card("D10", "10 of Diamonds",
            "opponentHandPanel",		10, topCardIcon);
    Card opponentHand_D11 = new Card("D11", "Jack of Diamonds",
            "opponentHandPanel",		11, topCardIcon);
    Card opponentHand_D12 = new Card("D12", "Queen of Diamonds",
            "opponentHandPanel",		12, topCardIcon);
    Card opponentHand_D13 = new Card("D13", "King of Diamonds",
            "opponentHandPanel",		13, topCardIcon);
    Card opponentHand_D14 = new Card("D14", "Ace of Diamonds",
            "opponentHandPanel",		14, topCardIcon);
    Card opponentHand_H2  = new Card("H2",  "2 of Hearts",
            "opponentHandPanel",		2,  topCardIcon);
    Card opponentHand_H3  = new Card("H3",  "3 of Hearts",
            "opponentHandPanel",		3,  topCardIcon);
    Card opponentHand_H4  = new Card("H4",  "4 of Hearts",
            "opponentHandPanel",		4,  topCardIcon);
    Card opponentHand_H5  = new Card("H5",  "5 of Hearts",
            "opponentHandPanel",		5,  topCardIcon);
    Card opponentHand_H6  = new Card("H6",  "6 of Hearts",
            "opponentHandPanel",		6,  topCardIcon);
    Card opponentHand_H7  = new Card("H7",  "7 of Hearts",
            "opponentHandPanel",		7,  topCardIcon);
    Card opponentHand_H8  = new Card("H8",  "8 of Hearts",
            "opponentHandPanel",		8,  topCardIcon);
    Card opponentHand_H9  = new Card("H9",  "9 of Hearts",
            "opponentHandPanel",		9,  topCardIcon);
    Card opponentHand_H10 = new Card("H10", "10 of Hearts",
            "opponentHandPanel",		10, topCardIcon);
    Card opponentHand_H11 = new Card("H11", "Jack of Hearts",
            "opponentHandPanel",		11, topCardIcon);
    Card opponentHand_H12 = new Card("H12", "Queen of Hearts",
            "opponentHandPanel",		12, topCardIcon);
    Card opponentHand_H13 = new Card("H13", "King of Hearts",
            "opponentHandPanel",		13, topCardIcon);
    Card opponentHand_H14 = new Card("H14", "Ace of Hearts",
            "opponentHandPanel",		14, topCardIcon);
    Card opponentHand_S2  = new Card("S2",  "2 of Spades",
            "opponentHandPanel",		2,  topCardIcon);
    Card opponentHand_S3  = new Card("S3",  "3 of Spades",
            "opponentHandPanel",		3,  topCardIcon);
    Card opponentHand_S4  = new Card("S4",  "4 of Spades",
            "opponentHandPanel",		4,  topCardIcon);
    Card opponentHand_S5  = new Card("S5",  "5 of Spades",
            "opponentHandPanel",		5,  topCardIcon);
    Card opponentHand_S6  = new Card("S6",  "6 of Spades",
            "opponentHandPanel",		6,  topCardIcon);
    Card opponentHand_S7  = new Card("S7",  "7 of Spades",
            "opponentHandPanel",		7,  topCardIcon);
    Card opponentHand_S8  = new Card("S8",  "8 of Spades",
            "opponentHandPanel",		8,  topCardIcon);
    Card opponentHand_S9  = new Card("S9",  "9 of Spades",
            "opponentHandPanel",		9,  topCardIcon);
    Card opponentHand_S10 = new Card("S10", "10 of Spades",
            "opponentHandPanel",		10, topCardIcon);
    Card opponentHand_S11 = new Card("S11", "Jack of Spades",
            "opponentHandPanel",		11, topCardIcon);
    Card opponentHand_S12 = new Card("S12", "Queen of Spades",
            "opponentHandPanel",		12, topCardIcon);
    Card opponentHand_S13 = new Card("S13", "King of Spades",
            "opponentHandPanel",		13, topCardIcon);
    Card opponentHand_S14 = new Card("S14", "Ace of Spades",
            "opponentHandPanel",		14, topCardIcon);
    Card opponentHand_C2_small  = new Card("C2",  "2 of Clubs",
            "opponentHandPanel",		2,  topRSCardIcon);
    Card opponentHand_C3_small  = new Card("C3",  "3 of Clubs",
            "opponentHandPanel",		3,  topRSCardIcon);
    Card opponentHand_C4_small  = new Card("C4",  "4 of Clubs",
            "opponentHandPanel",		4,  topRSCardIcon);
    Card opponentHand_C5_small  = new Card("C5",  "5 of Clubs",
            "opponentHandPanel",		5,  topRSCardIcon);
    Card opponentHand_C6_small  = new Card("C6",  "6 of Clubs",
            "opponentHandPanel",		6,  topRSCardIcon);
    Card opponentHand_C7_small  = new Card("C7",  "7 of Clubs",
            "opponentHandPanel",		7,  topRSCardIcon);
    Card opponentHand_C8_small  = new Card("C8",  "8 of Clubs",
            "opponentHandPanel",		8,  topRSCardIcon);
    Card opponentHand_C9_small  = new Card("C9",  "9 of Clubs",
            "opponentHandPanel",		9,  topRSCardIcon);
    Card opponentHand_C10_small = new Card("C10", "10 of Clubs",
            "opponentHandPanel",		10, topRSCardIcon);
    Card opponentHand_C11_small = new Card("C11", "Jack of Clubs",
            "opponentHandPanel",		11, topRSCardIcon);
    Card opponentHand_C12_small = new Card("C12", "Queen of Clubs",
            "opponentHandPanel",		12, topRSCardIcon);
    Card opponentHand_C13_small = new Card("C13", "King of Clubs",
            "opponentHandPanel",		13, topRSCardIcon);
    Card opponentHand_C14_small = new Card("C14", "Ace of Clubs",
            "opponentHandPanel",		14, topRSCardIcon);
    Card opponentHand_D2_small  = new Card("D2",  "2 of Diamonds",
            "opponentHandPanel",		2,  topRSCardIcon);
    Card opponentHand_D3_small  = new Card("D3",  "3 of Diamonds",
            "opponentHandPanel",		3,  topRSCardIcon);
    Card opponentHand_D4_small  = new Card("D4",  "4 of Diamonds",
            "opponentHandPanel",		4,  topRSCardIcon);
    Card opponentHand_D5_small  = new Card("D5",  "5 of Diamonds",
            "opponentHandPanel",		5,  topRSCardIcon);
    Card opponentHand_D6_small  = new Card("D6",  "6 of Diamonds",
            "opponentHandPanel",		6,  topRSCardIcon);
    Card opponentHand_D7_small  = new Card("D7",  "7 of Diamonds",
            "opponentHandPanel",		7,  topRSCardIcon);
    Card opponentHand_D8_small  = new Card("D8",  "8 of Diamonds",
            "opponentHandPanel",		8,  topRSCardIcon);
    Card opponentHand_D9_small  = new Card("D9",  "9 of Diamonds",
            "opponentHandPanel",		9,  topRSCardIcon);
    Card opponentHand_D10_small = new Card("D10", "10 of Diamonds",
            "opponentHandPanel",		10, topRSCardIcon);
    Card opponentHand_D11_small = new Card("D11", "Jack of Diamonds",
            "opponentHandPanel",		11, topRSCardIcon);
    Card opponentHand_D12_small = new Card("D12", "Queen of Diamonds",
            "opponentHandPanel",		12, topRSCardIcon);
    Card opponentHand_D13_small = new Card("D13", "King of Diamonds",
            "opponentHandPanel",		13, topRSCardIcon);
    Card opponentHand_D14_small = new Card("D14", "Ace of Diamonds",
            "opponentHandPanel",		14, topRSCardIcon);
    Card opponentHand_H2_small  = new Card("H2",  "2 of Hearts",
            "opponentHandPanel",		2,  topRSCardIcon);
    Card opponentHand_H3_small  = new Card("H3",  "3 of Hearts",
            "opponentHandPanel",		3,  topRSCardIcon);
    Card opponentHand_H4_small  = new Card("H4",  "4 of Hearts",
            "opponentHandPanel",		4,  topRSCardIcon);
    Card opponentHand_H5_small  = new Card("H5",  "5 of Hearts",
            "opponentHandPanel",		5,  topRSCardIcon);
    Card opponentHand_H6_small  = new Card("H6",  "6 of Hearts",
            "opponentHandPanel",		6,  topRSCardIcon);
    Card opponentHand_H7_small  = new Card("H7",  "7 of Hearts",
            "opponentHandPanel",		7,  topRSCardIcon);
    Card opponentHand_H8_small  = new Card("H8",  "8 of Hearts",
            "opponentHandPanel",		8,  topRSCardIcon);
    Card opponentHand_H9_small  = new Card("H9",  "9 of Hearts",
            "opponentHandPanel",		9,  topRSCardIcon);
    Card opponentHand_H10_small = new Card("H10", "10 of Hearts",
            "opponentHandPanel",		10, topRSCardIcon);
    Card opponentHand_H11_small = new Card("H11", "Jack of Hearts",
            "opponentHandPanel",		11, topRSCardIcon);
    Card opponentHand_H12_small = new Card("H12", "Queen of Hearts",
            "opponentHandPanel",		12, topRSCardIcon);
    Card opponentHand_H13_small = new Card("H13", "King of Hearts",
            "opponentHandPanel",		13, topRSCardIcon);
    Card opponentHand_H14_small = new Card("H14", "Ace of Hearts",
            "opponentHandPanel",		14, topRSCardIcon);
    Card opponentHand_S2_small  = new Card("S2",  "2 of Spades",
            "opponentHandPanel",		2,  topRSCardIcon);
    Card opponentHand_S3_small  = new Card("S3",  "3 of Spades",
            "opponentHandPanel",		3,  topRSCardIcon);
    Card opponentHand_S4_small  = new Card("S4",  "4 of Spades",
            "opponentHandPanel",		4,  topRSCardIcon);
    Card opponentHand_S5_small  = new Card("S5",  "5 of Spades",
            "opponentHandPanel",		5,  topRSCardIcon);
    Card opponentHand_S6_small  = new Card("S6",  "6 of Spades",
            "opponentHandPanel",		6,  topRSCardIcon);
    Card opponentHand_S7_small  = new Card("S7",  "7 of Spades",
            "opponentHandPanel",		7,  topRSCardIcon);
    Card opponentHand_S8_small  = new Card("S8",  "8 of Spades",
            "opponentHandPanel",		8,  topRSCardIcon);
    Card opponentHand_S9_small  = new Card("S9",  "9 of Spades",
            "opponentHandPanel",		9,  topRSCardIcon);
    Card opponentHand_S10_small = new Card("S10", "10 of Spades",
            "opponentHandPanel",		10, topRSCardIcon);
    Card opponentHand_S11_small = new Card("S11", "Jack of Spades",
            "opponentHandPanel",		11, topRSCardIcon);
    Card opponentHand_S12_small = new Card("S12", "Queen of Spades",
            "opponentHandPanel",		12, topRSCardIcon);
    Card opponentHand_S13_small = new Card("S13", "King of Spades",
            "opponentHandPanel",		13, topRSCardIcon);
    Card opponentHand_S14_small = new Card("S14", "Ace of Spades",
            "opponentHandPanel",		14, topRSCardIcon);
    Card opponentHand_top1 		 = new Card("TC",  "top card",
            "opponentHandPanel",		 0, topCardIcon);
    Card opponentHand_top2 		 = new Card("TC",  "top card",
            "opponentHandPanel",		 0, topCardIcon);
    Card opponentHand_top3 		 = new Card("TC",  "top card",
            "opponentHandPanel",		 0, topCardIcon);
    Card opponentHand_outline1  = new Card("CO",  "card outline",
            "opponentHandPanel",		 0, cardOutlineIcon);
    Card opponentHand_outline2  = new Card("CO",  "card outline",
            "opponentHandPanel",		 0, cardOutlineIcon);
    Card opponentHand_outline3  = new Card("CO",  "card outline",
            "opponentHandPanel",		 0, cardOutlineIcon);

    Card [] opponentHandArray = {
            opponentHand_C2,  opponentHand_C3,  opponentHand_C4,
            opponentHand_C5,  opponentHand_C6,  opponentHand_C7,
            opponentHand_C8,  opponentHand_C9,  opponentHand_C10,
            opponentHand_C11, opponentHand_C12, opponentHand_C13,
            opponentHand_C14, opponentHand_D2,  opponentHand_D3,
            opponentHand_D4,  opponentHand_D5,  opponentHand_D6,
            opponentHand_D7,  opponentHand_D8,  opponentHand_D9,
            opponentHand_D10, opponentHand_D11, opponentHand_D12,
            opponentHand_D13, opponentHand_D14, opponentHand_H2,
            opponentHand_H3,  opponentHand_H4,  opponentHand_H5,
            opponentHand_H6,  opponentHand_H7,  opponentHand_H8,
            opponentHand_H9,  opponentHand_H10, opponentHand_H11,
            opponentHand_H12, opponentHand_H13, opponentHand_H14,
            opponentHand_S2,  opponentHand_S3,  opponentHand_S4,
            opponentHand_S5,  opponentHand_S6,  opponentHand_S7,
            opponentHand_S8,  opponentHand_S9,  opponentHand_S10,
            opponentHand_S11,	opponentHand_S12, opponentHand_S13,
            opponentHand_S14,			opponentHand_C2_small,  opponentHand_C3_small,
            opponentHand_C4_small,  opponentHand_C5_small,  opponentHand_C6_small,
            opponentHand_C7_small,  opponentHand_C8_small,  opponentHand_C9_small,
            opponentHand_C10_small, opponentHand_C11_small, opponentHand_C12_small,
            opponentHand_C13_small, opponentHand_C14_small,	opponentHand_D2_small,
            opponentHand_D3_small,  opponentHand_D4_small,  opponentHand_D5_small,
            opponentHand_D6_small,  opponentHand_D7_small,  opponentHand_D8_small,
            opponentHand_D9_small,  opponentHand_D10_small, opponentHand_D11_small,
            opponentHand_D12_small, opponentHand_D13_small, opponentHand_D14_small,
            opponentHand_H2_small,  opponentHand_H3_small,  opponentHand_H4_small,
            opponentHand_H5_small,  opponentHand_H6_small,  opponentHand_H7_small,
            opponentHand_H8_small,  opponentHand_H9_small,  opponentHand_H10_small,
            opponentHand_H11_small, opponentHand_H12_small, opponentHand_H13_small,
            opponentHand_H14_small, opponentHand_S2_small,  opponentHand_S3_small,
            opponentHand_S4_small,  opponentHand_S5_small,  opponentHand_S6_small,
            opponentHand_S7_small,  opponentHand_S8_small,  opponentHand_S9_small,
            opponentHand_S10_small, opponentHand_S11_small, opponentHand_S12_small,
            opponentHand_S13_small, opponentHand_S14_small,
            opponentHand_top1,		opponentHand_top2,		opponentHand_top3,
            opponentHand_outline1,  opponentHand_outline2,  opponentHand_outline3 };

    // opponentUp
    Card opponentUp_C2  = new Card("C2",  "2 of Clubs",
            "opponentUpPanel",		2,  imageC2);
    Card opponentUp_C3  = new Card("C3",  "3 of Clubs",
            "opponentUpPanel",		3,  imageC3);
    Card opponentUp_C4  = new Card("C4",  "4 of Clubs",
            "opponentUpPanel",		4,  imageC4);
    Card opponentUp_C5  = new Card("C5",  "5 of Clubs",
            "opponentUpPanel",		5,  imageC5);
    Card opponentUp_C6  = new Card("C6",  "6 of Clubs",
            "opponentUpPanel",		6,  imageC6);
    Card opponentUp_C7  = new Card("C7",  "7 of Clubs",
            "opponentUpPanel",		7,  imageC7);
    Card opponentUp_C8  = new Card("C8",  "8 of Clubs",
            "opponentUpPanel",		8,  imageC8);
    Card opponentUp_C9  = new Card("C9",  "9 of Clubs",
            "opponentUpPanel",		9,  imageC9);
    Card opponentUp_C10 = new Card("C10", "10 of Clubs",
            "opponentUpPanel",		10, imageC10);
    Card opponentUp_C11 = new Card("C11", "Jack of Clubs",
            "opponentUpPanel",		11, imageC11);
    Card opponentUp_C12 = new Card("C12", "Queen of Clubs",
            "opponentUpPanel",		12, imageC12);
    Card opponentUp_C13 = new Card("C13", "King of Clubs",
            "opponentUpPanel",		13, imageC13);
    Card opponentUp_C14 = new Card("C14", "Ace of Clubs",
            "opponentUpPanel",		14, imageC14);
    Card opponentUp_D2  = new Card("D2",  "2 of Diamonds",
            "opponentUpPanel",		2,  imageD2);
    Card opponentUp_D3  = new Card("D3",  "3 of Diamonds",
            "opponentUpPanel",		3,  imageD3);
    Card opponentUp_D4  = new Card("D4",  "4 of Diamonds",
            "opponentUpPanel",		4,  imageD4);
    Card opponentUp_D5  = new Card("D5",  "5 of Diamonds",
            "opponentUpPanel",		5,  imageD5);
    Card opponentUp_D6  = new Card("D6",  "6 of Diamonds",
            "opponentUpPanel",		6,  imageD6);
    Card opponentUp_D7  = new Card("D7",  "7 of Diamonds",
            "opponentUpPanel",		7,  imageD7);
    Card opponentUp_D8  = new Card("D8",  "8 of Diamonds",
            "opponentUpPanel",		8,  imageD8);
    Card opponentUp_D9  = new Card("D9",  "9 of Diamonds",
            "opponentUpPanel",		9,  imageD9);
    Card opponentUp_D10 = new Card("D10", "10 of Diamonds",
            "opponentUpPanel",		10, imageD10);
    Card opponentUp_D11 = new Card("D11", "Jack of Diamonds",
            "opponentUpPanel",	11, imageD11);
    Card opponentUp_D12 = new Card("D12", "Queen of Diamonds",
            "opponentUpPanel",	12, imageD12);
    Card opponentUp_D13 = new Card("D13", "King of Diamonds",
            "opponentUpPanel",	13, imageD13);
    Card opponentUp_D14 = new Card("D14", "Ace of Diamonds",
            "opponentUpPanel",	14, imageD14);
    Card opponentUp_H2  = new Card("H2",  "2 of Hearts",
            "opponentUpPanel",		2,  imageH2);
    Card opponentUp_H3  = new Card("H3",  "3 of Hearts",
            "opponentUpPanel",		3,  imageH3);
    Card opponentUp_H4  = new Card("H4",  "4 of Hearts",
            "opponentUpPanel",		4,  imageH4);
    Card opponentUp_H5  = new Card("H5",  "5 of Hearts",
            "opponentUpPanel",		5,  imageH5);
    Card opponentUp_H6  = new Card("H6",  "6 of Hearts",
            "opponentUpPanel",		6,  imageH6);
    Card opponentUp_H7  = new Card("H7",  "7 of Hearts",
            "opponentUpPanel",		7,  imageH7);
    Card opponentUp_H8  = new Card("H8",  "8 of Hearts",
            "opponentUpPanel",		8,  imageH8);
    Card opponentUp_H9  = new Card("H9",  "9 of Hearts",
            "opponentUpPanel",		9,  imageH9);
    Card opponentUp_H10 = new Card("H10", "10 of Hearts",
            "opponentUpPanel",		10, imageH10);
    Card opponentUp_H11 = new Card("H11", "Jack of Hearts",
            "opponentUpPanel",		11, imageH11);
    Card opponentUp_H12 = new Card("H12", "Queen of Hearts",
            "opponentUpPanel",		12, imageH12);
    Card opponentUp_H13 = new Card("H13", "King of Hearts",
            "opponentUpPanel",		13, imageH13);
    Card opponentUp_H14 = new Card("H14", "Ace of Hearts",
            "opponentUpPanel",		14, imageH14);
    Card opponentUp_S2  = new Card("S2",  "2 of Spades",
            "opponentUpPanel",		2,  imageS2);
    Card opponentUp_S3  = new Card("S3",  "3 of Spades",
            "opponentUpPanel",		3,  imageS3);
    Card opponentUp_S4  = new Card("S4",  "4 of Spades",
            "opponentUpPanel",		4,  imageS4);
    Card opponentUp_S5  = new Card("S5",  "5 of Spades",
            "opponentUpPanel",		5,  imageS5);
    Card opponentUp_S6  = new Card("S6",  "6 of Spades",
            "opponentUpPanel",		6,  imageS6);
    Card opponentUp_S7  = new Card("S7",  "7 of Spades",
            "opponentUpPanel",		7,  imageS7);
    Card opponentUp_S8  = new Card("S8",  "8 of Spades",
            "opponentUpPanel",		8,  imageS8);
    Card opponentUp_S9  = new Card("S9",  "9 of Spades",
            "opponentUpPanel",		9,  imageS9);
    Card opponentUp_S10 = new Card("S10", "10 of Spades",
            "opponentUpPanel",		10, imageS10);
    Card opponentUp_S11 = new Card("S11", "Jack of Spades",
            "opponentUpPanel",		11, imageS11);
    Card opponentUp_S12 = new Card("S12", "Queen of Spades",
            "opponentUpPanel",		12, imageS12);
    Card opponentUp_S13 = new Card("S13", "King of Spades",
            "opponentUpPanel",		13, imageS13);
    Card opponentUp_S14 = new Card("S14", "Ace of Spades",
            "opponentUpPanel",		14, imageS14);
    Card opponentUp_C2_small  = new Card("C2",  "2 of Clubs",
            "opponentUpPanel",		2,  imageC2_small);
    Card opponentUp_C3_small  = new Card("C3",  "3 of Clubs",
            "opponentUpPanel",		3,  imageC3_small);
    Card opponentUp_C4_small  = new Card("C4",  "4 of Clubs",
            "opponentUpPanel",		4,  imageC4_small);
    Card opponentUp_C5_small  = new Card("C5",  "5 of Clubs",
            "opponentUpPanel",		5,  imageC5_small);
    Card opponentUp_C6_small  = new Card("C6",  "6 of Clubs",
            "opponentUpPanel",		6,  imageC6_small);
    Card opponentUp_C7_small  = new Card("C7",  "7 of Clubs",
            "opponentUpPanel",		7,  imageC7_small);
    Card opponentUp_C8_small  = new Card("C8",  "8 of Clubs",
            "opponentUpPanel",		8,  imageC8_small);
    Card opponentUp_C9_small  = new Card("C9",  "9 of Clubs",
            "opponentUpPanel",		9,  imageC9_small);
    Card opponentUp_C10_small = new Card("C10", "10 of Clubs",
            "opponentUpPanel",		10, imageC10_small);
    Card opponentUp_C11_small = new Card("C11", "Jack of Clubs",
            "opponentUpPanel",		11, imageC11_small);
    Card opponentUp_C12_small = new Card("C12", "Queen of Clubs",
            "opponentUpPanel",		12, imageC12_small);
    Card opponentUp_C13_small = new Card("C13", "King of Clubs",
            "opponentUpPanel",		13, imageC13_small);
    Card opponentUp_C14_small = new Card("C14", "Ace of Clubs",
            "opponentUpPanel",		14, imageC14_small);
    Card opponentUp_D2_small  = new Card("D2",  "2 of Diamonds",
            "opponentUpPanel",		2,  imageD2_small);
    Card opponentUp_D3_small  = new Card("D3",  "3 of Diamonds",
            "opponentUpPanel",		3,  imageD3_small);
    Card opponentUp_D4_small  = new Card("D4",  "4 of Diamonds",
            "opponentUpPanel",		4,  imageD4_small);
    Card opponentUp_D5_small  = new Card("D5",  "5 of Diamonds",
            "opponentUpPanel",		5,  imageD5_small);
    Card opponentUp_D6_small  = new Card("D6",  "6 of Diamonds",
            "opponentUpPanel",		6,  imageD6_small);
    Card opponentUp_D7_small  = new Card("D7",  "7 of Diamonds",
            "opponentUpPanel",		7,  imageD7_small);
    Card opponentUp_D8_small  = new Card("D8",  "8 of Diamonds",
            "opponentUpPanel",		8,  imageD8_small);
    Card opponentUp_D9_small  = new Card("D9",  "9 of Diamonds",
            "opponentUpPanel",		9,  imageD9_small);
    Card opponentUp_D10_small = new Card("D10", "10 of Diamonds",
            "opponentUpPanel",		10, imageD10_small);
    Card opponentUp_D11_small = new Card("D11", "Jack of Diamonds",
            "opponentUpPanel",	11, imageD11_small);
    Card opponentUp_D12_small = new Card("D12", "Queen of Diamonds",
            "opponentUpPanel",	12, imageD12_small);
    Card opponentUp_D13_small = new Card("D13", "King of Diamonds",
            "opponentUpPanel",	13, imageD13_small);
    Card opponentUp_D14_small = new Card("D14", "Ace of Diamonds",
            "opponentUpPanel",	14, imageD14_small);
    Card opponentUp_H2_small  = new Card("H2",  "2 of Hearts",
            "opponentUpPanel",		2,  imageH2_small);
    Card opponentUp_H3_small  = new Card("H3",  "3 of Hearts",
            "opponentUpPanel",		3,  imageH3_small);
    Card opponentUp_H4_small  = new Card("H4",  "4 of Hearts",
            "opponentUpPanel",		4,  imageH4_small);
    Card opponentUp_H5_small  = new Card("H5",  "5 of Hearts",
            "opponentUpPanel",		5,  imageH5_small);
    Card opponentUp_H6_small  = new Card("H6",  "6 of Hearts",
            "opponentUpPanel",		6,  imageH6_small);
    Card opponentUp_H7_small  = new Card("H7",  "7 of Hearts",
            "opponentUpPanel",		7,  imageH7_small);
    Card opponentUp_H8_small  = new Card("H8",  "8 of Hearts",
            "opponentUpPanel",		8,  imageH8_small);
    Card opponentUp_H9_small  = new Card("H9",  "9 of Hearts",
            "opponentUpPanel",		9,  imageH9_small);
    Card opponentUp_H10_small = new Card("H10", "10 of Hearts",
            "opponentUpPanel",		10, imageH10_small);
    Card opponentUp_H11_small = new Card("H11", "Jack of Hearts",
            "opponentUpPanel",		11, imageH11_small);
    Card opponentUp_H12_small = new Card("H12", "Queen of Hearts",
            "opponentUpPanel",		12, imageH12_small);
    Card opponentUp_H13_small = new Card("H13", "King of Hearts",
            "opponentUpPanel",		13, imageH13_small);
    Card opponentUp_H14_small = new Card("H14", "Ace of Hearts",
            "opponentUpPanel",		14, imageH14_small);
    Card opponentUp_S2_small  = new Card("S2",  "2 of Spades",
            "opponentUpPanel",		2,  imageS2_small);
    Card opponentUp_S3_small  = new Card("S3",  "3 of Spades",
            "opponentUpPanel",		3,  imageS3_small);
    Card opponentUp_S4_small  = new Card("S4",  "4 of Spades",
            "opponentUpPanel",		4,  imageS4_small);
    Card opponentUp_S5_small  = new Card("S5",  "5 of Spades",
            "opponentUpPanel",		5,  imageS5_small);
    Card opponentUp_S6_small  = new Card("S6",  "6 of Spades",
            "opponentUpPanel",		6,  imageS6_small);
    Card opponentUp_S7_small  = new Card("S7",  "7 of Spades",
            "opponentUpPanel",		7,  imageS7_small);
    Card opponentUp_S8_small  = new Card("S8",  "8 of Spades",
            "opponentUpPanel",		8,  imageS8_small);
    Card opponentUp_S9_small  = new Card("S9",  "9 of Spades",
            "opponentUpPanel",		9,  imageS9_small);
    Card opponentUp_S10_small = new Card("S10", "10 of Spades",
            "opponentUpPanel",		10, imageS10_small);
    Card opponentUp_S11_small = new Card("S11", "Jack of Spades",
            "opponentUpPanel",		11, imageS11_small);
    Card opponentUp_S12_small = new Card("S12", "Queen of Spades",
            "opponentUpPanel",		12, imageS12_small);
    Card opponentUp_S13_small = new Card("S13", "King of Spades",
            "opponentUpPanel",		13, imageS13_small);
    Card opponentUp_S14_small = new Card("S14", "Ace of Spades",
            "opponentUpPanel",		14, imageS14_small);
    Card opponentUp_top1		  = new Card("TC",  "top card",
            "opponentUpPanel",		 0, topCardIcon);
    Card opponentUp_top2 	  = new Card("TC",  "top card",
            "opponentUpPanel",		 0, topCardIcon);
    Card opponentUp_top3		  = new Card("TC",  "top card",
            "opponentUpPanel",		 0, topCardIcon);
    Card opponentUp_outline1  = new Card("CO",  "card outline",
            "opponentUpPanel",		 0, cardOutlineIcon);
    Card opponentUp_outline2  = new Card("CO",  "card outline",
            "opponentUpPanel",		 0, cardOutlineIcon);
    Card opponentUp_outline3  = new Card("CO",  "card outline",
            "opponentUpPanel",		 0, cardOutlineIcon);

    Card [] opponentUpArray = {
            opponentUp_C2,  opponentUp_C3,  opponentUp_C4,
            opponentUp_C5,  opponentUp_C6,  opponentUp_C7,
            opponentUp_C8,  opponentUp_C9,  opponentUp_C10,
            opponentUp_C11, opponentUp_C12, opponentUp_C13,
            opponentUp_C14, opponentUp_D2,  opponentUp_D3,
            opponentUp_D4,  opponentUp_D5,  opponentUp_D6,
            opponentUp_D7,  opponentUp_D8,  opponentUp_D9,
            opponentUp_D10, opponentUp_D11, opponentUp_D12,
            opponentUp_D13, opponentUp_D14, opponentUp_H2,
            opponentUp_H3,  opponentUp_H4,  opponentUp_H5,
            opponentUp_H6,  opponentUp_H7,  opponentUp_H8,
            opponentUp_H9,  opponentUp_H10, opponentUp_H11,
            opponentUp_H12, opponentUp_H13, opponentUp_H14,
            opponentUp_S2,  opponentUp_S3,  opponentUp_S4,
            opponentUp_S5,  opponentUp_S6,  opponentUp_S7,
            opponentUp_S8,  opponentUp_S9,  opponentUp_S10,
            opponentUp_S11, opponentUp_S12, opponentUp_S13,
            opponentUp_S14,		  opponentUp_C2_small,  opponentUp_C3_small,
            opponentUp_C4_small,  opponentUp_C5_small,  opponentUp_C6_small,
            opponentUp_C7_small,  opponentUp_C8_small,  opponentUp_C9_small,
            opponentUp_C10_small, opponentUp_C11_small, opponentUp_C12_small,
            opponentUp_C13_small, opponentUp_C14_small, opponentUp_D2_small,
            opponentUp_D3_small,  opponentUp_D4_small,  opponentUp_D5_small,
            opponentUp_D6_small,  opponentUp_D7_small,  opponentUp_D8_small,
            opponentUp_D9_small,  opponentUp_D10_small, opponentUp_D11_small,
            opponentUp_D12_small, opponentUp_D13_small, opponentUp_D14_small,
            opponentUp_H2_small,  opponentUp_H3_small,  opponentUp_H4_small,
            opponentUp_H5_small,  opponentUp_H6_small,  opponentUp_H7_small,
            opponentUp_H8_small,  opponentUp_H9_small,  opponentUp_H10_small,
            opponentUp_H11_small, opponentUp_H12_small, opponentUp_H13_small,
            opponentUp_H14_small, opponentUp_S2_small,  opponentUp_S3_small,
            opponentUp_S4_small,  opponentUp_S5_small,  opponentUp_S6_small,
            opponentUp_S7_small,  opponentUp_S8_small,  opponentUp_S9_small,
            opponentUp_S10_small, opponentUp_S11_small, opponentUp_S12_small,
            opponentUp_S13_small, opponentUp_S14_small,
            opponentUp_top1,      opponentUp_top2,      opponentUp_top3,
            opponentUp_outline1,  opponentUp_outline2,  opponentUp_outline3 };

    // pile
    Card pile_C2  = new Card("C2",  "2 of Clubs",
            "pilePanel",		2,  imageC2);
    Card pile_C3  = new Card("C3",  "3 of Clubs",
            "pilePanel",		3,  imageC3);
    Card pile_C4  = new Card("C4",  "4 of Clubs",
            "pilePanel",		4,  imageC4);
    Card pile_C5  = new Card("C5",  "5 of Clubs",
            "pilePanel",		5,  imageC5);
    Card pile_C6  = new Card("C6",  "6 of Clubs",
            "pilePanel",		6,  imageC6);
    Card pile_C7  = new Card("C7",  "7 of Clubs",
            "pilePanel",		7,  imageC7);
    Card pile_C8  = new Card("C8",  "8 of Clubs",
            "pilePanel",		8,  imageC8);
    Card pile_C9  = new Card("C9",  "9 of Clubs",
            "pilePanel",		9,  imageC9);
    Card pile_C10 = new Card("C10", "10 of Clubs",
            "pilePanel",		10, imageC10);
    Card pile_C11 = new Card("C11", "Jack of Clubs",
            "pilePanel",		11, imageC11);
    Card pile_C12 = new Card("C12", "Queen of Clubs",
            "pilePanel",		12, imageC12);
    Card pile_C13 = new Card("C13", "King of Clubs",
            "pilePanel",		13, imageC13);
    Card pile_C14 = new Card("C14", "Ace of Clubs",
            "pilePanel",		14, imageC14);
    Card pile_D2  = new Card("D2",  "2 of Diamonds",
            "pilePanel",		2,  imageD2);
    Card pile_D3  = new Card("D3",  "3 of Diamonds",
            "pilePanel",		3,  imageD3);
    Card pile_D4  = new Card("D4",  "4 of Diamonds",
            "pilePanel",		4,  imageD4);
    Card pile_D5  = new Card("D5",  "5 of Diamonds",
            "pilePanel",		5,  imageD5);
    Card pile_D6  = new Card("D6",  "6 of Diamonds",
            "pilePanel",		6,  imageD6);
    Card pile_D7  = new Card("D7",  "7 of Diamonds",
            "pilePanel",		7,  imageD7);
    Card pile_D8  = new Card("D8",  "8 of Diamonds",
            "pilePanel",		8,  imageD8);
    Card pile_D9  = new Card("D9",  "9 of Diamonds",
            "pilePanel",		9,  imageD9);
    Card pile_D10 = new Card("D10", "10 of Diamonds",
            "pilePanel",		10, imageD10);
    Card pile_D11 = new Card("D11", "Jack of Diamonds",
            "pilePanel",	11, imageD11);
    Card pile_D12 = new Card("D12", "Queen of Diamonds",
            "pilePanel",	12, imageD12);
    Card pile_D13 = new Card("D13", "King of Diamonds",
            "pilePanel",	13, imageD13);
    Card pile_D14 = new Card("D14", "Ace of Diamonds",
            "pilePanel",	14, imageD14);
    Card pile_H2  = new Card("H2",  "2 of Hearts",
            "pilePanel",		2,  imageH2);
    Card pile_H3  = new Card("H3",  "3 of Hearts",
            "pilePanel",		3,  imageH3);
    Card pile_H4  = new Card("H4",  "4 of Hearts",
            "pilePanel",		4,  imageH4);
    Card pile_H5  = new Card("H5",  "5 of Hearts",
            "pilePanel",		5,  imageH5);
    Card pile_H6  = new Card("H6",  "6 of Hearts",
            "pilePanel",		6,  imageH6);
    Card pile_H7  = new Card("H7",  "7 of Hearts",
            "pilePanel",		7,  imageH7);
    Card pile_H8  = new Card("H8",  "8 of Hearts",
            "pilePanel",		8,  imageH8);
    Card pile_H9  = new Card("H9",  "9 of Hearts",
            "pilePanel",		9,  imageH9);
    Card pile_H10 = new Card("H10", "10 of Hearts",
            "pilePanel",		10, imageH10);
    Card pile_H11 = new Card("H11", "Jack of Hearts",
            "pilePanel",		11, imageH11);
    Card pile_H12 = new Card("H12", "Queen of Hearts",
            "pilePanel",		12, imageH12);
    Card pile_H13 = new Card("H13", "King of Hearts",
            "pilePanel",		13, imageH13);
    Card pile_H14 = new Card("H14", "Ace of Hearts",
            "pilePanel",		14, imageH14);
    Card pile_S2  = new Card("S2",  "2 of Spades",
            "pilePanel",		2,  imageS2);
    Card pile_S3  = new Card("S3",  "3 of Spades",
            "pilePanel",		3,  imageS3);
    Card pile_S4  = new Card("S4",  "4 of Spades",
            "pilePanel",		4,  imageS4);
    Card pile_S5  = new Card("S5",  "5 of Spades",
            "pilePanel",		5,  imageS5);
    Card pile_S6  = new Card("S6",  "6 of Spades",
            "pilePanel",		6,  imageS6);
    Card pile_S7  = new Card("S7",  "7 of Spades",
            "pilePanel",		7,  imageS7);
    Card pile_S8  = new Card("S8",  "8 of Spades",
            "pilePanel",		8,  imageS8);
    Card pile_S9  = new Card("S9",  "9 of Spades",
            "pilePanel",		9,  imageS9);
    Card pile_S10 = new Card("S10", "10 of Spades",
            "pilePanel",		10, imageS10);
    Card pile_S11 = new Card("S11", "Jack of Spades",
            "pilePanel",		11, imageS11);
    Card pile_S12 = new Card("S12", "Queen of Spades",
            "pilePanel",		12, imageS12);
    Card pile_S13 = new Card("S13", "King of Spades",
            "pilePanel",		13, imageS13);
    Card pile_S14 = new Card("S14", "Ace of Spades",
            "pilePanel",		14, imageS14);
    Card pile_C2_small  = new Card("C2",  "2 of Clubs",
            "pilePanel",		2,  imageC2_small);
    Card pile_C3_small  = new Card("C3",  "3 of Clubs",
            "pilePanel",		3,  imageC3_small);
    Card pile_C4_small  = new Card("C4",  "4 of Clubs",
            "pilePanel",		4,  imageC4_small);
    Card pile_C5_small  = new Card("C5",  "5 of Clubs",
            "pilePanel",		5,  imageC5_small);
    Card pile_C6_small  = new Card("C6",  "6 of Clubs",
            "pilePanel",		6,  imageC6_small);
    Card pile_C7_small  = new Card("C7",  "7 of Clubs",
            "pilePanel",		7,  imageC7_small);
    Card pile_C8_small  = new Card("C8",  "8 of Clubs",
            "pilePanel",		8,  imageC8_small);
    Card pile_C9_small  = new Card("C9",  "9 of Clubs",
            "pilePanel",		9,  imageC9_small);
    Card pile_C10_small = new Card("C10", "10 of Clubs",
            "pilePanel",		10, imageC10_small);
    Card pile_C11_small = new Card("C11", "Jack of Clubs",
            "pilePanel",		11, imageC11_small);
    Card pile_C12_small = new Card("C12", "Queen of Clubs",
            "pilePanel",		12, imageC12_small);
    Card pile_C13_small = new Card("C13", "King of Clubs",
            "pilePanel",		13, imageC13_small);
    Card pile_C14_small = new Card("C14", "Ace of Clubs",
            "pilePanel",		14, imageC14_small);
    Card pile_D2_small  = new Card("D2",  "2 of Diamonds",
            "pilePanel",		2,  imageD2_small);
    Card pile_D3_small  = new Card("D3",  "3 of Diamonds",
            "pilePanel",		3,  imageD3_small);
    Card pile_D4_small  = new Card("D4",  "4 of Diamonds",
            "pilePanel",		4,  imageD4_small);
    Card pile_D5_small  = new Card("D5",  "5 of Diamonds",
            "pilePanel",		5,  imageD5_small);
    Card pile_D6_small  = new Card("D6",  "6 of Diamonds",
            "pilePanel",		6,  imageD6_small);
    Card pile_D7_small  = new Card("D7",  "7 of Diamonds",
            "pilePanel",		7,  imageD7_small);
    Card pile_D8_small  = new Card("D8",  "8 of Diamonds",
            "pilePanel",		8,  imageD8_small);
    Card pile_D9_small  = new Card("D9",  "9 of Diamonds",
            "pilePanel",		9,  imageD9_small);
    Card pile_D10_small = new Card("D10", "10 of Diamonds",
            "pilePanel",		10, imageD10_small);
    Card pile_D11_small = new Card("D11", "Jack of Diamonds",
            "pilePanel",	11, imageD11_small);
    Card pile_D12_small = new Card("D12", "Queen of Diamonds",
            "pilePanel",	12, imageD12_small);
    Card pile_D13_small = new Card("D13", "King of Diamonds",
            "pilePanel",	13, imageD13_small);
    Card pile_D14_small = new Card("D14", "Ace of Diamonds",
            "pilePanel",	14, imageD14_small);
    Card pile_H2_small  = new Card("H2",  "2 of Hearts",
            "pilePanel",		2,  imageH2_small);
    Card pile_H3_small  = new Card("H3",  "3 of Hearts",
            "pilePanel",		3,  imageH3_small);
    Card pile_H4_small  = new Card("H4",  "4 of Hearts",
            "pilePanel",		4,  imageH4_small);
    Card pile_H5_small  = new Card("H5",  "5 of Hearts",
            "pilePanel",		5,  imageH5_small);
    Card pile_H6_small  = new Card("H6",  "6 of Hearts",
            "pilePanel",		6,  imageH6_small);
    Card pile_H7_small  = new Card("H7",  "7 of Hearts",
            "pilePanel",		7,  imageH7_small);
    Card pile_H8_small  = new Card("H8",  "8 of Hearts",
            "pilePanel",		8,  imageH8_small);
    Card pile_H9_small  = new Card("H9",  "9 of Hearts",
            "pilePanel",		9,  imageH9_small);
    Card pile_H10_small = new Card("H10", "10 of Hearts",
            "pilePanel",		10, imageH10_small);
    Card pile_H11_small = new Card("H11", "Jack of Hearts",
            "pilePanel",		11, imageH11_small);
    Card pile_H12_small = new Card("H12", "Queen of Hearts",
            "pilePanel",		12, imageH12_small);
    Card pile_H13_small = new Card("H13", "King of Hearts",
            "pilePanel",		13, imageH13_small);
    Card pile_H14_small = new Card("H14", "Ace of Hearts",
            "pilePanel",		14, imageH14_small);
    Card pile_S2_small  = new Card("S2",  "2 of Spades",
            "pilePanel",		2,  imageS2_small);
    Card pile_S3_small  = new Card("S3",  "3 of Spades",
            "pilePanel",		3,  imageS3_small);
    Card pile_S4_small  = new Card("S4",  "4 of Spades",
            "pilePanel",		4,  imageS4_small);
    Card pile_S5_small  = new Card("S5",  "5 of Spades",
            "pilePanel",		5,  imageS5_small);
    Card pile_S6_small  = new Card("S6",  "6 of Spades",
            "pilePanel",		6,  imageS6_small);
    Card pile_S7_small  = new Card("S7",  "7 of Spades",
            "pilePanel",		7,  imageS7_small);
    Card pile_S8_small  = new Card("S8",  "8 of Spades",
            "pilePanel",		8,  imageS8_small);
    Card pile_S9_small  = new Card("S9",  "9 of Spades",
            "pilePanel",		9,  imageS9_small);
    Card pile_S10_small = new Card("S10", "10 of Spades",
            "pilePanel",		10, imageS10_small);
    Card pile_S11_small = new Card("S11", "Jack of Spades",
            "pilePanel",		11, imageS11_small);
    Card pile_S12_small = new Card("S12", "Queen of Spades",
            "pilePanel",		12, imageS12_small);
    Card pile_S13_small = new Card("S13", "King of Spades",
            "pilePanel",		13, imageS13_small);
    Card pile_S14_small = new Card("S14", "Ace of Spades",
            "pilePanel",		14, imageS14_small);
    Card pile_top1		  = new Card("TC",  "top card",
            "pilePanel", 	 0, topCardIcon);
    Card pile_top2		  = new Card("TC",  "top card",
            "pilePanel", 	 0, topCardIcon);
    Card pile_top3		  = new Card("TC",  "top card",
            "pilePanel", 	 0, topCardIcon);
    Card pile_outline1  = new Card("CO",  "card outline",
            "pilePanel", 	 0, cardOutlineIcon);
    Card pile_outline2  = new Card("CO",  "card outline",
            "pilePanel", 	 0, cardOutlineIcon);
    Card pile_outline3  = new Card("CO",  "card outline",
            "pilePanel", 	 0, cardOutlineIcon);

    Card [] pileArray = {
            pile_C2, pile_C3,  pile_C4,  pile_C5,  pile_C6,  pile_C7,  pile_C8,
            pile_C9, pile_C10, pile_C11, pile_C12, pile_C13, pile_C14,
            pile_D2, pile_D3,  pile_D4,  pile_D5,  pile_D6,  pile_D7,  pile_D8,
            pile_D9, pile_D10, pile_D11, pile_D12, pile_D13, pile_D14,
            pile_H2, pile_H3,  pile_H4,  pile_H5,  pile_H6,  pile_H7,  pile_H8,
            pile_H9, pile_H10, pile_H11, pile_H12, pile_H13, pile_H14,
            pile_S2, pile_S3,  pile_S4,  pile_S5,  pile_S6,  pile_S7,  pile_S8,
            pile_S9, pile_S10, pile_S11, pile_S12, pile_S13, pile_S14,
            pile_C2_small,  pile_C3_small,  pile_C4_small,  pile_C5_small,
            pile_C6_small,  pile_C7_small,  pile_C8_small,  pile_C9_small,
            pile_C10_small, pile_C11_small, pile_C12_small, pile_C13_small,
            pile_C14_small, pile_D2_small,  pile_D3_small,  pile_D4_small,
            pile_D5_small,  pile_D6_small,  pile_D7_small,  pile_D8_small,
            pile_D9_small,  pile_D10_small, pile_D11_small, pile_D12_small,
            pile_D13_small, pile_D14_small, pile_H2_small,  pile_H3_small,
            pile_H4_small,  pile_H5_small,  pile_H6_small,  pile_H7_small,
            pile_H8_small,  pile_H9_small,  pile_H10_small, pile_H11_small,
            pile_H12_small, pile_H13_small, pile_H14_small,	pile_S2_small,
            pile_S3_small,  pile_S4_small,  pile_S5_small,	pile_S6_small,
            pile_S7_small,  pile_S8_small,  pile_S9_small,	pile_S10_small,
            pile_S11_small, pile_S12_small, pile_S13_small, pile_S14_small,
            pile_top1,      pile_top2,      pile_top3,
            pile_outline1,  pile_outline2,  pile_outline3 };

    // deck
    Card deck_top = new Card("TC", "top card",
            "deckPanel", 0, topCardIcon);
    Card deck_outline = new Card("CO", "card outline",
            "deckPanel", 0, cardOutlineIcon);

    // playerUp
    Card playerUp_C2  = new Card("C2",  "2 of Clubs",
            "playerUpPanel",		2,	 imageC2);
    Card playerUp_C3  = new Card("C3",  "3 of Clubs",
            "playerUpPanel",		3,  imageC3);
    Card playerUp_C4  = new Card("C4",  "4 of Clubs",
            "playerUpPanel",		4,  imageC4);
    Card playerUp_C5  = new Card("C5",  "5 of Clubs",
            "playerUpPanel",		5,  imageC5);
    Card playerUp_C6  = new Card("C6",  "6 of Clubs",
            "playerUpPanel",		6,  imageC6);
    Card playerUp_C7  = new Card("C7",  "7 of Clubs",
            "playerUpPanel",		7,  imageC7);
    Card playerUp_C8  = new Card("C8",  "8 of Clubs",
            "playerUpPanel",		8,  imageC8);
    Card playerUp_C9  = new Card("C9",  "9 of Clubs",
            "playerUpPanel",		9,  imageC9);
    Card playerUp_C10 = new Card("C10", "10 of Clubs",
            "playerUpPanel",		10, imageC10);
    Card playerUp_C11 = new Card("C11", "Jack of Clubs",
            "playerUpPanel",		11, imageC11);
    Card playerUp_C12 = new Card("C12", "Queen of Clubs",
            "playerUpPanel",		12, imageC12);
    Card playerUp_C13 = new Card("C13", "King of Clubs",
            "playerUpPanel",		13, imageC13);
    Card playerUp_C14 = new Card("C14", "Ace of Clubs",
            "playerUpPanel",		14, imageC14);
    Card playerUp_D2  = new Card("D2",  "2 of Diamonds",
            "playerUpPanel",		2,  imageD2);
    Card playerUp_D3  = new Card("D3",  "3 of Diamonds",
            "playerUpPanel",		3,  imageD3);
    Card playerUp_D4  = new Card("D4",  "4 of Diamonds",
            "playerUpPanel",		4,  imageD4);
    Card playerUp_D5  = new Card("D5",  "5 of Diamonds",
            "playerUpPanel",		5,  imageD5);
    Card playerUp_D6  = new Card("D6",  "6 of Diamonds",
            "playerUpPanel",		6,  imageD6);
    Card playerUp_D7  = new Card("D7",  "7 of Diamonds",
            "playerUpPanel",		7,  imageD7);
    Card playerUp_D8  = new Card("D8",  "8 of Diamonds",
            "playerUpPanel",		8,  imageD8);
    Card playerUp_D9  = new Card("D9",  "9 of Diamonds",
            "playerUpPanel",		9,  imageD9);
    Card playerUp_D10 = new Card("D10", "10 of Diamonds",
            "playerUpPanel",		10, imageD10);
    Card playerUp_D11 = new Card("D11", "Jack of Diamonds",
            "playerUpPanel",	11, imageD11);
    Card playerUp_D12 = new Card("D12", "Queen of Diamonds",
            "playerUpPanel",	12, imageD12);
    Card playerUp_D13 = new Card("D13", "King of Diamonds",
            "playerUpPanel",	13, imageD13);
    Card playerUp_D14 = new Card("D14", "Ace of Diamonds",
            "playerUpPanel",	14, imageD14);
    Card playerUp_H2  = new Card("H2",  "2 of Hearts",
            "playerUpPanel",		2,  imageH2);
    Card playerUp_H3  = new Card("H3",  "3 of Hearts",
            "playerUpPanel",		3,  imageH3);
    Card playerUp_H4  = new Card("H4",  "4 of Hearts",
            "playerUpPanel",		4,  imageH4);
    Card playerUp_H5  = new Card("H5",  "5 of Hearts",
            "playerUpPanel",		5,  imageH5);
    Card playerUp_H6  = new Card("H6",  "6 of Hearts",
            "playerUpPanel",		6,  imageH6);
    Card playerUp_H7  = new Card("H7",  "7 of Hearts",
            "playerUpPanel",		7,  imageH7);
    Card playerUp_H8  = new Card("H8",  "8 of Hearts",
            "playerUpPanel",		8,  imageH8);
    Card playerUp_H9  = new Card("H9",  "9 of Hearts",
            "playerUpPanel",		9,  imageH9);
    Card playerUp_H10 = new Card("H10", "10 of Hearts",
            "playerUpPanel",		10, imageH10);
    Card playerUp_H11 = new Card("H11", "Jack of Hearts",
            "playerUpPanel",		11, imageH11);
    Card playerUp_H12 = new Card("H12", "Queen of Hearts",
            "playerUpPanel",		12, imageH12);
    Card playerUp_H13 = new Card("H13", "King of Hearts",
            "playerUpPanel",		13, imageH13);
    Card playerUp_H14 = new Card("H14", "Ace of Hearts",
            "playerUpPanel",		14, imageH14);
    Card playerUp_S2  = new Card("S2",  "2 of Spades",
            "playerUpPanel",		2,  imageS2);
    Card playerUp_S3  = new Card("S3",  "3 of Spades",
            "playerUpPanel",		3,  imageS3);
    Card playerUp_S4  = new Card("S4",  "4 of Spades",
            "playerUpPanel",		4,  imageS4);
    Card playerUp_S5  = new Card("S5",  "5 of Spades",
            "playerUpPanel",		5,  imageS5);
    Card playerUp_S6  = new Card("S6",  "6 of Spades",
            "playerUpPanel",		6,  imageS6);
    Card playerUp_S7  = new Card("S7",  "7 of Spades",
            "playerUpPanel",		7,  imageS7);
    Card playerUp_S8  = new Card("S8",  "8 of Spades",
            "playerUpPanel",		8,  imageS8);
    Card playerUp_S9  = new Card("S9",  "9 of Spades",
            "playerUpPanel",		9,  imageS9);
    Card playerUp_S10 = new Card("S10", "10 of Spades",
            "playerUpPanel",		10, imageS10);
    Card playerUp_S11 = new Card("S11", "Jack of Spades",
            "playerUpPanel",		11, imageS11);
    Card playerUp_S12 = new Card("S12", "Queen of Spades",
            "playerUpPanel",		12, imageS12);
    Card playerUp_S13 = new Card("S13", "King of Spades",
            "playerUpPanel",		13, imageS13);
    Card playerUp_S14 = new Card("S14", "Ace of Spades",
            "playerUpPanel",		14, imageS14);
    Card playerUp_C2_small  = new Card("C2",  "2 of Clubs",
            "playerUpPanel",		2,  imageC2_small);
    Card playerUp_C3_small  = new Card("C3",  "3 of Clubs",
            "playerUpPanel",		3,  imageC3_small);
    Card playerUp_C4_small  = new Card("C4",  "4 of Clubs",
            "playerUpPanel",		4,  imageC4_small);
    Card playerUp_C5_small  = new Card("C5",  "5 of Clubs",
            "playerUpPanel",		5,  imageC5_small);
    Card playerUp_C6_small  = new Card("C6",  "6 of Clubs",
            "playerUpPanel",		6,  imageC6_small);
    Card playerUp_C7_small  = new Card("C7",  "7 of Clubs",
            "playerUpPanel",		7,  imageC7_small);
    Card playerUp_C8_small  = new Card("C8",  "8 of Clubs",
            "playerUpPanel",		8,  imageC8_small);
    Card playerUp_C9_small  = new Card("C9",  "9 of Clubs",
            "playerUpPanel",		9,  imageC9_small);
    Card playerUp_C10_small = new Card("C10", "10 of Clubs",
            "playerUpPanel",		10, imageC10_small);
    Card playerUp_C11_small = new Card("C11", "Jack of Clubs",
            "playerUpPanel",		11, imageC11_small);
    Card playerUp_C12_small = new Card("C12", "Queen of Clubs",
            "playerUpPanel",		12, imageC12_small);
    Card playerUp_C13_small = new Card("C13", "King of Clubs",
            "playerUpPanel",		13, imageC13_small);
    Card playerUp_C14_small = new Card("C14", "Ace of Clubs",
            "playerUpPanel",		14, imageC14_small);
    Card playerUp_D2_small  = new Card("D2",  "2 of Diamonds",
            "playerUpPanel",		2,  imageD2_small);
    Card playerUp_D3_small  = new Card("D3",  "3 of Diamonds",
            "playerUpPanel",		3,  imageD3_small);
    Card playerUp_D4_small  = new Card("D4",  "4 of Diamonds",
            "playerUpPanel",		4,  imageD4_small);
    Card playerUp_D5_small  = new Card("D5",  "5 of Diamonds",
            "playerUpPanel",		5,  imageD5_small);
    Card playerUp_D6_small  = new Card("D6",  "6 of Diamonds",
            "playerUpPanel",		6,  imageD6_small);
    Card playerUp_D7_small  = new Card("D7",  "7 of Diamonds",
            "playerUpPanel",		7,  imageD7_small);
    Card playerUp_D8_small  = new Card("D8",  "8 of Diamonds",
            "playerUpPanel",		8,  imageD8_small);
    Card playerUp_D9_small  = new Card("D9",  "9 of Diamonds",
            "playerUpPanel",		9,  imageD9_small);
    Card playerUp_D10_small = new Card("D10", "10 of Diamonds",
            "playerUpPanel",		10, imageD10_small);
    Card playerUp_D11_small = new Card("D11", "Jack of Diamonds",
            "playerUpPanel",	11, imageD11_small);
    Card playerUp_D12_small = new Card("D12", "Queen of Diamonds",
            "playerUpPanel",	12, imageD12_small);
    Card playerUp_D13_small = new Card("D13", "King of Diamonds",
            "playerUpPanel",	13, imageD13_small);
    Card playerUp_D14_small = new Card("D14", "Ace of Diamonds",
            "playerUpPanel",	14, imageD14_small);
    Card playerUp_H2_small  = new Card("H2",  "2 of Hearts",
            "playerUpPanel",		2,  imageH2_small);
    Card playerUp_H3_small  = new Card("H3",  "3 of Hearts",
            "playerUpPanel",		3,  imageH3_small);
    Card playerUp_H4_small  = new Card("H4",  "4 of Hearts",
            "playerUpPanel",		4,  imageH4_small);
    Card playerUp_H5_small  = new Card("H5",  "5 of Hearts",
            "playerUpPanel",		5,  imageH5_small);
    Card playerUp_H6_small  = new Card("H6",  "6 of Hearts",
            "playerUpPanel",		6,  imageH6_small);
    Card playerUp_H7_small  = new Card("H7",  "7 of Hearts",
            "playerUpPanel",		7,  imageH7_small);
    Card playerUp_H8_small  = new Card("H8",  "8 of Hearts",
            "playerUpPanel",		8,  imageH8_small);
    Card playerUp_H9_small  = new Card("H9",  "9 of Hearts",
            "playerUpPanel",		9,  imageH9_small);
    Card playerUp_H10_small = new Card("H10", "10 of Hearts",
            "playerUpPanel",		10, imageH10_small);
    Card playerUp_H11_small = new Card("H11", "Jack of Hearts",
            "playerUpPanel",		11, imageH11_small);
    Card playerUp_H12_small = new Card("H12", "Queen of Hearts",
            "playerUpPanel",		12, imageH12_small);
    Card playerUp_H13_small = new Card("H13", "King of Hearts",
            "playerUpPanel",		13, imageH13_small);
    Card playerUp_H14_small = new Card("H14", "Ace of Hearts",
            "playerUpPanel",		14, imageH14_small);
    Card playerUp_S2_small  = new Card("S2",  "2 of Spades",
            "playerUpPanel",		2,  imageS2_small);
    Card playerUp_S3_small  = new Card("S3",  "3 of Spades",
            "playerUpPanel",		3,  imageS3_small);
    Card playerUp_S4_small  = new Card("S4",  "4 of Spades",
            "playerUpPanel",		4,  imageS4_small);
    Card playerUp_S5_small  = new Card("S5",  "5 of Spades",
            "playerUpPanel",		5,  imageS5_small);
    Card playerUp_S6_small  = new Card("S6",  "6 of Spades",
            "playerUpPanel",		6,  imageS6_small);
    Card playerUp_S7_small  = new Card("S7",  "7 of Spades",
            "playerUpPanel",		7,  imageS7_small);
    Card playerUp_S8_small  = new Card("S8",  "8 of Spades",
            "playerUpPanel",		8,  imageS8_small);
    Card playerUp_S9_small  = new Card("S9",  "9 of Spades",
            "playerUpPanel",		9,  imageS9_small);
    Card playerUp_S10_small = new Card("S10", "10 of Spades",
            "playerUpPanel",		10, imageS10_small);
    Card playerUp_S11_small = new Card("S11", "Jack of Spades",
            "playerUpPanel",		11, imageS11_small);
    Card playerUp_S12_small = new Card("S12", "Queen of Spades",
            "playerUpPanel",		12, imageS12_small);
    Card playerUp_S13_small = new Card("S13", "King of Spades",
            "playerUpPanel",		13, imageS13_small);
    Card playerUp_S14_small = new Card("S14", "Ace of Spades",
            "playerUpPanel",		14, imageS14_small);
    Card playerUp_top1		= new Card("TC",  "top card",
            "playerUpPanel", 		 0, topCardIcon);
    Card playerUp_top2		= new Card("TC",  "top card",
            "playerUpPanel", 		 0, topCardIcon);
    Card playerUp_top3 		= new Card("TC",  "top card",
            "playerUpPanel", 		 0, topCardIcon);
    Card playerUp_outline1  = new Card("CO",  "card outline",
            "playerUpPanel", 		 0, cardOutlineIcon);
    Card playerUp_outline2  = new Card("CO",  "card outline",
            "playerUpPanel", 		 0, cardOutlineIcon);
    Card playerUp_outline3  = new Card("CO",  "card outline",
            "playerUpPanel", 		 0, cardOutlineIcon);

    Card [] playerUpArray = {
            playerUp_C2,  playerUp_C3,  playerUp_C4,
            playerUp_C5,  playerUp_C6,  playerUp_C7,
            playerUp_C8,  playerUp_C9,  playerUp_C10,
            playerUp_C11, playerUp_C12, playerUp_C13,
            playerUp_C14, playerUp_D2,  playerUp_D3,
            playerUp_D4,  playerUp_D5,  playerUp_D6,
            playerUp_D7,  playerUp_D8,  playerUp_D9,
            playerUp_D10, playerUp_D11, playerUp_D12,
            playerUp_D13, playerUp_D14, playerUp_H2,
            playerUp_H3,  playerUp_H4,  playerUp_H5,
            playerUp_H6,  playerUp_H7,  playerUp_H8,
            playerUp_H9,  playerUp_H10, playerUp_H11,
            playerUp_H12, playerUp_H13, playerUp_H14,
            playerUp_S2,  playerUp_S3,  playerUp_S4,
            playerUp_S5,  playerUp_S6,  playerUp_S7,
            playerUp_S8,  playerUp_S9,  playerUp_S10,
            playerUp_S11, playerUp_S12, playerUp_S13,
            playerUp_S14,       playerUp_C2_small,  playerUp_C3_small,
            playerUp_C4_small,  playerUp_C5_small,  playerUp_C6_small,
            playerUp_C7_small,  playerUp_C8_small,  playerUp_C9_small,
            playerUp_C10_small, playerUp_C11_small, playerUp_C12_small,
            playerUp_C13_small, playerUp_C14_small, playerUp_D2_small,
            playerUp_D3_small,  playerUp_D4_small,  playerUp_D5_small,
            playerUp_D6_small,  playerUp_D7_small,  playerUp_D8_small,
            playerUp_D9_small,  playerUp_D10_small, playerUp_D11_small,
            playerUp_D12_small, playerUp_D13_small, playerUp_D14_small,
            playerUp_H2_small,  playerUp_H3_small,  playerUp_H4_small,
            playerUp_H5_small,  playerUp_H6_small,  playerUp_H7_small,
            playerUp_H8_small,  playerUp_H9_small,  playerUp_H10_small,
            playerUp_H11_small, playerUp_H12_small, playerUp_H13_small,
            playerUp_H14_small, playerUp_S2_small,  playerUp_S3_small,
            playerUp_S4_small,  playerUp_S5_small,  playerUp_S6_small,
            playerUp_S7_small,  playerUp_S8_small,  playerUp_S9_small,
            playerUp_S10_small, playerUp_S11_small, playerUp_S12_small,
            playerUp_S13_small, playerUp_S14_small,
            playerUp_top1,      playerUp_top2,      playerUp_top3,
            playerUp_outline1,  playerUp_outline2,  playerUp_outline3 };

    // playerHand
    Card playerHand_C2  = new Card("C2",  "2 of Clubs",
            "playerHandPanel",		2,	imageC2);
    Card playerHand_C3  = new Card("C3",  "3 of Clubs",
            "playerHandPanel",		3,  imageC3);
    Card playerHand_C4  = new Card("C4",  "4 of Clubs",
            "playerHandPanel",		4,  imageC4);
    Card playerHand_C5  = new Card("C5",  "5 of Clubs",
            "playerHandPanel",		5,  imageC5);
    Card playerHand_C6  = new Card("C6",  "6 of Clubs",
            "playerHandPanel",		6,  imageC6);
    Card playerHand_C7  = new Card("C7",  "7 of Clubs",
            "playerHandPanel",		7,  imageC7);
    Card playerHand_C8  = new Card("C8",  "8 of Clubs",
            "playerHandPanel",		8,  imageC8);
    Card playerHand_C9  = new Card("C9",  "9 of Clubs",
            "playerHandPanel",		9,  imageC9);
    Card playerHand_C10 = new Card("C10", "10 of Clubs",
            "playerHandPanel",		10, imageC10);
    Card playerHand_C11 = new Card("C11", "Jack of Clubs",
            "playerHandPanel",		11, imageC11);
    Card playerHand_C12 = new Card("C12", "Queen of Clubs",
            "playerHandPanel",		12, imageC12);
    Card playerHand_C13 = new Card("C13", "King of Clubs",
            "playerHandPanel",		13, imageC13);
    Card playerHand_C14 = new Card("C14", "Ace of Clubs",
            "playerHandPanel",		14, imageC14);
    Card playerHand_D2  = new Card("D2",  "2 of Diamonds",
            "playerHandPanel",		2,  imageD2);
    Card playerHand_D3  = new Card("D3",  "3 of Diamonds",
            "playerHandPanel",		3,  imageD3);
    Card playerHand_D4  = new Card("D4",  "4 of Diamonds",
            "playerHandPanel",		4,  imageD4);
    Card playerHand_D5  = new Card("D5",  "5 of Diamonds",
            "playerHandPanel",		5,  imageD5);
    Card playerHand_D6  = new Card("D6",  "6 of Diamonds",
            "playerHandPanel",		6,  imageD6);
    Card playerHand_D7  = new Card("D7",  "7 of Diamonds",
            "playerHandPanel",		7,  imageD7);
    Card playerHand_D8  = new Card("D8",  "8 of Diamonds",
            "playerHandPanel",		8,  imageD8);
    Card playerHand_D9  = new Card("D9",  "9 of Diamonds",
            "playerHandPanel",		9,  imageD9);
    Card playerHand_D10 = new Card("D10", "10 of Diamonds",
            "playerHandPanel",		10, imageD10);
    Card playerHand_D11 = new Card("D11", "Jack of Diamonds",
            "playerHandPanel",	11, imageD11);
    Card playerHand_D12 = new Card("D12", "Queen of Diamonds",
            "playerHandPanel",	12, imageD12);
    Card playerHand_D13 = new Card("D13", "King of Diamonds",
            "playerHandPanel",	13, imageD13);
    Card playerHand_D14 = new Card("D14", "Ace of Diamonds",
            "playerHandPanel",	14, imageD14);
    Card playerHand_H2  = new Card("H2",  "2 of Hearts",
            "playerHandPanel",		2,  imageH2);
    Card playerHand_H3  = new Card("H3",  "3 of Hearts",
            "playerHandPanel",		3,  imageH3);
    Card playerHand_H4  = new Card("H4",  "4 of Hearts",
            "playerHandPanel",		4,  imageH4);
    Card playerHand_H5  = new Card("H5",  "5 of Hearts",
            "playerHandPanel",		5,  imageH5);
    Card playerHand_H6  = new Card("H6",  "6 of Hearts",
            "playerHandPanel",		6,  imageH6);
    Card playerHand_H7  = new Card("H7",  "7 of Hearts",
            "playerHandPanel",		7,  imageH7);
    Card playerHand_H8  = new Card("H8",  "8 of Hearts",
            "playerHandPanel",		8,  imageH8);
    Card playerHand_H9  = new Card("H9",  "9 of Hearts",
            "playerHandPanel",		9,  imageH9);
    Card playerHand_H10 = new Card("H10", "10 of Hearts",
            "playerHandPanel",		10, imageH10);
    Card playerHand_H11 = new Card("H11", "Jack of Hearts",
            "playerHandPanel",		11, imageH11);
    Card playerHand_H12 = new Card("H12", "Queen of Hearts",
            "playerHandPanel",		12, imageH12);
    Card playerHand_H13 = new Card("H13", "King of Hearts",
            "playerHandPanel",		13, imageH13);
    Card playerHand_H14 = new Card("H14", "Ace of Hearts",
            "playerHandPanel",		14, imageH14);
    Card playerHand_S2  = new Card("S2",  "2 of Spades",
            "playerHandPanel",		2,  imageS2);
    Card playerHand_S3  = new Card("S3",  "3 of Spades",
            "playerHandPanel",		3,  imageS3);
    Card playerHand_S4  = new Card("S4",  "4 of Spades",
            "playerHandPanel",		4,  imageS4);
    Card playerHand_S5  = new Card("S5",  "5 of Spades",
            "playerHandPanel",		5,  imageS5);
    Card playerHand_S6  = new Card("S6",  "6 of Spades",
            "playerHandPanel",		6,  imageS6);
    Card playerHand_S7  = new Card("S7",  "7 of Spades",
            "playerHandPanel",		7,  imageS7);
    Card playerHand_S8  = new Card("S8",  "8 of Spades",
            "playerHandPanel",		8,  imageS8);
    Card playerHand_S9  = new Card("S9",  "9 of Spades",
            "playerHandPanel",		9,  imageS9);
    Card playerHand_S10 = new Card("S10", "10 of Spades",
            "playerHandPanel",		10, imageS10);
    Card playerHand_S11 = new Card("S11", "Jack of Spades",
            "playerHandPanel",		11, imageS11);
    Card playerHand_S12 = new Card("S12", "Queen of Spades",
            "playerHandPanel",		12, imageS12);
    Card playerHand_S13 = new Card("S13", "King of Spades",
            "playerHandPanel",		13, imageS13);
    Card playerHand_S14 = new Card("S14", "Ace of Spades",
            "playerHandPanel",		14, imageS14);
    Card playerHand_C2_small  = new Card("C2",  "2 of Clubs",
            "playerHandPanel",		2,  imageC2_small);
    Card playerHand_C3_small  = new Card("C3",  "3 of Clubs",
            "playerHandPanel",		3,  imageC3_small);
    Card playerHand_C4_small  = new Card("C4",  "4 of Clubs",
            "playerHandPanel",		4,  imageC4_small);
    Card playerHand_C5_small  = new Card("C5",  "5 of Clubs",
            "playerHandPanel",		5,  imageC5_small);
    Card playerHand_C6_small  = new Card("C6",  "6 of Clubs",
            "playerHandPanel",		6,  imageC6_small);
    Card playerHand_C7_small  = new Card("C7",  "7 of Clubs",
            "playerHandPanel",		7,  imageC7_small);
    Card playerHand_C8_small  = new Card("C8",  "8 of Clubs",
            "playerHandPanel",		8,  imageC8_small);
    Card playerHand_C9_small  = new Card("C9",  "9 of Clubs",
            "playerHandPanel",		9,  imageC9_small);
    Card playerHand_C10_small = new Card("C10", "10 of Clubs",
            "playerHandPanel",		10, imageC10_small);
    Card playerHand_C11_small = new Card("C11", "Jack of Clubs",
            "playerHandPanel",		11, imageC11_small);
    Card playerHand_C12_small = new Card("C12", "Queen of Clubs",
            "playerHandPanel",		12, imageC12_small);
    Card playerHand_C13_small = new Card("C13", "King of Clubs",
            "playerHandPanel",		13, imageC13_small);
    Card playerHand_C14_small = new Card("C14", "Ace of Clubs",
            "playerHandPanel",		14, imageC14_small);
    Card playerHand_D2_small  = new Card("D2",  "2 of Diamonds",
            "playerHandPanel",		2,  imageD2_small);
    Card playerHand_D3_small  = new Card("D3",  "3 of Diamonds",
            "playerHandPanel",		3,  imageD3_small);
    Card playerHand_D4_small  = new Card("D4",  "4 of Diamonds",
            "playerHandPanel",		4,  imageD4_small);
    Card playerHand_D5_small  = new Card("D5",  "5 of Diamonds",
            "playerHandPanel",		5,  imageD5_small);
    Card playerHand_D6_small  = new Card("D6",  "6 of Diamonds",
            "playerHandPanel",		6,  imageD6_small);
    Card playerHand_D7_small  = new Card("D7",  "7 of Diamonds",
            "playerHandPanel",		7,  imageD7_small);
    Card playerHand_D8_small  = new Card("D8",  "8 of Diamonds",
            "playerHandPanel",		8,  imageD8_small);
    Card playerHand_D9_small  = new Card("D9",  "9 of Diamonds",
            "playerHandPanel",		9,  imageD9_small);
    Card playerHand_D10_small = new Card("D10", "10 of Diamonds",
            "playerHandPanel",		10, imageD10_small);
    Card playerHand_D11_small = new Card("D11", "Jack of Diamonds",
            "playerHandPanel",	11, imageD11_small);
    Card playerHand_D12_small = new Card("D12", "Queen of Diamonds",
            "playerHandPanel",	12, imageD12_small);
    Card playerHand_D13_small = new Card("D13", "King of Diamonds",
            "playerHandPanel",	13, imageD13_small);
    Card playerHand_D14_small = new Card("D14", "Ace of Diamonds",
            "playerHandPanel",	14, imageD14_small);
    Card playerHand_H2_small  = new Card("H2",  "2 of Hearts",
            "playerHandPanel",		2,  imageH2_small);
    Card playerHand_H3_small  = new Card("H3",  "3 of Hearts",
            "playerHandPanel",		3,  imageH3_small);
    Card playerHand_H4_small  = new Card("H4",  "4 of Hearts",
            "playerHandPanel",		4,  imageH4_small);
    Card playerHand_H5_small  = new Card("H5",  "5 of Hearts",
            "playerHandPanel",		5,  imageH5_small);
    Card playerHand_H6_small  = new Card("H6",  "6 of Hearts",
            "playerHandPanel",		6,  imageH6_small);
    Card playerHand_H7_small  = new Card("H7",  "7 of Hearts",
            "playerHandPanel",		7,  imageH7_small);
    Card playerHand_H8_small  = new Card("H8",  "8 of Hearts",
            "playerHandPanel",		8,  imageH8_small);
    Card playerHand_H9_small  = new Card("H9",  "9 of Hearts",
            "playerHandPanel",		9,  imageH9_small);
    Card playerHand_H10_small = new Card("H10", "10 of Hearts",
            "playerHandPanel",		10, imageH10_small);
    Card playerHand_H11_small = new Card("H11", "Jack of Hearts",
            "playerHandPanel",		11, imageH11_small);
    Card playerHand_H12_small = new Card("H12", "Queen of Hearts",
            "playerHandPanel",		12, imageH12_small);
    Card playerHand_H13_small = new Card("H13", "King of Hearts",
            "playerHandPanel",		13, imageH13_small);
    Card playerHand_H14_small = new Card("H14", "Ace of Hearts",
            "playerHandPanel",		14, imageH14_small);
    Card playerHand_S2_small  = new Card("S2",  "2 of Spades",
            "playerHandPanel",		2,  imageS2_small);
    Card playerHand_S3_small  = new Card("S3",  "3 of Spades",
            "playerHandPanel",		3,  imageS3_small);
    Card playerHand_S4_small  = new Card("S4",  "4 of Spades",
            "playerHandPanel",		4,  imageS4_small);
    Card playerHand_S5_small  = new Card("S5",  "5 of Spades",
            "playerHandPanel",		5,  imageS5_small);
    Card playerHand_S6_small  = new Card("S6",  "6 of Spades",
            "playerHandPanel",		6,  imageS6_small);
    Card playerHand_S7_small  = new Card("S7",  "7 of Spades",
            "playerHandPanel",		7,  imageS7_small);
    Card playerHand_S8_small  = new Card("S8",  "8 of Spades",
            "playerHandPanel",		8,  imageS8_small);
    Card playerHand_S9_small  = new Card("S9",  "9 of Spades",
            "playerHandPanel",		9,  imageS9_small);
    Card playerHand_S10_small = new Card("S10", "10 of Spades",
            "playerHandPanel",		10, imageS10_small);
    Card playerHand_S11_small = new Card("S11", "Jack of Spades",
            "playerHandPanel",		11, imageS11_small);
    Card playerHand_S12_small = new Card("S12", "Queen of Spades",
            "playerHandPanel",		12, imageS12_small);
    Card playerHand_S13_small = new Card("S13", "King of Spades",
            "playerHandPanel",		13, imageS13_small);
    Card playerHand_S14_small = new Card("S14", "Ace of Spades",
            "playerHandPanel",		14, imageS14_small);
    Card playerHand_top1		  = new Card("TC",  "top card",
            "playerHandPanel", 	 0, topCardIcon);
    Card playerHand_top2		  = new Card("TC",  "top card",
            "playerHandPanel", 	 0, topCardIcon);
    Card playerHand_top3		  = new Card("TC",  "top card",
            "playerHandPanel", 	 0, topCardIcon);
    Card playerHand_outline1  = new Card("CO",  "card outline",
            "playerHandPanel", 	 0, cardOutlineIcon);
    Card playerHand_outline2  = new Card("CO",  "card outline",
            "playerHandPanel", 	 0, cardOutlineIcon);
    Card playerHand_outline3  = new Card("CO",  "card outline",
            "playerHandPanel", 	 0, cardOutlineIcon);

    Card [] playerHandArray = {
            playerHand_C2,  playerHand_C3,  playerHand_C4,
            playerHand_C5,  playerHand_C6,  playerHand_C7,
            playerHand_C8,  playerHand_C9,  playerHand_C10,
            playerHand_C11, playerHand_C12, playerHand_C13,
            playerHand_C14, playerHand_D2,  playerHand_D3,
            playerHand_D4,  playerHand_D5,  playerHand_D6,
            playerHand_D7,  playerHand_D8,  playerHand_D9,
            playerHand_D10, playerHand_D11, playerHand_D12,
            playerHand_D13, playerHand_D14, playerHand_H2,
            playerHand_H3,  playerHand_H4,  playerHand_H5,
            playerHand_H6,  playerHand_H7,  playerHand_H8,
            playerHand_H9,  playerHand_H10, playerHand_H11,
            playerHand_H12, playerHand_H13, playerHand_H14,
            playerHand_S2,  playerHand_S3,  playerHand_S4,
            playerHand_S5,  playerHand_S6,  playerHand_S7,
            playerHand_S8,  playerHand_S9,  playerHand_S10,
            playerHand_S11, playerHand_S12, playerHand_S13,
            playerHand_S14,       playerHand_C2_small,  playerHand_C3_small,
            playerHand_C4_small,  playerHand_C5_small,  playerHand_C6_small,
            playerHand_C7_small,  playerHand_C8_small,  playerHand_C9_small,
            playerHand_C10_small, playerHand_C11_small, playerHand_C12_small,
            playerHand_C13_small, playerHand_C14_small, playerHand_D2_small,
            playerHand_D3_small,  playerHand_D4_small,  playerHand_D5_small,
            playerHand_D6_small,  playerHand_D7_small,  playerHand_D8_small,
            playerHand_D9_small,  playerHand_D10_small, playerHand_D11_small,
            playerHand_D12_small, playerHand_D13_small, playerHand_D14_small,
            playerHand_H2_small,  playerHand_H3_small,  playerHand_H4_small,
            playerHand_H5_small,  playerHand_H6_small,  playerHand_H7_small,
            playerHand_H8_small,  playerHand_H9_small,  playerHand_H10_small,
            playerHand_H11_small, playerHand_H12_small, playerHand_H13_small,
            playerHand_H14_small, playerHand_S2_small,  playerHand_S3_small,
            playerHand_S4_small,  playerHand_S5_small,  playerHand_S6_small,
            playerHand_S7_small,  playerHand_S8_small,  playerHand_S9_small,
            playerHand_S10_small, playerHand_S11_small, playerHand_S12_small,
            playerHand_S13_small, playerHand_S14_small,
            playerHand_top1,      playerHand_top2,      playerHand_top3,
            playerHand_outline1,  playerHand_outline2,  playerHand_outline3 };


    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //																												//
    // MAIN METHOD																								//
    //																												//
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    public static void main( String [] args)
    {
        // Create a frame and set frame attributes
        AmericanGameMain frame = new AmericanGameMain();
        frame.setTitle("American");
        frame.setSize(1000,800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center frame on screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = (screenWidth - frame.getWidth()) / 2;
        int y = (screenHeight - frame.getHeight()) / 2;
        frame.setLocation(x,y);
        frame.setVisible(true);
    }


    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //																												//
    // GUI INTERFACE METHOD																					//
    //																												//
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    private AmericanGameMain()
    {
        // Initialize Variables
        cardSizeRatio = 1.00;
        wins = 0;
        losses = 0;

        // Add Menus
        JMenuBar jmb = new JMenuBar();
        this.setJMenuBar(jmb);

        // Create Menus
        JMenu fileMenu = new JMenu("File");
        JMenu optionsMenu = new JMenu("Options");
        JMenu helpMenu = new JMenu("Help");

        // Add menus to JMenuBar
        jmb.add(fileMenu);
        jmb.add(optionsMenu);
        jmb.add(helpMenu);
        jmb.add(Box.createGlue());
        jmb.add(winLossPanel);

        // Add to file men
        JMenuItem dealMenuButton = new JMenuItem("Deal New Game");
        fileMenu.add(dealMenuButton);
        fileMenu.addSeparator();
        JMenuItem exitMenuButton = new JMenuItem("Exit");
        fileMenu.add(exitMenuButton);

        // Add to options menu
        JMenu setDiffMenu = new JMenu("Set Difficulty");
        setDiffMenu.add(setDiffMenuItem_E);
        setDiffMenu.add(setDiffMenuItem_H);
        optionsMenu.add(setDiffMenu);
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(setDiffMenuItem_E);
        buttonGroup1.add(setDiffMenuItem_H);
        setDiffMenuItem_E.setSelected(true);
        JMenuItem clearWLMenuButton = new JMenuItem("Clear Wins & Losses");
        optionsMenu.add(clearWLMenuButton);

        // Create help menu
        JMenuItem rulesMenuButton = new JMenuItem("View Game Rules");
        helpMenu.add(rulesMenuButton);

        // Add to Content Pane
        // Get the content pane of the frame
        Container container = getContentPane();

        // Set layout manager for the frame
        container.setLayout(new GridBagLayout());

        // Create a GridBagConstraints object
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.BOTH;
        gbConstraints.anchor = GridBagConstraints.CENTER;

        // Place components
        // Place opponents cards
        addComp(opponentHandPanel, container, gbConstraints, 0, 0, 1, 4, 1, 1);
        addComp(opponentUpPanel, container, gbConstraints,   1, 1, 1, 2, 1, 1);

        // Place emptyPanel, deck and pile
        addComp(emptyPanel, container, gbConstraints,        1, 0, 1, 1, 1, 1);
        addComp(deckPanel, container, gbConstraints,         1, 3, 1, 1, 1, 0.5);
        addComp(pilePanel, container, gbConstraints,         2, 0, 1, 4, 1, 1);

        // Place players cards
        addComp(playerUpPanel, container, gbConstraints,     3, 1, 1, 2, 1, 1);
        addComp(playerHandPanel, container, gbConstraints,   4, 0, 1, 4, 1, 1);

        // Place message board 1,2,3,4,5
        addComp(messagePanel5, container, gbConstraints, 5, 0, 1, 4, 1, 0.25);
        addComp(messagePanel4, container, gbConstraints, 6, 0, 1, 4, 1, 0.25);
        addComp(messagePanel3, container, gbConstraints, 7, 0, 1, 4, 1, 0.25);
        addComp(messagePanel2, container, gbConstraints, 8, 0, 1, 4, 1, 0.25);
        addComp(messagePanel1, container, gbConstraints, 9, 0, 1, 4, 1, 0.25);

        // Place deal button
        addComp(dealButton, container, gbConstraints,      10, 0, 1, 1, 1, 0.25);

        // Place cursor position bar
        addComp(cursorMessage, container, gbConstraints, 10, 1, 1, 2, 1, 0.25);

        // Place rules button
        addComp(rulesButton, container, gbConstraints,   10, 3, 1, 1, 1, 0.25);

        // Add panels to emptyPanel
        emptyPanel.add(emptyPanelBlnk, "blank panel");
        emptyPanel.add(emptyPanelHide, "hidden panel");

        // Set buttons to unfocusable
        dealButton.setFocusable(false);
        rulesButton.setFocusable(false);

        // Color components (except background)
        opponentHandPanel.setOpaque(false);
        opponentUpPanel.setOpaque(false);
        pilePanel.setOpaque(false);
        deckPanel.setOpaque(false);
        emptyPanel.setOpaque(false);
        emptyPanelBlnk.setOpaque(true);
        emptyPanelHide.setOpaque(false);
        playerUpPanel.setOpaque(false);
        playerHandPanel.setOpaque(false);
        messagePanel5.setOpaque(true);
        messagePanel4.setOpaque(true);
        messagePanel3.setOpaque(true);
        messagePanel2.setOpaque(true);
        messagePanel1.setOpaque(true);
        cursorMessage.setOpaque(true);
        messagePanel5.setBackground(new Color(242,242,242));
        messagePanel4.setBackground(new Color(242,242,242));
        messagePanel3.setBackground(new Color(242,242,242));
        messagePanel2.setBackground(new Color(242,242,242));
        messagePanel1.setBackground(new Color(242,242,242));
        cursorMessage.setBackground(new Color(242,242,242));
        container.setBackground(new Color(90,150,100));
        emptyPanelBlnk.setBackground(new Color(90,150,100));

        // Register listeners
        dealMenuButton.addActionListener(this);
        exitMenuButton.addActionListener(this);
        setDiffMenuItem_E.addActionListener(this);
        setDiffMenuItem_H.addActionListener(this);
        clearWLMenuButton.addActionListener(this);
        rulesMenuButton.addActionListener(this);
        dealButton.addActionListener(this);
        rulesButton.addActionListener(this);
        registerCardListeners();

        // Import or create the data file
        boolean fileExists = dataFile.exists();
        if(fileExists)
        {
            importDataFile(dataFile);
        }
        else // file does not exist and must be created
        {
            createDataFile(dataFile);
        }

        // Resize cards
        resizeCards();

        // Begin the game
        setupGameArea();
    }

    // Taken from (1) on page 800 of
    //      "Introduction to Java Programming: Comprehensive Version"
    private void addComp(Component c, Container container,
                         GridBagConstraints gbConstraints,
                         int row, int column, int numberOfRows,
                         int numberOfColumns, double weightx,
                         double weighty)
    {
        // Set parameters
        gbConstraints.gridx = column;
        gbConstraints.gridy = row;
        gbConstraints.gridwidth = numberOfColumns;
        gbConstraints.gridheight = numberOfRows;
        gbConstraints.weightx = weightx;
        gbConstraints.weighty = weighty;

        // Add component to the container with the specified layout
        container.add(c, gbConstraints);
    }

    // Accepts an imageIcon, an int, and a JPanel
    // Places the icon in the JPanel the specified number of times
    private void addIconsToPanel(ImageIcon icon, int repetitions, JPanel panel)
    {
        for(int i=0; i<repetitions; i++)
        {
            JLabel label = new JLabel(icon);
            panel.add(label);
            label.setVisible(true);
            panel.setVisible(true);
            panel.revalidate();
            panel.repaint();
        }
    }

    // Clears the messagePanels
    private void clearMessages()
    {
        // Clear messagePanel
        messagePanel5.setText(" ");
        messagePanel4.setText(" ");
        messagePanel3.setText(" ");
        messagePanel2.setText(" ");
        messagePanel1.setText(" ");
    }

    // Taken from http://java.sun.com/docs/books/tutorial/uiswing/components/icon.html
    // Returns an ImageIcon, or null if the path was invalid.
    private ImageIcon createImageIcon(String path, String description)
    {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null)
        {
            return new ImageIcon(imgURL, description);
        }
        else
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    // Registers listeners for each card
    private void registerCardListeners()
    {
        // Register opponentHand listeners
        opponentHand_C2.addMouseListener(this);
        opponentHand_C3.addMouseListener(this);
        opponentHand_C4.addMouseListener(this);
        opponentHand_C5.addMouseListener(this);
        opponentHand_C6.addMouseListener(this);
        opponentHand_C7.addMouseListener(this);
        opponentHand_C8.addMouseListener(this);
        opponentHand_C9.addMouseListener(this);
        opponentHand_C10.addMouseListener(this);
        opponentHand_C11.addMouseListener(this);
        opponentHand_C12.addMouseListener(this);
        opponentHand_C13.addMouseListener(this);
        opponentHand_C14.addMouseListener(this);
        opponentHand_D2.addMouseListener(this);
        opponentHand_D3.addMouseListener(this);
        opponentHand_D4.addMouseListener(this);
        opponentHand_D5.addMouseListener(this);
        opponentHand_D6.addMouseListener(this);
        opponentHand_D7.addMouseListener(this);
        opponentHand_D8.addMouseListener(this);
        opponentHand_D9.addMouseListener(this);
        opponentHand_D10.addMouseListener(this);
        opponentHand_D11.addMouseListener(this);
        opponentHand_D12.addMouseListener(this);
        opponentHand_D13.addMouseListener(this);
        opponentHand_D14.addMouseListener(this);
        opponentHand_H2.addMouseListener(this);
        opponentHand_H3.addMouseListener(this);
        opponentHand_H4.addMouseListener(this);
        opponentHand_H5.addMouseListener(this);
        opponentHand_H6.addMouseListener(this);
        opponentHand_H7.addMouseListener(this);
        opponentHand_H8.addMouseListener(this);
        opponentHand_H9.addMouseListener(this);
        opponentHand_H10.addMouseListener(this);
        opponentHand_H11.addMouseListener(this);
        opponentHand_H12.addMouseListener(this);
        opponentHand_H13.addMouseListener(this);
        opponentHand_H14.addMouseListener(this);
        opponentHand_S2.addMouseListener(this);
        opponentHand_S3.addMouseListener(this);
        opponentHand_S4.addMouseListener(this);
        opponentHand_S5.addMouseListener(this);
        opponentHand_S6.addMouseListener(this);
        opponentHand_S7.addMouseListener(this);
        opponentHand_S8.addMouseListener(this);
        opponentHand_S9.addMouseListener(this);
        opponentHand_S10.addMouseListener(this);
        opponentHand_S11.addMouseListener(this);
        opponentHand_S12.addMouseListener(this);
        opponentHand_S13.addMouseListener(this);
        opponentHand_S14.addMouseListener(this);
        opponentHand_C2_small.addMouseListener(this);
        opponentHand_C3_small.addMouseListener(this);
        opponentHand_C4_small.addMouseListener(this);
        opponentHand_C5_small.addMouseListener(this);
        opponentHand_C6_small.addMouseListener(this);
        opponentHand_C7_small.addMouseListener(this);
        opponentHand_C8_small.addMouseListener(this);
        opponentHand_C9_small.addMouseListener(this);
        opponentHand_C10_small.addMouseListener(this);
        opponentHand_C11_small.addMouseListener(this);
        opponentHand_C12_small.addMouseListener(this);
        opponentHand_C13_small.addMouseListener(this);
        opponentHand_C14_small.addMouseListener(this);
        opponentHand_D2_small.addMouseListener(this);
        opponentHand_D3_small.addMouseListener(this);
        opponentHand_D4_small.addMouseListener(this);
        opponentHand_D5_small.addMouseListener(this);
        opponentHand_D6_small.addMouseListener(this);
        opponentHand_D7_small.addMouseListener(this);
        opponentHand_D8_small.addMouseListener(this);
        opponentHand_D9_small.addMouseListener(this);
        opponentHand_D10_small.addMouseListener(this);
        opponentHand_D11_small.addMouseListener(this);
        opponentHand_D12_small.addMouseListener(this);
        opponentHand_D13_small.addMouseListener(this);
        opponentHand_D14_small.addMouseListener(this);
        opponentHand_H2_small.addMouseListener(this);
        opponentHand_H3_small.addMouseListener(this);
        opponentHand_H4_small.addMouseListener(this);
        opponentHand_H5_small.addMouseListener(this);
        opponentHand_H6_small.addMouseListener(this);
        opponentHand_H7_small.addMouseListener(this);
        opponentHand_H8_small.addMouseListener(this);
        opponentHand_H9_small.addMouseListener(this);
        opponentHand_H10_small.addMouseListener(this);
        opponentHand_H11_small.addMouseListener(this);
        opponentHand_H12_small.addMouseListener(this);
        opponentHand_H13_small.addMouseListener(this);
        opponentHand_H14_small.addMouseListener(this);
        opponentHand_S2_small.addMouseListener(this);
        opponentHand_S3_small.addMouseListener(this);
        opponentHand_S4_small.addMouseListener(this);
        opponentHand_S5_small.addMouseListener(this);
        opponentHand_S6_small.addMouseListener(this);
        opponentHand_S7_small.addMouseListener(this);
        opponentHand_S8_small.addMouseListener(this);
        opponentHand_S9_small.addMouseListener(this);
        opponentHand_S10_small.addMouseListener(this);
        opponentHand_S11_small.addMouseListener(this);
        opponentHand_S12_small.addMouseListener(this);
        opponentHand_S13_small.addMouseListener(this);
        opponentHand_S14_small.addMouseListener(this);
        opponentHand_outline1.addMouseListener(this);
        opponentHand_outline2.addMouseListener(this);
        opponentHand_outline3.addMouseListener(this);
        opponentHand_top1.addMouseListener(this);
        opponentHand_top2.addMouseListener(this);
        opponentHand_top3.addMouseListener(this);

        // Register opponentUp listeners
        opponentUp_C2.addMouseListener(this);
        opponentUp_C3.addMouseListener(this);
        opponentUp_C4.addMouseListener(this);
        opponentUp_C5.addMouseListener(this);
        opponentUp_C6.addMouseListener(this);
        opponentUp_C7.addMouseListener(this);
        opponentUp_C8.addMouseListener(this);
        opponentUp_C9.addMouseListener(this);
        opponentUp_C10.addMouseListener(this);
        opponentUp_C11.addMouseListener(this);
        opponentUp_C12.addMouseListener(this);
        opponentUp_C13.addMouseListener(this);
        opponentUp_C14.addMouseListener(this);
        opponentUp_D2.addMouseListener(this);
        opponentUp_D3.addMouseListener(this);
        opponentUp_D4.addMouseListener(this);
        opponentUp_D5.addMouseListener(this);
        opponentUp_D6.addMouseListener(this);
        opponentUp_D7.addMouseListener(this);
        opponentUp_D8.addMouseListener(this);
        opponentUp_D9.addMouseListener(this);
        opponentUp_D10.addMouseListener(this);
        opponentUp_D11.addMouseListener(this);
        opponentUp_D12.addMouseListener(this);
        opponentUp_D13.addMouseListener(this);
        opponentUp_D14.addMouseListener(this);
        opponentUp_H2.addMouseListener(this);
        opponentUp_H3.addMouseListener(this);
        opponentUp_H4.addMouseListener(this);
        opponentUp_H5.addMouseListener(this);
        opponentUp_H6.addMouseListener(this);
        opponentUp_H7.addMouseListener(this);
        opponentUp_H8.addMouseListener(this);
        opponentUp_H9.addMouseListener(this);
        opponentUp_H10.addMouseListener(this);
        opponentUp_H11.addMouseListener(this);
        opponentUp_H12.addMouseListener(this);
        opponentUp_H13.addMouseListener(this);
        opponentUp_H14.addMouseListener(this);
        opponentUp_S2.addMouseListener(this);
        opponentUp_S3.addMouseListener(this);
        opponentUp_S4.addMouseListener(this);
        opponentUp_S5.addMouseListener(this);
        opponentUp_S6.addMouseListener(this);
        opponentUp_S7.addMouseListener(this);
        opponentUp_S8.addMouseListener(this);
        opponentUp_S9.addMouseListener(this);
        opponentUp_S10.addMouseListener(this);
        opponentUp_S11.addMouseListener(this);
        opponentUp_S12.addMouseListener(this);
        opponentUp_S13.addMouseListener(this);
        opponentUp_S14.addMouseListener(this);
        opponentUp_C2_small.addMouseListener(this);
        opponentUp_C3_small.addMouseListener(this);
        opponentUp_C4_small.addMouseListener(this);
        opponentUp_C5_small.addMouseListener(this);
        opponentUp_C6_small.addMouseListener(this);
        opponentUp_C7_small.addMouseListener(this);
        opponentUp_C8_small.addMouseListener(this);
        opponentUp_C9_small.addMouseListener(this);
        opponentUp_C10_small.addMouseListener(this);
        opponentUp_C11_small.addMouseListener(this);
        opponentUp_C12_small.addMouseListener(this);
        opponentUp_C13_small.addMouseListener(this);
        opponentUp_C14_small.addMouseListener(this);
        opponentUp_D2_small.addMouseListener(this);
        opponentUp_D3_small.addMouseListener(this);
        opponentUp_D4_small.addMouseListener(this);
        opponentUp_D5_small.addMouseListener(this);
        opponentUp_D6_small.addMouseListener(this);
        opponentUp_D7_small.addMouseListener(this);
        opponentUp_D8_small.addMouseListener(this);
        opponentUp_D9_small.addMouseListener(this);
        opponentUp_D10_small.addMouseListener(this);
        opponentUp_D11_small.addMouseListener(this);
        opponentUp_D12_small.addMouseListener(this);
        opponentUp_D13_small.addMouseListener(this);
        opponentUp_D14_small.addMouseListener(this);
        opponentUp_H2_small.addMouseListener(this);
        opponentUp_H3_small.addMouseListener(this);
        opponentUp_H4_small.addMouseListener(this);
        opponentUp_H5_small.addMouseListener(this);
        opponentUp_H6_small.addMouseListener(this);
        opponentUp_H7_small.addMouseListener(this);
        opponentUp_H8_small.addMouseListener(this);
        opponentUp_H9_small.addMouseListener(this);
        opponentUp_H10_small.addMouseListener(this);
        opponentUp_H11_small.addMouseListener(this);
        opponentUp_H12_small.addMouseListener(this);
        opponentUp_H13_small.addMouseListener(this);
        opponentUp_H14_small.addMouseListener(this);
        opponentUp_S2_small.addMouseListener(this);
        opponentUp_S3_small.addMouseListener(this);
        opponentUp_S4_small.addMouseListener(this);
        opponentUp_S5_small.addMouseListener(this);
        opponentUp_S6_small.addMouseListener(this);
        opponentUp_S7_small.addMouseListener(this);
        opponentUp_S8_small.addMouseListener(this);
        opponentUp_S9_small.addMouseListener(this);
        opponentUp_S10_small.addMouseListener(this);
        opponentUp_S11_small.addMouseListener(this);
        opponentUp_S12_small.addMouseListener(this);
        opponentUp_S13_small.addMouseListener(this);
        opponentUp_S14_small.addMouseListener(this);
        opponentUp_outline1.addMouseListener(this);
        opponentUp_outline2.addMouseListener(this);
        opponentUp_outline3.addMouseListener(this);
        opponentUp_top1.addMouseListener(this);
        opponentUp_top2.addMouseListener(this);
        opponentUp_top3.addMouseListener(this);

        // Register deck listener
        deck_top.addMouseListener(this);
        deck_outline.addMouseListener(this);

        // Register pile listeners
        pile_C2.addMouseListener(this);
        pile_C3.addMouseListener(this);
        pile_C4.addMouseListener(this);
        pile_C5.addMouseListener(this);
        pile_C6.addMouseListener(this);
        pile_C7.addMouseListener(this);
        pile_C8.addMouseListener(this);
        pile_C9.addMouseListener(this);
        pile_C10.addMouseListener(this);
        pile_C11.addMouseListener(this);
        pile_C12.addMouseListener(this);
        pile_C13.addMouseListener(this);
        pile_C14.addMouseListener(this);
        pile_D2.addMouseListener(this);
        pile_D3.addMouseListener(this);
        pile_D4.addMouseListener(this);
        pile_D5.addMouseListener(this);
        pile_D6.addMouseListener(this);
        pile_D7.addMouseListener(this);
        pile_D8.addMouseListener(this);
        pile_D9.addMouseListener(this);
        pile_D10.addMouseListener(this);
        pile_D11.addMouseListener(this);
        pile_D12.addMouseListener(this);
        pile_D13.addMouseListener(this);
        pile_D14.addMouseListener(this);
        pile_H2.addMouseListener(this);
        pile_H3.addMouseListener(this);
        pile_H4.addMouseListener(this);
        pile_H5.addMouseListener(this);
        pile_H6.addMouseListener(this);
        pile_H7.addMouseListener(this);
        pile_H8.addMouseListener(this);
        pile_H9.addMouseListener(this);
        pile_H10.addMouseListener(this);
        pile_H11.addMouseListener(this);
        pile_H12.addMouseListener(this);
        pile_H13.addMouseListener(this);
        pile_H14.addMouseListener(this);
        pile_S2.addMouseListener(this);
        pile_S3.addMouseListener(this);
        pile_S4.addMouseListener(this);
        pile_S5.addMouseListener(this);
        pile_S6.addMouseListener(this);
        pile_S7.addMouseListener(this);
        pile_S8.addMouseListener(this);
        pile_S9.addMouseListener(this);
        pile_S10.addMouseListener(this);
        pile_S11.addMouseListener(this);
        pile_S12.addMouseListener(this);
        pile_S13.addMouseListener(this);
        pile_S14.addMouseListener(this);
        pile_C2_small.addMouseListener(this);
        pile_C3_small.addMouseListener(this);
        pile_C4_small.addMouseListener(this);
        pile_C5_small.addMouseListener(this);
        pile_C6_small.addMouseListener(this);
        pile_C7_small.addMouseListener(this);
        pile_C8_small.addMouseListener(this);
        pile_C9_small.addMouseListener(this);
        pile_C10_small.addMouseListener(this);
        pile_C11_small.addMouseListener(this);
        pile_C12_small.addMouseListener(this);
        pile_C13_small.addMouseListener(this);
        pile_C14_small.addMouseListener(this);
        pile_D2_small.addMouseListener(this);
        pile_D3_small.addMouseListener(this);
        pile_D4_small.addMouseListener(this);
        pile_D5_small.addMouseListener(this);
        pile_D6_small.addMouseListener(this);
        pile_D7_small.addMouseListener(this);
        pile_D8_small.addMouseListener(this);
        pile_D9_small.addMouseListener(this);
        pile_D10_small.addMouseListener(this);
        pile_D11_small.addMouseListener(this);
        pile_D12_small.addMouseListener(this);
        pile_D13_small.addMouseListener(this);
        pile_D14_small.addMouseListener(this);
        pile_H2_small.addMouseListener(this);
        pile_H3_small.addMouseListener(this);
        pile_H4_small.addMouseListener(this);
        pile_H5_small.addMouseListener(this);
        pile_H6_small.addMouseListener(this);
        pile_H7_small.addMouseListener(this);
        pile_H8_small.addMouseListener(this);
        pile_H9_small.addMouseListener(this);
        pile_H10_small.addMouseListener(this);
        pile_H11_small.addMouseListener(this);
        pile_H12_small.addMouseListener(this);
        pile_H13_small.addMouseListener(this);
        pile_H14_small.addMouseListener(this);
        pile_S2_small.addMouseListener(this);
        pile_S3_small.addMouseListener(this);
        pile_S4_small.addMouseListener(this);
        pile_S5_small.addMouseListener(this);
        pile_S6_small.addMouseListener(this);
        pile_S7_small.addMouseListener(this);
        pile_S8_small.addMouseListener(this);
        pile_S9_small.addMouseListener(this);
        pile_S10_small.addMouseListener(this);
        pile_S11_small.addMouseListener(this);
        pile_S12_small.addMouseListener(this);
        pile_S13_small.addMouseListener(this);
        pile_S14_small.addMouseListener(this);
        pile_outline1.addMouseListener(this);
        pile_outline2.addMouseListener(this);
        pile_outline3.addMouseListener(this);
        pile_top1.addMouseListener(this);
        pile_top2.addMouseListener(this);
        pile_top3.addMouseListener(this);

        // Register playerUp listeners
        playerUp_C2.addMouseListener(this);
        playerUp_C3.addMouseListener(this);
        playerUp_C4.addMouseListener(this);
        playerUp_C5.addMouseListener(this);
        playerUp_C6.addMouseListener(this);
        playerUp_C7.addMouseListener(this);
        playerUp_C8.addMouseListener(this);
        playerUp_C9.addMouseListener(this);
        playerUp_C10.addMouseListener(this);
        playerUp_C11.addMouseListener(this);
        playerUp_C12.addMouseListener(this);
        playerUp_C13.addMouseListener(this);
        playerUp_C14.addMouseListener(this);
        playerUp_D2.addMouseListener(this);
        playerUp_D3.addMouseListener(this);
        playerUp_D4.addMouseListener(this);
        playerUp_D5.addMouseListener(this);
        playerUp_D6.addMouseListener(this);
        playerUp_D7.addMouseListener(this);
        playerUp_D8.addMouseListener(this);
        playerUp_D9.addMouseListener(this);
        playerUp_D10.addMouseListener(this);
        playerUp_D11.addMouseListener(this);
        playerUp_D12.addMouseListener(this);
        playerUp_D13.addMouseListener(this);
        playerUp_D14.addMouseListener(this);
        playerUp_H2.addMouseListener(this);
        playerUp_H3.addMouseListener(this);
        playerUp_H4.addMouseListener(this);
        playerUp_H5.addMouseListener(this);
        playerUp_H6.addMouseListener(this);
        playerUp_H7.addMouseListener(this);
        playerUp_H8.addMouseListener(this);
        playerUp_H9.addMouseListener(this);
        playerUp_H10.addMouseListener(this);
        playerUp_H11.addMouseListener(this);
        playerUp_H12.addMouseListener(this);
        playerUp_H13.addMouseListener(this);
        playerUp_H14.addMouseListener(this);
        playerUp_S2.addMouseListener(this);
        playerUp_S3.addMouseListener(this);
        playerUp_S4.addMouseListener(this);
        playerUp_S5.addMouseListener(this);
        playerUp_S6.addMouseListener(this);
        playerUp_S7.addMouseListener(this);
        playerUp_S8.addMouseListener(this);
        playerUp_S9.addMouseListener(this);
        playerUp_S10.addMouseListener(this);
        playerUp_S11.addMouseListener(this);
        playerUp_S12.addMouseListener(this);
        playerUp_S13.addMouseListener(this);
        playerUp_S14.addMouseListener(this);
        playerUp_C2_small.addMouseListener(this);
        playerUp_C3_small.addMouseListener(this);
        playerUp_C4_small.addMouseListener(this);
        playerUp_C5_small.addMouseListener(this);
        playerUp_C6_small.addMouseListener(this);
        playerUp_C7_small.addMouseListener(this);
        playerUp_C8_small.addMouseListener(this);
        playerUp_C9_small.addMouseListener(this);
        playerUp_C10_small.addMouseListener(this);
        playerUp_C11_small.addMouseListener(this);
        playerUp_C12_small.addMouseListener(this);
        playerUp_C13_small.addMouseListener(this);
        playerUp_C14_small.addMouseListener(this);
        playerUp_D2_small.addMouseListener(this);
        playerUp_D3_small.addMouseListener(this);
        playerUp_D4_small.addMouseListener(this);
        playerUp_D5_small.addMouseListener(this);
        playerUp_D6_small.addMouseListener(this);
        playerUp_D7_small.addMouseListener(this);
        playerUp_D8_small.addMouseListener(this);
        playerUp_D9_small.addMouseListener(this);
        playerUp_D10_small.addMouseListener(this);
        playerUp_D11_small.addMouseListener(this);
        playerUp_D12_small.addMouseListener(this);
        playerUp_D13_small.addMouseListener(this);
        playerUp_D14_small.addMouseListener(this);
        playerUp_H2_small.addMouseListener(this);
        playerUp_H3_small.addMouseListener(this);
        playerUp_H4_small.addMouseListener(this);
        playerUp_H5_small.addMouseListener(this);
        playerUp_H6_small.addMouseListener(this);
        playerUp_H7_small.addMouseListener(this);
        playerUp_H8_small.addMouseListener(this);
        playerUp_H9_small.addMouseListener(this);
        playerUp_H10_small.addMouseListener(this);
        playerUp_H11_small.addMouseListener(this);
        playerUp_H12_small.addMouseListener(this);
        playerUp_H13_small.addMouseListener(this);
        playerUp_H14_small.addMouseListener(this);
        playerUp_S2_small.addMouseListener(this);
        playerUp_S3_small.addMouseListener(this);
        playerUp_S4_small.addMouseListener(this);
        playerUp_S5_small.addMouseListener(this);
        playerUp_S6_small.addMouseListener(this);
        playerUp_S7_small.addMouseListener(this);
        playerUp_S8_small.addMouseListener(this);
        playerUp_S9_small.addMouseListener(this);
        playerUp_S10_small.addMouseListener(this);
        playerUp_S11_small.addMouseListener(this);
        playerUp_S12_small.addMouseListener(this);
        playerUp_S13_small.addMouseListener(this);
        playerUp_S14_small.addMouseListener(this);
        playerUp_outline1.addMouseListener(this);
        playerUp_outline2.addMouseListener(this);
        playerUp_outline3.addMouseListener(this);
        playerUp_top1.addMouseListener(this);
        playerUp_top2.addMouseListener(this);
        playerUp_top3.addMouseListener(this);

        // Register playerHand listeners
        playerHand_C2.addMouseListener(this);
        playerHand_C3.addMouseListener(this);
        playerHand_C4.addMouseListener(this);
        playerHand_C5.addMouseListener(this);
        playerHand_C6.addMouseListener(this);
        playerHand_C7.addMouseListener(this);
        playerHand_C8.addMouseListener(this);
        playerHand_C9.addMouseListener(this);
        playerHand_C10.addMouseListener(this);
        playerHand_C11.addMouseListener(this);
        playerHand_C12.addMouseListener(this);
        playerHand_C13.addMouseListener(this);
        playerHand_C14.addMouseListener(this);
        playerHand_D2.addMouseListener(this);
        playerHand_D3.addMouseListener(this);
        playerHand_D4.addMouseListener(this);
        playerHand_D5.addMouseListener(this);
        playerHand_D6.addMouseListener(this);
        playerHand_D7.addMouseListener(this);
        playerHand_D8.addMouseListener(this);
        playerHand_D9.addMouseListener(this);
        playerHand_D10.addMouseListener(this);
        playerHand_D11.addMouseListener(this);
        playerHand_D12.addMouseListener(this);
        playerHand_D13.addMouseListener(this);
        playerHand_D14.addMouseListener(this);
        playerHand_H2.addMouseListener(this);
        playerHand_H3.addMouseListener(this);
        playerHand_H4.addMouseListener(this);
        playerHand_H5.addMouseListener(this);
        playerHand_H6.addMouseListener(this);
        playerHand_H7.addMouseListener(this);
        playerHand_H8.addMouseListener(this);
        playerHand_H9.addMouseListener(this);
        playerHand_H10.addMouseListener(this);
        playerHand_H11.addMouseListener(this);
        playerHand_H12.addMouseListener(this);
        playerHand_H13.addMouseListener(this);
        playerHand_H14.addMouseListener(this);
        playerHand_S2.addMouseListener(this);
        playerHand_S3.addMouseListener(this);
        playerHand_S4.addMouseListener(this);
        playerHand_S5.addMouseListener(this);
        playerHand_S6.addMouseListener(this);
        playerHand_S7.addMouseListener(this);
        playerHand_S8.addMouseListener(this);
        playerHand_S9.addMouseListener(this);
        playerHand_S10.addMouseListener(this);
        playerHand_S11.addMouseListener(this);
        playerHand_S12.addMouseListener(this);
        playerHand_S13.addMouseListener(this);
        playerHand_S14.addMouseListener(this);
        playerHand_C2_small.addMouseListener(this);
        playerHand_C3_small.addMouseListener(this);
        playerHand_C4_small.addMouseListener(this);
        playerHand_C5_small.addMouseListener(this);
        playerHand_C6_small.addMouseListener(this);
        playerHand_C7_small.addMouseListener(this);
        playerHand_C8_small.addMouseListener(this);
        playerHand_C9_small.addMouseListener(this);
        playerHand_C10_small.addMouseListener(this);
        playerHand_C11_small.addMouseListener(this);
        playerHand_C12_small.addMouseListener(this);
        playerHand_C13_small.addMouseListener(this);
        playerHand_C14_small.addMouseListener(this);
        playerHand_D2_small.addMouseListener(this);
        playerHand_D3_small.addMouseListener(this);
        playerHand_D4_small.addMouseListener(this);
        playerHand_D5_small.addMouseListener(this);
        playerHand_D6_small.addMouseListener(this);
        playerHand_D7_small.addMouseListener(this);
        playerHand_D8_small.addMouseListener(this);
        playerHand_D9_small.addMouseListener(this);
        playerHand_D10_small.addMouseListener(this);
        playerHand_D11_small.addMouseListener(this);
        playerHand_D12_small.addMouseListener(this);
        playerHand_D13_small.addMouseListener(this);
        playerHand_D14_small.addMouseListener(this);
        playerHand_H2_small.addMouseListener(this);
        playerHand_H3_small.addMouseListener(this);
        playerHand_H4_small.addMouseListener(this);
        playerHand_H5_small.addMouseListener(this);
        playerHand_H6_small.addMouseListener(this);
        playerHand_H7_small.addMouseListener(this);
        playerHand_H8_small.addMouseListener(this);
        playerHand_H9_small.addMouseListener(this);
        playerHand_H10_small.addMouseListener(this);
        playerHand_H11_small.addMouseListener(this);
        playerHand_H12_small.addMouseListener(this);
        playerHand_H13_small.addMouseListener(this);
        playerHand_H14_small.addMouseListener(this);
        playerHand_S2_small.addMouseListener(this);
        playerHand_S3_small.addMouseListener(this);
        playerHand_S4_small.addMouseListener(this);
        playerHand_S5_small.addMouseListener(this);
        playerHand_S6_small.addMouseListener(this);
        playerHand_S7_small.addMouseListener(this);
        playerHand_S8_small.addMouseListener(this);
        playerHand_S9_small.addMouseListener(this);
        playerHand_S10_small.addMouseListener(this);
        playerHand_S11_small.addMouseListener(this);
        playerHand_S12_small.addMouseListener(this);
        playerHand_S13_small.addMouseListener(this);
        playerHand_S14_small.addMouseListener(this);
        playerHand_outline1.addMouseListener(this);
        playerHand_outline2.addMouseListener(this);
        playerHand_outline3.addMouseListener(this);
        playerHand_top1.addMouseListener(this);
        playerHand_top2.addMouseListener(this);
        playerHand_top3.addMouseListener(this);
    }

    // Resizes images and reset icons
    private void resizeCards()
    {
        // Resize images
        topCardIcon = resizeImageIcon(topCardIcon, cardSizeRatio);
        topRSCardIcon = resizeImageIcon(topRSCardIcon, cardSizeRatio);
        topRSCardIconSmall = resizeImageIcon(topRSCardIconSmall, cardSizeRatio);
        cardOutlineIcon = resizeImageIcon(cardOutlineIcon, cardSizeRatio);
        imageC2  = resizeImageIcon(imageC2,  cardSizeRatio);
        imageC3  = resizeImageIcon(imageC3,  cardSizeRatio);
        imageC4  = resizeImageIcon(imageC4,  cardSizeRatio);
        imageC5  = resizeImageIcon(imageC5,  cardSizeRatio);
        imageC6  = resizeImageIcon(imageC6,  cardSizeRatio);
        imageC7  = resizeImageIcon(imageC7,  cardSizeRatio);
        imageC8  = resizeImageIcon(imageC8,  cardSizeRatio);
        imageC9  = resizeImageIcon(imageC9,  cardSizeRatio);
        imageC10 = resizeImageIcon(imageC10, cardSizeRatio);
        imageC11 = resizeImageIcon(imageC11, cardSizeRatio);
        imageC12 = resizeImageIcon(imageC12, cardSizeRatio);
        imageC13 = resizeImageIcon(imageC13, cardSizeRatio);
        imageC14 = resizeImageIcon(imageC14, cardSizeRatio);
        imageD2  = resizeImageIcon(imageD2,  cardSizeRatio);
        imageD3  = resizeImageIcon(imageD3,  cardSizeRatio);
        imageD4  = resizeImageIcon(imageD4,  cardSizeRatio);
        imageD5  = resizeImageIcon(imageD5,  cardSizeRatio);
        imageD6  = resizeImageIcon(imageD6,  cardSizeRatio);
        imageD7  = resizeImageIcon(imageD7,  cardSizeRatio);
        imageD8  = resizeImageIcon(imageD8,  cardSizeRatio);
        imageD9  = resizeImageIcon(imageD9,  cardSizeRatio);
        imageD10 = resizeImageIcon(imageD10, cardSizeRatio);
        imageD11 = resizeImageIcon(imageD11, cardSizeRatio);
        imageD12 = resizeImageIcon(imageD12, cardSizeRatio);
        imageD13 = resizeImageIcon(imageD13, cardSizeRatio);
        imageD14 = resizeImageIcon(imageD14, cardSizeRatio);
        imageH2  = resizeImageIcon(imageH2,  cardSizeRatio);
        imageH3  = resizeImageIcon(imageH3,  cardSizeRatio);
        imageH4  = resizeImageIcon(imageH4,  cardSizeRatio);
        imageH5  = resizeImageIcon(imageH5,  cardSizeRatio);
        imageH6  = resizeImageIcon(imageH6,  cardSizeRatio);
        imageH7  = resizeImageIcon(imageH7,  cardSizeRatio);
        imageH8  = resizeImageIcon(imageH8,  cardSizeRatio);
        imageH9  = resizeImageIcon(imageH9,  cardSizeRatio);
        imageH10 = resizeImageIcon(imageH10, cardSizeRatio);
        imageH11 = resizeImageIcon(imageH11, cardSizeRatio);
        imageH12 = resizeImageIcon(imageH12, cardSizeRatio);
        imageH13 = resizeImageIcon(imageH13, cardSizeRatio);
        imageH14 = resizeImageIcon(imageH14, cardSizeRatio);
        imageS2  = resizeImageIcon(imageS2,  cardSizeRatio);
        imageS3  = resizeImageIcon(imageS3,  cardSizeRatio);
        imageS4  = resizeImageIcon(imageS4,  cardSizeRatio);
        imageS5  = resizeImageIcon(imageS5,  cardSizeRatio);
        imageS6  = resizeImageIcon(imageS6,  cardSizeRatio);
        imageS7  = resizeImageIcon(imageS7,  cardSizeRatio);
        imageS8  = resizeImageIcon(imageS8,  cardSizeRatio);
        imageS9  = resizeImageIcon(imageS9,  cardSizeRatio);
        imageS10 = resizeImageIcon(imageS10, cardSizeRatio);
        imageS11 = resizeImageIcon(imageS11, cardSizeRatio);
        imageS12 = resizeImageIcon(imageS12, cardSizeRatio);
        imageS13 = resizeImageIcon(imageS13, cardSizeRatio);
        imageS14 = resizeImageIcon(imageS14, cardSizeRatio);
        imageC2_small  = resizeImageIcon(imageC2_small,  cardSizeRatio);
        imageC3_small  = resizeImageIcon(imageC3_small,  cardSizeRatio);
        imageC4_small  = resizeImageIcon(imageC4_small,  cardSizeRatio);
        imageC5_small  = resizeImageIcon(imageC5_small,  cardSizeRatio);
        imageC6_small  = resizeImageIcon(imageC6_small,  cardSizeRatio);
        imageC7_small  = resizeImageIcon(imageC7_small,  cardSizeRatio);
        imageC8_small  = resizeImageIcon(imageC8_small,  cardSizeRatio);
        imageC9_small  = resizeImageIcon(imageC9_small,  cardSizeRatio);
        imageC10_small = resizeImageIcon(imageC10_small, cardSizeRatio);
        imageC11_small = resizeImageIcon(imageC11_small, cardSizeRatio);
        imageC12_small = resizeImageIcon(imageC12_small, cardSizeRatio);
        imageC13_small = resizeImageIcon(imageC13_small, cardSizeRatio);
        imageC14_small = resizeImageIcon(imageC14_small, cardSizeRatio);
        imageD2_small  = resizeImageIcon(imageD2_small,  cardSizeRatio);
        imageD3_small  = resizeImageIcon(imageD3_small,  cardSizeRatio);
        imageD4_small  = resizeImageIcon(imageD4_small,  cardSizeRatio);
        imageD5_small  = resizeImageIcon(imageD5_small,  cardSizeRatio);
        imageD6_small  = resizeImageIcon(imageD6_small,  cardSizeRatio);
        imageD7_small  = resizeImageIcon(imageD7_small,  cardSizeRatio);
        imageD8_small  = resizeImageIcon(imageD8_small,  cardSizeRatio);
        imageD9_small  = resizeImageIcon(imageD9_small,  cardSizeRatio);
        imageD10_small = resizeImageIcon(imageD10_small, cardSizeRatio);
        imageD11_small = resizeImageIcon(imageD11_small, cardSizeRatio);
        imageD12_small = resizeImageIcon(imageD12_small, cardSizeRatio);
        imageD13_small = resizeImageIcon(imageD13_small, cardSizeRatio);
        imageD14_small = resizeImageIcon(imageD14_small, cardSizeRatio);
        imageH2_small  = resizeImageIcon(imageH2_small,  cardSizeRatio);
        imageH3_small  = resizeImageIcon(imageH3_small,  cardSizeRatio);
        imageH4_small  = resizeImageIcon(imageH4_small,  cardSizeRatio);
        imageH5_small  = resizeImageIcon(imageH5_small,  cardSizeRatio);
        imageH6_small  = resizeImageIcon(imageH6_small,  cardSizeRatio);
        imageH7_small  = resizeImageIcon(imageH7_small,  cardSizeRatio);
        imageH8_small  = resizeImageIcon(imageH8_small,  cardSizeRatio);
        imageH9_small  = resizeImageIcon(imageH9_small,  cardSizeRatio);
        imageH10_small = resizeImageIcon(imageH10_small, cardSizeRatio);
        imageH11_small = resizeImageIcon(imageH11_small, cardSizeRatio);
        imageH12_small = resizeImageIcon(imageH12_small, cardSizeRatio);
        imageH13_small = resizeImageIcon(imageH13_small, cardSizeRatio);
        imageH14_small = resizeImageIcon(imageH14_small, cardSizeRatio);
        imageS2_small  = resizeImageIcon(imageS2_small,  cardSizeRatio);
        imageS3_small  = resizeImageIcon(imageS3_small,  cardSizeRatio);
        imageS4_small  = resizeImageIcon(imageS4_small,  cardSizeRatio);
        imageS5_small  = resizeImageIcon(imageS5_small,  cardSizeRatio);
        imageS6_small  = resizeImageIcon(imageS6_small,  cardSizeRatio);
        imageS7_small  = resizeImageIcon(imageS7_small,  cardSizeRatio);
        imageS8_small  = resizeImageIcon(imageS8_small,  cardSizeRatio);
        imageS9_small  = resizeImageIcon(imageS9_small,  cardSizeRatio);
        imageS10_small = resizeImageIcon(imageS10_small, cardSizeRatio);
        imageS11_small = resizeImageIcon(imageS11_small, cardSizeRatio);
        imageS12_small = resizeImageIcon(imageS12_small, cardSizeRatio);
        imageS13_small = resizeImageIcon(imageS13_small, cardSizeRatio);
        imageS14_small = resizeImageIcon(imageS14_small, cardSizeRatio);

        // Reset images in cardImageArray to new size
        ImageIcon [] temp = { imageC2, imageC3,  imageC4,  imageC5,  imageC6,  imageC7,
                imageC8, imageC9,  imageC10, imageC11, imageC12, imageC13,
                imageC14,imageD2,  imageD3,  imageD4,  imageD5,  imageD6,
                imageD7, imageD8,  imageD9,  imageD10, imageD11, imageD12,
                imageD13,imageD14, imageH2,  imageH3,  imageH4,  imageH5,
                imageH6, imageH7,  imageH8,  imageH9,  imageH10, imageH11,
                imageH12,imageH13, imageH14, imageS2,  imageS3,  imageS4,
                imageS5, imageS6,  imageS7,  imageS8,  imageS9,  imageS10,
                imageS11,imageS12, imageS13, imageS14, imageC2_small,
                imageC3_small,     imageC4_small,      imageC5_small,
                imageC6_small,     imageC7_small,      imageC8_small,
                imageC9_small,     imageC10_small,     imageC11_small,
                imageC12_small,    imageC13_small,     imageC14_small,
                imageD2_small,     imageD3_small,      imageD4_small,
                imageD5_small,     imageD6_small,      imageD7_small,
                imageD8_small,     imageD9_small,      imageD10_small,
                imageD11_small,    imageD12_small,     imageD13_small,
                imageD14_small,    imageH2_small,      imageH3_small,
                imageH4_small,     imageH5_small,      imageH6_small,
                imageH7_small,     imageH8_small,      imageH9_small,
                imageH10_small,    imageH11_small,     imageH12_small,
                imageH13_small,    imageH14_small,     imageS2_small,
                imageS3_small,     imageS4_small,      imageS5_small,
                imageS6_small,     imageS7_small,      imageS8_small,
                imageS9_small,     imageS10_small,     imageS11_small,
                imageS12_small,    imageS13_small,     imageS14_small,
                topCardIcon,       topCardIcon,        topCardIcon,
                cardOutlineIcon,   cardOutlineIcon,    cardOutlineIcon };

        cardImageArray = temp;

        // Resize opponentHand cards
        opponentHand_C2.setIcon(topCardIcon);
        opponentHand_C3.setIcon(topCardIcon);
        opponentHand_C4.setIcon(topCardIcon);
        opponentHand_C5.setIcon(topCardIcon);
        opponentHand_C6.setIcon(topCardIcon);
        opponentHand_C7.setIcon(topCardIcon);
        opponentHand_C8.setIcon(topCardIcon);
        opponentHand_C9.setIcon(topCardIcon);
        opponentHand_C10.setIcon(topCardIcon);
        opponentHand_C11.setIcon(topCardIcon);
        opponentHand_C12.setIcon(topCardIcon);
        opponentHand_C13.setIcon(topCardIcon);
        opponentHand_C14.setIcon(topCardIcon);
        opponentHand_D2.setIcon(topCardIcon);
        opponentHand_D3.setIcon(topCardIcon);
        opponentHand_D4.setIcon(topCardIcon);
        opponentHand_D5.setIcon(topCardIcon);
        opponentHand_D6.setIcon(topCardIcon);
        opponentHand_D7.setIcon(topCardIcon);
        opponentHand_D8.setIcon(topCardIcon);
        opponentHand_D9.setIcon(topCardIcon);
        opponentHand_D10.setIcon(topCardIcon);
        opponentHand_D11.setIcon(topCardIcon);
        opponentHand_D12.setIcon(topCardIcon);
        opponentHand_D13.setIcon(topCardIcon);
        opponentHand_D14.setIcon(topCardIcon);
        opponentHand_H2.setIcon(topCardIcon);
        opponentHand_H3.setIcon(topCardIcon);
        opponentHand_H4.setIcon(topCardIcon);
        opponentHand_H5.setIcon(topCardIcon);
        opponentHand_H6.setIcon(topCardIcon);
        opponentHand_H7.setIcon(topCardIcon);
        opponentHand_H8.setIcon(topCardIcon);
        opponentHand_H9.setIcon(topCardIcon);
        opponentHand_H10.setIcon(topCardIcon);
        opponentHand_H11.setIcon(topCardIcon);
        opponentHand_H12.setIcon(topCardIcon);
        opponentHand_H13.setIcon(topCardIcon);
        opponentHand_H14.setIcon(topCardIcon);
        opponentHand_S2.setIcon(topCardIcon);
        opponentHand_S3.setIcon(topCardIcon);
        opponentHand_S4.setIcon(topCardIcon);
        opponentHand_S5.setIcon(topCardIcon);
        opponentHand_S6.setIcon(topCardIcon);
        opponentHand_S7.setIcon(topCardIcon);
        opponentHand_S8.setIcon(topCardIcon);
        opponentHand_S9.setIcon(topCardIcon);
        opponentHand_S10.setIcon(topCardIcon);
        opponentHand_S11.setIcon(topCardIcon);
        opponentHand_S12.setIcon(topCardIcon);
        opponentHand_S13.setIcon(topCardIcon);
        opponentHand_S14.setIcon(topCardIcon);
        opponentHand_C2_small.setIcon(topRSCardIcon);
        opponentHand_C3_small.setIcon(topRSCardIcon);
        opponentHand_C4_small.setIcon(topRSCardIcon);
        opponentHand_C5_small.setIcon(topRSCardIcon);
        opponentHand_C6_small.setIcon(topRSCardIcon);
        opponentHand_C7_small.setIcon(topRSCardIcon);
        opponentHand_C8_small.setIcon(topRSCardIcon);
        opponentHand_C9_small.setIcon(topRSCardIcon);
        opponentHand_C10_small.setIcon(topRSCardIcon);
        opponentHand_C11_small.setIcon(topRSCardIcon);
        opponentHand_C12_small.setIcon(topRSCardIcon);
        opponentHand_C13_small.setIcon(topRSCardIcon);
        opponentHand_C14_small.setIcon(topRSCardIcon);
        opponentHand_D2_small.setIcon(topRSCardIcon);
        opponentHand_D3_small.setIcon(topRSCardIcon);
        opponentHand_D4_small.setIcon(topRSCardIcon);
        opponentHand_D5_small.setIcon(topRSCardIcon);
        opponentHand_D6_small.setIcon(topRSCardIcon);
        opponentHand_D7_small.setIcon(topRSCardIcon);
        opponentHand_D8_small.setIcon(topRSCardIcon);
        opponentHand_D9_small.setIcon(topRSCardIcon);
        opponentHand_D10_small.setIcon(topRSCardIcon);
        opponentHand_D11_small.setIcon(topRSCardIcon);
        opponentHand_D12_small.setIcon(topRSCardIcon);
        opponentHand_D13_small.setIcon(topRSCardIcon);
        opponentHand_D14_small.setIcon(topRSCardIcon);
        opponentHand_H2_small.setIcon(topRSCardIcon);
        opponentHand_H3_small.setIcon(topRSCardIcon);
        opponentHand_H4_small.setIcon(topRSCardIcon);
        opponentHand_H5_small.setIcon(topRSCardIcon);
        opponentHand_H6_small.setIcon(topRSCardIcon);
        opponentHand_H7_small.setIcon(topRSCardIcon);
        opponentHand_H8_small.setIcon(topRSCardIcon);
        opponentHand_H9_small.setIcon(topRSCardIcon);
        opponentHand_H10_small.setIcon(topRSCardIcon);
        opponentHand_H11_small.setIcon(topRSCardIcon);
        opponentHand_H12_small.setIcon(topRSCardIcon);
        opponentHand_H13_small.setIcon(topRSCardIcon);
        opponentHand_H14_small.setIcon(topRSCardIcon);
        opponentHand_S2_small.setIcon(topRSCardIcon);
        opponentHand_S3_small.setIcon(topRSCardIcon);
        opponentHand_S4_small.setIcon(topRSCardIcon);
        opponentHand_S5_small.setIcon(topRSCardIcon);
        opponentHand_S6_small.setIcon(topRSCardIcon);
        opponentHand_S7_small.setIcon(topRSCardIcon);
        opponentHand_S8_small.setIcon(topRSCardIcon);
        opponentHand_S9_small.setIcon(topRSCardIcon);
        opponentHand_S10_small.setIcon(topRSCardIcon);
        opponentHand_S11_small.setIcon(topRSCardIcon);
        opponentHand_S12_small.setIcon(topRSCardIcon);
        opponentHand_S13_small.setIcon(topRSCardIcon);
        opponentHand_S14_small.setIcon(topRSCardIcon);
        opponentHand_outline1.setIcon(cardOutlineIcon);
        opponentHand_outline2.setIcon(cardOutlineIcon);
        opponentHand_outline3.setIcon(cardOutlineIcon);
        opponentHand_top1.setIcon(topCardIcon);
        opponentHand_top2.setIcon(topCardIcon);
        opponentHand_top3.setIcon(topCardIcon);

        // Resize opponentUp cards
        opponentUp_C2.setIcon(imageC2);
        opponentUp_C3.setIcon(imageC3);
        opponentUp_C4.setIcon(imageC4);
        opponentUp_C5.setIcon(imageC5);
        opponentUp_C6.setIcon(imageC6);
        opponentUp_C7.setIcon(imageC7);
        opponentUp_C8.setIcon(imageC8);
        opponentUp_C9.setIcon(imageC9);
        opponentUp_C10.setIcon(imageC10);
        opponentUp_C11.setIcon(imageC11);
        opponentUp_C12.setIcon(imageC12);
        opponentUp_C13.setIcon(imageC13);
        opponentUp_C14.setIcon(imageC14);
        opponentUp_D2.setIcon(imageD2);
        opponentUp_D3.setIcon(imageD3);
        opponentUp_D4.setIcon(imageD4);
        opponentUp_D5.setIcon(imageD5);
        opponentUp_D6.setIcon(imageD6);
        opponentUp_D7.setIcon(imageD7);
        opponentUp_D8.setIcon(imageD8);
        opponentUp_D9.setIcon(imageD9);
        opponentUp_D10.setIcon(imageD10);
        opponentUp_D11.setIcon(imageD11);
        opponentUp_D12.setIcon(imageD12);
        opponentUp_D13.setIcon(imageD13);
        opponentUp_D14.setIcon(imageD14);
        opponentUp_H2.setIcon(imageH2);
        opponentUp_H3.setIcon(imageH3);
        opponentUp_H4.setIcon(imageH4);
        opponentUp_H5.setIcon(imageH5);
        opponentUp_H6.setIcon(imageH6);
        opponentUp_H7.setIcon(imageH7);
        opponentUp_H8.setIcon(imageH8);
        opponentUp_H9.setIcon(imageH9);
        opponentUp_H10.setIcon(imageH10);
        opponentUp_H11.setIcon(imageH11);
        opponentUp_H12.setIcon(imageH12);
        opponentUp_H13.setIcon(imageH13);
        opponentUp_H14.setIcon(imageH14);
        opponentUp_S2.setIcon(imageS2);
        opponentUp_S3.setIcon(imageS3);
        opponentUp_S4.setIcon(imageS4);
        opponentUp_S5.setIcon(imageS5);
        opponentUp_S6.setIcon(imageS6);
        opponentUp_S7.setIcon(imageS7);
        opponentUp_S8.setIcon(imageS8);
        opponentUp_S9.setIcon(imageS9);
        opponentUp_S10.setIcon(imageS10);
        opponentUp_S11.setIcon(imageS11);
        opponentUp_S12.setIcon(imageS12);
        opponentUp_S13.setIcon(imageS13);
        opponentUp_S14.setIcon(imageS14);
        opponentUp_C2_small.setIcon(imageC2_small);
        opponentUp_C3_small.setIcon(imageC3_small);
        opponentUp_C4_small.setIcon(imageC4_small);
        opponentUp_C5_small.setIcon(imageC5_small);
        opponentUp_C6_small.setIcon(imageC6_small);
        opponentUp_C7_small.setIcon(imageC7_small);
        opponentUp_C8_small.setIcon(imageC8_small);
        opponentUp_C9_small.setIcon(imageC9_small);
        opponentUp_C10_small.setIcon(imageC10_small);
        opponentUp_C11_small.setIcon(imageC11_small);
        opponentUp_C12_small.setIcon(imageC12_small);
        opponentUp_C13_small.setIcon(imageC13_small);
        opponentUp_C14_small.setIcon(imageC14_small);
        opponentUp_D2_small.setIcon(imageD2_small);
        opponentUp_D3_small.setIcon(imageD3_small);
        opponentUp_D4_small.setIcon(imageD4_small);
        opponentUp_D5_small.setIcon(imageD5_small);
        opponentUp_D6_small.setIcon(imageD6_small);
        opponentUp_D7_small.setIcon(imageD7_small);
        opponentUp_D8_small.setIcon(imageD8_small);
        opponentUp_D9_small.setIcon(imageD9_small);
        opponentUp_D10_small.setIcon(imageD10_small);
        opponentUp_D11_small.setIcon(imageD11_small);
        opponentUp_D12_small.setIcon(imageD12_small);
        opponentUp_D13_small.setIcon(imageD13_small);
        opponentUp_D14_small.setIcon(imageD14_small);
        opponentUp_H2_small.setIcon(imageH2_small);
        opponentUp_H3_small.setIcon(imageH3_small);
        opponentUp_H4_small.setIcon(imageH4_small);
        opponentUp_H5_small.setIcon(imageH5_small);
        opponentUp_H6_small.setIcon(imageH6_small);
        opponentUp_H7_small.setIcon(imageH7_small);
        opponentUp_H8_small.setIcon(imageH8_small);
        opponentUp_H9_small.setIcon(imageH9_small);
        opponentUp_H10_small.setIcon(imageH10_small);
        opponentUp_H11_small.setIcon(imageH11_small);
        opponentUp_H12_small.setIcon(imageH12_small);
        opponentUp_H13_small.setIcon(imageH13_small);
        opponentUp_H14_small.setIcon(imageH14_small);
        opponentUp_S2_small.setIcon(imageS2_small);
        opponentUp_S3_small.setIcon(imageS3_small);
        opponentUp_S4_small.setIcon(imageS4_small);
        opponentUp_S5_small.setIcon(imageS5_small);
        opponentUp_S6_small.setIcon(imageS6_small);
        opponentUp_S7_small.setIcon(imageS7_small);
        opponentUp_S8_small.setIcon(imageS8_small);
        opponentUp_S9_small.setIcon(imageS9_small);
        opponentUp_S10_small.setIcon(imageS10_small);
        opponentUp_S11_small.setIcon(imageS11_small);
        opponentUp_S12_small.setIcon(imageS12_small);
        opponentUp_S13_small.setIcon(imageS13_small);
        opponentUp_S14_small.setIcon(imageS14_small);
        opponentUp_outline1.setIcon(cardOutlineIcon);
        opponentUp_outline2.setIcon(cardOutlineIcon);
        opponentUp_outline3.setIcon(cardOutlineIcon);
        opponentUp_top1.setIcon(topCardIcon);
        opponentUp_top2.setIcon(topCardIcon);
        opponentUp_top3.setIcon(topCardIcon);

        // Resize deck card
        deck_top.setIcon(topCardIcon);
        deck_outline.setIcon(cardOutlineIcon);

        // Resize pile cards
        pile_C2.setIcon(imageC2);
        pile_C3.setIcon(imageC3);
        pile_C4.setIcon(imageC4);
        pile_C5.setIcon(imageC5);
        pile_C6.setIcon(imageC6);
        pile_C7.setIcon(imageC7);
        pile_C8.setIcon(imageC8);
        pile_C9.setIcon(imageC9);
        pile_C10.setIcon(imageC10);
        pile_C11.setIcon(imageC11);
        pile_C12.setIcon(imageC12);
        pile_C13.setIcon(imageC13);
        pile_C14.setIcon(imageC14);
        pile_D2.setIcon(imageD2);
        pile_D3.setIcon(imageD3);
        pile_D4.setIcon(imageD4);
        pile_D5.setIcon(imageD5);
        pile_D6.setIcon(imageD6);
        pile_D7.setIcon(imageD7);
        pile_D8.setIcon(imageD8);
        pile_D9.setIcon(imageD9);
        pile_D10.setIcon(imageD10);
        pile_D11.setIcon(imageD11);
        pile_D12.setIcon(imageD12);
        pile_D13.setIcon(imageD13);
        pile_D14.setIcon(imageD14);
        pile_H2.setIcon(imageH2);
        pile_H3.setIcon(imageH3);
        pile_H4.setIcon(imageH4);
        pile_H5.setIcon(imageH5);
        pile_H6.setIcon(imageH6);
        pile_H7.setIcon(imageH7);
        pile_H8.setIcon(imageH8);
        pile_H9.setIcon(imageH9);
        pile_H10.setIcon(imageH10);
        pile_H11.setIcon(imageH11);
        pile_H12.setIcon(imageH12);
        pile_H13.setIcon(imageH13);
        pile_H14.setIcon(imageH14);
        pile_S2.setIcon(imageS2);
        pile_S3.setIcon(imageS3);
        pile_S4.setIcon(imageS4);
        pile_S5.setIcon(imageS5);
        pile_S6.setIcon(imageS6);
        pile_S7.setIcon(imageS7);
        pile_S8.setIcon(imageS8);
        pile_S9.setIcon(imageS9);
        pile_S10.setIcon(imageS10);
        pile_S11.setIcon(imageS11);
        pile_S12.setIcon(imageS12);
        pile_S13.setIcon(imageS13);
        pile_S14.setIcon(imageS14);
        pile_C2_small.setIcon(imageC2_small);
        pile_C3_small.setIcon(imageC3_small);
        pile_C4_small.setIcon(imageC4_small);
        pile_C5_small.setIcon(imageC5_small);
        pile_C6_small.setIcon(imageC6_small);
        pile_C7_small.setIcon(imageC7_small);
        pile_C8_small.setIcon(imageC8_small);
        pile_C9_small.setIcon(imageC9_small);
        pile_C10_small.setIcon(imageC10_small);
        pile_C11_small.setIcon(imageC11_small);
        pile_C12_small.setIcon(imageC12_small);
        pile_C13_small.setIcon(imageC13_small);
        pile_C14_small.setIcon(imageC14_small);
        pile_D2_small.setIcon(imageD2_small);
        pile_D3_small.setIcon(imageD3_small);
        pile_D4_small.setIcon(imageD4_small);
        pile_D5_small.setIcon(imageD5_small);
        pile_D6_small.setIcon(imageD6_small);
        pile_D7_small.setIcon(imageD7_small);
        pile_D8_small.setIcon(imageD8_small);
        pile_D9_small.setIcon(imageD9_small);
        pile_D10_small.setIcon(imageD10_small);
        pile_D11_small.setIcon(imageD11_small);
        pile_D12_small.setIcon(imageD12_small);
        pile_D13_small.setIcon(imageD13_small);
        pile_D14_small.setIcon(imageD14_small);
        pile_H2_small.setIcon(imageH2_small);
        pile_H3_small.setIcon(imageH3_small);
        pile_H4_small.setIcon(imageH4_small);
        pile_H5_small.setIcon(imageH5_small);
        pile_H6_small.setIcon(imageH6_small);
        pile_H7_small.setIcon(imageH7_small);
        pile_H8_small.setIcon(imageH8_small);
        pile_H9_small.setIcon(imageH9_small);
        pile_H10_small.setIcon(imageH10_small);
        pile_H11_small.setIcon(imageH11_small);
        pile_H12_small.setIcon(imageH12_small);
        pile_H13_small.setIcon(imageH13_small);
        pile_H14_small.setIcon(imageH14_small);
        pile_S2_small.setIcon(imageS2_small);
        pile_S3_small.setIcon(imageS3_small);
        pile_S4_small.setIcon(imageS4_small);
        pile_S5_small.setIcon(imageS5_small);
        pile_S6_small.setIcon(imageS6_small);
        pile_S7_small.setIcon(imageS7_small);
        pile_S8_small.setIcon(imageS8_small);
        pile_S9_small.setIcon(imageS9_small);
        pile_S10_small.setIcon(imageS10_small);
        pile_S11_small.setIcon(imageS11_small);
        pile_S12_small.setIcon(imageS12_small);
        pile_S13_small.setIcon(imageS13_small);
        pile_S14_small.setIcon(imageS14_small);
        pile_outline1.setIcon(cardOutlineIcon);
        pile_outline2.setIcon(cardOutlineIcon);
        pile_outline3.setIcon(cardOutlineIcon);
        pile_top1.setIcon(topCardIcon);
        pile_top2.setIcon(topCardIcon);
        pile_top3.setIcon(topCardIcon);

        // Resize playerUp
        playerUp_C2.setIcon(imageC2);
        playerUp_C3.setIcon(imageC3);
        playerUp_C4.setIcon(imageC4);
        playerUp_C5.setIcon(imageC5);
        playerUp_C6.setIcon(imageC6);
        playerUp_C7.setIcon(imageC7);
        playerUp_C8.setIcon(imageC8);
        playerUp_C9.setIcon(imageC9);
        playerUp_C10.setIcon(imageC10);
        playerUp_C11.setIcon(imageC11);
        playerUp_C12.setIcon(imageC12);
        playerUp_C13.setIcon(imageC13);
        playerUp_C14.setIcon(imageC14);
        playerUp_D2.setIcon(imageD2);
        playerUp_D3.setIcon(imageD3);
        playerUp_D4.setIcon(imageD4);
        playerUp_D5.setIcon(imageD5);
        playerUp_D6.setIcon(imageD6);
        playerUp_D7.setIcon(imageD7);
        playerUp_D8.setIcon(imageD8);
        playerUp_D9.setIcon(imageD9);
        playerUp_D10.setIcon(imageD10);
        playerUp_D11.setIcon(imageD11);
        playerUp_D12.setIcon(imageD12);
        playerUp_D13.setIcon(imageD13);
        playerUp_D14.setIcon(imageD14);
        playerUp_H2.setIcon(imageH2);
        playerUp_H3.setIcon(imageH3);
        playerUp_H4.setIcon(imageH4);
        playerUp_H5.setIcon(imageH5);
        playerUp_H6.setIcon(imageH6);
        playerUp_H7.setIcon(imageH7);
        playerUp_H8.setIcon(imageH8);
        playerUp_H9.setIcon(imageH9);
        playerUp_H10.setIcon(imageH10);
        playerUp_H11.setIcon(imageH11);
        playerUp_H12.setIcon(imageH12);
        playerUp_H13.setIcon(imageH13);
        playerUp_H14.setIcon(imageH14);
        playerUp_S2.setIcon(imageS2);
        playerUp_S3.setIcon(imageS3);
        playerUp_S4.setIcon(imageS4);
        playerUp_S5.setIcon(imageS5);
        playerUp_S6.setIcon(imageS6);
        playerUp_S7.setIcon(imageS7);
        playerUp_S8.setIcon(imageS8);
        playerUp_S9.setIcon(imageS9);
        playerUp_S10.setIcon(imageS10);
        playerUp_S11.setIcon(imageS11);
        playerUp_S12.setIcon(imageS12);
        playerUp_S13.setIcon(imageS13);
        playerUp_S14.setIcon(imageS14);
        playerUp_C2_small.setIcon(imageC2_small);
        playerUp_C3_small.setIcon(imageC3_small);
        playerUp_C4_small.setIcon(imageC4_small);
        playerUp_C5_small.setIcon(imageC5_small);
        playerUp_C6_small.setIcon(imageC6_small);
        playerUp_C7_small.setIcon(imageC7_small);
        playerUp_C8_small.setIcon(imageC8_small);
        playerUp_C9_small.setIcon(imageC9_small);
        playerUp_C10_small.setIcon(imageC10_small);
        playerUp_C11_small.setIcon(imageC11_small);
        playerUp_C12_small.setIcon(imageC12_small);
        playerUp_C13_small.setIcon(imageC13_small);
        playerUp_C14_small.setIcon(imageC14_small);
        playerUp_D2_small.setIcon(imageD2_small);
        playerUp_D3_small.setIcon(imageD3_small);
        playerUp_D4_small.setIcon(imageD4_small);
        playerUp_D5_small.setIcon(imageD5_small);
        playerUp_D6_small.setIcon(imageD6_small);
        playerUp_D7_small.setIcon(imageD7_small);
        playerUp_D8_small.setIcon(imageD8_small);
        playerUp_D9_small.setIcon(imageD9_small);
        playerUp_D10_small.setIcon(imageD10_small);
        playerUp_D11_small.setIcon(imageD11_small);
        playerUp_D12_small.setIcon(imageD12_small);
        playerUp_D13_small.setIcon(imageD13_small);
        playerUp_D14_small.setIcon(imageD14_small);
        playerUp_H2_small.setIcon(imageH2_small);
        playerUp_H3_small.setIcon(imageH3_small);
        playerUp_H4_small.setIcon(imageH4_small);
        playerUp_H5_small.setIcon(imageH5_small);
        playerUp_H6_small.setIcon(imageH6_small);
        playerUp_H7_small.setIcon(imageH7_small);
        playerUp_H8_small.setIcon(imageH8_small);
        playerUp_H9_small.setIcon(imageH9_small);
        playerUp_H10_small.setIcon(imageH10_small);
        playerUp_H11_small.setIcon(imageH11_small);
        playerUp_H12_small.setIcon(imageH12_small);
        playerUp_H13_small.setIcon(imageH13_small);
        playerUp_H14_small.setIcon(imageH14_small);
        playerUp_S2_small.setIcon(imageS2_small);
        playerUp_S3_small.setIcon(imageS3_small);
        playerUp_S4_small.setIcon(imageS4_small);
        playerUp_S5_small.setIcon(imageS5_small);
        playerUp_S6_small.setIcon(imageS6_small);
        playerUp_S7_small.setIcon(imageS7_small);
        playerUp_S8_small.setIcon(imageS8_small);
        playerUp_S9_small.setIcon(imageS9_small);
        playerUp_S10_small.setIcon(imageS10_small);
        playerUp_S11_small.setIcon(imageS11_small);
        playerUp_S12_small.setIcon(imageS12_small);
        playerUp_S13_small.setIcon(imageS13_small);
        playerUp_S14_small.setIcon(imageS14_small);
        playerUp_outline1.setIcon(cardOutlineIcon);
        playerUp_outline2.setIcon(cardOutlineIcon);
        playerUp_outline3.setIcon(cardOutlineIcon);
        playerUp_top1.setIcon(topCardIcon);
        playerUp_top2.setIcon(topCardIcon);
        playerUp_top3.setIcon(topCardIcon);

        // Resize playerHand cards
        playerHand_C2.setIcon(imageC2);
        playerHand_C3.setIcon(imageC3);
        playerHand_C4.setIcon(imageC4);
        playerHand_C5.setIcon(imageC5);
        playerHand_C6.setIcon(imageC6);
        playerHand_C7.setIcon(imageC7);
        playerHand_C8.setIcon(imageC8);
        playerHand_C9.setIcon(imageC9);
        playerHand_C10.setIcon(imageC10);
        playerHand_C11.setIcon(imageC11);
        playerHand_C12.setIcon(imageC12);
        playerHand_C13.setIcon(imageC13);
        playerHand_C14.setIcon(imageC14);
        playerHand_D2.setIcon(imageD2);
        playerHand_D3.setIcon(imageD3);
        playerHand_D4.setIcon(imageD4);
        playerHand_D5.setIcon(imageD5);
        playerHand_D6.setIcon(imageD6);
        playerHand_D7.setIcon(imageD7);
        playerHand_D8.setIcon(imageD8);
        playerHand_D9.setIcon(imageD9);
        playerHand_D10.setIcon(imageD10);
        playerHand_D11.setIcon(imageD11);
        playerHand_D12.setIcon(imageD12);
        playerHand_D13.setIcon(imageD13);
        playerHand_D14.setIcon(imageD14);
        playerHand_H2.setIcon(imageH2);
        playerHand_H3.setIcon(imageH3);
        playerHand_H4.setIcon(imageH4);
        playerHand_H5.setIcon(imageH5);
        playerHand_H6.setIcon(imageH6);
        playerHand_H7.setIcon(imageH7);
        playerHand_H8.setIcon(imageH8);
        playerHand_H9.setIcon(imageH9);
        playerHand_H10.setIcon(imageH10);
        playerHand_H11.setIcon(imageH11);
        playerHand_H12.setIcon(imageH12);
        playerHand_H13.setIcon(imageH13);
        playerHand_H14.setIcon(imageH14);
        playerHand_S2.setIcon(imageS2);
        playerHand_S3.setIcon(imageS3);
        playerHand_S4.setIcon(imageS4);
        playerHand_S5.setIcon(imageS5);
        playerHand_S6.setIcon(imageS6);
        playerHand_S7.setIcon(imageS7);
        playerHand_S8.setIcon(imageS8);
        playerHand_S9.setIcon(imageS9);
        playerHand_S10.setIcon(imageS10);
        playerHand_S11.setIcon(imageS11);
        playerHand_S12.setIcon(imageS12);
        playerHand_S13.setIcon(imageS13);
        playerHand_S14.setIcon(imageS14);
        playerHand_C2_small.setIcon(imageC2_small);
        playerHand_C3_small.setIcon(imageC3_small);
        playerHand_C4_small.setIcon(imageC4_small);
        playerHand_C5_small.setIcon(imageC5_small);
        playerHand_C6_small.setIcon(imageC6_small);
        playerHand_C7_small.setIcon(imageC7_small);
        playerHand_C8_small.setIcon(imageC8_small);
        playerHand_C9_small.setIcon(imageC9_small);
        playerHand_C10_small.setIcon(imageC10_small);
        playerHand_C11_small.setIcon(imageC11_small);
        playerHand_C12_small.setIcon(imageC12_small);
        playerHand_C13_small.setIcon(imageC13_small);
        playerHand_C14_small.setIcon(imageC14_small);
        playerHand_D2_small.setIcon(imageD2_small);
        playerHand_D3_small.setIcon(imageD3_small);
        playerHand_D4_small.setIcon(imageD4_small);
        playerHand_D5_small.setIcon(imageD5_small);
        playerHand_D6_small.setIcon(imageD6_small);
        playerHand_D7_small.setIcon(imageD7_small);
        playerHand_D8_small.setIcon(imageD8_small);
        playerHand_D9_small.setIcon(imageD9_small);
        playerHand_D10_small.setIcon(imageD10_small);
        playerHand_D11_small.setIcon(imageD11_small);
        playerHand_D12_small.setIcon(imageD12_small);
        playerHand_D13_small.setIcon(imageD13_small);
        playerHand_D14_small.setIcon(imageD14_small);
        playerHand_H2_small.setIcon(imageH2_small);
        playerHand_H3_small.setIcon(imageH3_small);
        playerHand_H4_small.setIcon(imageH4_small);
        playerHand_H5_small.setIcon(imageH5_small);
        playerHand_H6_small.setIcon(imageH6_small);
        playerHand_H7_small.setIcon(imageH7_small);
        playerHand_H8_small.setIcon(imageH8_small);
        playerHand_H9_small.setIcon(imageH9_small);
        playerHand_H10_small.setIcon(imageH10_small);
        playerHand_H11_small.setIcon(imageH11_small);
        playerHand_H12_small.setIcon(imageH12_small);
        playerHand_H13_small.setIcon(imageH13_small);
        playerHand_H14_small.setIcon(imageH14_small);
        playerHand_S2_small.setIcon(imageS2_small);
        playerHand_S3_small.setIcon(imageS3_small);
        playerHand_S4_small.setIcon(imageS4_small);
        playerHand_S5_small.setIcon(imageS5_small);
        playerHand_S6_small.setIcon(imageS6_small);
        playerHand_S7_small.setIcon(imageS7_small);
        playerHand_S8_small.setIcon(imageS8_small);
        playerHand_S9_small.setIcon(imageS9_small);
        playerHand_S10_small.setIcon(imageS10_small);
        playerHand_S11_small.setIcon(imageS11_small);
        playerHand_S12_small.setIcon(imageS12_small);
        playerHand_S13_small.setIcon(imageS13_small);
        playerHand_S14_small.setIcon(imageS14_small);
        playerHand_outline1.setIcon(cardOutlineIcon);
        playerHand_outline2.setIcon(cardOutlineIcon);
        playerHand_outline3.setIcon(cardOutlineIcon);
        playerHand_top1.setIcon(topCardIcon);
        playerHand_top2.setIcon(topCardIcon);
        playerHand_top3.setIcon(topCardIcon);
    }

    // Resizes imageIcon to a percentage between 0 and 1
    private ImageIcon resizeImageIcon(ImageIcon icon, double percent)
    {
        if(percent < 0 || percent > 1)
        { return icon; }

        // Get current image size
        int currentW = icon.getIconWidth();
        int currentH = icon.getIconHeight();

        // Calculate new image size
        double newWdouble = currentW*percent;
        double newHdouble = currentH*percent;
        int newW = (int)newWdouble;
        int newH = (int)newHdouble;

        // Resize image and return
        Image img = icon.getImage();
        BufferedImage bi = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, newW, newH, null, null);
        icon = new ImageIcon(bi);
        return icon;
    }

    // Sends a new message to the messagePanels
    private void sendMessage(String newMessage)
    {
        // Get messages currently displaying on messagPanels
        String message4 = messagePanel4.getText();
        String message3 = messagePanel3.getText();
        String message2 = messagePanel2.getText();
        String message1 = messagePanel1.getText();

        // Place old and new messages on appropriate panels
        messagePanel5.setText(message4);
        messagePanel4.setText(message3);
        messagePanel3.setText(message2);
        messagePanel2.setText(message1);
        messagePanel1.setText(newMessage);
    }

    // Updates the winLossPanel
    private void setWinLossPanel()
    {
        String stringWins = Integer.toString(wins);
        String stringlosses = Integer.toString(losses);
        winLossPanel.setText("Wins: " + stringWins +
                "    Losses: " + stringlosses + " ");
        winLossPanel.revalidate();
        winLossPanel.repaint();
    }

    // Updates the cards displayed for a panel as indicated by int n
    //		n==0 - opponentHandCards
    //		n==1 - opponentUpCards
    //		n==2 - opponentDownCards
    //		n==3 - pileCards
    //		n==4 - playerDownCards
    //		n==5 - playerUpCards
    //		n==6 - playerHandCards
    //		n==7 - deckCards
    //    n==8 - UPDATE ALL!!!
    private void updateDisplayedCards(int n)
    {
        // Sort hands
        opponentHandCards = sortCardArrayList(opponentHandCards);
        playerHandCards = sortCardArrayList(playerHandCards);

        // Update opponentHandCards
        if(n==0 || n==8)
        {
            int size = opponentHandCards.size();
            if(size == 0)
            {
                opponentHandPanel.removeAll();
                opponentHandPanel.add(opponentHandArray[107]);
                opponentHandPanel.revalidate();
                opponentHandPanel.repaint();
            }
            else
            {
                opponentHandPanel.removeAll();
                for(int i=0; i<size-1; i++)
                {
                    int tempIndex = (Integer)opponentHandCards.get(i);
                    tempIndex = tempIndex + 52;
                    Card tempCard = opponentHandArray[tempIndex];
                    opponentHandPanel.add(tempCard);
                }
                Card tempCard = opponentHandArray[(Integer)opponentHandCards.get(size-1)];
                opponentHandPanel.add(tempCard);
                opponentHandPanel.revalidate();
                opponentHandPanel.repaint();
            }
        }

        // Update opponentUpCards
        if(n==1 || n==8)
        {
            int size = opponentUpCards.size();
            if(size == 0)
            {
                opponentUpPanel.removeAll();
                opponentUpPanel.add(opponentUpArray[107]);
                opponentUpPanel.add(opponentUpArray[108]);
                opponentUpPanel.add(opponentUpArray[109]);
                opponentUpPanel.revalidate();
                opponentUpPanel.repaint();

                // Place down cards, if any
                size = opponentDownCards.size();
                if(size != 0)
                {
                    opponentUpPanel.removeAll();
                    opponentUpPanel.add(opponentUpArray[104]);
                    opponentUpPanel.add(opponentUpArray[105]);
                    opponentUpPanel.add(opponentUpArray[106]);
                    opponentUpPanel.revalidate();
                    opponentUpPanel.repaint();
                }
            }
            else
            {
                opponentUpPanel.removeAll();
                for(int i=0; i<size; i++)
                {
                    Card tempCard = opponentUpArray[(Integer)opponentUpCards.get(i)];
                    addIconsToPanel(topRSCardIcon, 1, opponentUpPanel);
                    opponentUpPanel.add(tempCard);
                }
                opponentUpPanel.revalidate();
                opponentUpPanel.repaint();
            }
        }

        // Update pileCards
        if(n==3 || n==8)
        {
            int size = pileCards.size();
            if(size == 0)
            {
                pilePanel.removeAll();
                pilePanel.add(pileArray[107]);
                pilePanel.revalidate();
                pilePanel.repaint();
            }
            else
            {
                pilePanel.removeAll();
                for(int i=0; i<size-1; i++)
                {
                    int tempIndex = (Integer)pileCards.get(i);
                    tempIndex = tempIndex + 52;
                    Card tempCard = pileArray[tempIndex];
                    pilePanel.add(tempCard);
                }
                Card tempCard = pileArray[(Integer)pileCards.get(size-1)];
                pilePanel.add(tempCard);
                pilePanel.revalidate();
                pilePanel.repaint();
            }
        }

        // Update playerUpCards
        if(n==5 || n==8)
        {
            int size = playerUpCards.size();
            if(size == 0)
            {
                playerUpPanel.removeAll();
                playerUpPanel.add(playerUpArray[107]);
                playerUpPanel.add(playerUpArray[108]);
                playerUpPanel.add(playerUpArray[109]);
                playerUpPanel.revalidate();
                playerUpPanel.repaint();

                // Place down cards, if any
                size = playerDownCards.size();
                if(size != 0)
                {
                    playerUpPanel.removeAll();
                    playerUpPanel.add(playerUpArray[104]);
                    playerUpPanel.add(playerUpArray[105]);
                    playerUpPanel.add(playerUpArray[106]);
                    playerUpPanel.revalidate();
                    playerUpPanel.repaint();
                }
            }
            else
            {
                playerUpPanel.removeAll();
                for(int i=0; i<size; i++)
                {
                    Card tempCard = playerUpArray[(Integer)playerUpCards.get(i)];
                    addIconsToPanel(topRSCardIcon, 1, playerUpPanel);
                    playerUpPanel.add(tempCard);
                }
                playerUpPanel.revalidate();
                playerUpPanel.repaint();
            }
        }

        // Update playerHandCards
        if(n==6 || n==8)
        {
            int size = playerHandCards.size();
            if(size == 0)
            {
                playerHandPanel.removeAll();
                playerHandPanel.add(playerHandArray[107]);
                playerHandPanel.revalidate();
                playerHandPanel.repaint();
            }
            else
            {
                playerHandPanel.removeAll();
                for(int i=0; i<size-1; i++)
                {
                    int tempIndex = (Integer)playerHandCards.get(i);
                    tempIndex = tempIndex + 52;
                    Card tempCard = playerHandArray[tempIndex];
                    playerHandPanel.add(tempCard);
                }
                Card tempCard = playerHandArray[(Integer)playerHandCards.get(size-1)];
                playerHandPanel.add(tempCard);
                playerHandPanel.revalidate();
                playerHandPanel.repaint();
            }
        }

        // Update deckCards
        if(n==7 || n==8)
        {
            int size = deckCards.size();
            if(size == 0)
            {
                deckPanel.removeAll();
                deckPanel.add(deck_outline);
                deckPanel.revalidate();
                deckPanel.repaint();
            }
            else
            {
                deckPanel.removeAll();
                if(size > 10)
                {
                    addIconsToPanel(topRSCardIconSmall, 10, deckPanel);
                }
                else if(size < 5)
                {
                    addIconsToPanel(topRSCardIcon, size-1, deckPanel);
                }
                else
                {
                    addIconsToPanel(topRSCardIconSmall, size-1, deckPanel);
                }
                deckPanel.add(deck_top);
                deckPanel.revalidate();
                deckPanel.repaint();
            }
        }

        // Update emptyPanelHide
        if(n==7 || n==8)
        {
            int size = deckCards.size();
            if(size == 0)
            {
                emptyPanelHide.removeAll();
                addIconsToPanel(cardOutlineIcon, 1, emptyPanelHide);
                emptyPanelHide.revalidate();
                emptyPanelHide.repaint();
            }
            else
            {
                emptyPanelHide.removeAll();
                if(size > 10)
                {
                    addIconsToPanel(topRSCardIconSmall, 10, emptyPanelHide);
                }
                else if(size < 5)
                {
                    addIconsToPanel(topRSCardIcon, size-1, emptyPanelHide);
                }
                else
                {
                    addIconsToPanel(topRSCardIconSmall, size-1, emptyPanelHide);
                }
                addIconsToPanel(topCardIcon, 1, emptyPanelHide);
                emptyPanelHide.revalidate();
                emptyPanelHide.repaint();
            }
        }
        CardLayout cl = (CardLayout)(emptyPanel.getLayout());
        cl.show(emptyPanel, "blank panel");
    }

    // Updates setDiffMenu to the current difficulty setting
    private void updateSetDiffMenu()
    {
        if(difficulty == 0) // easy
        {
            setDiffMenuItem_E.setSelected(true);
        }
        else // hard
        {
            setDiffMenuItem_H.setSelected(true);
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //																												//
    // FILE METHODS																							//
    //																												//
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    // Creates a data file needed to store game information
    // The data file is a *.txt file and can be opened up with a text editor
    // - Line 1: wins
    // - Line 2: losses
    // - Line 3: difficulty
    // - endLine
    private void createDataFile(File inFile)
    {
        try
        {
            // Create a writer to write to the file
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(inFile));

            // Write to file
            fileWriter.write("0");		fileWriter.newLine(); // no wins
            fileWriter.write("0");		fileWriter.newLine(); // no losses
            fileWriter.write("0");		fileWriter.newLine(); // easy difficulty

            // Close writer
            fileWriter.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Imports a currently existing data file needed to store game information
    private void importDataFile(File inFile)
    {
        try
        {
            // Create a writer to write to the file
            BufferedReader fileReader = new BufferedReader(new FileReader(inFile));

            // Read in a line and test for the end of the file
            String line;
            line = fileReader.readLine();
            wins = Integer.parseInt(line);
            line = fileReader.readLine();
            losses = Integer.parseInt(line);
            line = fileReader.readLine();
            difficulty = Integer.parseInt(line);

            // Close reader
            fileReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Opens "American Game Rules.pdf" using the platform's default program
    private void openGameRulesPDF()
    {
        // Open using Runtime.exec()
        try
        {
            // Check if file exists
            boolean fileExists = rulesFile.exists();

            // Open file
            if(fileExists)
            {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " +
                        curDir + "\\" + rulesFileName);
            }
            else
            {
                sendMessage("Could not find \"" + rulesFileName + "\"");
            }
        }
        catch(Exception e)
        {
            sendMessage("Unable to open \"" + rulesFileName + "\"");
        }
    }

    // Saves current game information to a data file
    private void saveDataFile(File inFile)
    {
        try
        {
            // Create a writer to write to the file
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(inFile));

            // Write to file
            fileWriter.write(Integer.toString(wins));			fileWriter.newLine();
            fileWriter.write(Integer.toString(losses));		fileWriter.newLine();
            fileWriter.write(Integer.toString(difficulty));	fileWriter.newLine();

            // Close writer
            fileWriter.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////
    //																												//
    // BUTTON AND MOUSE METHODS																			//
    //																												//
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    // ActionListener Event
    public void actionPerformed(ActionEvent e)
    {
        // Test for DEAL MENU button
        if(e.getActionCommand().equals("Deal New Game"))
        {
            beginGame();
        }

        // Test for EXIT MENU button
        if(e.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }

        // Test for EASY DIFF MENU button
        if(e.getActionCommand().equals("Easy"))
        {
            difficulty = 0;
            updateSetDiffMenu();
            saveDataFile(dataFile);
        }

        // Test for HARD DIFF MENU button
        if(e.getActionCommand().equals("Hard"))
        {
            difficulty = 1;
            updateSetDiffMenu();
            saveDataFile(dataFile);
        }

        // Test for CLEAR WINS & LOSSES MENU button
        if(e.getActionCommand().equals("Clear Wins & Losses"))
        {
            wins = 0;
            losses = 0;
            setWinLossPanel();
            saveDataFile(dataFile);
        }

        // Test for RULES MENU button
        if(e.getActionCommand().equals("View Game Rules"))
        {
            // Open up "American Game Rules.pdf"
            openGameRulesPDF();
        }

        // Test for DEAL button
        if(e.getActionCommand().equals("Deal!"))
        {
            beginGame();
        }

        // Test for RULES button
        if(e.getActionCommand().equals("Rules"))
        {
            // Open up "American Game Rules.pdf"
            openGameRulesPDF();
        }
    }

    // Mouse Event: Mouse Clicked (pressed + released)
    public void mouseClicked(MouseEvent e)
    {
        // Get card component
        Card comp = (Card)e.getComponent();

        // Test if game has begun
        if(!gameBegun)
        {
            // GAME HAS NOT BEGUN!!!!
            // Test for deal click
            if(e.getSource()==pile_top1)
            {
                beginGame();
            }
            else
            {
                clearMessages();
                sendMessage("Click DEAL! to start a new game!");
                sendMessage(" ");
                sendMessage(" ");
            }
        }
        else
        {
            // GAME HAS BEGUN!!!!
            executePlayerTurn(e, comp);
        }
    }

    // Mouse Event: Mouse Entered Component
    public void mouseEntered(MouseEvent e)
    {
        // Get component
        Card comp = (Card)e.getComponent();

        // Get component name
        String compName = comp.getLongName();

        if(compName != "top card" && compName != "card outline")
        {
            // Set cursorMessage to compName
            cursorMessage.setText(comp.getLongName());
        }
    }

    // Mouse Event: Mouse Exited Component
    public void mouseExited(MouseEvent e)
    {
        // Set text to empty once the cursor leaves the area
        cursorMessage.setText("");
    }

    // Mouse Event: Mouse Pressed
    public void mousePressed(MouseEvent e)
    {}

    // Mouse Event: Mouse Released
    public void mouseReleased(MouseEvent e)
    {}


    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////
    //																												//
    // GAME LOGIC METHODS - controls the flow of the game logic									//
    //																												//
    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////	
    //////////////////////////////////////////////////////////////////////////////////////
    private void beginGame()
    {
        // Begin game
        gameBegun = true;
        playersFirstMove = true;

        // Clear message panel
        clearMessages();

        // Initialize playerState
        playerState = 1;

        // Deal out cards
        dealCardsAndDisplay();

        // Find the lowest card and display
        playerTurn = selectLowestCardAndDisplay();

        // If opponent's turn, execute opponent's turn
        if(!playerTurn)
        {
            executeOpponentTurn();
        }
    }

    // Clears the table and sets up the playing area with a single deck
    private void setupGameArea()
    {
        // Update winLossPanel and difficulty
        setWinLossPanel();
        updateSetDiffMenu();

        // Add deck image and blank spaces
        addIconsToPanel(cardOutlineIcon, 1, opponentHandPanel);
        addIconsToPanel(cardOutlineIcon, 3, opponentUpPanel);
        addIconsToPanel(cardOutlineIcon, 3, playerUpPanel);
        addIconsToPanel(cardOutlineIcon, 1, playerHandPanel);
        addIconsToPanel(topRSCardIconSmall, 10, pilePanel);
        pilePanel.add(pile_top1);

        // Add message to panel
        clearMessages();
        sendMessage("CLICK THE DECK TO BEGIN!");
        sendMessage(" ");
        sendMessage("Game design by Matt G. Latas");
        sendMessage("Program design and implementation by Nick C. Schnack");

        // Initialize global state variables
        gameBegun = false;
    }

    // A method to deal out cards and then display them appropriately
    private void dealCardsAndDisplay()
    {
        // Get a deck
        int [] deck = getDeck();

        // Shuffle deck
        deck = shuffle(deck);

        // Deal out cards
        dealDeck(deck);

        // Update cards
        updateDisplayedCards(8);
    }

    // Returns an array of 52 ints as a card deck:
    // (corresponds to index in a Card [] array)
    //   00-12 (2 of Hearts to Ace of Hearts)
    //   13-25 (2 of Diamonds to Ace of Diamonds)
    //   26-38 (2 of Clubs to Ace of Clubs)
    //   39-51 (2 of Spades to Ace of Spades)
    private int [] getDeck()
    {
        int [] deck = {0,1,2,3,4,5,6,7,8,9,10,11,12,
                13,14,15,16,17,18,19,20,21,22,23,24,25,
                26,27,28,29,30,31,32,33,34,35,36,37,38,
                39,40,41,42,43,44,45,46,47,48,49,50,51};
        return deck;
    }

    // Returns a shuffled array of 52 cards
    private static int [] shuffle(int [] deck)
    {
        // Create a new deck to store shuffled cards in
        int [] shufDeck = new int[52];

        // Create an ArrayList containing the unshuffled deck
        ArrayList unShufDeck = new ArrayList(52);
        for(int i=0; i<52; i++)
        {
            unShufDeck.add(deck[i]);
        }

        // Create a random number generator
        Random randNum = new Random();

        // For each element in ArrayList, place randomly in shufDeck
        int count = 0;
        while(unShufDeck.size() != 0)
        {
            // Generate a random number between 0 (inclusive) and size (exclusive)
            int size = unShufDeck.size();
            int num = randNum.nextInt(size);

            // Use number to select a card from ArrayList and place in shuffled deck
            shufDeck[count] = (Integer) unShufDeck.remove(num);

            // Increment count
            count = count + 1;
        }

        return shufDeck;
    }

    // Deals cards out to the appropriate ArrayList groups
    private void dealDeck(int [] deck)
    {
        opponentHandCards = new ArrayList();	// Initialize ArrayLists
        opponentUpCards = new ArrayList();
        opponentDownCards = new ArrayList();
        pileCards = new ArrayList();
        playerDownCards = new ArrayList();
        playerUpCards = new ArrayList();
        playerHandCards = new ArrayList();
        deckCards = new ArrayList();
        opponentDownCards.add(deck[0]);	// Deal 3 cards down to opponent
        opponentDownCards.add(deck[1]);
        opponentDownCards.add(deck[2]);
        playerDownCards.add(deck[3]);		// Deal 3 cards down to player
        playerDownCards.add(deck[4]);
        playerDownCards.add(deck[5]);
        opponentUpCards.add(deck[6]);		// Deal 3 cards up to opponent
        opponentUpCards.add(deck[7]);
        opponentUpCards.add(deck[8]);
        playerUpCards.add(deck[9]);		// Deal 3 cards up to player
        playerUpCards.add(deck[10]);
        playerUpCards.add(deck[11]);
        opponentHandCards.add(deck[12]);	// Deal 3 cards to opponent's hand
        opponentHandCards.add(deck[13]);
        opponentHandCards.add(deck[14]);
        playerHandCards.add(deck[15]);	// Deal 3 cards to player's hand
        playerHandCards.add(deck[16]);
        playerHandCards.add(deck[17]);
        for(int i=18; i<52; i++)			// Deal rest to deckCards
        {
            deckCards.add(deck[i]);
        }
    }

    // Accepts an arrayList of card indexes and sorts according to card value
    // Suite order in case of a tie is C,D,H,S
    private static ArrayList sortCardArrayList(ArrayList array)
    {
        // create an int array to temporarily store indexes
        int size = array.size();
        int [] tempIndexStorage = new int[size];
        int [] tempIndexPosition = new int[size];

        // place ArrayList indexes into int [] tempStorage
        for(int i=0; i<size; i++)
        {
            tempIndexStorage[i] = (Integer) array.remove(0);
        }

        // Get index positions for each value in tempIndexStorage []
        // and place in tempIndexPosition []
        for(int i=0; i<size; i++)
        {
            tempIndexPosition[i] = getIndexSortPosition(tempIndexStorage[i]);
        }

        // sort int array
        for(int i=0; i<size; i++)
        {
            // find the smallest sort position in tempIndexPosition[i...size-1]
            int minPosition = i;
            int count = i+1;
            while(count < size)
            {
                if(tempIndexPosition[count] < tempIndexPosition[minPosition])
                {
                    minPosition = count;
                }
                count = count + 1;
            }

            int min1 = tempIndexPosition[minPosition];
            int min2 = tempIndexStorage[minPosition];
            tempIndexPosition[minPosition] = tempIndexPosition[i];
            tempIndexStorage[minPosition] = tempIndexStorage[i];
            tempIndexPosition[i] = min1;
            tempIndexStorage[i] = min2;
        }

        // place sorted tempIndexStorage values back into an ArrayList
        for(int i=0; i<size; i++)
        {
            array.add(tempIndexStorage[i]);
        }

        // return sorted ArrayList
        return array;
    }

    // Returns the sort position for a given index
    private static int getIndexSortPosition(int index)
    {
        switch(index)
        {
            case 1:	return 0;	case 2:	return 4;
            case 3:	return 8;	case 4:	return 12;
            case 5:	return 16;	case 6:	return 20;
            case 7:	return 24;	case 9:	return 28;
            case 10:	return 32;	case 11:	return 36;
            case 12:	return 40;	case 0:	return 44;
            case 8:	return 48;	case 14:	return 1;
            case 15:	return 5;	case 16:	return 9;
            case 17:	return 13;	case 18:	return 17;
            case 19:	return 21;	case 20:	return 25;
            case 22:	return 29;	case 23:	return 33;
            case 24:	return 37;	case 25:	return 41;
            case 13:	return 45;	case 21:	return 49;
            case 27:	return 2;	case 28:	return 6;
            case 29:	return 10;	case 30:	return 14;
            case 31:	return 18;	case 32:	return 22;
            case 33:	return 26;	case 35:	return 30;
            case 36:	return 34;	case 37:	return 38;
            case 38:	return 42;	case 26:	return 46;
            case 34:	return 50;	case 40:	return 3;
            case 41:	return 7;	case 42:	return 11;
            case 43:	return 15;	case 44:	return 19;
            case 45:	return 23;	case 46:	return 27;
            case 48:	return 31;	case 49:	return 35;
            case 50:	return 39;	case 51:	return 43;
            case 39:	return 47;	case 47:	return 51;
            case 53:	return 0;	case 54:	return 4;
            case 55:	return 8;	case 56:	return 12;
            case 57:	return 16;	case 58:	return 20;
            case 59:	return 24;	case 61:	return 28;
            case 62:	return 32;	case 63:	return 36;
            case 64:	return 40;	case 52:	return 44;
            case 60:	return 48;	case 66:	return 1;
            case 67:	return 5;	case 68:	return 9;
            case 69:	return 13;	case 70:	return 17;
            case 71:	return 21;	case 72:	return 25;
            case 74:	return 29;	case 75:	return 33;
            case 76:	return 37;	case 77:	return 41;
            case 65:	return 45;	case 73:	return 49;
            case 79:	return 2;	case 80:	return 6;
            case 81:	return 10;	case 82:	return 14;
            case 83:	return 18;	case 84:	return 22;
            case 85:	return 26;	case 87:	return 30;
            case 88:	return 34;	case 89:	return 38;
            case 90:	return 42;	case 78:	return 46;
            case 86:	return 50;	case 92:	return 3;
            case 93:	return 7;	case 94:	return 11;
            case 95:	return 15;	case 96:	return 19;
            case 97:	return 23;	case 98:	return 27;
            case 100:	return 31;	case 101:	return 35;	case 102:	return 39;
            case 103:	return 43;	case 91:		return 47;	case 99:		return 51;
            case 104:	System.out.println("ERROR IN getIndexSortPosition() [1]");
                return -1;
            case 105:	System.out.println("ERROR IN getIndexSortPosition() [2]");
                return -1;
            case 106:	System.out.println("ERROR IN getIndexSortPosition() [3]");
                return -1;
            case 107:	System.out.println("ERROR IN getIndexSortPosition() [4]");
                return -1;
            case 108:	System.out.println("ERROR IN getIndexSortPosition() [5]");
                return -1;
            case 109:	System.out.println("ERROR IN getIndexSortPosition() [6]");
                return -1;
            default:		System.out.println("ERROR IN getIndexSortPosition() [7]");
                return -1;
        }
    }

    // Returns the int value of the card for a given index
    private static int getIndexValue(int index)
    {
        switch(index)
        {
            case 0:	return 2;	case 1:	return 3;
            case 2:	return 4;	case 3:	return 5;
            case 4:	return 6;	case 5:	return 7;
            case 6:	return 8;	case 7:	return 9;
            case 8:	return 10;	case 9:	return 11;
            case 10:	return 12;	case 11:	return 13;
            case 12:	return 14;	case 13:	return 2;
            case 14:	return 3;	case 15:	return 4;
            case 16:	return 5;	case 17:	return 6;
            case 18:	return 7;	case 19:	return 8;
            case 20:	return 9;	case 21:	return 10;
            case 22:	return 11;	case 23:	return 12;
            case 24:	return 13;	case 25:	return 14;
            case 26:	return 2;	case 27:	return 3;
            case 28:	return 4;	case 29:	return 5;
            case 30:	return 6;	case 31:	return 7;
            case 32:	return 8;	case 33:	return 9;
            case 34:	return 10;	case 35:	return 11;
            case 36:	return 12;	case 37:	return 13;
            case 38:	return 14;	case 39:	return 2;
            case 40:	return 3;	case 41:	return 4;
            case 42:	return 5;	case 43:	return 6;
            case 44:	return 7;	case 45:	return 8;
            case 46:	return 9;	case 47:	return 10;
            case 48:	return 11;	case 49:	return 12;
            case 50:	return 13;	case 51:	return 14;
            case 52:	return 2;	case 53:	return 3;
            case 54:	return 4;	case 55:	return 5;
            case 56:	return 6;	case 57:	return 7;
            case 58:	return 8;	case 59:	return 9;
            case 60:	return 10;	case 61:	return 11;
            case 62:	return 12;	case 63:	return 13;
            case 64:	return 14;	case 65:	return 2;
            case 66:	return 3;	case 67:	return 4;
            case 68:	return 5;	case 69:	return 6;
            case 70:	return 7;	case 71:	return 8;
            case 72:	return 9;	case 73:	return 10;
            case 74:	return 11;	case 75:	return 12;
            case 76:	return 13;	case 77:	return 14;
            case 78:	return 2;	case 79:	return 3;
            case 80:	return 4;	case 81:	return 5;
            case 82:	return 6;	case 83:	return 7;
            case 84:	return 8;	case 85:	return 9;
            case 86:	return 10;	case 87:	return 11;
            case 88:	return 12;	case 89:	return 13;
            case 90:	return 14;	case 91:	return 2;
            case 92:	return 3;	case 93:	return 4;
            case 94:	return 5;	case 95:	return 6;
            case 96:	return 7;	case 97:	return 8;
            case 98:	return 9;	case 99:	return 10;
            case 100:	return 11;
            case 101:	return 12;
            case 102:	return 13;
            case 103:	return 14;
            case 104:	return 0;
            case 105:	return 0;
            case 106:	return 0;
            case 107:	return 0;
            case 108:	return 0;
            case 109:	return 0;
            default: System.out.println("ERROR IN getIndexValue");
                return 0;
        }
    }

    // Selects the lowest cards and displays it (not a 2)
    // Returns:	false -> opponent had lowest card
    //          true ->  player had lowest card
    private boolean selectLowestCardAndDisplay()
    {
        // Find the lowest card in opponentHand, save 2s or 10s
        int oMinValue = 1000;
        int oMinIndex = 1000;
        for(int i=0; i<3; i++)
        {
            int tempIndex = (Integer)opponentHandCards.get(i);
            int tempValue = getIndexValue(tempIndex);
            if(tempValue != 2 && tempValue != 10)
            {
                if(tempValue < oMinValue)
                {
                    oMinValue = tempValue;
                    oMinIndex = i;
                }
                else if(tempValue == oMinValue)
                {
                    Random rand = new Random();
                    int randNum = rand.nextInt(10);
                    if(randNum < 5)
                    {
                        oMinValue = tempValue;
                        oMinIndex = i;
                    }
                }
            }
        }

        if(oMinValue == 1000) // if only 2s and 10s are found, get 2s
        {
            for(int i=0; i<3; i++)
            {
                int tempIndex = (Integer)opponentHandCards.get(i);
                int tempValue = getIndexValue(tempIndex);
                if(tempValue != 10)
                {
                    if(tempValue < oMinValue)
                    {
                        oMinValue = tempValue;
                        oMinIndex = i;
                    }
                    else if(tempValue == oMinValue)
                    {
                        Random rand = new Random();
                        int randNum = rand.nextInt(10);
                        if(randNum < 5)
                        {
                            oMinValue = tempValue;
                            oMinIndex = i;
                        }
                    }
                }
            }
        }

        if(oMinValue == 1000) // if only 0s are found, get 10s
        {
            for(int i=0; i<3; i++)
            {
                int tempIndex = (Integer)opponentHandCards.get(i);
                int tempValue = getIndexValue(tempIndex);
                if(tempValue < oMinValue)
                {
                    oMinValue = tempValue;
                    oMinIndex = i;
                }
                else if(tempValue == oMinValue)
                {
                    Random rand = new Random();
                    int randNum = rand.nextInt(10);
                    if(randNum < 5)
                    {
                        oMinValue = tempValue;
                        oMinIndex = i;
                    }
                }
            }
        }
        int cIndex = (Integer)opponentHandCards.get(oMinIndex);

        // Find the lowest card in playerHand, save 2s or 10s
        int pMinValue = 1000;
        int pMinIndex = 1000;
        for(int i=0; i<3; i++)
        {
            int tempIndex = (Integer)playerHandCards.get(i);
            int tempValue = getIndexValue(tempIndex);
            if(tempValue != 2 && tempValue != 10)
            {
                if(tempValue < pMinValue)
                {
                    pMinValue = tempValue;
                    pMinIndex = i;
                }
                else if(tempValue == pMinValue)
                {
                    Random rand = new Random();
                    int randNum = rand.nextInt(10);
                    if(randNum < 5)
                    {
                        pMinValue = tempValue;
                        pMinIndex = i;
                    }
                }
            }
        }

        if(pMinValue == 1000) // if only 2s and 10s are found, get 2s
        {
            for(int i=0; i<3; i++)
            {
                int tempIndex = (Integer)playerHandCards.get(i);
                int tempValue = getIndexValue(tempIndex);
                if(tempValue != 10)
                {
                    if(tempValue < pMinValue)
                    {
                        pMinValue = tempValue;
                        pMinIndex = i;
                    }
                    else if(tempValue == pMinValue)
                    {
                        Random rand = new Random();
                        int randNum = rand.nextInt(10);
                        if(randNum < 5)
                        {
                            pMinValue = tempValue;
                            pMinIndex = i;
                        }
                    }
                }
            }
        }

        if(pMinValue == 1000) // if only 10s are found, get 10s
        {
            for(int i=0; i<3; i++)
            {
                int tempIndex = (Integer)playerHandCards.get(i);
                int tempValue = getIndexValue(tempIndex);
                if(tempValue < pMinValue)
                {
                    pMinValue = tempValue;
                    pMinIndex = i;
                }
                else if(tempValue == pMinValue)
                {
                    Random rand = new Random();
                    int randNum = rand.nextInt(10);
                    if(randNum < 5)
                    {
                        pMinValue = tempValue;
                        pMinIndex = i;
                    }
                }
            }
        }
        cIndex = (Integer)playerHandCards.get(pMinIndex);

        // Find the lowest of the two selected cards
        int minValue = 1000;
        int minIndex = 1000;
        boolean opponentHasLowest = false;
        if(oMinValue < pMinValue)
        {
            minValue = oMinValue;
            minIndex = oMinIndex;
            opponentHasLowest = true;
        }
        else if(oMinValue == pMinValue)
        {
            Random rand = new Random();
            int randNum = rand.nextInt(10);
            if(randNum < 5)
            {
                minValue = oMinValue;
                minIndex = oMinIndex;
                opponentHasLowest = true;
            }
            else
            {
                minValue = pMinValue;
                minIndex = pMinIndex;
                opponentHasLowest = false;
            }
        }
        else
        {
            minValue = pMinValue;
            minIndex = pMinIndex;
            opponentHasLowest = false;
        }

        if(opponentHasLowest)
        {
            cIndex = (Integer)opponentHandCards.get(minIndex);
        }
        else
        {
            cIndex = (Integer)playerHandCards.get(minIndex);
        }

        // Remove lowest card from hand, place it on the pile, draw a new card,
        // and display message
        if(opponentHasLowest)
        {
            // remove lowest card from hand and place on pile
            int temp = (Integer) opponentHandCards.remove(minIndex);
            pileCards.add(temp);
            Card tempCard1 = pileArray[temp];

            // draw a card
            temp = (Integer) deckCards.remove(0);
            opponentHandCards.add(temp);
            Card tempCard2 = opponentHandArray[temp];

            // display message
            sendMessage("Your opponent had the lowest card, the " +
                    tempCard1.getLongName() + ", and draws a new card, the " +
                    tempCard2.getLongName());
            sendMessage("Your Turn!");
        }
        else
        {
            // remove lowest card from hand and place on pile
            int temp = (Integer) playerHandCards.remove(minIndex);
            pileCards.add(temp);
            Card tempCard1 = pileArray[temp];

            // draw a card
            temp = (Integer) deckCards.remove(0);
            playerHandCards.add(temp);
            Card tempCard2 = playerHandArray[temp];

            // display message
            sendMessage("You had the lowest card, the " + tempCard1.getLongName() +
                    ", and draw a new card, the " + tempCard2.getLongName());
        }

        // Set appropriate card to display
        updateDisplayedCards(0);
        updateDisplayedCards(3);
        updateDisplayedCards(6);

        return opponentHasLowest;
    }

    // Executes the player's turn
    private void executePlayerTurn(MouseEvent e, Card cardClicked)
    {
        // Test for a click on the opponent's hand
        if(cardClicked.getParentName() == "opponentHandPanel" ||
                cardClicked.getParentName() == "opponentUpPanel")
        {
            // Send a help message
            sendMessage("Please don't cheat! " +
                    "Click on the deck, the pile, or your own cards only!");
        }
        else
        {
            // Clear messages if it is the players first click during his/her turn
            if(playersFirstMove)
            {
                clearMessages();
                playersFirstMove = false;
            }

            // STATE 1 - Begin turn
            if(playerState == 1)
            {
                // Initialize
                killCardUsed = false;

                // Clicked on a card in hand
                if(cardClicked.getParentName() == "playerHandPanel")
                {
                    // Check to ensure card clicked on is bigger
                    // than the card on the pile
                    boolean cardValid = playerCardLaidDownValid(cardClicked);
                    if(cardValid)
                    {
                        // Check for multiples in the hand
                        // If multiples exist, a display box
                        // asks how many cards the player wants to lay down
                        int cardsSelectionCount =
                                playerMultipleCardsCheck(cardClicked.getValue());

                        // Test for cancellation of multiple seleciton box
                        if( cardsSelectionCount == 1 ||
                                cardsSelectionCount == 2 ||
                                cardsSelectionCount == 3 ||
                                cardsSelectionCount == 4    )
                        {
                            // Lay down cards
                            playerLayDownCard(cardClicked.getValue(),
                                    cardsSelectionCount);

                            // Check if a 10 was used
                            if(cardClicked.getValue() == 10)
                            {
                                killCardUsed = true;
                            }

                            // Remove cards from the pile if a 10 was used
                            if(killCardUsed)
                            {
                                // Remove all cards from pile
                                killPile();
                            }

                            // Change player state
                            playerState = 2;
                        }
                    }
                }
                // Clicked on pile
                else if(cardClicked.getParentName() == "pilePanel")
                {
                    if(pileCards.size() == 0)
                    {
                        sendMessage("You cannot pick up any cards " +
                                "because there are no cards in the pile");
                    }
                    else
                    {
                        playerPickupPile();
                    }
                }
                // Clicked on deck
                else if(cardClicked.getParentName() == "deckPanel")
                {
                    // Send a help message
                    if(pileCards.size() == 0)
                    {
                        sendMessage("You must lay down a card");
                    }
                    else
                    {
                        sendMessage("You must pickup the pile or lay down a card");
                    }
                }
                // Clicked on up/down cards
                else if(cardClicked.getParentName() == "playerUpPanel")
                {
                    // Send a help message
                    if(pileCards.size() == 0)
                    {
                        sendMessage("You must lay down a card at this time");
                    }
                    else
                    {
                        sendMessage("You must pickup the pile or " +
                                "lay down a card at this time");
                    }
                }

                // AUTOMATIC CHECKS
                // Perform automatic checks on this mouse click if playerState == 2
                if(playerState == 2)
                {
                    // Perform automatic check 1 - Check for win condition
                    if(checkWinCondition())
                    {
                        // Set gameBegun to false
                        gameBegun = false;

                        // Send message
                        sendMessage("WIN! You have won the game!");
                    }

                    // No win condition exists
                    else
                    {
                        // Perform automatic check 2 - Check for end of turn
                        playerState = 3;
                        if(deckCards.size() == 0 && playerHandCards.size() == 0)
                        {
                            playerState = 2;
                        }
                        else if(deckCards.size() != 0 && playerHandCards.size() < 3)
                        {
                            playerState = 2;
                        }
                        else if(killCardUsed)
                        {
                            playerState = 2;
                        }

                        // Not end of turn
                        if(playerState == 2)
                        {
                            // Perform automatic check 3 - Check for killCardUsed
                            //                             when cards in deck == 0
                            if(killCardUsed && deckCards.size() == 0 &&
                                    playerHandCards.size() != 0)
                            {
                                playerState = 1;
                            }
                            else if(killCardUsed && playerHandCards.size() >= 3)
                            {
                                playerState = 1;
                            }
                        }
                    }
                }
            }

            // STATE 2 - Card laid down
            else if(playerState == 2)
            {
                // Clicked on a card in hand
                if(cardClicked.getParentName() == "playerHandPanel")
                {
                    // Send a help message
                    if(playerHandCards.size() == 0 && deckCards.size() == 0)
                    {
                        if(playerUpCards.size() != 0)
                        {
                            sendMessage("You must pickup your 3 cards facing up");
                        }
                        else
                        {
                            sendMessage("You must pickup your 3 cards facing down");
                        }
                    }
                    else
                    {
                        sendMessage("You must draw a card");
                    }
                }
                // Clicked on pile
                else if(cardClicked.getParentName() == "pilePanel")
                {
                    // Send a help message
                    if(playerHandCards.size() == 0 && deckCards.size() == 0)
                    {
                        if(playerUpCards.size() != 0)
                        {
                            sendMessage("You must pickup your 3 cards facing up");
                        }
                        else
                        {
                            sendMessage("You must pickup your 3 cards facing down");
                        }
                    }
                    else
                    {
                        sendMessage("You must draw a card");
                    }
                }
                // Clicked on deck
                else if(cardClicked.getParentName() == "deckPanel")
                {
                    if(deckCards.size() == 0)
                    {
                        // Send a help message
                        sendMessage("You cannot draw a card when there " +
                                "are no cards left in the deck");
                    }
                    else if(playerHandCards.size() >= 3)
                    {
                        // Send a help message
                        sendMessage("You cannot draw a card when there " +
                                "are 3 or more cards in your hand");
                    }
                    else
                    {
                        // Draw a card
                        playerDrawCard();

                        // End turn unless a kill card was used
                        if(killCardUsed)
                        {
                            playerState = 1;
                        }
                        else
                        {
                            playerState = 3;
                        }
                    }
                }
                // Clicked on up/down cards
                else if(cardClicked.getParentName() == "playerUpPanel")
                {
                    if(playerHandCards.size() != 0 || deckCards.size() != 0)
                    {
                        // Send a help message
                        sendMessage("You must draw a card");
                    }
                    else
                    {
                        // Pickup up/down cards
                        playerPickupUpDown();

                        // End turn unless a kill card was used
                        if(killCardUsed)
                        {
                            playerState = 1;
                        }
                        else
                        {
                            playerState = 3;
                        }
                    }
                }
            }

            // STATE 3 - End turn
            if(playerState == 3)
            {
                // Set playerState back to 1
                playerState = 1;

                // Clear messages and send player ended turn message
                clearMessages();
                sendMessage("Your turn has ended!");

                // Do opponent's turn
                executeOpponentTurn();
            }
        }
    }

    // Moves the cards from the pile into the player's hand
    private void playerPickupPile()
    {
        // Iterate through the cards in the pile,
        // removing each one and placing it in playerHandCards
        int size = pileCards.size();
        for(int i=0; i<size; i++)
        {
            int cardIndex = (Integer) pileCards.remove(0);
            playerHandCards.add(cardIndex);
        }

        // Send message to interface
        sendMessage("You picked up the cards in the pile. " +
                "Please lay down a card!");

        // Update cards in interface
        updateDisplayedCards(8);
    }

    // Checks whether or not the card passed to it
    // can legally be placed on the pile
    private boolean playerCardLaidDownValid(Card card)
    {
        // Check if there are 0 cards in the pile
        if(pileCards.size() == 0)
        {
            return true;
        }

        // Get the value of the card passed to this method
        int cardValue = card.getValue();

        // Check for a 2, 10, or Ace (14) which are always legal
        if(cardValue == 2 || cardValue == 10 || cardValue == 14)
        {
            return true;
        }

        // Get the value of the card in the top pile
        int size = pileCards.size();
        int topCardIndex = (Integer) pileCards.get(size-1);
        int topCardValue = getIndexValue(topCardIndex);

        // Allow if cardValue > topCardValue
        if(cardValue > topCardValue)
        {
            return true;
        }

        // Allow if there is only 1 group of cards in pileCards
        // and cardValue == topCardValue

        // Prepare an array to help keep track of the number
        // of different card values
        int diffCardValuesCount = 0;
        boolean [] diffCardValueArray = new boolean[15];
        for(int i=0; i<15; i++)
        {
            diffCardValueArray[i] = false;
        }

        // Iterate through the cards in the pile,
        // counting once each time a new card value is found
        for(int i=0; i<size; i++)
        {
            // Get card value
            int tempCardIndex = (Integer) pileCards.get(i);
            int tempCardValue = getIndexValue(tempCardIndex);
            if(diffCardValueArray[tempCardValue] == false)
            {
                diffCardValueArray[tempCardValue] = true;
                diffCardValuesCount = diffCardValuesCount + 1;
            }
        }

        if(cardValue == topCardValue && diffCardValuesCount == 1)
        {
            return true;
        }

        // Send a help message
        if(diffCardValuesCount == 1)
        {
            // Only 1 card on te pile (or multiples with the same value)
            sendMessage("You must select a card with a value greater " +
                    "than or equal to the " +
                    pileArray[topCardIndex].getLongName());
        }
        else if(topCardValue == 14) // An ace on top of the pile
        {
            sendMessage("You must lay down a 2, 10, or Ace");
        }
        else
        {
            sendMessage("You must select a card with a value greater than the " +
                    pileArray[topCardIndex].getLongName());
            sendMessage("Remember a 10 kills the pile, and a 2 resets the pile");
        }

        // Otherwise return false
        return false;
    }

    // Check for multiples in the hand
    // If multiples exist, a display box asks the user
    //   how many cards he/she wants to lay down
    // Returns an int with the number of cards to be laid down
    private int playerMultipleCardsCheck(int targetValue)
    {
        int selectedCardNum = 0;

        // Count the number of cards in hand == targetValue
        for(int i=0; i<playerHandCards.size(); i++)
        {
            int cardIndex = (Integer)playerHandCards.get(i);
            int cardValue = getIndexValue(cardIndex);
            if(cardValue == targetValue)
            {
                selectedCardNum = selectedCardNum + 1;
            }
        }

        // Return 1 if there are no multiple cards == targetValue
        //	If there are multiple cards, prompt the user with a choice
        Object [] options;
        int selection = 0;
        if(selectedCardNum == 1)
        {
            return 1;
        }
        else if(selectedCardNum == 2)
        {
            Object [] tempOptions = {"One", "Two", "Cancel"};
            options = tempOptions;
        }
        else if(selectedCardNum == 3)
        {
            Object [] tempOptions = {"One", "Two", "Three", "Cancel"};
            options = tempOptions;
        }
        else if(selectedCardNum == 4)
        {
            Object [] tempOptions = {"One", "Two", "Three", "Four", "Cancel"};
            options = tempOptions;
        }
        else
        {
            return 0;
        }

        // Prompt user and return selection
        selection = JOptionPane.showOptionDialog( null,
                "There is more than one card with the value of " +
                        targetValue +
                        "\nHow many cards would you like to lay down?",
                "Lay Down Multiple Cards?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                0 );

        // Return 0 if cancel selected
        if(selectedCardNum == 2 && selection == 2)
        { return 0; }
        else if(selectedCardNum == 3 && selection == 3)
        { return 0; }
        else if(selectedCardNum == 4 && selection == 4)
        { return 0; }

        // Return selection if not cancelled
        return selection + 1;
    }

    // Lays down the appropriate number if cards for the selected targetValue
    private void playerLayDownCard(int targetValue, int layDownCount)
    {
        // Iterate through playerHandCards
        // and remove the correct number of cards from the deck
        // and place them on the pile
        String printLine = "You lay down the ";
        boolean firstCardFound = false;
        for(int k=0; k<layDownCount; k++)
        {
            int size = playerHandCards.size();
            for(int i=0; i<size; i++)
            {
                // Get card value
                int cardIndex = (Integer) playerHandCards.get(i);
                int cardValue = getIndexValue(cardIndex);

                if(cardValue == targetValue)
                {
                    // Remove card and add to pile
                    cardIndex = (Integer) playerHandCards.remove(i);
                    pileCards.add(cardIndex);

                    // Prepare message for interface
                    if(!firstCardFound)
                    {
                        printLine = printLine +
                                playerHandArray[cardIndex].getLongName();
                        firstCardFound = true;
                    }
                    else
                    {
                        printLine = printLine + ", and the " +
                                playerHandArray[cardIndex].getLongName();
                    }

                    break;
                }
            }
        }

        // Send message
        sendMessage(printLine);

        // Update cards in interface
        updateDisplayedCards(8);
    }

    // Player draws a card
    private void playerDrawCard()
    {
        int size = deckCards.size();
        if(size == 0)
        {

        }
        else
        {
            // Get the top card from deckCards and place it in playerHandCards
            int cardIndex = (Integer) deckCards.remove(0);
            playerHandCards.add(cardIndex);

            // Send message
            sendMessage("You draw a new card, the " +
                    playerHandArray[cardIndex].getLongName());

            // Update cards in interface
            updateDisplayedCards(8);
        }
    }

    // Player picks up cards facing up or down
    private void playerPickupUpDown()
    {
        int upCardsSize = playerUpCards.size();
        int downCardsSize = playerDownCards.size();
        if(upCardsSize == 0 && downCardsSize == 0)
        {

        }
        else
        {
            if(upCardsSize == 0)
            {
                // Iterate through the cards in playerDownCards,
                // removing each one and placing it in playerHandCards
                for(int i=0; i<3; i++)
                {
                    int cardIndex = (Integer) playerDownCards.remove(0);
                    playerHandCards.add(cardIndex);
                }

                // Send message
                sendMessage("You pick up your 3 cards facing down");
            }
            else
            {
                // Iterate through the cards in playerUpCards,
                // removing each one and placing it in playerHandCards
                for(int i=0; i<3; i++)
                {
                    int cardIndex = (Integer) playerUpCards.remove(0);
                    playerHandCards.add(cardIndex);
                }

                // Send message
                sendMessage("You pick up your 3 cards facing up");
            }
        }

        // Update cards in interface
        updateDisplayedCards(8);
    }

    // Executes the opponent's turn
    private void executeOpponentTurn()
    {
        // Initialization
        boolean canBeatPile = false;
        boolean wantToPickup = false;
        playersFirstMove = true;

        // QUESTION: Can I beat the top card?
        canBeatPile = opponentBeatTopCardQuery();
        if(!canBeatPile)
        {
            opponentPickupPile();
        }
        else // can beat the top card
        {
            // QUESTION: Do I want to pickup?
            wantToPickup = opponentPickupQuery();

            if(wantToPickup)
            {
                opponentPickupPile();
            }
        }

        // Lay down an approriate card
        boolean opponentKillCardUsed = opponentLayDownCard();

        // If a 10 was laid down, do the following
        while(opponentKillCardUsed)
        {
            // Kill the pile
            killPile();

            // Draw a card if there are cards left in the deck,
            // and the cards in your hand is < 0
            if(deckCards.size() != 0 && opponentHandCards.size() < 3)
            {
                opponentDrawCard();
            }
            else
            {
                // Pickup up or down cards if there are no cards in the deck,
                // and there are no cards in your hand
                if(deckCards.size() == 0 && opponentHandCards.size() == 0)
                {
                    opponentPickupUpDown();
                }
            }
            opponentKillCardUsed = opponentLayDownCard();
        }

        // QUESTION: Are there < 3 cards in opponentHand?
        if(opponentHandCards.size() < 3)
        {
            // QUESTION: Are there cards left in the deck?
            if(deckCards.size() > 0)
            {
                opponentDrawCard();
            }
            else
            {
                // QUESTION: Are there cards left in opponentHand?
                if(opponentHandCards.size() == 0)
                {
                    // QUESTION: Any cards left up or down?
                    if(opponentUpCards.size() != 0 ||
                            opponentDownCards.size() != 0)
                    {
                        opponentPickupUpDown();
                    }
                }
            }
        }

        // Check for win condition
        if(checkWinCondition())
        {
            // Set gameBegun to false
            gameBegun = false;

            // Send message
            sendMessage("LOSE! Your opponent has won!");
        }
        else
        {
            // Send message
            sendMessage("Your turn!");

            // End turn
            playerTurn = !playerTurn;
        }
    }

    // Returns true if the opponent can beat the top card on the pile
    private boolean opponentBeatTopCardQuery()
    {
        // Get the value of the top card from the pile
        int size = pileCards.size();
        int topCardIndex = (Integer) pileCards.get(size-1);
        int topCardValue = getIndexValue(topCardIndex);

        // Iterate through the opponent's hand
        // and find a card higher than two, a 10, or a 2
        size = opponentHandCards.size();
        for(int i=0; i<size; i++)
        {
            // Get card value for index i
            int cardIndex = (Integer) opponentHandCards.get(i);
            int cardValue = getIndexValue(cardIndex);

            // If a 10, return true
            if(cardValue == 10)
            {
                return true;
            }

            // If a 2, return true
            if(cardValue == 2)
            {
                return true;
            }

            // If an Ace, return true if topCardValue is also an Ace
            if(cardValue == 14 && topCardValue == 14)
            {
                return true;
            }

            // If card value > topCardValue
            if(cardValue > topCardValue)
            {
                return true;
            }
        }

        // Return false otherwise
        return false;
    }

    // Returns true if the opponent wants to pickup the pile
    // HARD LOGIC: - Pickup if any card in opponentHand is less than
    //               or equal to the bottom card in the pile
    //             - Pickup if the pile has more than 1/2 high cards - ONLY
    //               if there are cards left in the pile
    //               - low cards = cards with value < 9
    //               - high cards = cards with a value 9, J, K, Q, or Ace
    //               - count multiple low cards of same value as one card
    //               - count multiple high cards of same value as multiple cards
    // EASY LOGIC: - Never try to pickup
    private boolean opponentPickupQuery()
    {
        // Return false if difficulty == easy
        if(difficulty == 0)
        {
            return false;
        }

        // Get card on the bottom of the pile
        int bottomCardIndex = (Integer) pileCards.get(0);
        int bottomCardValue = getIndexValue(bottomCardIndex);

        // Iterate through the opponent's hand
        // and find a card lower than bottomCardIndex
        int size = opponentHandCards.size();
        for(int i=0; i<size; i++)
        {
            // Get card value for index i
            int cardIndex = (Integer) opponentHandCards.get(i);
            int cardValue = getIndexValue(cardIndex);

            // If card value <= bottomCardValue
            if(cardValue != 2 && cardValue != 10)
            {
                if(cardValue <= bottomCardValue)
                {
                    return true;
                }
            }
        }

        if(deckCards.size() != 0)
        {
            // Iterate through the pile cards and count high and low cards
            // - low cards = cards with value < 9
            // - high cards = cards with a value 9, J, K, Q, or Ace
            // - count multiple low cards of same value as one card
            // - count multiple high cards of same value as multiple 
            size = pileCards.size();
            int highCardCount = 0;
            int lowCardCount = 0;
            boolean [] lowCardsCounted = new boolean[15];
            for(int i=0; i<15; i++)
            {
                lowCardsCounted[i] = false;
            }
            for(int i=0; i<size; i++) // iterate through pile cards
            {
                // Get card value for index i
                int cardIndex = (Integer) pileCards.get(i);
                int cardValue = getIndexValue(cardIndex);

                if(cardValue > 8) // high card found
                {
                    highCardCount = highCardCount + 1;
                }
                else // low card found
                {
                    if(lowCardsCounted[cardValue] == false)
                    {
                        lowCardsCounted[cardValue] = true;
                        lowCardCount = lowCardCount + 1;
                    }
                }
            }

            // Test if pileCards has higher than 50% high cards
            if(highCardCount >= lowCardCount)
            {
                return true;
            }
        }

        // Return false otherwise
        return false;
    }

    // Moves the cards from the pile into the opponent's hand
    private void opponentPickupPile()
    {
        // Iterate through the cards in the pile,
        // removing each one and placing it in opponentHand
        int size = pileCards.size();
        for(int i=0; i<size; i++)
        {
            int cardIndex = (Integer) pileCards.remove(0);
            opponentHandCards.add(cardIndex);
        }

        // Send message to interface
        sendMessage("Your opponent picks up the pile");

        // Update cards in interface
        updateDisplayedCards(8);
    }

    // Removes a choice card from opponentHand and places it on top of the pile
    // HARD LOGIC: - Hold an Ace over a 2
    //             - Hold a 10 over an Ace
    //					- Lay down the lowest card possible
    // EASY LOGIC: - Hold an Ace over a 2
    //             - Hold a 10 over an Ace
    //					- Lay down the lowest card possible
    //             - Play a 10 if there are > 5 cards in pile
    // Returns TRUE if a killCard was used
    private boolean opponentLayDownCard()
    {
        // Prepare an array to help keep track of the number
        // of different card values
        int diffCardValuesCount = 0;
        boolean [] diffCardValueArray = new boolean[15];
        for(int i=0; i<15; i++)
        {
            diffCardValueArray[i] = false;
        }

        // Find the card with the highest priority, which can be laid down,
        //   as defined in opponentCardThrowPriority()
        // Keep track of the number of different card values
        int size = opponentHandCards.size();
        int minCardValue = 100;
        int minCardIndex = 100;
        int minCardPriority = 100;
        boolean killCardInHand = false;
        for(int i=0; i<size; i++)
        {
            // Get card value
            int cardIndex = (Integer) opponentHandCards.get(i);
            int cardValue = getIndexValue(cardIndex);

            // Test if card is a 10
            if(cardValue == 10)
            {
                killCardInHand = true;
            }

            // Test for a card value not counted yet
            if(diffCardValueArray[cardValue] == false)
            {
                diffCardValueArray[cardValue] = true;
                diffCardValuesCount = diffCardValuesCount + 1;
            }

            // Get card priority
            int cardPriority = opponentCardThrowPriority(cardValue);
            boolean validMove =
                    opponentCardLaidDownValid(opponentHandArray[cardIndex]);
            if((cardPriority < minCardPriority) && validMove)
            {
                minCardPriority = cardPriority;
                minCardValue = cardValue;
                minCardIndex = cardIndex;
            }
        }

        // Exceptions to force a 10 to be play if one exists
        if(killCardInHand)
        {
            if(pileCards.size() > 5 && difficulty == 0)
            {
                minCardValue = 10;
            }
        }

        // Test for a kill card to be put down
        boolean opponentKillCardUsed = false;
        if(minCardValue == 10)
        {
            opponentKillCardUsed = true;
        }

        // HARD LOGIC: - Find cards in opponentHand with minCardValue
        //             - If there are multiple low cards == minCardValue,
        //					  remove them all and place in the pile
        //             - If there are multiple high cards == minCardValue,
        //					  remove the first one and place in the pile
        //             - Lay down a 10 or a 2 one at a time
        // EASY LOGIC: - Always lay down multiples
        boolean layDownAllMultiples = true;
        if(minCardValue == 10 || minCardValue == 2)
        {
            layDownAllMultiples = false;
        }
        if(minCardValue >= 9)
        {
            layDownAllMultiples = false;
        }
        if(difficulty == 0)
        {
            layDownAllMultiples = true;
        }

        if(layDownAllMultiples) // lay down all cards == minValue
        {
            boolean noMinCardsLeft = false;
            String printLine = "Your opponent lays down the ";
            boolean firstCardFound = false;
            while(!noMinCardsLeft)
            {
                noMinCardsLeft = true;
                size = opponentHandCards.size();
                for(int i=0; i<size; i++)
                {
                    // Get card value
                    int cardIndex = (Integer) opponentHandCards.get(i);
                    int cardValue = getIndexValue(cardIndex);

                    if(cardValue == minCardValue)
                    {
                        // Remove card
                        cardIndex = (Integer) opponentHandCards.remove(i);
                        pileCards.add(cardIndex);

                        // Send message to interface
                        if(!firstCardFound)
                        {
                            printLine = printLine +
                                    opponentHandArray[cardIndex].getLongName();
                            firstCardFound = true;
                        }
                        else
                        {
                            printLine = printLine + ", and the " +
                                    opponentHandArray[cardIndex].getLongName();
                        }
                        noMinCardsLeft = false;
                        break;
                    }
                }
            }
            sendMessage(printLine);
        }
        else // lay first card == minValue
        {
            size = opponentHandCards.size();
            for(int i=0; i<size; i++)
            {
                // Get card value
                int cardIndex = (Integer) opponentHandCards.get(i);
                int cardValue = getIndexValue(cardIndex);

                if(cardValue == minCardValue)
                {
                    // Remove card
                    cardIndex = (Integer) opponentHandCards.remove(i);
                    pileCards.add(cardIndex);

                    // Send message to interface
                    sendMessage("Your opponent lays down the " +
                            opponentHandArray[cardIndex].getLongName());
                    break;
                }
            }
        }

        // Update cards in interface
        updateDisplayedCards(8);

        if(opponentKillCardUsed)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // Returns a priority for a given card value
    private int opponentCardThrowPriority(int value)
    {
        switch(value)
        {
            case 3:	return 1;
            case 4:	return 2;
            case 5:	return 3;
            case 6:	return 4;
            case 7:	return 5;
            case 8:	return 6;
            case 9:	return 7;
            case 11:	return 8;
            case 12: return 9;
            case 13: return 10;
            case 2:	return 11;
            case 14:	return 12;
            case 10:	return 13;
            default:	System.out.println(
                    "ERROR IN opponentCardThrowPriority()");
                return -1;
        }
    }

    // Checks whether or not the card passed to it
    //   can legally be placed on the pile
    private boolean opponentCardLaidDownValid(Card card)
    {
        // Check if there are 0 cards in the pile
        if(pileCards.size() == 0)
        {
            return true;
        }

        // Get the value of the card passed to this method
        int cardValue = card.getValue();

        // Check for a 2, 10, or Ace (14) which are always legal
        if(cardValue == 2 || cardValue == 10 || cardValue == 14)
        {
            return true;
        }

        // Get the value of the card in the top pile
        int size = pileCards.size();
        int topCardIndex = (Integer) pileCards.get(size-1);
        int topCardValue = getIndexValue(topCardIndex);

        // Allow if cardValue > topCardValue
        if(cardValue > topCardValue)
        {
            return true;
        }

        // Allow if there is only 1 group of cards in pileCards
        // and cardValue == topCardValue

        // Prepare an array to help keep track of the number
        // of different card values
        int diffCardValuesCount = 0;
        boolean [] diffCardValueArray = new boolean[15];
        for(int i=0; i<15; i++)
        {
            diffCardValueArray[i] = false;
        }

        // Iterate through the cards in the pile,
        // counting once each time a new card value is found
        for(int i=0; i<size; i++)
        {
            // Get card value
            int tempCardIndex = (Integer) pileCards.get(i);
            int tempCardValue = getIndexValue(tempCardIndex);
            if(diffCardValueArray[tempCardValue] == false)
            {
                diffCardValueArray[tempCardValue] = true;
                diffCardValuesCount = diffCardValuesCount + 1;
            }
        }

        if(cardValue == topCardValue && diffCardValuesCount == 1)
        {
            return true;
        }

        // Otherwise return false
        return false;
    }

    // Opponent draw a card
    private void opponentDrawCard()
    {
        int size = deckCards.size();
        if(size == 0)
        {

        }
        else
        {
            // Get the top card from deckCards and place it in opponentHandCards
            int cardIndex = (Integer) deckCards.remove(0);
            opponentHandCards.add(cardIndex);

            // Send message
            sendMessage("Your opponent draws a new card, the " +
                    opponentHandArray[cardIndex].getLongName());

            // Update cards in interface
            updateDisplayedCards(8);
        }
    }

    // Opponent picks up the up or down cards
    private void opponentPickupUpDown()
    {
        int upCardsSize = opponentUpCards.size();
        int downCardsSize = opponentDownCards.size();
        if(upCardsSize == 0 && downCardsSize == 0)
        {

        }
        else
        {
            if(upCardsSize == 0)
            {
                // Iterate through the cards in opponentDownCards,
                // removing each one and placing it in opponentHand
                for(int i=0; i<3; i++)
                {
                    int cardIndex = (Integer) opponentDownCards.remove(0);
                    opponentHandCards.add(cardIndex);
                }

                // Send message
                sendMessage("Your opponent picks up his cards facing down");
            }
            else
            {
                // Iterate through the cards in opponentUpCards,
                // removing each one and placing it in opponentHand
                for(int i=0; i<3; i++)
                {
                    int cardIndex = (Integer) opponentUpCards.remove(0);
                    opponentHandCards.add(cardIndex);
                }

                // Send message
                sendMessage("Your opponent picks up his cards facing up");
            }
        }

        // Update cards in interface
        updateDisplayedCards(8);

    }

    // Kills the pile by laying down a 10 and all cards in the pile are removed
    private void killPile()
    {
        // Iterate through the whole pile and remove the cards from play one by one
        int size = pileCards.size();
        for(int i=0; i<size; i++)
        {
            pileCards.remove(0);
        }

        // Update cards in interface
        updateDisplayedCards(8);
    }

    // Checks for a win condition
    // Returns true if a win conditions are met!
    private boolean checkWinCondition()
    {
        // Test if opponent has won
        if(opponentHandCards.size() == 0 &&
                opponentDownCards.size() == 0 &&
                opponentUpCards.size() == 0)
        {
            losses = losses + 1;
            setWinLossPanel();
            saveDataFile(dataFile);
            return true;
        }

        // Test if player has won
        if(playerHandCards.size() == 0 &&
                playerDownCards.size() == 0 &&
                playerUpCards.size() == 0)
        {
            wins = wins + 1;
            setWinLossPanel();
            saveDataFile(dataFile);
            return true;
        }

        // Otherwise return false
        return false;
    }
}
