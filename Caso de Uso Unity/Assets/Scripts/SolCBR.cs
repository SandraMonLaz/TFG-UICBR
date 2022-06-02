using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class SolCBR
{
    public Combined[] sol;

    public SolCBR(SolCBRParser s)
    {
        sol = new Combined[s.sol.Length];
        for(int i=0; i < sol.Length; i++)
        {
            sol[i] = new Combined(s.sol[i]);       
        }
    }

}

public class Combined
{
    public ItemSolution[] items;
    public ScreenPos screenPosition;
    //public Scale itemScale;

    public Combined(CombinedItem comb)
    {
        screenPosition = (ScreenPos)Enum.Parse(typeof(ScreenPos), comb.screenPosition);
        //itemScale = (Scale)Enum.Parse(typeof(Scale), comb.itemScale);
        items = new ItemSolution[comb.items.Length];

        for(int i = 0; i < items.Length; i++)
        {
            items[i] = new ItemSolution(comb.items[i]);
        }
    }
}

public class ItemSolution
{
    public ScreenPos screenPosition;
    public Scale itemScale;
    public string image;
    public ItemId itemId;

    public ItemSolution(ItemSol item)
    {
        screenPosition = (ScreenPos)Enum.Parse(typeof(ScreenPos), item.screenPosition);
        itemScale = (Scale)Enum.Parse(typeof(Scale), item.itemScale);
        image = item.image;
        itemId = (ItemId)Enum.Parse(typeof(ItemId), item.id);
    }
}

public class HashItemSolutionComparer : Comparer<List<ItemSolution>>
{
    public override int Compare(List<ItemSolution> x, List<ItemSolution> y)
    {
        int a = 0;
        foreach(ItemSolution item in x)
        {
            if((int)item.itemScale > a)
                a = (int)item.itemScale;
        }
        int b = 0;
        foreach (ItemSolution item in y)
        {
            if ((int)item.itemScale > b)
                b = (int)item.itemScale;
        }

        if (a == b) return 0;
        if (a < b) return -1;
        else return 1;
    }
};
//Compara al reves que el de arriba
public class HashItemSolutionComparerInOrder : Comparer<List<ItemSolution>>
{
    public override int Compare(List<ItemSolution> x, List<ItemSolution> y)
    {
        int a = 0;
        foreach (ItemSolution item in x)
        {
            if ((int)item.itemScale > a)
                a = (int)item.itemScale;
        }
        int b = 0;
        foreach (ItemSolution item in y)
        {
            if ((int)item.itemScale > b)
                b = (int)item.itemScale;
        }

        if (a == b) return 0;
        if (a > b) return -1;
        else return 1;
    }
};

public class ItemSolutionComparer : Comparer<ItemSolution>
{
    public override int Compare(ItemSolution x, ItemSolution y)
    {
        if ((int)x.itemScale > (int)y.itemScale) return -1;
        else if ((int)x.itemScale < (int)y.itemScale) return 1;
        else return 0;
    }
}