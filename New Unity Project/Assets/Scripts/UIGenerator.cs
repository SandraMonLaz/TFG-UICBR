using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using UnityEngine.UI;

public class UIGenerator : MonoBehaviour
{
    public TextAsset jsonFile;
    public int elementOffset = 20;
    private SolCBRParser solCBR;
    private Vector2 resolution = new Vector2(1920, 1080);

    //Prefabs
    [SerializeField]
    private GameObject _UIParent;

    [SerializeField]
    private GameObject _UIImage;

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
                //Esquinas
                if(combined.screenPosition == ScreenPos.TOP_LEFT || combined.screenPosition == ScreenPos.TOP_RIGHT ||
                   combined.screenPosition == ScreenPos.BOTTOM_LEFT || combined.screenPosition == ScreenPos.BOTTOM_RIGHT)
                {
                    BuildCornerCombined(combined, combined.screenPosition, canvas);

                    switch (combined.screenPosition)
                    {

                    }
                }
                //Forzar alineacion horizontal
                else if(combined.screenPosition == ScreenPos.MIDDLE_LEFT || combined.screenPosition == ScreenPos.MIDDLE_RIGHT)
                {

                }
                //Forzar alineacion vertical
                else
                {

                }
            }
        }
    }

    private void BuildCornerCombined(Combined combined, ScreenPos screenPos, Canvas canvas)
    {
        float maxItemWidth = (resolution.x / 3.0f);
        float maxItemHeight = (resolution.y / 3.0f);

        //Instanciamos el pivote en la esquina inferior izquierda del canvas
        GameObject pivot = Instantiate(_UIParent, canvas.transform);

        //Creamos  una lista de conjuntos para los items y la ordenamos por importancia
        List<HashSet<ItemSolution>> hashList = Adaptation(ref combined);
        hashList.Sort(new HashItemSolutionComparer());

        //Para cada conjunto creamos los combinados asignandoles la informacion necesaria
        List<CombinedObject> combinedObjects = CreateCombinedObjects(ref hashList, maxItemWidth, maxItemHeight);

        //Asignamos al primero el padre
        combinedObjects[0].gameObject.transform.SetParent(pivot.transform);

        float total_width = combinedObjects[0].w + elementOffset;
        float total_height = combinedObjects[0].h + elementOffset;
        for (int i = 1; i < combinedObjects.Count; ++i){
            CombinedObject obj = combinedObjects[i];

            obj.gameObject.transform.SetParent(pivot.transform);
            RectTransform rect = obj.gameObject.GetComponent<RectTransform>();

            if(total_width > total_height)
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
        float scaleFactor = (float)combined.itemScale / (float)Scale.VERY_BIG;
        float combinedSizeX = maxItemWidth * scaleFactor;
        float combinedSizeY = maxItemHeight * scaleFactor;

        RectTransform rectCombain = pivot.GetComponent<RectTransform>();
        rectCombain.localScale = new Vector3(0.7f, 0.7f,0);

    }
    /// <summary>
    /// Crea una lista de conjuntos donde cada conjunto contiene items con similaridad.
    /// </summary>
    /// <param name="combined"></param>
    /// <returns></returns>
    private List<HashSet<ItemSolution>> Adaptation(ref Combined combined)
    {
        List<HashSet<ItemSolution>> combinedGroup = new List<HashSet<ItemSolution>>();

        //Se recorre cada item comparandolo con el resto
        foreach(ItemSolution item in combined.items)
        {
            foreach (ItemSolution otherItem in combined.items)
            {
                //En caso de ser muy similares se añaden a un hash
                if (SimilarityTable.getInstance().GetTable()[(int)item.itemId, (int)otherItem.itemId] >= 0.8f && 
                    item.itemId != otherItem.itemId)
                {
                    //Buscamos si alguno de los dos se encuentra en otro hash
                    HashSet<ItemSolution> set = SearchHash(ref combinedGroup, item, otherItem);
                    //Los añadimos
                    if(!set.Contains(item))
                        set.Add(item);
                    if (!set.Contains(otherItem))
                        set.Add(otherItem);
                     
                    if(!combinedGroup.Contains(set))
                        combinedGroup.Add(set);
                }
            }
        }
        //Añadimos los items que no estén en ningun hash para no perderlos
        foreach(ItemSolution item in combined.items)
        {
            HashSet<ItemSolution> hash = SearchHash(ref combinedGroup, item);
            if(hash == null)
            {
                hash = new HashSet<ItemSolution>();
                hash.Add(item);
                combinedGroup.Add(hash);
            }
        }

        return combinedGroup;
    }

    /// <summary>
    /// Devuelve el hash asignado a alguno de los dos items entrantes, en caso de no haberlo se devuelve un hash vacio
    /// </summary>
    /// <param name="combinedGroup"></param>
    /// <param name="item"></param>
    /// <param name="other"></param>
    /// <returns></returns>
    private HashSet<ItemSolution> SearchHash(ref List<HashSet<ItemSolution>> combinedGroup, ItemSolution item, ItemSolution other)
    {
        foreach(HashSet<ItemSolution> hash in combinedGroup)
        {
            if((hash.Contains(item)) || hash.Contains(other))
                return hash;
        }

        return new HashSet<ItemSolution>();
    }

    /// <summary>
    /// Devuelve el hash asignado el item, en caso de no haberlo se devuelve null
    /// </summary>
    /// <param name="combinedGroup"></param>
    /// <param name="item"></param>
    /// <param name="other"></param>
    /// <returns></returns>
    private HashSet<ItemSolution> SearchHash(ref List<HashSet<ItemSolution>> combinedGroup, ItemSolution item)
    {
        foreach (HashSet<ItemSolution> hash in combinedGroup)
        {
            if ((hash.Contains(item)))
                return hash;
        }

        return null;
    }
    /// <summary>
    /// Crea una lista de objetos combinados. Estos combinados contienen items que se colocarán en
    /// horizontal o vertical según su ancho u alto total.
    /// </summary>
    /// <param name="hashList"></param>
    /// <returns></returns>
    private List<CombinedObject> CreateCombinedObjects(ref List<HashSet<ItemSolution>> hashList, float maxItemWidth, float maxItemHeight)
    {
        List<CombinedObject> combinedObjects = new List<CombinedObject>();

        //para cada conjunto se coje los items del mismo
        //y se añaden a un padre verticalmente y horizontalmente
        foreach (HashSet<ItemSolution> hash in hashList)
        {
            //Creamos el padre
            CombinedObject co = new CombinedObject();

            co.gameObject = Instantiate(_UIParent);
            int w = 0;
            int h = 0;
            List<GameObject> children = new List<GameObject>();
            //Hallamos la suma del ancho y alto total de los items dentro
            //del set y ya de paso inicializamos cada item
            foreach(ItemSolution item in hash)
            {
                h += imgs[item.image].texture.height;
                w += imgs[item.image].texture.width;

                GameObject g = Instantiate(_UIImage, co.gameObject.transform);
                Image image = g.GetComponent<Image>();
                image.sprite = imgs[item.image];
                children.Add(g);
            }

            float acc_height = 0;
            float acc_width = 0;
            if(w > h)   //si el ancho total es mayor se pone en vertical
            {
                int i = 0;
                //Para cada item se haya la posicion nueva en vertical y se asigna
                foreach(ItemSolution item in hash)
                {
                    RectTransform rect = children[i].GetComponent<RectTransform>();
                    rect.position = new Vector3(0f, acc_height, 0f);

                    Vector2 size = GetSizeItem(item, imgs[item.image], ref rect, maxItemWidth, maxItemHeight);
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
                //Para cada item se haya la posicion nueva en horizontal y se asigna
                foreach (ItemSolution item in hash)
                {
                    RectTransform rect = children[i].GetComponent<RectTransform>();
                    rect.position = new Vector3(acc_width, 0f, 0f);

                    Vector2 size = GetSizeItem(item, imgs[item.image], ref rect, maxItemWidth, maxItemHeight);
                    acc_width += size.x + elementOffset;

                    ++i;

                    if (imgs[item.image].texture.height > acc_height)
                        acc_height = imgs[item.image].texture.height;
                }
            }
            //asignamos el ancho y alto del padre
            co.h = acc_height;
            co.w = acc_width;
            //Lo añadimos a la lista
            combinedObjects.Add(co);
        }

        return combinedObjects;
    }

    private Vector2 GetSizeItem(ItemSolution item,  Sprite sprite, ref RectTransform rectTr, float maxItemWidth, float maxItemHeight)
    {
        float itemScaleFactor = (float)item.itemScale / (float)Scale.VERY_BIG;
        int imageWidth = sprite.texture.width;
        int imageHeight = sprite.texture.height;

        float width = 0;
        float height = 0;
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

        return new Vector2(width, height);
    }
}
