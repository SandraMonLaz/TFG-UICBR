using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using UnityEngine.UI;

public class UIGenerator : MonoBehaviour
{
    [Tooltip("Json file with the interface data")]
    public TextAsset jsonFile;
    [Tooltip("Offset between each element set and the screen limits")]
    public int marginOffset = 10;
    [Tooltip("Offset between each element in the HUD")]
    public int elementOffset = 20;
    private Vector2 screenResolution = new Vector2(1920, 1080);

    //Prefabs
    private GameObject _UIParent;
    private GameObject _UIImage;
    private GameObject _UIBackground;

    //Im?genes
    public Sprite discreteLife;
    public Sprite continuousLife;
    public Sprite score;
    public Sprite discreteShieldsMana;
    public Sprite continuousShieldsMana;
    public Sprite infiniteWeapon;
    public Sprite limitedWeapon;
    public Sprite collectable;
    public Sprite infiniteAbility;
    public Sprite limitedAbility;
    public Sprite characterInfo;
    public Sprite levelProgressContinuous;
    public Sprite levelProgressDiscrete;
    public Sprite characterProgressContinuous;
    public Sprite characterProgressDiscrete;
    public Sprite timeChrono;
    public Sprite timeCountdown;

    private Dictionary<string, Sprite> imgs;

    public void generateUI()
    {
        _UIParent = UnityEditor.AssetDatabase.LoadAssetAtPath<GameObject>("Assets/Prefabs/UIParent.prefab");
        _UIImage = UnityEditor.AssetDatabase.LoadAssetAtPath<GameObject>("Assets/Prefabs/UIImagePrefab.prefab");
        _UIBackground = UnityEditor.AssetDatabase.LoadAssetAtPath<GameObject>("Assets/Prefabs/UIBackground.prefab");

        imgs = new Dictionary<string, Sprite>
        {
            {"vidaContinua", continuousLife},
            {"vidaDiscreta", discreteLife},
            {"puntos", score},
            {"escudosDiscretos", discreteShieldsMana},
            {"escudosContinuos", continuousShieldsMana},
            {"armaInfinita", infiniteWeapon},
            {"armaLimitada", limitedWeapon},
            {"collectable", collectable},
            {"habilidadInfinita", infiniteAbility},
            {"habilidadLimitada", limitedAbility},
            {"informacionPersonaje", characterInfo},
            {"progresoNivelContinuo", levelProgressContinuous},
            {"progresoNivelDiscreto", levelProgressDiscrete},
            {"progresoPersonajeContinuo", characterProgressContinuous},
            {"progresoPersonajeDiscreto", characterProgressDiscrete},
            {"tiempoChrono", timeChrono},
            {"tiempoCountdown", timeCountdown}
        };

        foreach (Transform child in transform)
            GameObject.DestroyImmediate(child.gameObject);

        //Canvas
        CanvasScaler scaler = this.gameObject.GetComponent<CanvasScaler>();
        if (scaler != null) DestroyImmediate(scaler);
        Canvas canvas = this.gameObject.GetComponent<Canvas>();
        if (canvas != null)
        {
            //Limpiamos todos los hijos antes de a?adir cualquier objeto
            for(int i = 0; i < canvas.gameObject.transform.childCount; ++i)
            {
                DestroyImmediate(canvas.gameObject.transform.GetChild(0).gameObject);
            }
            //Destruimos el canvas
            DestroyImmediate(canvas);
        }
        canvas = this.gameObject.AddComponent<Canvas>();
        canvas.renderMode = RenderMode.ScreenSpaceOverlay;

        scaler = this.gameObject.AddComponent<CanvasScaler>();
        scaler.uiScaleMode = CanvasScaler.ScaleMode.ScaleWithScreenSize;
        scaler.referenceResolution = screenResolution;
        scaler.screenMatchMode = CanvasScaler.ScreenMatchMode.Expand;

        //Instanciamos la imagen de fondo
        Instantiate(_UIBackground, canvas.transform);

        SolCBRParser solCBR = new SolCBRParser();
        solCBR = JsonUtility.FromJson<SolCBRParser>(jsonFile.text);
        SolCBR solEnums = new SolCBR(solCBR);
        foreach(Combined combined in solEnums.sol)
        {
            if (combined.items.Length > 0)
            {
                CombinedObject pivot;
                //Esquinas
                if (combined.screenPosition == ScreenPos.TOP_LEFT || combined.screenPosition == ScreenPos.TOP_RIGHT ||
                   combined.screenPosition == ScreenPos.BOTTOM_LEFT || combined.screenPosition == ScreenPos.BOTTOM_RIGHT)
                {
                    pivot = BuildCornerCombined(combined, canvas);
                    RectTransform rect = pivot.gameObject.GetComponent<RectTransform>();

                    LocateCombinedInCorner(ref rect, combined.screenPosition);
                }
                //Forzar alineacion horizontal
                else if(combined.screenPosition == ScreenPos.MIDDLE_LEFT || combined.screenPosition == ScreenPos.MIDDLE_RIGHT)
                {
                    pivot = BuildHorizontalCombined(combined, canvas);
                    RectTransform rect = pivot.gameObject.GetComponent<RectTransform>();
                    if (combined.screenPosition == ScreenPos.MIDDLE_RIGHT)
                        ReverseLateralCombined(ref rect);
                }
                //Forzar alineacion vertical
                else
                {
                    pivot = BuildVerticalCombined(combined, canvas);
                    RectTransform rect = pivot.gameObject.GetComponent<RectTransform>();
                    if(combined.screenPosition == ScreenPos.TOP_CENTER)
                        ReverseBottomCombined(ref rect);
                }

                ApplyMargins(combined.screenPosition, pivot);
            }
        }
    }

