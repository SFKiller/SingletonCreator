package com.sfkiller.plugin.singletoncreator;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * Created by qipu on 2017/8/28.
 */
public class SingletonCreatorAction extends BaseGenerateAction implements OnTypeSelectedListener {

    private AnActionEvent actionEvent;

    public SingletonCreatorAction() {
        super(null);
    }

    public SingletonCreatorAction(CodeInsightActionHandler handler) {
        super(handler);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Messages.showMessageDialog("Hello", "Information", Messages.getInformationIcon());
        this.actionEvent = e;
        PatternPickerDialog pickerDialog = new PatternPickerDialog();
    }

    @Override
    public void onTypeSelected(int type) {

    }
}
