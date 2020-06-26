package com.learn.jvm.chap10;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner6;

import java.util.EnumSet;

import static javax.lang.model.element.ElementKind.*;
import static javax.lang.model.element.Modifier.*;
import static javax.tools.Diagnostic.Kind.WARNING;

/**
 * 程序名称规范的编译器插件
 * 类或接口驼峰式命名，首字母大写
 * 方法：驼峰式，首字母小写
 * 字段：类，实例变量->驼峰式，首字母小写 ;常量->全部大写
 *
 *
 */
public class NameChecker {
    private final Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    public NameChecker(ProcessingEnvironment processingEnvironment) {
        this.messager = processingEnvironment.getMessager();
    }

    public void checkNames(Element element){
        nameCheckScanner.scan(element);
    }

    //名称检查器的实现类
    private class NameCheckScanner extends ElementScanner6<Void,Void>{

        //用于检查java类
        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            scan(e.getTypeParameters(),aVoid);
            checkCamelCase(e,true);
            super.visitType(e, aVoid);
            return null;
        }

        //用于检查变量命名是否合法
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            if(e.getKind()==ENUM_CONSTANT||e.getConstantValue()!=null||heuristically(e)){
                checkAllCaps(e);
            }
            else {
                checkCamelCase(e,false);
            }
            return null;
        }

        //检查方法命名是否合法
        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if(e.getKind() == METHOD){
                Name name = e.getSimpleName();
                if(name.contentEquals(e.getEnclosingElement().getSimpleName())){
                    messager.printMessage(WARNING,"一个普通方法"+name+"不应当与类名重复，避免与构造函数混淆");
                    checkCamelCase(e,false);
                }
            }
            super.visitExecutable(e, aVoid);
            return null;
        }

        //判断一个变量是否是常量
        private boolean heuristically(VariableElement e){
            if(e.getEnclosingElement().getKind()==INTERFACE){
                return true;
            }
            else return e.getKind() == FIELD && e.getModifiers().containsAll(EnumSet.of(PUBLIC, STATIC, FINAL));
        }

        //检查传入的element是否符合驼峰式命名法，如果不符合，则输出警告信息
        private void checkCamelCase(Element e,boolean initialCaps){
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if(Character.isUpperCase(firstCodePoint)){
                previousUpper = true;
                if(!initialCaps){
                    messager.printMessage(WARNING,"名称"+name+"应该以小写字母开头",e);
                    return;
                }
            }
            else if(Character.isLowerCase(firstCodePoint)){
                if(initialCaps){
                    messager.printMessage(WARNING,"名称"+name+"应该以大写字母开头",e);
                    return;
                }
            }
            else conventional = false;

            if(conventional){
                int cp = firstCodePoint;
                for(int i=Character.charCount(cp);i<name.length();i+=Character.charCount(cp)){
                    cp = name.codePointAt(i);
                    if(Character.isUpperCase(cp)){
                        if(previousUpper){
                            conventional=false;
                            break;
                        }
                        previousUpper=true;
                    }
                    else previousUpper=false;
                }
            }

            if(!conventional) messager.printMessage(WARNING,"名称"+name+"应该符合驼峰式命名法",e);
        }

        //大写命名检查
        private void checkAllCaps(Element e){
            String name = e.getSimpleName().toString();
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if(!Character.isUpperCase(firstCodePoint)){
                conventional = false;
            }
            else {
                boolean previousUnderscore = false;
                int cp = firstCodePoint;
                for(int i=Character.charCount(cp);i<name.length();i+=Character.charCount(cp)){
                    cp = name.codePointAt(i);
                    if(cp==(int) '_'){
                        if(previousUnderscore){
                            conventional=false;
                            break;
                        }
                        previousUnderscore=true;
                    }
                    else {
                        previousUnderscore=false;
                        if(!Character.isUpperCase(cp)&&!Character.isDigit(cp)){
                            conventional=false;
                            break;
                        }
                    }
                }
            }

            if(!conventional) messager.printMessage(WARNING,"常量"+name+"应该全部以大写字母或下划线命名，并且字母开头",e);

        }

    }


}