    private float GetItemScaleFactor(Scale scale)
    {
        float result = 1f;
        switch (scale)
        {
            case Scale.VERY_BIG:
                result = 1f;
                break;
            case Scale.BIG:
                result = 0.85f;
                break;
            case Scale.MEDIUM:
                result = 0.7f;
                break;
            case Scale.SMALL:
                result = 0.6f;
                break;
            case Scale.VERY_SMALL:
                result = 0.5f;
                break;
        }

        return result;
    }

    /// <summary>
    /// Construye un combined de clase esquina
    /// </summary>
    /// <returns></returns>
    private CombinedObject BuildCornerCombined(Combined combined, Canvas canvas)
    {
        //Instanciamos el pivote en la esquina inferior izquierda del canvas
        GameObject pivot = Instantiate(_UIParent, canvas.transform);

        //Creamos  una lista de conjuntos para los items y la ordenamos por importancia
        List<List<ItemSolution>> combinedList = Adaptation(ref combined);
        combinedList.Sort(new HashItemSolutionComparerInOrder());


        //Para cada conjunto creamos los combinados asignandoles la informacion necesaria
        List<CombinedObject> combinedObjects = CreateCombinedObjects(ref combinedList, Vector2.zero);

        //Asignamos al primero el padre
        combinedObjects[0].gameObject.transform.SetParent(pivot.transform);

        float total_width = combinedObjects[0].w + elementOffset;
        float total_height = combinedObjects[0].h + elementOffset;
        for (int i = 1; i < combinedObjects.Count; ++i){
            CombinedObject obj = combinedObjects[i];

            obj.gameObject.transform.SetParent(pivot.transform);
            RectTransform rect = obj.gameObject.GetComponent<RectTransform>();

            if (total_width > total_height)
            {
                rect.position = new Vector3(0, total_height, 0);
                total_height += obj.h + elementOffset;
            }
            else
            {
                rect.position = new Vector3(total_width, 0, 0);
                total_width += obj.w + elementOffset;
            }
        }
        float maxItemWidth = (screenResolution.x / 3.0f);
        float maxItemHeight = (screenResolution.y / 3.0f);

        //float scaleFactor = GetItemScaleFactor(combined.itemScale);
        float combinedSizeX = maxItemWidth; //* scaleFactor;
        float combinedSizeY = maxItemHeight; //* scaleFactor;

        /*float scaleRect = 0;
        RectTransform rectCombined = pivot.GetComponent<RectTransform>();
        if (total_width > total_height)
            scaleRect = combinedSizeX / total_width;
        else
            scaleRect = combinedSizeY / total_height;
        */
        //rectCombined.localScale = new Vector3(scaleRect, scaleRect, 0f);
        return new CombinedObject(pivot, combinedSizeY, combinedSizeX);
    }

