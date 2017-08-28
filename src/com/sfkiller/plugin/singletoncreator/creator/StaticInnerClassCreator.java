package com.sfkiller.plugin.singletoncreator.creator;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;

public class StaticInnerClassCreator extends BaseSingletonCreator{

    @Override
    public void create(Editor editor, PsiElementFactory psiElementFactory, Project project, PsiClass psiClass, String className) {
        PsiClass innerClass = createStaticInnerClass(className, project, psiElementFactory);
        if (!containInnerClass(psiClass, innerClass)) {
            psiClass.add(innerClass);
        }

        if (0 == psiClass.getConstructors().length) {
            PsiMethod privateConstructor = psiElementFactory.createConstructor();
            privateConstructor.getModifierList().setModifierProperty(PsiModifier.PRIVATE, true);
            psiClass.add(privateConstructor);
        }

        String getInstanceMethodContent = generateMethodContent(className);
        PsiMethod getInstanceMethod = psiElementFactory.createMethodFromText(getInstanceMethodContent, psiClass);
        if (!containMethod(psiClass, getInstanceMethod)) {
            psiClass.add(getInstanceMethod);
        }
    }

    private PsiClass createStaticInnerClass(String className, Project project, PsiElementFactory psiElementFactory) {
        PsiClass staticInnerClass = psiElementFactory.createClass(generateInnerClassName());
        PsiModifierList classModifyList = staticInnerClass.getModifierList();
        if (null != classModifyList) {
            classModifyList.setModifierProperty(PsiModifier.PRIVATE, true);
            classModifyList.setModifierProperty(PsiModifier.STATIC, true);
        }

        PsiType psiType = PsiType.getTypeByName(className, project, GlobalSearchScope.EMPTY_SCOPE);
        PsiField psiField = psiElementFactory.createField(generateFieldContent(), psiType);

        PsiModifierList fieldModifierList = psiField.getModifierList();
        if (null != fieldModifierList) {
            fieldModifierList.setModifierProperty(PsiModifier.STATIC, true);
            fieldModifierList.setModifierProperty(PsiModifier.FINAL, true);
        }

        PsiExpression initializer = psiElementFactory.createExpressionFromText(generateInitializerContent(className), psiField);
        psiField.setInitializer(initializer);
        staticInnerClass.add(psiField);

        return staticInnerClass;

    }

    private String generateInitializerContent(String className) {
        return "new " + className + "()";
    }

    private String generateFieldContent() {
        return "sInstance";
    }

    private String generateInnerClassName() {
        return "SingletonHolder";
    }

    private String generateMethodContent(String className) {
        return "public static " + className + " getInstance() {\n" +
                "        return SingletonHolder.sInstance;\n" +
                "    }";
    }

}
