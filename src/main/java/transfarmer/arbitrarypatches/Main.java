package transfarmer.arbitrarypatches;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import transfarmer.arbitrarypatches.block.entity.SoulSandBlockEntity;

public class Main implements ModInitializer {
    public static final String MOD_ID = "arbitrarypatches";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static BlockEntityType<SoulSandBlockEntity> SOUL_SAND;

    @Override
    public void onInitialize() {
        SOUL_SAND = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier(Main.MOD_ID, "soul_sand"),
                BlockEntityType.Builder.create(SoulSandBlockEntity::new, Blocks.SOUL_SAND).build(null)
        );
    }
}
