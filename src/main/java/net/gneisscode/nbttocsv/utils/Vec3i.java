package net.gneisscode.nbttocsv.utils;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;
import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;




public class Vec3i implements Comparable<Vec3i> {
    public static final Codec<Vec3i> CODEC = Codec.INT_STREAM.comapFlatMap((p_123318_) -> {
        return Util.fixedSize(p_123318_, 3).map((p_175586_) -> {
            return new Vec3i(p_175586_[0], p_175586_[1], p_175586_[2]);
        });
    }, (p_123313_) -> {
        return IntStream.of(p_123313_.getX(), p_123313_.getY(), p_123313_.getZ());
    });
    /** An immutable vector with zero as all coordinates. */
    public static final Vec3i ZERO = new Vec3i(0, 0, 0);
    private int x;
    private int y;
    private int z;

    private static Function<Vec3i, DataResult<Vec3i>> checkOffsetAxes(int p_194646_) {
        return (p_194649_) -> {
            return Math.abs(p_194649_.getX()) < p_194646_ && Math.abs(p_194649_.getY()) < p_194646_ && Math.abs(p_194649_.getZ()) < p_194646_ ? DataResult.success(p_194649_) : DataResult.error("Position out of range, expected at most " + p_194646_ + ": " + p_194649_);
        };
    }

    public static Codec<Vec3i> offsetCodec(int p_194651_) {
        return CODEC.flatXmap(checkOffsetAxes(p_194651_), checkOffsetAxes(p_194651_));
    }

    public Vec3i(int pX, int pY, int pZ) {
        this.x = pX;
        this.y = pY;
        this.z = pZ;
    }

    public Vec3i(double pX, double pY, double pZ) {
        this((int)Math.floor(pX), (int)Math.floor(pY), (int)Math.floor(pZ));
    }