    /// <summary>
    /// Crea un combinado alineado completamente en vertical (posiciones superior e inferior central de la pantalla)
    /// </summary>
    private CombinedObject BuildVerticalCombined(Combined combined, Canvas canvas)
    {
        //Instanciamos el pivote en la parte inferior central del canvas
        GameObject pivot = Instantiate(_UIParent, canvas.transform);
        RectTransform pivotRect = pivot.GetComponent<RectTransform>();
        pivotRect.anchorMin = pivotRect.anchorMax = new Vector2(0.5f, 0f);
        pivotRect.anchoredPosition = Vector2.zero;

        //Creamos  una lista de conjuntos para los items y la ordenamos por importancia
        List<List<ItemSolution>> combinedList = Adaptation(ref combined);
        combinedList.Sort(new HashItemSolutionComparerInOrder());

        //Para cada conjunto creamos los combinados asignandoles la informacion necesaria
        List<CombinedObject> combinedObjects = CreateCombinedObjects(ref combinedList, new Vector2(0.5f, 0f));

        //Asignamos al primero el padre
        combinedObjects[0].gameObject.transform.SetParent(pivot.transform);
        combinedObjects[0].gameObject.GetComponent<RectTransform>().localPosition = Vector3.zero;

        float total_height = combinedObjects[0].h + elementOffset;
        for (int i = 1; i < combinedObjects.Count; ++i)
        {
            CombinedObject obj = combinedObjects[i];

            obj.gameObject.transform.SetParent(pivot.transform);
            RectTransform rect = obj.gameObject.GetComponent<RectTransform>();

            rect.localPosition = new Vector3(0, total_height, 0);
            rect.pivot = new Vector2(0.5f, 0);
            total_height += obj.h + elementOffset;
        }
        float maxItemWidth = (screenResolution.x / 3.0f);
        float maxItemHeight = (screenResolution.y / 3.0f);

        //float scaleFactor = GetItemScaleFactor(combined.itemScale);
        float combinedSizeX = maxItemWidth;     // * scaleFactor;
        float combinedSizeY = maxItemHeight;    // * scaleFactor;

        RectTransform rectCombain = pivot.GetComponent<RectTransform>();

        float scaleRect = combinedSizeY / total_height;

        rectCombain.localScale = new Vector3(scaleRect, scaleRect, 0f);
        return new CombinedObject(pivot, combinedSizeY, combinedSizeX);
    }

    /// <summary>
    /// Crea un combinado alineado completamente en horizontal para las posiciones laterales de pantalla
    /// </summary>
    /// <returns></returns>
    private CombinedObject BuildHorizontalCombined(Combined combined, Canvas canvas)
    {
        //Instanciamos el pivote en la esquina inferior izquierda del canvas
        GameObject pivot = Instantiate(_UIParent, canvas.transform);
        RectTransform pivotRect = pivot.GetComponent<RectTransform>();
        pivotRect.anchorMin = pivotRect.anchorMax = new Vector2(0f, 0.5f);
        pivotRect.anchoredPosition = Vector2.zero;

        //Creamos  una lista de conjuntos para los items y la ordenamos por importancia
        List<List<ItemSolution>> combinedList = Adaptation(ref combined);
        combinedList.Sort(new HashItemSolutionComparerInOrder());


        //Para cada conjunto creamos los combinados asignandoles la informacion necesaria
        List<CombinedObject> combinedObjects = CreateCombinedObjects(ref combinedList, new Vector2(0f, 0.5f));

        //Asignamos al primero el padre
        combinedObjects[0].gameObject.transform.SetParent(pivot.transform);
        combinedObjects[0].gameObject.GetComponent<RectTransform>().localPosition = Vector3.zero;

        float total_width = combinedObjects[0].w + elementOffset;
        for (int i = 1; i < combinedObjects.Count; ++i)
        {
            CombinedObject obj = combinedObjects[i];

            obj.gameObject.transform.SetParent(pivot.transform);
            RectTransform rect = obj.gameObject.GetComponent<RectTransform>();

            rect.localPosition = new Vector3(total_width, 0f, 0f);
            total_width += obj.w + elementOffset;
        }
        float maxItemWidth = (screenResolution.x / 3.0f);
        float maxItemHeight = (screenResolution.y / 3.0f);

        //float scaleFactor = GetItemScaleFactor(combined.itemScale);
        float combinedSizeX = maxItemWidth;  // * scaleFactor;
        float combinedSizeY = maxItemHeight; // * scaleFactor;

        RectTransform rectCombined = pivot.GetComponent<RectTransform>();
        
        float scaleRect = combinedSizeX / total_width;

        rectCombined.localScale = new Vector3(scaleRect, scaleRect, 0f);
        return new CombinedObject(pivot, combinedSizeY, combinedSizeX);
    }

