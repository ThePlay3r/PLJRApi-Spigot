package me.pljr.pljrapispigot.builders;

import com.cryptomorin.xseries.XSound;
import me.pljr.pljrapispigot.objects.PLJRSound;
import org.bukkit.Sound;

public class SoundBuilder {
    private Sound sound;
    private float volume;
    private float pitch;


    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    /**
     * Creates a default, empty SoundBuilder.
     */
    public SoundBuilder(){
        this(Sound.VILLAGER_NO, 1, 1);
    }

    /**
     * Creates an SoundBuilder with selected sound.
     *
     * @param sound {@link Sound} that will represent the sound.
     */
    public SoundBuilder(Sound sound){
        this(sound, 1, 1);
    }

    /**
     * Creates an SoundBuilder with selected sound and volume.
     *
     * @param sound {@link Sound} that will represent the sound.
     * @param volume float that will represent the volume.
     */
    public SoundBuilder(Sound sound, float volume){
        this(sound, volume, 1);
    }

    /**
     * Creates an SoundBuilder with selected sound and volume.
     *
     * @param sound {@link XSound} that will represent the sound.
     * @param volume float that will represent the volume.
     * @param pitch float that will represent the pitch.
     */
    public SoundBuilder(XSound sound, float volume, float pitch){
        this(sound.parseSound(), volume, pitch);
    }

    /**
     * Creates an SoundBuilder with selected sound and volume.
     *
     * @param sound {@link Sound} that will represent the sound.
     * @param volume float that will represent the volume.
     * @param pitch float that will represent the pitch.
     */
    public SoundBuilder(Sound sound, float volume, float pitch){
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    /**
     * Creates an SoundBuilder from {@link PLJRSound}.
     *
     * @param sound {@link PLJRSound} that will be copied.
     */
    public SoundBuilder(PLJRSound sound){
        this.sound = sound.getType();
        this.volume = sound.getVolume();
        this.pitch = sound.getPitch();
    }

    /**
     * Changes the current sound.
     *
     * @param sound {@link Sound} that will represent the new sound.
     * @return {@link SoundBuilder} with changed sound.
     */
    public SoundBuilder withSound(Sound sound){
        this.sound = sound;
        return this;
    }

    /**
     * Changes the current volume.
     *
     * @param volume float that will represent the new volume.
     * @return {@link SoundBuilder} with changed volume.
     */
    public SoundBuilder withVolume(float volume){
        this.volume = volume;
        return this;
    }

    /**
     * Changes the current pitch.
     *
     * @param pitch float that will represent the new pitch.
     * @return {@link SoundBuilder} with changed pitch.
     */
    public SoundBuilder withPitch(float pitch){
        this.pitch = pitch;
        return this;
    }

    /**
     * Creates PLJRSound from selected values.
     *
     * @return {@link PLJRSound} consisting of all previously selected values.
     */
    public PLJRSound create(){
        return new PLJRSound(sound, volume, pitch);
    }
}
