package com.captrojo.resadditae.packet;

import io.netty.buffer.ByteBuf;

public enum DynamicTypeRW
{
	BYTE,
	SHORT,
	INT,
	LONG,
	FLOAT,
	DOUBLE,
	STRING;
	
	public static void write(ByteBuf buf, Object obj)
	{
		if (obj instanceof Byte) {
			buf.writeByte(BYTE.ordinal());
			buf.writeByte((byte) obj);
		} else if (obj instanceof Short) {
			buf.writeByte(SHORT.ordinal());
			buf.writeShort((short) obj);
		} else if (obj instanceof Integer) {
			buf.writeByte(INT.ordinal());
			buf.writeInt((int) obj);
		} else if (obj instanceof Long) {
			buf.writeByte(LONG.ordinal());
			buf.writeLong((long) obj);
		} else if (obj instanceof Float) {
			buf.writeByte(FLOAT.ordinal());
			buf.writeFloat((float) obj);
		} else if (obj instanceof Double) {
			buf.writeByte(DOUBLE.ordinal());
			buf.writeDouble((double) obj);
		} else if (obj instanceof String) {
			buf.writeByte(STRING.ordinal());
			for (char c : ((String) obj).toCharArray()) {
				buf.writeChar(c);
			}
			buf.writeChar('\0');
		}
	}
	
	public static Object read(ByteBuf buf)
	{
		DynamicTypeRW dtrw = DynamicTypeRW.values()[buf.readByte()];
		switch (dtrw) {
		case BYTE:
			return buf.readByte();
		case SHORT:
			return buf.readShort();
		case INT:
			return buf.readInt();
		case LONG:
			return buf.readLong();
		case FLOAT:
			return buf.readFloat();
		case DOUBLE:
			return buf.readDouble();
		case STRING:
			String str = "";
			for (char c = buf.readChar(); c != '\0'; c = buf.readChar()) {
				str += c;
			}
			return str;
		}
		return null;
	}
}