    /// <summary>
    /// Crea una lista de conjuntos donde cada conjunto contiene items con similaridad.
    /// </summary>
    /// <param name="combined"></param>
    /// <returns></returns>
    private List<List<ItemSolution>> Adaptation(ref Combined combined)
    {
        List<List<ItemSolution>> combinedGroup = new List<List<ItemSolution>>();

        //Se recorre cada item comparandolo con el resto
        foreach(ItemSolution item in combined.items)
        {
            foreach (ItemSolution otherItem in combined.items)
            {
                //En caso de ser muy similares se a?aden a una lista
                if (SimilarityTable.getInstance().GetTable()[(int)item.itemId, (int)otherItem.itemId] >= 0.8f && 
                    item != otherItem)
                {
                    //Buscamos si alguno de los dos se encuentra en otra lista
                    List<ItemSolution> set = SearchList(ref combinedGroup, item, otherItem);

                    //Los a?adimos
                    if(!set.Contains(item))
                        set.Add(item);
                    if (!set.Contains(otherItem))
                        set.Add(otherItem);
                     
                    if(!combinedGroup.Contains(set))
                        combinedGroup.Add(set);
                }
            }
        }

        foreach(List<ItemSolution> combinedList in combinedGroup)
        {
            combinedList.Sort(new ItemSolutionComparer());
        }

        //A?adimos los items que no est?n en ninguna lista para no perderlos
        foreach(ItemSolution item in combined.items)
        {
            List<ItemSolution> hash = SearchList(ref combinedGroup, item);
            if(hash == null)
            {
                hash = new List<ItemSolution>();
                hash.Add(item);
                combinedGroup.Add(hash);
            }
        }

        return combinedGroup;
    }

    /// <summary>
    /// Devuelve la lista asignada a alguno de los dos items entrantes, en caso de no haberlo se devuelve una lista vacio
    /// </summary>
    /// <param name="combinedGroup"></param>
    /// <param name="item"></param>
    /// <param name="other"></param>
    /// <returns></returns>
    private List<ItemSolution> SearchList(ref List<List<ItemSolution>> combinedGroup, ItemSolution item, ItemSolution other)
    {
        foreach(List<ItemSolution> list in combinedGroup)
        {
            if((list.Contains(item)) || list.Contains(other))
                return list;
        }

        return new List<ItemSolution>();
    }

    /// <summary>
    /// Devuelve el hash asignado el item, en caso de no haberlo se devuelve null
    /// </summary>
    /// <param name="combinedGroup"></param>
    /// <param name="item"></param>
    /// <param name="other"></param>
    /// <returns></returns>
    private List<ItemSolution> SearchList(ref List<List<ItemSolution>> combinedGroup, ItemSolution item)
    {
        foreach (List<ItemSolution> list in combinedGroup)
        {
            if ((list.Contains(item)))
                return list;
        }

        return null;
    }
    /// <summary>
    /// Crea una lista de objetos combinados. Estos combinados contienen items que se colocar?n en
    /// horizontal o vertical seg?n su ancho u alto total.
    /// </summary>
    /// <param name="hashList"></param>
    /// <returns></returns>
    private List<CombinedObject> CreateCombinedObjects(ref List<List<ItemSolution>> hashList, Vector2 imagePivots)
    {
        List<CombinedObject> combinedObjects = new List<CombinedObject>();

        //para cada conjunto se coje los items del mismo
        //y se a?aden a un padre verticalmente y horizontalmente
        foreach (List<ItemSolution> list in hashList)
        {
            //Creamos el padre
            CombinedObject co = new CombinedObject();

            co.gameObject = Instantiate(_UIParent);
            int w = 0;
            int h = 0;
            List<GameObject> children = new List<GameObject>();
            //Hallamos la suma del ancho y alto total de los items dentro
            //del set y ya de paso inicializamos cada item
            foreach(ItemSolution item in list)
            {
                h += imgs[item.image].texture.height;
                w += imgs[item.image].texture.width;

                GameObject g = Instantiate(_UIImage, co.gameObject.transform);
                Image image = g.GetComponent<Image>();
                image.sprite = imgs[item.image];
                RectTransform rect = image.GetComponent<RectTransform>();
                rect.pivot = imagePivots;
                children.Add(g);
            }

            float acc_height = 0;
            float acc_width = 0;
            if(w > h)   //si el ancho total es mayor se pone en vertical
            {
                int i = 0;
                //Para cada item se obtiene su posicion nueva en vertical y se asigna
                foreach(ItemSolution item in list)
                {
                    RectTransform rect = children[i].GetComponent<RectTransform>();
                    rect.position = new Vector3(0f, acc_height, 0f);

                    Vector2 size = GetSizeItem(item, imgs[item.image], ref rect);
                    acc_height += size.y + elementOffset;

                    rect.sizeDelta = size;

                    ++i;

                    if (imgs[item.image].texture.width > acc_width)
                        acc_width = imgs[item.image].texture.width;
                }
            }
            else  //sino se pone en horizontal
            {
                int i = 0;
                //Para cada item se obtiene su posicion nueva en horizontal y se asigna
                foreach (ItemSolution item in list)
                {
                    RectTransform rect = children[i].GetComponent<RectTransform>();
                    rect.position = new Vector3(acc_width, 0f, 0f);

                    Vector2 size = GetSizeItem(item, imgs[item.image], ref rect);
                    acc_width += size.x + elementOffset;

                    rect.sizeDelta = size;

                    ++i;

                    if (imgs[item.image].texture.height > acc_height)
                        acc_height = imgs[item.image].texture.height;
                }
            }
            //asignamos el ancho y alto del padre
            co.h = acc_height;
            co.w = acc_width;
            //Lo a?adimos a la lista
            combinedObjects.Add(co);
        }

        return combinedObjects;
    }

