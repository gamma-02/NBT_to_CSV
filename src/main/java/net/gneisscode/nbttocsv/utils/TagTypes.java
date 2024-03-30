package net.gneisscode.nbttocsv.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.querz.nbt.tag.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * help. this is actual Mojang code. why.
 */
public class TagTypes {
    public static final TagType<EndTag> END_TAG_TYPE = new TagType<EndTag>() {
        public EndTag load(DataInput p_128550_, int p_128551_, NbtAccounter p_128552_) {
            p_128552_.accountBits(64L);
            return EndTag.INSTANCE;
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197465_, StreamTagVisitor p_197466_) {
            return p_197466_.visitEnd();
        }

        public void skip(DataInput p_197462_, int p_197463_) {
        }

        public void skip(DataInput p_197460_) {
        }

        public String getName() {
            return "END";
        }

        public String getPrettyName() {
            return "TAG_End";
        }

        public boolean isValue() {
            return true;
        }
    };
    public static final TagType<ByteTag> BYTE_TAG_TYPE = new TagType.StaticSize<ByteTag>() {
        public ByteTag load(DataInput p_128292_, int p_128293_, NbtAccounter p_128294_) throws IOException {
            p_128294_.accountBits(72L);
            return ByteTagCache.cache[128 + p_128292_.readByte()];
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197438_, StreamTagVisitor p_197439_) throws IOException {
            return p_197439_.visit(p_197438_.readByte());
        }

        public int size() {
            return 1;
        }

        public String getName() {
            return "BYTE";
        }

        public String getPrettyName() {
            return "TAG_Byte";
        }

        public boolean isValue() {
            return true;
        }
    };
    public static final TagType<ShortTag> SHORT_TAG_TYPE = new TagType.StaticSize<ShortTag>() {
        public ShortTag load(DataInput p_129277_, int p_129278_, NbtAccounter p_129279_) throws IOException {
            p_129279_.accountBits(80L);
            return valueOf(p_129277_.readShort());
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197517_, StreamTagVisitor p_197518_) throws IOException {
            return p_197518_.visit(p_197517_.readShort());
        }

        public int size() {
            return 2;
        }

        public String getName() {
            return "SHORT";
        }

        public String getPrettyName() {
            return "TAG_Short";
        }

        public boolean isValue() {
            return true;
        }
    };

    public static final TagType<IntTag> INT_TAG_TYPE = new TagType.StaticSize<IntTag>() {
        public IntTag load(DataInput p_128703_, int p_128704_, NbtAccounter p_128705_) throws IOException {
            p_128705_.accountBits(96L);
            return valueOf(p_128703_.readInt());
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197483_, StreamTagVisitor p_197484_) throws IOException {
            return p_197484_.visit(p_197483_.readInt());
        }

        public int size() {
            return 4;
        }

        public String getName() {
            return "INT";
        }

        public String getPrettyName() {
            return "TAG_Int";
        }

        public boolean isValue() {
            return true;
        }
    };
    public static final TagType<LongTag> LONG_TAG_TYPE = new TagType.StaticSize<LongTag>() {
        public LongTag load(DataInput p_128906_, int p_128907_, NbtAccounter p_128908_) throws IOException {
            p_128908_.accountBits(128L);
            return valueOf(p_128906_.readLong());
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197506_, StreamTagVisitor p_197507_) throws IOException {
            return p_197507_.visit(p_197506_.readLong());
        }

        public int size() {
            return 8;
        }

        public String getName() {
            return "LONG";
        }

        public String getPrettyName() {
            return "TAG_Long";
        }

        public boolean isValue() {
            return true;
        }
    };
    public static final TagType<FloatTag> FLOAT_TAG_TYPE = new TagType.StaticSize<FloatTag>() {
        public FloatTag load(DataInput p_128590_, int p_128591_, NbtAccounter p_128592_) throws IOException {
            p_128592_.accountBits(96L);
            return valueOf(p_128590_.readFloat());
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197470_, StreamTagVisitor p_197471_) throws IOException {
            return p_197471_.visit(p_197470_.readFloat());
        }

        public int size() {
            return 4;
        }

        public String getName() {
            return "FLOAT";
        }

        public String getPrettyName() {
            return "TAG_Float";
        }

        public boolean isValue() {
            return true;
        }
    };
    public static final TagType<DoubleTag> DOUBLE_TAG_TYPE = new TagType.StaticSize<DoubleTag>() {
        public DoubleTag load(DataInput p_128524_, int p_128525_, NbtAccounter p_128526_) throws IOException {
            p_128526_.accountBits(128L);
            return valueOf(p_128524_.readDouble());
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197454_, StreamTagVisitor p_197455_) throws IOException {
            return p_197455_.visit(p_197454_.readDouble());
        }

        public int size() {
            return 8;
        }

        public String getName() {
            return "DOUBLE";
        }

        public String getPrettyName() {
            return "TAG_Double";
        }

        public boolean isValue() {
            return true;
        }
    };

