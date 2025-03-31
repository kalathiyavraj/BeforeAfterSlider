# <h1 align="center">Before and after image slider</h1>
<p align="center">
  <img src="[before-after-slider.gif](https://ibb.co/BVXvcfYC)"/>
</p>
The library uses Glide for image loading

---------------------------------------------------------

Fix: The image clip retains its last position when screen orientation changes.

---------------------------------------------------------

```java
    <com.image.swipe.beforeafterslider.BeforeAfterSlider
        android:id="@+id/before_after_slider_id"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        >

    </com.image.swipe.beforeafterslider.BeforeAfterSlider>
```

```kotlin
  //Inside java
  
  mySlider.setBeforeImage(imgUrl1).setAfterImage(imgUrl2)  
```

```kotlin
  //to change slider_thumb programmaticaly
  mySlider.setSliderThumb(yourDrawable)
```

```xml  
  <!--  to set images from xml  -->
  app:before_image="@mipmap/image1"
  app:after_image="@mipmap/image2"
```

<h1>Gradle dependency</h1>

```groovy        

//and add this to your module level build.gradle file
implementation 'com.github.kalathiyavraj:BeforeAfterSlider:Tag'

  
