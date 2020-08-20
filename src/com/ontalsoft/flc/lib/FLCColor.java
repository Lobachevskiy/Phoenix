// Generated by delombok at Sat Jul 01 03:46:04 EEST 2017
package com.ontalsoft.flc.lib;

public class FLCColor {
	private byte r;
	private byte g;
	private byte b;

	public FLCColor(byte r, byte g, byte b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public String toString() {
		return String.format("R: %02x, G: %02x, B: %02x", r, g, b);
	}

	public int getRgbInt() {
		return (r & 255) << 16 | (g & 255) << 8 | (b & 255);
	}

	@java.lang.SuppressWarnings("all")
	public byte getR() {
		return this.r;
	}

	@java.lang.SuppressWarnings("all")
	public byte getG() {
		return this.g;
	}

	@java.lang.SuppressWarnings("all")
	public byte getB() {
		return this.b;
	}
}
