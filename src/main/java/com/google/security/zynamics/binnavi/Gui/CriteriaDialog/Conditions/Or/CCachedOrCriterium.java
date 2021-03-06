/*
Copyright 2015 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.google.security.zynamics.binnavi.Gui.CriteriaDialog.Conditions.Or;

import com.google.security.zynamics.binnavi.Gui.CriteriaDialog.Conditions.ICachedCriterium;
import com.google.security.zynamics.binnavi.Gui.CriteriaDialog.ExpressionModel.CCachedExpressionTreeNode;
import com.google.security.zynamics.binnavi.yfileswrap.zygraph.NaviNode;
import com.google.security.zynamics.zylib.strings.Commafier;
import com.google.security.zynamics.zylib.types.common.CollectionHelpers;
import com.google.security.zynamics.zylib.types.common.ICollectionMapper;

import java.util.List;



/**
 * Cached OR criterium.
 */
public final class CCachedOrCriterium implements ICachedCriterium, IAbstractOrCriterium {
  @Override
  public String getFormulaString(final List<CCachedExpressionTreeNode> children) {
    if (children.size() < 2) {
      throw new IllegalStateException("IE01113: AND operator has less than two child criteria.");
    }

    final List<String> childStrings =
        CollectionHelpers.map(children, new ICollectionMapper<CCachedExpressionTreeNode, String>() {
          @Override
          public String map(final CCachedExpressionTreeNode item) {
            return item.getCriterium().getFormulaString(item.getChildren());
          }
        });

    return "(" + Commafier.commafy(childStrings, " || ") + ")";
  }

  @Override
  public boolean matches(final NaviNode node) {
    return true;
  }
}
