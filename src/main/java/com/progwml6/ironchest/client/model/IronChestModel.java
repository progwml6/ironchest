package com.progwml6.ironchest.client.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class IronChestModel extends Model<Float>  {
  private static final String BOTTOM = "iron_bottom";
  private static final String LID = "iron_lid";
  private static final String LOCK = "iron_lock";
  private final ModelPart lid;
  private final ModelPart lock;

  public IronChestModel(ModelPart root) {
    super(root, RenderType::entitySolid);
    this.lid = root.getChild(LID);
    this.lock = root.getChild(LOCK);
  }

  public static LayerDefinition createLayerDefinition() {
    MeshDefinition meshDefinition = new MeshDefinition();
    PartDefinition partDefinition = meshDefinition.getRoot();

    partDefinition.addOrReplaceChild(BOTTOM, CubeListBuilder.create().texOffs(0, 19).addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F), PartPose.ZERO);
    partDefinition.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), PartPose.offset(0.0F, 9.0F, 1.0F));
    partDefinition.addOrReplaceChild(LOCK, CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -2.0F, 14.0F, 2.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 9.0F, 1.0F));

    return LayerDefinition.create(meshDefinition, 64, 64);
  }

  @Override
  public void setupAnim(Float renderState) {
    super.setupAnim(renderState);
    this.lid.xRot = -(renderState * (float) (Math.PI / 2));
    this.lock.xRot = this.lid.xRot;
  }
}
