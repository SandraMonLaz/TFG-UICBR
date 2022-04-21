[System.Serializable]
public class SolCBRParser
{
    public CombinedItem[] sol;
}

[System.Serializable]
public class CombinedItem
{
    public ItemSol[] items;
    public string screenPosition;
    public string itemScale;
}

[System.Serializable]
public class ItemSol
{
    public string screenPosition;
    public string itemScale;
    public string image;
    public string id;
}