    private Vector2 GetSizeItem(ItemSolution item,  Sprite sprite, ref RectTransform rectTr)
    {
        float itemScaleFactor = (float)item.itemScale / (float)Scale.VERY_BIG;
        int imageWidth = sprite.texture.width;
        int imageHeight = sprite.texture.height;

        float width = 0;
        float height = 0;
        if (imageWidth > imageHeight)
        {
            width = imageWidth * itemScaleFactor;
            height = width * ((float)imageHeight / (float)imageWidth);
        }
        else
        {
            height = imageHeight * itemScaleFactor;
            width = height * ((float)imageWidth / (float)imageHeight);
        }

        return new Vector2(width, height);
    }

    /// <summary>
    /// Desplaza un rect que representa un combined item a una de las esquinas
    /// </summary>
    /// <param name="rect"> Rect padre del combined item</param>
    /// <param name="screenPosition"> Posicion de la pantalla donde hay que mover el rect </param>
    private void LocateCombinedInCorner(ref RectTransform rect, ScreenPos screenPosition)
    {
        Vector2 anchor = Vector2.zero;
        Vector2 anchoredPositionFactor = Vector2.zero;

        //Se eligen los valores de anchor y anchoredPositionFactor dependiendo de a donde se va a desplazar el rect
        switch (screenPosition)
        {
            case ScreenPos.TOP_LEFT:
                anchor = new Vector2(0f, 1f);
                anchoredPositionFactor = new Vector2(1f, -1f);
                break;
            case ScreenPos.TOP_RIGHT:
                anchor = new Vector2(1f, 1f);
                anchoredPositionFactor = new Vector2(-1f, -1f);
                break;
            case ScreenPos.BOTTOM_RIGHT:
                anchoredPositionFactor = new Vector2(-1f, 1f);
                anchor = new Vector2(1f, 0f);
                break;
            case ScreenPos.BOTTOM_LEFT:
                return;
        }

        //Set del anchor del padre y su posicion a 0
        rect.anchorMin = rect.anchorMax = anchor;
        rect.anchoredPosition = Vector2.zero;

        //Para cada hijo se asigna su posicion en x e y multiplicada por el factor correspondiente (1 o -1) determinados por la posicion de pantalla destino
        for (int i = 0; i < rect.childCount; i++)
        {
            RectTransform groupRectTr = rect.GetChild(i).GetComponent<RectTransform>();
            groupRectTr.anchorMin = groupRectTr.anchorMax = anchor;
            groupRectTr.anchoredPosition = new Vector2(groupRectTr.anchoredPosition.x * anchoredPositionFactor.x, groupRectTr.anchoredPosition.y * anchoredPositionFactor.y);

            //Set del anchor y posicion a 0 del UIImagePrefab
            for (int j = 0; j < groupRectTr.childCount; j++)
            {
                RectTransform itemRectTr = groupRectTr.GetChild(j).GetComponent<RectTransform>();
                itemRectTr.pivot = anchor;
                itemRectTr.anchoredPosition *= anchoredPositionFactor.y;
            }
        }
    }

