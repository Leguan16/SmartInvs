![SmartInvs Logo](http://minuskube.fr/img/smart-invs/smart_invs.png)

[![License](https://img.shields.io/github/license/minuskube/smartinvs.svg?style=flat-square)](https://github.com/MinusKube/SmartInvs/blob/master/LICENSE.md)
[![Javadocs](https://img.shields.io/maven-central/v/io.github.leguan16/smartInvs.svg?label=javadoc&style=flat-square)](https://javadoc.io/doc/io.github.leguan16/smartInvs/latest/)

# SmartInvs
Advanced Inventory API for your Minecraft Bukkit plugins.

*Tested Minecraft versions: 1.17.1*  
**You can use this as a Plugin, or use it as a library** (see [the docs](https://minuskube.gitbook.io/smartinvs/))

### Disclaimer
This is **not** the original version of SmartInvs. 
It uses the updated Paper version so that you don't get the "Initializing Legacy materials" error message.
If you want to download the official version click [here](https://github.com/minuskube/smartinvs/).
## Features
* Inventories of any type (workbench, chest, furnace, ...)
* Customizable size when possible (chest, ...)
* Custom titles
* Allows to prevent the player from closing its inventory
* Custom listeners for the event related to the inventory
* Iterator for inventory slots
* Page system
* Util methods to fill an inventory's row/column/borders/...
* Actions when player clicks on an item
* Update methods to edit the content of the inventory every tick

## Docs
[Click here to read the docs on Gitbook](https://minuskube.gitbook.io/smartinvs/)

## Usage
To use the SmartInvs API, either:
- Put it in the `plugins` folder of your server, add it to your dependencies in your plugin.yml (e.g. `depend: [SmartInvs]`) and add it to the dependencies in your IDE.
- Put it inside your plugin jar, initialize an `InventoryManager` in your plugin (don't forget to call the `init()` method), and add a `.manager(invManager)` to your SmartInventory Builders.

You can download the latest version on the [Releases page](https://github.com/Leguan16/SmartInvs/releases/) on Github.

You can also use a build system:
### Gradle
```gradle
repositories {
    mavenCentral()
}

dependencies {
    compile 'io.github.leguan16:smartInvs:1.2.0'
}
```

### Maven
```xml
<dependency>
  <groupId>io.github.leguan16</groupId>
  <artifactId>smartInvs</artifactId>
  <version>1.2.0</version>
</dependency>
```

## TODO
* Add some Javadocs

## Issues
If you have a problem with the API, or you want to request a feature, make an issue [here](https://github.com/leguan16/SmartInvs/issues).
