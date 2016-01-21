/*
 * Copyright (c)  RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package org.nest.integration;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.nest.base.GenerationTestBase;
import org.nest.nestml._ast.ASTNESTMLCompilationUnit;
import org.nest.spl._ast.ASTOdeDeclaration;
import org.nest.utils.ASTNodes;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * Test the entire generation pipeline without mocks
 *
 * @author plotnikov
 */
@Ignore
public class GeneratorIntegrationTest extends GenerationTestBase {
  private final List<String> pscModelsWithOde = Lists.newArrayList(
      "src/test/resources/codegeneration/iaf_neuron_ode_module.nestml"
  );

  private final List<String> nestmlPSCModels = Lists.newArrayList(
      "src/test/resources/codegeneration/iaf_neuron_module.nestml",
      "src/test/resources/codegeneration/iaf_tum_2000_module.nestml",
      "src/test/resources/codegeneration/iaf_psc_alpha_module.nestml",
      "src/test/resources/codegeneration/iaf_psc_exp_module.nestml",
      "src/test/resources/codegeneration/iaf_psc_delta_module.nestml",
      "src/test/resources/codegeneration/iaf_psc_exp_multisynapse_module.nestml",
      "src/test/resources/codegeneration/mat2_psc_exp_module.nestml",
      "src/test/resources/codegeneration/izhikevich_module.nestml",
      "src/test/resources/codegeneration/iaf_psc_alpha_multisynapse_module.nestml"
  );

  private final List<String> nestmlCondModels = Lists.newArrayList(
      "src/test/resources/codegeneration/iaf_cond_alpha_module.nestml"
  );

  private final List<String> nestmlCondModelExplicit = Lists.newArrayList(
      "src/test/resources/codegeneration/iaf_cond_alpha_implicit_module.nestml"
  );


  private final List<String> feedbackModels = Lists.newArrayList(
      "src/test/resources/codegeneration/neuron_level_1.nestml",
      "src/test/resources/codegeneration/neuron_level_2.nestml",
      "src/test/resources/codegeneration/neuron_level_3.nestml"
  );

  private final List<String> workshopModels = Lists.newArrayList(
      "src/test/resources/codegeneration/neuron_level_1.nestml",
      "src/test/resources/codegeneration/neuron_level_2.nestml",
      "src/test/resources/codegeneration/neuron_level_3.nestml"
  );

  @Ignore
  @Test
  public void testFeedbackModels() {
    workshopModels.forEach(this::invokeCodeGenerator);

  }

  @Ignore
  @Test
  public void testWorkshopCode() {
    workshopModels.forEach(this::checkCocos);
    workshopModels.forEach(this::invokeCodeGenerator);
  }

  @Test
  public void checkCocosOnModels() throws IOException {
    nestmlPSCModels.forEach(this::checkCocos);
    pscModelsWithOde.forEach(this::checkCocos);
    nestmlCondModels.forEach(this::checkCocos);
  }

  @Test
  public void testModelsWithoutOde() throws IOException {
    nestmlPSCModels.forEach(this::invokeCodeGenerator);
  }

  @Test
  public void testPscModelWithOde() {
    pscModelsWithOde.forEach(this::invokeCodeGenerator);
  }

  @Ignore
  @Test
  public void testCondModel() {
    nestmlCondModels.forEach(this::generateNESTMLImplementation);
  }

  @Test
  public void testImplicitForm() {

    nestmlCondModelExplicit.forEach(model -> {
      final ASTNESTMLCompilationUnit root = parseNESTMLModel(model);
      scopeCreator.runSymbolTableCreator(root);
      Optional<ASTOdeDeclaration> odeDeclaration = ASTNodes.getAny(root, ASTOdeDeclaration.class);
      Assert.assertTrue(odeDeclaration.isPresent());

      generator.generateNESTCode(root, Paths.get("target"));
    });

  }
}