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
package com.google.security.zynamics.binnavi.Gui.MainWindow.ProjectTree.DragAndDrop;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;


import com.google.common.base.Preconditions;
import com.google.security.zynamics.binnavi.Gui.MainWindow.Implementations.CViewFunctions;
import com.google.security.zynamics.binnavi.Gui.MainWindow.ProjectTree.Nodes.Tag.CTagNode;
import com.google.security.zynamics.binnavi.Tagging.CTag;
import com.google.security.zynamics.binnavi.disassembly.views.INaviView;
import com.google.security.zynamics.zylib.gui.dndtree.DNDTree;

/**
 * Drag & Drop handler for dragging views into tag nodes.
 */
public final class CViewsToTagHandler extends CAbstractDropHandler {
  /**
   * Parent window used for dialogs.
   */
  private final JFrame m_parent;

  /**
   * Creates a new handler object.
   * 
   * @param parent Parent window used for dialogs.
   */
  public CViewsToTagHandler(final JFrame parent) {
    super(CViewTransferable.VIEW_FLAVOR);

    Preconditions.checkNotNull(parent, "IE01936: Parent argument can not be null");

    m_parent = parent;
  }

  @Override
  public boolean canHandle(final DefaultMutableTreeNode parentNode,
      final DefaultMutableTreeNode draggedNode) {
    return false;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean canHandle(final DefaultMutableTreeNode parentNode, final Object data) {
    if (parentNode instanceof CTagNode) {
      final List<INaviView> views = (List<INaviView>) data;

      final CTag tag = ((CTagNode) parentNode).getObject().getObject();

      for (final INaviView view : views) {
        if (!view.getConfiguration().isTagged(tag) && view.inSameDatabase(tag)) {
          return true;
        }

      }

      return false;
    }

    return false;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void drop(final DefaultMutableTreeNode parentNode, final Object data) {
    final List<INaviView> views = (List<INaviView>) data;

    final CTag tag = ((CTagNode) parentNode).getObject().getObject();

    for (final INaviView view : views) {
      CViewFunctions.tagView(m_parent, view, tag);
    }
  }

  @Override
  public void drop(final DNDTree target, final DefaultMutableTreeNode parentNode,
      final DefaultMutableTreeNode draggedNode) {
    // Views can not be dragged from nodes
  }
}
