package com.sfkiller.plugin.singletoncreator.creator;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

public abstract class BaseSingletonCreator {

    public void create(AnActionEvent event) {
        PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
        if (null == psiFile) {
            return;
        }

        WriteCommandAction.runWriteCommandAction(event.getProject(), () -> {
            Editor editor = event.getData(PlatformDataKeys.EDITOR);
            if (null == editor) {
                return;
            }
            Project project = editor.getProject();
            if (null == project) {
                return;
            }

            PsiElement element = psiFile.findElementAt(editor.getCaretModel().getOffset());
            PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
            if (null == psiClass) {
                return;
            }
            if (null == psiClass.getNameIdentifier()) {
                return;
            }
            String className = psiClass.getNameIdentifier().getText();
            PsiElementFactory psiElementFactory = JavaPsiFacade.getElementFactory(project);
            create(editor, psiElementFactory, project, psiClass, className);
        });
    }

    public abstract void create(Editor editor,
                                PsiElementFactory psiElementFactory,
                                Project project,
                                PsiClass psiClass,
                                String className);

    protected boolean containField(PsiClass psiClass, PsiField psiField) {
        return (null != psiClass && null != psiField) && null != psiClass.findFieldByName(psiField.getName(), true);
    }

    protected boolean containMethod(PsiClass psiClass, PsiMethod psiMethod) {
        return (null != psiClass && null != psiMethod) && psiClass.findMethodsByName(psiMethod.getName(), true).length > 0;
    }

    protected boolean containInnerClass(PsiClass psiClass, PsiClass innerClass) {
        return (null != psiClass && null != innerClass) && null != psiClass.findInnerClassByName(innerClass.getName(), true);
    }

}
