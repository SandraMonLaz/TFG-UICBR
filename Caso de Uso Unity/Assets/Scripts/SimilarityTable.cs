using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SimilarityTable
{
    private static SimilarityTable instance;
    private float[,] _table;

    public static SimilarityTable getInstance()
    {
        if(instance == null)
        {
            instance = new SimilarityTable();
            instance.createTable();
        }

        return instance;
    }

    public float[,] GetTable()
    {
        return _table;
    }

    private void createTable()
    {
        float[,] aux = 
					/* HEALTH  SCORE  SHIELDS   COLLECTABLE  WEAPONS ABILITIES  TIME  LEVEL_PROGRESS CHARACTER_INFO CHARACTER_PROGRESS*/	
/*HEALTH*/{             {1f,    0.3f,   0.8f,       0.3f,       0f,     0f,     0f,         0f,         0.75f,      0.75f},
/*SCORE*/				{0.3f,  1f,     0.3f,       0.8f,       0.3f,   0.3f,   0.75f,      0.6f,       0.2f,       0.2f},
/*SHIELDS*/				{0.8f,  0.3f,   1f,         0.3f,       0f,     0f,     0f,         0f,         0f,         0f},
/*COLLECTABLE*/			{0.3f,  0.8f,   0.3f,       1f,         0f,     0f,     0.6f,       0.3f,       0f,         0f},
/*WEAPONS*/				{0f,    0.3f,   0f,         0f,         1f,     0.8f,   0f,         0f,         0f,         0f},
/*ABILITIES*/			{0f,    0.3f,   0f,         0f,         0.8f,   1f,     0f,         0f,         0f,         0f},
/*TIME*/				{0f,    0.75f,  0f,         0.6f,       0f,     0f,     1f,         0.8f,       0.4f,       0.4f},
/*LEVEL_PROGRESS*/		{0f,    0.6f,   0f,         0.3f,       0f,     0f,     0.8f,       1f,         0.65f,      0.65f},
/*CHARACTER_INFO*/		{0.75f, 0.2f,   0f,         0f,         0f,     0f,     0.4f,       0.65f,      1f,         0.9f},
/*CHARACTER_PROGRESS*/	{0.75f, 0.2f,   0f,         0f,         0f,     0f,     0.4f,       0.65f,      0.9f,       1f}};

        _table = aux;
    }
}