    public static final TagType<ByteArrayTag> BYTE_ARRAY_TAG_TYPE = new TagType.VariableSize<ByteArrayTag>() {
        public ByteArrayTag load(DataInput p_128247_, int p_128248_, NbtAccounter p_128249_) throws IOException {
            p_128249_.accountBits(192L);
            int i = p_128247_.readInt();
            p_128249_.accountBits(8L * (long)i);
            byte[] abyte = new byte[i];
            p_128247_.readFully(abyte);
            return new ByteArrayTag(abyte);
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197433_, StreamTagVisitor p_197434_) throws IOException {
            int i = p_197433_.readInt();
            byte[] abyte = new byte[i];
            p_197433_.readFully(abyte);
            return p_197434_.visit(abyte);
        }

        public void skip(DataInput p_197431_) throws IOException {
            p_197431_.skipBytes(p_197431_.readInt() * 1);
        }

        public String getName() {
            return "BYTE[]";
        }

        public String getPrettyName() {
            return "TAG_Byte_Array";
        }
    };

    public static final TagType<StringTag> STRING_TAG_TYPE = new TagType.VariableSize<StringTag>() {
        public StringTag load(DataInput p_129315_, int p_129316_, NbtAccounter p_129317_) throws IOException {
            p_129317_.accountBits(288L);
            String s = p_129315_.readUTF();
            p_129317_.readUTF(s);
            return valueOf(s);
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197570_, StreamTagVisitor p_197571_) throws IOException {
            return p_197571_.visit(p_197570_.readUTF());
        }

        public void skip(DataInput p_197568_) throws IOException {
            p_197568_.skipBytes(p_197568_.readUnsignedShort());
        }

        public String getName() {
            return "STRING";
        }

        public String getPrettyName() {
            return "TAG_String";
        }

        public boolean isValue() {
            return true;
        }
    };

    public static final TagType<ListTag<Tag<?>>> LIST_TAG_TYPE = new TagType.VariableSize<>() {
        public ListTag<Tag<?>> load(DataInput p_128792_, int p_128793_, NbtAccounter p_128794_) throws IOException {
            p_128794_.accountBits(296L);
            if (p_128793_ > 512) {
                throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
            } else {
                byte b0 = p_128792_.readByte();
                int i = p_128792_.readInt();
                if (b0 == 0 && i > 0) {
                    throw new RuntimeException("Missing type on ListTag");
                } else {
                    p_128794_.accountBits(32L * (long)i);
                    TagType<?> tagtype = TagTypes.getType(b0);
                    List<Tag<?>> list = Lists.newArrayListWithCapacity(i);

                    for(int j = 0; j < i; ++j) {
                        list.add(tagtype.load(p_128792_, p_128793_ + 1, p_128794_));
                    }
                    ListTag<Tag<?>> listTag;
                    if(!list.isEmpty()) {
                        listTag = new ListTag<>((Class<? super Tag<?>>) list.getFirst().getClass());//lord save us all.

                        listTag.addAll(list);
                        return  listTag;
                    }

                    return new ListTag<>(Tag.class);
                }
            }
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197491_, StreamTagVisitor p_197492_) throws IOException {
            TagType<?> tagtype = TagTypes.getType(p_197491_.readByte());
            int i = p_197491_.readInt();
            switch (p_197492_.visitList(tagtype, i)) {
                case HALT:
                    return StreamTagVisitor.ValueResult.HALT;
                case BREAK:
                    tagtype.skip(p_197491_, i);
                    return p_197492_.visitContainerEnd();
                default:
                    int j = 0;

                    while(true) {
                        label45: {
                            if (j < i) {
                                switch (p_197492_.visitElement(tagtype, j)) {
                                    case HALT:
                                        return StreamTagVisitor.ValueResult.HALT;
                                    case BREAK:
                                        tagtype.skip(p_197491_);
                                        break;
                                    case SKIP:
                                        tagtype.skip(p_197491_);
                                        break label45;
                                    default:
                                        switch (tagtype.parse(p_197491_, p_197492_)) {
                                            case HALT:
                                                return StreamTagVisitor.ValueResult.HALT;
                                            case BREAK:
                                                break;
                                            default:
                                                break label45;
                                        }
                                }
                            }

                            int k = i - 1 - j;
                            if (k > 0) {
                                tagtype.skip(p_197491_, k);
                            }

                            return p_197492_.visitContainerEnd();
                        }

                        ++j;
                    }
            }
        }

        public void skip(DataInput p_197489_) throws IOException {
            TagType<?> tagtype = TagTypes.getType(p_197489_.readByte());
            int i = p_197489_.readInt();
            tagtype.skip(p_197489_, i);
        }

        public String getName() {
            return "LIST";
        }

        public String getPrettyName() {
            return "TAG_List";
        }
    };

