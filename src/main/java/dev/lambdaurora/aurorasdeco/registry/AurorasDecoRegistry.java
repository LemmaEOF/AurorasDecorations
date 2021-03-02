/*
 * Copyright (c) 2020 LambdAurora <aurora42lambda@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.lambdaurora.aurorasdeco.registry;

import dev.lambdaurora.aurorasdeco.AurorasDeco;
import dev.lambdaurora.aurorasdeco.accessor.ItemExtensions;
import dev.lambdaurora.aurorasdeco.block.*;
import dev.lambdaurora.aurorasdeco.block.big_flower_pot.*;
import dev.lambdaurora.aurorasdeco.block.entity.*;
import dev.lambdaurora.aurorasdeco.entity.FakeLeashKnotEntity;
import dev.lambdaurora.aurorasdeco.item.BlackboardItem;
import dev.lambdaurora.aurorasdeco.recipe.BlackboardCloneRecipe;
import dev.lambdaurora.aurorasdeco.screen.ShelfScreenHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static dev.lambdaurora.aurorasdeco.AurorasDeco.id;

/**
 * Represents the Aurora's Decorations registry.
 *
 * @author LambdAurora
 * @version 1.0.0
 * @since 1.0.0
 */
public final class AurorasDecoRegistry {
    public static final BigFlowerPotBlock BIG_FLOWER_POT_BLOCK = registerWithItem(
            "big_flower_pot",
            PottedPlantType.register("none", Blocks.AIR, Items.AIR),
            new Item.Settings().group(ItemGroup.DECORATIONS)
    );
    public static final BigFlowerPotBlock.PlantAir PLANT_AIR_BLOCK = register(
            "plant_air",
            new BigFlowerPotBlock.PlantAir(
                    FabricBlockSettings.of(Material.AIR)
                            .nonOpaque()
                            .strength(-1.f, 3600000.f)
                            .dropsNothing()
                            .allowsSpawning((state, world, pos, type) -> false)
            )
    );
    public static final BigPottedCactusBlock BIG_POTTED_CACTUS_BLOCK = registerBigPotted("cactus",
            Blocks.CACTUS, Items.CACTUS,
            type -> new BigPottedCactusBlock(type, BigPottedCactusBlock.CACTUS_SHAPE));
    public static final BigStaticFlowerPot BIG_POTTED_BAMBOO_BLOCK = registerBigPotted("bamboo",
            Blocks.AIR, Items.BAMBOO,
            type -> new BigStaticFlowerPot(type, Block.createCuboidShape(
                    7.f, 14.f, 7.f,
                    9.f, 29.f, 9.f
            )));
    public static final BigPottedAzaleaBlock BIG_POTTED_AZALEA_BLOCK = registerBigPotted("azalea",
            Blocks.AZALEA, Items.AZALEA,
            BigPottedAzaleaBlock::new);
    public static final BigPottedAzaleaBlock BIG_POTTED_FLOWERING_AZALEA_BLOCK = registerBigPotted("flowering_azalea",
            Blocks.FLOWERING_AZALEA, Items.FLOWERING_AZALEA,
            BigPottedAzaleaBlock::new);
    public static final BigPottedSweetBerryBushBlock BIG_POTTED_SWEET_BERRY_BUSH_BLOCK =
            registerBigPotted("sweet_berry_bush",
                    Blocks.SWEET_BERRY_BUSH, Items.SWEET_BERRIES,
                    BigPottedSweetBerryBushBlock::new);
    public static final BigStaticFlowerPot BIG_POTTED_TATER_BLOCK = registerBigPotted("tater",
            Blocks.AIR, Items.POTATO,
            type -> new BigStaticFlowerPot(type, Block.createCuboidShape(
                    4.f, 14.f, 4.f,
                    12.f, 21.f, 12.f
            )));

