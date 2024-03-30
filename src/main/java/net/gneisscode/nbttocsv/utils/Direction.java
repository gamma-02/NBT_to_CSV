package net.gneisscode.nbttocsv.utils;

import com.google.common.collect.Iterators;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum Direction {

    DOWN(0, 1, -1, "down", Direction.AxisDirection.NEGATIVE, Direction.Axis.Y, new Vec3i(0, -1, 0)),
    UP(1, 0, -1, "up", Direction.AxisDirection.POSITIVE, Direction.Axis.Y, new Vec3i(0, 1, 0)),
    NORTH(2, 3, 2, "north", Direction.AxisDirection.NEGATIVE, Direction.Axis.Z, new Vec3i(0, 0, -1)),
    SOUTH(3, 2, 0, "south", Direction.AxisDirection.POSITIVE, Direction.Axis.Z, new Vec3i(0, 0, 1)),
    WEST(4, 5, 1, "west", Direction.AxisDirection.NEGATIVE, Direction.Axis.X, new Vec3i(-1, 0, 0)),
    EAST(5, 4, 3, "east", Direction.AxisDirection.POSITIVE, Direction.Axis.X, new Vec3i(1, 0, 0));





    /** Ordering index for D-U-N-S-W-E */
    private final int data3d;
    /** Index of the opposite Direction in the VALUES array */
    private final int oppositeIndex;
    /** Ordering index for the HORIZONTALS field (S-W-N-E) */
    private final int data2d;
    private final String name;
    private final Direction.Axis axis;
    private final Direction.AxisDirection axisDirection;
    /** Normalized vector that points in the direction of this Direction */
    private final Vec3i normal;
    private static final Direction[] VALUES = values();

    private static final Direction[] BY_3D_DATA = Arrays.stream(VALUES).sorted(Comparator.comparingInt((p_235687_) -> {
        return p_235687_.data3d;
    })).toArray((p_235681_) -> {
        return new Direction[p_235681_];
    });


    private Direction(int pData3d, int pOppositeIndex, int pData2d, String pName, Direction.AxisDirection pAxisDirection, Direction.Axis pAxis, Vec3i pNormal) {
        this.data3d = pData3d;
        this.data2d = pData2d;
        this.oppositeIndex = pOppositeIndex;
        this.name = pName;
        this.axis = pAxis;
        this.axisDirection = pAxisDirection;
        this.normal = pNormal;
    }

    public Axis getAxis() {
        return axis;
    }

    /**
     * @return the offset in the x direction
     */
    public int getStepX() {
        return this.normal.getX();
    }

    /**
     * @return the offset in the y direction
     */
    public int getStepY() {
        return this.normal.getY();
    }

    /**
     * @return the offset in the z direction
     */
    public int getStepZ() {
        return this.normal.getZ();
    }

    public Vec3i getNormal() {
        return normal;
    }
    public Direction getOpposite() {
        return from3DDataValue(this.oppositeIndex);
    }
    /**
     * @return the index of this Direction (0-5). The order is D-U-N-S-W-E
     */
    public int get3DDataValue() {
        return this.data3d;
    }

    /**
     * @return the {@code Direction} corresponding to the given index (0-5). Out of bounds values are wrapped around. The
     * order is D-U-N-S-W-E.
     * @see #get3DDataValue
     */
    public static Direction from3DDataValue(int pIndex) {
        return BY_3D_DATA[Math.abs(pIndex % BY_3D_DATA.length)];
    }


    public String getName() {
        return this.name;
    }

    public static enum Axis {
        X("x") {
            public int choose(int p_122496_, int p_122497_, int p_122498_) {
                return p_122496_;
            }

            public double choose(double p_122492_, double p_122493_, double p_122494_) {
                return p_122492_;
            }
        },
        Y("y") {
            public int choose(int p_122510_, int p_122511_, int p_122512_) {
                return p_122511_;
            }

            public double choose(double p_122506_, double p_122507_, double p_122508_) {
                return p_122507_;
            }
        },
        Z("z") {
            public int choose(int p_122524_, int p_122525_, int p_122526_) {
                return p_122526_;
            }

            public double choose(double p_122520_, double p_122521_, double p_122522_) {
                return p_122522_;
            }
        };

        public static final Direction.Axis[] VALUES = values();
        private final String name;

        Axis(String pName) {
            this.name = pName;
        }


        public String getName() {
            return this.name;
        }

        public boolean isVertical() {
            return this == Y;
        }

        /**
         * @return whether this Axis is on the horizontal plane (true for X and Z)
         */
        public boolean isHorizontal() {
            return this == X || this == Z;
        }

        public String toString() {
            return this.name;
        }

        public static Direction.Axis getRandom(Random pRandom) {
            return Util.getRandom(VALUES, pRandom);
        }

        public boolean test(@Nullable Direction pDirection) {
            return pDirection != null && pDirection.getAxis() == this;
        }

        /**
         * @return this Axis' Plane (VERTICAL for Y, HORIZONTAL for X and Z)
         */
        public Direction.Plane getPlane() {
            Direction.Plane direction$plane;
            switch (this) {
                case X:
                case Z:
                    direction$plane = Direction.Plane.HORIZONTAL;
                    break;
                case Y:
                    direction$plane = Direction.Plane.VERTICAL;
                    break;
                default:
                    throw new IncompatibleClassChangeError();
            }

            return direction$plane;
        }

        public String getSerializedName() {
            return this.name;
        }

        public abstract int choose(int pX, int pY, int pZ);

        public abstract double choose(double pX, double pY, double pZ);
    }



    public static enum AxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(-1, "Towards negative");

        private final int step;
        private final String name;

        private AxisDirection(int pStep, String pName) {
            this.step = pStep;
            this.name = pName;
        }

        /**
         * @return the offset for this AxisDirection. 1 for POSITIVE, -1 for NEGATIVE
         */
        public int getStep() {
            return this.step;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        public Direction.AxisDirection opposite() {
            return this == POSITIVE ? NEGATIVE : POSITIVE;
        }
    }

    public static enum Plane implements Iterable<Direction>, Predicate<Direction> {
        HORIZONTAL(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}, new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}),
        VERTICAL(new Direction[]{Direction.UP, Direction.DOWN}, new Direction.Axis[]{Direction.Axis.Y});

        private final Direction[] faces;
        private final Direction.Axis[] axis;

        private Plane(Direction[] pFaces, Direction.Axis[] pAxis) {
            this.faces = pFaces;
            this.axis = pAxis;
        }

        public Direction getRandomDirection(Random pRandom) {
            return Util.getRandom(this.faces, pRandom);
        }

        public Direction.Axis getRandomAxis(Random pRandom) {
            return Util.getRandom(this.axis, pRandom);
        }

        public boolean test(@Nullable Direction pDirection) {
            return pDirection != null && pDirection.getAxis().getPlane() == this;
        }

        public Iterator<Direction> iterator() {
            return Iterators.forArray(this.faces);
        }

        public Stream<Direction> stream() {
            return Arrays.stream(this.faces);
        }

        public List<Direction> shuffledCopy(Random pRandom) {
            return Util.shuffledCopy(this.faces, pRandom);
        }
    }

}
