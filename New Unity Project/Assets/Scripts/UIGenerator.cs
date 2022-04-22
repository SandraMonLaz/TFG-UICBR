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
    private Dictionary<string, Sprite> imgs;

    public void generateUI()
    {
        imgs = new Dictionary<string, Sprite>
        {
            {"vidaContinua", vidaContinua},
            {"vidaDiscreta", vidaDiscreta},
            {"puntos", puntuacion}
        };

        //Canvas
        CanvasScaler scaler = this.gameObject.GetComponent<CanvasScaler>();
        if (scaler != null) DestroyImmediate(scaler);
        Canvas canvas = this.gameObject.GetComponent<Canvas>();
        if (canvas != null) DestroyImmediate(canvas);

        canvas = this.gameObject.AddComponent<Canvas>();
        canvas.renderMode = RenderMode.ScreenSpaceOverlay;
        scaler = this.gameObject.AddComponent<CanvasScaler>();
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
                        break;
                    case ScreenPos.TOP_RIGHT:
                        break;
                    case ScreenPos.BOTTOM_LEFT:
                        BuildCornerCombined(combined, combined.screenPosition, canvas);
                        break;
                    case ScreenPos.BOTTOM_RIGHT:
                        break;
                    //Extremos vertical
                    case ScreenPos.TOP_CENTER:
                        break;
                    case ScreenPos.BOTTOM_CENTER:
                        break;
                    //Extremos horizontal
                    case ScreenPos.MIDDLE_LEFT:
                        break;
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

        float maxItemWidth = (maxX - minX) / (float)combined.items.Length;
        float maxItemHeight = (maxY - minY) / (float)combined.items.Length;

        //Instanciamos el pivote en la esquina inferior izquierda del canvas
        GameObject pivot = Instantiate(_UIItemPrefab, canvas.transform);
        RectTransform pivotRect = pivot.GetComponent<RectTransform>();
        pivotRect.anchoredPosition = new Vector2(0, 0);
        pivotRect.pivot = new Vector2(0, 0);
        pivotRect.position = new Vector3(0f, 0f, 0f);

        //Ordenar el array de items y quedarse con la mayor escala
        System.Array.Sort(combined.items, new ItemSolution.ItemSolutionComparer());
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
        image.sprite = imgs[item.image];

        //Tamaño del item (esta repetido abajo)
        //TODO: Llevar a un metodo?
        float itemScaleFactor = (float)item.itemScale / (float)Scale.VERY_BIG;
        int imageWidth = image.sprite.texture.width;
        int imageHeight = image.sprite.texture.height;
        RectTransform rectTr = UIItem.GetComponent<RectTransform>();
        float width = 0;
        float height = 0;
        if(imageWidth > imageHeight)
        {
            width = maxItemWidth * itemScaleFactor;
            height = width * ((float)imageHeight / (float)imageWidth);
        }
        else
        {
            height = maxItemHeight * itemScaleFactor;
            width = height * ((float)imageWidth / (float)imageHeight);
        }
        rectTr.sizeDelta = new Vector2(width, height);
        combinedWidth = width;
        combinedHeight = height;

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
            image.sprite = imgs[item.image];

            //Tamaño del item (esta repetido abajo)
            //TODO: Llevar a un metodo?
            itemScaleFactor = (float)item.itemScale / (float)Scale.VERY_BIG;
            imageWidth = image.sprite.texture.width;
            imageHeight = image.sprite.texture.height;
            rectTr = UIItem.GetComponent<RectTransform>();
            width = 0;
            height = 0;
            if (imageWidth > imageHeight)
            {
                width = maxItemWidth * itemScaleFactor;
                height = width * ((float)imageHeight / (float)imageWidth);
            }
            else
            {
                height = maxItemHeight * itemScaleFactor;
                width = height * ((float)imageWidth / (float)imageHeight);
            }
            rectTr.sizeDelta = new Vector2(width, height);

            //Colocarlo horizontalmente
            if (Mathf.Abs(image.sprite.rect.width - combinedWidth) > Mathf.Abs(image.sprite.rect.height - combinedHeight))
            {
                itemRect.position = new Vector3(combinedWidth, 0f, 0f);
                combinedWidth += width;
            }
            //Colocarlo verticalmente
            else
            {
                itemRect.position = new Vector3(0f, combinedHeight, 0f);
                combinedHeight += height;
            }
        }

        //Aplicar escala 

    }
}
