package com.sinosoft.claim.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptEngineUtils {
	public static ScriptEngine engine = null;

	private static ScriptEngine getScriptEngine() {
		if (engine == null) {
			ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
			engine = scriptEngineManager.getEngineByName("js");
		}
		return engine;
	}

	/***
	 * 动态执行java代码，并返回结果集。同名函数一次编译后不再编译，因此id需要具有唯一性。
	 * 适合固定方法内容的函数集。（同函数省略编译）
	 * @param id
	 * @param expr 动态执行的代码集
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Object eval(String id, String expr, Map<String, Object> params) throws Exception {
		String function = "f" + id;
		String source = "function " + function + "() {" + expr + "}";
		ScriptEngine scriptEngine = getScriptEngine();
		Compilable compilable = (Compilable) scriptEngine;
		CompiledScript script = compilable.compile(source);
		ScriptEngine engine = ScriptEngineUtils.getScriptEngine();
		Bindings bindings = engine.createBindings();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				bindings.put(entry.getKey(), entry.getValue());
			}
		}
		return eval(function, script, bindings);
	}

	private static synchronized Object eval(String fn, CompiledScript script, Bindings bindings) {
		try {
			engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
			script.eval();
			return ((Invocable) engine).invokeFunction(fn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * 动态执行java代码，并返回结果集。
	 * @param expr
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Object eval(String expr, Map<String, Object> params) throws Exception {
		String function = "fevalExpression";
		String source = "function " + function + "() {" + expr + "}";
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("js");
		Compilable engine = (Compilable) scriptEngine;
		CompiledScript script = engine.compile(source);
		Bindings bindings = scriptEngine.createBindings();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				bindings.put(entry.getKey(), entry.getValue());
			}
		}
		scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		script.eval(bindings);
		return ((Invocable) scriptEngine).invokeFunction(function);
	}

	public static void main(String args[]) throws Exception {
		String expr = " return \"2\".equals(p);";
		String expr1 = " return \"3\".equals(p);";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("p", "2");
		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("p", "3");
		long l1 = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			eval("",expr, params);
		}
		long l2 = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			eval(expr, params);
		}
		long l3 = System.currentTimeMillis();
		System.err.println("eval:"+(l2-l1));
		System.err.println("eval1:"+(l3-l2));
	}
}
