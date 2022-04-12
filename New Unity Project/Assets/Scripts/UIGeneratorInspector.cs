using UnityEditor;
using UnityEngine;

[CustomEditor(typeof(UIGenerator))]
public class UIGeneratorInspector : Editor
{
    private UIGenerator generator;
    //public TextAsset jsonFile;

    private void OnEnable()
    {
        generator = target as UIGenerator;
    }

    public override void OnInspectorGUI()
    {
        base.OnInspectorGUI();

        if (GUILayout.Button("Create UI"))
        {

            generator.generateUI();
        }
    }
}
