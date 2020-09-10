# PLJRApi
Spigot plugin created to make development of other plugins as easy and efficient as possible for me.

----

## Really cool stuff, that the plugin is using
- HikariCP: https://github.com/brettwooldridge/HikariCP
- XSeries: https://github.com/CryptoMorin/XSeries
- MiniMessage: https://github.com/KyoriPowered/adventure-text-minimessage
- VaultAPI: https://github.com/MilkBowl/VaultAPI
- HolographicDisplays: https://github.com/filoghost/HolographicDisplays
- PlaceholderAPI: https://github.com/PlaceholderAPI/PlaceholderAPI
- ParticleLib: https://github.com/ByteZ1337/ParticleLib

## Features
- ConfigManager for easier usage of Bukkit's FileConfiguration
- QueryManager using Hikari for better performance
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) Support (Used by most of the Spigot's plugin, definitely should check out)
- [MiniMessage](https://docs.adventure.kyori.net/minimessage.html#usage) Support (A fantastic thing for message customization, also comes with RGB Support as of Spigot 1.16+)
- [Vault](https://www.spigotmc.org/resources/vault.34315/) Support (Used by most of the Spigot's plugin as well)

## ConfigManager Examples
### ItemStack:
```yaml
example:
	type: "GLOWSTONE" # XMaterial or Material here. (Default: Stone)
	# Everything under is optional and doesn't need to be included,
	# default values will be applied if so.
	amount: 1 #Integer from 1 to 64 (Default: 1)
	name: "§eShiny" #String that will be the display name (Default: none)
	lore: #List of Strings that will be the lore (Default: none)
		- "§7You better take some"
		- "§7sunglasses for this one!"
	enchantments: #List of XEnchantements of Enchantements (Default: none)
		#Format: <ENCHANT>:<LEVEL>
		- "DURABILITY:3"
```
- Materials: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
- Enchantements: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/enchantments/Enchantment.html

### Head:
Can be simply used at the place of ItemStack, by replacing `type` to `head-owner`.

Does **not** support enchantments.
```yaml
username-example: # Create Minecraft head from Player's username.
	head-owner: "9hx" # Name of the Player
	name: "§eHead of 9hx" # Same as ItemStack, explained above.
	amount: 1 # Same as ItemStack, explained above.
	lore: # Same as ItemStack, explained above.
		- "§7Head of the greatest one."

uuid-example: # Creates Minecraft head from Player's unique id.
	head-owner: "ef9e4f79-a04a-4b85-8472-94ada476e629" # UUID of the Player
	name: "§eHead of 9hx"
	amount:  1 
	lore:  
		-  "§7Head of the greatest one."

url-example: # Creates Minecraft head from Minecraft-URL
	head-owner: "ed1e9979289f03099a7c587d52d488e26e7bb17ab594b69f92438d77eabc" # Texture URL
	name: "§bTV"
	amount: 1
	lore:
		- "§7Do you guys still use these?"

base64-example: # Creates Minecraft head from Base64
	head-owner: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQxZTk5NzkyODlmMDMwOTlhN2M1ODdkNTJkNDg4ZTI2ZTdiYjE3YWI1OTRiNjlmOTI0MzhkNzdlYWJjIn19fQ==" # Base64 texture of the head.
	name: "§bTV"
	amount: 1
	lore:
		- "§7Do you guys still use these?"
```
- Television used in the examples: https://minecraft-heads.com/custom-heads/decoration/753-tv

### Title
```yaml
example:
	title: "§bBoo!" # The title message that will be displayed.
	subtitle: "§fHaha! §eI bet this scared you!" # The subtitle message that will be displayed.
	# 20 ticks = 1 second
	in: 0 # Time for Title to appear in ticks.
	stay: 40 # Time for Title to stay on the screen in ticks.
	out: 10 # Time for Title to disapear in ticks.
```

### ActionBar
```yaml
example:
	message: "§fThis message will be displayed for §b2 §fseconds!" # Displayed message.
	duration: 40 # Duration of the ActionBar in ticks. (20 ticks = 1 second) 
```

### Sound
```yaml
example:
	type: "CLICK" # Sound of the sound. (Yes, it does make sense)
	volume: 1 # Volume of the sound.
	pitch: 1 # Pitch of the sound.
```