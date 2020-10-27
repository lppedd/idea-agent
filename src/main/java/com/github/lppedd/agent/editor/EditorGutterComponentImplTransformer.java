package com.github.lppedd.agent.editor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @author Edoardo Luppi
 */
public class EditorGutterComponentImplTransformer implements ClassFileTransformer {
  private static final String CLASS_NAME = "com/intellij/openapi/editor/impl/EditorGutterComponentImpl";

  @Override
  public byte[] transform(
      final ClassLoader loader,
      final String className,
      final Class<?> classBeingRedefined,
      final ProtectionDomain protectionDomain,
      final byte[] classfileBuffer
  ) {
    if (!className.equals(CLASS_NAME)) {
      return classfileBuffer;
    }

    final var cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    final var cv = new EditorGutterComponentImplClassVisitor(cw);
    final var cr = new ClassReader(classfileBuffer);
    cr.accept(cv, 0);
    return cw.toByteArray();
  }
}
