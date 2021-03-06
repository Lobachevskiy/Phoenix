// Generated by delombok at Sat Jul 01 03:46:04 EEST 2017
package com.ontalsoft.flc.lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.ontalsoft.flc.util.BinaryReader;
import com.ontalsoft.flc.util.Util;

public class FLCHeader {
	private int size; // DWORD, Size of FLIC including this header
	private short type; // WORD, File type 0xAF11, 0xAF12, 0xAF30, 0xAF44, ...
	private short frames; // WORD, Number of frames in first segment
	private short width; // WORD, FLIC width in pixels
	private short height; // WORD, FLIC height in pixels
	private short depth; // WORD, Bits per pixel (usually 8)
	private short flags; // WORD, Set to zero or to three
	private int speed; // DWORD, Delay between frames
	private short reserved1; // WORD, Set to zero
	private int created; // DWORD, Date of FLIC creation (FLC only)
	private int creator; // DWORD, Serial number or compiler id (FLC only)
	private int updated; // DWORD, Date of FLIC update (FLC only)
	private int updater; // DWORD, Serial number (FLC only), see creator
	private short aspectDx; // WORD, Width of square rectangle (FLC only)
	private short aspectDy; // WORD, Height of square rectangle (FLC only)
	private short extFlags; // WORD, EGI: flags for specific EGI extensions
	private short keyframes; // WORD, EGI: key-image frequency
	private short totalframes; // WORD, EGI: total number of frames (segments)
	private int reqMemory; // DWORD, EGI: maximum chunk size (uncompressed)
	private short maxRegions; // WORD, EGI: max. number of regions in a CHK_REGION chunk
	private short transpNum; // WORD, EGI: number of transparent levels
	private byte[] reserved2; // BYTE, Set to zero
	private int oframe1; // DWORD, Offset to frame 1 (FLC only)
	private int oframe2; // DWORD, Offset to frame 2 (FLC only)
	private byte[] reserved3; // BYTE, Set to zero

	public FLCHeader() {
		reserved2 = new byte[24];
		reserved3 = new byte[40];
	}

	public static FLCHeader readFromStream(BinaryReader reader) throws IOException {
		FLCHeader header = new FLCHeader();
		header.setSize(reader.readInt32());
		header.setType(reader.readInt16());
		header.setFrames(reader.readInt16());
		header.setWidth(reader.readInt16());
		header.setHeight(reader.readInt16());
		header.setDepth(reader.readInt16());
		header.setFlags(reader.readInt16());
		header.setSpeed(reader.readInt32());
		header.setReserved1(reader.readInt16());
		header.setCreated(reader.readInt32());
		header.setCreator(reader.readInt32());
		header.setUpdated(reader.readInt32());
		header.setUpdater(reader.readInt32());
		header.setAspectDx(reader.readInt16());
		header.setAspectDy(reader.readInt16());
		header.setExtFlags(reader.readInt16());
		header.setKeyframes(reader.readInt16());
		header.setTotalframes(reader.readInt16());
		header.setReqMemory(reader.readInt32());
		header.setMaxRegions(reader.readInt16());
		header.setTranspNum(reader.readInt16());
		header.setReserved2(reader.readBytes(24));
		header.setOframe1(reader.readInt32());
		header.setOframe2(reader.readInt32());
		header.setReserved3(reader.readBytes(40));
		return header;
	}

