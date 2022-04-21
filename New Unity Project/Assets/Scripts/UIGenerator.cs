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

    //Prefabs
    [SerializeField]
    private GameObject _UIItemPrefab;

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
                switch (combined.screenPosition)
                {
                    case ScreenPos.TOP_LEFT:
                    case ScreenPos.TOP_RIGHT:
                    case ScreenPos.BOTTOM_LEFT:
                    case ScreenPos.BOTTOM_RIGHT:
                        BuildCornerCombined(combined, combined.screenPosition, canvas);
                        break;
                    //Extremos vertical
                    case ScreenPos.TOP_CENTER:
                    case ScreenPos.BOTTOM_CENTER:
                        break;
                    //Extremos horizontal
                    case ScreenPos.MIDDLE_LEFT:
                    case ScreenPos.MIDDLE_RIGHT:
                        break;
                    //--------------------------
                    case ScreenPos.MIDDLE_CENTER:
                        break;
                }

            }
        }
    }

    private void BuildCornerCombined(Combined combined, ScreenPos screenPos, Canvas canvas)
    {
        float minX, maxX, minY, maxY;
        float combinedWidth, combinedHeight;

        float scaleFactor = (float)combined.itemScale / (float)Scale.VERY_BIG;

        minX = 0;
        minY = 0;
        maxX = (resolution.x / 3.0f) * scaleFactor;
        maxY = (resolution.y / 3.0f) * scaleFactor;

        //Instanciamos el pivote en la esquina inferior izquierda del canvas
        GameObject pivot = Instantiate(_UIItemPrefab, canvas.transform);
        RectTransform pivotRect = pivot.GetComponent<RectTransform>();
        pivotRect.anchoredPosition = new Vector2(0, 0);
        pivotRect.pivot = new Vector2(0, 0);
        pivotRect.position = new Vector3(0f, 0f, 0f);

        //Ordenar el array de items y quedarse con la mayor escala
        System.Array.Sort(combined.items);
        Scale maxScale = combined.items[0].itemScale;

        //Instanciar cada objeto del combined entrante
        //Comenzamos por el primero de todos(mas prioritario)
        ItemSolution item = combined.items[0];
        GameObject UIItem = Instantiate(_UIItemPrefab, pivot.transform);

        RectTransform itemRect = UIItem.GetComponent<RectTransform>();
        itemRect.anchoredPosition = new Vector2(0, 0);
        itemRect.pivot = new Vector2(0, 0);
        itemRect.position = new Vector3(0f, 0f, 0f);

        UIItem.AddComponent<CanvasRenderer>();

        Image image = UIItem.AddComponent<Image>();
        //image.sprite = * CONSULTA DICCIONARIO STRING -> IMAGE *;
        combinedWidth = image.sprite.rect.width;
        combinedHeight = image.sprite.rect.height;

        //Resto de items
        for(int i = 1; i < combined.items.Length; ++i)
        {
            item = combined.items[i];
            UIItem = Instantiate(_UIItemPrefab, pivot.transform);

            itemRect = UIItem.GetComponent<RectTransform>();
            itemRect.anchoredPosition = new Vector2(0, 0);
            itemRect.pivot = new Vector2(0, 0);

            UIItem.AddComponent<CanvasRenderer>();

            image = UIItem.AddComponent<Image>();
            //image.sprite = * CONSULTA DICCIONARIO STRING -> IMAGE *;
            
            //Colocarlo horizontalmente
            if(Mathf.Abs(image.sprite.rect.width - combinedWidth) > Mathf.Abs(image.sprite.rect.height - combinedHeight))
            {
                itemRect.position = new Vector3(combinedWidth, 0f, 0f);
                combinedWidth += image.sprite.rect.width;
            }
            //Colocarlo verticalmente
            else
            {
                itemRect.position = new Vector3(0f, combinedHeight, 0f);
                combinedHeight += image.sprite.rect.height;
            }
        }

        //Aplicar escala 

    }
}
