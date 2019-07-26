package com.grinderwolf.smw.nms;

import com.grinderwolf.smw.api.world.SlimeChunk;
import com.grinderwolf.smw.api.loaders.SlimeLoader;
import com.grinderwolf.smw.api.world.SlimeWorld;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CraftSlimeWorld implements SlimeWorld {

    private final SlimeLoader loader;
    private final String name;
    private final Map<Long, SlimeChunk> chunks;
    private final SlimeProperties properties;

    @Setter
    private boolean saving;

    @Override
    public SlimeChunk getChunk(int x, int z) {
        Long index = (((long) z) * Integer.MAX_VALUE + ((long) x));

        return chunks.get(index);
    }

    public void updateChunk(SlimeChunk chunk) {
        CraftSlimeChunk craftChunk = (CraftSlimeChunk) chunk;

        if (!craftChunk.getWorldName().equals(getName())) {
            throw new IllegalArgumentException("Chunk (" + chunk.getX() + ", " + chunk.getZ() + ") belongs to world '" + ((CraftSlimeChunk) chunk).getWorldName() + "', not to '" + getName() + "'!");
        }

        while (saving) { }
        chunks.put(((long) chunk.getZ()) * Integer.MAX_VALUE + ((long) chunk.getX()), chunk);
    }
}