    public static final TagType<CompoundTag> COMPOUND_TAG_TYPE = new TagType.VariableSize<CompoundTag>() {
        public CompoundTag load(DataInput p_128485_, int p_128486_, NbtAccounter p_128487_) throws IOException {
            p_128487_.accountBits(384L);
            if (p_128486_ > 512) {
                throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
            } else {
                Map<String, Tag> map = Maps.newHashMap();
                var retTag = new CompoundTag();
                byte b0;
                while((b0 = readNamedTagType(p_128485_, p_128487_)) != 0) {
                    String s = readNamedTagName(p_128485_, p_128487_);
                    p_128487_.accountBits((long)(224 + 16 * s.length()));
                    p_128487_.accountBits(32); //Forge: 4 extra bytes for the object allocation.
                    Tag tag = readNamedTagData(TagTypes.getType(b0), s, p_128485_, p_128486_ + 1, p_128487_);
                    if (retTag.put(s, tag) != null) {
                        p_128487_.accountBits(288L);
                    }
                }


                return retTag;
            }
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197446_, StreamTagVisitor p_197447_) throws IOException {
            while(true) {
                byte b0;
                if ((b0 = p_197446_.readByte()) != 0) {
                    TagType<?> tagtype = TagTypes.getType(b0);
                    switch (p_197447_.visitEntry(tagtype)) {
                        case HALT:
                            return StreamTagVisitor.ValueResult.HALT;
                        case BREAK:
                            skipString(p_197446_);
                            tagtype.skip(p_197446_);
                            break;
                        case SKIP:
                            skipString(p_197446_);
                            tagtype.skip(p_197446_);
                            continue;
                        default:
                            String s = p_197446_.readUTF();
                            switch (p_197447_.visitEntry(tagtype, s)) {
                                case HALT:
                                    return StreamTagVisitor.ValueResult.HALT;
                                case BREAK:
                                    tagtype.skip(p_197446_);
                                    break;
                                case SKIP:
                                    tagtype.skip(p_197446_);
                                    continue;
                                default:
                                    switch (tagtype.parse(p_197446_, p_197447_)) {
                                        case HALT:
                                            return StreamTagVisitor.ValueResult.HALT;
                                        case BREAK:
                                        default:
                                            continue;
                                    }
                            }
                    }
                }

                if (b0 != 0) {
                    while((b0 = p_197446_.readByte()) != 0) {
                        skipString(p_197446_);
                        TagTypes.getType(b0).skip(p_197446_);
                    }
                }

                return p_197447_.visitContainerEnd();
            }
        }

        public void skip(DataInput p_197444_) throws IOException {
            byte b0;
            while((b0 = p_197444_.readByte()) != 0) {
                skipString(p_197444_);
                TagTypes.getType(b0).skip(p_197444_);
            }

        }

        public String getName() {
            return "COMPOUND";
        }

        public String getPrettyName() {
            return "TAG_Compound";
        }

        static byte readNamedTagType(DataInput pInput, NbtAccounter pAccounter) throws IOException {
            pAccounter.accountBits(8);
            return pInput.readByte();
        }

        static String readNamedTagName(DataInput pInput, NbtAccounter pAccounter) throws IOException {
            return pAccounter.readUTF(pInput.readUTF());
        }

        static Tag readNamedTagData(TagType<?> pType, String pName, DataInput pInput, int pDepth, NbtAccounter pAccounter) {
            try {
                return pType.load(pInput, pDepth, pAccounter);
            } catch (IOException ioexception) {
                throw new RuntimeException(ioexception);
            }
        }
    };

    public static final TagType<IntArrayTag> INT_ARRAY_TAG_TYPE = new TagType.VariableSize<IntArrayTag>() {
        public IntArrayTag load(DataInput p_128662_, int p_128663_, NbtAccounter p_128664_) throws IOException {
            p_128664_.accountBits(192L);
            int i = p_128662_.readInt();
            p_128664_.accountBits(32L * (long)i);
            int[] aint = new int[i];

            for(int j = 0; j < i; ++j) {
                aint[j] = p_128662_.readInt();
            }

            return new IntArrayTag(aint);
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197478_, StreamTagVisitor p_197479_) throws IOException {
            int i = p_197478_.readInt();
            int[] aint = new int[i];

            for(int j = 0; j < i; ++j) {
                aint[j] = p_197478_.readInt();
            }

            return p_197479_.visit(aint);
        }

        public void skip(DataInput p_197476_) throws IOException {
            p_197476_.skipBytes(p_197476_.readInt() * 4);
        }

        public String getName() {
            return "INT[]";
        }

        public String getPrettyName() {
            return "TAG_Int_Array";
        }
    };

