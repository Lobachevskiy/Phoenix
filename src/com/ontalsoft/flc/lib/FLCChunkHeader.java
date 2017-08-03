// Generated by delombok at Sat Jul 01 03:46:04 EEST 2017
package com.ontalsoft.flc.lib;

import java.io.IOException;
import com.ontalsoft.flc.util.BinaryReader;

public class FLCChunkHeader {
	public int size; // Bytes in this chunk.
	public ChunkType type; // Type of chunk (see below).
	public short subchunks;
	public int reserved1;
	public int reserved2;
	public static final int sizeOf = 16;

	public static FLCChunkHeader readHeader(BinaryReader reader) throws IOException {
		FLCChunkHeader result = new FLCChunkHeader();
		result.size = reader.readInt32();
		result.type = ChunkType.getChunkType(reader.readUInt16());
		result.subchunks = reader.readInt16();
		result.reserved1 = reader.readInt32();
		result.reserved2 = reader.readInt32();
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getSize() {
		return this.size;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public ChunkType getType() {
		return this.type;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public short getSubchunks() {
		return this.subchunks;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getReserved1() {
		return this.reserved1;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getReserved2() {
		return this.reserved2;
	}
}