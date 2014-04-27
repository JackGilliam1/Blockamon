package blockamon.io;

import blockamon.objects.Player;

import java.io.File;

public class SaveWriter implements ISaveWriter
{
    private IFileChooserHandler _fileChooserHandler;

    public SaveWriter(IFileChooserHandler fileChooserHandler)
    {
        _fileChooserHandler = fileChooserHandler;
    }

    public void SaveGame(Player player)
    {
        File file = _fileChooserHandler.getSaveFile();
    }

    /**
     * Things to save:
     * Player:
     *  Items:
     *      Type, Count
     *  Money
     *  Position:
     *      X, Y
     *  Blockamon:
     *      Type, Status, CurrentHealth, TotalHealth, Level, IsLead
     *  Blockamon Team:
     *      Order
     */
    private String BuildSaveFile(Player player) {
        return "";
    }
}
