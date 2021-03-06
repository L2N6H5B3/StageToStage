# StageToStage for ProPresenter 6

![alt text](https://raw.githubusercontent.com/L2N6H5B3/StageToStage/master/StandardLayout.png)

## StageToStage
StageToStage is a little Java-based app that I wrote to pull in the Stage Display data from ProPresenter and display current and next lyric slides, along with the stage display message, Video Counter, and Clock.


## Configuration and Use
StageToStage uses a ".properties" file for configuration information. Simply open config.properties just like you would any other text file to configure the IP/Hostname, ports, and passwords to use with StageToStage.

Download the latest StageToStage release from the [releases tab.](https://github.com/L2N6H5B3/ProPresenter-StageToStage-Stage-Display/releases)

**Quick-Exit:** To quick-exit the app, use "Q" on your keyboard while the app is in focus


## Layout Modes

### StandardLayout
![alt text](https://raw.githubusercontent.com/L2N6H5B3/StageToStage/master/StandardLayout.png)
This layout is the default layout, and is static.

### Custom(Auto)Layout
![alt text](https://raw.githubusercontent.com/L2N6H5B3/StageToStage/master/AutoLayout.png)
This layout is not enabled by default, and can be enabled using the "auto-layout" property in config.properties. It's designed to pull in the current layout from ProPresenter.

## New Features
* **New!** StageToStage has now migrated away from ".txt" file reading to a ".properties" file, allowing for an error-free experience.  Simply open config.properties just like you would any other text file.
* **New!** Auto IP scan mode (with specific pre-set port) is now available!  Simply specify the port to search inside config.properties, and set auto-scan to yes and StageToStage will search your local network for a machine running ProPresenter.  Very handy if your ProPresenter machine is assigned an IP through DHCP.
* **New!** Custom Layout Support is now available!  StageToStage can now automatically pull the Stage Display layout from ProPresenter!  This feature is currently in beta, but if you still want to give it a go, just enable the "auto-layout" option in config.properties.  (NOTE: the "current-font-size", "next-font-size", "clock-message-size", and "flash-colour" options only apply when auto-layout is not enabled, and StageToStage will only ever use the layout provided by ProPresenter during initial startup.)
* **New!** Rebuilt Data Object Engine has now been finished.  This allows for much greater expansion later on down the line to support additional features.
* **New!** Custom flash colour is now available for the standard layout!  Use the "flash-colour" property in config.properties to assign an RGB (255,255,255) value to the message flash.

## Upcoming Features
Some of the features that I'm working on for the future are:
* Ability for StageToStage to follow the currently selected Stage Display Layout in ProPresenter (requires complete overhaul of ProPresenter communication module to use WS instead of Socket)
* Live Slide Image support (via Base64 encoded images, requires complete overhaul of ProPresenter communication module to use WS instead of Socket)
* Support for Windowed mode (with auto-resizing elements)
* GUI for changing settings (via tray icon or button)
* Keep display alive when StageToStage is in focus


## Changelog
* Version 1.7: Added Custom(Auto) Layout Support, removed unused classes
* Version 1.6: Added Auto IP Scan capability, started deprecation of unused classes

**Credits:**
Inspiration for this project came from @JJHW149 and originally used some resources created by @DanielKhilgren, although the project has since migrated away from these resources.