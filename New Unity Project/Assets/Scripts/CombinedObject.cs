using UnityEngine;

public struct CombinedObject
{
    public GameObject gameObject;
    public float h;
    public float w;

    public CombinedObject(GameObject o, float h, float w)
    {
        this.gameObject = o;
        this.h = h;
        this.w = w;
    }
}