	private static final Map<String, String> formatsMap = new HashMap<String, String>(5) {
		private static final long serialVersionUID = -1811058103671440902L;
		{
			put("AF11", "FLI");
			put("AF12", "Standard FLC");
			put("AF44", "Non-standard colour depth FLIC");
			put("AF30", "Huffman or BWT compressed FLIC");
			put("AF31", "Frame shift compressed FLIC");
		}
	};

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("------ FLC HEADER ------");
		sb.append("\nsize = " + size);
		String typeHex = Util.byteArrayToHexString(Util.int16ToByteArray(type, true));
		sb.append("\ntype = " + typeHex + " (" + formatsMap.get(typeHex) + ")");
		sb.append("\nframes = " + frames);
		sb.append("\nwidth = " + width);
		sb.append("\nheight = " + height);
		sb.append("\ndepth = " + depth);
		sb.append("\nflags = " + flags);
		sb.append("\nspeed = " + speed);
		sb.append("\nreserved1 = " + reserved1);
		byte[] createdBytes = Util.int32ToByteArray(created, false);
		sb.append("\ncreated = " + Util.byteArrayToHexString(createdBytes) + " (" + Util.msDosDecimalDateToString(created) + ")");
		byte[] creatorBytes = Util.int32ToByteArray(creator, false);
		sb.append("\ncreator = " + Util.byteArrayToHexString(creatorBytes) + " (" + new String(creatorBytes) + ")");
		byte[] updatedBytes = Util.int32ToByteArray(updated, false);
		sb.append("\nupdated = " + Util.byteArrayToHexString(updatedBytes) + " (" + Util.msDosDecimalDateToString(updated) + ")");
		byte[] updaterBytes = Util.int32ToByteArray(updater, false);
		sb.append("\nupdater = " + Util.byteArrayToHexString(updaterBytes) + " (" + new String(updaterBytes) + ")");
		sb.append("\naspectDx = " + aspectDx);
		sb.append("\naspectDy = " + aspectDy);
		sb.append("\nextFlags = " + extFlags);
		sb.append("\nkeyframes = " + keyframes);
		sb.append("\ntotalframes = " + totalframes);
		sb.append("\nreqMemory = " + reqMemory);
		sb.append("\nmaxRegions = " + maxRegions);
		sb.append("\ntranspNum = " + transpNum);
		sb.append("\nreserved2 = " + Util.byteArrayToHexString(reserved2));
		sb.append("\noframe1 = " + oframe1);
		sb.append("\noframe2 = " + oframe2);
		sb.append("\nreserved3 = " + Util.byteArrayToHexString(reserved3));
		sb.append("\n");
		return sb.toString();
	}

	@java.lang.SuppressWarnings("all")
	public int getSize() {
		return this.size;
	}

	@java.lang.SuppressWarnings("all")
	public void setSize(final int size) {
		this.size = size;
	}

	@java.lang.SuppressWarnings("all")
	public short getType() {
		return this.type;
	}

	@java.lang.SuppressWarnings("all")
	public void setType(final short type) {
		this.type = type;
	}

	@java.lang.SuppressWarnings("all")
	public short getFrames() {
		return this.frames;
	}

	@java.lang.SuppressWarnings("all")
	public void setFrames(final short frames) {
		this.frames = frames;
	}

	@java.lang.SuppressWarnings("all")
	public short getWidth() {
		return this.width;
	}

	@java.lang.SuppressWarnings("all")
	public void setWidth(final short width) {
		this.width = width;
	}

	@java.lang.SuppressWarnings("all")
	public short getHeight() {
		return this.height;
	}

	@java.lang.SuppressWarnings("all")
	public void setHeight(final short height) {
		this.height = height;
	}

	@java.lang.SuppressWarnings("all")
	public short getDepth() {
		return this.depth;
	}

	@java.lang.SuppressWarnings("all")
	public void setDepth(final short depth) {
		this.depth = depth;
	}

	@java.lang.SuppressWarnings("all")
	public short getFlags() {
		return this.flags;
	}

	@java.lang.SuppressWarnings("all")
	public void setFlags(final short flags) {
		this.flags = flags;
	}

	@java.lang.SuppressWarnings("all")
	public int getSpeed() {
		return this.speed;
	}

	@java.lang.SuppressWarnings("all")
	public void setSpeed(final int speed) {
		this.speed = speed;
	}

	@java.lang.SuppressWarnings("all")
	public short getReserved1() {
		return this.reserved1;
	}

	@java.lang.SuppressWarnings("all")
	public void setReserved1(final short reserved1) {
		this.reserved1 = reserved1;
	}

	@java.lang.SuppressWarnings("all")
	public int getCreated() {
		return this.created;
	}

	@java.lang.SuppressWarnings("all")
	public void setCreated(final int created) {
		this.created = created;
	}

	@java.lang.SuppressWarnings("all")
	public int getCreator() {
		return this.creator;
	}

	@java.lang.SuppressWarnings("all")
	public void setCreator(final int creator) {
		this.creator = creator;
	}

	@java.lang.SuppressWarnings("all")
	public int getUpdated() {
		return this.updated;
	}

	@java.lang.SuppressWarnings("all")
	public void setUpdated(final int updated) {
		this.updated = updated;
	}

	@java.lang.SuppressWarnings("all")
	public int getUpdater() {
		return this.updater;
	}

	@java.lang.SuppressWarnings("all")
	public void setUpdater(final int updater) {
		this.updater = updater;
	}

	@java.lang.SuppressWarnings("all")
	public short getAspectDx() {
		return this.aspectDx;
	}

	@java.lang.SuppressWarnings("all")
	public void setAspectDx(final short aspectDx) {
		this.aspectDx = aspectDx;
	}

	@java.lang.SuppressWarnings("all")
	public short getAspectDy() {
		return this.aspectDy;
	}

	@java.lang.SuppressWarnings("all")
	public void setAspectDy(final short aspectDy) {
		this.aspectDy = aspectDy;
	}

	@java.lang.SuppressWarnings("all")
	public short getExtFlags() {
		return this.extFlags;
	}

	@java.lang.SuppressWarnings("all")
	public void setExtFlags(final short extFlags) {
		this.extFlags = extFlags;
	}

	@java.lang.SuppressWarnings("all")
	public short getKeyframes() {
		return this.keyframes;
	}

	@java.lang.SuppressWarnings("all")
	public void setKeyframes(final short keyframes) {
		this.keyframes = keyframes;
	}

	@java.lang.SuppressWarnings("all")
	public short getTotalframes() {
		return this.totalframes;
	}

	@java.lang.SuppressWarnings("all")
	public void setTotalframes(final short totalframes) {
		this.totalframes = totalframes;
	}

	@java.lang.SuppressWarnings("all")
	public int getReqMemory() {
		return this.reqMemory;
	}

	@java.lang.SuppressWarnings("all")
	public void setReqMemory(final int reqMemory) {
		this.reqMemory = reqMemory;
	}

	@java.lang.SuppressWarnings("all")
	public short getMaxRegions() {
		return this.maxRegions;
	}

	@java.lang.SuppressWarnings("all")
	public void setMaxRegions(final short maxRegions) {
		this.maxRegions = maxRegions;
	}

	@java.lang.SuppressWarnings("all")
	public short getTranspNum() {
		return this.transpNum;
	}

	@java.lang.SuppressWarnings("all")
	public void setTranspNum(final short transpNum) {
		this.transpNum = transpNum;
	}

	@java.lang.SuppressWarnings("all")
	public byte[] getReserved2() {
		return this.reserved2;
	}

	@java.lang.SuppressWarnings("all")
	public void setReserved2(final byte[] reserved2) {
		this.reserved2 = reserved2;
	}

	@java.lang.SuppressWarnings("all")
	public int getOframe1() {
		return this.oframe1;
	}

	@java.lang.SuppressWarnings("all")
	public void setOframe1(final int oframe1) {
		this.oframe1 = oframe1;
	}

	@java.lang.SuppressWarnings("all")
	public int getOframe2() {
		return this.oframe2;
	}

	@java.lang.SuppressWarnings("all")
	public void setOframe2(final int oframe2) {
		this.oframe2 = oframe2;
	}

	@java.lang.SuppressWarnings("all")
	public byte[] getReserved3() {
		return this.reserved3;
	}

	@java.lang.SuppressWarnings("all")
	public void setReserved3(final byte[] reserved3) {
		this.reserved3 = reserved3;
	}
}
