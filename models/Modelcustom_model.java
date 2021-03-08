// Made with Blockbench 3.8.0
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

public static class Modelcustom_model extends EntityModel<Entity> {
	private final ModelRenderer WholeBody;
	private final ModelRenderer LArm;
	private final ModelRenderer RLeg;
	private final ModelRenderer LLeg;
	private final ModelRenderer RArm;
	private final ModelRenderer Head;
	private final ModelRenderer SpinnyThing;

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

		RLeg = new ModelRenderer(this);
		RLeg.setRotationPoint(-4.0F, 4.5F, 0.0F);
		WholeBody.addChild(RLeg);
		RLeg.setTextureOffset(0, 17).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

		LLeg = new ModelRenderer(this);
		LLeg.setRotationPoint(-2.0F, 4.5F, 0.0F);
		WholeBody.addChild(LLeg);
		LLeg.setTextureOffset(12, 13).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

		RArm = new ModelRenderer(this);
		RArm.setRotationPoint(-6.0F, 0.0F, 0.0F);
		WholeBody.addChild(RArm);
		RArm.setTextureOffset(18, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
		RArm.setTextureOffset(9, 13).addBox(-0.5F, 3.25F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(-3.0F, -1.0F, 0.0F);
		WholeBody.addChild(Head);
		Head.setTextureOffset(14, 2).addBox(1.0F, -3.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		Head.setTextureOffset(18, 1).addBox(-0.5F, -1.5F, -2.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		Head.setTextureOffset(8, 17).addBox(-2.0F, -3.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		Head.setTextureOffset(12, 5).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, false);
		Head.setTextureOffset(0, 8).addBox(-2.0F, -4.5F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		Head.setTextureOffset(0, 13).addBox(-1.5F, -5.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);

		SpinnyThing = new ModelRenderer(this);
		SpinnyThing.setRotationPoint(0.0F, -5.5F, 0.0F);
		Head.addChild(SpinnyThing);
		SpinnyThing.setTextureOffset(0, 8).addBox(-0.5F, -1.25F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		SpinnyThing.setTextureOffset(11, 0).addBox(-1.5F, -2.25F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		WholeBody.render(matrixStack, buffer, packedLight, packedOverlay);
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
		this.SpinnyThing.rotateAngleY = f2;
		this.RLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		this.RArm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1;
	}
}