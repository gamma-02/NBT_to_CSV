package net.gneisscode.nbttocsv.utils;

import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.EndTag;
import net.querz.nbt.tag.Tag;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.zip.GZIPInputStream;

public class Util {

    public static <T> List<T> shuffledCopy(T[] p_214682_, Random p_214683_) {
        ObjectArrayList<T> objectarraylist = new ObjectArrayList<>(p_214682_);
        shuffle(objectarraylist, p_214683_);
        return objectarraylist;
    }

    public static <T> List<T> shuffledCopy(ObjectArrayList<T> p_214612_, Random p_214613_) {
        ObjectArrayList<T> objectarraylist = new ObjectArrayList<>(p_214612_);
        shuffle(objectarraylist, p_214613_);
        return objectarraylist;
    }

    public static <T> void shuffle(ObjectArrayList<T> p_214674_, Random p_214675_) {
        int i = p_214674_.size();

        for(int j = i; j > 1; --j) {
            int k = p_214675_.nextInt(j);
            p_214674_.set(j - 1, p_214674_.set(k, p_214674_.get(j - 1)));
        }

    }
    public static DataResult<int[]> fixedSize(IntStream pStream, int pSize) {
        int[] aint = pStream.limit((long)(pSize + 1)).toArray();
        if (aint.length != pSize) {
            String s = "Input is not a list of " + pSize + " ints";
            return aint.length >= pSize ? DataResult.error(s, Arrays.copyOf(aint, pSize)) : DataResult.error(s);
        } else {
            return DataResult.success(aint);
        }
    }
    public static <T> DataResult<List<T>> fixedSize(List<T> pList, int pExpectedSize) {
        if (pList.size() != pExpectedSize) {
            String s = "Input is not a list of " + pExpectedSize + " elements";
            return pList.size() >= pExpectedSize ? DataResult.error(s, pList.subList(0, pExpectedSize)) : DataResult.error(s);
        } else {
            return DataResult.success(pList);
        }
    }
    public static <T> T getRandom(T[] pSelections, Random pRandom) {
        return pSelections[pRandom.nextInt(pSelections.length)];
    }

    /**
     * Method for linear interpolation of floats
     * @param pDelta A value usually between 0 and 1 that indicates the percentage of the lerp. (0 will give the start
     * value and 1 will give the end value)
     * @param pStart Start value for the lerp
     * @param pEnd End value for the lerp
     */
    public static float lerp(float pDelta, float pStart, float pEnd) {
        return pStart + pDelta * (pEnd - pStart);
    }

    /**
     * Method for linear interpolation of doubles
     * @param pDelta A value usually between 0 and 1 that indicates the percentage of the lerp. (0 will give the start
     * value and 1 will give the end value)
     * @param pStart Start value for the lerp
     * @param pEnd End value for the lerp
     */
    public static double lerp(double pDelta, double pStart, double pEnd) {
        return pStart + pDelta * (pEnd - pStart);
    }

    public static double lerp2(double p_14013_, double p_14014_, double p_14015_, double p_14016_, double p_14017_, double p_14018_) {
        return lerp(p_14014_, lerp(p_14013_, p_14015_, p_14016_), lerp(p_14013_, p_14017_, p_14018_));
    }

    public static double lerp3(double p_14020_, double p_14021_, double p_14022_, double p_14023_, double p_14024_, double p_14025_, double p_14026_, double p_14027_, double p_14028_, double p_14029_, double p_14030_) {
        return lerp(p_14022_, lerp2(p_14020_, p_14021_, p_14023_, p_14024_, p_14025_, p_14026_), lerp2(p_14020_, p_14021_, p_14027_, p_14028_, p_14029_, p_14030_));
    }

    public static float catmullrom(float p_216245_, float p_216246_, float p_216247_, float p_216248_, float p_216249_) {
        return 0.5F * (2.0F * p_216247_ + (p_216248_ - p_216246_) * p_216245_ + (2.0F * p_216246_ - 5.0F * p_216247_ + 4.0F * p_216248_ - p_216249_) * p_216245_ * p_216245_ + (3.0F * p_216247_ - p_216246_ - 3.0F * p_216248_ + p_216249_) * p_216245_ * p_216245_ * p_216245_);
    }

    public static double smoothstep(double p_14198_) {
        return p_14198_ * p_14198_ * p_14198_ * (p_14198_ * (p_14198_ * 6.0D - 15.0D) + 10.0D);
    }

    public static double smoothstepDerivative(double p_144947_) {
        return 30.0D * p_144947_ * p_144947_ * (p_144947_ - 1.0D) * (p_144947_ - 1.0D);
    }

    public static int log2nlz( int bits )
    {
        if( bits == 0 )
            return 0; // or throw exception
        return 31 - Integer.numberOfLeadingZeros( bits );
    }

    /**
     * Returns the input value rounded up to the next highest power of two.
     */
    public static int smallestEncompassingPowerOfTwo(int pValue) {
        int i = pValue - 1;
        i |= i >> 1;
        i |= i >> 2;
        i |= i >> 4;
        i |= i >> 8;
        i |= i >> 16;
        return i + 1;
    }

    public static CompoundTag readCompressed(File pFile) throws IOException {
        InputStream inputstream = new FileInputStream(pFile);

        CompoundTag compoundtag;
        try {
            compoundtag = readCompressed(inputstream);
        } catch (Throwable throwable1) {
            try {
                inputstream.close();
            } catch (Throwable throwable) {
                throwable1.addSuppressed(throwable);
            }

            throw throwable1;
        }

        inputstream.close();
        return compoundtag;
    }

    public static CompoundTag readCompressed(InputStream pZippedStream) throws IOException {
        DataInputStream datainputstream = createDecompressorStream(pZippedStream);

        CompoundTag compoundtag;
        try {
            compoundtag = read(datainputstream, NbtAccounter.UNLIMITED);
        } catch (Throwable throwable1) {
            if (datainputstream != null) {
                try {
                    datainputstream.close();
                } catch (Throwable throwable) {
                    throwable1.addSuppressed(throwable);
                }
            }

            throw throwable1;
        }

        if (datainputstream != null) {
            datainputstream.close();
        }

        return compoundtag;
    }

    private static DataInputStream createDecompressorStream(InputStream pZippedStream) throws IOException {
        return new DataInputStream(new FastBufferedInputStream(new GZIPInputStream(pZippedStream)));
    }

    /**
     * Reads a compound tag from a file. The size of the file is limited by the {@code accounter}.
     * @throws RuntimeException if the size of the file is larger than the maximum amount of bytes specified by the
     * {@code accounter}
     */
    public static CompoundTag read(DataInput pInput, NbtAccounter pAccounter) throws IOException {
        Tag tag = readUnnamedTag(pInput, 0, pAccounter);
        if (tag instanceof CompoundTag) {
            return (CompoundTag)tag;
        } else {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    private static Tag readUnnamedTag(DataInput pInput, int pDepth, NbtAccounter pAccounter) throws IOException {
        byte b0 = pInput.readByte();
        pAccounter.accountBits(8); // Forge: Count everything!
        if (b0 == 0) {
            return EndTag.INSTANCE;
        } else {
            pAccounter.readUTF(pInput.readUTF()); //Forge: Count this string.
            pAccounter.accountBits(32); //Forge: 4 extra bytes for the object allocation.

            try {
                return TagTypes.getType(b0).load(pInput, pDepth, pAccounter);
            } catch (IOException ioexception) {
                throw new RuntimeException(ioexception);
            }
        }
    }

}
