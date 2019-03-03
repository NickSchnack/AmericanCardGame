// Card.java
// Nick Schnack   03-22-10

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

// an implementation of a playing card
public class Card extends JButton
{
    private String shortName;
    private String longName;
    private String parentName;
    private int value;

    // initialize card to given values
    public Card(String newShortName, String newLongName,
                String newParentName, int newValue, ImageIcon image)
    {
        super(image);
        setShortName(newShortName);
        setLongName(newLongName);
        setParentName(newParentName);
        setValue(newValue);
        setBorder(BorderFactory.createEmptyBorder());
        setFocusable(false);
    }

    // initialize record to the empty card
    public Card()
    {
        this("","","",0,null);
    }

    // shortName is set to newShortName
    public void setShortName(String newShortName)
    {
        // check validity of newShortName
        shortName = newShortName;
    }

    // longName is set to newLongName
    public void setLongName(String newLongName)
    {
        // check validity of newLongName
        longName = newLongName;
    }

    // parentName is set to newParentName
    public void setParentName(String newParentName)
    {
        // check validity of newParentame
        parentName = newParentName;
    }

    // value is set to newValue
    public void setValue(int newValue)
    {
        // check validity of newValue
        value = newValue;
    }

    // returns shortName
    public String getShortName()
    {
        return shortName;
    }




    // returns longName
    public String getLongName()
    {
        return longName;
    }

    // returns parentName
    public String getParentName()
    {
        return parentName;
    }

    // returns value
    public int getValue()
    {
        return value;
    }
}
