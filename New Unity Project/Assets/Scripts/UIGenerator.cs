using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class UIGenerator : MonoBehaviour
{
    public TextAsset jsonFile;
    private SolCBRParser solCBR;

    public void generateUI()
    {
        solCBR = JsonUtility.FromJson<SolCBRParser>(jsonFile.text);
        this.gameObject.AddComponent<Canvas>();
    }
}
