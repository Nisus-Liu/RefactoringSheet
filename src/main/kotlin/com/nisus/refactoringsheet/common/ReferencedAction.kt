package com.nisus.refactoringsheet.common

import com.nisus.refactoringsheet.action.sheet.*
import com.nisus.refactoringsheet.util.CommandUtil
import java.net.URI
import kotlin.reflect.KClass

interface ReferencedAction {
    companion object {
        private val ReferenceURLMapping: Map<KClass<out BaseRsAction>, String> = mapOf(
            ExtractFunction::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#61-%E6%8F%90%E7%82%BC%E5%87%BD%E6%95%B0%EF%BC%88extract-function%EF%BC%89",
            InlineFunction::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#62-%E5%86%85%E8%81%94%E5%87%BD%E6%95%B0%EF%BC%88inline-function%EF%BC%89",
            ExtractVariable::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#63-%E6%8F%90%E7%82%BC%E5%8F%98%E9%87%8F%EF%BC%88extract-variable%EF%BC%89",
            InlineVariable::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#64-%E5%86%85%E8%81%94%E5%8F%98%E9%87%8F%EF%BC%88inline-variable%EF%BC%89",
            ChangeFunctionDeclaration::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#65-%E6%94%B9%E5%8F%98%E5%87%BD%E6%95%B0%E5%A3%B0%E6%98%8E%EF%BC%88change-function-declaration%EF%BC%89",
            EncapsulateVariable::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#66-%E5%B0%81%E8%A3%85%E5%8F%98%E9%87%8F%EF%BC%88encapsulate-variable%EF%BC%89",
            RenameVariable::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#67-%E5%8F%98%E9%87%8F%E6%94%B9%E5%90%8D%EF%BC%88rename-variable%EF%BC%89",
            IntroduceParameterObject::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#68-%E5%BC%95%E5%85%A5%E5%8F%82%E6%95%B0%E5%AF%B9%E8%B1%A1%EF%BC%88introduce-parameter-object%EF%BC%89",
            CombineFunctionsIntoClass::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#69-%E5%87%BD%E6%95%B0%E7%BB%84%E5%90%88%E6%88%90%E7%B1%BB%EF%BC%88combine-functions-into-class%EF%BC%89",
            CombineFunctionsIntoTransform::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#610-%E5%87%BD%E6%95%B0%E7%BB%84%E5%90%88%E6%88%90%E5%8F%98%E6%8D%A2%EF%BC%88combine-functions-into-transform%EF%BC%89",
            SplitPhase::class to "https://book-refactoring2.ifmicro.com/docs/ch6.html#611-%E6%8B%86%E5%88%86%E9%98%B6%E6%AE%B5%EF%BC%88split-phase%EF%BC%89",
            EncapsulateRecord::class to "https://book-refactoring2.ifmicro.com/docs/ch7.html#71-%E5%B0%81%E8%A3%85%E8%AE%B0%E5%BD%95%EF%BC%88encapsulate-record%EF%BC%89",
            EncapsulateCollection::class to "https://book-refactoring2.ifmicro.com/docs/ch7.html#72-%E5%B0%81%E8%A3%85%E9%9B%86%E5%90%88%EF%BC%88encapsulate-collection%EF%BC%89",
            ReplacePrimitiveWithObject::class to "https://book-refactoring2.ifmicro.com/docs/ch7.html#73-%E4%BB%A5%E5%AF%B9%E8%B1%A1%E5%8F%96%E4%BB%A3%E5%9F%BA%E6%9C%AC%E7%B1%BB%E5%9E%8B%EF%BC%88replace-primitive-with-object%EF%BC%89",
            ReplaceTempWithQuery::class to "https://book-refactoring2.ifmicro.com/docs/ch7.html#74-%E4%BB%A5%E6%9F%A5%E8%AF%A2%E5%8F%96%E4%BB%A3%E4%B8%B4%E6%97%B6%E5%8F%98%E9%87%8F%EF%BC%88replace-temp-with-query%EF%BC%89",
            ExtractClass::class to "https://book-refactoring2.ifmicro.com/docs/ch7.html#75-%E6%8F%90%E7%82%BC%E7%B1%BB%EF%BC%88extract-class%EF%BC%89",
            InlineClass::class to "https://book-refactoring2.ifmicro.com/docs/ch7.html#76-%E5%86%85%E8%81%94%E7%B1%BB%EF%BC%88inline-class%EF%BC%89",
            HideDelegate::class to "https://book-refactoring2.ifmicro.com/docs/ch7.html#77-%E9%9A%90%E8%97%8F%E5%A7%94%E6%89%98%E5%85%B3%E7%B3%BB%EF%BC%88hide-delegate%EF%BC%89",
            RemoveMiddleMan::class to "https://book-refactoring2.ifmicro.com/docs/ch7.html#78-%E7%A7%BB%E9%99%A4%E4%B8%AD%E9%97%B4%E4%BA%BA%EF%BC%88remove-middle-man%EF%BC%89",
            SubstituteAlgorithm::class to "https://book-refactoring2.ifmicro.com/docs/ch7.html#79-%E6%9B%BF%E6%8D%A2%E7%AE%97%E6%B3%95%EF%BC%88substitute-algorithm%EF%BC%89",
            MoveFunction::class to "https://book-refactoring2.ifmicro.com/docs/ch8.html#81-%E6%90%AC%E7%A7%BB%E5%87%BD%E6%95%B0%EF%BC%88move-function%EF%BC%89",
            MoveField::class to "https://book-refactoring2.ifmicro.com/docs/ch8.html#82-%E6%90%AC%E7%A7%BB%E5%AD%97%E6%AE%B5%EF%BC%88move-field%EF%BC%89",
            MoveStatementsIntoFunction::class to "https://book-refactoring2.ifmicro.com/docs/ch8.html#83-%E6%90%AC%E7%A7%BB%E8%AF%AD%E5%8F%A5%E5%88%B0%E5%87%BD%E6%95%B0%EF%BC%88move-statements-into-function%EF%BC%89",
            MoveStatementsToCallers::class to "https://book-refactoring2.ifmicro.com/docs/ch8.html#84-%E6%90%AC%E7%A7%BB%E8%AF%AD%E5%8F%A5%E5%88%B0%E8%B0%83%E7%94%A8%E8%80%85%EF%BC%88move-statements-to-callers%EF%BC%89",
            ReplaceInlineCodeWithFunctionCall::class to "https://book-refactoring2.ifmicro.com/docs/ch8.html#85-%E4%BB%A5%E5%87%BD%E6%95%B0%E8%B0%83%E7%94%A8%E5%8F%96%E4%BB%A3%E5%86%85%E8%81%94%E4%BB%A3%E7%A0%81%EF%BC%88replace-inline-code-with-function-call%EF%BC%89",
            SlideStatements::class to "https://book-refactoring2.ifmicro.com/docs/ch8.html#86-%E7%A7%BB%E5%8A%A8%E8%AF%AD%E5%8F%A5%EF%BC%88slide-statements%EF%BC%89",
            SplitLoop::class to "https://book-refactoring2.ifmicro.com/docs/ch8.html#87-%E6%8B%86%E5%88%86%E5%BE%AA%E7%8E%AF%EF%BC%88split-loop%EF%BC%89",
            ReplaceLoopWithPipeline::class to "https://book-refactoring2.ifmicro.com/docs/ch8.html#88-%E4%BB%A5%E7%AE%A1%E9%81%93%E5%8F%96%E4%BB%A3%E5%BE%AA%E7%8E%AF%EF%BC%88replace-loop-with-pipeline%EF%BC%89",
            RemoveDeadCode::class to "https://book-refactoring2.ifmicro.com/docs/ch8.html#89-%E7%A7%BB%E9%99%A4%E6%AD%BB%E4%BB%A3%E7%A0%81%EF%BC%88remove-dead-code%EF%BC%89",
            SplitVariable::class to "https://book-refactoring2.ifmicro.com/docs/ch9.html#91-%E6%8B%86%E5%88%86%E5%8F%98%E9%87%8F%EF%BC%88split-variable%EF%BC%89",
            RenameField::class to "https://book-refactoring2.ifmicro.com/docs/ch9.html#92-%E5%AD%97%E6%AE%B5%E6%94%B9%E5%90%8D%EF%BC%88rename-field%EF%BC%89",
            ReplaceDerivedVariableWithQuery::class to "https://book-refactoring2.ifmicro.com/docs/ch9.html#93-%E4%BB%A5%E6%9F%A5%E8%AF%A2%E5%8F%96%E4%BB%A3%E6%B4%BE%E7%94%9F%E5%8F%98%E9%87%8F%EF%BC%88replace-derived-variable-with-query%EF%BC%89",
            ChangeReferenceToValue::class to "https://book-refactoring2.ifmicro.com/docs/ch9.html#94-%E5%B0%86%E5%BC%95%E7%94%A8%E5%AF%B9%E8%B1%A1%E6%94%B9%E4%B8%BA%E5%80%BC%E5%AF%B9%E8%B1%A1%EF%BC%88change-reference-to-value%EF%BC%89",
            ChangeValueToReference::class to "https://book-refactoring2.ifmicro.com/docs/ch9.html#95-%E5%B0%86%E5%80%BC%E5%AF%B9%E8%B1%A1%E6%94%B9%E4%B8%BA%E5%BC%95%E7%94%A8%E5%AF%B9%E8%B1%A1%EF%BC%88change-value-to-reference%EF%BC%89",
            DecomposeConditional::class to "https://book-refactoring2.ifmicro.com/docs/ch10.html#101-%E5%88%86%E8%A7%A3%E6%9D%A1%E4%BB%B6%E8%A1%A8%E8%BE%BE%E5%BC%8F%EF%BC%88decompose-conditional%EF%BC%89",
            ConsolidateConditionalExpression::class to "https://book-refactoring2.ifmicro.com/docs/ch10.html#102-%E5%90%88%E5%B9%B6%E6%9D%A1%E4%BB%B6%E8%A1%A8%E8%BE%BE%E5%BC%8F%EF%BC%88consolidate-conditional-expression%EF%BC%89",
            ReplaceNestedConditionalWithGuardClauses::class to "https://book-refactoring2.ifmicro.com/docs/ch10.html#103-%E4%BB%A5%E5%8D%AB%E8%AF%AD%E5%8F%A5%E5%8F%96%E4%BB%A3%E5%B5%8C%E5%A5%97%E6%9D%A1%E4%BB%B6%E8%A1%A8%E8%BE%BE%E5%BC%8F%EF%BC%88replace-nested-conditional-with-guard-clauses%EF%BC%89",
            ReplaceConditionalWithPolymorphism::class to "https://book-refactoring2.ifmicro.com/docs/ch10.html#104-%E4%BB%A5%E5%A4%9A%E6%80%81%E5%8F%96%E4%BB%A3%E6%9D%A1%E4%BB%B6%E8%A1%A8%E8%BE%BE%E5%BC%8F%EF%BC%88replace-conditional-with-polymorphism%EF%BC%89",
            IntroduceSpecialCase::class to "https://book-refactoring2.ifmicro.com/docs/ch10.html#105-%E5%BC%95%E5%85%A5%E7%89%B9%E4%BE%8B%EF%BC%88introduce-special-case%EF%BC%89",
            IntroduceAssertion::class to "https://book-refactoring2.ifmicro.com/docs/ch10.html#106-%E5%BC%95%E5%85%A5%E6%96%AD%E8%A8%80%EF%BC%88introduce-assertion%EF%BC%89",
            SeparateQueryFromModifier::class to "https://book-refactoring2.ifmicro.com/docs/ch11.html#111-%E5%B0%86%E6%9F%A5%E8%AF%A2%E5%87%BD%E6%95%B0%E5%92%8C%E4%BF%AE%E6%94%B9%E5%87%BD%E6%95%B0%E5%88%86%E7%A6%BB%EF%BC%88separate-query-from-modifier%EF%BC%89",
            ParameterizeFunction::class to "https://book-refactoring2.ifmicro.com/docs/ch11.html#112-%E5%87%BD%E6%95%B0%E5%8F%82%E6%95%B0%E5%8C%96%EF%BC%88parameterize-function%EF%BC%89",
            RemoveFlagArgument::class to "https://book-refactoring2.ifmicro.com/docs/ch11.html#113-%E7%A7%BB%E9%99%A4%E6%A0%87%E8%AE%B0%E5%8F%82%E6%95%B0%EF%BC%88remove-flag-argument%EF%BC%89",
            PreserveWholeObject::class to "https://book-refactoring2.ifmicro.com/docs/ch11.html#114-%E4%BF%9D%E6%8C%81%E5%AF%B9%E8%B1%A1%E5%AE%8C%E6%95%B4%EF%BC%88preserve-whole-object%EF%BC%89",
            ReplaceParameterWithQuery::class to "https://book-refactoring2.ifmicro.com/docs/ch11.html#115-%E4%BB%A5%E6%9F%A5%E8%AF%A2%E5%8F%96%E4%BB%A3%E5%8F%82%E6%95%B0%EF%BC%88replace-parameter-with-query%EF%BC%89",
            ReplaceQueryWithParameter::class to "https://book-refactoring2.ifmicro.com/docs/ch11.html#116-%E4%BB%A5%E5%8F%82%E6%95%B0%E5%8F%96%E4%BB%A3%E6%9F%A5%E8%AF%A2%EF%BC%88replace-query-with-parameter%EF%BC%89",
            RemoveSettingMethod::class to "https://book-refactoring2.ifmicro.com/docs/ch11.html#117-%E7%A7%BB%E9%99%A4%E8%AE%BE%E5%80%BC%E5%87%BD%E6%95%B0%EF%BC%88remove-setting-method%EF%BC%89",
            ReplaceConstructorWithFactoryFunction::class to "https://book-refactoring2.ifmicro.com/docs/ch11.html#118-%E4%BB%A5%E5%B7%A5%E5%8E%82%E5%87%BD%E6%95%B0%E5%8F%96%E4%BB%A3%E6%9E%84%E9%80%A0%E5%87%BD%E6%95%B0%EF%BC%88replace-constructor-with-factory-function%EF%BC%89",
            ReplaceFunctionWithCommand::class to "https://book-refactoring2.ifmicro.com/docs/ch11.html#119-%E4%BB%A5%E5%91%BD%E4%BB%A4%E5%8F%96%E4%BB%A3%E5%87%BD%E6%95%B0%EF%BC%88replace-function-with-command%EF%BC%89",
            ReplaceCommandWithFunction::class to "https://book-refactoring2.ifmicro.com/docs/ch11.html#1110-%E4%BB%A5%E5%87%BD%E6%95%B0%E5%8F%96%E4%BB%A3%E5%91%BD%E4%BB%A4%EF%BC%88replace-command-with-function%EF%BC%89",
            PullUpMethod::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#121-%E5%87%BD%E6%95%B0%E4%B8%8A%E7%A7%BB%EF%BC%88pull-up-method%EF%BC%89",
            PullUpField::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#122-%E5%AD%97%E6%AE%B5%E4%B8%8A%E7%A7%BB%EF%BC%88pull-up-field%EF%BC%89",
            PullUpConstructorBody::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#123-%E6%9E%84%E9%80%A0%E5%87%BD%E6%95%B0%E6%9C%AC%E4%BD%93%E4%B8%8A%E7%A7%BB%EF%BC%88pull-up-constructor-body%EF%BC%89",
            PushDownMethod::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#124-%E5%87%BD%E6%95%B0%E4%B8%8B%E7%A7%BB%EF%BC%88push-down-method%EF%BC%89",
            PushDownField::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#125-%E5%AD%97%E6%AE%B5%E4%B8%8B%E7%A7%BB%EF%BC%88push-down-field%EF%BC%89",
            ReplaceTypeCodeWithSubclasses::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#126-%E4%BB%A5%E5%AD%90%E7%B1%BB%E5%8F%96%E4%BB%A3%E7%B1%BB%E5%9E%8B%E7%A0%81%EF%BC%88replace-type-code-with-subclasses%EF%BC%89",
            RemoveSubclass::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#127-%E7%A7%BB%E9%99%A4%E5%AD%90%E7%B1%BB%EF%BC%88remove-subclass%EF%BC%89",
            ExtractSuperclass::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#128-%E6%8F%90%E7%82%BC%E8%B6%85%E7%B1%BB%EF%BC%88extract-superclass%EF%BC%89",
            CollapseHierarchy::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#129-%E6%8A%98%E5%8F%A0%E7%BB%A7%E6%89%BF%E4%BD%93%E7%B3%BB%EF%BC%88collapse-hierarchy%EF%BC%89",
            ReplaceSubclassWithDelegate::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#1210-%E4%BB%A5%E5%A7%94%E6%89%98%E5%8F%96%E4%BB%A3%E5%AD%90%E7%B1%BB%EF%BC%88replace-subclass-with-delegate%EF%BC%89",
            ReplaceSuperclassWithDelegate::class to "https://book-refactoring2.ifmicro.com/docs/ch12.html#1211-%E4%BB%A5%E5%A7%94%E6%89%98%E5%8F%96%E4%BB%A3%E8%B6%85%E7%B1%BB%EF%BC%88replace-superclass-with-delegate%EF%BC%89",

            // 坏味道 //

            com.nisus.refactoringsheet.badsmell.MysteriousName::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#31-%E7%A5%9E%E7%A7%98%E5%91%BD%E5%90%8D%EF%BC%88mysterious-name%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.DuplicatedCode::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#32-%E9%87%8D%E5%A4%8D%E4%BB%A3%E7%A0%81%EF%BC%88duplicated-code%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.LongFunction::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#33-%E8%BF%87%E9%95%BF%E5%87%BD%E6%95%B0%EF%BC%88long-function%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.LongParameterList::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#34-%E8%BF%87%E9%95%BF%E5%8F%82%E6%95%B0%E5%88%97%E8%A1%A8%EF%BC%88long-parameter-list%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.MutableData::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#36-%E5%8F%AF%E5%8F%98%E6%95%B0%E6%8D%AE%EF%BC%88mutable-data%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.DivergentChange::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#37-%E5%8F%91%E6%95%A3%E5%BC%8F%E5%8F%98%E5%8C%96%EF%BC%88divergent-change%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.ShotgunSurgery::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#38-%E9%9C%B0%E5%BC%B9%E5%BC%8F%E4%BF%AE%E6%94%B9%EF%BC%88shotgun-surgery%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.FeatureEnvy::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#39-%E4%BE%9D%E6%81%8B%E6%83%85%E7%BB%93%EF%BC%88feature-envy%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.DataClumps::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#310-%E6%95%B0%E6%8D%AE%E6%B3%A5%E5%9B%A2%EF%BC%88data-clumps%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.PrimitiveObsession::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#311-%E5%9F%BA%E6%9C%AC%E7%B1%BB%E5%9E%8B%E5%81%8F%E6%89%A7%EF%BC%88primitive-obsession%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.RepeatedSwitches::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#312-%E9%87%8D%E5%A4%8D%E7%9A%84-switch-%EF%BC%88repeated-switches%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.Loops::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#313-%E5%BE%AA%E7%8E%AF%E8%AF%AD%E5%8F%A5%EF%BC%88loops%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.LazyElement::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#314-%E5%86%97%E8%B5%98%E7%9A%84%E5%85%83%E7%B4%A0%EF%BC%88lazy-element%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.SpeculativeGenerality::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#315-%E5%A4%B8%E5%A4%B8%E5%85%B6%E8%B0%88%E9%80%9A%E7%94%A8%E6%80%A7%EF%BC%88speculative-generality%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.TemporaryField::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#316-%E4%B8%B4%E6%97%B6%E5%AD%97%E6%AE%B5%EF%BC%88temporary-field%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.MessageChains::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#317-%E8%BF%87%E9%95%BF%E7%9A%84%E6%B6%88%E6%81%AF%E9%93%BE%EF%BC%88message-chains%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.MiddleMan::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#318-%E4%B8%AD%E9%97%B4%E4%BA%BA%EF%BC%88middle-man%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.LargeClass::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#320-%E8%BF%87%E5%A4%A7%E7%9A%84%E7%B1%BB%EF%BC%88large-class%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.AlternativeClassesWithDifferentInterfaces::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#320-%E8%BF%87%E5%A4%A7%E7%9A%84%E7%B1%BB%EF%BC%88large-class%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.DataClass::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#322-%E7%BA%AF%E6%95%B0%E6%8D%AE%E7%B1%BB%EF%BC%88data-class%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.RefusedBequest::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#323-%E8%A2%AB%E6%8B%92%E7%BB%9D%E7%9A%84%E9%81%97%E8%B5%A0%EF%BC%88refused-bequest%EF%BC%89",
            com.nisus.refactoringsheet.badsmell.Comments::class to "https://book-refactoring2.ifmicro.com/docs/ch3.html#324-%E6%B3%A8%E9%87%8A%EF%BC%88comments%EF%BC%89",
        )
    }

    fun gotoReference() {
        val url = ReferenceURLMapping[this::class] ?: return
        CommandUtil.browse(URI(url))
    }

}