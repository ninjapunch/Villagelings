
package net.tturbe.villagelings.entity;

import net.tturbe.villagelings.VillagelingsModElements;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@VillagelingsModElements.ModElement.Tag
public class ReaperEntity extends VillagelingsModElements.ModElement {
	public static EntityType entity = null;
	public ReaperEntity(VillagelingsModElements instance) {
		super(instance, 2);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.6f, 1.8f)).build("reaper")
						.setRegistryName("reaper");
		elements.entities.add(() -> entity);
		elements.items.add(() -> new SpawnEggItem(entity, -1, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("reaper_spawn_egg"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(entity, 20, 4, 4));
		}
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				MonsterEntity::canMonsterSpawn);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(entity, renderManager -> {
			return new MobRenderer(renderManager, new Modelreaper_model(), 0.5f) {
				@Override
				public ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("villagelings:textures/reaper.png");
				}
			};
		});
	}
	public static class CustomEntity extends MonsterEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 0;
			setNoAI(false);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2, false));
			this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1));
			this.targetSelector.addGoal(6, new HurtByTargetGoal(this));
			this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
			this.goalSelector.addGoal(8, new OpenDoorGoal(this, true));
			this.goalSelector.addGoal(9, new OpenDoorGoal(this, false));
			this.goalSelector.addGoal(10, new SwimGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
		}

		@Override
		protected void registerAttributes() {
			super.registerAttributes();
			if (this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
			if (this.getAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
			if (this.getAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0);
			if (this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
			this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3);
		}
	}

	// Made with Blockbench 3.8.0
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class Modelreaper_model extends EntityModel<Entity> {
		private final ModelRenderer all;
		private final ModelRenderer LArm;
		private final ModelRenderer Spinnything2;
		private final ModelRenderer spin2;
		private final ModelRenderer Head;
		private final ModelRenderer RArm;
		private final ModelRenderer Spinnything;
		private final ModelRenderer spin;
		private final ModelRenderer LLeg;
		private final ModelRenderer Torso;
		private final ModelRenderer RLeg;
		public Modelreaper_model() {
			textureWidth = 32;
			textureHeight = 32;
			all = new ModelRenderer(this);
			all.setRotationPoint(0.0F, 29.0F, 0.0F);
			LArm = new ModelRenderer(this);
			LArm.setRotationPoint(3.0F, -14.0F, 1.5F);
			all.addChild(LArm);
			LArm.setTextureOffset(18, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			LArm.setTextureOffset(20, 0).addBox(-0.5F, 3.25F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Spinnything2 = new ModelRenderer(this);
			Spinnything2.setRotationPoint(-8.0F, 2.25F, 0.0F);
			LArm.addChild(Spinnything2);
			Spinnything2.setTextureOffset(8, 15).addBox(9.0F, 0.5F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			spin2 = new ModelRenderer(this);
			spin2.setRotationPoint(9.0F, 4.0F, 0.0F);
			Spinnything2.addChild(spin2);
			spin2.setTextureOffset(11, 19).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
			Head = new ModelRenderer(this);
			Head.setRotationPoint(0.0F, -15.125F, 1.5F);
			all.addChild(Head);
			Head.setTextureOffset(20, 14).addBox(1.0F, -2.875F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(18, 11).addBox(-2.0F, -2.875F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(9, 8).addBox(-0.5F, -1.375F, -2.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(0, 8).addBox(-1.5F, -3.875F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, false);
			RArm = new ModelRenderer(this);
			RArm.setRotationPoint(-3.0F, -14.0F, 1.5F);
			all.addChild(RArm);
			RArm.setTextureOffset(0, 15).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			RArm.setTextureOffset(11, 0).addBox(-0.5F, 3.25F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Spinnything = new ModelRenderer(this);
			Spinnything.setRotationPoint(-2.0F, 2.25F, 0.0F);
			RArm.addChild(Spinnything);
			Spinnything.setTextureOffset(13, 7).addBox(0.0F, 0.5F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
			spin = new ModelRenderer(this);
			spin.setRotationPoint(1.0F, 4.0F, 0.0F);
			Spinnything.addChild(spin);
			spin.setTextureOffset(17, 7).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
			LLeg = new ModelRenderer(this);
			LLeg.setRotationPoint(1.0F, -10.0F, 1.5F);
			all.addChild(LLeg);
			LLeg.setTextureOffset(14, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			Torso = new ModelRenderer(this);
			Torso.setRotationPoint(0.0F, 0.0F, 0.0F);
			all.addChild(Torso);
			Torso.setTextureOffset(0, 0).addBox(-2.0F, -15.0F, 0.0F, 4.0F, 5.0F, 3.0F, 0.0F, false);
			RLeg = new ModelRenderer(this);
			RLeg.setRotationPoint(-1.0F, -10.0F, 1.5F);
			all.addChild(RLeg);
			RLeg.setTextureOffset(12, 12).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			all.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.LLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.Head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.LArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
			this.spin.rotateAngleX = f2;
			this.spin2.rotateAngleX = f2;
			this.RLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.RArm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		}
	}
}
