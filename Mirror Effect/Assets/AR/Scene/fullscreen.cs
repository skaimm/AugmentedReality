﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class fullscreen : MonoBehaviour
{
    // Start is called before the first frame update
    void Start() =>
        // Toggle fullscreen
        Screen.fullScreen = !Screen.fullScreen;

    // Update is called once per frame
    void Update()
    {

    }
}
