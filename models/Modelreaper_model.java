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
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		all.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
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