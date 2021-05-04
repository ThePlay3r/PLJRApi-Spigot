package me.pljr.pljrapispigot.sound

import com.cryptomorin.xseries.XSound
import org.bukkit.Sound

class SoundBuilder(var sound: Sound, var volume: Float, var pitch: Float) {

    constructor(xSound: XSound, volume: Float, pitch: Float) : this(xSound.parseSound()!!, volume, pitch)

    constructor(pljrSound: PLJRSound) : this(pljrSound.type, pljrSound.volume, pljrSound.pitch)

    constructor() : this(XSound.ENTITY_VILLAGER_NO, 1f, 1f)

    constructor(sound: Sound) : this(sound, 1f, 1f)

    constructor(xSound: XSound) : this(xSound.parseSound()!!)

    /**
     * Changes the current sound.
     *
     * @param sound [Sound] that will represent the new sound.
     * @return [SoundBuilder] with changed sound.
     */
    fun withSound(sound: Sound): SoundBuilder {
        this.sound = sound
        return this
    }

    /**
     * Changes the current volume.
     *
     * @param volume float that will represent the new volume.
     * @return [SoundBuilder] with changed volume.
     */
    fun withVolume(volume: Float): SoundBuilder {
        this.volume = volume
        return this
    }

    /**
     * Changes the current pitch.
     *
     * @param pitch float that will represent the new pitch.
     * @return [SoundBuilder] with changed pitch.
     */
    fun withPitch(pitch: Float): SoundBuilder {
        this.pitch = pitch
        return this
    }

    /**
     * Creates PLJRSound from selected values.
     *
     * @return [PLJRSound] consisting of all previously selected values.
     */
    fun create(): PLJRSound {
        return PLJRSound(sound, volume, pitch)
    }
}