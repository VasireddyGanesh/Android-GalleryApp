package com.example.galleryapp

class Image {
    var imageName:String? =null
    var imagePath:String? =null

    constructor(imageName: String?, imagePath: String?) {
        this.imageName = imageName
        this.imagePath = imagePath
    }
    constructor(){}
}