    /// <summary>
    /// Invierte los elementos a la posicion superior de la pantalla
    /// </summary>
    /// <param name="rect"></param>
    private void ReverseBottomCombined(ref RectTransform rect)
    {
        Vector2 anchor = new Vector2(0.5f, 1f);
        Vector2 anchoredPositionFactor = new Vector2(1f, -1f);

        //Set del anchor del padre y su posicion a 0
        rect.anchorMin = rect.anchorMax = anchor;
        rect.anchoredPosition = Vector2.zero;

        //Para cada hijo se asigna su posicion en x e y multiplicada por el factor correspondiente (1 o -1) determinados por la posicion de pantalla destino
        for (int i = 0; i < rect.childCount; i++)
        {
            RectTransform groupRectTr = rect.GetChild(i).GetComponent<RectTransform>();
            groupRectTr.anchorMin = groupRectTr.anchorMax = anchor;
            groupRectTr.anchoredPosition = new Vector2(groupRectTr.anchoredPosition.x * anchoredPositionFactor.x, groupRectTr.anchoredPosition.y * anchoredPositionFactor.y);

            //Set del anchor y posicion a 0 del UIImagePrefab
            for (int j = 0; j < groupRectTr.childCount; j++)
            {
                RectTransform itemRectTr = groupRectTr.GetChild(j).GetComponent<RectTransform>();
                itemRectTr.pivot = anchor;
                itemRectTr.anchoredPosition *= -1f;
            }
        }
    }

    /// <summary>
    /// Invierte los elementos a la posicion derecha de la pantalla
    /// </summary>
    /// <param name="rect"></param>
    private void ReverseLateralCombined(ref RectTransform rect)
    {
        Vector2 anchor = new Vector2(1f, 0.5f);
        Vector2 anchoredPositionFactor = new Vector2(-1f, 1f);

        //Set del anchor del padre y su posicion a 0
        rect.anchorMin = rect.anchorMax = anchor;
        rect.anchoredPosition = Vector2.zero;

        //Para cada hijo se asigna su posicion en x e y multiplicada por el factor correspondiente (1 o -1) determinados por la posicion de pantalla destino
        for (int i = 0; i < rect.childCount; i++)
        {
            RectTransform groupRectTr = rect.GetChild(i).GetComponent<RectTransform>();
            groupRectTr.anchorMin = groupRectTr.anchorMax = anchor;
            groupRectTr.anchoredPosition = new Vector2(groupRectTr.anchoredPosition.x * anchoredPositionFactor.x, groupRectTr.anchoredPosition.y * anchoredPositionFactor.y);

            //Set del anchor y posicion a 0 del UIImagePrefab
            for (int j = 0; j < groupRectTr.childCount; j++)
            {
                RectTransform itemRectTr = groupRectTr.GetChild(j).GetComponent<RectTransform>();
                itemRectTr.pivot = anchor;
                itemRectTr.anchoredPosition *= -1f;
            }
        }
    }

    private void ApplyMargins(ScreenPos screenPos, CombinedObject combined)
    {
        Vector2 margin = Vector2.zero;

        switch (screenPos)
        {
            case ScreenPos.TOP_LEFT:
                margin = new Vector2(marginOffset, -marginOffset);
                break;
            case ScreenPos.TOP_CENTER:
                margin = new Vector2(0f, -marginOffset);
                break;
            case ScreenPos.TOP_RIGHT:
                margin = new Vector2(-marginOffset, -marginOffset);
                break;
            case ScreenPos.MIDDLE_LEFT:
                margin = new Vector2(marginOffset, 0f);
                break;
            case ScreenPos.MIDDLE_CENTER:
                margin = new Vector2(0f, 0f);
                break;
            case ScreenPos.MIDDLE_RIGHT:
                margin = new Vector2(-marginOffset, 0f);
                break;
            case ScreenPos.BOTTOM_LEFT:
                margin = new Vector2(marginOffset, marginOffset);
                break;
            case ScreenPos.BOTTOM_CENTER:
                margin = new Vector2(0f, marginOffset);
                break;
            case ScreenPos.BOTTOM_RIGHT:
                margin = new Vector2(-marginOffset, marginOffset);
                break;
        }


        RectTransform parent = combined.gameObject.GetComponent<RectTransform>();
        parent.anchoredPosition = margin;
        
    }
}