    public static final BlackboardBlock BLACKBOARD_BLOCK = registerWithItem("blackboard",
            new BlackboardBlock(FabricBlockSettings.of(Material.DECORATION).strength(.2f)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.WOOD),
                    false),
            new FabricItemSettings().group(ItemGroup.DECORATIONS).equipmentSlot(stack -> EquipmentSlot.HEAD),
            BlackboardItem::new);

    public static final BookPileBlock BOOK_PILE_BLOCK = register("book_pile",
            new BookPileBlock(FabricBlockSettings.of(Material.DECORATION).strength(.2f)
                    .nonOpaque()));

    public static final BurntVineBlock BURNT_VINE_BLOCK = register("burnt_vine", new BurntVineBlock());

    public static final BlackboardBlock CHALKBOARD_BLOCK = registerWithItem("chalkboard",
            new BlackboardBlock(FabricBlockSettings.copyOf(BLACKBOARD_BLOCK), false),
            new FabricItemSettings().group(ItemGroup.DECORATIONS).equipmentSlot(stack -> EquipmentSlot.HEAD),
            BlackboardItem::new);

    public static final FenceGateBlock NETHER_BRICK_FENCE_GATE = registerWithItem("nether_brick_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICK_FENCE)),
            new FabricItemSettings().group(ItemGroup.REDSTONE));

    public static final PieBlock PUMPKIN_PIE_BLOCK = register("pumpkin_pie", PieBlock.fromPieItem(Items.PUMPKIN_PIE));

    public static final SturdyStoneBlock STURDY_STONE_BLOCK = registerWithItem("sturdy_stone",
            new SturdyStoneBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.5f)),
            new FabricItemSettings().group(ItemGroup.REDSTONE));

    public static final WallLanternBlock WALL_LANTERN_BLOCK = register("wall_lantern", new WallLanternBlock());

    public static final BlackboardBlock WAXED_BLACKBOARD_BLOCK = registerWithItem("waxed_blackboard",
            new BlackboardBlock(FabricBlockSettings.copyOf(BLACKBOARD_BLOCK), true),
            new FabricItemSettings().group(ItemGroup.DECORATIONS).equipmentSlot(stack -> EquipmentSlot.HEAD),
            BlackboardItem::new);
    public static final BlackboardBlock WAXED_CHALKBOARD_BLOCK = registerWithItem("waxed_chalkboard",
            new BlackboardBlock(FabricBlockSettings.copyOf(BLACKBOARD_BLOCK), true),
            new FabricItemSettings().group(ItemGroup.DECORATIONS).equipmentSlot(stack -> EquipmentSlot.HEAD),
            BlackboardItem::new);

    public static final WindChimeBlock WIND_CHIME_BLOCK = registerWithItem("wind_chime",
            new WindChimeBlock(FabricBlockSettings.of(Material.DECORATION).nonOpaque()
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)),
            new Item.Settings().group(ItemGroup.DECORATIONS));

    public static final Block[] SHELF_BLOCKS = WoodType.stream().map(woodType ->
            registerWithItem("shelf/" + woodType.getPathName(),
                    new ShelfBlock(FabricBlockSettings.of(Material.WOOD, woodType.getMapColor())
                            .nonOpaque()
                            .strength(2.f, 3.f)
                            .breakByTool(FabricToolTags.AXES)
                            .sounds(BlockSoundGroup.WOOD)),
                    new FabricItemSettings().group(ItemGroup.DECORATIONS))
    ).toArray(ShelfBlock[]::new);

    /* Block Entities */

    public static final BlockEntityType<BlackboardBlockEntity> BLACKBOARD_BLOCK_ENTITY_TYPE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            id("blackboard"),
            FabricBlockEntityTypeBuilder.create(BlackboardBlockEntity::new,
                    BLACKBOARD_BLOCK, CHALKBOARD_BLOCK, WAXED_BLACKBOARD_BLOCK, WAXED_CHALKBOARD_BLOCK)
                    .build()
    );
    public static final BlockEntityType<BookPileBlockEntity> BOOK_PILE_BLOCK_ENTITY_TYPE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            id("book_pile"),
            FabricBlockEntityTypeBuilder.create(BookPileBlockEntity::new, BOOK_PILE_BLOCK).build()
    );
    public static final BlockEntityType<LanternBlockEntity> LANTERN_BLOCK_ENTITY_TYPE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            id("lantern"),
            FabricBlockEntityTypeBuilder.create(LanternBlockEntity::new, WALL_LANTERN_BLOCK).build()
    );
    public static final BlockEntityType<ShelfBlockEntity> SHELF_BLOCK_ENTITY_TYPE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            id("shelf"),
            FabricBlockEntityTypeBuilder.create(ShelfBlockEntity::new, SHELF_BLOCKS).build()
    );
    public static final BlockEntityType<WindChimeBlockEntity> WIND_CHIME_BLOCK_ENTITY_TYPE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            id("wind_chime"),
            FabricBlockEntityTypeBuilder.create(WindChimeBlockEntity::new, WIND_CHIME_BLOCK).build()
    );

    public static final ScreenHandlerType<ShelfScreenHandler> SHELF_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerExtended(
            AurorasDeco.id("shelf"),
            ShelfScreenHandler::new
    );

    /* Entities */

    public static final EntityType<FakeLeashKnotEntity> FAKE_LEASH_KNOT_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            id("fake_leash_knot"),
            FabricEntityTypeBuilder.<FakeLeashKnotEntity>createMob()
                    .entityFactory(FakeLeashKnotEntity::new)
                    .dimensions(EntityDimensions.fixed(.375f, .5f))
                    .defaultAttributes(MobEntity::createMobAttributes)
                    .forceTrackedVelocityUpdates(false)
                    .trackRangeChunks(10)
                    .trackedUpdateRate(Integer.MAX_VALUE)
                    .build()
    );

    /* Sounds */

    public static final Identifier LANTERN_SWING_SOUND_ID = id("block.lantern.swing");
    public static final SoundEvent LANTERN_SWING_SOUND_EVENT = Registry.register(Registry.SOUND_EVENT,
            LANTERN_SWING_SOUND_ID,
            new SoundEvent(LANTERN_SWING_SOUND_ID)
    );
    public static final Identifier ARMOR_STAND_HIDE_BASE_PLATE_SOUND_ID = id("entity.armor_stand.hide_base_plate");
    public static final SoundEvent ARMOR_STAND_HIDE_BASE_PLATE_SOUND_EVENT = Registry.register(Registry.SOUND_EVENT,
            ARMOR_STAND_HIDE_BASE_PLATE_SOUND_ID,
            new SoundEvent(ARMOR_STAND_HIDE_BASE_PLATE_SOUND_ID)
    );
    public static final Identifier ITEM_FRAME_HIDE_BACKGROUND_ID = id("entity.item_frame.hide_background");
    public static final SoundEvent ITEM_FRAME_HIDE_BACKGROUND_EVENT = Registry.register(Registry.SOUND_EVENT,
            ITEM_FRAME_HIDE_BACKGROUND_ID,
            new SoundEvent(ITEM_FRAME_HIDE_BACKGROUND_ID)
    );

    /* Recipes */

    public static final SpecialRecipeSerializer<BlackboardCloneRecipe> BLACKBOARD_CLONE_RECIPE_SERIALIZER
            = register("crafting_special_blackboard_clone",
            new SpecialRecipeSerializer<>(BlackboardCloneRecipe::new));

    /* Tags */

    public static final Tag<Block> PET_BEDS = TagRegistry.block(AurorasDeco.id("pet_beds"));
    public static final Tag<Block> SHELVES = TagRegistry.block(AurorasDeco.id("shelves"));

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registry.BLOCK, id(name), block);
    }

    private static <T extends Block> T registerWithItem(String name, T block, Item.Settings settings) {
        return registerWithItem(name, block, settings, BlockItem::new);
    }

    private static <T extends Block> T registerWithItem(String name, T block, Item.Settings settings,
                                                        BiFunction<T, Item.Settings, BlockItem> factory) {
        register(name, factory.apply(register(name, block), settings));
        return block;
    }

    private static PetBedBlock registerPetBed(DyeColor color) {
        return registerWithItem("pet_bed/" + color.getName(),
                new PetBedBlock(FabricBlockSettings.of(Material.WOOL)
                        .mapColor(color).breakByTool(FabricToolTags.AXES)
                        .sounds(BlockSoundGroup.WOOD).strength(.2f)),
                new FabricItemSettings().group(ItemGroup.DECORATIONS));
    }

    private static <T extends BigFlowerPotBlock> T registerBigPotted(String name, Block plant, Item item,
                                                                     Function<PottedPlantType, T> block) {
        return register("big_flower_pot/" + name, PottedPlantType.register(name, plant, item, block));
    }

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, id(name), item);
    }

    private static <R extends Recipe<?>, T extends RecipeSerializer<R>> T register(String name, T recipe) {
        return Registry.register(Registry.RECIPE_SERIALIZER, id(name), recipe);
    }

    public static void init(Map<Identifier, Block> delayed) {
        List<BigFlowerPotBlock> plants = new ArrayList<>();
        Registry.BLOCK.forEach(block -> {
            if (PottedPlantType.isValidPlant(block)) {
                BigFlowerPotBlock potBlock = PottedPlantType.registerFromBlock(block);
                if (potBlock != null)
                    plants.add(potBlock);
            }
        });

        plants.forEach(pot -> register("big_flower_pot/" + pot.getPlantType().getId(), pot));

        Registry.ITEM.getOrEmpty(new Identifier("pockettools", "pocket_cactus"))
                .ifPresent(pocketCactus -> registerBigPotted("pocket_cactus", Blocks.POTTED_CACTUS, pocketCactus,
                        type -> new BigPottedCactusBlock(type, BigPottedCactusBlock.POCKET_CACTUS_SHAPE)));

        delayed.forEach((id, block) -> Registry.register(Registry.BLOCK, id, block));

        for (DyeColor color : DyeColor.values()) {
            registerPetBed(color);
        }

        FlammableBlockRegistry.getDefaultInstance().add(PET_BEDS, 10, 30);
        FlammableBlockRegistry.getDefaultInstance().add(SHELVES, 5, 20);

        ((ItemExtensions) Items.BOOK).makePlaceable(BOOK_PILE_BLOCK);
        ((ItemExtensions) Items.ENCHANTED_BOOK).makePlaceable(BOOK_PILE_BLOCK);
    }
}
