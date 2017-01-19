
package com.yu.dialog.util;

public class Method {
	public interface Action {
		void invoke();
	}

	public interface ActionE {
		void invoke() throws Exception;
	}

	public interface Action1<T1> {
		void invoke(T1 p);
	}

	public interface Action1E<T1> {
		void invoke(T1 p) throws Exception;
	}

	public interface Action2<T1, T2> {
		void invoke(T1 t1, T2 t2);
	}

	public interface Action3<T1, T2, T3> {
		void invoke(T1 t1, T2 t2, T3 t3);
	}

	public interface Action4<T1, T2, T3, T4> {
		void invoke(T1 t1, T2 t2, T3 t3, T4 t4);
	}

	public interface Func<Tresult> {
		Tresult invoke();
	}

	public interface FuncE<Tresult> {
		Tresult invoke() throws Exception;
	}

	public interface Func1<T1, Tresult> {
		Tresult invoke(T1 t1);
	}

	public interface Func1E<T1, Tresult> {
		Tresult invoke(T1 t1) throws Exception;
	}

	public interface Func2<T1, T2, Tresult> {
		Tresult invoke(T1 t1, T2 t2);
	}

	public interface Func2E<T1, T2, Tresult> {
		Tresult invoke(T1 t1, T2 t2) throws Exception;
	}

	public interface Func3<T1, T2, T3, Tresult> {
		Tresult invoke(T1 t1, T2 t2, T3 t3);
	}

	public interface Func3E<T1, T2, T3, Tresult> {
		Tresult invoke(T1 t1, T2 t2, T3 t3) throws Exception;
	}

	public interface Func4<T1, T2, T3, T4, Tresult> {
		Tresult invoke(T1 t1, T2 t2, T3 t3, T4 t4);
	}

	public interface Func4E<T1, T2, T3, T4, Tresult> {
		Tresult invoke(T1 t1, T2 t2, T3 t3, T4 t4) throws Exception;
	}
}
