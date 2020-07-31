# Notes

> This is a fork of the `cordova-plugin-kiosk` plugin by `khalinka` <https://github.com/hkalina/cordova-plugin-kiosk>. 
> If you don't know which Kiosk plugin to use, you might start out with `khalinka`'s plugin or with guatedude2's one under <https://github.com/guatedude2/cordova-plugin-kiosk-launcher> and only return here in case you miss the updates I did (see *changes* below).

# Cordova Kiosk Mode - changes

* Non-clickable area on top of screen has been made smaller
* Top menu is closed more aggressively
* Added `setCloseSystemDialogIntervalMillis(int)`, 
  `setCloseSystemDialogDurationMillis(int)` and `setKioskEnabled(bool)`

Find the original documumentation by `khalinka` at <https://github.com/hkalina/cordova-plugin-kiosk>.

# Intro

A Cordova plugin to create a Cordova application with *"kiosk mode"*.
An app with this plugin can be set as an Android launcher.
If the app starts as a launcher, it blocks hardware buttons and the statusbar, so a user cannot close the app (or switch to another app) until the app requests it. The plugin does not change behavior of the application until it is set as a *launcher*.

 ⚠️  ANDROID ONLY ⚠️


# Install

    cordova plugin add cordova-plugin-kiosk-ia

Android platform files (like `AndroidManifest.xml`) should be updated immediately. If you will modify plugin code, you will need to re-add android platform to plugin modifications take effect:

    cordova platform rm android
    cordova platform add android


# API

All methods should only be called after the `deviceready` event.

## Exit Kiosk

    KioskPlugin.exitKiosk();

This shows the application launcher which lets you choose which application to launch to. This way, you can exit your application by choosing the default desktop application.

## Check if Kiosk Plugin is enabled/active

    KioskPlugin.isInKiosk(function(isInKiosk){ ... });

## Detect whether the app is set as launcher:

    KioskPlugin.isSetAsLauncher(function(isLauncher){ ... });

## Defining allowed buttons

buttons whose event propagation should not be prevented - so you can for example allow setting volume up/down:

    KioskPlugin.setAllowedKeys([ 24, 25 ]); // KEYCODE_VOLUME_UP, KEYCODE_VOLUME_DOWN

For list of keycode values check KeyEvent reference: <https://developer.android.com/reference/android/view/KeyEvent#KEYCODE_0>


# Enable/disable kiosk mode

    KioskPlugin.setKioskEnabled(boolean);

This is not heavily tested and you might have to call it whenever your app is re-entered (`resume` event) after it went to the background or received the `pause` event.

# Set duration and frequency of statusbar/top-menu removal

    KioskPlugin.setCloseSystemDialogIntervalMillis(200)
    KioskPlugin.setCloseSystemDialogDurationMillis(20000)

Once the application loses focus (e.g. top menu pulled down), this will close any system dialogs (and the top-menu/statusbar) with the specified frequency for the specified duration. So in the above example it will close system dialogs every 200ms it for 20 seconds.