    public static final TagType<LongArrayTag> LONG_ARRAY_TAG_TYPE = new TagType.VariableSize<LongArrayTag>() {
        public LongArrayTag load(DataInput p_128865_, int p_128866_, NbtAccounter p_128867_) throws IOException {
            p_128867_.accountBits(192L);
            int i = p_128865_.readInt();
            p_128867_.accountBits(64L * (long)i);
            long[] along = new long[i];

            for(int j = 0; j < i; ++j) {
                along[j] = p_128865_.readLong();
            }

            return new LongArrayTag(along);
        }

        public StreamTagVisitor.ValueResult parse(DataInput p_197501_, StreamTagVisitor p_197502_) throws IOException {
            int i = p_197501_.readInt();
            long[] along = new long[i];

            for(int j = 0; j < i; ++j) {
                along[j] = p_197501_.readLong();
            }

            return p_197502_.visit(along);
        }

        public void skip(DataInput p_197499_) throws IOException {
            p_197499_.skipBytes(p_197499_.readInt() * 8);
        }

        public String getName() {
            return "LONG[]";
        }

        public String getPrettyName() {
            return "TAG_Long_Array";
        }
    };







    private static final TagType<?>[] TYPES = new TagType[]{END_TAG_TYPE, BYTE_TAG_TYPE, SHORT_TAG_TYPE, INT_TAG_TYPE, LONG_TAG_TYPE, FLOAT_TAG_TYPE, DOUBLE_TAG_TYPE, BYTE_ARRAY_TAG_TYPE, STRING_TAG_TYPE, LIST_TAG_TYPE, COMPOUND_TAG_TYPE, INT_ARRAY_TAG_TYPE, LONG_ARRAY_TAG_TYPE};

    public static TagType<?> getType(int pId) {
        return pId >= 0 && pId < TYPES.length ? TYPES[pId] : TagType.createInvalid(pId);
    }

    public static ShortTag valueOf(short pData) {
        return pData >= -128 && pData <= 1024 ? ShortTagCache.cache[pData - -128] : new ShortTag(pData);
    }

    public static IntTag valueOf(int pData) {
        return pData >= -128 && pData <= 1024 ? IntTagCache.cache[pData - -128] : new IntTag(pData);
    }
    public static LongTag valueOf(long pData) {
        return pData >= -128L && pData <= 1024L ? LongTagCache.cache[(int)pData - -128] : new LongTag(pData);
    }
    public static DoubleTag valueOf(double pData) {
        return pData == 0.0D ? new DoubleTag(DoubleTag.ZERO_VALUE) : new DoubleTag(pData);
    }








    static class ByteTagCache {
        static final ByteTag[] cache = new ByteTag[256];

        private ByteTagCache() {
        }

        static {
            for(int i = 0; i < cache.length; ++i) {
                cache[i] = new ByteTag((byte)(i - 128));
            }

        }
    }

    static class ShortTagCache {
        private static final int HIGH = 1024;
        private static final int LOW = -128;
        static final ShortTag[] cache = new ShortTag[1153];

        private ShortTagCache() {
        }

        static {
            for(int i = 0; i < cache.length; ++i) {
                cache[i] = new ShortTag((short)(-128 + i));
            }

        }
    }

    static class IntTagCache {
        private static final int HIGH = 1024;
        private static final int LOW = -128;
        static final IntTag[] cache = new IntTag[1153];

        private IntTagCache() {
        }

        static {
            for(int i = 0; i < cache.length; ++i) {
                cache[i] = new IntTag(-128 + i);
            }

        }
    }

    static class LongTagCache {
        private static final int HIGH = 1024;
        private static final int LOW = -128;
        static final LongTag[] cache = new LongTag[1153];

        private LongTagCache() {
        }

        static {
            for(int i = 0; i < cache.length; ++i) {
                cache[i] = new LongTag((long)(-128 + i));
            }

        }
    }
    public static FloatTag valueOf(float pData) {
        return pData == 0.0F ? new FloatTag(FloatTag.ZERO_VALUE) : new FloatTag(pData);
    }

    public static StringTag valueOf(String pData) {
        return pData.isEmpty() ? new StringTag(StringTag.ZERO_VALUE) : new StringTag(pData);
    }

    public static void skipString(DataInput pInput) throws IOException {
        pInput.skipBytes(pInput.readUnsignedShort());
    }




}