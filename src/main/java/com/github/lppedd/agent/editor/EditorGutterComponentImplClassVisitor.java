package com.github.lppedd.agent.editor;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.*;

import javax.swing.*;

/**
 * @author Edoardo Luppi
 */
class EditorGutterComponentImplClassVisitor extends ClassVisitor {
  EditorGutterComponentImplClassVisitor(final @NotNull ClassVisitor cv) {
    super(Opcodes.ASM9, cv);
  }

  @Override
  public MethodVisitor visitMethod(
      final int access,
      final String name,
      final String descriptor,
      final String signature,
      final String[] exceptions
  ) {
    final var mv = super.visitMethod(access, name, descriptor, signature, exceptions);
    return "calcAnnotationExtraSize".equalsIgnoreCase(name)
        ? new CalcAnnotationExtraSizeMethodVisitor(api, mv)
        : mv;
  }

  private static class CalcAnnotationExtraSizeMethodVisitor extends MethodVisitor {
    CalcAnnotationExtraSizeMethodVisitor(final int api, final MethodVisitor mv) {
      super(api, mv);
    }

    @Override
    public void visitInvokeDynamicInsn(
        final String name,
        final String descriptor,
        final Handle bootstrapMethodHandle,
        final Object... bootstrapMethodArguments
    ) {
      if ("test".equals(name)) {
        return;
      }

      super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    @Override
    public void visitMethodInsn(
        final int opcode,
        final String owner,
        final String name,
        final String descriptor,
        final boolean isInterface
    ) {
      if ("com/intellij/ui/ComponentUtil".equals(owner) && "findParentByCondition".equals(name)) {
        super.visitMethodInsn(
            opcode,
            Type.getInternalName(SwingUtilities.class),
            "getWindowAncestor",
            "(Ljava/awt/Component;)Ljava/awt/Window;",
            false
        );
      } else {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
      }
    }
  }
}
