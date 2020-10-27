package com.github.lppedd.agent;

import com.github.lppedd.agent.editor.EditorGutterComponentImplTransformer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.instrument.Instrumentation;

/**
 * @author Edoardo Luppi
 */
public class IdeaAgent {
  public static void premain(
      final @Nullable String args,
      final @NotNull Instrumentation instrumentation
  ) {
    instrumentation.addTransformer(new EditorGutterComponentImplTransformer());
  }
}
