package Controller.Input;

import org.lwjgl.input.Keyboard;

public enum Key 
{
	A(Keyboard.KEY_A),
	B(Keyboard.KEY_B),
	C(Keyboard.KEY_C),
	D(Keyboard.KEY_D),
	E(Keyboard.KEY_E),
	F(Keyboard.KEY_F),
	G(Keyboard.KEY_G), 
	H(Keyboard.KEY_H), 
	I(Keyboard.KEY_I), 
	J(Keyboard.KEY_J), 
	K(Keyboard.KEY_K), 
	L(Keyboard.KEY_L), 
	M(Keyboard.KEY_M),
	N(Keyboard.KEY_N),
	O(Keyboard.KEY_O),
	P(Keyboard.KEY_P),
	Q(Keyboard.KEY_Q),
	R(Keyboard.KEY_R),
	S(Keyboard.KEY_S),
	T(Keyboard.KEY_T),
	U(Keyboard.KEY_U),
	V(Keyboard.KEY_V),
	W(Keyboard.KEY_W),
	X(Keyboard.KEY_X),
	Y(Keyboard.KEY_Y),
	Z(Keyboard.KEY_Z),
	
	SPACE(Keyboard.KEY_SPACE),
	ENTER(Keyboard.KEY_RETURN),
	ESCAPE(Keyboard.KEY_ESCAPE),
	CONTROL(Keyboard.KEY_LCONTROL),
	SHIFT(Keyboard.KEY_LSHIFT),
	TAB(Keyboard.KEY_TAB),
	
	NUM_0(Keyboard.KEY_NUMPAD0),
	NUM_1(Keyboard.KEY_NUMPAD1),
	NUM_2(Keyboard.KEY_NUMPAD2),
	NUM_3(Keyboard.KEY_NUMPAD3),
	NUM_4(Keyboard.KEY_NUMPAD4),
	NUM_5(Keyboard.KEY_NUMPAD5),
	NUM_6(Keyboard.KEY_NUMPAD6),
	NUM_7(Keyboard.KEY_NUMPAD7),
	NUM_8(Keyboard.KEY_NUMPAD8),
	NUM_9(Keyboard.KEY_NUMPAD9),
	
	KEY_NONE(-1);
	
	private int numVal;
	
	Key (int numVal)
	{
		this.numVal = numVal;
	}
	
	public static Key fromInt (int x)
	{
		switch (x)
		{
		case Keyboard.KEY_A: return A;
		case Keyboard.KEY_B: return B;
		case Keyboard.KEY_C: return C;
		case Keyboard.KEY_D: return D;
		case Keyboard.KEY_E: return E;
		case Keyboard.KEY_F: return F;
		case Keyboard.KEY_G: return G;
		case Keyboard.KEY_H: return H;
		case Keyboard.KEY_I: return I;
		case Keyboard.KEY_J: return J;
		case Keyboard.KEY_K: return K;
		case Keyboard.KEY_L: return L;
		case Keyboard.KEY_M: return M;
		case Keyboard.KEY_N: return N;
		case Keyboard.KEY_O: return O;
		case Keyboard.KEY_P: return P;
		case Keyboard.KEY_Q: return Q;
		case Keyboard.KEY_R: return R;
		case Keyboard.KEY_S: return S;
		case Keyboard.KEY_T: return T;
		case Keyboard.KEY_U: return U;
		case Keyboard.KEY_V: return V;
		case Keyboard.KEY_W: return W;
		case Keyboard.KEY_X: return X;
		case Keyboard.KEY_Y: return Y;
		case Keyboard.KEY_Z: return Z;
		case Keyboard.KEY_SPACE: return SPACE;
		case Keyboard.KEY_RETURN: return ENTER;
		case Keyboard.KEY_ESCAPE: return ESCAPE;
		case Keyboard.KEY_LCONTROL: return CONTROL;
		case Keyboard.KEY_LSHIFT: return SHIFT;
		case Keyboard.KEY_TAB: return TAB;
		case Keyboard.KEY_0: return NUM_0;
		case Keyboard.KEY_1: return NUM_1;
		case Keyboard.KEY_2: return NUM_2;
		case Keyboard.KEY_3: return NUM_3;
		case Keyboard.KEY_4: return NUM_4;
		case Keyboard.KEY_5: return NUM_5;
		case Keyboard.KEY_6: return NUM_6;
		case Keyboard.KEY_7: return NUM_7;
		case Keyboard.KEY_8: return NUM_8;
		case Keyboard.KEY_9: return NUM_9;
		
		default: return KEY_NONE;
		}
	}
	
	public int getLWJGLKey()
	{
		return numVal;
	}
}

