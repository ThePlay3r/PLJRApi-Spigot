# PLJRApi
Spigot plugin created to make development of other plugins as easy and efficient as possible for me.

----
## BungeeCord Support
- If you want to have full BungeeCord support, please drag-n-drop [PLJRApi-Bungee](https://github.com/ThePlay3r/PLJRApi-Bungee/releases/latest) to your BungeeCord's plugin folder.

----

## Really cool stuff, that the plugin is using
- HikariCP: https://github.com/brettwooldridge/HikariCP
- XSeries: https://github.com/CryptoMorin/XSeries
- MiniMessage: https://github.com/KyoriPowered/adventure-text-minimessage
- VaultAPI: https://github.com/MilkBowl/VaultAPI
- HolographicDisplays: https://github.com/wherkamp/Holograms
- PlaceholderAPI: https://github.com/PlaceholderAPI/PlaceholderAPI
- ParticleLib: https://github.com/ByteZ1337/ParticleLib

## Importing to your project

- Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependencies>
    <dependency>
        <groupId>com.github.ThePlay3r</groupId>
        <artifactId>PLJRApi-Spigot</artifactId>
        <version>v{VERSION}</version>
    </dependency>
</dependencies>
```

- Gradle
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
    implementation 'com.github.ThePlay3r:PLJRApi-Spigot:v{VERSION}'
}
```

## Features
- ConfigManager for easier usage of Bukkit's FileConfiguration
- QueryManager using Hikari for better performance
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) Support (Used by most of the Spigot's plugin, definitely should check out)
- [MiniMessage](https://docs.adventure.kyori.net/minimessage.html#usage) Support (A fantastic thing for message customization, also comes with RGB Support as of Spigot 1.16+)
- [Vault](https://www.spigotmc.org/resources/vault.34315/) Support (Used by most of the Spigot's plugin as well)

## [Wiki](https://www.github.com/ThePlay3r/PLJRApi/wiki)