    public boolean equals(Object pOther) {
        if (this == pOther) {
            return true;
        } else if (!(pOther instanceof Vec3i)) {
            return false;
        } else {
            Vec3i vec3i = (Vec3i)pOther;
            if (this.getX() != vec3i.getX()) {
                return false;
            } else if (this.getY() != vec3i.getY()) {
                return false;
            } else {
                return this.getZ() == vec3i.getZ();
            }
        }
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int compareTo(Vec3i pOther) {
        if (this.getY() == pOther.getY()) {
            return this.getZ() == pOther.getZ() ? this.getX() - pOther.getX() : this.getZ() - pOther.getZ();
        } else {
            return this.getY() - pOther.getY();
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    protected Vec3i setX(int pX) {
        this.x = pX;
        return this;
    }

    protected Vec3i setY(int pY) {
        this.y = pY;
        return this;
    }

    protected Vec3i setZ(int pZ) {
        this.z = pZ;
        return this;
    }

    public Vec3i offset(double pDx, double pDy, double pDz) {
        return pDx == 0.0D && pDy == 0.0D && pDz == 0.0D ? this : new Vec3i((double)this.getX() + pDx, (double)this.getY() + pDy, (double)this.getZ() + pDz);
    }

    public Vec3i offset(int pDx, int pDy, int pDz) {
        return pDx == 0 && pDy == 0 && pDz == 0 ? this : new Vec3i(this.getX() + pDx, this.getY() + pDy, this.getZ() + pDz);
    }

    public Vec3i offset(Vec3i pVector) {
        return this.offset(pVector.getX(), pVector.getY(), pVector.getZ());
    }

    public Vec3i subtract(Vec3i pVector) {
        return this.offset(-pVector.getX(), -pVector.getY(), -pVector.getZ());
    }

    public Vec3i multiply(int pScalar) {
        if (pScalar == 1) {
            return this;
        } else {
            return pScalar == 0 ? ZERO : new Vec3i(this.getX() * pScalar, this.getY() * pScalar, this.getZ() * pScalar);
        }
    }

    /**
     * Offset this vector 1 unit up
     */
    public Vec3i above() {
        return this.above(1);
    }

    /**
     * Offset this vector upwards by the given distance.
     */
    public Vec3i above(int pDistance) {
        return this.relative(Direction.UP, pDistance);
    }

    /**
     * Offset this vector 1 unit down
     */
    public Vec3i below() {
        return this.below(1);
    }

    /**
     * Offset this vector downwards by the given distance.
     */
    public Vec3i below(int pDistance) {
        return this.relative(Direction.DOWN, pDistance);
    }

    public Vec3i north() {
        return this.north(1);
    }

    public Vec3i north(int pDistance) {
        return this.relative(Direction.NORTH, pDistance);
    }

    public Vec3i south() {
        return this.south(1);
    }

    public Vec3i south(int pDistance) {
        return this.relative(Direction.SOUTH, pDistance);
    }

    public Vec3i west() {
        return this.west(1);
    }

    public Vec3i west(int pDistance) {
        return this.relative(Direction.WEST, pDistance);
    }

    public Vec3i east() {
        return this.east(1);
    }

    public Vec3i east(int pDistance) {
        return this.relative(Direction.EAST, pDistance);
    }

    public Vec3i relative(Direction pDirection) {
        return this.relative(pDirection, 1);
    }

    /**
     * Offsets this Vector by the given distance in the specified direction.
     */
    public Vec3i relative(Direction pDirection, int pDistance) {
        return pDistance == 0 ? this : new Vec3i(this.getX() + pDirection.getStepX() * pDistance, this.getY() + pDirection.getStepY() * pDistance, this.getZ() + pDirection.getStepZ() * pDistance);
    }

    public Vec3i relative(Direction.Axis pAxis, int pAmount) {
        if (pAmount == 0) {
            return this;
        } else {
            int i = pAxis == Direction.Axis.X ? pAmount : 0;
            int j = pAxis == Direction.Axis.Y ? pAmount : 0;
            int k = pAxis == Direction.Axis.Z ? pAmount : 0;
            return new Vec3i(this.getX() + i, this.getY() + j, this.getZ() + k);
        }
    }

    /**
     * Calculate the cross product of this and the given Vector
     */
    public Vec3i cross(Vec3i pVector) {
        return new Vec3i(this.getY() * pVector.getZ() - this.getZ() * pVector.getY(), this.getZ() * pVector.getX() - this.getX() * pVector.getZ(), this.getX() * pVector.getY() - this.getY() * pVector.getX());
    }

    public boolean closerThan(Vec3i pVector, double pDistance) {
        return this.distSqr(pVector) < (pDistance * pDistance);
    }

    public boolean closerToCenterThan(Position pPosition, double pDistance) {
        return this.distToCenterSqr(pPosition) < (pDistance * pDistance);
    }

    /**
     * Calculate squared distance to the given Vector
     */
    public double distSqr(Vec3i pVector) {
        return this.distToLowCornerSqr((double)pVector.getX(), (double)pVector.getY(), (double)pVector.getZ());
    }

    public double distToCenterSqr(Position pPosition) {
        return this.distToCenterSqr(pPosition.x(), pPosition.y(), pPosition.z());
    }

    public double distToCenterSqr(double pX, double pY, double pZ) {
        double d0 = (double)this.getX() + 0.5D - pX;
        double d1 = (double)this.getY() + 0.5D - pY;
        double d2 = (double)this.getZ() + 0.5D - pZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distToLowCornerSqr(double pX, double pY, double pZ) {
        double d0 = (double)this.getX() - pX;
        double d1 = (double)this.getY() - pY;
        double d2 = (double)this.getZ() - pZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public int distManhattan(Vec3i pVector) {
        float f = (float)Math.abs(pVector.getX() - this.getX());
        float f1 = (float)Math.abs(pVector.getY() - this.getY());
        float f2 = (float)Math.abs(pVector.getZ() - this.getZ());
        return (int)(f + f1 + f2);
    }

//    public int get(Direction.Axis pAxis) {
//        return pAxis.choose(this.x, this.y, this.z);
//    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
    }

    public String toShortString() {
        return this.getX() + ", " + this.getY() + ", " + this.getZ();
    }


}
