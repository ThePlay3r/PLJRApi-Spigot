package me.pljr.pljrapispigot.objects;

import me.pljr.pljrapispigot.utils.ActionBarUtil;
import org.bukkit.entity.Player;

public class PLJRActionBar {
    private final String message;
    private final long duration;

    public PLJRActionBar(String message, long duration){
        this.message = message;
        this.duration = duration;
    }

    public String getMessage() {
        return message;
    }
    public long getDuration() {
        return duration;
    }

    public void send(Player player){
        ActionBarUtil.send(player, this);
    }

    public void broadcast(){
        ActionBarUtil.broadcast(this);
    }
}
