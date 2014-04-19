HideyBarPhotoViewScreen
=======================

This library has been created for showing pictures like the native pictures gallery does. The screen actionbar will be hidden once the user enters the screen and after the time you've set has been reached. Then, once the actionbar is displayed again, it will be hidden again when the user taps the photo view.
   
You can load both pictures from resources or fetch a picture from an url using [**Picasso (Square)**](http://square.github.io/picasso/).

**Sample of request:**

```java
Intent hideyBarPhotoViewIntent = HideyBarPhotoViewIntent.newConfiguration()
                .setPhotoUrl("https://lh6.googleusercontent.com/-dJgpQyZK89k/UQOBedpoASI/AAAAAAAALuk/kWhI3-xIX1w/w1902-h1070-no/reunion.jpg",
                        new PicassoPhotoLoader().baseSetup()
                                .setPlaceHolderResId(R.drawable.ic_hidey_bar_photo_view_screen_placeholder)
                                .showProgressView(false))
                .timeToStartHideyMode(4000)
                .screenTitle("Reunion")
                .create(this, HideyBarPhotoViewScreen.class);
        startActivity(hideyBarPhotoViewIntent);
```

## Screenshots:

![Screenshot library status 0](screenshots/status0.png)
![Screenshot library status 1](screenshots/status1.png)
![Screenshot library status 2](screenshots/status2.png)

## Libraries:

[**Picasso (Square)**](http://square.github.io/picasso/).
[**PhotoView from Chris Banes**](https://github.com/chrisbanes/PhotoView)

## Photographies:

Photographs have been captured by Romain Guy.

## NOTE: 

This library needs some unit testing. I'll do in my free time. Pull requests and new features will be always welcome.
  
