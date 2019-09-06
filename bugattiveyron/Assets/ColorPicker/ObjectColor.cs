using UnityEngine;
using System.Collections;

public class ObjectColor : MonoBehaviour {

	void OnSetColor(Color color)
	{
        
        foreach (Renderer r in GetComponentsInChildren<Renderer>())
        {
            r.material.shader = Shader.Find("Transparent/Diffuse");
            r.material.color = color;
        }
    }

	void OnGetColor(ColorPicker picker)
	{
		picker.NotifyColor(GetComponent<Renderer>().material.color);
	}

}
