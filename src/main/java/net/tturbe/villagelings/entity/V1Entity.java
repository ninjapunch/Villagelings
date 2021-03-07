
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
import net.minecraft.util.math.BlockPos;
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
import net.minecraft.block.BlockState;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@VillagelingsModElements.ModElement.Tag
public class V1Entity extends VillagelingsModElements.ModElement {
	public static EntityType entity = null;
	public V1Entity(VillagelingsModElements instance) {
		super(instance, 1);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.3f, 0.9f)).build("v_1")
						.setRegistryName("v_1");
		elements.entities.add(() -> entity);
		elements.items
				.add(() -> new SpawnEggItem(entity, -16777216, -1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("v_1_spawn_egg"));
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
			return new MobRenderer(renderManager, new Modelcustom_model(), 0.25f) {
				@Override
				public ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("villagelings:textures/texture.png");
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
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.ambient"));
		}

		@Override
		public void playStepSound(BlockPos pos, BlockState blockIn) {
			this.playSound((net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.step")), 0.15f, 1);
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
	public static class Modelcustom_model extends EntityModel<Entity> {
		private final ModelRenderer WholeBody;
		private final ModelRenderer LArm;
		private final ModelRenderer Head;
		private final ModelRenderer SpinnyThing;
		private final ModelRenderer RLeg;
		private final ModelRenderer LLeg;
		private final ModelRenderer RArm;
		public Modelcustom_model() {
			textureWidth = 32;
			textureHeight = 32;
			WholeBody = new ModelRenderer(this);
			WholeBody.setRotationPoint(3.0F, 15.0F, 1.5F);
			WholeBody.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -1.5F, 4.0F, 5.0F, 3.0F, 0.0F, false);
			LArm = new ModelRenderer(this);
			LArm.setRotationPoint(0.0F, 0.0F, 0.0F);
			WholeBody.addChild(LArm);
			LArm.setTextureOffset(8, 20).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			LArm.setTextureOffset(18, 12).addBox(-0.5F, 3.25F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
			Head = new ModelRenderer(this);
			Head.setRotationPoint(0.0F, 1.5F, 0.5F);
			LArm.addChild(Head);
			Head.setTextureOffset(11, 0).addBox(-4.5F, -10.25F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(14, 2).addBox(-2.0F, -5.5F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(18, 1).addBox(-3.5F, -4.0F, -3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(8, 17).addBox(-5.0F, -5.5F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			Head.setTextureOffset(12, 5).addBox(-4.5F, -6.5F, -2.0F, 3.0F, 4.0F, 3.0F, 0.0F, false);
			Head.setTextureOffset(0, 13).addBox(-4.5F, -7.5F, -2.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
			SpinnyThing = new ModelRenderer(this);
			SpinnyThing.setRotationPoint(-3.0F, 12.5F, -2.0F);
			Head.addChild(SpinnyThing);
			SpinnyThing.setTextureOffset(0, 8).addBox(-0.5F, -21.75F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
			SpinnyThing.setTextureOffset(0, 8).addBox(-2.0F, -19.5F, -0.5F, 4.0F, 1.0F, 4.0F, 0.0F, false);
			RLeg = new ModelRenderer(this);
			RLeg.setRotationPoint(-3.0F, 14.0F, -1.5F);
			WholeBody.addChild(RLeg);
			RLeg.setTextureOffset(0, 17).addBox(-2.0F, -10.0F, 0.5F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			LLeg = new ModelRenderer(this);
			LLeg.setRotationPoint(-3.0F, 14.0F, -1.5F);
			WholeBody.addChild(LLeg);
			LLeg.setTextureOffset(12, 13).addBox(0.0F, -10.0F, 0.5F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			RArm = new ModelRenderer(this);
			RArm.setRotationPoint(-6.0F, 0.0F, 0.0F);
			WholeBody.addChild(RArm);
			RArm.setTextureOffset(18, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			RArm.setTextureOffset(9, 13).addBox(-0.5F, 3.25F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			WholeBody.render(matrixStack, buffer, packedLight, packedOverlay);
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
			this.SpinnyThing.rotateAngleZ = f2 / 20.f;
			this.RLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.RArm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
		}
	}
}
