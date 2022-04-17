using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using UnityEngine.UI;

public class UIGenerator : MonoBehaviour
{
    public TextAsset jsonFile;
    private SolCBRParser solCBR;
    private Vector2 resolution = new Vector2(1920, 1080);

    //Imágenes
    public Sprite vidaDiscreta;
    public Sprite vidaContinua;
    public Sprite puntuacion;

    public void generateUI()
    {
        //Canvas
        Canvas canvas = this.gameObject.AddComponent<Canvas>();
        canvas.renderMode = RenderMode.ScreenSpaceOverlay;
        CanvasScaler scaler = this.gameObject.AddComponent<CanvasScaler>();
        scaler.uiScaleMode = CanvasScaler.ScaleMode.ScaleWithScreenSize;
        scaler.referenceResolution = resolution;
        scaler.screenMatchMode = CanvasScaler.ScreenMatchMode.Expand;

        solCBR = JsonUtility.FromJson<SolCBRParser>(jsonFile.text);
        SolCBR solEnums = new SolCBR(solCBR);
        foreach(Combined combined in solEnums.sol)
        {
            if (combined.items.Length > 0)
            {
                float minX, maxX, minY, maxY;
                float maxItemX, maxItemY;
                float scaleFactor = (float)combined.itemScale / (float)Scale.VERY_BIG;

                bool[] colocados = new bool[combined.items.Length];
                for (int i = 0; i < colocados.Length; i++) colocados[i] = false;
                bool todosColocados = false;

                Scale maxScale = combined.items[0].itemScale;

                switch (combined.screenPosition)
                {
                    case ScreenPos.TOP_LEFT:

                        break;
                    case ScreenPos.TOP_CENTER:
                        break;
                    case ScreenPos.TOP_RIGHT:
                        break;

                    case ScreenPos.MIDDLE_LEFT:
                        break;
                    case ScreenPos.MIDDLE_CENTER:
                        break;
                    case ScreenPos.MIDDLE_RIGHT:
                        break;

                    case ScreenPos.BOTTOM_LEFT:

                        minX = 0 * scaleFactor; 
                        minY = 0 * scaleFactor;
                        maxX = (resolution.x / 3.0f) * scaleFactor;
                        maxY = (resolution.y / 3.0f) * scaleFactor;

                        maxItemX = (maxX - minX) / (float)combined.items.Length;
                        maxItemY = (maxY - minY) / (float)combined.items.Length;

                        while (!todosColocados)
                        {
                            todosColocados = true;
                            for(int j=0; j< combined.items.Length; j++)
                            {
                                if (colocados[j] == false) todosColocados = false;

                            }
                        }

                        break;
                    case ScreenPos.BOTTOM_CENTER:
                        break;
                    case ScreenPos.BOTTOM_RIGHT:
                        break;
                }

            }
        }
    }
}
